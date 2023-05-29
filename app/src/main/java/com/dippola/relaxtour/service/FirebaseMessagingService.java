package com.dippola.relaxtour.service;

import static com.dippola.relaxtour.notification.NotifiControllID.FIREBASE_NOTIFICATION_ID;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.community.main.notification.Notification;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        if (remoteMessage.getNotification() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Log.d("FMS>>>", "2");
//            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//        }
    }

    @Override
    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            sendNotification(intent.getStringExtra("title"), intent.getStringExtra("body"));
        }
    }

    private void sendNotification(String title, String messageBody) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        if (databaseHandler.getNotificationAgree() == 1) {
            Intent intent;
            String showTitle;
            if (title.contains("●")) {
                if (title.split("●")[0].equals("comment")) {
                    intent = new Intent(this, CommunityMainDetail.class);
                    intent.putExtra("parent_id", Integer.parseInt(title.split("●")[1]));
                    showTitle = title.split("●")[4];
                    insertDB(title, messageBody);
                } else if (title.split("●")[0].equals("update")){
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse(getString(R.string.playstore_uri)));
                    showTitle = title.split("●")[1];
                    insertDB(title, messageBody);
                } else if (title.split("●")[0].equals("admin")) {
                    intent = new Intent(this, Notification.class);
                    showTitle = title.split("●")[4];
                    insertDB(title, messageBody);
                } else if (title.split("●")[0].equals("admin_profile")) {
                    intent = new Intent(this, CommunityAuth.class);
                    showTitle = title.split("●")[3];
                    insertDB(title, messageBody);
                    updateProfileInDB();
                } else {
                    intent = new Intent(this, MainActivity.class);
                    showTitle = title;
                }
            } else {
                intent = new Intent(this, MainActivity.class);
                showTitle = title;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_IMMUTABLE);

            String channelId = "relax tour notification channel id";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.tabicon_chakra)
                            .setContentTitle(showTitle)
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(FIREBASE_NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
        }
    }

    private void insertDB(String title, String body) {
        SharedPreferences sharedPreferences = getSharedPreferences("haveNewNotification", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("haveNewNotification", true);
        editor.apply();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        String date = getTime();
        databaseHandler.insertCNotification(title, body, date);
    }

    private void updateProfileInDB() {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        RetrofitClient.getApiService(this).getUser(databaseHandler.getUserModel().getId(), getString(R.string.appkey)).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    databaseHandler.updateUserProfile(response.body().get(0).getNickname(), response.body().get(0).getImageurl(), response.body().get(0).getUid());
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }
    private String getTime() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = format.format(mDate);
        return date;
    }
}
