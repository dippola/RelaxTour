package com.dippola.relaxtour.community.main.detail;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainCommentModel;
import com.dippola.relaxtour.retrofit.model.MainModelDetail;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMainDetail extends AppCompatActivity {

    private int id;

    private ShimmerFrameLayout load;
    private ConstraintLayout load_body;
    private ConstraintLayout main;
    private ScrollView scrollView;
    private Button back, like, commentsend;
    private TextView title, nickname, date, viewcount, body, nullcomment, commentcount;
    private ImageView userimg, like2;
    private LinearLayout likebox, commentbox;
    private RecyclerView commentlist;
    private EditText editComment;

    private MainModelDetail model = new MainModelDetail();

    private MainDetailCommentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main_detail);

        id = getIntent().getIntExtra("parent_id", 0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int y = (int)(size.y * 0.25);

        setInit(y);
        getData();
    }

    private void setInit(int y) {
        load = findViewById(R.id.community_main_detail_load);
        load_body = findViewById(R.id.community_main_detail_body_box_load);
        load_body.setMinHeight(y);
        main = findViewById(R.id.communtiy_main_detail);
        main.setVisibility(View.GONE);
        scrollView = findViewById(R.id.community_main_detail_scrollview);
        back = findViewById(R.id.community_main_detail_backbtn);
        like = findViewById(R.id.community_main_detail_likebtn);
        commentsend = findViewById(R.id.community_main_detail_comment_ok);
        title = findViewById(R.id.community_main_detail_title);
        nickname = findViewById(R.id.community_main_detail_nickname);
        date = findViewById(R.id.community_main_detail_date);
        viewcount = findViewById(R.id.community_main_detail_view);
        body = findViewById(R.id.community_main_detail_body);
        body.setMinHeight(y);
        nullcomment = findViewById(R.id.community_main_detail_nullcomment);
        userimg = findViewById(R.id.community_main_detail_userimg);
        like2 = findViewById(R.id.community_main_detail_like2);
        likebox = findViewById(R.id.community_main_detail_likebox);
        commentbox = findViewById(R.id.community_main_detail_commentbox);
        commentcount = findViewById(R.id.community_main_detail_comment_count);
        commentlist = findViewById(R.id.community_main_detail_commentlist);
        editComment = findViewById(R.id.community_main_detail_editcomment);
        setScrollView();
    }

    private void getData() {
        RetrofitClient.getApiService().getMain(id).enqueue(new Callback<MainModelDetail>() {
            @Override
            public void onResponse(Call<MainModelDetail> call, Response<MainModelDetail> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    setData(model);
                }
            }

            @Override
            public void onFailure(Call<MainModelDetail> call, Throwable t) {

            }
        });
    }

    private void setData(MainModelDetail model) {
        title.setText(model.getTitle());
        if (model.getUser_url().length() != 0) {
            Glide.with(CommunityMainDetail.this).load(model.getUser_url()).transform(new CircleCrop()).into(userimg);
        } else {
            Glide.with(CommunityMainDetail.this).load(R.drawable.nullpic).transform(new CircleCrop()).into(userimg);
        }
        nickname.setText(model.getNickname());
        viewcount.setText(String.valueOf(model.getView()));
        body.setText(model.getBody());
        commentcount.setText(String.valueOf(model.getComment().size()));
        main.setVisibility(View.VISIBLE);
        load.setVisibility(View.GONE);

        setComment(model.getComment().size(), model.getComment());
    }

    private void setComment(int size, List<MainCommentModel> list) {
        if (size == 0) {
            nullcomment.setVisibility(View.VISIBLE);
            commentlist.setVisibility(View.GONE);
        } else {
            Log.d("CommentDatil>>>", "list size: " + list.size());
            nullcomment.setVisibility(View.GONE);
            adapter = new MainDetailCommentAdapter(list, CommunityMainDetail.this);
            commentlist.setLayoutManager(new LinearLayoutManager(CommunityMainDetail.this));
            commentlist.setAdapter(adapter);
            commentlist.setVisibility(View.VISIBLE);
        }
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
}
