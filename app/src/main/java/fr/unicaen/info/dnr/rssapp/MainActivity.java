package fr.unicaen.info.dnr.rssapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import fr.unicaen.info.dnr.rssapp.adapter.RSSFeedCursorAdapter;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.manager.EntryManager;


public class MainActivity extends AppCompatActivity{
    private ListView rssList;
    private AlertDialog addFeedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rssList = (ListView) findViewById(R.id.rssList);

        // Open the RSSListActivity
        rssList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RssItemActivity.class);
                intent.putExtra("id", rssList.getItemIdAtPosition(position));
                startActivity(intent);
            }
        });

        // Open the modal to delete the item
        rssList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
                removeItemFromList(id);
                return true;
            }
        });

        EntryManager em = new EntryManager(this);
        RSSFeed feedEntry = new RSSFeed("BBC World news", "http://feeds.bbci.co.uk/news/world/rss.xml");
        if (em.findFeed(feedEntry) == null) {
            em.insert(feedEntry);
        }
        refreshList();
    }

    /**
     * Remove an item from the list
     * @param id The id
     */
    protected void removeItemFromList(final long id) {
        final Context context = this;

        new AlertDialog.Builder(this)
            .setTitle(R.string.delete)
            .setMessage(R.string.ask_delete)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new EntryManager(context).remove(id);
                    Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                    refreshList();
                }
            })
            .setNegativeButton(android.R.string.no, null)
            .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                openAddFeedDialogBox();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Refresh the ListView of all RSS feeds.
     */
    public void refreshList() {
        rssList.setAdapter(new RSSFeedCursorAdapter(
            this,
            R.layout.item_list,
            new EntryManager(this).getFeedsCursor(),
            0
        ));
    }

    /**
     * Display the adding feed dialog
     */
    private void openAddFeedDialogBox() {
        // Get the dialog layout
        View promptView = this.getLayoutInflater().inflate(R.layout.dialog_signin, null);

        final EditText dialogFeedName = (EditText) promptView.findViewById(R.id.dialogFeedName);
        final EditText dialogFeedUrl = (EditText) promptView.findViewById(R.id.dialogFeedUrl);

        // Watch text events
        TextWatcher textWatcher = new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {}
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean dialogFeedNameValid = dialogFeedName.getText().length() != 0;
                boolean dialogFeedUrlValid = Patterns.WEB_URL.matcher(dialogFeedUrl.getText()).matches();

                // Disable the positive button if inputs are invalid
                addFeedDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(dialogFeedNameValid && dialogFeedUrlValid);
            }
        };
        dialogFeedName.addTextChangedListener(textWatcher);
        dialogFeedUrl.addTextChangedListener(textWatcher);

        // Create the dialog
        final MainActivity main = this;
        final AlertDialog.Builder addFeedBuilder = new AlertDialog.Builder(this);
        addFeedBuilder.setTitle(R.string.addFeed);
        addFeedBuilder.setView(promptView)
            .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    String name = dialogFeedName.getText().toString();
                    String url = dialogFeedUrl.getText().toString();

                    // Add http if the url does not contains it
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }

                    // Add the feed on database
                    new EntryManager(main).insert(new RSSFeed(name, url));
                    refreshList();
                }
            })
            .setNegativeButton(android.R.string.cancel, null);
        addFeedDialog = addFeedBuilder.create();

        addFeedDialog.show();
        // Disable the positive button
        addFeedDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
