package com.startboardland.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jialiang on 3/16/15.
 */
public class SegmentDAO {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {MySQLiteHelper.SEGMENT_ID,
            MySQLiteHelper.STEP_COUNT};

    public SegmentDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createSegment(Segment segment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.SEGMENT_ID, segment.getSegment_id());
        values.put(MySQLiteHelper.STEP_COUNT, segment.getStepCount());
        database.insert(MySQLiteHelper.TABLE_MY_SEGMENT, null,
                values);
    }

}
