package com.dippola.relaxtour.community;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dippola.relaxtour.R;
import com.jsibbold.zoomage.ZoomageView;

public class ImageViewer extends AppCompatActivity {
    private ZoomageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);

        img = findViewById(R.id.zoomage_view);

        if (getIntent().getStringExtra("url") != null) {
            Glide.with(ImageViewer.this).load(getIntent().getStringExtra("url")).into(img);
        } else {
            Glide.with(ImageViewer.this).load(getResources().getDrawable(R.drawable.nullpic)).into(img);
        }
    }
}
