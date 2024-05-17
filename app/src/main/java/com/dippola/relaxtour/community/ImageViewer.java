package com.dippola.relaxtour.community;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dippola.relaxtour.R;
import com.jsibbold.zoomage.ZoomageView;

public class ImageViewer extends AppCompatActivity {
    private ZoomageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);

        img = findViewById(R.id.zoomage_view);

        if (getIntent().getStringExtra("url").equals("")) {
            Glide.with(ImageViewer.this).load(R.drawable.nullpic).into(img);
        } else {
            Glide.with(ImageViewer.this).load(getIntent().getStringExtra("url")).into(img);
        }
    }
}
