package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class CommunityAskSignOutDialog extends AppCompatActivity {

    private TextView email;
    private Button ok, cancel;
    private ImageView providerIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_ask_signout_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        email = findViewById(R.id.community_ask_signout_email);
        ok = findViewById(R.id.community_ask_signout_ok);
        cancel = findViewById(R.id.community_ask_signout_cancel);
        providerIcon = findViewById(R.id.community_ask_signout_email_icon);

        if (getIntent().getStringExtra("provider").equals("Google")) {
            providerIcon.setBackground(getResources().getDrawable(R.drawable.google_white_icon));
        }

        email.setText(getIntent().getStringExtra("email"));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAskSignOutDialog.this, CommunityAuth.class);
                intent.putExtra("willSignout", true);
                setResult(CommunityAuth.FROM_ASK_SIGNOUT, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAskSignOutDialog.this, CommunityAuth.class);
                intent.putExtra("willSignout", false);
                setResult(CommunityAuth.FROM_ASK_SIGNOUT, intent);
                finish();
            }
        });
    }
}
