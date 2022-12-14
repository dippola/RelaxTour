package com.dippola.relaxtour.community.auth.userscommunity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;

public class UsersCommunity extends AppCompatActivity {

    private Button back;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_community);

        String from = getIntent().getStringExtra("from");
        getData(from);
    }

    private void setInit() {
        back = findViewById(R.id.users_community_back);
        recyclerView = findViewById(R.id.users_community_recyclerview);
    }

    private void getData(String from) {
        if (from.equals("posts")) {
            getPostsData();
        }
    }

    private void getPostsData() {

    }
}
