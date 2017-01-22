package fr.unicaen.info.dnr.rssapp.sqlite.rssitem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;


/**
 * Created by lenaic on 11/01/2017.
 */
public final class RSSItemDb {

    /**
     * Create a new item in our database.
     * @param db : database.
     * @param item : Object Message.
     */
    public static void add(SQLiteDatabase db, RSSItem item) {
        ContentValues values = new ContentValues();
        //values.put(RSSItemDbOperation.FeedEntry.COLUMN_NAME_TEXT, item.getMessage());
        //values.put(RSSItemDbOperation.FeedEntry.COLUMN_NAME_DATE, item.getDate());

        // db.insert(RSSItemDbOperation.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Delete a item using an id in our database.
     * @param db : database.
     * @param id : item identifier.
     */
    public static void delete(SQLiteDatabase db, long id) {
        String selection = RSSItemDbOperation.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };
        db.delete(RSSItemDbOperation.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * List all the rssItems from our database.
     * @param db : database.
     * @param args : arguments from the where clause.
     * @return List of Object RSSItem.
     */
    public static List get(SQLiteDatabase db, String[] args) {
        String[] projection = {
                RSSItemDbOperation.FeedEntry._ID,
                RSSItemDbOperation.FeedEntry.COLUMN_NAME_TEXT,
                RSSItemDbOperation.FeedEntry.COLUMN_NAME_DATE
        };

        String selection = RSSItemDbOperation.FeedEntry.COLUMN_NAME_TEXT + " = ? AND " + RSSItemDbOperation.FeedEntry.COLUMN_NAME_DATE + " = ?";

        String sortOrder =
                RSSItemDbOperation.FeedEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                RSSItemDbOperation.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                args,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List itemIds = new ArrayList();
        while(cursor.moveToNext()) {
            // long rssItemId = cursor.getLong(cursor.getColumnIndexOrThrow(RSSItemDbOperation.FeedEntry._ID));
            // String rssItemText = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.FeedEntry.COLUMN_NAME_TEXT));
            // String rssItemDate = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.FeedEntry.COLUMN_NAME_DATE));
            // RSSItem rssItem = new RSSItem(rssItemId, rssItemText, rssItemDate);
            // itemIds.add(rssItem);
        }
        cursor.close();

        return itemIds;
    }




}
