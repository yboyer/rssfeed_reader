package fr.unicaen.info.dnr.rssapp;

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

public class RssItemActivity extends AppCompatActivity {

    ListView rssItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item);

        rssItemList = (ListView) findViewById(R.id.rssItemList);

        RSSFeed feed = new EntryManager(this).getFeed(getIntent().getExtras().getLong("id"));
        setTitle(feed.getName());
        List<RSSItem> items = feed.getItems();

        List<HashMap<String, String>> adapterList = new ArrayList();

        HashMap<String, String> element;
        for(int i = 0 ; i < items.size() ; i++) {
            element = new HashMap();
            element.put("link", items.get(i).getLink());
            if (items.get(i).getStringPubDate() != "") {
                element.put("date", DateFormat.getDateTimeInstance().format(items.get(i).getPubDate()) + "");
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
}
