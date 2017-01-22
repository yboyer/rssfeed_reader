package fr.unicaen.info.dnr.rssapp.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.R;
import fr.unicaen.info.dnr.rssapp.entity.*; // RSSItem, RSSFeed
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.*; // RSSFeedDb, RSSFeedDbOpener
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.*; // RSSItemDb, RSSItemDbOpener

/**
 * Represents an entry manager.
 */
public class EntryManager {
    private final Context context;
    private ProgressDialog loading;

    public EntryManager(Context context) {
        this.context = context;
    }

    /**
     * Display the loading dialog
     */
    private void showLoading() {
        this.loading = new ProgressDialog(this.context);
        this.loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.loading.setMessage(this.context.getResources().getString(R.string.checking));
        this.loading.setIndeterminate(true);
        this.loading.setCancelable(false);
        this.loading.show();
    }

    /**
     * Close the loading dialog
     */
    private void closeLoading() {
        this.loading.dismiss();
    }

    /**
     * Get a complete feed
     * @param feedEntry The feed to retrieve
     * @return The completed feed
     */
    public RSSFeed getFeed(RSSFeed feedEntry) {
        final SQLiteDatabase feedDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        List<RSSItem> list = RSSFeedDb.getItems(feedDB, feedEntry);
        feedEntry.setItems(list);

        return feedEntry;
    }

    /**
     * Clean the database
     */
    public void clean() {
        SQLiteDatabase itemDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        itemDB.execSQL(RSSItemDbOperation.SQL_DELETE_ENTRIES);
        itemDB.execSQL(RSSItemDbOperation.SQL_CREATE_ENTRIES);

        SQLiteDatabase feedDB = new RSSItemDbOpener(this.context).getWritableDatabase();
        feedDB.execSQL(RSSFeedDbOperation.SQL_DELETE_ENTRIES);
        feedDB.execSQL(RSSFeedDbOperation.SQL_CREATE_ENTRIES);
    }

    /**
     * Fill the database with default RSS feeds
     */
    public void fill() {
        clean();
        add(new RSSFeed("Korben", "http://korben.info/feed"));
    }

    public List getFeed() {
        SQLiteDatabase db = new RSSFeedDbOpener(this.context).getWritableDatabase();
        String[] args = { "", "" };
        List links = RSSFeedDb.get(db,args);
        return links;
    }

    /**
     * Add a feed and its items on database
     * @param rssFeed The RSS feed
     */
    public void add(final RSSFeed rssFeed) {
        showLoading();

        // Add the feed item on database
        final SQLiteDatabase feedDB = new RSSFeedDbOpener(this.context).getWritableDatabase();
        final long feedId = RSSFeedDb.add(feedDB, rssFeed);

        // For the future uses
        final Context context = this.context;

        // Async retrieve
        new RetrieveFeedTask(new RetrieveFeedTask.AsyncResponse(){
            @Override
            public void processFinish(org.mcsoxford.rss.RSSFeed feed, Exception e){
                closeLoading();

                if (e != null) {
                    // Display the exception as an error
                    new AlertDialog.Builder(context)
                        .setTitle(R.string.error)
                        .setMessage(e.getMessage())
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                    return;
                }

                List<org.mcsoxford.rss.RSSItem> list = feed.getItems();
                Log.d("## EntryManager:add", rssFeed.getUrl() + " have " + list.size() + " items");

                // Add feed items on database
                final SQLiteDatabase itemDB = new RSSItemDbOpener(context).getWritableDatabase();
                for (org.mcsoxford.rss.RSSItem item : list) {
                    RSSItem rssItem = new RSSItem()
                        .setLink(item.getLink().toString())
                        .setDescription(item.getDescription())
                        .setContent(item.getContent())
                        .setPubDate(item.getPubDate())
                        .setFeedId(feedId);

                    RSSItemDb.add(itemDB, rssItem);
                }
            }
        }).execute(rssFeed);
    }
}
