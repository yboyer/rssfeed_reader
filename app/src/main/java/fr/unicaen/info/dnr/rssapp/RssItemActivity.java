package fr.unicaen.info.dnr.rssapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RssItemActivity extends AppCompatActivity {

    TextView itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item);

        itemSelected = (TextView) findViewById(R.id.itemSelected);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            itemSelected.setText(bundle.getString("item"));
        }
    }
}
