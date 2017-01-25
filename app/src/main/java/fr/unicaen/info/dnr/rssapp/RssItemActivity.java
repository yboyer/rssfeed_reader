package fr.unicaen.info.dnr.rssapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.manager.EntryManager;

public class RssItemActivity extends AppCompatActivity {

    TextView itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item);

        itemSelected = (TextView) findViewById(R.id.itemSelected);
        Bundle bundle = getIntent().getExtras();
        List<RSSItem> item = new EntryManager(this).getItemsById(bundle.getLong("id"));

        if(bundle != null) {
            //itemSelected.setText(item.getLink());
        }
    }
}
