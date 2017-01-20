package fr.unicaen.info.dnr.rssapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.RSSItemDb;
import fr.unicaen.info.dnr.rssapp.sqlite.rssitem.RSSItemDbOpener;


public class MainActivity extends AppCompatActivity {

    private ListView rssList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RSSItemDbOpener mDbHelper = new RSSItemDbOpener(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // RSSItemDb.delete(db,2);

        String[] args = { "toto", "19 janvier 2017" };

        rssList = (ListView) findViewById(R.id.rssList);
        String[] rssItems = new String[10];

        List messages = RSSItemDb.get(db,args);
        for ( int i = 0; i < rssItems.length; i++) {
            RSSItem message = (RSSItem)messages.get(i);
            rssItems[i] = messages.get(i).toString();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,rssItems);
        rssList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(this, AddActivity.class));
                return true;
            case R.id.menu_show_list:
                // TODO: show the Feed list
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
