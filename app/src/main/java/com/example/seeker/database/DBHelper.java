package com.example.seeker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.seeker.models.BatteryLog;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BatteryLogger.db";
    private static final int DATABASE_VERSION = 1;

    // Table and columns
    private static final String TABLE_LOGS = "battery_logs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PERCENT = "percentage";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_CHARGING = "is_charging";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LOGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PERCENT + " INTEGER,"
                + COLUMN_TIMESTAMP + " LONG,"
                + COLUMN_CHARGING + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        onCreate(db);
    }

    public void addLog(int percentage, long timestamp, boolean isCharging) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERCENT, percentage);
        values.put(COLUMN_TIMESTAMP, timestamp);
        values.put(COLUMN_CHARGING, isCharging ? 1 : 0);
        db.insert(TABLE_LOGS, null, values);
        db.close();
    }

    public List<BatteryLog> getAllLogs() {
        List<BatteryLog> logs = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LOGS + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                BatteryLog log = new BatteryLog(
                        cursor.getInt(1), // percentage
                        cursor.getLong(2), // timestamp
                        cursor.getInt(3) == 1 // isCharging
                );
                logs.add(log);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return logs;
    }

    public void deleteOldLogs(long cutoffTimestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGS,
                COLUMN_TIMESTAMP + " < ?",
                new String[]{String.valueOf(cutoffTimestamp)});
        db.close();
    }
}