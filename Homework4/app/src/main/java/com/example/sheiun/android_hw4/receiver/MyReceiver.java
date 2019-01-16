package com.example.sheiun.android_hw4.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.example.sheiun.android_hw4.R;
import com.example.sheiun.android_hw4.utilities.Alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bData = intent.getExtras();
        notificaton(context, bData.get("msg").toString(), bData.get("id").toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notificaton(Context context, String text, String id) {
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_add);
        mBuilder.setContentText("快要上課囉!");
        mBuilder.setContentTitle(text);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Integer.parseInt(id), mBuilder.build());

        Calendar cal = Calendar.getInstance();
        System.out.println(String.format("Notification %s %s", id, cal.getTime()));

        String weekday = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
        String time = String.format("%1$02d:%2$02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

        Alarm.sceduleAlarm(context, weekday, time, text, id, false);
    }
}
