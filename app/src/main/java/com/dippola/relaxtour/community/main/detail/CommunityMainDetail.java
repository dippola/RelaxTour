package com.dippola.relaxtour.community.main.detail;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.dippola.relaxtour.community.bottomsheet_intent.DeleteCommunityDialog;
import com.dippola.relaxtour.community.bottomsheet_intent.EditComment;
import com.dippola.relaxtour.community.bottomsheet_intent.EditPost;
import com.dippola.relaxtour.community.bottomsheet_intent.Report;
import com.dippola.relaxtour.community.main.CommunityMain;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.community.translate.Language;
import com.dippola.relaxtour.community.translate.Translate;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.Premium;
import com.dippola.relaxtour.pages.item.PageItem;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.CommentAllList;
import com.dippola.relaxtour.retrofit.model.LikeUserListModel;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.dippola.relaxtour.retrofit.model.PostDetailWithComments;
import com.dippola.relaxtour.retrofit.model.PostModelDetail;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityMainDetail extends AppCompatActivity {

    public static final int FROM_DELETE = 301;
    public static final int FROM_EDITPOST = 302;
    public static final int FROM_CREATE_PROFILE = 303;
    public static final int FROM_EDIT_COMMENT = 304;

    private int id;
    private static int parent_user;

    private DatabaseHandler databaseHandler;
    private boolean willAddHit;

    //like
    private List<LikeUserListModel> likeUserList = new ArrayList<>();

    private List<PostCommentModel> commentModelList = new ArrayList<>();
    public static List<String> imageList = new ArrayList<>();

    public static boolean isCommentLoad;

    private int nowPage;

    private ShimmerFrameLayout topMiddleLoad, bottomLoad, likecommentboxLoad;
    private SwipeRefreshLayout refreshLayout;
    private ConstraintLayout topFinish, middleFinish, bottomFinish, commentLayout, changeConstraint;
    private RelativeLayout likecommentrefbox;
    private ConstraintLayout load_body;
    private NestedScrollView scrollView;
    private Button back, refresh, refreshload, commentViewMore;
    private CheckBox like;
    private RelativeLayout commentsend, commentsendload;
    private TextView title, nickname, date, viewcount, body, nullcomment, commentcount, likecount;
    public static TextView towho;
    private ImageView userimg, like2;
    private LinearLayout likebox, commentbox;
    private RecyclerView commentlist;
    private ProgressBar commentMoreLoad;
    public static EditText editComment;
    private ConstraintLayout firstCommentLoad1, firstCommentLoad2;

    //translate
    private LinearLayout transBox;
    private TextView transText;
    private ProgressBar transLoad;
    private boolean isTrans;

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

    private PostModelDetail postModel = new PostModelDetail();

    private MainDetailCommentAdapter adapter;

    //bottomsheet
    private Button more;
    private LinearLayout include;
    public static BottomSheetBehavior bottomSheetBehavior;
    private View outside;
    private static LinearLayout l1, l2, l3;
    public static String bottomFrom;
    public static int comment_parent_user, comment_parent_id, comment_index;
    public static String comment_body;

    private static FirebaseUser mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main_detail);

        id = getIntent().getIntExtra("parent_id", 0);
        parent_user = getIntent().getIntExtra("parent_user", 0);

        if (id == 0) {
            finish();
        }

        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        databaseHandler = new DatabaseHandler(CommunityMainDetail.this);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int y = (int) (size.y * 0.25);
        checkHits();
        setInit(y);
        setImageInit();
        setListInit();
        getData("");
    }

    private void checkHits() {
        String getHit = databaseHandler.getPostDate(id);
        String nowDate = getNowDate();
        if (getHit.equals("")) {
            willAddHit = true;
            databaseHandler.insertForHits(id, nowDate);
        } else {
            if (!getHit.equals(nowDate)) {
                willAddHit = true;
                databaseHandler.updateForHits(id, nowDate);
            }
        }
    }

    private void setInit(int y) {
        refreshLayout = findViewById(R.id.community_main_detail_refresh);
        topMiddleLoad = findViewById(R.id.community_main_detail_load);
        topMiddleLoad.setVisibility(View.VISIBLE);
        changeConstraint = findViewById(R.id.community_main_detail_for_change_constraint_layout);
        bottomLoad = findViewById(R.id.community_main_detail_comment_item_load);
        bottomLoad.setVisibility(View.VISIBLE);
        likecommentboxLoad = findViewById(R.id.community_main_detail_comment_item_load_when_loaded_first);
        likecommentboxLoad.setVisibility(View.GONE);
        topFinish = findViewById(R.id.community_main_detail_load_top_finish);
        likecommentrefbox = findViewById(R.id.community_main_detail_likecomment_box);
        middleFinish = findViewById(R.id.community_main_detail_load_middle_finish);
        bottomFinish = findViewById(R.id.community_main_detail_load_bottom_finish);
        topFinish.setVisibility(View.INVISIBLE);
        middleFinish.setVisibility(View.INVISIBLE);
        bottomFinish.setVisibility(View.INVISIBLE);
        load_body = findViewById(R.id.community_main_detail_body_box_load);
        load_body.setMinHeight(y);
        firstCommentLoad1 = findViewById(R.id.community_main_detail_comment_item_load1);
        firstCommentLoad2 = findViewById(R.id.community_main_detail_comment_item_load2);
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
        transBox = findViewById(R.id.community_main_detail_translate_box);
        transText = findViewById(R.id.community_main_detail_translate_text);
        transLoad = findViewById(R.id.community_main_detail_translate_load);
        setRefreshLayout();
        setBottomSheet();
        setScrollView();
        onClickOk();
        onClickRefresh();
        onClickViewMore();
        onClickLike();
        onClickBack();
        setTranslate();
    }

    private void setTranslate() {
        transBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTrans) {
                    transLoad.setVisibility(View.VISIBLE);
                    transBox.setEnabled(false);
                    SharedPreferences sharedPreferences;
                    try {
                        sharedPreferences = EncryptedSharedPreferences.create(
                                "languageTable",
                                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                                getApplicationContext(),
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                        );
                    } catch (GeneralSecurityException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    String lang = sharedPreferences.getString("nowLanguage", "device_language");
                    if (lang.equals("device_language")) {
                        lang = Locale.getDefault().getLanguage();
                    }
                    Translate translate = new Translate("auto", lang, body.getText().toString());
                    translate.setTranslateListener(new Translate.TranslateListener() {
                        @Override
                        public void onSuccess(String translatedText) {
                            transText.setText("view original");
                            transLoad.setVisibility(View.INVISIBLE);
                            body.setText(translatedText);
                            isTrans = true;
                            transBox.setEnabled(true);
                        }

                        @Override
                        public void onFailure(String ErrorText) {
                            Toast.makeText(CommunityMainDetail.this, "Translate failed: " + ErrorText, Toast.LENGTH_SHORT).show();
                            isTrans = false;
                            transLoad.setVisibility(View.INVISIBLE);
                            transBox.setEnabled(true);
                        }
                    });
                } else {
                    isTrans = false;
                    transText.setText("translate");
                    transLoad.setVisibility(View.INVISIBLE);
                    body.setText(postModel.getBody());
                }
            }
        });
    }

    private void onClickBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh();
            }
        });
    }

    private void startRefresh() {
        topMiddleLoad.setVisibility(View.VISIBLE);
        bottomLoad.setVisibility(View.VISIBLE);
        firstCommentLoad1.setVisibility(View.VISIBLE);
        firstCommentLoad2.setVisibility(View.VISIBLE);
        topFinish.setVisibility(View.INVISIBLE);
        middleFinish.setVisibility(View.INVISIBLE);
        bottomFinish.setVisibility(View.INVISIBLE);

        commentViewMore.setEnabled(true);

        refreshLayout.setRefreshing(false);
        getData("");
    }

    private void setBottomSheet() {
        bottomFrom = "";
        more = findViewById(R.id.community_main_detail_more);
        include = findViewById(R.id.community_main_detail_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(include);
        bottomSheetBehavior.setDraggable(true);
        outside = findViewById(R.id.community_main_detail_bottom_sheet_outside);
        l1 = findViewById(R.id.community_bottom_sheet1);
        l2 = findViewById(R.id.community_bottom_sheet2);
        l3 = findViewById(R.id.community_bottom_sheet3);

        outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    outside.setVisibility(View.GONE);
                }
            }
        });
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    outside.setVisibility(View.VISIBLE);
                } else {
                    outside.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new DatabaseHandler(CommunityMainDetail.this).getIsProUser() == 1) {
                    Toast.makeText(CommunityMainDetail.this, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMainDetail.this, Premium.class));
                } else if (mAuth == null) {
                    Toast.makeText(CommunityMainDetail.this, "Sign in is required.", Toast.LENGTH_SHORT).show();
                } else {
                    bottomFrom = "post";
                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        setBottomSheetBehavior(CommunityMainDetail.this);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomFrom.equals("post")) {
                    Intent intent = new Intent(CommunityMainDetail.this, EditPost.class);
                    intent.putExtra("id", id);
                    launcher.launch(intent);
                } else if (bottomFrom.equals("comment")) {
                    Intent intent = new Intent(CommunityMainDetail.this, EditComment.class);
                    intent.putExtra("body", comment_body);
                    intent.putExtra("comment_id", comment_parent_id);
                    intent.putExtra("post_id", id);
                    launcher.launch(intent);
                }
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DeleteCommunityDialog.class);
                intent.putExtra("delete", bottomFrom);
                intent.putExtra("id", id);
                if (bottomFrom.equals("comment")) {
                    intent.putExtra("commentid", comment_parent_id);
                }
                launcher.launch(intent);
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, Report.class);
                intent.putExtra("from", bottomFrom);
                intent.putExtra("postid", id);
                intent.putExtra("decuser", databaseHandler.getUserModel().getId());
                if (bottomFrom.equals("comment")) {
                    intent.putExtra("commentid", comment_parent_id);
                }
                startActivity(intent);
            }
        });
        GestureDetector detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                return false;
            }

            @Override
            public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                return false;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
        l1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return false;
            }
        });
        l2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return false;
            }
        });
        l3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    public static void setBottomSheetBehavior(Context context) {
        if (mAuth != null) {
            int myId = new DatabaseHandler(context).getUserModel().getId();
            if (bottomFrom.equals("post")) {
                if (myId != CommunityMainDetail.parent_user) {
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                } else {
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                }
            } else if (bottomFrom.equals("comment")) {
                if (myId != comment_parent_user) {
                    l1.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                } else {
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_DELETE) {
                if (result.getData().getBooleanExtra("isDelete", false)) {
                    if (result.getData().getStringExtra("from").equals("post")) {
                        Toast.makeText(CommunityMainDetail.this, "Post deletion complete", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (result.getData().getStringExtra("from").equals("comment")) {
                        commentModelList.remove(comment_index);
                        adapter.notifyItemRemoved(comment_index);
                        adapter.notifyDataSetChanged();
                        if (commentModelList.size() == 0) {
                            nullcomment.setVisibility(View.VISIBLE);
                            commentViewMore.setVisibility(View.GONE);
                        }
                        commentcount.setText(String.valueOf(Integer.parseInt(commentcount.getText().toString()) - 1));
                    }
                }
            } else if (result.getResultCode() == FROM_EDITPOST) {
                if (result.getData().getBooleanExtra("isEdit", false)) {
                    startRefresh();
                }
            } else if (result.getResultCode() == FROM_CREATE_PROFILE) {
                if (result.getData().getBooleanExtra("isCreate", false)) {
                    Intent intent = new Intent(CommunityMainDetail.this, CommunityMain.class);
                    intent.putExtra("isCreate", true);
                    intent.putExtra("isCreatePic", result.getData().getBooleanExtra("isCreatePic", false));
                    setResult(CommunityMain.FROM_DETAIL, intent);
                    finish();
                }
            } else if (result.getResultCode() == FROM_EDIT_COMMENT) {
                isCommentLoad = true;
                if (adapter != null) {
                    adapter.notifyItemRangeChanged(0, commentModelList.size());
                }

                Animation animation = AnimationUtils.loadAnimation(CommunityMainDetail.this, R.anim.refresh_turn);
                refreshload.startAnimation(animation);
//                bottomLoad.setVisibility(View.VISIBLE);
                likecommentboxLoad.setVisibility(View.VISIBLE);
                if (adapter == null) {
                    bottomFinish.setVisibility(View.GONE);
                } else {
                    likecommentrefbox.setVisibility(View.INVISIBLE);
                }
                getData("refresh");
            }
        }
    });

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
        imgSetOnClick();
    }

    private void imgSetOnClick() {
        img1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img1_1));
                intent.putExtra("clickposition", getClickImagePosition(img1_1));
                startActivity(intent);
            }
        });
        img2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img2_1));
                intent.putExtra("clickposition", getClickImagePosition(img2_1));
                startActivity(intent);
            }
        });
        img2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img2_2));
                intent.putExtra("clickposition", getClickImagePosition(img2_2));
                startActivity(intent);
            }
        });
        img3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img3_1));
                intent.putExtra("clickposition", getClickImagePosition(img3_1));
                startActivity(intent);
            }
        });
        img3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img3_2));
                intent.putExtra("clickposition", getClickImagePosition(img3_2));
                startActivity(intent);
            }
        });
        img3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img3_3));
                intent.putExtra("clickposition", getClickImagePosition(img3_3));
                startActivity(intent);
            }
        });
        img4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img4_1));
                intent.putExtra("clickposition", getClickImagePosition(img4_1));
                startActivity(intent);
            }
        });
        img4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img4_2));
                intent.putExtra("clickposition", getClickImagePosition(img4_2));
                startActivity(intent);
            }
        });
        img4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img4_3));
                intent.putExtra("clickposition", getClickImagePosition(img4_3));
                startActivity(intent);
            }
        });
        img4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img4_4));
                intent.putExtra("clickposition", getClickImagePosition(img4_4));
                startActivity(intent);
            }
        });
        img5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img5_1));
                intent.putExtra("clickposition", getClickImagePosition(img5_1));
                startActivity(intent);
            }
        });
        img5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img5_2));
                intent.putExtra("clickposition", getClickImagePosition(img5_2));
                startActivity(intent);
            }
        });
        img5_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img5_3));
                intent.putExtra("clickposition", getClickImagePosition(img5_3));
                startActivity(intent);
            }
        });
        img5_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img5_4));
                intent.putExtra("clickposition", getClickImagePosition(img5_4));
                startActivity(intent);
            }
        });
        img5_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityMainDetail.this, DetailImageViewer.class);
                intent.putExtra("imagecount", getImageCount(img5_5));
                intent.putExtra("clickposition", getClickImagePosition(img5_5));
                startActivity(intent);
            }
        });
    }

    private void onClickViewMore() {
        commentViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentMoreLoad.setVisibility(View.VISIBLE);
                int np = getNextCommentPage();
                int beforeSize = commentModelList.size();
                RetrofitClient.getApiService(CommunityMainDetail.this).getMainCommentMore(id, getNextCommentPage(), getString(R.string.appkey)).enqueue(new Callback<CommentWithPageWhenMore>() {
                    @Override
                    public void onResponse(Call<CommentWithPageWhenMore> call, Response<CommentWithPageWhenMore> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getComments().size() != 0) {
                                commentViewMore.setText("View More (" + np + "/" + String.valueOf(response.body().getPages()) + ")");
                                int addSize = commentModelList.size() + response.body().getComments().size() - 1;
                                commentModelList.addAll(response.body().getComments());
                                adapter.notifyItemRangeChanged(beforeSize, addSize);
                                nowPage += 1;
                                if (nowPage == response.body().getPages()) {
                                    commentViewMore.setEnabled(false);
                                }
                            }
                        }
                        commentMoreLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<CommentWithPageWhenMore> call, Throwable t) {
                        commentMoreLoad.setVisibility(View.GONE);
                        Toast.makeText(CommunityMainDetail.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private int getNextCommentPage() {
        int i = nowPage + 1;
        return i;
    }

    private void setListInit() {
        listLayout = findViewById(R.id.community_main_detail_favlist_box);
        listTitle = findViewById(R.id.community_main_detail_list_layout_title);
        listCount = findViewById(R.id.community_main_detail_list_layout_count);
        listUandd = findViewById(R.id.community_main_detail_list_layout_uandd);
        listRecyclerview = findViewById(R.id.community_main_detail_list_layout_recyclerview);
        listAddfav = findViewById(R.id.community_main_detail_list_layout_addfav);
    }

    private void onClickLike() {
        like.setEnabled(false);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new DatabaseHandler(CommunityMainDetail.this).getIsProUser() == 1) {
                    Toast.makeText(CommunityMainDetail.this, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMainDetail.this, Premium.class));
                } else if (mAuth == null) {
                    Toast.makeText(CommunityMainDetail.this, "Sign in is required.", Toast.LENGTH_SHORT).show();
                } else {
                    like.setEnabled(false);
                    int myId = databaseHandler.getUserModel().getId();
                    RetrofitClient.getApiService(CommunityMainDetail.this).setLike(id, myId, getString(R.string.appkey)).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                if (response.body().equals("add")) {
                                    int i = Integer.parseInt(likecount.getText().toString());
                                    likecount.setText(String.valueOf(i + 1));
                                } else if (response.body().equals("remove")) {
                                    int i = Integer.parseInt(likecount.getText().toString());
                                    likecount.setText(String.valueOf(i - 1));
                                }
                            } else {
                                if (like.isChecked()) {
                                    like.setChecked(false);
                                } else {
                                    like.setChecked(true);
                                }
                                Log.d("MainDetail>>>", "message2: " + response.message());
                                Toast.makeText(CommunityMainDetail.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                            like.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            if (like.isChecked()) {
                                like.setChecked(false);
                            } else {
                                like.setChecked(true);
                            }
                            like.setEnabled(true);
                            Log.d("MainDetail>>>", "message3: " + t.getMessage());
                            Toast.makeText(CommunityMainDetail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mAuth != null) {
                    if (b) {
                        like.setBackgroundResource(R.drawable.community_like_icon);
                    } else {
                        like.setBackgroundResource(R.drawable.like_icon);
                    }
                } else {
                    like.setBackgroundResource(R.drawable.like_icon);
                    like.setChecked(false);
                }
            }
        });
    }

    private void onClickRefresh() {
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCommentLoad = true;
                commentViewMore.setEnabled(true);
                if (adapter != null) {
                    adapter.notifyItemRangeChanged(0, commentModelList.size());
                }

                Animation animation = AnimationUtils.loadAnimation(CommunityMainDetail.this, R.anim.refresh_turn);
                refreshload.startAnimation(animation);
//                bottomLoad.setVisibility(View.VISIBLE);
                likecommentboxLoad.setVisibility(View.VISIBLE);
//                if (adapter == null) {
//                    Log.d("MainDetail>>>", "1");
//                    bottomFinish.setVisibility(View.GONE);
//                } else {
//                    Log.d("MainDetail>>>", "2");
//                    likecommentrefbox.setVisibility(View.INVISIBLE);
//                }
                likecommentrefbox.setVisibility(View.INVISIBLE);
                getData("refresh");
            }
        });
    }

    private void onClickOk() {
        commentsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new DatabaseHandler(CommunityMainDetail.this).getIsProUser() == 1) {
                    Toast.makeText(CommunityMainDetail.this, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommunityMainDetail.this, Premium.class));
                } else if (mAuth == null) {
                    Toast.makeText(CommunityMainDetail.this, "Sign in is required to post a comment.", Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHandler.getUserModel().getNickname() == null) {
                        Intent intent = new Intent(CommunityMainDetail.this, CommunityProfileCreate.class);
                        intent.putExtra("from", "detail");
                        launcher.launch(intent);
                    } else if (databaseHandler.getUserModel().getNickname().equals("") || databaseHandler.getUserModel().getNickname().equals("null")) {
                        Intent intent = new Intent(CommunityMainDetail.this, CommunityProfileCreate.class);
                        intent.putExtra("from", "detail");
                        launcher.launch(intent);
                    } else {
                        if (editComment.getText().toString().length() != 0) {
                            if (editComment.getText().toString().contains("●")) {
                                Toast.makeText(CommunityMainDetail.this, "'●' characters are not allowed.", Toast.LENGTH_SHORT).show();
                            } else {
                                sendCommentLoad();
                                hideKeyboard(view);
                                sendComment();
                            }
                        } else {
                            Toast.makeText(CommunityMainDetail.this, "Please enter the contents", Toast.LENGTH_SHORT).show();
                        }
                    }
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
        RetrofitClient.getApiService(CommunityMainDetail.this).createComment(id, new DatabaseHandler(CommunityMainDetail.this).getUserModel().getId(), model, getString(R.string.appkey)).enqueue(new Callback<PostCommentModel>() {
            @Override
            public void onResponse(Call<PostCommentModel> call, Response<PostCommentModel> response) {
                if (response.isSuccessful()) {
                    resetComments();
                    editComment.setText("");
                    towho.setVisibility(View.GONE);
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
        RetrofitClient.getApiService(CommunityMainDetail.this).getPostAllComments(id, getString(R.string.appkey)).enqueue(new Callback<CommentAllList>() {
            @Override
            public void onResponse(Call<CommentAllList> call, Response<CommentAllList> response) {
                if (response.isSuccessful()) {
                    commentModelList.clear();
                    commentModelList = response.body().getComments();
                    setComment(commentModelList.size(), "refresh");
                    scrollDown();
                    Log.d("MainDetail>>>", "1: " + response.body().getComments().size());
                } else {
                    Log.d("MainDetail>>>", "2");
                }
            }

            @Override
            public void onFailure(Call<CommentAllList> call, Throwable t) {
                Log.d("MainDetail>>>", "3: " + t.getMessage());
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

        isCommentLoad = true;
        commentViewMore.setEnabled(true);
        if (adapter != null) {
            adapter.notifyItemRangeChanged(0, commentModelList.size());
        }

        Animation animation = AnimationUtils.loadAnimation(CommunityMainDetail.this, R.anim.refresh_turn);
        refreshload.startAnimation(animation);
//                bottomLoad.setVisibility(View.VISIBLE);
        likecommentboxLoad.setVisibility(View.VISIBLE);
        if (adapter == null) {
            bottomFinish.setVisibility(View.GONE);
        } else {
            likecommentrefbox.setVisibility(View.INVISIBLE);
        }
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
        nowPage = 1;
        AddHitModel addHitModel = new AddHitModel();
        addHitModel.setWillAddHit(willAddHit);
        RetrofitClient.getApiService(CommunityMainDetail.this).getPost(id, addHitModel, getString(R.string.appkey)).enqueue(new Callback<PostDetailWithComments>() {
            @Override
            public void onResponse(Call<PostDetailWithComments> call, Response<PostDetailWithComments> response) {
                if (response.isSuccessful()) {
                    postModel = response.body().getPost();
                    parent_user = postModel.getParent_user();
                    setBottomSheetBehavior(CommunityMainDetail.this);
                    commentModelList = response.body().getComments();
                    likeUserList = response.body().getLikeuserlist();
                    commentViewMore.setText("View More (" + nowPage + "/" + String.valueOf(response.body().getCommentsPages()) + ")");
                    if (nowPage == response.body().getCommentsPages()) {
                        commentViewMore.setEnabled(false);
                    }
                    setData(postModel);
                    setComment(commentModelList.size(), from);
                }
            }

            @Override
            public void onFailure(Call<PostDetailWithComments> call, Throwable t) {
                Toast.makeText(CommunityMainDetail.this, "Please try again..\nerror: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(PostModelDetail model) {
        imageList.clear();
        if (String.valueOf(model.getList()).length() != 0) {
            setList(model.getList());
        }
        title.setText(model.getTitle());
        if (model.getUser_url().length() != 0) {
            Glide.with(getApplicationContext()).load(model.getUser_url()).transform(new CircleCrop()).into(userimg);
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.nullpic).transform(new CircleCrop()).into(userimg);
        }
        if (!String.valueOf(model.getImageurl()).equals("")) {
            String[] imgsplit = String.valueOf(model.getImageurl()).split("●");
            imageList.addAll(Arrays.asList(imgsplit).subList(1, imgsplit.length));
            setImages(imageList);
        }
        nickname.setText(model.getNickname());
        viewcount.setText(String.valueOf(model.getView()));
        date.setText(getDateResult(String.valueOf(model.getDate())));
        body.setText(model.getBody());
        likecount.setText(String.valueOf(model.getLike()));
        topFinish.setVisibility(View.VISIBLE);
        middleFinish.setVisibility(View.VISIBLE);
        topMiddleLoad.setVisibility(View.INVISIBLE);
        commentcount.setText(String.valueOf(model.getCommentcount()));

        if (mAuth != null) {
            if (checkLikeListContains(likeUserList)) {
                like.setChecked(true);
            } else {
                like.setChecked(false);
            }
        } else {
            like.setChecked(false);
        }
        like.setEnabled(true);

    }

    private boolean checkLikeListContains(List<LikeUserListModel> likeUserList) {
        boolean result = false;
        int myId = databaseHandler.getUserModel().getId();
        for (int i = 0; i < likeUserList.size(); i++) {
            if (likeUserList.get(i).getUser_ids() == myId) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void setImages(List<String> images) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CenterCrop(), new RoundedCorners(40));
        if (images.size() == 1) {
            img1.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(images.get(0)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
        } else if (images.size() == 2) {
            img2.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(images.get(0)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(1)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
        } else if (images.size() == 3) {
            img3.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(images.get(0)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(1)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(2)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
        } else if (images.size() == 4) {
            img4.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(images.get(0)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(1)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(2)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(3)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
        } else if (images.size() == 5) {
            img5.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(images.get(0)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(1)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(2)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(3)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
            Glide.with(getApplicationContext()).load(images.get(4)).apply(requestOptions).listener(new RequestListener<Drawable>() {
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
        isCommentLoad = false;
        if (size == 0) {
            nullcomment.setVisibility(View.VISIBLE);
            commentLayout.setVisibility(View.GONE);
            bottomLoad.setVisibility(View.GONE);
            likecommentboxLoad.setVisibility(View.GONE);
            bottomFinish.setVisibility(View.VISIBLE);
            likecommentrefbox.setVisibility(View.VISIBLE);
            adapter = null;
        } else {
            nullcomment.setVisibility(View.GONE);
            adapter = new MainDetailCommentAdapter(commentModelList, CommunityMainDetail.this, databaseHandler.getUserModel().getId(), mAuth, launcher, databaseHandler);
            commentlist.setLayoutManager(new LinearLayoutManager(CommunityMainDetail.this));
            commentlist.setAdapter(adapter);
            bottomLoad.setVisibility(View.GONE);
            likecommentboxLoad.setVisibility(View.GONE);
            commentLayout.setVisibility(View.VISIBLE);
            bottomFinish.setVisibility(View.VISIBLE);
            likecommentrefbox.setVisibility(View.VISIBLE);

            //if first load finished firstCommentLoad1 gone
            firstCommentLoad1.setVisibility(View.GONE);
            firstCommentLoad2.setVisibility(View.GONE);

            if (from.equals("refresh")) {
                refreshload.clearAnimation();
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

    public static void showKeyboard(View v, Context context) {
//        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager manager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        manager.showSoftInput(editComment, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    public void onBackPressed() {
        if (commentsendload.getVisibility() == View.GONE) {
            super.onBackPressed();
        } else {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                super.onBackPressed();
            }
        }
    }

    private void setList(String list) {
        String[] favList = String.valueOf(list).split("●");
        listTitle.setText(favList[0]);
        List<String> splitList = new ArrayList<>();
        for (int i = 0; i < favList.length; i++) {
            if (i != 0) {
                splitList.add(favList[i]);
            }
        }
        listCount.setText("[" + splitList.size() + "]");
        List<PageItem> allTrackTidList = databaseHandler.getAllTrackTidList();
        shareListAdapter = new ShareListAdapter(splitList, new DatabaseHandler(CommunityMainDetail.this), CommunityMainDetail.this, allTrackTidList);
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
                String list = "";
                for (int i = 0; i < favList.length; i++) {
                    if (i != 0) {
                        if (i == favList.length - 1) {
                            list += favList[i];
                        } else {
                            list += favList[i] + "●";
                        }
                    }
                }
                Intent intent = new Intent(CommunityMainDetail.this, AddFavAskDialog.class);
                intent.putExtra("title", favList[0]);
                intent.putExtra("list", list);
                startActivity(intent);
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
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private String getDateResult(String dateFromServer) {
        //3번째 글 2022-11-24 21:06
        String nowTime = getNowDate();
        String postTime = changeTime(dateFromServer);
        if (nowTime.split(" ")[0].equals(postTime.split(" ")[0])) {
            return postTime.split(" ")[1].split(":")[0] + ":" + postTime.split(" ")[1].split(":")[1];
        } else {
            return postTime.split(" ")[0];
        }
    }

    private String changeTime(String dateFromServer) {
        String[] cut = dateFromServer.split(" ");
        String[] cut1 = cut[1].split("\\.");
        String result = cut[0] + " " + cut1[0];
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oldFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormat.parse(result);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newFormat.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormat.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dueDateAsNormal;
    }

    private String getNowDate() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = format.format(mDate);
        return date;
    }

    private String changeTimeForHit(String dateFromServer) {
        Log.d("MainDetail>>>", "check: " + dateFromServer);
        String result = dateFromServer.split("\\.")[0];
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oldFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormat.parse(result);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            newFormat.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormat.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dueDateAsNormal;
    }

    private int getImageCount(ImageView i) {
        if (i == img1_1) {
            return 1;
        } else if (i == img2_1 || i == img2_2) {
            return 2;
        } else if (i == img3_1 || i == img3_2 || i == img3_3) {
            return 3;
        } else if (i == img4_1 || i == img4_2 || i == img4_3 || i == img4_4) {
            return 4;
        } else {
            return 5;
        }
    }

    private int getClickImagePosition(ImageView i) {
        if (i == img1_1 || i == img2_1 || i == img3_1 || i == img4_1 || i == img5_1) {
            return 1;
        } else if (i == img2_2 || i == img3_2 || i == img4_2 || i == img5_2) {
            return 2;
        } else if (i == img3_3 || i == img4_3 || i == img5_3) {
            return 3;
        } else if (i == img4_4 || i == img5_4) {
            return 4;
        } else {
            return 5;
        }
    }

}
