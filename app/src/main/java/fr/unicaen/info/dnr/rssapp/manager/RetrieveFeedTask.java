package fr.unicaen.info.dnr.rssapp.manager;

import android.os.AsyncTask;
import android.util.Log;
import org.mcsoxford.rss.RSSReader;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;

/**
 * Represents an async feed retriever
 */
public class RetrieveFeedTask extends AsyncTask<RSSFeed, Void, org.mcsoxford.rss.RSSFeed>{
    /**
     * The async callback
     */
    protected interface AsyncResponse {
        void processFinish(org.mcsoxford.rss.RSSFeed feed, Exception e);
    }

    protected AsyncResponse callback = null;
    private Exception exception = null;


    protected RetrieveFeedTask(AsyncResponse callback){
        this.callback = callback;
    }

    @Override
    protected org.mcsoxford.rss.RSSFeed doInBackground(RSSFeed... feeds) {
        try {
            RSSFeed feed = feeds[0];
            Log.d("## RetrieveFeedTask", "Getting \"" + feed.getUrl() + "\"");
            return new RSSReader().load(feed.getUrl());
        } catch (Exception e) {
            Log.d("## RetrieveFeedTask", "Exception \"" + e.toString() + "\"");
            this.exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(org.mcsoxford.rss.RSSFeed feed) {
        this.callback.processFinish(feed, this.exception);
    }
}
