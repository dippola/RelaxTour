package com.dippola.relaxtour.service;

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
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.notification.DefaultNotification;
import com.dippola.relaxtour.notification.NotificationActionService;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.timer.Timer2;
import com.dippola.relaxtour.timer.TimerDialog;

public class TimerService extends Service {
    public static boolean isCount;
    public static boolean isFinish;

    public static CountDownTimer cdt = null;

    public static final String CHANNEL_ID = "audio";

    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_CLOSE = "actionclose";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isCount = true;
        MainActivity.maincount.setVisibility(View.VISIBLE);
        MainActivity.cancel.setVisibility(View.VISIBLE);
        Log.d("TimerService>>>", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Timer2.et_timer.getText().toString().length() > 0) {
            Timer2.myProgress = 0;

            try {
                cdt.cancel();

            } catch (Exception e) {

            }

            String timeInterval = Timer2.et_timer.getText().toString();
            Timer2.progress = 1;
            Timer2.endTime = Integer.parseInt(timeInterval);

            cdt = new CountDownTimer(Timer2.endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(Timer2.progress, Timer2.endTime);
                    Timer2.progress = Timer2.progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;
                    String newtimeset = "";

                    if (newtime.equals("0:0:0")) {
                        newtimeset = "0:0:0";
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        newtimeset = "0" + hours + ":0" + minutes + ":0" + seconds;
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        newtimeset = "0" + hours + ":0" + minutes + ":" + seconds;
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        newtimeset = "0" + hours + ":" + minutes + ":0" + seconds;
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        newtimeset = hours + ":0" + minutes + ":0" + seconds;
                    } else if (String.valueOf(hours).length() == 1) {
                        newtimeset = "0" + hours + ":" + minutes + ":" + seconds;
                    } else if (String.valueOf(minutes).length() == 1) {
                        newtimeset = hours + ":0" + minutes + ":" + seconds;
                    } else if (String.valueOf(seconds).length() == 1) {
                        newtimeset = hours + ":" + minutes + ":0" + seconds;
                    } else {
                        newtimeset = hours + ":" + minutes + ":" + seconds;
                    }

                    Timer2.tv_time.setText(newtimeset);
                    MainActivity.maincount.setText(newtimeset);

                    startForegroundService(getApplicationContext(), newtimeset);

                    isCount = true;
                    isFinish = false;
                }

                @Override
                public void onFinish() {
                    isCount = false;
                    isFinish = true;
                    setProgress(Timer2.progress, Timer2.endTime);
                    cdt.cancel();
                    stopService(new Intent(getApplicationContext(), TimerService.class));
                    NotificationService.closeNotification(getApplicationContext());

                    //stop audio
                    if (NotificationService.isPlaying) {
                        stopService(new Intent(getApplicationContext(), NotificationService.class));
                    }

                    MainActivity.maincount.setText("");
                    MainActivity.maincount.setVisibility(View.GONE);
                    MainActivity.cancel.setVisibility(View.GONE);
                }
            };
            cdt.start();
        } else {
            isCount = false;
            isFinish = false;
        }
    }

    @Override
    public void onDestroy() {
        TimerDialog.viewPager.setCurrentItem(0);
        isCount = false;
        cdt.cancel();
        Log.d("TimerService>>>", "onDestroy, " + NotificationService.isPlaying);

        MainActivity.maincount.setText("");
        MainActivity.maincount.setVisibility(View.GONE);
        MainActivity.cancel.setVisibility(View.GONE);


        if (NotificationService.isPlaying) {
            DefaultNotification.defauleNotification(getApplicationContext());
        }

        super.onDestroy();
    }

    public void setProgress(int startTime, int endTime) {
        Timer2.progressBarView.setMax(endTime);
        Timer2.progressBarView.setSecondaryProgress(endTime);
        Timer2.progressBarView.setProgress(startTime);
    }

    public void startForegroundService(Context context, String time) {
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
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0, intentClose,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    0);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "RRRain", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                notification = new NotificationCompat.Builder(context);
            }
            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.bottom_sheet_play);
            notification.setContentTitle("meditation title " + time);//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);

            notification.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
            notification.addAction(R.drawable.notification_close, "close", pendingIntentClose);

            notification.setContentIntent(pIntent);
            notification.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1).
                    setMediaSession(mediaSessionCompat.getSessionToken()));
            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW
//                    .build();

//            notificationManagerCompat.notify(1, notification);
            startForeground(1, notification.build());
//            stopForeground(true);
//            stopSelf();
//            Log.d(">>>", "open foreground");
        }
    }
}
