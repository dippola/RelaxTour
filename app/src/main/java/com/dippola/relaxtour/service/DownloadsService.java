package com.dippola.relaxtour.service;

import static com.dippola.relaxtour.notification.NotifiControllID.DOWNLOADS_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.dialog.AskDownloadsDialog;
import com.dippola.relaxtour.notification.SuccessDownloadNotification;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.setting.StorageManageAdapter;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadsService extends Service {
    public static final String CHANNEL_ID = "channel_downloads";

    private static FirebaseStorage storage;
    private static StorageReference reference;

    public DownloadsService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        openDownloadsNotification(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    private void openDownloadsNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Downloads", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
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

            startForeground(DOWNLOADS_ID, notification.build());

//            setOnClickDownload(context, progressBar, button, download, pnp, page);

//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    stopSelf();
//                    SuccessDownloadNotification.successDownloadNotification(context);
//                }
//            }, 2000);
        }
    }

    public static void downloads(Context context, ProgressBar progressBar, TextView count, ArrayList<String> pnps, TextView title, ProgressBar button2, Button button3) {
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        final int[] counter = {0};

        progressBar.setVisibility(View.VISIBLE);
        count.setVisibility(View.VISIBLE);
        progressBar.setMax(pnps.size());
        count.setText("0 / " + pnps.size());

        progressBar.setProgress(counter[0]);
        for (int i = 0; i < pnps.size(); i++) {
            String pnp = pnps.get(i);
            String ptop = pnps.get(i).substring(0, 1) + "to" + pnps.get(i).substring(2, 3);
            int page = Integer.parseInt(pnps.get(i).substring(0, 1));
            String fileName = "audio" + ptop + ".mp3";
            try {
                File localFile = File.createTempFile("downloading", "0");
                Log.d("DownloadsService>>>", "check: " + localFile.getName());
                reference.child("audios").child(fileName).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        resetPage(page);
                        File from = new File(context.getApplicationInfo().dataDir + "/cache", localFile.getName());
                        File to = new File(context.getApplicationInfo().dataDir + "/cache", fileName);
                        if (from.exists()) {
                            from.renameTo(to);
                        }
                        counter[0] += 1;
                        count.setText(counter[0] + " / " + pnps.size());
                        progressBar.setProgress(counter[0]);
                        if (counter[0] == pnps.size()) {
                            checkSuccess(context, pnps, title, button2, button3);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DownloadsService>>>", "5");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("DownloadsService>>>", "finished for");
    }

    private static void checkSuccess(Context context, ArrayList<String> pnps, TextView title, ProgressBar button2, Button button3) {
        boolean isFail = false;
        ArrayList<String> failList = new ArrayList<>();
        for (int i = 0; i < pnps.size(); i++) {
            String ptop = pnps.get(i).substring(0, 1) + "to" + pnps.get(i).substring(2, 3);
            File file = new File(context.getApplicationInfo().dataDir + "/cache/audio" + ptop + ".mp3");
            if (!file.exists()) {
                isFail = true;
                failList.add(pnps.get(i));
            }
            if (i == pnps.size() - 1) {
                if (isFail) {
                    //fail download
                    title.setText("failed download\n" + setTextWhenFailed(failList));
                    button2.setVisibility(View.GONE);
                    button3.setVisibility(View.VISIBLE);
                } else {
//                    MPList.initalMP(pnp, context, 3);
                    Intent intent = new Intent(context, DownloadsService.class);
                    context.stopService(intent);
                    SuccessDownloadNotification.successDownloadNotification(context);
                    AskDownloadsDialog.alertDialog.dismiss();
                }
            }
        }
    }

    private static void resetPage(int page) {
        if (page == 5 && ChakraPage.arrayList.size() != 0) {
            ChakraPage.adapter.notifyDataSetChanged();
        } else if (page == 6 && MantraPage.arrayList.size() != 0) {
            MantraPage.adapter.notifyDataSetChanged();
        } else if (page == 7 && HzPage.arrayList.size() != 0) {
            HzPage.adapter.notifyDataSetChanged();
        }
    }

    private static String setTextWhenFailed(ArrayList<String> failList) {
        String failedText = "";
        for (int i = 0; i < failList.size(); i++) {
            String page = StorageManageAdapter.getPageName(Integer.parseInt(failList.get(i).substring(0, 1)));
            String name = MainActivity.databaseHandler.getTrackName(Integer.parseInt(failList.get(i).substring(0, 1)), Integer.parseInt(failList.get(i).substring(2, 3)));
            String print = page + " - " + name + "\n";
            failedText += print;
        }
        return failedText;
    }
}
