package com.dippola.relaxtour.community.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dippola.relaxtour.NetworkStatus;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.community.main.notification.Notification;
import com.dippola.relaxtour.community.main.write.CommunityWrite;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.community.signIn.CommunitySignIn;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.Premium;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostModelView;
import com.dippola.relaxtour.retrofit.model.PostsViewWitPages;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMain extends AppCompatActivity {

    List<PostModelView> lists = new ArrayList<>();

    public static boolean isLoading;

    private SharedPreferences sharedPreferences;

    private DatabaseHandler databaseHandler;
    private FirebaseAuth auth;
    private SwipeRefreshLayout refresh;
    private ImageView authicon;
    private ProgressBar iconload;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout itemload;
    private ConstraintLayout pagebox;
    private FloatingActionButton fabmain;
    private ConstraintLayout fab1, fab2;
    private Button back;
    private NestedScrollView scrollView;
    private AppBarLayout appBarLayout;

    //tab layout
    private ConstraintLayout tabbox;
    private RadioGroup tabGroup;
    private RadioButton tab1, tab2, tab3;
    private ImageView tab1bg, tab2bg, tab3bg;

    //pagination
    private Button prev, next;
    private RelativeLayout p0, p1, p2, p3, p4, p5, p6;
    private ImageView m1, m2;
    private TextView t0, t1, t2, t3, t4, t5, t6;
    private int nowPage;

    //notification
    private ConstraintLayout notification;
    private ImageView notification_circle;

    private RelativeLayout fbg;

    private MainAdapter adapter;

    //bottom intent
    private TextView text1, text2;
    private ConstraintLayout btibox;

    public static final int FROM_SIGNIN = 105;
    public static final int FROM_CREATE_PROFILE = 106;
    public static final int FROM_AUTH = 107;
    public static final int FROM_WRITE = 108;
    public static final int FROM_DETAIL = 109;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        auth = FirebaseAuth.getInstance();
        databaseHandler = new DatabaseHandler(CommunityMain.this);

        setInit();
        onClickBackbtn();
        setOnClickTab();
        setRefresh();
        setImageAuthIcon();
        onClickAuth();
        nowPage = 1;
        loadCommunityAllFirst(nowPage);
        Animation show = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.fab_open);
        Animation close = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.fab_close);
        onClickFloating(show, close);
    }

    private void setInit() {
        back = findViewById(R.id.community_main_backbtn);
        refresh = findViewById(R.id.community_main_refresh);
        authicon = findViewById(R.id.community_main_auth);
        iconload = findViewById(R.id.community_main_iconload);
        recyclerView = findViewById(R.id.community_main_recyclerview);
        recyclerView.setVisibility(View.INVISIBLE);
        btibox = findViewById(R.id.community_main_bottom_intent_box);
        btibox.setVisibility(View.GONE);
        itemload = findViewById(R.id.community_main_load_item);
        itemload.startShimmer();
        pagebox = findViewById(R.id.community_main_page_box);
        pagebox.setVisibility(View.GONE);
        scrollView = findViewById(R.id.community_main_scrollview);
        appBarLayout = findViewById(R.id.community_main_appbarlayout);


        tabbox = findViewById(R.id.community_main_tab_box);
        tabGroup = findViewById(R.id.community_main_tab_group);
        tab1 = findViewById(R.id.community_main_tab1);
        tab1.setChecked(true);
        tab1.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_checked_color));
        tab2 = findViewById(R.id.community_main_tab2);
        tab3 = findViewById(R.id.community_main_tab3);
        tab1bg = findViewById(R.id.community_main_tab1bg);
        tab1bg.setVisibility(View.VISIBLE);
        tab2bg = findViewById(R.id.community_main_tab2bg);
        tab3bg = findViewById(R.id.community_main_tab3bg);

        fabmain = findViewById(R.id.community_main_fabmain);
        fab1 = findViewById(R.id.community_main_fab1);
        fab1.setVisibility(View.GONE);
        fab2 = findViewById(R.id.community_main_fab2);
        fab2.setVisibility(View.GONE);
        fbg = findViewById(R.id.community_main_floating_background);
        fbg.setVisibility(View.GONE);
        setPaginationInit();

        notification = findViewById(R.id.community_main_notification);
        notification_circle = findViewById(R.id.community_main_notification_circle);
        setNotificationVisible();
        setNotification();

        text1 = findViewById(R.id.community_main_bottom_intent1);
        text2 = findViewById(R.id.community_main_bottom_intent2);
        setOnClickBottomText();
    }

    private void setOnClickBottomText() {
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.private_policy_url))));
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.terms_and_conditions_url))));
            }
        });
    }

    private void setNotification() {
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "haveNewNotification",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        if (sharedPreferences.getBoolean("haveNewNotification", false)) {
            notification_circle.setVisibility(View.VISIBLE);
        } else {
            notification_circle.setVisibility(View.GONE);
        }
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityMain.this, Notification.class));
                notification_circle.setVisibility(View.GONE);
            }
        });
    }

    private void setPaginationInit() {
        prev = findViewById(R.id.community_main_page_item_prev);
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
    }

    private void onClickBackbtn() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setOnClickTab() {
        Animation show1 = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.tab_anim_1);
        Animation hide1 = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.tab_anim_2);
        Animation show2 = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.tab_anim_1);
        Animation hide2 = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.tab_anim_2);
        Animation show3 = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.tab_anim_1);
        Animation hide3 = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.tab_anim_2);
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
                    setAllPageUnChoice();
                    startLoad();
                    if (tab2.isChecked()) {
                        tab2.setChecked(false);
                    } else if (tab3.isChecked()) {
                        tab3.setChecked(false);
                    }
                    tab1.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_checked_color));
                    tab1bg.startAnimation(show1);
                    loadCommunityAll(1);
                } else {
                    tab1.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_unchecked_color));
                    tab1bg.startAnimation(hide1);
                }
            }
        });
        tab2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    setAllPageUnChoice();
                    startLoad();
                    if (tab1.isChecked()) {
                        tab1.setChecked(false);
                    } else if (tab3.isChecked()) {
                        tab3.setChecked(false);
                    }
                    tab2.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_checked_color));
                    tab2bg.startAnimation(show2);
                    loadCommunityCategory(1, "free");
                } else {
                    tab2.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_unchecked_color));
                    tab2bg.startAnimation(hide2);
                }
            }
        });
        tab3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    setAllPageUnChoice();
                    startLoad();
                    if (tab1.isChecked()) {
                        tab1.setChecked(false);
                    } else if (tab2.isChecked()) {
                        tab2.setChecked(false);
                    }
                    tab3.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_checked_color));
                    tab3bg.startAnimation(show3);
                    loadCommunityCategory(1, "qna");
                } else {
                    tab3.setTextColor(ContextCompat.getColor(CommunityMain.this, R.color.tab_text_unchecked_color));
                    tab3bg.startAnimation(hide3);
                }
            }
        });
    }

    private void setRefresh() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startReflesh();
            }
        });
    }

    private void startReflesh() {
        startLoad();
        if (tab1.isChecked()) {
            RetrofitClient.getApiService(CommunityMain.this).getMainPageAll(nowPage, getString(R.string.appkey)).enqueue(new Callback<PostsViewWitPages>() {
                @Override
                public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                    if (response.isSuccessful()) {
                        int beforeCount = lists.size();
                        lists.clear();
                        lists.addAll(response.body().getPosts());
                        isLoading = false;
//                        itemChange(beforeCount, lists.size());
                        setRecyclerView();
                        setPagination(response.body().getPages(), nowPage);
                        refresh.setRefreshing(false);
                        scrollView.smoothScrollTo(0, 0);
                        appBarLayout.setExpanded(true);
                    }
                }

                @Override
                public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                    requestError(call);
                }
            });
        } else if (tab2.isChecked()) {
            RetrofitClient.getApiService(CommunityMain.this).getMainPageCategory("free", nowPage, getString(R.string.appkey)).enqueue(new Callback<PostsViewWitPages>() {
                @Override
                public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                    if (response.isSuccessful()) {
                        int beforeCount = lists.size();
                        lists.clear();
                        lists.addAll(response.body().getPosts());
                        isLoading = false;
                        setRecyclerView();
                        setPagination(response.body().getPages(), nowPage);
                        refresh.setRefreshing(false);
                        scrollView.smoothScrollTo(0, 0);
                        appBarLayout.setExpanded(true);
                    }
                }

                @Override
                public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                    requestError(call);
                }
            });
        } else if (tab3.isChecked()) {
            RetrofitClient.getApiService(CommunityMain.this).getMainPageCategory("qna", nowPage, getString(R.string.appkey)).enqueue(new Callback<PostsViewWitPages>() {
                @Override
                public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                    if (response.isSuccessful()) {
                        int beforeCount = lists.size();
                        lists.clear();
                        lists.addAll(response.body().getPosts());
                        isLoading = false;
                        setRecyclerView();
                        setPagination(response.body().getPages(), nowPage);
                        refresh.setRefreshing(false);
                        scrollView.smoothScrollTo(0, 0);
                        appBarLayout.setExpanded(true);
                    }
                }

                @Override
                public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                    requestError(call);
                }
            });
        }
    }

    private void onClickFloating(Animation show, Animation close) {
        show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab1.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        close.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fabmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fbg.getVisibility() == View.GONE) {
                    fab1.startAnimation(show);
                    fab2.startAnimation(show);
                    fbg.setVisibility(View.VISIBLE);
                } else {
                    fab1.startAnimation(close);
                    fab2.startAnimation(close);
                    fbg.setVisibility(View.GONE);
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.getIsProUser() == 1) {
                    Toast.makeText(CommunityMain.this, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMain.this, Premium.class));
                    fab1.startAnimation(close);
                    fab2.startAnimation(close);
                    fbg.setVisibility(View.GONE);
                } else if (auth.getCurrentUser() == null) {
                    Toast.makeText(CommunityMain.this, "Login is required for community use.", Toast.LENGTH_SHORT).show();
                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
                    fab1.startAnimation(close);
                    fab2.startAnimation(close);
                    fbg.setVisibility(View.GONE);
                } else {
                    if (databaseHandler.getUserModel().getNickname() == null) {
                        Intent intent = new Intent(CommunityMain.this, CommunityProfileCreate.class);
                        intent.putExtra("from", "main");
                        launcher.launch(intent);
                    } else if (databaseHandler.getUserModel().getNickname().equals("") || databaseHandler.getUserModel().getNickname().equals("null")) {
                        Intent intent = new Intent(CommunityMain.this, CommunityProfileCreate.class);
                        intent.putExtra("from", "main");
                        launcher.launch(intent);
                    } else {
                        Intent intent = new Intent(CommunityMain.this, CommunityWrite.class);
                        intent.putExtra("category", "free");
                        launcher.launch(intent);
                        fab1.startAnimation(close);
                        fab2.startAnimation(close);
                        fbg.setVisibility(View.GONE);
                    }
                }
