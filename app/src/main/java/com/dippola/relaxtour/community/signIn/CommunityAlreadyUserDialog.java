package com.dippola.relaxtour.community.signIn;

import android.content.Intent;
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

public class CommunityAlreadyUserDialog extends AppCompatActivity {

    private Button ok, cancel;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_already_user_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ok = findViewById(R.id.community_already_user_ok);
        cancel = findViewById(R.id.community_already_user_cancel);
        title = findViewById(R.id.community_already_user_title);
        title.setText("You have already registered as a member\nusing " + "'" + getIntent().getStringExtra("provider") + "'" + "\nWould you like to log in?");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAlreadyUserDialog.this, CommunitySignUp.class);
                intent.putExtra("click", "ok");
                setResult(CommunitySignUp.FROM_ALREADY_USER_DIALOG, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAlreadyUserDialog.this, CommunitySignUp.class);
                intent.putExtra("click", "cancel");
                setResult(CommunitySignUp.FROM_ALREADY_USER_DIALOG, intent);
                finish();
            }
        });
    }
}
