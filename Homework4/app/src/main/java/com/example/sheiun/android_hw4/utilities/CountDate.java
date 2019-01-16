package com.example.sheiun.android_hw4.utilities;

import java.util.Calendar;

public class CountDate {
    public static Calendar getNearestTimeMinus5(String weekday, String Time, boolean first) {
        int week = weekDayToWeek(weekday);
        int hour = Integer.parseInt(Time.split(":")[0]);
        int min = Integer.parseInt(Time.split(":")[1]);
        Calendar cal = Calendar.getInstance();
        int now_week = cal.get(Calendar.DAY_OF_WEEK);
        if (!first) {
            cal.add(Calendar.DATE, 7);
            min += 5;
        } else if (week == now_week && hour < cal.get(Calendar.HOUR_OF_DAY) || hour == cal.get(Calendar.HOUR_OF_DAY) && min < cal.get(Calendar.MINUTE) + 5) {
            cal.add(Calendar.DATE, 7);
        } else {
            cal.add(Calendar.DATE, (week - cal.get(Calendar.DAY_OF_WEEK) + 7) % 7);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.MINUTE, -5);
        return cal;
    }

    public static int weekDayToWeek(String weekday) {
        int week;
        switch (weekday) {
            case "Sunday":
                week = 1;
                break;
            case "Monday":
                week = 2;
                break;
            case "Tuesday":
                week = 3;
                break;
            case "Wednesday":
                week = 4;
                break;
            case "Thursday":
                week = 5;
                break;
            case "Friday":
                week = 6;
                break;
            case "Saturday":
                week = 7;
                break;
            default:
                week = -1;
                System.out.println(String.format("%s Out of Range", weekday));
        }
        return week;
    }
}
