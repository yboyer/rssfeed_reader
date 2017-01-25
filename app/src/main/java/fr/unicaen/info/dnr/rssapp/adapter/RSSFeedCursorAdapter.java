package fr.unicaen.info.dnr.rssapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import fr.unicaen.info.dnr.rssapp.R;


/**
 * Created by lenaic on 25/01/2017.
 */

public class RSSFeedCursorAdapter extends ResourceCursorAdapter {
    public RSSFeedCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));

        TextView url = (TextView) view.findViewById(R.id.url);
        url.setText(cursor.getString(cursor.getColumnIndex("url")));
    }
}
