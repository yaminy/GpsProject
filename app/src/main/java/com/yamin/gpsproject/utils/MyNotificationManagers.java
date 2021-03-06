package com.yamin.gpsproject.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.yamin.gpsproject.R;

import java.util.Random;



public class MyNotificationManagers {

    public static String channelId;
    private static final int GLOBAL_NOTIFICATION_ID = 920;

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the Class Parameter.
     *
     * @param title      The title of the notification.
     * @param message The message of user exit or enter detail.
     * @param className           The class witch click on notification trigger
     * @param context           The context of the app
     */
    public static void showNotification(String title, String message, Class className, Context context) {
        Intent notifyIntent = new Intent(context, className);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notification.setDefaults(Notification.DEFAULT_SOUND) ;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1", "Pastille", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100});
            assert notificationManager != null;
            notification.setChannelId("1");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        final Random rand = new Random();
        int diceRoll = rand.nextInt(1000) + 1;
        if (notificationManager != null)
        notificationManager.notify(diceRoll, notification.build());
    }



    public static void showNotificationById(int notificationId, String groupId, String title, String message, Class className, Context context) {
        Intent notifyIntent = new Intent(context, className);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setGroupSummary(true)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setGroup(groupId)
                .setGroupSummary(true)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "notify_002pastille", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }

        final Random rand = new Random();
        int diceRoll = rand.nextInt(1000) + 1;
        notificationManager.notify(notificationId, notification);
    }

}
