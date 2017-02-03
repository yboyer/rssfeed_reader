package fr.unicaen.info.dnr.rssapp.sqlite.rssitem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
     * @return The inserted id
     */
    public static long add(SQLiteDatabase db, RSSItem item) {
        ContentValues values = new ContentValues();
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_DESCRIPTION, item.getDescription());
        values.put(RSSItemDbOperation.ItemEntry.COLUMN_NAME_TITLE, item.getTitle());
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
     * Get a RSS item using his link.
     * @param db : database.
     * @param link : link of the RSS Item.
     * @return : The RSS item.
     */
    public static RSSItem getItemByLink(SQLiteDatabase db, String link) {
        String[] projection = {
                RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK
        };

        // Find in db if there is a RSS item with same id
        String selection = RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK + " = ?";
        String[] where = { link };

        Cursor cursor = db.query(
                RSSItemDbOperation.ItemEntry.TABLE_NAME, // The table to query
                projection, // The columns to return
                selection, // The columns for the WHERE clause
                where, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        RSSItem item = null;

        // If there is a result, return the RSS feed
        if (cursor.moveToNext()) {
            String url = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK));

            item = new RSSItem()
                .setLink(url)
            ;
        }
        cursor.close();

        return item;
    }

    /**
     * Get a list of RSS items using a RSS feed id.
     * @param db : Database.
     * @param feedId : RSS feed id.
     * @return : list of RSS items.
     */
    public static List<RSSItem> getItemsByFeedId(SQLiteDatabase db, long feedId) {
        String[] projection = {
            RSSItemDbOperation.ItemEntry._ID,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_PUBDATE,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_TITLE,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_CONTENT,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_DESCRIPTION,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK,
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_FEEDID,
        };
        String[] where = {
                feedId + ""
        };

        // Find using the id
        String selection = RSSItemDbOperation.ItemEntry.COLUMN_NAME_FEEDID + " = ?";

        Cursor cursor = db.query(
            RSSItemDbOperation.ItemEntry.TABLE_NAME,
            projection, // The columns to return
            selection, // The columns for the WHERE clause
            where, // The values for the WHERE clause
            null, // Rows group
            null, // Row groups filter
            RSSItemDbOperation.ItemEntry.COLUMN_NAME_PUBDATE + " DESC, " + RSSItemDbOperation.ItemEntry._ID + " ASC" // The sort order
        );

        // List of RSS items
        List<RSSItem> items = new ArrayList();
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry._ID));
            String pubDate = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_PUBDATE));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_CONTENT));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_DESCRIPTION));
            String link = cursor.getString(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_LINK));
            long feedid = cursor.getLong(cursor.getColumnIndexOrThrow(RSSItemDbOperation.ItemEntry.COLUMN_NAME_FEEDID));
            // Create a RSSItem using the database result
            RSSItem rssItem = new RSSItem()
                    .setId(id)
                    .setLink(link)
                    .setTitle(title)
                    .setDescription(description)
                    .setContent(content)
                    .setStringPubDate(pubDate)
                    .setFeedId(feedid);
            items.add(rssItem);
        }
        cursor.close();

        return items;
    }
}
