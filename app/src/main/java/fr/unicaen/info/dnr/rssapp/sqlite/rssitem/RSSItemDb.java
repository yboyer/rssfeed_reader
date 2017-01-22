package fr.unicaen.info.dnr.rssapp.sqlite.rssitem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;
import fr.unicaen.info.dnr.rssapp.entity.RSSItem;


/**
 * Created by lenaic on 11/01/2017.
 */
public final class RSSItemDb {

    /**
     * Create a new item in our database.
     * @param db : database.
     * @param item : Object Message.
     * @return The inserted id
     */
    public static long add(SQLiteDatabase db, RSSItem item) {
        ContentValues values = new ContentValues();
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_DESCRIPTION, item.getDescription());
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_CONTENT, item.getContent());
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK, item.getLink());
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_PUBDATE, item.getStringPubDate());
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_FEEDID, item.getFeedId());

        Log.d("RSSItemDb:adding", item.toString());

        return db.insert(RSSItemDbOperation.ItemEntry.TABLE_NAME, null, values);
    }

    /**
     * Delete a item using an id in our database.
     * @param db : database.
     * @param id : item identifier.
     */
    public static void delete(SQLiteDatabase db, long id) {
        String selection = RSSItemDbOperation.ItemEntry._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };
        db.delete(RSSItemDbOperation.ItemEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * List all the rssItems from our database.
     * @param db : database.
     * @param args : arguments from the where clause.
     * @return List of Object RSSItem.
     */
    public static List get(SQLiteDatabase db, String[] args) {
        // TODO
        // String[] projection = {
        //         RSSItemDbOperation.ItemEntry._ID,
        //         RSSItemDbOperation.ItemEntry.COLUMN_NAME_TEXT,
        //         RSSItemDbOperation.ItemEntry.COLUMN_NAME_DATE
        // };
        //
        // String selection = RSSItemDbOperation.ItemEntry.COLUMN_NAME_TEXT + " = ? AND " + RSSItemDbOperation.ItemEntry.COLUMN_NAME_DATE + " = ?";
        //
        // String sortOrder =
        //         RSSItemDbOperation.ItemEntry.COLUMN_NAME_DATE + " DESC";
        //
        // Cursor cursor = db.query(
        //         RSSItemDbOperation.ItemEntry.TABLE_NAME,                     // The table to query
        //         projection,                               // The columns to return
        //         selection,                                // The columns for the WHERE clause
        //         args,                            // The values for the WHERE clause
        //         null,                                     // don't group the rows
        //         null,                                     // don't filter by row groups
        //         sortOrder                                 // The sort order
        // );
        //
        List itemIds = new ArrayList();
        // while(cursor.moveToNext()) {
        //     // long rssItemId = cursor.getLong(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry._ID));
        //     // String rssItemText = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_TEXT));
        //     // String rssItemDate = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_DATE));
        //     // RSSItem rssItem = new RSSItem(rssItemId, rssItemText, rssItemDate);
        //     // itemIds.add(rssItem);
        // }
        // cursor.close();
        //
        return itemIds;
    }

    /**
     * Get the feedEntry items
     * @param db The database to interacti with
     * @param feedEntry The feed reference
     * @return The feedEntry items
     */
    public static List<RSSItem> getItems(SQLiteDatabase db, RSSFeed feedEntry) {
        String[] projection = {
            RSSItemDbOperation.ItemEntry._ID,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_CONTENT,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_DESCRIPTION,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_PUBDATE,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_FEEDID,
        };

        String[] where = {
            feedEntry.getId()+""
        };
        String selection = RSSItemDbOperation.ItemEntry.COLUMN_NAME_FEEDID + " = ?";

        Cursor cursor = db.query(
            RSSItemDbOperation.ItemEntry.TABLE_NAME,
            projection, // The columns to return
            selection, // The columns for the WHERE clause
            where, // The values for the WHERE clause
            null, // Rows group
            null, // Row groups filter
            null // The sort order
        );

        List items = new ArrayList();
        while(cursor.moveToNext()) {
        //     // long rssItemId = cursor.getLong(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry._ID));
        //     // String rssItemText = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_TEXT));
        //     // String rssItemDate = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_DATE));
        //     // RSSItem rssItem = new RSSItem(rssItemId, rssItemText, rssItemDate);
        //     // items.add(rssItem);
        }
        cursor.close();

        return items;
    }
}
