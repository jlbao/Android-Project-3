package com.startboardland.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jialiang on 3/16/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_MY_SEGMENT = "MySegment";
    public static final String SEGMENT_ID = "_id";
    public static final String STEP_COUNT = "step";

    private static final String DATABASE_NAME = "test";
    private static final int DATABASE_VERSION = 1;

//    // Database creation sql statement
//    private static final String DATABASE_CREATE = "create table "
//            + TABLE_MY_SEGMENT + "(" + SEGMENT_ID
//            + " integer primary key, " + STEP_COUNT
//            + " integer not null);";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table MySegment (_id integer, dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, step integer not null, PRIMARY KEY (_id, dt));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_SEGMENT);
        onCreate(db);
    }

}
