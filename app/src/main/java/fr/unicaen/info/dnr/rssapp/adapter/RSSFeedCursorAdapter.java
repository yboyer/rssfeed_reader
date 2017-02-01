package fr.unicaen.info.dnr.rssapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import fr.unicaen.info.dnr.rssapp.R;


/**
 * Created by lenaic on 25/01/2017.
 */

public class RSSFeedCursorAdapter extends ResourceCursorAdapter {
    public RSSFeedCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));

        TextView url = (TextView) view.findViewById(R.id.url);
        url.setText(cursor.getString(cursor.getColumnIndex("url")));

        ImageView edit = (ImageView) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click edit", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView delete = (ImageView) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                
            }
        });
    }
}
