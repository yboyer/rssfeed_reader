package fr.unicaen.info.dnr.rssapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import org.mcsoxford.rss.*;

import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.*;


class RetrieveFeedTask extends AsyncTask<String, Void, RSSFeed> {
    private Exception exception;
    private Context context;
    private SQLiteDatabase db;

    public RetrieveFeedTask (Context context){
        this.context = context;
        this.db = new RSSItemDbOpener(this.context).getWritableDatabase();
    }

    protected RSSFeed doInBackground(String... urls) {
        try {
            String url = urls[0];
            Log.d("## RetrieveFeedTask", "Getting \"" + url + "\"");
            return new RSSReader().load(url);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(RSSFeed feed) {
        List<org.mcsoxford.rss.RSSItem> list = feed.getItems();
        SQLiteDatabase db = new RSSItemDbOpener(this.context).getWritableDatabase();


        for (org.mcsoxford.rss.RSSItem item : feed.getItems()) {
            // item.getContent();
            // item.getCategories();
            // item.getDescription();
            // item.getLink();
            // item.getPubDate();
            // RSSItemDb.add(db, new RSSItem(0, item.getContent(), item.getPubDate()));
        }
    }
}
