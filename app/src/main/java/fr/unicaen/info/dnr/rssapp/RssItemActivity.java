package fr.unicaen.info.dnr.rssapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.manager.EntryManager;

public class RssItemActivity extends AppCompatActivity {

    ListView rssItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item);

        rssItemList = (ListView) findViewById(R.id.rssItemList);

        Bundle bundle = getIntent().getExtras();
        List<RSSItem> items = new EntryManager(this).getItemsById(bundle.getLong("id"));

        List<HashMap<String, String>> liste = new ArrayList();

        HashMap<String, String> element;
        for(int i = 0 ; i < items.size() ; i++) {
            element = new HashMap();
            element.put("link", items.get(i).getLink());
            if (items.get(i).getStringPubDate() != "")
                element.put("date", DateFormat.getDateTimeInstance().format(items.get(i).getPubDate()) + "");
            element.put("description", Html.fromHtml(items.get(i).getDescription()).toString());
            liste.add(element);
        }

        ListAdapter adapter = new SimpleAdapter(
                this,
                liste,
                R.layout.details_item_list,
                new String[] {"link", "date", "description"},
                new int[] {R.id.link, R.id.date, R.id.description }
        );
        rssItemList.setAdapter(adapter);
    }
}
