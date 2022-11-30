package com.dippola.relaxtour.community.main;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.widget.NestedScrollView;

import com.dippola.relaxtour.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityWrite extends AppCompatActivity {

    private int FROM_GALLERY = 1;

    private NestedScrollView scrollView;
    private Button goback, ok, addshare;
    private EditText title, body;
    private ConstraintLayout addimg;
    private ImageView img1, img2, img3, img4, img5;
    private TextView shereText, bottomtext;
    private ConstraintLayout imgbox1, imgbox2, imgbox3, imgbox4, imgbox5;
    private Button cancel1, cancel2, cancel3, cancel4, cancel5;

    private List<String> urllist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_write);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int y = (int)(size.y * 0.3);
        int x = (int)(size.x * 0.2);

        setInit(y, x);
    }

    private void setInit(int y, int x) {
        scrollView = findViewById(R.id.community_write_scrollview);
        setScrollView();
        goback = findViewById(R.id.community_write_back);
        onClickGoBack();
        ok = findViewById(R.id.community_write_ok);
        title = findViewById(R.id.community_write_title);
        body = findViewById(R.id.community_write_body);
        addshare = findViewById(R.id.community_write_addshare);
        addimg = findViewById(R.id.community_write_addimg);
        onClickAddImg();
        imgbox1 = findViewById(R.id.community_write_imgbox1);
        imgbox2 = findViewById(R.id.community_write_imgbox2);
        imgbox3 = findViewById(R.id.community_write_imgbox3);
        imgbox4 = findViewById(R.id.community_write_imgbox4);
        imgbox5 = findViewById(R.id.community_write_imgbox5);
        img1 = findViewById(R.id.community_write_img1);
        img2 = findViewById(R.id.community_write_img2);
        img3 = findViewById(R.id.community_write_img3);
        img4 = findViewById(R.id.community_write_img4);
        img5 = findViewById(R.id.community_write_img5);
        cancel1 = findViewById(R.id.community_write_img1_cancel1);
        cancel2 = findViewById(R.id.community_write_img1_cancel2);
        cancel3 = findViewById(R.id.community_write_img1_cancel3);
        cancel4 = findViewById(R.id.community_write_img1_cancel4);
        cancel5 = findViewById(R.id.community_write_img1_cancel5);
        shereText = findViewById(R.id.community_write_share_text);
        bottomtext = findViewById(R.id.community_write_bottom_text);
        body.setMinHeight(y);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(x, x);
        addimg.setLayoutParams(params);
        img1.setLayoutParams(params);
        img2.setLayoutParams(params);
        img3.setLayoutParams(params);
        img4.setLayoutParams(params);
        img5.setLayoutParams(params);
        imgbox1.setVisibility(View.GONE);
        imgbox2.setVisibility(View.GONE);
        imgbox3.setVisibility(View.GONE);
        imgbox4.setVisibility(View.GONE);
        imgbox5.setVisibility(View.GONE);
    }
    private void onClickAddImg() {
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (urllist.size() > 5) {
//                    Toast.makeText(CommunityWrite.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    launcher.launch(intent);
//                }
                urllist.add("1");
                urllist.add("2");
                urllist.add("3");
                urllist.add("4");
                Log.d("CommunityWrite>>>", "1 urllist size: " + urllist.size());
                Log.d("CommunityWrite>>>", "2 urllist get 1: " + urllist.get(1));
                urllist.remove("2");
                Log.d("CommunityWrite>>>", "3 urllist size1: " + urllist.get(1));
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    Intent data = result.getData();
                    if (data.getClipData() != null) {
                        ClipData clipData = result.getData().getClipData();
                        if (urllist.size() + clipData.getItemCount() < 5) {
                            Toast.makeText(CommunityWrite.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                urllist.add(imageUri.toString());
                                Log.d("CommunityWrite>>>", "uri: " + imageUri);
                            }
                        }
                    } else {
                        Uri imageUri = data.getData();
                        urllist.add(imageUri.toString());
                        Log.d("CommunityWrite>>>", "uri: " + imageUri);
                    }
                    setImage(urllist);
                }
            }
        }
    });

    private void setImage(List<String> urllist) {
//        if ()
    }

    private void setScrollView() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.clearFocus();
                return false;
            }
        });
    }
    private void onClickGoBack() {
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
