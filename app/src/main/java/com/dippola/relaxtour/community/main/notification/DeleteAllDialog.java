package com.dippola.relaxtour.community.main.notification;

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

public class DeleteAllDialog extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_delete_all_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button ok = findViewById(R.id.notification_delete_all_dialog_ok);
        Button cancel = findViewById(R.id.notification_delete_all_dialog_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteAllDialog.this, Notification.class);
                intent.putExtra("isDelete", true);
                setResult(Notification.FROM_DELETE_ALL_DIALOG, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteAllDialog.this, Notification.class);
                intent.putExtra("isDelete", false);
                setResult(Notification.FROM_DELETE_ALL_DIALOG, intent);
                finish();
            }
        });

    }
}
