package com.dippola.relaxtour.community.main.notification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Notification extends AppCompatActivity {

    public static final int FROM_DELETE_ALL_DIALOG = 100;

    private SharedPreferences sharedPreferences;

    private List<NotificationItem> list;
    private RecyclerView recyclerView;
    private Button back;
    private DatabaseHandler databaseHandler;
    private NotificationAdapter adapter;
    private ConstraintLayout zero;
    private TextView more;
    private LinearLayout deleteAll;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("haveNewNotification", false);
        editor.apply();

        setInit();
    }

    private void setInit() {
        databaseHandler = new DatabaseHandler(Notification.this);
        list = databaseHandler.getNotificationList(0, 3);
        refresh = findViewById(R.id.notification_reflesh);
        recyclerView = findViewById(R.id.notification_recyclerview);
        back = findViewById(R.id.notification_back);
        zero = findViewById(R.id.notification_null);
        more = findViewById(R.id.notification_more);
        if (list.size() == 0) {
            more.setVisibility(View.GONE);
        }
        deleteAll = findViewById(R.id.notification_toolbar_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (list.size() != 0) {
            zero.setVisibility(View.GONE);
            setRecyclerView();
        } else {
            recyclerView.setVisibility(View.GONE);
            zero.setVisibility(View.VISIBLE);
        }

        setReflesh();
        onClickMore();
        onClickDeleteAll();
    }

    private void setReflesh() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                list = databaseHandler.getNotificationList(0, 3);
                more.setTextColor(getResources().getColor(R.color.button_design_color));
                setRecyclerView();
                refresh.setRefreshing(false);
            }
        });
    }

    private void setRecyclerView() {
        RequestOptions userr = new RequestOptions();
        userr.transform(new CircleCrop());
        adapter = new NotificationAdapter(Notification.this, list, databaseHandler, recyclerView, zero, userr);
        adapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Notification.this));
        recyclerView.setAdapter(adapter);
        if (list.size() != 0) {
            more.setVisibility(View.VISIBLE);
        } else {
            more.setVisibility(View.GONE);
        }
    }

    private void onClickMore() {
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = list.size();
                int end = list.size() + 3;
                List<NotificationItem> addlist = databaseHandler.getNotificationList(start, end);
                if (addlist.size() != 0) {
                    list.addAll(addlist);
                    adapter.notifyItemRangeInserted(start, addlist.size());
                } else {
                    more.setTextColor(getResources().getColor(R.color.icon_in_edittext_color));
                }
            }
        });
    }

    private void onClickDeleteAll() {
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notification.this, DeleteAllDialog.class);
                launcher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == FROM_DELETE_ALL_DIALOG) {
                if (result.getData().getBooleanExtra("isDelete", false)) {
                    int listSize = list.size();
                    list.clear();
                    more.setVisibility(View.GONE);
                    databaseHandler.deleteNotificationAll();
                    if (adapter != null) {
                        adapter.notifyItemRangeRemoved(0, listSize);
                    }
                    zero.setVisibility(View.VISIBLE);
                }
            }
        }
    });
}
