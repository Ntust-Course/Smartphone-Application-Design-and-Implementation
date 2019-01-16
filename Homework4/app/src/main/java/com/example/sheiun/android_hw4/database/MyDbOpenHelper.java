package com.example.sheiun.android_hw4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sheiun.android_hw4.utilities.Alarm;

import java.util.ArrayList;
import java.util.List;

public class MyDbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "hw4.db";
    private static final int DATABASE_VERSION = 6;
    public static final String TABLE_NAME = "courseList";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_WEEK = "week";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ENABLED = "enabled";

    private Context context;

    public MyDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_TABLE = "CREATE TABLE " + this.TABLE_NAME + " (" +
                this.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                this.COLUMN_TITLE + " TEXT NOT NULL, " +
                this.COLUMN_WEEK + " TEXT NOT NULL, " +
                this.COLUMN_TIME + " TIME NOT NULL," +
                this.COLUMN_ENABLED + " INTEGER NOT NULL" + " )";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
        Log.d("[DB]", "Create Database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + this.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, course.getTitle());
        values.put(COLUMN_WEEK, course.getWeek());
        values.put(COLUMN_TIME, course.getTime().toString());
        values.put(COLUMN_ENABLED, course.getEnabled() ? 1 : 0);
        // insert
        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        // when adding new course we'll run query
        //Alarm.sceduleAlarm(context, course.getWeek(), course.getTime().toString(), course.getTitle(), String.valueOf(id));
    }

    public void updateCourse(long id, boolean c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENABLED, c ? 1 : 0);
        db.update(TABLE_NAME, values, COLUMN_ID + "=" + id, null);

        if (c) {
            Course result = getCourse(id);
            Alarm.sceduleAlarm(context, result.getWeek(), result.getTime().toString(), result.getTitle(), String.valueOf(id), true);
        } else {
            Alarm.cancelAlarm(context, String.valueOf(id));
        }
    }

    public Course getCourse(Long id) {
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Course course = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String weekday = cursor.getString(cursor.getColumnIndex(COLUMN_WEEK));
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                String enable = cursor.getString(cursor.getColumnIndex(COLUMN_ENABLED));
                course = new Course(title, weekday, time, enable);
            }
        }
        return course;
    }

    public List<Course> getCourseList() {
        String query = "SELECT  * FROM " + TABLE_NAME;

        List<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Course course;

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String weekday = cursor.getString(cursor.getColumnIndex(COLUMN_WEEK));
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                String enable = cursor.getString(cursor.getColumnIndex(COLUMN_ENABLED));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                course = new Course(title, weekday, time, enable);
                course.setId(id);
                courseList.add(course);

                if (Integer.parseInt(enable) > 0) {
                    Alarm.sceduleAlarm(context, weekday, time, title, String.valueOf(id), true);
                } else {
                    // 這行保險用 (避免有些人是寫好 sql 才丟給別人)
                    Alarm.cancelAlarm(context, String.valueOf(id));
                }
            } while (cursor.moveToNext());
        }

        return courseList;
    }
}
