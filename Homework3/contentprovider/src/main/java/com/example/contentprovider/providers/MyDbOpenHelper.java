package com.example.contentprovider.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contentprovider.data.MyContract;

public class MyDbOpenHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "sqllite.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public MyDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + MyContract.WaitlistEntry.TABLE_NAME + " (" +
                MyContract.WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MyContract.WaitlistEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MyContract.WaitlistEntry.COLUMN_PHONE + " TEXT NOT NULL, " +
                MyContract.WaitlistEntry.COLUMN_PEOPLE + " INTEGER NOT NULL" + " )";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyContract.WaitlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

