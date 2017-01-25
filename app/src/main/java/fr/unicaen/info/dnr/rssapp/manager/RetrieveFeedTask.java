package fr.unicaen.info.dnr.rssapp.manager;

import android.os.AsyncTask;
import android.util.Log;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;

/**
 * Represents an async feed retriever
 */
public class RetrieveFeedTask extends AsyncTask<RSSFeed, Void, List<RSSItem>>{
    /**
     * The async callback
     */
    protected interface AsyncResponse {
        void processFinish(List<RSSItem> feed, Exception e);
    }

    protected AsyncResponse callback = null;
    private Exception exception = null;


    protected RetrieveFeedTask(AsyncResponse callback){
        this.callback = callback;
    }

    @Override
    protected List<RSSItem> doInBackground(RSSFeed... feeds) {
        try {
            RSSFeed feed = feeds[0];
            HttpURLConnection connection = (HttpURLConnection) new URL(feed.getUrl()).openConnection();
            Iterator itEntries = new SyndFeedInput()
                .build(new XmlReader(connection))
                .getEntries()
                .iterator();

            List<RSSItem> list = new ArrayList<RSSItem>();
            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();

                Date date = entry.getPublishedDate();
                if (date == null) {
                    date = entry.getUpdatedDate();
                }

                list.add(new RSSItem()
                    .setDescription(entry.getDescription().getValue())
                    .setPubDate(date)
                    .setContent(entry.getContents().toString())
                    .setLink(entry.getLink())
                );
            }
            return list;
        } catch (Exception e) {
            Log.d("## RetrieveFeedTask", "Exception \"" + e.toString() + "\"");
            this.exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<RSSItem> feed) {
        this.callback.processFinish(feed, this.exception);
    }
}
