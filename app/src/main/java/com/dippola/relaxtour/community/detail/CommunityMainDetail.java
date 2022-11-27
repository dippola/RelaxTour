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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main_detail);

        getData();
        setInit();
    }

    private void getData() {
        parent_user = getIntent().getIntExtra("parent_user", 0);
    }

    private void setInit() {
        title = findViewById(R.id.community_main_detail_title);
        nickname = findViewById(R.id.community_main_detail_nickname);
        view = findViewById(R.id.community_main_detail_view);
        body = findViewById(R.id.community_main_detail_body);
    }
}