//                if (auth.getCurrentUser() == null) {
//                    Toast.makeText(CommunityMain.this, "Login is required for community use.", Toast.LENGTH_SHORT).show();
//                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
//                    fab1.startAnimation(close);
//                    fab2.startAnimation(close);
//                    fbg.setVisibility(View.GONE);
//                } else {
//                    Intent intent = new Intent(CommunityMain.this, CommunityWrite.class);
//                    intent.putExtra("category", "free");
//                    launcher.launch(intent);
//                    fab1.startAnimation(close);
//                    fab2.startAnimation(close);
//                    fbg.setVisibility(View.GONE);
//                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.getIsProUser() == 1) {
                    Toast.makeText(CommunityMain.this, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMain.this, Premium.class));
                    fab1.startAnimation(close);
                    fab2.startAnimation(close);
                    fbg.setVisibility(View.GONE);
                } else if (auth.getCurrentUser() == null) {
                    Toast.makeText(CommunityMain.this, "Login is required for community use.", Toast.LENGTH_SHORT).show();
                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
                    fab1.startAnimation(close);
                    fab2.startAnimation(close);
                    fbg.setVisibility(View.GONE);
                } else {
                    if (databaseHandler.getUserModel().getNickname() == null) {
                        Intent intent = new Intent(CommunityMain.this, CommunityProfileCreate.class);
                        intent.putExtra("from", "main");
                        launcher.launch(intent);
                    } else if (databaseHandler.getUserModel().getNickname().equals("") || databaseHandler.getUserModel().getNickname().equals("null")) {
                        Intent intent = new Intent(CommunityMain.this, CommunityProfileCreate.class);
                        intent.putExtra("from", "main");
                        launcher.launch(intent);
                    } else {
                        Intent intent = new Intent(CommunityMain.this, CommunityWrite.class);
                        intent.putExtra("category", "qna");
                        launcher.launch(intent);
                        fab1.startAnimation(close);
                        fab2.startAnimation(close);
                        fbg.setVisibility(View.GONE);
                    }
                }
