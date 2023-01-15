package com.dippola.relaxtour.community.main.notification;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class AdminNotificationDialog extends AppCompatActivity {

    Button button;
    TextView text1, text2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notification_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String body = getIntent().getStringExtra("body");

        text1 = findViewById(R.id.admin_notification_dialog_text1);
        text2 = findViewById(R.id.admin_notification_dialog_text2);
        button = findViewById(R.id.admin_notification_dialog_button);
        text2.setText(body);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
