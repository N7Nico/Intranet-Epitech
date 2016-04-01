package com.nico_11_riv.intranetepitech;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;

/**
 * Created by Jimmy on 01/04/2016.
 */
public class Notifications {

    private Context context;
    private String title;
    private String msg;
    private String content;
    private int delay;

    public Notifications(Context context, String title, String msg, String content, int delay) {
        this.context = context;
        this.title = title;
        this.msg = msg;
        this.content = content;
        this.delay = delay * 1000;
    }

    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentInfo(msg);
        builder.setContentText(content);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setSmallIcon(R.drawable.logo);
        return builder.build();
    }

    public void initNotification() {
        scheduleNotification(getNotification());
    }
}
