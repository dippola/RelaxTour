package com.dippola.relaxtour.notification;

import static com.dippola.relaxtour.notification.NotifiControllID.DEFAULT_ID;
import static com.dippola.relaxtour.notification.NotifiControllID.MAIN_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;

public class DefaultNotification {
    public static final String CHANNEL_ID = "channel_main";

    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_CLOSE = "actionclose";

    public static void defauleNotification (Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_PLAY);
            Intent intentClose = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_CLOSE);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay,
                    PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0, intentClose,
                    PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "RelaxTour", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                notification = new NotificationCompat.Builder(context);
            }
            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.bottom_sheet_play);
            notification.setContentTitle("Relax Tour default");//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);

//            if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0).getPnp())) {
//                notification.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
//            } else {
//                notification.addAction(R.drawable.bottom_sheet_play, "Play", pendingIntentPlay);
//            }
//            notification.addAction(R.drawable.bottom_sheet_play, "Play", pendingIntentPlay);
            if (MainActivity.bottomSheetPlayList.size() != 0) {
                initMP(context);
                if (AudioController.playingListindex0_1(MainActivity.bottomSheetPlayList.get(0).getPnp()).isPlaying() || AudioController.playingListindex0_2(MainActivity.bottomSheetPlayList.get(0).getPnp()).isPlaying()) {
                    notification.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
                } else {
                    notification.addAction(R.drawable.bottom_sheet_play, "Play", pendingIntentPlay);
                }
            } else {
                notification.addAction(R.drawable.bottom_sheet_play, "Play", pendingIntentPlay);
            }
//            notification.addAction(R.drawable.bottom_play, "Play", pendingIntentPlay);
            notification.addAction(R.drawable.notification_close, "close", pendingIntentClose);

            notification.setContentIntent(pIntent);
            notification.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1).
                    setMediaSession(mediaSessionCompat.getSessionToken()));
            mediaSessionCompat.release();
            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW

            notificationManagerCompat.notify(MAIN_ID, notification.build());
        }
    }

    public static void closeNotification(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.cancelAll();
    }

    private static void initMP(Context context) {
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            MPList.initalMP(MainActivity.bottomSheetPlayList.get(i).getPnp(), context, MainActivity.bottomSheetPlayList.get(i).getSeek());
        }
    }
}
