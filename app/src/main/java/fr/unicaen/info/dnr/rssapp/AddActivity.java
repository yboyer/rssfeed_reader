package fr.unicaen.info.dnr.rssapp;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("## AddActivity:save", item.getItemId()+"");
        if (item.getItemId() == R.id.menu_save) {
            NavUtils.navigateUpFromSameTask(this);
            // TODO: Upsert on DB
            // new RetrieveFeedTask(this).execute("http://korben.info/feed");
            // TODO: open the Feed activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
}
