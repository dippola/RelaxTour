package com.dippola.relaxtour.community.admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class AdminDialog extends AppCompatActivity {

    Button b0, b1, b2, b3, b4, b5;
    String date, from;
    int postid, commentid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        date = getIntent().getStringExtra("date");
        from = getIntent().getStringExtra("from");
        postid = getIntent().getIntExtra("postid", 0);
        commentid = getIntent().getIntExtra("commentid", 0);

        setInit();
    }

    private void setInit() {
        b0 = findViewById(R.id.admin_dialog0);
        b1 = findViewById(R.id.admin_dialog1);
        b2 = findViewById(R.id.admin_dialog2);
        b3 = findViewById(R.id.admin_dialog3);
        b4 = findViewById(R.id.admin_dialog4);
        b5 = findViewById(R.id.admin_dialog5);

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDialog.this.onClick(100);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDialog.this.onClick(1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDialog.this.onClick(2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDialog.this.onClick(3);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDialog.this.onClick(4);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminDialog.this.onClick(5);
            }
        });
    }

    private void onClick(int i) {
        Intent intent = new Intent(AdminDialog.this, Admin.class);
        if (i == 100) {
            intent.putExtra("date", date);
        }
        if (i == 1) {
            intent.putExtra("from", from);
            intent.putExtra("postid", postid);
            intent.putExtra("commentid", commentid);
        }
        intent.putExtra("click", i);
        setResult(Admin.FROM_ADMIN_DIALOG, intent);
        finish();
    }
}
