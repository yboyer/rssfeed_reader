package fr.unicaen.info.dnr.rssapp.sqlite.rssfeed;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import fr.unicaen.info.dnr.rssapp.entity.RSSFeed;


/**
 * Created by lenaic on 11/01/2017.
 */
public final class RSSFeedDb {

    /**
     * Create a new link from an RSSFeed in our database.
     * @param db : database.
     * @param item : Object RSSFeed.
     */
    public static void add(SQLiteDatabase db, RSSFeed item) {
        // ContentValues values = new ContentValues();
        // values.put(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LINK, link.getUrl());

        // db.insert(RSSFeedDbOperation.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Delete a link using an id in our database.
     * @param db : database.
     * @param id : link identifier.
     */
    public static void delete(SQLiteDatabase db, long id) {
        String selection = RSSFeedDbOperation.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };
        db.delete(RSSFeedDbOperation.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * List all the links from our database.
     * @param db : database.
     * @param args : arguments from the where clause.
     * @return List of Object RSSFeed.
     */
    public static List get(SQLiteDatabase db, String[] args) {
        String[] projection = {
                RSSFeedDbOperation.FeedEntry._ID,
                RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL,
        };

        String selection = RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL + " = ?";

        String sortOrder =
                RSSFeedDbOperation.FeedEntry.COLUMN_NAME_URL + " ASC";

        Cursor cursor = db.query(
                RSSFeedDbOperation.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                args,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List feed = new ArrayList();
        while(cursor.moveToNext()) {
            // long linkId = cursor.getLong(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry._ID));
            // String linkText = cursor.getString(cursor.getColumnIndexOrThrow(RSSFeedDbOperation.FeedEntry.COLUMN_NAME_LINK));
            //RSSFeed link = new RSSFeed(linkId,linkText);
            //feed.add(link);
        }
        cursor.close();

        return feed;
    }




}
