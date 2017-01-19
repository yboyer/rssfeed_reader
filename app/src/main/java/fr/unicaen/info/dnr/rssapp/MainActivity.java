package fr.unicaen.info.dnr.rssapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.List;

import fr.unicaen.info.dnr.rssapp.sqlite.MessageDb;
import fr.unicaen.info.dnr.rssapp.sqlite.MessageDbOpener;
import fr.unicaen.info.dnr.rssapp.sqlite.MessageDbOperation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MessageDbOpener mDbHelper = new MessageDbOpener(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        MessageDb.add(db,"toto","19 janvier 2017");

        MessageDb.delete(db,2);

        String[] args = { "toto", "19 janvier 2017" };

        List messages = MessageDb.get(db,args);
        for ( int i = 0; i < messages.size(); i++) {
            Log.d("Message : " + i,messages.get(i).toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
