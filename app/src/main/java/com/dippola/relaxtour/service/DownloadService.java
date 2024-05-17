package com.dippola.relaxtour.service;

import static com.dippola.relaxtour.notification.NotifiControllID.DOWNLOAD_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.notification.SuccessDownloadNotification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadService extends Service {
    public static boolean isDownloadOpen;

    public static final String CHANNEL_ID = "downloading";

//    public static ArrayList<String> downloadList = new ArrayList<>();
    public static ArrayList<DownloadItem> downloadList = new ArrayList<>();

    private static NotificationCompat.Builder notification;

    public DownloadService() {

    }

//    public DownloadService(ProgressBar progressBar, ImageView button, ImageView download) {
//        this.progressBar = progressBar;
//        this.button = button;
//        this.download = download;
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isDownloadOpen = true;
        openDownloadNotification(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    private void openDownloadNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);


            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Downloading track", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                notification = new NotificationCompat.Builder(context);
            }
            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.downloading_icon);
            notification.setContentTitle("Downloading..");//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);
            notification.setAutoCancel(true);

            notification.setContentIntent(pIntent);
            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW

            startForeground(DOWNLOAD_ID, notification.build());
            Log.d("DownloadService>>>", "ok");

//            setOnClickDownload(context, progressBar, button, download, pnp, page);
        }
    }

    public static void setViewDownloading(DownloadItem downloadItem) {
        downloadItem.getLoading().setVisibility(View.VISIBLE);
        downloadItem.getImg().setEnabled(false);
        downloadItem.getDownload().setEnabled(false);
    }

    public static void setOnClickDownload(Context context) {
        String tid = downloadList.get(0).getTid();
        String fileName = "audio" + tid + ".mp3";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        File localFile;
        try {
            localFile = File.createTempFile("audio", "0");
            reference.child("audios").child(fileName).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    File from = new File(context.getApplicationInfo().dataDir + "/cache", localFile.getName());
                    File to = new File(context.getApplicationInfo().dataDir + "/cache", fileName);
                    if (from.exists()) {
                        from.renameTo(to);
                    }
                    resetMediaPlayer(tid, context);
                    downloadList.get(0).getImg().setEnabled(true);
                    downloadList.get(0).getDownload().setVisibility(View.GONE);
                    downloadList.get(0).getLoading().setVisibility(View.GONE);
                    downloadList.get(0).getSeekBar().setEnabled(true);
                    downloadList.get(0).getSeekBar().setProgressDrawable(context.getResources().getDrawable(R.drawable.seekbar_in_page_enable));
                    downloadList.get(0).getSeekBar().setThumb(context.getResources().getDrawable(R.drawable.seekbar_in_page_thumb_enable));
//                    stopSelf();

                    downloadList.remove(0);
                    if (downloadList.size() == 0 && isDownloadOpen) {
                        Intent intent = new Intent(context, DownloadService.class);
                        context.stopService(intent);
                        SuccessDownloadNotification.successDownloadNotification(context);
                    } else {
                        setOnClickDownload(context);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DownloadService>>>", "download failed: " + e.toString());
                    downloadList.get(0).getDownload().setEnabled(true);
                    downloadList.get(0).getLoading().setVisibility(View.GONE);
                    Toast.makeText(context, "failed download. please try again. (" + e.toString() + ")", Toast.LENGTH_LONG).show();
                    downloadList.remove(0);
                    if (downloadList.size() == 0 && isDownloadOpen) {
                        Intent intent = new Intent(context, DownloadService.class);
                        context.stopService(intent);
                        SuccessDownloadNotification.successDownloadNotification(context);
                    } else {
                        setOnClickDownload(context);
                    }
                    SuccessDownloadNotification.failedDownloadNotification(context, e.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetMediaPlayer(String tid, Context context) {
        MPList.initalMP(tid, context, 3);
    }

    @Override
    public void onDestroy() {
        Log.d("DownloadService>>>", "onDestroy");
        isDownloadOpen = false;
        super.onDestroy();
    }
}
