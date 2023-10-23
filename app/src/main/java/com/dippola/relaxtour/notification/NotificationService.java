package com.dippola.relaxtour.notification;

import static com.dippola.relaxtour.notification.NotifiControllID.MAIN_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.ArrayList;

public class NotificationService extends Service {
    public static final String CHANNEL_ID = "channel_main";

    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_CLOSE = "actionclose";

    public static boolean isPlaying;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isPlaying = true;
        startForgroundServiceMain(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isPlaying = false;
        //여기서 재생중인곡 있으면 종료하기
        if (MainActivity.bottomSheetPlayList.size() != 0 && AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0), getApplicationContext())) {//재생중
            MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
            ArrayList<PageItem> page = new ArrayList<>();
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                page.add(MainActivity.bottomSheetPlayList.get(i));
                if (i == MainActivity.bottomSheetPlayList.size() - 1) {
                    AudioController.stopPlayingList(page);
                }
            }
        }
        super.onDestroy();
    }

    private void startForgroundServiceMain(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
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
//            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.notification_small_icon);
            notification.setContentTitle("Relax Tour");//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);
            notification.setOngoing(true);

            notification.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
            notification.addAction(R.drawable.notification_close, "close", pendingIntentClose);

            notification.setContentIntent(pIntent);
            notification.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1).
                    setMediaSession(mediaSessionCompat.getSessionToken()));
            mediaSessionCompat.release();
            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW
//                    .build();

//            notificationManagerCompat.notify(1, notification);
            startForeground(MAIN_ID, notification.build());
//            stopForeground(true);
//            stopSelf();
//            Log.d(">>>", "open foreground");
        }
    }

    public static void closeNotification(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
//        notificationManagerCompat.cancelAll();
        notificationManagerCompat.cancel(MAIN_ID);
    }
}
