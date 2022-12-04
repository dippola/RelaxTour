package com.dippola.relaxtour.community;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.community.main.write.UploadService;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.service.DownloadService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Test extends AppCompatActivity {
    TextView t1url;
    Uri uri;

    List<Uri> urllist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communiry_test);
        test();
    }

    private void test() {
        EditText e1 = findViewById(R.id.e1);
        EditText e2 = findViewById(R.id.e2);
        Button t1 = findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCurrentFocus() == e1) {
                    Log.d("Test>>>", "1");
                } else if (getCurrentFocus() == e2) {
                    Log.d("Test>>>", "2");
                }
            }
        });

        ImageView img1 = findViewById(R.id.img1);
        byte[] url = new DatabaseHandler(Test.this).getPageList(1).get(0).getImg();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(url, 0, url.length);
        img1.setImageBitmap(bitmap1);
        ImageView img2 = findViewById(R.id.img2);
        img2.setImageBitmap(bitmap1);
        ImageView img3 = findViewById(R.id.img3);
        img3.setImageBitmap(bitmap1);
        ImageView img4 = findViewById(R.id.img4);
        img4.setImageBitmap(bitmap1);
        ImageView img5 = findViewById(R.id.img5);
        img5.setImageBitmap(bitmap1);
        ImageView img6 = findViewById(R.id.img6);
        img6.setImageBitmap(bitmap1);
        ImageView img7 = findViewById(R.id.img7);
        img7.setImageBitmap(bitmap1);
        ImageView img8 = findViewById(R.id.img8);
        img8.setImageBitmap(bitmap1);
        ImageView img9 = findViewById(R.id.img9);
        img9.setImageBitmap(bitmap1);
        ImageView img10 = findViewById(R.id.img10);
        img10.setImageBitmap(bitmap1);
    }
}
