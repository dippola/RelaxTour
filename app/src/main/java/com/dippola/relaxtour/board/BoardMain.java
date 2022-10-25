package com.dippola.relaxtour.board;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class BoardMain extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_main);


        View test = findViewById(R.id.testview);

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.favlist_open);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.favlist_close);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.startAnimation(anim1);
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.startAnimation(anim2);
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.clearAnimation();
            }
        });
    }
}
