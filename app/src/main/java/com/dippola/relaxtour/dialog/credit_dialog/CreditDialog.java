package com.dippola.relaxtour.dialog.credit_dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.setting.CreditAdapter;
import com.dippola.relaxtour.setting.CreditItem;
import com.dippola.relaxtour.setting.StorageManageAdapter;
import com.dippola.relaxtour.setting.StorageManageDialog;

import java.util.ArrayList;

public class CreditDialog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CreditItem> arrayList;
    private CreditAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ImageButton close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setRecyclerView();
        setCloseButton();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.credit_dialog_recyclerview);
        arrayList = MainActivity.databaseHandler.getCreditList();
        adapter = new CreditAdapter(arrayList);
        layoutManager = new LinearLayoutManager(CreditDialog.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setCloseButton() {
        close = findViewById(R.id.credit_dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
