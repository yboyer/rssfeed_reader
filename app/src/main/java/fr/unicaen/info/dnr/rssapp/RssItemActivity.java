package fr.unicaen.info.dnr.rssapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.manager.EntryManager;

public class RssItemActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView rssItemList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RSSFeed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rssItemList = (ListView) findViewById(R.id.rssItemList);
        feed = new EntryManager(this).getFeed(getIntent().getExtras().getLong("id"));
        this.refreshList();
        setTitle(feed.getName());
    }

    public void refreshList() {
        EntryManager em = new EntryManager(this);
        em.upsert(feed);
        this.feed = em.getFeed(feed.getId());
        List<RSSItem> items = feed.getItems();
        List<HashMap<String, String>> liste = new ArrayList();
        HashMap<String, String> element;
        List<HashMap<String, String>> adapterList = new ArrayList();

        for(int i = 0 ; i < items.size() ; i++) {
            element = new HashMap();
            element.put("link", items.get(i).getLink());
            if (items.get(i).getStringPubDate() != "") {
                element.put("date", DateFormat.getDateTimeInstance().format(items.get(i).getPubDate()) + "");
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                element.put("description", Html.fromHtml(items.get(i).getDescription(),Html.FROM_HTML_MODE_LEGACY).toString());
            } else {
                element.put("description", Html.fromHtml(items.get(i).getDescription()).toString());
            }
            element.put("description", Html.fromHtml(items.get(i).getDescription()).toString());
            adapterList.add(element);
        }

        ListAdapter adapter = new SimpleAdapter(
                this,
                adapterList,
                R.layout.details_item_list,
                new String[] {"link", "date", "description"},
                new int[] {R.id.link, R.id.date, R.id.description }
        );
        rssItemList.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}
