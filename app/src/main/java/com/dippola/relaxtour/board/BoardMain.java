package com.dippola.relaxtour.board;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.item.DownloadItem;

import java.io.IOException;
import java.util.ArrayList;

public class BoardMain extends AppCompatActivity {

    RelativeLayout load;
//    FirebaseUser user;

    ArrayList<DownloadItem> downloadList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_main);

        this.load = (RelativeLayout) findViewById(R.id.board_main_load);
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        this.user = currentUser;
//        if (currentUser == null) {
//            this.load.setVisibility(View.VISIBLE);
//            startActivity(new Intent(this, BoardLogin.class));
//            return;
//        }
        this.load.setVisibility(View.GONE);

        test();
    }

//    private void test() {
//        Context context = BoardMain.this;
//        Button btn1 = findViewById(R.id.test1);
//        Button btn2 = findViewById(R.id.test2);
//        Button btn3 = findViewById(R.id.test3);
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DownloadItem downloadItem = new DownloadItem(1, 1);
//                TestService.downloadList.add(downloadItem);
//                if (!TestService.isTestOpen) {
//                    TestService testService = new TestService();
//                    Intent intent = new Intent(context, testService.getClass());
//                    if (Build.VERSION.SDK_INT >= 26) {
//                        context.startForegroundService(intent);
//                    } else {
//                        context.startService(intent);
//                    }
//                }
////                TestService testService = new TestService();
////                Intent intent = new Intent(context, testService.getClass());
////                intent.putExtra("ci", "1-1");
////                if (Build.VERSION.SDK_INT >= 26) {
////                    context.startForegroundService(intent);
////                } else {
////                    context.startService(intent);
////                }
////                TestService.fiveSecond(context, "1-1");
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TestService testService = new TestService();
//                Intent intent = new Intent(context, testService.getClass());
//                intent.putExtra("ci", "1-2");
//                if (Build.VERSION.SDK_INT >= 26) {
//                    context.startForegroundService(intent);
//                } else {
//                    context.startService(intent);
//                }
//                TestService.fiveSecond(context, "1-2");
//            }
//        });
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TestService testService = new TestService();
//                Intent intent = new Intent(context, testService.getClass());
//                intent.putExtra("ci", "1-3");
//                if (Build.VERSION.SDK_INT >= 26) {
//                    context.startForegroundService(intent);
//                } else {
//                    context.startService(intent);
//                }
//                TestService.fiveSecond(context, "1-3");
//            }
//        });
//    }

    private void test() {
        Context context = BoardMain.this;
        Button btn1 = findViewById(R.id.test1);
        Button btn2 = findViewById(R.id.test2);
        Button btn3 = findViewById(R.id.test3);


        DownloadItem downloadItem1 = new DownloadItem(1, 1);
        DownloadItem downloadItem2 = new DownloadItem(1, 2);
        DownloadItem downloadItem3 = new DownloadItem(1, 3);
        DownloadItem downloadItem4 = new DownloadItem(1, 4);
        downloadList.add(downloadItem1);
        downloadList.add(downloadItem2);
        downloadList.add(downloadItem3);
        downloadList.add(downloadItem4);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mytest(downloadItem1, downloadItem2);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void mytest(DownloadItem d1, DownloadItem d2) {
        Log.d("BoardMain>>>", "index of1: " + downloadList.indexOf(d2));
        downloadList.remove(d1);
        Log.d("BoardMain>>>", "index of2: " + downloadList.indexOf(d2));
    }

}
