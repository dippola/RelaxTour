package com.dippola.relaxtour.community.auth.userscommunity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostModelView;
import com.dippola.relaxtour.retrofit.model.PostsViewWitPages;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersCommunity extends AppCompatActivity {

    private Button back;
    private RecyclerView recyclerView;
    private List<PostModelView> lists = new ArrayList<>();
    private int nowPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_community);

        nowPage = 1;

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
        RetrofitClient.getApiService().getMainPageAll(nowPage).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists = response.body().getPosts();
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

            }
        });
    }
}