//                if (auth.getCurrentUser() == null) {
//                    Toast.makeText(CommunityMain.this, "Login is required for community use.", Toast.LENGTH_SHORT).show();
//                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
//                    fab1.startAnimation(close);
//                    fab2.startAnimation(close);
//                    fbg.setVisibility(View.GONE);
//                } else {
//                    Intent intent = new Intent(CommunityMain.this, CommunityWrite.class);
//                    intent.putExtra("category", "qna");
//                    launcher.launch(intent);
//                    fab1.startAnimation(close);
//                    fab2.startAnimation(close);
//                    fbg.setVisibility(View.GONE);
//                }
            }
        });
        fbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab1.startAnimation(close);
                fab2.startAnimation(close);
                fbg.setVisibility(View.GONE);
            }
        });
    }

    private void setImageAuthIcon() {
        if (auth.getCurrentUser() == null) {
            Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
            iconload.setVisibility(View.GONE);
        } else {
            if (databaseHandler.getUserModel().getImageurl() == null || databaseHandler.getUserModel().getImageurl().length() == 0 || databaseHandler.getUserModel().getImageurl().equals("null")) {
                Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                iconload.setVisibility(View.GONE);
            } else {
                Glide.with(CommunityMain.this).load(databaseHandler.getUserModel().getImageurl()).transform(new CircleCrop()).into(authicon);
                iconload.setVisibility(View.GONE);
            }
        }
    }

    private void onClickAuth() {
        authicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHandler.getIsProUser() == 1) {
//                    Toast.makeText(CommunityMain.this, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMain.this, Premium.class));
                } else if (auth.getCurrentUser() == null) {
                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
                } else {
                    launcher.launch(new Intent(CommunityMain.this, CommunityAuth.class));
                }

