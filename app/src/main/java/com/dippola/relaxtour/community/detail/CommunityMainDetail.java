package com.dippola.relaxtour.community.detail;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainModelDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMainDetail extends AppCompatActivity {

    private MainModelDetail mainModelDetail;
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
        RetrofitClient.getApiService().getMain(parent_user).enqueue(new Callback<com.dippola.relaxtour.retrofit.model.MainModelDetail>() {
            @Override
            public void onResponse(Call<com.dippola.relaxtour.retrofit.model.MainModelDetail> call, Response<com.dippola.relaxtour.retrofit.model.MainModelDetail> response) {
                if (response.isSuccessful()) {
                    mainModelDetail.setParent_user(parent_user);
                    mainModelDetail.setParent_user(response.body().getParent_user());
                    mainModelDetail.setNickname(response.body().getNickname());
                    mainModelDetail.setUser_url(response.body().getUser_url());
                    mainModelDetail.setDate(response.body().getDate());
                    mainModelDetail.setTitle(response.body().getTitle());
                    mainModelDetail.setBody(response.body().getBody());
                    mainModelDetail.setImageurl(response.body().getImageurl());
                    mainModelDetail.setView(response.body().getView());
                    mainModelDetail.setLike(response.body().getLike());
                    mainModelDetail.setList(response.body().getList());

                }
            }

            @Override
            public void onFailure(Call<com.dippola.relaxtour.retrofit.model.MainModelDetail> call, Throwable t) {

            }
        });
    }

    private void setInit() {
        title = findViewById(R.id.community_main_detail_title);
        nickname = findViewById(R.id.community_main_detail_nickname);
        view = findViewById(R.id.community_main_detail_view);
        body = findViewById(R.id.community_main_detail_body);
    }
}
