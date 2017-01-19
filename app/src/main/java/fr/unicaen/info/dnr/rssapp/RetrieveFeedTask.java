package fr.unicaen.info.dnr.rssapp;

import android.os.AsyncTask;
import android.util.Log;
import org.mcsoxford.rss.*;
import java.util.List;


class RetrieveFeedTask extends AsyncTask<String, Void, RSSFeed> {

    private Exception exception;

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
        List<RSSItem> list = feed.getItems();
    }
}
