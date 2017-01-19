package fr.unicaen.info.dnr.rssapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.List;

import fr.unicaen.info.dnr.rssapp.sqlite.message.MessageDb;
import fr.unicaen.info.dnr.rssapp.sqlite.message.MessageDbOpener;
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.RSSFeedDb;
import fr.unicaen.info.dnr.rssapp.sqlite.rssfeed.RSSFeedDbOpener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
