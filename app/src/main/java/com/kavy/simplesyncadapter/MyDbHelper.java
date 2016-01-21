package com.kavy.simplesyncadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pedro on 21/01/16.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private static final String CREATE_DB = "CREATE TABLE " + Contract.TABLE_DAYS_NAME + " ( "
            + Contract.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Contract.COL_DAY + " TEXT NOT NULL, "
            + Contract.COL_HOUR + " TEXT NOT NULL" + ");";

    private static final String DESTROY_DB = "DROP TABLE IF EXISTS " + Contract.TABLE_DAYS_NAME;

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // change the version of the db when structure changes, usually destroy and create again
        db.execSQL(DESTROY_DB);
        onCreate(db);
    }
}
