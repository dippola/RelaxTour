package com.dippola.relaxtour.community.main.write;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.Test;
import com.dippola.relaxtour.community.main.CommunityMain;
import com.dippola.relaxtour.community.signIn.CommunityProfileCreate;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainModelDetail;
import com.dippola.relaxtour.retrofit.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityWrite extends AppCompatActivity {

    private int FROM_GALLERY = 1;
    public static int FROM_LIST = 2;

    private NestedScrollView scrollView;
    private Button goback, ok, addshare;
    private EditText title, body;
    private ConstraintLayout addimg;
    private TextView bottomtext;
    public static TextView imagecount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WriteImageAdapter adapter;
    private List<Uri> urllist = new ArrayList<>();
    public static RelativeLayout load;
    private TextView loadtext;

    //set sharelist
    private TextView shereText, listtitle, listcount;
    private CheckBox uandd;
    private ConstraintLayout favlayout;
    private RecyclerView sharelist;
    private Button sharecancel;
    private List<FavListItem> favListItems = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_write);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int y = (int)(size.y * 0.3);
        int x = (int)(size.x * 0.2);

        setInit(y, x);
        setInitShareList();
    }

    private void setInit(int y, int x) {
        load = findViewById(R.id.community_write_load);
        loadtext = findViewById(R.id.community_write_load_text);
        scrollView = findViewById(R.id.community_write_scrollview);
        setScrollView();
        goback = findViewById(R.id.community_write_back);
        onClickGoBack();
        ok = findViewById(R.id.community_write_ok);
        title = findViewById(R.id.community_write_title);
        body = findViewById(R.id.community_write_body);
        addshare = findViewById(R.id.community_write_addshare);
        onClickAddShare();
        addimg = findViewById(R.id.community_write_addimg);
        onClickAddImg();
        imagecount = findViewById(R.id.community_write_imgcount);
        recyclerView = findViewById(R.id.community_write_recyclerview);
        bottomtext = findViewById(R.id.community_write_bottom_text);
        body.setMinHeight(y);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(x, x);
        ConstraintLayout.LayoutParams paramsToAdapter = new ConstraintLayout.LayoutParams(x, x);
        addimg.setLayoutParams(params);
        setRecyclerView(paramsToAdapter);
        onClickOk();
    }

    private void setInitShareList() {
        shereText = findViewById(R.id.community_write_share_text);
        listtitle = findViewById(R.id.community_write_share_title);
        listcount = findViewById(R.id.community_write_share_title_count);
        uandd = findViewById(R.id.community_write_share_uandd);
        favlayout = findViewById(R.id.community_write_share_title_layout1);
        sharelist = findViewById(R.id.community_write_share_recyclerview);
        sharecancel = findViewById(R.id.community_write_share_cancel);
        shereText.setVisibility(View.VISIBLE);
        nullShare();

        favlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharelist.getVisibility() == View.VISIBLE) {
                    collapse(sharelist);
                } else {
                    expand(sharelist);
                }
            }
        });

        sharecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favListItems.clear();
                nullShare();
            }
        });
    }

    private void nullShare() {
        shereText.setVisibility(View.VISIBLE);
        favlayout.setVisibility(View.GONE);
        sharecancel.setVisibility(View.GONE);
    }

    private void haveShare() {
        shereText.setVisibility(View.GONE);
        favlayout.setVisibility(View.VISIBLE);
        sharecancel.setVisibility(View.VISIBLE);
    }

    private void onClickOk() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().length() == 0) {
                    Toast.makeText(CommunityWrite.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                } else if (body.getText().toString().length() == 0) {
                    Toast.makeText(CommunityWrite.this, "Please enter a body", Toast.LENGTH_SHORT).show();
                } else {
                    load.setVisibility(View.VISIBLE);
                    MainModelDetail model = new MainModelDetail();
                    DatabaseHandler databaseHandler = new DatabaseHandler(CommunityWrite.this);
                    UserModel myProfile = databaseHandler.getUserModel();
                    model.setParent_user(myProfile.getId());
                    model.setNickname(myProfile.getNickname());
                    model.setUser_url(myProfile.getImageurl());
                    model.setTitle(title.getText().toString());
                    model.setBody(body.getText().toString());
                    String urls = "";
                    String rd = "";
                    String copyrd = "";
                    if (urllist.size() != 0) {
                        rd = rd(10);
                        copyrd = rd;
                        rd += "●";
                        for (int i = 0; i < urllist.size(); i++) {
                            if (i == 0) {
                                urls += rd;
                            }
                            urls += urllist.get(i).toString();
                            if (i != urllist.size() - 1) {
                                urls += "●";
                            }
                        }
                    }
                    model.setImageurl(urls);
                    String list = "";
                    if (favListItems.size() != 0) {
                        for (int i = 0; i < favListItems.size(); i++) {
                            list += favListItems.get(i).getPage() + "-" + favListItems.get(i).getPosition() + "-" + favListItems.get(i).getSeek();
                            if (i != favListItems.size() - 1) {
                                list += "●";
                            }
                        }
                    }
                    model.setList(list);

                    UploadService uploadService = new UploadService();
                    Intent intent = new Intent(CommunityWrite.this, uploadService.getClass());
                    if (Build.VERSION.SDK_INT >= 26) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    UploadService.upload(loadtext, CommunityWrite.this, CommunityWrite.this, urllist, copyrd, myProfile.getId(), model);
                }
            }
        });
    }

    public static void uploadToDjango(Activity activity, Context context, int id, MainModelDetail model) {
        RetrofitClient.getApiService().createMain(id, model).enqueue(new Callback<MainModelDetail>() {
            @Override
            public void onResponse(Call<MainModelDetail> call, Response<MainModelDetail> response) {
                if (response.isSuccessful()) {
                    Log.d("CommunityWrite>>>", "1: " + response.message());
                    Toast.makeText(context, "Post registration complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CommunityMain.class);
                    intent.putExtra("write", true);
                    activity.setResult(CommunityMain.FROM_WRITE, intent);
                    activity.finish();
                } else {
                    Toast.makeText(context, "The Internet connection was unstable and failed.\nPlease try again.\n" + response.message(), Toast.LENGTH_SHORT).show();
                }
                load.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MainModelDetail> call, Throwable t) {
                Toast.makeText(context, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickAddShare() {
        addshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityWrite.this, ShareListDialog.class);
                launcher.launch(intent);
            }
        });
    }

    private void setRecyclerView(ConstraintLayout.LayoutParams params) {
        adapter = new WriteImageAdapter(urllist, CommunityWrite.this, params);
        layoutManager = new LinearLayoutManager(CommunityWrite.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void onClickAddImg() {
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urllist.size() > 5) {
                    Toast.makeText(CommunityWrite.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    launcher.launch(intent);
                }
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    Intent data = result.getData();
                    if (data.getClipData() != null) {
                        ClipData clipData = result.getData().getClipData();
                        if (urllist.size() + clipData.getItemCount() > 5) {
                            Toast.makeText(CommunityWrite.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                if (!urllist.contains(imageUri.toString())) {
                                    urllist.add(imageUri);
                                }
                                Log.d("CommunityWrite>>>", "uri: " + imageUri);
                            }
                        }
                    } else {
                        Uri imageUri = data.getData();
                        urllist.add(imageUri);
                        Log.d("CommunityWrite>>>", "uri: " + imageUri);
                    }
                    setImage();
                }
            } else if (result.getResultCode() == FROM_LIST) {
                if (result.getData() != null) {
                    Log.d("CommunityWrite>>>", "from list: " + result.getData().getStringExtra("title"));
                    if (result.getData().getStringExtra("title") != null || !result.getData().getStringExtra("title").equals("")) {
                        favListItems.clear();
                        favListItems = new DatabaseHandler(CommunityWrite.this).getFavListItem(result.getData().getStringExtra("title"));
                        listtitle.setText(result.getData().getStringExtra("title"));
                        ShareListAdapter shareListAdapter = new ShareListAdapter(favListItems, CommunityWrite.this);
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(CommunityWrite.this);
                        sharelist.setLayoutManager(layoutManager1);
                        sharelist.setAdapter(shareListAdapter);
                        listcount.setText("[" + favListItems.size() + "]");
                        haveShare();
                    }
                }
            }
        }
    });

    private void setImage() {
        adapter.notifyDataSetChanged();
        imagecount.setText(urllist.size() + "/5");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = (int)(size.x * 0.3);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.width = x * urllist.size();
        recyclerView.setLayoutParams(params);
        recyclerView.requestLayout();
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
    private void onClickGoBack() {
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    public static String rd(int wordLength) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(wordLength);
        for(int i = 0; i < wordLength; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }
}
