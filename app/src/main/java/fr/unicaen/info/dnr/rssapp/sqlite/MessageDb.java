package fr.unicaen.info.dnr.rssapp.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenaic on 11/01/2017.
 */

public final class MessageDb {

    public static void add(SQLiteDatabase db, String message, String date) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT, message);
        values.put(MessageDbOperation.FeedEntry.COLUMN_NAME_DATE, date);

        // Insert the new row, returning the primary key value of the new row
        db.insert(MessageDbOperation.FeedEntry.TABLE_NAME, null, values);
    }

    public static void delete(SQLiteDatabase db, long id) {
        String selection = MessageDbOperation.FeedEntry._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { id+"" };
        // Issue SQL statement.
        db.delete(MessageDbOperation.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public static List get(SQLiteDatabase db, String[] args) {
        String[] projection = {
                MessageDbOperation.FeedEntry._ID,
                MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT,
                MessageDbOperation.FeedEntry.COLUMN_NAME_DATE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT + " = ? AND " + MessageDbOperation.FeedEntry.COLUMN_NAME_DATE + " = ?";

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MessageDbOperation.FeedEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                MessageDbOperation.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                args,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List itemIds = new ArrayList();
        while(cursor.moveToNext()) {
            long messageId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(MessageDbOperation.FeedEntry._ID));
            String messageText = cursor.getString(cursor.getColumnIndexOrThrow(MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT));
            String messageDate = cursor.getString(cursor.getColumnIndexOrThrow(MessageDbOperation.FeedEntry.COLUMN_NAME_DATE));
            Message message = new Message(messageId,messageText,messageDate);
            itemIds.add(message);
        }
        cursor.close();

        return itemIds;
    }




}
