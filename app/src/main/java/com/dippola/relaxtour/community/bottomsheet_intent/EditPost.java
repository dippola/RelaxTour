package com.dippola.relaxtour.community.bottomsheet_intent;

import android.content.ClipData;
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
import android.view.inputmethod.InputMethodManager;
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
import com.dippola.relaxtour.community.main.detail.AddHitModel;
import com.dippola.relaxtour.community.main.write.CommunityWrite;
import com.dippola.relaxtour.community.main.write.ShareListAdapter;
import com.dippola.relaxtour.community.main.write.ShareListDialog;
import com.dippola.relaxtour.community.main.write.UploadService;
import com.dippola.relaxtour.community.main.write.UriAndFileNameModel;
import com.dippola.relaxtour.community.main.write.WriteImageAdapter;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.PostDetailWithComments;
import com.dippola.relaxtour.retrofit.model.PostModelDetail;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPost extends AppCompatActivity {

    private int id;

    private int FROM_GALLERY = 1;
    public static int FROM_LIST = 2;

    private PostModelDetail model;

    private NestedScrollView scrollView;
    private Button goback, ok, addshare;
    private EditText title, body;
    private ConstraintLayout addimg, listbox;
    private View line5;
    private TextView bottomtext;
    public static TextView imagecount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditImageAdapter adapter;
    private List<UriAndFromModel> urllist;
    private String rd;
    public static List<Uri> deleteUrlList;
    public static List<UriAndFileNameModel> addUrlList;
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
        int y = (int) (size.y * 0.3);
        int x = (int) (size.x * 0.2);

        id = getIntent().getIntExtra("id", 0);

        urllist = new ArrayList<>();
        deleteUrlList = new ArrayList<>();
        addUrlList = new ArrayList<>();

        setInit(y, x);
        setInitShareList();
    }

    private void setInit(int y, int x) {
        model = new PostModelDetail();
        load = findViewById(R.id.community_write_load);
        load.setVisibility(View.VISIBLE);
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
        listbox = findViewById(R.id.community_write_playlist_box);
        line5 = findViewById(R.id.community_write_line5);

        imagecount = findViewById(R.id.community_write_imgcount);
        recyclerView = findViewById(R.id.community_write_recyclerview);
        bottomtext = findViewById(R.id.community_write_bottom_text);
        body.setMinHeight(y);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(x, x);
        ConstraintLayout.LayoutParams paramsToAdapter = new ConstraintLayout.LayoutParams(x, x);
        addimg.setLayoutParams(params);

        getData();

        setRecyclerView(paramsToAdapter);
        onClickOk();
    }

    private void getData() {
        AddHitModel willAddHit = new AddHitModel();
        willAddHit.setWillAddHit(false);
        RetrofitClient.getApiService().getPost(id, willAddHit, getString(R.string.appkey)).enqueue(new Callback<PostDetailWithComments>() {
            @Override
            public void onResponse(Call<PostDetailWithComments> call, Response<PostDetailWithComments> response) {
                if (response.isSuccessful()) {
                    model = response.body().getPost();
                    if (model.getCategory().equals("free")) {
                        listbox.setVisibility(View.VISIBLE);
                        line5.setVisibility(View.VISIBLE);
                    } else {
                        listbox.setVisibility(View.GONE);
                        line5.setVisibility(View.GONE);
                    }
                    title.setText(model.getTitle());
                    body.setText(model.getBody());
                    if (!model.getImageurl().equals("")) {
                        rd = model.getImageurl().split("●")[0];
                        for (int i = 0; i < model.getImageurl().split("●").length; i++) {
                            if (i != 0) {
                                Uri uri = Uri.parse(model.getImageurl().split("●")[i]);
                                UriAndFromModel uriAndFromModel = new UriAndFromModel(uri, "exist");
                                urllist.add(uriAndFromModel);
                            }
                        }
                        setImage();
                    } else {
                        rd = "";
                    }
                    if (!model.getList().equals("")) {
                        String[] favList = model.getList().split("●");
                        listtitle.setText(favList[0]);
                        listcount.setText("[" + (favList.length - 1) + "]");

//                        favListItems = new DatabaseHandler(EditPost.this).getFavListItem(result.getData().getStringExtra("title"));
                        List<FavListItem> favListItems = new ArrayList<>();
                        favListItems = new DatabaseHandler(EditPost.this).getListInCommunityShare(model.getList());

                        ShareListAdapter shareListAdapter = new ShareListAdapter(favListItems, EditPost.this);
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(EditPost.this);
                        sharelist.setLayoutManager(layoutManager1);
                        sharelist.setAdapter(shareListAdapter);
                        haveShare();
                    }
                    load.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PostDetailWithComments> call, Throwable t) {

            }
        });
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
                hideKeyboard(view);
                if (title.getText().toString().contains("●")) {
                    Toast.makeText(EditPost.this, "'●' characters are not allowed.", Toast.LENGTH_SHORT).show();
                } else if (title.getText().toString().length() == 0) {
                    Toast.makeText(EditPost.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                } else if (body.getText().toString().length() == 0) {
                    Toast.makeText(EditPost.this, "Please enter a body", Toast.LENGTH_SHORT).show();
                } else {
                    load.setVisibility(View.VISIBLE);
                    DatabaseHandler databaseHandler = new DatabaseHandler(EditPost.this);
                    UserModel myProfile = databaseHandler.getUserModel();
                    model.setTitle(title.getText().toString());
                    model.setBody(body.getText().toString());

                    if (rd.equals("")) {//원래 image없었음.
                        rd = rd(10);
                    }

                    String list = "";
                    if (favListItems.size() != 0) {
                        list += listtitle.getText().toString() + "●";
                        for (int i = 0; i < favListItems.size(); i++) {
                            list += favListItems.get(i).getPage() + "-" + favListItems.get(i).getPosition() + "-" + favListItems.get(i).getSeek();
                            if (i != favListItems.size() - 1) {
                                list += "●";
                            }
                        }
                    }
                    model.setList(list);

                    EditUploadService uploadService = new EditUploadService();
                    Intent intent = new Intent(EditPost.this, uploadService.getClass());
                    if (Build.VERSION.SDK_INT >= 26) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                    EditUploadService.deleteImage(id, loadtext, EditPost.this, EditPost.this, urllist, deleteUrlList, addUrlList, rd, myProfile.getId(), model, load);
                }
            }
        });
    }

    private void onClickAddShare() {
        addshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPost.this, ShareListDialog.class);
                launcher.launch(intent);
            }
        });
    }

    private void setRecyclerView(ConstraintLayout.LayoutParams params) {
        adapter = new EditImageAdapter(urllist, EditPost.this, params);
        layoutManager = new LinearLayoutManager(EditPost.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void onClickAddImg() {
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urllist.size() > 5) {
                    Toast.makeText(EditPost.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
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
                        if (urllist.size() + addUrlList.size() + clipData.getItemCount() > 5) {
                            Toast.makeText(EditPost.this, "You can add up to 5 photos.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                try {
                                    InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                                    int dataSize = fileInputStream.available();
                                    if (dataSize < 5242880) {
                                        if (!addUrlList.contains(imageUri.toString())) {
                                            UriAndFileNameModel uriAndFileNameModel = new UriAndFileNameModel(imageUri, imageUri.getLastPathSegment());
                                            addUrlList.add(uriAndFileNameModel);
                                            UriAndFromModel uriAndFromModel = new UriAndFromModel(imageUri, "new");
                                            urllist.add(uriAndFromModel);
                                        }
                                    } else {
                                        Toast.makeText(EditPost.this, "Maximum image capacity available for selection is 5 MB", Toast.LENGTH_SHORT).show();

                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        try {
                            InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                            int dataSize = fileInputStream.available();
                            if (dataSize < 5242880) {
                                Uri imageUri = data.getData();
                                UriAndFileNameModel uriAndFileNameModel = new UriAndFileNameModel(imageUri, imageUri.getLastPathSegment());
                                addUrlList.add(uriAndFileNameModel);
                                UriAndFromModel uriAndFromModel = new UriAndFromModel(imageUri, "new");
                                urllist.add(uriAndFromModel);
                            } else {
                                Toast.makeText(EditPost.this, "Maximum image capacity available for selection is 5 MB", Toast.LENGTH_SHORT).show();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    setImage();
                }
            } else if (result.getResultCode() == FROM_LIST) {
                if (result.getData() != null) {
                    Log.d("CommunityWrite>>>", "from list: " + result.getData().getStringExtra("title"));
                    if (result.getData().getStringExtra("title") != null || !result.getData().getStringExtra("title").equals("")) {
                        favListItems.clear();
                        favListItems = new DatabaseHandler(EditPost.this).getFavListItem(result.getData().getStringExtra("title"));
                        listtitle.setText(result.getData().getStringExtra("title"));
                        ShareListAdapter shareListAdapter = new ShareListAdapter(favListItems, EditPost.this);
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(EditPost.this);
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
        int x = (int) (size.x * 0.3);
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
        uandd.setBackgroundResource(R.drawable.fav_page_item_up);
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
        uandd.setBackgroundResource(R.drawable.fav_page_item_down);
    }

    public static String rd(int wordLength) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(wordLength);
        for (int i = 0; i < wordLength; i++) {
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

    private void hideKeyboard(View v) {
        InputMethodManager manager = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
