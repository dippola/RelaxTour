package com.dippola.relaxtour.service;

import static com.dippola.relaxtour.notification.NotifiControllID.MAIN_ID;
import static com.dippola.relaxtour.notification.NotifiControllID.TIMER_ID;

import android.app.Notification;
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

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.notification.DefaultNotification;
import com.dippola.relaxtour.notification.NotificationActionService;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.notification.TimerNotificationActionService;
import com.dippola.relaxtour.timer.Timer2;
import com.dippola.relaxtour.timer.TimerDialog;

public class TimerService extends Service {
    public static boolean isCount;
    public static boolean isFinish;

    public static CountDownTimer cdt = null;

    public static final String CHANNEL_ID = "channel_main";

    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_CLOSE = "actionclose";

    public static int timerHour;
    public static int timerMin;
    public static String setNewTime;
    public static String et_timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isCount = true;
        if (MainActivity.maincount != null) {
            MainActivity.maincount.setVisibility(View.VISIBLE);
            MainActivity.cancel.setVisibility(View.VISIBLE);
            MainActivity.mainTitle.setVisibility(View.GONE);
        }
        Log.d("TimerService>>>", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Timer2.tv_time != null && et_timer.length() > 0) {
            startForegroundService(getApplicationContext(), "timer start");
            Timer2.myProgress = 0;

            try {
                cdt.cancel();

            } catch (Exception e) {

            }

            if (Timer2.tv_time != null) {
                String timeInterval = et_timer.toString();
                Timer2.progress = 1;
                Timer2.endTime = Integer.parseInt(timeInterval);
            }

            cdt = new CountDownTimer(Timer2.endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;
//                    setNewTime = "";

                    if (newtime.equals("0:0:0")) {
                        setNewTime = "0:0:0";
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        setNewTime = "0" + hours + ":0" + minutes + ":0" + seconds;
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        setNewTime = "0" + hours + ":0" + minutes + ":" + seconds;
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        setNewTime = "0" + hours + ":" + minutes + ":0" + seconds;
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        setNewTime = hours + ":0" + minutes + ":0" + seconds;
                    } else if (String.valueOf(hours).length() == 1) {
                        setNewTime = "0" + hours + ":" + minutes + ":" + seconds;
                    } else if (String.valueOf(minutes).length() == 1) {
                        setNewTime = hours + ":0" + minutes + ":" + seconds;
                    } else if (String.valueOf(seconds).length() == 1) {
                        setNewTime = hours + ":" + minutes + ":0" + seconds;
                    } else {
                        setNewTime = hours + ":" + minutes + ":" + seconds;
                    }

                    if (Timer2.tv_time != null) {
                        setProgress(Timer2.progress, Timer2.endTime);
                        Timer2.tv_time.setText(setNewTime);
                        Timer2.progress = Timer2.progress + 1;
                    }
                    if (MainActivity.maincount != null) {
                        MainActivity.maincount.setText(setNewTime);
                    }

                    updateCountNotification(getApplicationContext(), setNewTime);

                    isCount = true;
                    isFinish = false;
                }

                @Override
                public void onFinish() {
                    isCount = false;
                    isFinish = true;
                    if (Timer2.tv_time != null) {
                        setProgress(Timer2.progress, Timer2.endTime);
                    }
                    cdt.cancel();
                    stopService(new Intent(getApplicationContext(), TimerService.class));
                    NotificationService.closeNotification(getApplicationContext());

                    //stop audio
                    if (NotificationService.isPlaying) {
                        stopService(new Intent(getApplicationContext(), NotificationService.class));
                    }

                    if (MainActivity.maincount != null) {
                        MainActivity.maincount.setText("");
                        MainActivity.maincount.setVisibility(View.GONE);
                        MainActivity.cancel.setVisibility(View.GONE);
                        MainActivity.mainTitle.setVisibility(View.VISIBLE);
                    }
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
        MainActivity.mainTitle.setVisibility(View.VISIBLE);


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

            Intent intentPlay = new Intent(context, TimerNotificationActionService.class)
                    .setAction(ACTION_PLAY);
            Intent intentClose = new Intent(context, TimerNotificationActionService.class)
                    .setAction(ACTION_CLOSE);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay,
                    PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0, intentClose,
                    PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "RelaxTour Timer", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                notification = new NotificationCompat.Builder(context);
            }
            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.bottom_sheet_play);
            notification.setContentTitle("Relax Tour timer " + time);//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);

//            notification.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
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

    private void updateCountNotification(Context context, String count) {
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        Intent intentPlay = new Intent(context, TimerNotificationActionService.class)
                .setAction(ACTION_PLAY);
        Intent intentClose = new Intent(context, TimerNotificationActionService.class)
                .setAction(ACTION_CLOSE);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay,
                PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0, intentClose,
                PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "RelaxTour Timer", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getActiveNotifications();
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setSilent(true);
        builder.setSmallIcon(R.drawable.tabicon_chakra);
        builder.setContentTitle("Relax Tour " + count);//.setContentText(track.getName())
        builder.setLargeIcon(icon);
        builder.setOnlyAlertOnce(true);//show notification for only first time
        builder.setShowWhen(false);

//            notification.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
        if (MainActivity.bottomSheetPlayList.size() != 0) {
            initMP(context);
            if (AudioController.playingListindex0_1(MainActivity.bottomSheetPlayList.get(0).getPnp()).isPlaying() || AudioController.playingListindex0_2(MainActivity.bottomSheetPlayList.get(0).getPnp()).isPlaying()) {
                builder.addAction(R.drawable.bottom_pause, "Play", pendingIntentPlay);
            } else {
                builder.addAction(R.drawable.bottom_sheet_play, "Play", pendingIntentPlay);
            }
        } else {
            builder.addAction(R.drawable.bottom_sheet_play, "Play", pendingIntentPlay);
        }
        builder.addAction(R.drawable.notification_close, "close", pendingIntentClose);

        builder.setContentIntent(pIntent);
        builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1).
                setMediaSession(mediaSessionCompat.getSessionToken()));
        mediaSessionCompat.release();
        builder.setPriority(NotificationCompat.PRIORITY_LOW);
        notificationManager.notify(MAIN_ID, builder.build());
    }

    private void initMP(Context context) {
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            MPList.initalMP(MainActivity.bottomSheetPlayList.get(i).getPnp(), context, MainActivity.bottomSheetPlayList.get(i).getSeek());
        }
    }
}