//                if (auth.getCurrentUser() == null) {
//                    launcher.launch(new Intent(CommunityMain.this, CommunitySignIn.class));
//                } else {
//                    launcher.launch(new Intent(CommunityMain.this, CommunityAuth.class));
//                }

//                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/relax-tour-de785.appspot.com/o/community%2Fmain%2Fbmadydkdyq%2Fimage%253A12190?alt=media&token=83d309d5-d557-4321-ae16-b0de2ae88e68");
//                String name = uri.getLastPathSegment().split("/")[uri.getLastPathSegment().split("/").length - 1];
//                String documentFile = DocumentFile.fromSingleUri(CommunityMain.this, uri).getName();
//                Log.d("CommunityMain>>>", "file name from uri: " + name);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_SIGNIN) {//when sign up
                if (result.getData().getBooleanExtra("need_create_profile", false)) {
                    Intent intent = new Intent(CommunityMain.this, CommunityProfileCreate.class);
                    intent.putExtra("from", "main");
                    launcher.launch(intent);
                } else if (result.getData().getBooleanExtra("isSignIn", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService(CommunityMain.this).getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId(), getString(R.string.appkey));
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                    setNotificationVisible();
                }
            } else if (result.getResultCode() == FROM_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("isCreatePic", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService(CommunityMain.this).getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId(), getString(R.string.appkey));
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                } else {
                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                }
                setNotificationVisible();
            } else if (result.getResultCode() == FROM_AUTH) {
                if (result.getData().getBooleanExtra("isChangePic", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService(CommunityMain.this).getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId(), getString(R.string.appkey));
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                }
                if (result.getData().getBooleanExtra("isSignout", false)) {
                    if (auth.getCurrentUser() == null) {
                        iconload.setVisibility(View.VISIBLE);
                        Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
                        iconload.setVisibility(View.GONE);
                    }
                }
                if (result.getData().getBooleanExtra("isDeleteUser", false)) {
                    if (auth.getCurrentUser() == null) {
                        iconload.setVisibility(View.VISIBLE);
                        Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nulluser)).transform(new CircleCrop()).into(authicon);
                        iconload.setVisibility(View.GONE);
                    }
                }
                setNotificationVisible();
            } else if (result.getResultCode() == FROM_WRITE) {
                if (result.getData().getBooleanExtra("write", false)) {
                    startReflesh();
                }
            } else if (result.getResultCode() == FROM_DETAIL) {
                if (result.getData().getBooleanExtra("isCreatePic", false)) {
                    iconload.setVisibility(View.VISIBLE);
                    Call<List<UserModel>> call;
                    call = RetrofitClient.getApiService(CommunityMain.this).getUser(new DatabaseHandler(CommunityMain.this).getUserModel().getId(), getString(R.string.appkey));
                    call.enqueue(new Callback<List<UserModel>>() {
                        @Override
                        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(0).getImageurl().length() != 0) {
                                    Glide.with(CommunityMain.this).load(response.body().get(0).getImageurl()).transform(new CircleCrop()).into(authicon);
                                } else {
                                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                                }
                                iconload.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<UserModel>> call, Throwable t) {

                        }
                    });
                } else {
                    Glide.with(CommunityMain.this).load(getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(authicon);
                }
                setNotificationVisible();
            }
        }
    });

    private void loadCommunityAllFirst(int page) {
        RetrofitClient.getApiService(CommunityMain.this).getMainPageAll(page, getString(R.string.appkey)).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists.addAll(response.body().getPosts());
                    lists = response.body().getPosts();
                    isLoading = false;
                    setRecyclerView();
                    setPagination(response.body().getPages(), page);
                    recyclerView.setVisibility(View.VISIBLE);
                    btibox.setVisibility(View.VISIBLE);
                    pagebox.setVisibility(View.VISIBLE);
                    itemload.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                requestError(call);
            }
        });
    }

    private void loadCommunityAll(int page) {
        RetrofitClient.getApiService(CommunityMain.this).getMainPageAll(page, getString(R.string.appkey)).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    lists.clear();
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerView();
                    setPagination(response.body().getPages(), page);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                requestError(call);
            }
        });
    }
    private void loadCommunityCategory(int page, String category) {
        RetrofitClient.getApiService(CommunityMain.this).getMainPageCategory(category, page, getString(R.string.appkey)).enqueue(new Callback<PostsViewWitPages>() {
            @Override
            public void onResponse(Call<PostsViewWitPages> call, Response<PostsViewWitPages> response) {
                if (response.isSuccessful()) {
                    int beforeCount = lists.size();
                    lists.clear();
                    lists.addAll(response.body().getPosts());
                    isLoading = false;
                    setRecyclerView();
                    setPagination(response.body().getPages(), page);
                }
            }

            @Override
            public void onFailure(Call<PostsViewWitPages> call, Throwable t) {
                requestError(call);
            }
        });
    }

    private void itemChange(int beforeCount, int afterCount) {
        if (beforeCount == afterCount) {
            adapter.notifyItemRangeChanged(0, afterCount);
        } else if (beforeCount > afterCount) {
            adapter.notifyItemRangeRemoved(afterCount, (beforeCount - afterCount));
            adapter.notifyItemRangeChanged(0, afterCount);
        } else {
            adapter.notifyItemRangeInserted(beforeCount, (afterCount - beforeCount));
            adapter.notifyItemRangeChanged(0, afterCount);
        }
    }

    private void setRecyclerView() {
        RequestOptions userr = new RequestOptions();
        userr.transform(new CircleCrop());
        RequestOptions imgr = new RequestOptions();
        imgr.transform(new CenterCrop(), new RoundedCorners(20));
        adapter = new MainAdapter(CommunityMain.this, lists, userr, imgr, launcher);
        adapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommunityMain.this));
        recyclerView.setAdapter(adapter);
    }

    private void startLoad() {
        isLoading = true;
//        adapter.notifyItemRangeChanged(0, lists.size());
        adapter.notifyDataSetChanged();
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
                if (nowPage % 10 == 1 || nowPage % 10 == 6) {
                    nowPage -= 1;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 2 || nowPage % 10 == 7) {
                    nowPage -= 2;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 3 || nowPage % 10 == 8) {
                    nowPage -= 3;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 4 || nowPage % 10 == 9) {
                    nowPage -=4;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 5 || nowPage % 10 == 0) {
                    nowPage -=5;
                    setAllPageUnChoice();
                    startReflesh();
                }
            }
        });
        p0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = 1;
                setAllPageUnChoice();
                p0.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t0.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t1.getText().toString());
                setAllPageUnChoice();
                p1.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t1.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t2.getText().toString());
                setAllPageUnChoice();
                p2.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t2.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t3.getText().toString());
                setAllPageUnChoice();
                p3.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t3.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t4.getText().toString());
                setAllPageUnChoice();
                p4.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t4.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = Integer.parseInt(t5.getText().toString());
                setAllPageUnChoice();
                p5.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t5.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        p6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPage = totalPage;
                setAllPageUnChoice();
                p6.setBackground(getResources().getDrawable(R.drawable.page_round_choice));
                t6.setTextColor(getResources().getColor(R.color.button_design_color_2));
                startReflesh();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nowPage % 10 == 1 || nowPage % 10 == 6) {
                    nowPage += 5;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 2 || nowPage % 10 == 7) {
                    nowPage += 4;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 3 || nowPage % 10 == 8) {
                    nowPage += 3;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 4 || nowPage % 10 == 9) {
                    nowPage += 2;
                    setAllPageUnChoice();
                    startReflesh();
                } else if (nowPage % 10 == 5 || nowPage % 10 == 0) {
                    nowPage += 1;
                    setAllPageUnChoice();
                    startReflesh();
                }
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

    private void setNotificationVisible() {
        if (auth.getCurrentUser() == null) {
            notification.setVisibility(View.GONE);
        } else {
            notification.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences != null) {
            if (sharedPreferences.getBoolean("haveNewNotification", false)) {
                notification_circle.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (fbg.getVisibility() == View.VISIBLE) {
            Animation close = AnimationUtils.loadAnimation(CommunityMain.this, R.anim.fab_close);
            close.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fab1.setVisibility(View.GONE);
                    fab2.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            fab1.startAnimation(close);
            fab2.startAnimation(close);
            fbg.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    private void requestError(Call<PostsViewWitPages> call) {
        authicon.setEnabled(false);
        fabmain.setEnabled(false);
        tab1.setEnabled(false);
        tab2.setEnabled(false);
        tab3.setEnabled(false);
        if (NetworkStatus.getNetworkStatus(CommunityMain.this) == NetworkStatus.NO) {
            Toast.makeText(CommunityMain.this, "The internet is not connected. Please try again. Returning to the home screen shortly.", Toast.LENGTH_LONG).show();
        } else if (call.request().body() == null) {
            Toast.makeText(CommunityMain.this, "Unable to load data. Please try again. Returning to the home screen shortly.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CommunityMain.this, "For unknown reasons, data cannot be loaded. Please check your internet connection. Returning to the home screen shortly.", Toast.LENGTH_LONG).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2500);
    }
}