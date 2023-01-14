package com.dippola.relaxtour.community.admin.detail;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.detail.AddHitModel;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.dippola.relaxtour.retrofit.model.PostDetailWithComments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetail extends AppCompatActivity {

    private List<PostCommentModel> commentList = new ArrayList<>();

    ImageView userimg, img1, img2, img3, img4, img5;
    TextView from, nickname, title, body;
    RecyclerView recyclerView;

    AdminDetailCommentAdapter adapter;

    int postid;
    int commentid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_detail);

        postid = getIntent().getIntExtra("postid", 0);
        commentid = getIntent().getIntExtra("commentid", 0);

        setInit();
        getData();
    }

    private void setInit() {
        from = findViewById(R.id.admin_detail_from);
        from.setText(getIntent().getStringExtra("from"));
        userimg = findViewById(R.id.admin_detail_userimage);
        img1 = findViewById(R.id.admin_detail_img1);
        img2 = findViewById(R.id.admin_detail_img2);
        img3 = findViewById(R.id.admin_detail_img3);
        img4 = findViewById(R.id.admin_detail_img4);
        img5 = findViewById(R.id.admin_detail_img5);
        nickname = findViewById(R.id.admin_detail_nickname);
        title = findViewById(R.id.admin_detail_title);
        body = findViewById(R.id.admin_detail_body);
        recyclerView = findViewById(R.id.admin_detail_recyclerview);
    }

    private void getData() {
        AddHitModel addHitModel = new AddHitModel();
        addHitModel.setWillAddHit(false);
        RetrofitClient.getApiService(AdminDetail.this).getPost(postid, addHitModel, getString(R.string.appkey)).enqueue(new Callback<PostDetailWithComments>() {
            @Override
            public void onResponse(Call<PostDetailWithComments> call, Response<PostDetailWithComments> response) {
                if (response.isSuccessful()) {
                    setData(response);
                }
            }

            @Override
            public void onFailure(Call<PostDetailWithComments> call, Throwable t) {

            }
        });
    }

    private void setData(Response<PostDetailWithComments> response) {
        Glide.with(userimg.getContext()).load(response.body().getPost().getUser_url()).into(userimg);
        nickname.setText("nickname: " + response.body().getPost().getNickname());
        title.setText("title: " + response.body().getPost().getTitle());
        body.setText("body: " + response.body().getPost().getBody());
        if (response.body().getPost().getImageurl().length() != 0) {
            String[] url = response.body().getPost().getImageurl().split("●");
            if (url.length == 2) {
                Glide.with(img1.getContext()).load(url[1]).into(img1);
            } else if (url.length == 3) {
                Glide.with(img1.getContext()).load(url[1]).into(img1);
                Glide.with(img2.getContext()).load(url[2]).into(img2);
            } else if (url.length == 4) {
                Glide.with(img1.getContext()).load(url[1]).into(img1);
                Glide.with(img2.getContext()).load(url[2]).into(img2);
                Glide.with(img3.getContext()).load(url[3]).into(img3);
            } else if (url.length == 5) {
                Glide.with(img1.getContext()).load(url[1]).into(img1);
                Glide.with(img2.getContext()).load(url[2]).into(img2);
                Glide.with(img3.getContext()).load(url[3]).into(img3);
                Glide.with(img4.getContext()).load(url[4]).into(img4);
            } else if (url.length == 6) {
                Glide.with(img1.getContext()).load(url[1]).into(img1);
                Glide.with(img2.getContext()).load(url[2]).into(img2);
                Glide.with(img3.getContext()).load(url[3]).into(img3);
                Glide.with(img4.getContext()).load(url[4]).into(img4);
                Glide.with(img5.getContext()).load(url[5]).into(img5);
            }
        }

        RetrofitClient.getApiService(AdminDetail.this).getPostAllComments(postid, getString(R.string.appkey)).enqueue(new Callback<List<PostCommentModel>>() {
            @Override
            public void onResponse(Call<List<PostCommentModel>> call, Response<List<PostCommentModel>> response) {
                if (response.isSuccessful()) {
                    Log.d("AdminDetail>>>", "1");
                    commentList = response.body();
                    setRecyclerView();
                } else {
                    Log.d("AdminDetail>>>", "2");
                }
            }

            @Override
            public void onFailure(Call<List<PostCommentModel>> call, Throwable t) {
                Log.d("AdminDetail>>>", "3: " + t.getMessage());
            }
        });
    }

    private void setRecyclerView() {
        adapter = new AdminDetailCommentAdapter(commentList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminDetail.this));
        recyclerView.setAdapter(adapter);
    }

}
