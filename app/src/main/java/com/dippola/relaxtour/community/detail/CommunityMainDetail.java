package com.dippola.relaxtour.community.detail;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.model.MainModel;

public class CommunityMainDetail extends AppCompatActivity {

    private MainModel mainModel;
    Button back;
    TextView title, nickname, view, body;
    int id, parent_user;
    String dateString, titleString, nicknameString, viewString, bodyString, imageurl, like, list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main_detail);

        getData();
        setInit();
    }

    private void getData() {
        id = getIntent().getIntExtra("id", 0);
        parent_user = getIntent().getIntExtra("parent_user", 0);
        dateString = getIntent().getStringExtra("date");
        titleString = getIntent().getStringExtra("title");
        nicknameString = getIntent().getStringExtra("nickname");
        viewString = getIntent().getStringExtra("view");
        bodyString = getIntent().getStringExtra("body");
        imageurl = getIntent().getStringExtra("imageurl");
        like = getIntent().getStringExtra("like");
        list = getIntent().getStringExtra("list");
    }

    private void setInit() {
        title = findViewById(R.id.community_main_detail_title);
        nickname = findViewById(R.id.community_main_detail_nickname);
        view = findViewById(R.id.community_main_detail_view);
        body = findViewById(R.id.community_main_detail_body);
    }
}
