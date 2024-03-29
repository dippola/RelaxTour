package com.dippola.relaxtour.community.main.write;

import static com.dippola.relaxtour.notification.NotifiControllID.POST_ID;

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
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.CommunityMain;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostModelDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadService extends Service {
    public static final String CHANNEL_ID = "postring";
    private static List<String> resultUrlList = new ArrayList<>();

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
                    PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder notification;
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Posting", NotificationManager.IMPORTANCE_DEFAULT);
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

            startForeground(POST_ID, notification.build());

//            setOnClickDownload(context, progressBar, button, download, pnp, page);
        }
    }

    public static void upload(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        if (urllist.size() != 0) {
            loadtext.setText("Image Uploading... 0/" + urllist.size());
            upload1(1, loadtext, activity, context, urllist, rd, myid, model, load);
        } else {
            model.setImageurl("");
            uploadToDjango(activity, context, myid, model, load, loadtext, rd);
        }
    }

    private static void upload1(int i, TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), urllist.get(i-1).getUri());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageData = baos.toByteArray();
            final int position = i;
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + urllist.get(i - 1).getName());
            reference.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            loadtext.setText("Image Uploading... " + position + "/" + urllist.size());
                            resultUrlList.add(uri.toString());
                            if (urllist.size() == position) {
                                checkurllistsize(urllist.size(), resultUrlList, rd, loadtext, context, activity, myid, model, load);
                            } else {
                                int positionPlus = position + 1;
                                upload1(positionPlus, loadtext, activity, context, urllist, rd, myid, model, load);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkurllistsize(int size, List<String> resultUrlList, String rd, TextView loadtext, Context context, Activity activity, int myid, PostModelDetail model, RelativeLayout load) {
        if (size == resultUrlList.size()) {
            String resultUrlStrings = rd;
            for (int i = 0; i < resultUrlList.size(); i++) {
                resultUrlStrings += "●" + resultUrlList.get(i);
            }
            model.setImageurl(resultUrlStrings);
            uploadToDjango(activity, context, myid, model, load, loadtext, rd);
        }
    }

    public static void uploadToDjango(Activity activity, Context context, int id, PostModelDetail model, RelativeLayout load, TextView loadtext, String rd) {
        loadtext.setText("Post Uploading...");
        RetrofitClient.getApiService(context).createPost(id, model, context.getString(R.string.appkey)).enqueue(new Callback<PostModelDetail>() {
            @Override
            public void onResponse(Call<PostModelDetail> call, Response<PostModelDetail> response) {
                if (response.isSuccessful()) {
                    Log.d("CommunityWrite>>>", "1: " + response.message());
                    Toast.makeText(context, "Post registration complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CommunityMain.class);
                    intent.putExtra("write", true);
                    activity.setResult(CommunityMain.FROM_WRITE, intent);
                    Intent intent1 = new Intent(context, UploadService.class);
                    context.stopService(intent1);
                    FinishedUploadNotification.finishedUploadNotification(context);
                    activity.finish();
                } else {
                    Toast.makeText(context, "The Internet connection was unstable and failed.\nPlease try again.\n" + response.message(), Toast.LENGTH_SHORT).show();
                }
                load.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PostModelDetail> call, Throwable t) {
                Map<String, Object> map = new HashMap<>();
                map.put("rd", rd);
                FirebaseFirestore.getInstance().collection("post_error").document(rd).set(map);
                Intent intent1 = new Intent(context, UploadService.class);
                context.stopService(intent1);
                Toast.makeText(context, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
            }
        });
    }
}
