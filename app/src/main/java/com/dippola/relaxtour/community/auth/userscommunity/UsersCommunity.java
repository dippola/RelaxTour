package com.dippola.relaxtour.community.auth.userscommunity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostModelView;
import com.dippola.relaxtour.retrofit.model.PostsViewWitPages;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersCommunity extends AppCompatActivity {

    public static boolean isLoading;
    private DatabaseHandler databaseHandler;
    private int myId;

    private List<PostModelView> lists = new ArrayList<>();
    private List<UsersCommunityCommentModel> commentLists = new ArrayList<>();

    private SwipeRefreshLayout refresh;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout itemload;
    private ConstraintLayout pagebox;
    private Button back;
    private ConstraintLayout tabbox;
    private RadioButton tab1, tab2, tab3;
    private ImageView tab1bg, tab2bg, tab3bg;
    private Button prev, next;
    private RelativeLayout p0, p1, p2, p3, p4, p5, p6;
    private ImageView m1, m2;
    private TextView t0, t1, t2, t3, t4, t5, t6;

    private RelativeLayout load;
    private NestedScrollView scrollView;
    private AppBarLayout appBarLayout;

    private int nowPage;
    private String from;

    private UsersCommunityPostsAdapter adapter;
    private UsersCommunityCommentsAdapter commentAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_community);

        databaseHandler = new DatabaseHandler(UsersCommunity.this);
        myId = databaseHandler.getUserModel().getId();

        nowPage = 1;
        from = getIntent().getStringExtra("from");
        setInit();
        if (from.equals("post")) {
            loadPostAllFirst(nowPage);
        } else if (from.equals("comment")) {
            loadCommentAllFirst(nowPage);
            tabbox.setVisibility(View.GONE);
        } else if (from.equals("like")) {
            loadLikeAllFirst(nowPage);
            tabbox.setVisibility(View.GONE);
        }
        onClickBackBtn();
        setRefresh();
        setOnClickTab();
    }

    private void setInit() {
        refresh = findViewById(R.id.users_community_refresh);
        recyclerView = findViewById(R.id.users_community_recyclerview);
        recyclerView.setVisibility(View.INVISIBLE);
        itemload = findViewById(R.id.users_community_load_item);
        itemload.startShimmer();
        pagebox = findViewById(R.id.users_community_page_box);
        pagebox.setVisibility(View.GONE);
        back = findViewById(R.id.users_community_backbtn);
        tabbox = findViewById(R.id.users_community_tab_box);
        tab1 = findViewById(R.id.users_community_tab1);
        tab1.setChecked(true);
        tab1.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_checked_color));
        tab2 = findViewById(R.id.users_community_tab2);
        tab3 = findViewById(R.id.users_community_tab3);
        tab1bg = findViewById(R.id.users_community_tab1bg);
        tab1bg.setVisibility(View.VISIBLE);
        tab2bg = findViewById(R.id.users_community_tab2bg);
        tab3bg = findViewById(R.id.users_community_tab3bg);
        prev = findViewById(R.id.community_main_page_item_prev);
        next = findViewById(R.id.community_main_page_item_next);
        p0 = findViewById(R.id.community_main_page_item0);
        m1 = findViewById(R.id.community_main_page_item_more1);
        p1 = findViewById(R.id.community_main_page_item1);
        p2 = findViewById(R.id.community_main_page_item2);
        p3 = findViewById(R.id.community_main_page_item3);
        p4 = findViewById(R.id.community_main_page_item4);
        p5 = findViewById(R.id.community_main_page_item5);
        p6 = findViewById(R.id.community_main_page_item6);
        m2 = findViewById(R.id.community_main_page_item_more2);
        next = findViewById(R.id.community_main_page_item_next);
        t0 = findViewById(R.id.community_main_page_item0t);
        t1 = findViewById(R.id.community_main_page_item1t);
        t2 = findViewById(R.id.community_main_page_item2t);
        t3 = findViewById(R.id.community_main_page_item3t);
        t4 = findViewById(R.id.community_main_page_item4t);
        t5 = findViewById(R.id.community_main_page_item5t);
        t6 = findViewById(R.id.community_main_page_item6t);
        load = findViewById(R.id.users_community_load);
        scrollView = findViewById(R.id.users_community_scrollview);
        appBarLayout = findViewById(R.id.users_community_appbarlayout);
    }

    private void onClickBackBtn() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadPostAllFirst(int page) {
        RetrofitClient.getApiService().getUserCommunityPostsPage(myId, page).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerViewFromPost();
                    setPagination(response.body().getPages(), page);
                    recyclerView.setVisibility(View.VISIBLE);
                    pagebox.setVisibility(View.VISIBLE);
                    itemload.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

            }
        });
    }

    private void setRecyclerViewFromPost() {
        RequestOptions userr = new RequestOptions();
        userr.transform(new CircleCrop());
        RequestOptions imgr = new RequestOptions();
        imgr.transform(new CenterCrop(), new RoundedCorners(20));
        adapter = new UsersCommunityPostsAdapter(UsersCommunity.this, lists, userr, imgr);
        adapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UsersCommunity.this));
        recyclerView.setAdapter(adapter);
    }

    private void loadCommentAllFirst(int page) {
        RetrofitClient.getApiService().getUserCommentAll(myId, page).enqueue(new Callback<UserCommentWithPageModel>() {
            @Override
            public void onResponse(Call<UserCommentWithPageModel> call, Response<UserCommentWithPageModel> response) {
                if (response.isSuccessful()) {
                    commentLists.addAll(response.body().getList());
                    isLoading = false;
                    setRecyclerViewFromComment();
                    setPagination(response.body().getPages(), page);
                    recyclerView.setVisibility(View.VISIBLE);
                    pagebox.setVisibility(View.VISIBLE);
                    itemload.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserCommentWithPageModel> call, Throwable t) {

            }
        });
    }

    private void loadLikeAllFirst(int page) {
        RetrofitClient.getApiService().getUserCommunityLikePostsPage(myId, page).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerViewFromPost();
                    setPagination(response.body().getPages(), page);
                    recyclerView.setVisibility(View.VISIBLE);
                    pagebox.setVisibility(View.VISIBLE);
                    itemload.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

            }
        });
    }

    private void setRecyclerViewFromComment() {
        commentAdapter = new UsersCommunityCommentsAdapter(UsersCommunity.this, commentLists);
        commentAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UsersCommunity.this));
        recyclerView.setAdapter(commentAdapter);
    }

    private void setOnClickTab() {
        Animation show1 = AnimationUtils.loadAnimation(UsersCommunity.this, R.anim.tab_anim_1);
        Animation hide1 = AnimationUtils.loadAnimation(UsersCommunity.this, R.anim.tab_anim_2);
        Animation show2 = AnimationUtils.loadAnimation(UsersCommunity.this, R.anim.tab_anim_1);
        Animation hide2 = AnimationUtils.loadAnimation(UsersCommunity.this, R.anim.tab_anim_2);
        Animation show3 = AnimationUtils.loadAnimation(UsersCommunity.this, R.anim.tab_anim_1);
        Animation hide3 = AnimationUtils.loadAnimation(UsersCommunity.this, R.anim.tab_anim_2);
        show1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tab1bg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hide1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tab1bg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        show2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tab2bg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hide2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tab2bg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        show3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tab3bg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hide3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tab3bg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tab1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startLoad();
                    if (tab2.isChecked()) {
                        tab2.setChecked(false);
                    } else if (tab3.isChecked()) {
                        tab3.setChecked(false);
                    }
                    tab1.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_checked_color));
                    tab1bg.startAnimation(show1);
                    loadCommunityAll(1);
                } else {
                    tab1.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_unchecked_color));
                    tab1bg.startAnimation(hide1);
                }
            }
        });
        tab2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startLoad();
                    if (tab1.isChecked()) {
                        tab1.setChecked(false);
                    } else if (tab3.isChecked()) {
                        tab3.setChecked(false);
                    }
                    tab2.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_checked_color));
                    tab2bg.startAnimation(show2);
                    loadCommunityCategory(1, "free");
                } else {
                    tab2.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_unchecked_color));
                    tab2bg.startAnimation(hide2);
                }
            }
        });
        tab3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startLoad();
                    if (tab1.isChecked()) {
                        tab1.setChecked(false);
                    } else if (tab2.isChecked()) {
                        tab2.setChecked(false);
                    }
                    tab3.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_checked_color));
                    tab3bg.startAnimation(show3);
                    loadCommunityCategory(1, "qna");
                } else {
                    tab3.setTextColor(ContextCompat.getColor(UsersCommunity.this, R.color.tab_text_unchecked_color));
                    tab3bg.startAnimation(hide3);
                }
            }
        });
    }

    private void loadCommunityAll(int page) {
        RetrofitClient.getApiService().getUserCommunityPostsPage(myId, page).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists.clear();
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerViewFromPost();
                    setPagination(response.body().getPages(), page);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

            }
        });
    }

    private void loadCommunityCategory(int page, String category) {
        RetrofitClient.getApiService().getUserCommunityCategoryPage(myId, category, page).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    int beforeCount = lists.size();
                    lists.clear();
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerViewFromPost();
                    setPagination(response.body().getPages(), page);
                }
                Log.d("UsersCommunity>>>", "2: " + response);
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                Log.d("UsersCommunity>>>", "3: " + t.getMessage());
            }
        });
    }

    private void startLoad() {
        isLoading = true;
        if (!from.equals("comment")) {
//            adapter.notifyItemRangeChanged(0, lists.size());
            adapter.notifyDataSetChanged();
        }
    }

    private void setRefresh() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (from.equals("post")) {
                    startRefleshFromPost();
                } else if (from.equals("comment")) {
                    startRefleshFromComment();
                } else if (from.equals("like")) {
                    startRefleshFromLike();
                }
            }
        });
    }

    private void setRefreshInPage() {
        if (from.equals("post")) {
            startRefleshFromPost();
        } else if (from.equals("comment")) {
            load.setVisibility(View.VISIBLE);
            startRefleshFromComment();
        } else if (from.equals("like")) {
            startRefleshFromLike();
        }
    }

    private void startRefleshFromPost() {
        startLoad();
        if (tab1.isChecked()) {
            RetrofitClient.getApiService().getUserCommunityPostsPage(myId, nowPage).enqueue(new Callback<PostsViewWitPages>() {
                @Override
                public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                    if (response.isSuccessful()) {
                        lists.clear();
                        lists.addAll(response.body().getPosts());
                        isLoading = false;
                        setRecyclerViewFromPost();
                        refresh.setRefreshing(false);
                        scrollView.smoothScrollTo(0, 0);
                        appBarLayout.setExpanded(true);
                    }
                }

                @Override
                public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

                }
            });
        } else if (tab2.isChecked()) {
            RetrofitClient.getApiService().getUserCommunityCategoryPage(myId, "free", nowPage).enqueue(new Callback<PostsViewWitPages>() {
                @Override
                public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                    if (response.isSuccessful()) {
                        lists.clear();
                        lists.addAll(response.body().getPosts());
                        isLoading = false;
                        setRecyclerViewFromPost();
                        refresh.setRefreshing(false);
                        scrollView.smoothScrollTo(0, 0);
                        appBarLayout.setExpanded(true);
                    }
                }

                @Override
                public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

                }
            });
        } else if (tab3.isChecked()) {
            RetrofitClient.getApiService().getUserCommunityCategoryPage(myId, "qna", nowPage).enqueue(new Callback<PostsViewWitPages>() {
                @Override
                public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                    if (response.isSuccessful()) {
                        lists.clear();
                        lists.addAll(response.body().getPosts());
                        isLoading = false;
                        setRecyclerViewFromPost();
                        refresh.setRefreshing(false);
                        scrollView.smoothScrollTo(0, 0);
                        appBarLayout.setExpanded(true);
                    }
                }

                @Override
                public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

                }
            });
        }
    }

    private void startRefleshFromComment() {
        startLoad();
        RetrofitClient.getApiService().getUserCommentAll(myId, nowPage).enqueue(new Callback<UserCommentWithPageModel>() {
            @Override
            public void onResponse(Call<UserCommentWithPageModel> call, Response<UserCommentWithPageModel> response) {
                if (response.isSuccessful()) {
                    commentLists.clear();
                    commentLists.addAll(response.body().getList());
                    isLoading = false;
                    setRecyclerViewFromComment();
                    setPagination(response.body().getPages(), nowPage);
                    refresh.setRefreshing(false);
                    if (load.getVisibility() == View.VISIBLE) {
                        load.setVisibility(View.GONE);
                    }
                    scrollView.smoothScrollTo(0, 0);
                }
            }

            @Override
            public void onFailure(Call<UserCommentWithPageModel> call, Throwable t) {

            }
        });
    }

    private void startRefleshFromLike() {
        startLoad();
        RetrofitClient.getApiService().getUserCommunityLikePostsPage(myId, nowPage).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists.clear();
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerViewFromPost();
                    setPagination(response.body().getPages(), nowPage);
                    refresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {

            }
        });
    }

    private void setPagination(int totalPage, int nowPage) {
        allShowAgain();
        onClickPagination(totalPage);
        int rest = totalPage % 5;
        int position;
        boolean isLast = false;
        if (nowPage % 10 == 1 || nowPage % 10 == 6) {
            position = 1;
        } else if (nowPage % 10 == 2 || nowPage % 10 == 7) {
            position = 2;
        } else if (nowPage % 10 == 3 || nowPage % 10 == 8) {
            position = 3;
        } else if (nowPage % 10 == 4 || nowPage % 10 == 9) {
            position = 4;
        } else {
            position = 5;
        }//
        if (nowPage <= 5) {//position is in first block
            prev.setEnabled(false);
            p0.setVisibility(View.GONE);
            m1.setVisibility(View.GONE);

            if (totalPage <= 5) {//first block is last
                m2.setVisibility(View.GONE);
                p6.setVisibility(View.GONE);
                next.setEnabled(false);
                if (rest == 1) {
                    p2.setVisibility(View.GONE);
                    p3.setVisibility(View.GONE);
                    p4.setVisibility(View.GONE);
                    p5.setVisibility(View.GONE);
                } else if (rest == 2) {
                    p3.setVisibility(View.GONE);
                    p4.setVisibility(View.GONE);
                    p5.setVisibility(View.GONE);
                } else if (rest == 3) {
                    p4.setVisibility(View.GONE);
                    p5.setVisibility(View.GONE);
                } else if (rest == 4) {
                    p5.setVisibility(View.GONE);
                }
            } else {
                m2.setVisibility(View.VISIBLE);
                p6.setVisibility(View.VISIBLE);
                t6.setText(String.valueOf(totalPage));
            }
        } else {//position not in first block
            prev.setEnabled(true);
            p0.setVisibility(View.VISIBLE);
            m1.setVisibility(View.VISIBLE);
            t0.setText("1");
            if (totalPage % 10 == 1) {
                if (nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 2) {
                if (nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 3) {
                if (nowPage == (totalPage - 2) || nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 4) {
                if (nowPage == (totalPage - 3) || nowPage == (totalPage - 2) || nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 5) {
                if (nowPage == (totalPage - 4) || nowPage == (totalPage - 3) || nowPage == (totalPage - 2) || nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 6) {
                if (nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 7) {
                if (nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 8) {
                if (nowPage == (totalPage - 2) || nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 9) {
                if (nowPage == (totalPage - 3) || nowPage == (totalPage - 2) || nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            } else if (totalPage % 10 == 0) {
                if (nowPage == (totalPage - 4) || nowPage == (totalPage - 3) || nowPage == (totalPage - 2) || nowPage == (totalPage - 1) || nowPage == totalPage) {
                    isLast = true;
                }
            }

            if (isLast) {//position is in last block
                next.setEnabled(false);
                m2.setVisibility(View.GONE);
                p6.setVisibility(View.GONE);
                if (rest == 1) {
                    p2.setVisibility(View.GONE);
                    p3.setVisibility(View.GONE);
                    p4.setVisibility(View.GONE);
                    p5.setVisibility(View.GONE);
                } else if (rest == 2) {
                    p3.setVisibility(View.GONE);
                    p4.setVisibility(View.GONE);
                    p5.setVisibility(View.GONE);
                } else if (rest == 3) {
                    p4.setVisibility(View.GONE);
                    p5.setVisibility(View.GONE);
                } else if (rest == 4) {
                    p5.setVisibility(View.GONE);
                }
            } else {
                m2.setVisibility(View.VISIBLE);
                p6.setVisibility(View.VISIBLE);
                t6.setText(String.valueOf(totalPage));
            }//
        }//

        if (position == 1) {
            p1.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
            t1.setText(String.valueOf(nowPage));
            t2.setText(String.valueOf(nowPage + 1));
            t3.setText(String.valueOf(nowPage + 2));
            t4.setText(String.valueOf(nowPage + 3));
            t5.setText(String.valueOf(nowPage + 4));
            t1.setTextColor(getResources().getColor(R.color.button_design_color_2));
        } else if (position == 2) {
            p2.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
            t1.setText(String.valueOf(nowPage - 1));
            t2.setText(String.valueOf(nowPage));
            t3.setText(String.valueOf(nowPage + 1));
            t4.setText(String.valueOf(nowPage + 2));
            t5.setText(String.valueOf(nowPage + 3));
            t2.setTextColor(getResources().getColor(R.color.button_design_color_2));
        } else if (position == 3) {
            p3.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
            t1.setText(String.valueOf(nowPage - 2));
            t2.setText(String.valueOf(nowPage - 1));
            t3.setText(String.valueOf(nowPage));
            t4.setText(String.valueOf(nowPage + 1));
            t5.setText(String.valueOf(nowPage + 2));
            t3.setTextColor(getResources().getColor(R.color.button_design_color_2));
        } else if (position == 4) {
            p4.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
            t1.setText(String.valueOf(nowPage - 3));
            t2.setText(String.valueOf(nowPage - 2));
            t3.setText(String.valueOf(nowPage - 1));
            t4.setText(String.valueOf(nowPage));
            t5.setText(String.valueOf(nowPage + 1));
            t4.setTextColor(getResources().getColor(R.color.button_design_color_2));
        } else if (position == 5) {
            p5.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
            t1.setText(String.valueOf(nowPage - 4));
            t2.setText(String.valueOf(nowPage - 3));
            t3.setText(String.valueOf(nowPage - 2));
            t4.setText(String.valueOf(nowPage - 1));
            t5.setText(String.valueOf(nowPage));
            t5.setTextColor(getResources().getColor(R.color.button_design_color_2));
        }//
    }

    private void allShowAgain() {
        p0.setVisibility(View.VISIBLE);
        p1.setVisibility(View.VISIBLE);
        p2.setVisibility(View.VISIBLE);
        p3.setVisibility(View.VISIBLE);
        p4.setVisibility(View.VISIBLE);
        p5.setVisibility(View.VISIBLE);
        p6.setVisibility(View.VISIBLE);
        m1.setVisibility(View.VISIBLE);
        m2.setVisibility(View.VISIBLE);
        prev.setEnabled(true);
        next.setEnabled(true);
    }

    private void onClickPagination(int totalPage) {
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nowPage -= 1;
//                setAllPageUnChoice();
//                setRefreshInPage();
            }
        });
        p0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = 1;
                setAllPageUnChoice();
                p0.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t0.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t1.getText().toString());
                setAllPageUnChoice();
                p1.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t1.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t2.getText().toString());
                setAllPageUnChoice();
                p2.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t2.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t3.getText().toString());
                setAllPageUnChoice();
                p3.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t3.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t4.getText().toString());
                setAllPageUnChoice();
                p4.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t4.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t5.getText().toString());
                setAllPageUnChoice();
                p5.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t5.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        p6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = totalPage;
                setAllPageUnChoice();
                p6.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t6.setTextColor(getResources().getColor(R.color.button_design_color_2));
                setRefreshInPage();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nowPage += 1;
//                setAllPageUnChoice();
//                setRefreshInPage();
            }
        });
    }

    private void setAllPageUnChoice() {
        p0.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        p1.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        p2.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        p3.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        p4.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        p5.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        p6.setBackground(getResources().getDrawable(R.drawable.page_round_unchoice));
        t0.setTextColor(getResources().getColor(R.color.button_design_color));
        t1.setTextColor(getResources().getColor(R.color.button_design_color));
        t2.setTextColor(getResources().getColor(R.color.button_design_color));
        t3.setTextColor(getResources().getColor(R.color.button_design_color));
        t4.setTextColor(getResources().getColor(R.color.button_design_color));
        t5.setTextColor(getResources().getColor(R.color.button_design_color));
        t6.setTextColor(getResources().getColor(R.color.button_design_color));
    }
}
