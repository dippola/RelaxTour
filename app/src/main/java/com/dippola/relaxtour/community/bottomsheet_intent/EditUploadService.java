package com.dippola.relaxtour.community.bottomsheet_intent;

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
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.community.main.write.CommunityWrite;
import com.dippola.relaxtour.community.main.write.FinishedUploadNotification;
import com.dippola.relaxtour.community.main.write.UploadService;
import com.dippola.relaxtour.community.main.write.UriAndFileNameModel;
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

public class EditUploadService extends Service {
    public static final String CHANNEL_ID = "edit upload";
//    public static List<Uri> urilist = new ArrayList<>();
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

    public static void deleteImage(int postid, TextView loadtext, Activity activity, Context context, List<UriAndFromModel> urllist, List<Uri> deleteUrlList, List<UriAndFileNameModel> addUrllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        Log.d("EditUploadService>>>", "delete size: " + deleteUrlList.size());
        if (deleteUrlList.size() != 0) {
            deleteImage2(postid, 0, loadtext, activity, context, urllist, deleteUrlList, addUrllist, rd, myid, model, load);
        } else {
            upload(postid, loadtext, activity, context, urllist, addUrllist, rd, myid, model, load);
        }
    }

    private static void deleteImage2(int postid, int i, TextView loadtext, Activity activity, Context context, List<UriAndFromModel> urllist, List<Uri> deleteUrlList, List<UriAndFileNameModel> addUrllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        loadtext.setText("Delete Image... " + i + "/" + deleteUrlList.size());
        Uri uri = deleteUrlList.get(i);
        String fileName = uri.getLastPathSegment().split("/")[uri.getLastPathSegment().split("/").length - 1];
        StorageReference reference1 = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + fileName);
        reference1.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (deleteUrlList.size() == i + 1) {
                        upload(postid, loadtext, activity, context, urllist, addUrllist, rd, myid, model, load);
                    } else {
                        int iplus = i + 1;
                        deleteImage2(postid, iplus, loadtext, activity, context, urllist, deleteUrlList, addUrllist, rd, myid, model, load);
                    }
                }
            }
        });
    }

    private static void upload(int postid, TextView loadtext, Activity activity, Context context, List<UriAndFromModel> urllist, List<UriAndFileNameModel> addUrllist, String rd, int myid, PostModelDetail model, RelativeLayout load) {
        if (addUrllist.size() != 0) {
            loadtext.setText("Image Uploading... 0/" + addUrllist.size());
            List<String> downloadUrl = new ArrayList<>();
            upload2(postid, 0, loadtext, activity, context, urllist, addUrllist, rd, myid, model, load, downloadUrl);
        } else {
            List<String> nulllist = new ArrayList<>();
            uploadToDjango(postid, activity, context, myid, model, load, loadtext, rd, urllist, nulllist);
        }
    }

    private static void upload2(int postid, int i, TextView loadtext, Activity activity, Context context, List<UriAndFromModel> urllist, List<UriAndFileNameModel> addUrllist, String rd, int myid, PostModelDetail model, RelativeLayout load, List<String> downloadUrl) {
        Uri uri = addUrllist.get(i).getUri();
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("community/main/" + rd + "/" + addUrllist.get(i).getName());
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUrl.add(uri.toString());
                        int iplus = i + 1;
                        loadtext.setText("Image Uploading... " + iplus + "/" + addUrllist.size());
                        if (addUrllist.size() == i + 1) {
                            uploadToDjango(postid, activity, context, myid, model, load, loadtext, rd, urllist, downloadUrl);
                        } else {
                            upload2(postid, iplus, loadtext, activity, context, urllist, addUrllist, rd, myid, model, load, downloadUrl);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (addUrllist.size() == i + 1) {
                            uploadToDjango(postid, activity, context, myid, model, load, loadtext, rd, urllist, downloadUrl);
                        } else {
                            upload2(postid, i + 1, loadtext, activity, context, urllist, addUrllist, rd, myid, model, load, downloadUrl);
                        }
                    }
                });
            }
        });
    }

    public static void uploadToDjango(int postid, Activity activity, Context context, int id, PostModelDetail model, RelativeLayout load, TextView loadtext, String rd, List<UriAndFromModel> urlList, List<String> downloadUrl) {
        Log.d("EditUploadService>>>", "add size: " + downloadUrl.size());
        loadtext.setText("Post Uploading...");
        String resultUrlString = "";

        PostModelDetail postModelDetail = new PostModelDetail();
        postModelDetail.setTitle(model.getTitle());
        postModelDetail.setBody(model.getBody());

        if (urlList.size() != 0) {
            resultUrlString = rd;
            for (int i = 0; i < urlList.size(); i++) {
                if (urlList.get(i).getFrom().equals("exist")) {
                    resultUrlString += "●" + urlList.get(i).getUrl().toString();
                }
            }
        }
        if (downloadUrl.size() != 0) {
            if (resultUrlString.length() == 0) {
                resultUrlString = rd;
            }
            for (int i = 0; i < downloadUrl.size(); i++) {
                resultUrlString += "●" + downloadUrl.get(i);
            }
        }

        postModelDetail.setImageurl(resultUrlString);
        if (model.getList().length() == 0) {
            postModelDetail.setList("");
        } else {
            postModelDetail.setList(model.getList());
        }
        RetrofitClient.getApiService(context).updateMain(postid, postModelDetail, context.getString(R.string.appkey)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Post registration complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CommunityMainDetail.class);
                    intent.putExtra("isEdit", true);
                    activity.setResult(CommunityMainDetail.FROM_EDITPOST, intent);
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
            public void onFailure(Call<String> call, Throwable t) {
                Intent intent1 = new Intent(context, UploadService.class);
                context.stopService(intent1);
                Toast.makeText(context, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                load.setVisibility(View.GONE);
            }
        });
    }


}
