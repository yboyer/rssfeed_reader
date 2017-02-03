package fr.unicaen.info.dnr.rssapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.manager.Connectivity;
import fr.unicaen.info.dnr.rssapp.manager.EntryManager;

public class RssItemActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView rssItemList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RSSFeed feed;
    private EntryManager em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item);
        long feedId = getIntent().getExtras().getLong("id");

        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);

        this.em = new EntryManager(this);

        this.rssItemList = (ListView) findViewById(R.id.rssItemList);

        this.rssItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) rssItemList.getItemAtPosition(position);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.get("link"))));
            }
        });

        this.feed = em.getFeed(feedId);
        setTitle(this.feed.getName());

        SharedPreferences data = getSharedPreferences(PreferencesActivity.PREFERENCE_KEY, 0);
        boolean autoupdate = data.getBoolean(PreferencesActivity.AUTOUPDATE_KEY, PreferencesActivity.AUTOUPDATE_DEFAULT);
        boolean wifiUpdate = data.getBoolean(PreferencesActivity.WIFIUPDATE_KEY, PreferencesActivity.WIFIUPDATE_DEFAULT);
        boolean dataUpdate = data.getBoolean(PreferencesActivity.DATAUPDATE_KEY, PreferencesActivity.DATAUPDATE_DEFAULT);

        wifiUpdate = Connectivity.isConnectedWifi(this) && wifiUpdate;
        dataUpdate = Connectivity.isConnectedMobile(this) && dataUpdate;

        boolean moreThan15Minutes = (feed.getElapsedTime() / 60) > 15;

        if (autoupdate && (wifiUpdate || dataUpdate) && moreThan15Minutes) {
            updateList();
        } else {
            refreshList();
        }
    }

    /**
     * Retrieve items and refresh the list
     */
    public void updateList() {
        mSwipeRefreshLayout.setRefreshing(true);
        this.em.upsert(this.feed, new EntryManager.AsyncResponse() {
            @Override
            public void processFinish() {
                mSwipeRefreshLayout.setRefreshing(false);
                feed = em.getFeed(feed.getId());
                refreshList();
            }
        });
    }

    /**
     * Refresh the list
     */
    public void refreshList() {
        List<HashMap<String, String>> adapterList = new ArrayList();
        List<RSSItem> items = feed.getItems();
        HashMap<String, String> element;
        for(int i = 0 ; i < items.size() ; i++) {
            element = new HashMap();

            element.put("title", items.get(i).getTitle());
            element.put("link", items.get(i).getLink());

            // Format the publication date
            if (!items.get(i).getStringPubDate().equals("")) {
                element.put("date", DateFormat.getDateTimeInstance().format(items.get(i).getPubDate()) + "");
            }
            // Checking the current Android version and parse the HTML code
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                element.put("description", Html.fromHtml(items.get(i).getDescription(),Html.FROM_HTML_MODE_LEGACY).toString());
            } else {
                element.put("description", Html.fromHtml(items.get(i).getDescription()).toString());
            }
            adapterList.add(element);
        }
        // Fill the List View
        rssItemList.setAdapter(new SimpleAdapter(
            this,
            adapterList,
            R.layout.details_item_list,
            new String[] {"title","link", "date", "description"},
            new int[] {R.id.itemTitle, R.id.link, R.id.name, R.id.description }
        ));

        getSupportActionBar().setSubtitle(this.feed.getHumanLastUpdate(this));
    }

    @Override
    public void onRefresh() {
        updateList();
    }
}
