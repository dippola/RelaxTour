package com.dippola.relaxtour.community.main.write;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityWrite extends AppCompatActivity {

    private int FROM_GALLERY = 1;
    public static int FROM_LIST = 2;

    private NestedScrollView scrollView;
    private Button goback, ok, addshare;
    private EditText title, body;
    private ConstraintLayout addimg;
    private TextView shereText, bottomtext;
    public static TextView imagecount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WriteImageAdapter adapter;

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
        onClickAddShare();
        addimg = findViewById(R.id.community_write_addimg);
        onClickAddImg();
        imagecount = findViewById(R.id.community_write_imgcount);
        recyclerView = findViewById(R.id.community_write_recyclerview);
        shereText = findViewById(R.id.community_write_share_text);
        bottomtext = findViewById(R.id.community_write_bottom_text);
        body.setMinHeight(y);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(x, x);
        ConstraintLayout.LayoutParams paramsToAdapter = new ConstraintLayout.LayoutParams(x, x);
        addimg.setLayoutParams(params);
        setRecyclerView(paramsToAdapter);
        onClickOk();
    }

    private void onClickOk() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().length() == 0) {
                    Toast.makeText(CommunityWrite.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                } else if (body.getText().toString().length() == 0) {
                    Toast.makeText(CommunityWrite.this, "Please enter a body", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });
    }

    private void onClickAddShare() {
        addshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityWrite.this, ShareListDialog.class);
                launcher.launch(intent);
            }
        });
    }

    private void setRecyclerView(ConstraintLayout.LayoutParams params) {
        adapter = new WriteImageAdapter(urllist, CommunityWrite.this, params);
        layoutManager = new LinearLayoutManager(CommunityWrite.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void onClickAddImg() {
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urllist.size() > 5) {
                    Toast.makeText(CommunityWrite.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    launcher.launch(intent);
                }
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
                        if (urllist.size() + clipData.getItemCount() > 5) {
                            Toast.makeText(CommunityWrite.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                if (!urllist.contains(imageUri.toString())) {
                                    urllist.add(imageUri.toString());
                                }
                                Log.d("CommunityWrite>>>", "uri: " + imageUri);
                            }
                        }
                    } else {
                        Uri imageUri = data.getData();
                        urllist.add(imageUri.toString());
                        Log.d("CommunityWrite>>>", "uri: " + imageUri);
                    }
                    setImage();
                }
            } else if (result.getResultCode() == FROM_LIST) {
                if (result.getData() != null) {
                    Log.d("CommunityWrite>>>", "from list: " + result.getData().getStringExtra("title"));
                }
            }
        }
    });

    private void setImage() {
        adapter.notifyDataSetChanged();
        imagecount.setText(urllist.size() + "/5");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = (int)(size.x * 0.3);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.width = x * urllist.size();
        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();
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
