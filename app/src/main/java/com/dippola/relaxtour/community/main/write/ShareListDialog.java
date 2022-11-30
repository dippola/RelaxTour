package com.dippola.relaxtour.community.main.write;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.pages.item.FavTitleItem;

import java.util.ArrayList;
import java.util.List;

public class ShareListDialog extends AppCompatActivity {

    private ConstraintLayout layout;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ShareTitleAdapter adapter;
    private DatabaseHandler databaseHandler;
    private List<FavTitleItem> titleitems = new ArrayList<>();
    private ConstraintLayout nulllist;
    private Button close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_share_list_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = (int)(size.x * 0.8);
        int y = (int)(size.y * 0.7);

        layout = findViewById(R.id.community_share_list_dialog_layout);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(x, y);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        layout.setLayoutParams(params);

        setInit();
    }

    private void setInit() {
        nulllist = findViewById(R.id.community_share_list_dialog_nulllist);
        recyclerView = findViewById(R.id.community_share_list_dialog_recyclerview);
        close = findViewById(R.id.community_share_list_dialog_2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        databaseHandler = new DatabaseHandler(ShareListDialog.this);
        titleitems = databaseHandler.getFavTitleList();
        Log.d("ShareListDialog>>>", "size: " + titleitems.size());
        if (titleitems.size() == 0) {
            nulllist.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            nulllist.setVisibility(View.GONE);
            adapter = new ShareTitleAdapter(titleitems, ShareListDialog.this);
            layoutManager = new LinearLayoutManager(ShareListDialog.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
