package com.example.sheiun.android_hw4.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.sheiun.android_hw4.receiver.MyReceiver;

import java.util.Calendar;

public class Alarm {
    public static void sceduleAlarm(Context context, String weekday, String time, String text, String id, boolean first) {
        Calendar cal = CountDate.getNearestTimeMinus5(weekday, time, first);

        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("id", id);
        intent.putExtra("msg", text);

        PendingIntent pi = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        System.out.println(String.format("Start %d", Integer.parseInt(id)));
        System.out.println(cal.getTime());
    }

    public static void cancelAlarm(Context context, String id) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
        System.out.println(String.format("Cancel %d", Integer.parseInt(id)));
    }
}
