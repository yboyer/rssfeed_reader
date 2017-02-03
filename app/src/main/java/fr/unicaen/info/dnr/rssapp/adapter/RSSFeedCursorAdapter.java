package fr.unicaen.info.dnr.rssapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import fr.unicaen.info.dnr.rssapp.MainActivity;
import fr.unicaen.info.dnr.rssapp.R;
import fr.unicaen.info.dnr.rssapp.RssItemActivity;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.manager.EntryManager;


/**
 * Created by lenaic on 25/01/2017.
 */

public class RSSFeedCursorAdapter extends ResourceCursorAdapter {

    private AlertDialog addFeedDialog;

    public RSSFeedCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        final TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));

        final TextView url = (TextView) view.findViewById(R.id.url);
        url.setText(cursor.getString(cursor.getColumnIndex("url")));

        final Cursor cursor1 = cursor;
        long id = cursor.getLong(cursor.getColumnIndex("_id"));

        ImageView edit = (ImageView) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                //ouvrir un formulaire de modification de l'item de la rssList a position
                //puis valider ces changements en BDD
                notifyDataSetChanged();

                // Get the dialog layout
                View promptView = LayoutInflater.from(context).inflate(R.layout.dialog_modify, null);

                final EditText dialogFeedName = (EditText) promptView.findViewById(R.id.modifyDialogFeedName);
                final EditText dialogFeedUrl = (EditText) promptView.findViewById(R.id.modifyDialogFeedUrl);

                dialogFeedName.setText(cursor1.getString(cursor1.getColumnIndex("name")));
                dialogFeedUrl.setText(cursor1.getString(cursor1.getColumnIndex("url")));

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

                //Create and show the dialog code
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(R.string.update);

                // set dialog message
                alertDialogBuilder.setView(promptView)
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String name = dialogFeedName.getText().toString();
                                String url = dialogFeedUrl.getText().toString();

                                // Add http if the url does not contains it
                                if (!url.startsWith("http")) {
                                    url = "http://" + url;
                                }

                                EntryManager em = new EntryManager(context);
                                RSSFeed feed = em.getFeed(id);
                                // Modify feed in DB
                                em.update(feed);
                                Toast.makeText(context, R.string.update_success, Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                addFeedDialog = alertDialogBuilder.create();

                addFeedDialog.show();
                // Disable the positive button
                addFeedDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            }
        });
        /*
        ImageView delete = (ImageView) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                Integer position = (Integer) v.getTag();
                //remove l'item de la rssList a position et dans la BDD
                notifyDataSetChanged();
            }
        });*/
        notifyDataSetChanged();
    }
}
