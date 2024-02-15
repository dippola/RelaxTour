package com.dippola.relaxtour.community.main.write;

import static com.dippola.relaxtour.notification.NotifiControllID.FINISHED_POST_ID;
import static com.dippola.relaxtour.notification.NotifiControllID.POST_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.CommunityMain;
import com.dippola.relaxtour.community.main.notification.Notification;

public class FinishedUploadNotification {
    public static final String CHANNEL_ID = "post completed";

    public static void finishedUploadNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

            Intent intent = new Intent(context, CommunityMain.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

//            NotificationCompat.Builder notification;
//            if (Build.VERSION.SDK_INT >= 26) {
//                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Post Completed", NotificationManager.IMPORTANCE_DEFAULT);
//                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
//                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
//            } else {
//                notification = new NotificationCompat.Builder(context);
//            }
//            notification.setSilent(true);
//            notification.setSmallIcon(R.drawable.success_download_icon);
//            notification.setContentTitle("Post Completed");//.setContentText(track.getName())
//            notification.setLargeIcon(icon);
//            notification.setOnlyAlertOnce(true);//show notification for only first time
//            notification.setShowWhen(false);
//
//            notification.setContentIntent(pIntent);
//            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context, CHANNEL_ID).
                            setSilent(true).
                            setSmallIcon(R.drawable.success_download_icon).
                            setContentTitle("Post Completed").
                            setLargeIcon(icon).
                            setOnlyAlertOnce(true).
                            setAutoCancel(true).
                            setShowWhen(false).
                            setContentIntent(pIntent).
                            setPriority(NotificationCompat.PRIORITY_LOW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Post Completed", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(FINISHED_POST_ID, notificationBuilder.build());
        }
    }
}
