package com.dippola.relaxtour.community.main.detail;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.dippola.relaxtour.retrofit.model.PostModelDetail;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMainDetail extends AppCompatActivity {

    private int id;

    private List<PostCommentModel> commentModelList = new ArrayList<>();

    private ShimmerFrameLayout topMiddleLoad, bottomLoad;
    private ConstraintLayout topFinish, middleFinish, bottomFinish, commentLayout;
    private ConstraintLayout load_body;
    private NestedScrollView scrollView;
    private Button back, like, refresh, refreshload, commentViewMore;
    private RelativeLayout commentsend, commentsendload;
    private TextView title, nickname, date, viewcount, body, nullcomment, commentcount, likecount;
    public static TextView towho;
    private ImageView userimg, like2;
    private LinearLayout likebox, commentbox;
    private RecyclerView commentlist;
    private ProgressBar commentMoreLoad;
    public static EditText editComment;

    //share list
    private ConstraintLayout listLayout;
    private TextView listTitle, listCount;
    private ImageView listUandd;
    private RecyclerView listRecyclerview;
    private Button listAddfav;
    private ShareListAdapter shareListAdapter;

    //image
    private ConstraintLayout imagebox, img1, img2, img3, img4, img5;
    private ImageView img1_1, img2_1, img2_2, img3_1, img3_2, img3_3, img4_1, img4_2, img4_3, img4_4, img5_1, img5_2, img5_3, img5_4, img5_5;
    private ProgressBar imgload1_1, imgload2_1, imgload2_2, imgload3_1, imgload3_2, imgload3_3, imgload4_1, imgload4_2, imgload4_3, imgload4_4, imgload5_1, imgload5_2, imgload5_3, imgload5_4, imgload5_5;

    public static int towhoid;

    private PostModelDetail model = new PostModelDetail();

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
        setListInit();
        setImageInit();
        getData("");
    }

    private void setInit(int y) {
        topMiddleLoad = findViewById(R.id.community_main_detail_load);
        bottomLoad = findViewById(R.id.community_main_detail_comment_item_load);
        topFinish = findViewById(R.id.community_main_detail_load_top_finish);
        middleFinish = findViewById(R.id.community_main_detail_load_middle_finish);
        bottomFinish = findViewById(R.id.community_main_detail_load_bottom_finish);
        topFinish.setVisibility(View.INVISIBLE);
        middleFinish.setVisibility(View.INVISIBLE);
        bottomFinish.setVisibility(View.INVISIBLE);
        load_body = findViewById(R.id.community_main_detail_body_box_load);
        load_body.setMinHeight(y);
        scrollView = findViewById(R.id.community_main_detail_scrollview);
        back = findViewById(R.id.community_main_detail_backbtn);
        like = findViewById(R.id.community_main_detail_likebtn);
        commentsend = findViewById(R.id.community_main_detail_comment_ok_layout);
        commentsendload = findViewById(R.id.community_main_detail_comment_ok_load_layout);
        refreshload = findViewById(R.id.community_main_refresh_button_load);
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
        commentLayout = findViewById(R.id.community_main_detail_comment_view_box);
        commentViewMore = findViewById(R.id.community_main_detail_comment_view_more);
        commentMoreLoad = findViewById(R.id.community_main_detail_comment_view_more_load);
        commentMoreLoad.setVisibility(View.GONE);
        editComment = findViewById(R.id.community_main_detail_editcomment);
        towho = findViewById(R.id.community_main_detail_towho);
        towhoid = 0;
        setScrollView();
        onClickOk();
        onClickRefresh();
        onClickViewMore();
    }

    private void setImageInit() {
        imagebox = findViewById(R.id.community_main_detail_image_box);
        img1 = findViewById(R.id.community_detail_image1);
        img2 = findViewById(R.id.community_detail_image2);
        img3 = findViewById(R.id.community_detail_image3);
        img4 = findViewById(R.id.community_detail_image4);
        img5 = findViewById(R.id.community_detail_image5);
        img1_1 = findViewById(R.id.community_detail_image1_1);
        img2_1 = findViewById(R.id.community_detail_image2_1);
        img2_2 = findViewById(R.id.community_detail_image2_2);
        img3_1 = findViewById(R.id.communtiy_detail_image3_1);
        img3_2 = findViewById(R.id.community_detail_image3_2);
        img3_3 = findViewById(R.id.community_detail_image3_3);
        img4_1 = findViewById(R.id.community_detail_image4_1);
        img4_2 = findViewById(R.id.community_detail_image4_2);
        img4_3 = findViewById(R.id.community_detail_image4_3);
        img4_4 = findViewById(R.id.community_detail_image4_4);
        img5_1 = findViewById(R.id.community_detail_image5_1);
        img5_2 = findViewById(R.id.community_detail_image5_2);
        img5_3 = findViewById(R.id.community_detail_image5_3);
        img5_4 = findViewById(R.id.community_detail_image5_4);
        img5_5 = findViewById(R.id.community_detail_image5_5);
        imgload1_1 = findViewById(R.id.community_detail_image_load_1_1);
        imgload2_1 = findViewById(R.id.community_detail_image_load_2_1);
        imgload2_2 = findViewById(R.id.community_detail_image_load_2_2);
        imgload3_1 = findViewById(R.id.community_detail_image_load_3_1);
        imgload3_2 = findViewById(R.id.community_detail_image_load_3_2);
        imgload3_3 = findViewById(R.id.community_detail_image_load_3_3);
        imgload4_1 = findViewById(R.id.community_detail_image_load_4_1);
        imgload4_2 = findViewById(R.id.community_detail_image_load_4_2);
        imgload4_3 = findViewById(R.id.community_detail_image_load_4_3);
        imgload4_4 = findViewById(R.id.community_detail_image_load_4_4);
        imgload5_1 = findViewById(R.id.community_detail_image_load_5_1);
        imgload5_2 = findViewById(R.id.community_detail_image_load_5_2);
        imgload5_3 = findViewById(R.id.community_detail_image_load_5_3);
        imgload5_4 = findViewById(R.id.community_detail_image_load_5_4);
        imgload5_5 = findViewById(R.id.community_detail_image_load_5_5);
    }

    private void onClickViewMore() {
        commentViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentMoreLoad.setVisibility(View.VISIBLE);
                int lastid = commentModelList.get(commentModelList.size() - 1).getId();
                int beforeSize = commentModelList.size();
                RetrofitClient.getApiService().getMainCommentMore(id, lastid).enqueue(new Callback<List<PostCommentModel>>() {
                    @Override
                    public void onResponse(Call<List<PostCommentModel>> call, Response<List<PostCommentModel>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().size() != 0) {
                                int addSize = commentModelList.size() + response.body().size() - 1;
                                commentModelList.addAll(response.body());
                                adapter.notifyItemRangeChanged(beforeSize, addSize);
                            }
                        }
                        commentMoreLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<PostCommentModel>> call, Throwable t) {
                        commentMoreLoad.setVisibility(View.GONE);
                        Toast.makeText(CommunityMainDetail.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setListInit() {
        listLayout = findViewById(R.id.community_main_detail_favlist_box);
        listTitle = findViewById(R.id.community_main_detail_list_layout_title);
        listCount = findViewById(R.id.community_main_detail_list_layout_count);
        listUandd = findViewById(R.id.community_main_detail_list_layout_uandd);
        listRecyclerview = findViewById(R.id.community_main_detail_list_layout_recyclerview);
        listAddfav = findViewById(R.id.community_main_detail_list_layout_addfav);
    }

    private void onClickRefresh() {
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(CommunityMainDetail.this, R.anim.refresh_turn);
                refreshload.startAnimation(animation);
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
        PostCommentModel model = new PostCommentModel();
        model.setBody(editComment.getText().toString());
        if (towhoid != 0) {
            Log.d("MainDetail>>>", "1");
            model.setTo_id(towhoid);
        }
        RetrofitClient.getApiService().createComment(id, new DatabaseHandler(CommunityMainDetail.this).getUserModel().getId(), model).enqueue(new Callback<PostCommentModel>() {
            @Override
            public void onResponse(Call<PostCommentModel> call, Response<PostCommentModel> response) {
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
            public void onFailure(Call<PostCommentModel> call, Throwable t) {
                sendCommentFinished();
                Toast.makeText(CommunityMainDetail.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetComments() {
        RetrofitClient.getApiService().getMain(id).enqueue(new Callback<PostModelDetail>() {
            @Override
            public void onResponse(Call<PostModelDetail> call, Response<PostModelDetail> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    scrollDown();
                }
            }

            @Override
            public void onFailure(Call<PostModelDetail> call, Throwable t) {

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
        commentsend.setVisibility(View.INVISIBLE);
        commentsend.setEnabled(false);
    }

    private void sendCommentFinished() {
        commentsend.setVisibility(View.VISIBLE);
        commentsendload.setVisibility(View.GONE);
        commentsend.setEnabled(true);
        Animation animation = AnimationUtils.loadAnimation(CommunityMainDetail.this, R.anim.refresh_turn);
        refreshload.startAnimation(animation);
        bottomLoad.setVisibility(View.VISIBLE);
        bottomFinish.setVisibility(View.GONE);
        getData("refresh");
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
        RetrofitClient.getApiService().getMain(id).enqueue(new Callback<PostModelDetail>() {
            @Override
            public void onResponse(Call<PostModelDetail> call, Response<PostModelDetail> response) {
                if (response.isSuccessful()) {
                    model = response.body();
                    setData(model);
                }
            }

            @Override
            public void onFailure(Call<PostModelDetail> call, Throwable t) {

            }
        });
        RetrofitClient.getApiService().getMainComment(id, 1).enqueue(new Callback<List<PostCommentModel>>() {
            @Override
            public void onResponse(Call<List<PostCommentModel>> call, Response<List<PostCommentModel>> response) {
                if (response.isSuccessful()) {
                    commentModelList = response.body();
                    setComment(commentModelList.size(), from);
                }
            }

            @Override
            public void onFailure(Call<List<PostCommentModel>> call, Throwable t) {

            }
        });
    }

    private void setData(PostModelDetail model) {
        if (String.valueOf(model.getList()).length() != 0) {
            setList(model.getList());
        }
        title.setText(model.getTitle());
        if (model.getUser_url().length() != 0) {
            Glide.with(CommunityMainDetail.this).load(model.getUser_url()).transform(new CircleCrop()).into(userimg);
        } else {
            Glide.with(CommunityMainDetail.this).load(R.drawable.nullpic).transform(new CircleCrop()).into(userimg);
        }
        if (!String.valueOf(model.getImageurl()).equals("")) {
            setImages(String.valueOf(model.getImageurl()));
        }
        nickname.setText(model.getNickname());
        viewcount.setText(String.valueOf(model.getView()));
        body.setText(model.getBody());
        likecount.setText(String.valueOf(model.getLike()));
        topFinish.setVisibility(View.VISIBLE);
        middleFinish.setVisibility(View.VISIBLE);
        topMiddleLoad.setVisibility(View.INVISIBLE);
        commentcount.setText(String.valueOf(model.getCommentcount()));
    }

    private void setImages(String images) {
        String image[] = images.split("●");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CenterCrop(), new RoundedCorners(40));
        if (image.length == 2) {
            img1.setVisibility(View.VISIBLE);
            Glide.with(img1_1).load(image[1]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload1_1.setVisibility(View.GONE);
                    return false;
                }
            }).into(img1_1);
        } else if (image.length == 3) {
            img2.setVisibility(View.VISIBLE);
            Glide.with(img2_1).load(image[1]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload2_1.setVisibility(View.GONE);
                    return false;
                }
            }).into(img2_1);
            Glide.with(img2_2).load(image[2]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload2_2.setVisibility(View.GONE);
                    return false;
                }
            }).into(img2_2);
        } else if (image.length == 4) {
            img3.setVisibility(View.VISIBLE);
            Glide.with(img3_1).load(image[1]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload3_1.setVisibility(View.GONE);
                    return false;
                }
            }).into(img3_1);
            Glide.with(img3_2).load(image[2]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload3_2.setVisibility(View.GONE);
                    return false;
                }
            }).into(img3_2);
            Glide.with(img3_3).load(image[3]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload3_3.setVisibility(View.GONE);
                    return false;
                }
            }).into(img3_3);
        } else if (image.length == 5) {
            img4.setVisibility(View.VISIBLE);
            Glide.with(img4_1).load(image[1]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload4_1.setVisibility(View.GONE);
                    return false;
                }
            }).into(img4_1);
            Glide.with(img4_2).load(image[2]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload4_2.setVisibility(View.GONE);
                    return false;
                }
            }).into(img4_2);
            Glide.with(img4_3).load(image[3]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload4_3.setVisibility(View.GONE);
                    return false;
                }
            }).into(img4_3);
            Glide.with(img4_4).load(image[4]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload4_4.setVisibility(View.GONE);
                    return false;
                }
            }).into(img4_4);
        } else if (image.length == 6) {
            img5.setVisibility(View.VISIBLE);
            Glide.with(img5_1).load(image[1]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload5_1.setVisibility(View.GONE);
                    return false;
                }
            }).into(img5_1);
            Glide.with(img5_2).load(image[2]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload5_2.setVisibility(View.GONE);
                    return false;
                }
            }).into(img5_2);
            Glide.with(img5_3).load(image[3]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload5_3.setVisibility(View.GONE);
                    return false;
                }
            }).into(img5_3);
            Glide.with(img5_4).load(image[4]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload5_4.setVisibility(View.GONE);
                    return false;
                }
            }).into(img5_4);
            Glide.with(img5_5).load(image[5]).apply(requestOptions).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    imgload5_5.setVisibility(View.GONE);
                    return false;
                }
            }).into(img5_5);
        }
    }

    private void setComment(int size, String from) {
        if (size == 0) {
            nullcomment.setVisibility(View.VISIBLE);
            commentLayout.setVisibility(View.GONE);
            bottomLoad.setVisibility(View.GONE);
            bottomFinish.setVisibility(View.VISIBLE);
        } else {
            Log.d("CommentDatil>>>", "list size: " + commentModelList.size());
            nullcomment.setVisibility(View.GONE);
            adapter = new MainDetailCommentAdapter(commentModelList, CommunityMainDetail.this);
            commentlist.setLayoutManager(new LinearLayoutManager(CommunityMainDetail.this));
            commentlist.setAdapter(adapter);
            bottomLoad.setVisibility(View.GONE);
            commentLayout.setVisibility(View.VISIBLE);
            bottomFinish.setVisibility(View.VISIBLE);

            if (from.equals("refresh")) {
                refreshload.clearAnimation();
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

    private void setList(String list) {
        String favList[] = String.valueOf(list).split("●");
        listTitle.setText(favList[0]);
        List<String> splitList = new ArrayList<>();
        for (int i = 0; i < favList.length; i++) {
            if (i != 0) {
                splitList.add(favList[i]);
            }
        }
        listCount.setText("[" + splitList.size() + "]");
        shareListAdapter  = new ShareListAdapter(splitList, new DatabaseHandler(CommunityMainDetail.this), CommunityMainDetail.this);
        listRecyclerview.setLayoutManager(new LinearLayoutManager(CommunityMainDetail.this));
        listRecyclerview.setAdapter(shareListAdapter);

        listLayout.setVisibility(View.VISIBLE);

        listLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listRecyclerview.getVisibility() == View.GONE) {
                    expand(listRecyclerview);
                    listUandd.setBackgroundResource(R.drawable.fav_page_item_up);
                } else {
                    collapse(listRecyclerview);
                    listUandd.setBackgroundResource(R.drawable.fav_page_item_down);
                }
            }
        });

        listAddfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
