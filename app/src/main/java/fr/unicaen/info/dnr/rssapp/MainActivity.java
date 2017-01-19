package fr.unicaen.info.dnr.rssapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.unicaen.info.dnr.rssapp.fr.unicaen.info.dnr.rssapp.entity.Message;
import fr.unicaen.info.dnr.rssapp.sqlite.MessageDb;
import fr.unicaen.info.dnr.rssapp.sqlite.MessageDbOpener;

public class MainActivity extends AppCompatActivity {

    private ListView rssList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MessageDbOpener mDbHelper = new MessageDbOpener(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        MessageDb.add(db,"toto","19 janvier 2017");

        // MessageDb.delete(db,2);

        String[] args = { "toto", "19 janvier 2017" };

        rssList = (ListView) findViewById(R.id.rssList);
        String[] rssItems = new String[10];

        List messages = MessageDb.get(db,args);
        for ( int i = 0; i < rssItems.length; i++) {
            Log.d("Message : " + i,messages.get(i).toString());
            Message message = (Message)messages.get(i);
            rssItems[i] = messages.get(i).toString();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,rssItems);
        rssList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
