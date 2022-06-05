package com.dippola.relaxtour.setting;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.pages.item.PageItem;

import java.io.File;
import java.util.ArrayList;

public class StorageManageDialog extends AppCompatActivity {

    RecyclerView recyclerView;
    public static RelativeLayout nullScreen;
    public static RelativeLayout progressBar;
    ArrayList<PageItem> list;
    StorageManageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_manage_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = (int) (size.x * 0.9);
        int y = (int) (size.y * 0.8);
        getWindow().setLayout(x, y);

        databaseHandler = new DatabaseHandler(StorageManageDialog.this);

        setInit();
    }

    private void setInit() {
        recyclerView = findViewById(R.id.storage_manage_dialog_recyclerview);
        nullScreen = findViewById(R.id.storage_manage_dialog_null);
        progressBar = findViewById(R.id.storage_manage_dialog_progressbar);
        progressBar.setClickable(false);
        recyclerView.setVisibility(View.GONE);
        nullScreen.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
        getStorageList();
    }

    private void getStorageList() {
        list = new ArrayList<>();
        String path = getApplicationInfo().dataDir + "/cache/";
        File file = new File(path);
        File[] files = file.listFiles();

        if (files.length != 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains("audio")) {
//                Log.d("StorageManageDialog>>>", "get file name: " + files.)
                    String pnp = files[i].getName().substring(5, 9);
                    int page = Integer.parseInt(pnp.substring(0, 1));
                    int position = Integer.parseInt(pnp.substring(3, 4));
                    list.add(databaseHandler.getPageItemInStorageManage(page, position));
                    if (i + 1 == files.length) {
                        setRecyclerView();
                    }
                }
            }
        } else {
            progressBar.setVisibility(View.GONE);
            nullScreen.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new StorageManageAdapter(StorageManageDialog.this, list);
        layoutManager = new LinearLayoutManager(StorageManageDialog.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    String getName(String pnp) {
        if (pnp.equals("3-1")) {
            return "audio3-1";
        } else if (pnp.equals("3-2")) {
            return "audio3-2";
        } else if (pnp.equals("4-1")) {
            return "audio4-1";
        } else {
            return "audio4-2";
        }
    }
}
