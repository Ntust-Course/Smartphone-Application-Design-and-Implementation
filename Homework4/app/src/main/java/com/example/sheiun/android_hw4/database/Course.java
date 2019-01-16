package com.example.sheiun.android_hw4.database;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Course {
    private long id;
    private String title; // :3 就是標題
    private String week; // 就是週
    private Time time; // 就是時間
    private boolean enable; // 就是有沒有啟用

    public Course(String title, String week, String time) {
        this.title = title;
        this.week = week;
        DateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            this.time = new Time(formatter.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.enable = true;
    }

    public Course(String title, String week, String time, String ienable) {
        this.title = title;
        this.week = week;
        DateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            this.time = new Time(formatter.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.enable = Integer.parseInt(ienable) > 0 ? true : false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getWeek() {
        new SimpleDateFormat("w").format(new java.util.Date());

        return week;
    }

    public Time getTime() {
        return time;
    }

    public boolean getEnabled() {
        return enable;
    }

    public void setEnabled(Boolean c) {
        this.enable = c;
    }

}
