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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadService extends Service {
    public static final String CHANNEL_ID = "upload";
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

//            setOnClickDownload(context, progressBar, button, download, pnp, page);
        }
    }

    public static void upload(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        if (urllist.size() != 0) {
            loadtext.setText("Image Uploading... 0/" + urllist.size());
            upload1(loadtext, activity, context, urllist, rd, myid, model, load);
        } else {
            model.setImageurl("");
            uploadToDjango(activity, context, myid, model, load, loadtext);
        }
    }

    private static void upload1(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        int i = 1;
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + urllist.get(i - 1).getName());
        reference.putFile(urllist.get(i - 1).getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        loadtext.setText("Image Uploading... " + i + "/" + urllist.size());
                        resultUrlList.add(uri.toString());
                        if (urllist.size() == i) {
                            checkurllistsize(urllist.size(), resultUrlList, rd, loadtext, context, activity, myid, model, load);
                        } else {
                            upload2(loadtext, activity, context, urllist, rd, myid, model, load);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private static void upload2(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        int i = 2;
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + urllist.get(i - 1).getName());
        reference.putFile(urllist.get(i - 1).getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        loadtext.setText("Image Uploading... " + i + "/" + urllist.size());
                        resultUrlList.add(uri.toString());
                        if (urllist.size() == i) {
                            checkurllistsize(urllist.size(), resultUrlList, rd, loadtext, context, activity, myid, model, load);
                        } else {
                            upload3(loadtext, activity, context, urllist, rd, myid, model, load);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private static void upload3(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        int i = 3;
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + urllist.get(i - 1).getName());
        reference.putFile(urllist.get(i - 1).getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        loadtext.setText("Image Uploading... " + i + "/" + urllist.size());
                        resultUrlList.add(uri.toString());
                        if (urllist.size() == i) {
                            checkurllistsize(urllist.size(), resultUrlList, rd, loadtext, context, activity, myid, model, load);
                        } else {
                            upload4(loadtext, activity, context, urllist, rd, myid, model, load);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private static void upload4(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        int i = 4;
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + urllist.get(i - 1).getName());
        reference.putFile(urllist.get(i - 1).getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        loadtext.setText("Image Uploading... " + i + "/" + urllist.size());
                        resultUrlList.add(uri.toString());
                        if (urllist.size() == i) {
                            checkurllistsize(urllist.size(), resultUrlList, rd, loadtext, context, activity, myid, model, load);
                        } else {
                            upload5(loadtext, activity, context, urllist, rd, myid, model, load);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private static void upload5(TextView loadtext, Activity activity, Context context, List<UriAndFileNameModel> urllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        int i = 5;
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + urllist.get(i - 1).getName());
        reference.putFile(urllist.get(i - 1).getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        loadtext.setText("Image Uploading... " + i + "/" + urllist.size());
                        resultUrlList.add(uri.toString());
                        checkurllistsize(urllist.size(), resultUrlList, rd, loadtext, context, activity, myid, model, load);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private static void checkurllistsize(int size, List<String> resultUrlList, String rd, TextView loadtext, Context context, Activity activity, int myid, PostModelDetail model, RelativeLayout load) {
        if (size == resultUrlList.size()) {
            String resultUrlStrings = rd;
            for (int i = 0; i < resultUrlList.size(); i++) {
                resultUrlStrings += "●" + resultUrlList.get(i);
            }
            model.setImageurl(resultUrlStrings);
            uploadToDjango(activity, context, myid, model, load, loadtext);
        }
    }

    public static void uploadToDjango(Activity activity, Context context, int id, PostModelDetail model, RelativeLayout load, TextView loadtext) {
        loadtext.setText("Post Uploading...");
        RetrofitClient.getApiService().createPost(id, model, context.getString(R.string.appkey)).enqueue(new Callback<PostModelDetail>() {
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
                Intent intent1 = new Intent(context, UploadService.class);
                context.stopService(intent1);
                Toast.makeText(context, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
            }
        });
    }
}
