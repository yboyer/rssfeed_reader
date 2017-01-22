package fr.unicaen.info.dnr.rssapp.sqlite.rssitem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lenaic on 11/01/2017.
 */
public class RSSItemDbOpener extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RSSItems.db";

    public RSSItemDbOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RSSItemDbOperation.SQL_CREATE_ENTRIES);
        Log.v("test", RSSItemDbOperation.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RSSItemDbOperation.SQL_DELETE_ENTRIES);
        this.onCreate(db);
    }
}
