package fr.unicaen.info.dnr.rssapp.sqlite.message;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.unicaen.info.dnr.rssapp.fr.unicaen.info.dnr.rssapp.entity.Message;

/**
 * Created by lenaic on 11/01/2017.
 */


public final class MessageDb {

    /**
     * Create a new message in our database.
     * @param db : database.
     * @param message : Object Message.
     */
    public static void add(SQLiteDatabase db, Message message) {
        ContentValues values = new ContentValues();
        values.put(MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT, message.getMessage());
        values.put(MessageDbOperation.FeedEntry.COLUMN_NAME_DATE, message.getDate());

        db.insert(MessageDbOperation.FeedEntry.TABLE_NAME, null, values);
    }

    /**
     * Delete a message using an id in our database.
     * @param db : database.
     * @param id : message identifier.
     */
    public static void delete(SQLiteDatabase db, long id) {
        String selection = MessageDbOperation.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { id+"" };
        db.delete(MessageDbOperation.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * List all the messages from our database.
     * @param db : database.
     * @param args : arguments from the where clause.
     * @return List of Object Message.
     */
    public static List get(SQLiteDatabase db, String[] args) {
        String[] projection = {
                MessageDbOperation.FeedEntry._ID,
                MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT,
                MessageDbOperation.FeedEntry.COLUMN_NAME_DATE
        };

        String selection = MessageDbOperation.FeedEntry.COLUMN_NAME_TEXT + " = ? AND " + MessageDbOperation.FeedEntry.COLUMN_NAME_DATE + " = ?";

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
