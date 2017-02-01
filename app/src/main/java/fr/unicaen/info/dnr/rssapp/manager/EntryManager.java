package fr.unicaen.info.dnr.rssapp.manager;

import android.content.Context;
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

/**
 * Represents an entry manager.
 */
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

        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        feedDB.execSQL(RSSFeedDbOperation.SQL_DELETE_ENTRIES);
        feedDB.execSQL(RSSFeedDbOperation.SQL_CREATE_ENTRIES);
    }

    /**
     * Fill the database with default RSS feeds.
     */
    public void fill() {
        //clean();
        //add(new RSSFeed("Le monde", "http://lesclesdedemain.lemonde.fr/screens/RSS/sw_getFeed.php?idTheme=HOME"));
        //add(new RSSFeed("Libération", "http://rss.liberation.fr/rss/latest/"));
        //add(new RSSFeed("BBC", "http://feeds.bbci.co.uk/news/video_and_audio/technology/rss.xml"));
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

        return feedEntry;
    }

    /**
     * Search in database if there is a RSS feed with same name or url.
     * @param feed : the RSS feed to search.
     * @return : a RSS feed or null.
     */
    private RSSFeed findFeed(RSSFeed feed) {
        // Find feed using his name and url
        SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        return RSSFeedDb.getFeedByNameOrUrl(feedDB,feed.getUrl(), feed.getName());
    }

    /**
     * Search in database if there is a RSS item with same id.
     * @param item : the RSS item to search.
     * @return : a RSS item of null.
     */
    private RSSItem findItem(RSSItem item) {
        // Find item using his id
        SQLiteDatabase itemDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        return RSSItemDb.getItemById(itemDB,item.getId());
    }

    /**
     * Add a feed and its items on database
     * @param rssFeed The RSS feed
     */
    public void upsert(final RSSFeed rssFeed, final AsyncResponse callback) {
        // Add the feed item on database
        final SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        if (findFeed(rssFeed) == null) {
            final long feedId = RSSFeedDb.add(feedDB, rssFeed);
            // For the future uses
            final Context context = this.context;

            // Async retrieve
            new RetrieveFeedTask(new RetrieveFeedTask.AsyncResponse(){
                @Override
                public void processFinish(List<RSSItem> items, Exception e){
                    if (e != null) {
                        // Display the exception as an error
                        new AlertDialog.Builder(context)
                            .setTitle(R.string.error)
                            .setMessage(e.getMessage())
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                        return;
                    }

                    Log.d("## EntryManager:add", rssFeed.getUrl() + " have " + items.size() + " items");

                    // Add feed items on database
                    final SQLiteDatabase itemDB = new RSSItemDbOpener(context).getWritableDatabase();
                    for (RSSItem item : items) {
                        if (findItem(item) == null) {
                            RSSItemDb.add(itemDB, item.setFeedId(feedId));
                        }
                    }

                    if (callback != null) {
                        callback.processFinish();
                    }
                }
            }).execute(rssFeed);
        } else {
            Toast.makeText(this.context, "Ce flux RSS a déjà été ajouté !", Toast.LENGTH_SHORT).show();
        }
    }
}
