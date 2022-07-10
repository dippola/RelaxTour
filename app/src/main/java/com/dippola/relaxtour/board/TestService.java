package com.dippola.relaxtour.board;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.item.DownloadItem;

import java.util.ArrayList;

public class TestService extends Service {

    String channel_id;
    public static boolean isTestOpen;
    public static boolean isDownloading;
    public static ArrayList<DownloadItem> downloadList = new ArrayList<>();

    public TestService() {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        channel_id = intent.getExtras().getString("ci");
        isTestOpen = true;
        openNotification(getApplicationContext(), channel_id);
        return super.onStartCommand(intent, flags, startId);
    }

    private void openNotification(Context context, String channel_id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Log.d("TestService>>>", "channel_id: " + channel_id);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    0);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(channel_id, "RRRain", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, channel_id);
            } else {
                notification = new NotificationCompat.Builder(context);
            }
            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.download);
            notification.setContentTitle("downloading..");//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);

            notification.setContentIntent(pIntent);
            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW

            startForeground(2, notification.build());
//            Log.d("DownloadService>>>", "ok");

//            setOnClickDownload(context, progressBar, button, download, pnp, page);
        }
    }

    public static void fiveSecond(Context context, String ci) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                
            }
        }, 4000);
    }

    @Override
    public void onDestroy() {
        isTestOpen = false;
        super.onDestroy();
    }

    public static class MyThread extends Thread {
        Context context;
        public MyThread(Context context) {
            this.context = context;
        }
        @Override
        public void run() {
            while (!isDownloading) {
                Intent intent = new Intent(context, TestService.class);
                context.stopService(intent);
                TestService2.testService2Notification(context);
            }
        }
    }
}
