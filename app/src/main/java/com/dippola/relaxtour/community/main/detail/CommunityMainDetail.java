package com.dippola.relaxtour.community.main.detail;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
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

    private ShimmerFrameLayout topLoad, bottomLoad;
    private ConstraintLayout topFinish, middleFinish, bottomFinish;
    private ConstraintLayout load_body;
    private NestedScrollView scrollView;
    private Button back, like, refresh;
    private RelativeLayout commentsend, commentsendload;
    private TextView title, nickname, date, viewcount, body, nullcomment, commentcount, likecount;
    public static TextView towho;
    private ImageView userimg, like2;
    private LinearLayout likebox, commentbox;
    private RecyclerView commentlist;
    public static EditText editComment;

    public static int towhoid;

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
        getData("");
    }

    private void setInit(int y) {
        topLoad = findViewById(R.id.community_main_detail_load);
        bottomLoad = findViewById(R.id.community_main_detail_comment_item_load);
        topFinish = findViewById(R.id.community_main_detail_load_top_finish);
        middleFinish = findViewById(R.id.community_main_detail_load_middle_finish);
        bottomFinish = findViewById(R.id.community_main_detail_load_bottom_finish);
        topFinish.setVisibility(View.GONE);
        middleFinish.setVisibility(View.GONE);
        bottomFinish.setVisibility(View.GONE);
        load_body = findViewById(R.id.community_main_detail_body_box_load);
        load_body.setMinHeight(y);
        scrollView = findViewById(R.id.community_main_detail_scrollview);
        back = findViewById(R.id.community_main_detail_backbtn);
        like = findViewById(R.id.community_main_detail_likebtn);
        commentsend = findViewById(R.id.community_main_detail_comment_ok_layout);
        commentsendload = findViewById(R.id.community_main_detail_comment_ok_load_layout);
        title = findViewById(R.id.community_main_detail_title);
        nickname = findViewById(R.id.community_main_detail_nickname);
        date = findViewById(R.id.community_main_detail_date);
        viewcount = findViewById(R.id.community_main_detail_view);
        body = findViewById(R.id.community_main_detail_body);
        body.setMinHeight(y);
        nullcomment = findViewById(R.id.community_main_detail_nullcomment);
        likecount = findViewById(R.id.community_main_detail_like2_count);
        userimg = findViewById(R.id.community_main_detail_userimg);
        like2 = findViewById(R.id.community_main_detail_like2);
        likebox = findViewById(R.id.community_main_detail_likebox);
        refresh = findViewById(R.id.community_main_refresh_button);
        commentbox = findViewById(R.id.community_main_detail_commentbox);
        commentcount = findViewById(R.id.community_main_detail_comment_count);
        commentlist = findViewById(R.id.community_main_detail_commentlist);
        editComment = findViewById(R.id.community_main_detail_editcomment);
        towho = findViewById(R.id.community_main_detail_towho);
        towhoid = 0;
        setScrollView();
        onClickOk();
        onClickRefresh();
    }

    private void onClickRefresh() {
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(CommunityMainDetail.this, R.anim.refresh_turn);
                refresh.startAnimation(animation);
                bottomLoad.setVisibility(View.VISIBLE);
                bottomFinish.setVisibility(View.GONE);
                getData("refresh");
            }
        });
    }

    private void onClickOk() {
        commentsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editComment.getText().toString().length() != 0) {
                    sendCommentLoad();
                    hideKeyboard(view);
                    sendComment();
                } else {
                    Toast.makeText(CommunityMainDetail.this, "Please enter the contents", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendComment() {
        MainCommentModel model = new MainCommentModel();
        model.setBody(editComment.getText().toString());
        if (towhoid != 0) {
            model.setTo_id(towhoid);
        }
        RetrofitClient.getApiService().createComment(id, new DatabaseHandler(CommunityMainDetail.this).getUserModel().getId(), model).enqueue(new Callback<MainCommentModel>() {
            @Override
            public void onResponse(Call<MainCommentModel> call, Response<MainCommentModel> response) {
                if (response.isSuccessful()) {
                    resetComments();
                    editComment.setText("");
                    towho.setText("");
                    towhoid = 0;
                    sendCommentFinished();
                    Toast.makeText(CommunityMainDetail.this, "Successful comment writing", Toast.LENGTH_SHORT).show();
                } else {
                    sendCommentFinished();
                    Toast.makeText(CommunityMainDetail.this, "Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainCommentModel> call, Throwable t) {
                sendCommentFinished();
                Toast.makeText(CommunityMainDetail.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetComments() {
        RetrofitClient.getApiService().getMain(id).enqueue(new Callback<MainModelDetail>() {
            @Override
            public void onResponse(Call<MainModelDetail> call, Response<MainModelDetail> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    scrollDown();
                }
            }

            @Override
            public void onFailure(Call<MainModelDetail> call, Throwable t) {

            }
        });
    }

    private void scrollDown() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void sendCommentLoad() {
        commentsendload.setVisibility(View.VISIBLE);
        commentsend.setVisibility(View.GONE);
        commentsend.setEnabled(false);
    }

    private void sendCommentFinished() {
        commentsend.setVisibility(View.VISIBLE);
        commentsendload.setVisibility(View.GONE);
        commentsend.setEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (getCurrentFocus() == editComment) {
                if (editComment.getText().toString().length() == 0) {
                    towho.setText("");
                    towho.setVisibility(View.GONE);
                    towhoid = 0;
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getData(String from) {
        if (from.equals("refresh")) {
            RetrofitClient.getApiService().getMainComment(id, 1).enqueue(new Callback<List<MainCommentModel>>() {
                @Override
                public void onResponse(Call<List<MainCommentModel>> call, Response<List<MainCommentModel>> response) {
                    if (response.isSuccessful()) {
                        List<MainCommentModel> commentModelList = response.body();
                        setComment(commentModelList.size(), commentModelList, from);
                    }
                }

                @Override
                public void onFailure(Call<List<MainCommentModel>> call, Throwable t) {

                }
            });
        } else {
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
            RetrofitClient.getApiService().getMainComment(id, 1).enqueue(new Callback<List<MainCommentModel>>() {
                @Override
                public void onResponse(Call<List<MainCommentModel>> call, Response<List<MainCommentModel>> response) {
                    if (response.isSuccessful()) {
                        List<MainCommentModel> commentModelList = response.body();
                        setComment(commentModelList.size(), commentModelList, "");
                    }
                }

                @Override
                public void onFailure(Call<List<MainCommentModel>> call, Throwable t) {

                }
            });
        }
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
        likecount.setText(String.valueOf(model.getLike()));
        topLoad.setVisibility(View.INVISIBLE);
        topFinish.setVisibility(View.VISIBLE);
        middleFinish.setVisibility(View.VISIBLE);
    }

    private void setComment(int size, List<MainCommentModel> list, String from) {
        if (size == 0) {
            nullcomment.setVisibility(View.VISIBLE);
            commentlist.setVisibility(View.GONE);
        } else {
            Log.d("CommentDatil>>>", "list size: " + list.size());
            commentcount.setText(String.valueOf(list.size()));
            nullcomment.setVisibility(View.GONE);
            adapter = new MainDetailCommentAdapter(list, CommunityMainDetail.this);
            commentlist.setLayoutManager(new LinearLayoutManager(CommunityMainDetail.this));
            commentlist.setAdapter(adapter);
            bottomLoad.setVisibility(View.GONE);
            commentlist.setVisibility(View.VISIBLE);
            bottomFinish.setVisibility(View.VISIBLE);

            if (from.equals("refresh")) {
                refresh.clearAnimation();
                Toast.makeText(CommunityMainDetail.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
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

    public static void showKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {
        if (commentsendload.getVisibility() == View.GONE){
            super.onBackPressed();
        }
    }
}
