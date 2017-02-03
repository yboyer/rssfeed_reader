package fr.unicaen.info.dnr.rssapp.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import fr.unicaen.info.dnr.rssapp.R;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.RSSFeedDb;
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.RSSFeedDbOpener;
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.RSSFeedDbOperation;
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.RSSItemDb;
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.RSSItemDbOpener;
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.RSSItemDbOperation;


public class EntryManager {

    /**
     * The async callback
     */
    public interface AsyncResponse {
        void processFinish();
    }

    private final Context context;

    public EntryManager(Context context) {
        this.context = context;
    }

    /**
     * Clean the database.
     */
    public void clean() {
        SQLiteDatabase itemDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        itemDB.execSQL(RSSItemDbOperation.SQL_DELETE_ENTRIES);
        itemDB.execSQL(RSSItemDbOperation.SQL_CREATE_ENTRIES);
        itemDB.close();

        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        feedDB.execSQL(RSSFeedDbOperation.SQL_DELETE_ENTRIES);
        feedDB.execSQL(RSSFeedDbOperation.SQL_CREATE_ENTRIES);
        feedDB.close();
    }

    public void remove(long id) {
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        RSSFeedDb.delete(feedDB,id);
        feedDB.close();
    }

    /**
     * Fill the database with default RSS feeds.
     */
    public void fill() {
        clean();
        insert(new RSSFeed("Le monde", "http://lesclesdedemain.lemonde.fr/screens/RSS/sw_getFeed.php?idTheme=HOME"));
        insert(new RSSFeed("Libération", "http://rss.liberation.fr/rss/latest/"));
        insert(new RSSFeed("BBC", "http://feeds.bbci.co.uk/news/video_and_audio/technology/rss.xml"));
    }

    /**
     * Get the feeds retrieval cursor
     * @return The cursor
     */
    public Cursor getFeedsCursor() {
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getReadableDatabase();
        return RSSFeedDb.getFeedsCursor(feedDB);
    }

    public List<RSSFeed> getAllFeeds() {
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getReadableDatabase();
        return RSSFeedDb.getAllFeeds(feedDB);
    }

    /**
     * Get one RSS feed.
     * @param id : The feed id.
     * @return : The RSS feed.
     */
    public RSSFeed getFeed(long id) {
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        SQLiteDatabase itemDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        RSSFeed feedEntry = RSSFeedDb.getFeedById(feedDB, id);

        feedEntry.setItems(RSSItemDb.getItemsByFeedId(itemDB, id));

        feedDB.close();
        itemDB.close();

        return feedEntry;
    }

    /**
     * Search in database if there is a RSS feed with same name or url.
     * @param feed : the RSS feed to search.
     * @return : a RSS feed or null.
     */
    public RSSFeed findFeed(RSSFeed feed) {
        // Find feed using his name and url
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        RSSFeed resFeed = RSSFeedDb.getFeedByNameOrUrl(feedDB,feed.getUrl(), feed.getName());
        feedDB.close();
        return resFeed;
    }

    /**
     * Search in database if there is a RSS item with same id.
     * @param item : the RSS item to search.
     * @return : a RSS item of null.
     */
    public RSSItem findItem(RSSItem item) {
        // Find item using his id
        SQLiteDatabase itemDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        RSSItem resItem = RSSItemDb.getItemByLink(itemDB, item.getLink());
        itemDB.close();
        return resItem;
    }

    /**
     * Add a feed and its items on database
     * @param rssFeed The feed to add
     */
    public void insert(final RSSFeed rssFeed) {
        if (findFeed(rssFeed) != null) {
            Toast.makeText(this.context, R.string.existing_feed, Toast.LENGTH_SHORT).show();
        } else {
            upsert(rssFeed, null);
        }
    }

    /**
     * Update a feed
     * @param rssFeed The feed to update
     */
    public void update(RSSFeed rssFeed) {
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        RSSFeedDb.update(feedDB, rssFeed);
        feedDB.close();
    }

    /**
     * Update a feed and its items on database
     * @param rssFeed The RSS feed
     * @param callback The callback
     */
    public void upsert(final RSSFeed rssFeed, final AsyncResponse callback) {
        // Add the feed item on database
        long feedId = rssFeed.getId();
        if (feedId == -1) {
            final SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
            feedId = RSSFeedDb.add(feedDB, rssFeed);
            feedDB.close();
        } else {
            update(rssFeed);
        }

        // For the future uses
        final Context context = this.context;

        // Async retrieve
        final long finalFeedId = feedId;
        new RetrieveFeedTask(new RetrieveFeedTask.AsyncResponse(){
            @Override
            public void processFinish(List<RSSItem> items, Exception e){
                if (e != null) {
                    // Display the exception as an error
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                            .setPositiveButton(android.R.string.ok, null)
                            ;
                    alertDialog.setTitle(R.string.error);
                    alertDialog.setMessage(R.string.errorDefaultMsg);
                    if (e instanceof com.google.code.rome.android.repackaged.com.sun.syndication.io.ParsingFeedException) {
                        alertDialog.setTitle(R.string.errorParsingTitle);
                        alertDialog.setMessage(R.string.errorParsingMsg);
                    } else if (e instanceof java.net.UnknownHostException) {
                        alertDialog.setTitle(R.string.errorUnknownHostTitle);
                        alertDialog.setMessage(R.string.errorUnknownHostMsg);
                    }
                    alertDialog.show();
                }
                else {
                    Log.d("## EntryManager:add", rssFeed.getUrl() + " have " + items.size() + " items");

                    // Add feed items on database
                    final SQLiteDatabase itemDB = new RSSItemDbOpener(context).getWritableDatabase();
                    for (RSSItem item : items) {
                        if (findItem(item) == null) {
                            RSSItemDb.add(itemDB, item.setFeedId(finalFeedId));
                        }
                    }
                    itemDB.close();
                }
                if (callback != null) {
                    callback.processFinish();
                }
            }
        }).execute(rssFeed);
    }
}
