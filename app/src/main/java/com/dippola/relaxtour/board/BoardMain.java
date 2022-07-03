package com.dippola.relaxtour.board;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

import java.io.IOException;

public class BoardMain extends AppCompatActivity {

    RelativeLayout load;
//    FirebaseUser user;

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

    private void test() {
        Context context = BoardMain.this;
        Button btn1 = findViewById(R.id.test1);
        Button btn2 = findViewById(R.id.test2);
//        MediaPlayer mp = new MediaPlayer();
//        try {
//            mp.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio5to2.mp3");
//            mp.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        MediaPlayer mp =  MediaPlayer.create(context, R.raw.audio5to1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mp.isPlaying()) {
                    mp.start();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.prepareAsync();
                }
            }
        });
    }
}
