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
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.controller.RainController;
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

    private static MediaPlayer p71, p72;
    private void test() {
        Context context = BoardMain.this;
        Button btn1 = findViewById(R.id.test1);
        Button btn2 = findViewById(R.id.test2);
        Button btn3 = findViewById(R.id.test3);

        p71 = MediaPlayer.create(context, R.raw.audio7to1);
        p72 = MediaPlayer.create(context, R.raw.audio7to1);
        float float4 = (float) 0.10375938;
        p71.setVolume(float4, float4);
        p72.setVolume(float4, float4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p71.start();
                new p7t1().start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p71.start();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p71.isPlaying()) {
                    p71.stop();
                    p71.prepareAsync();
                }
                if (p72.isPlaying()) {
                    p72.stop();
                    p72.prepareAsync();
                }
                new p7t1().setStop(true);
                new p7t2().setStop(true);
            }
        });
    }

    public static class p7t1 extends Thread {
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (p71.isPlaying()) {
                    int i = p71.getCurrentPosition();
                    if (p71.getCurrentPosition() >= 7800) {//2.2초 전에
                        p72.start();
                        new p7t2().start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p7t2 extends Thread {
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (p72.isPlaying()) {
                    int i = p72.getCurrentPosition();
                    if (p72.getCurrentPosition() >= 7800) {
                        p71.start();
                        new p7t1().start();
                        setStop(true);
                    }
                }
            }
        }
    }
}
