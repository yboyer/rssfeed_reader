package fr.unicaen.info.dnr.rssapp.sqlite.rssfeed;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class RSSFeedDbOpener extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RSSFeed.db";

    public RSSFeedDbOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RSSFeedDbOperation.SQL_CREATE_ENTRIES);
        Log.v("test", RSSFeedDbOperation.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RSSFeedDbOperation.SQL_DELETE_ENTRIES);
        this.onCreate(db);
    }
}
