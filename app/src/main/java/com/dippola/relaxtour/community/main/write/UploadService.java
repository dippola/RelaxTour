package com.dippola.relaxtour.community.main.write;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.CommunityMain;
import com.dippola.relaxtour.retrofit.model.MainModelDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UploadService extends Service {
    public static final String CHANNEL_ID = "upload";
    public static List<Uri> urilist = new ArrayList<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        openUploadNotification(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }
    private void openUploadNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.main_head);

            Intent intent = new Intent(context, CommunityWrite.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    0);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Upload", NotificationManager.IMPORTANCE_DEFAULT);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                notification = new NotificationCompat.Builder(context);
            }
            notification.setSilent(true);
            notification.setSmallIcon(R.drawable.upload_icon);
            notification.setContentTitle("Post Uploading..");//.setContentText(track.getName())
            notification.setLargeIcon(icon);
            notification.setOnlyAlertOnce(true);//show notification for only first time
            notification.setShowWhen(false);

            notification.setContentIntent(pIntent);
            notification.setPriority(NotificationCompat.PRIORITY_LOW);//PRIORITY_LOW

            startForeground(3, notification.build());
            Log.d("DownloadService>>>", "ok");

//            setOnClickDownload(context, progressBar, button, download, pnp, page);
        }
    }

    public static void upload(TextView loadtext, Activity activity, Context context, List<Uri> urllist, String rd, int myid, MainModelDetail model) {
        for (int i = 0; i < urllist.size(); i++) {
            Uri uri = urllist.get(i);
            int position = i;
            int pos = i+1;
            loadtext.setText("Image Uploading... " + pos + "/" + urllist.size());
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + String.valueOf(i));
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("UploadService>>>", "1");
                    if (position == urllist.size() - 1) {
                        Log.d("UploadService>>>", "2");
                        loadtext.setText("Post Uploading...");
                        Intent intent = new Intent(context, UploadService.class);
                        context.stopService(intent);
                        CommunityWrite.uploadToDjango(activity, context, myid, model);
                        FinishedUploadNotification.finishedUploadNotification(context);
                    }
                }
            });
        }
    }
}
