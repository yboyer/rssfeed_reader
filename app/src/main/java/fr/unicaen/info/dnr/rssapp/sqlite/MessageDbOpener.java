package fr.unicaen.info.dnr.rssapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenaic on 11/01/2017.
 */

public class MessageDbOpener extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Messages.db";

    public MessageDbOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessageDbOperation.SQL_CREATE_ENTRIES);
        Log.v("test",MessageDbOperation.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MessageDbOperation.SQL_DELETE_ENTRIES);
        this.onCreate(db);
    }
}
