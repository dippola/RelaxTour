package com.dippola.relaxtour.community.signIn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.CommunityMain;

public class CommunityAskSignUpDialog extends AppCompatActivity{

    private Button ok, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_ask_signup_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ok = findViewById(R.id.community_ask_signup_dialog_ok);
        cancel = findViewById(R.id.community_ask_signup_dialog_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAskSignUpDialog.this, CommunitySignIn.class);
                intent.putExtra("click", "ok");
                setResult(CommunitySignIn.ASK_SIGNIN_RESULT_CODE, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAskSignUpDialog.this, CommunitySignIn.class);
                intent.putExtra("click", "cancel");
                setResult(CommunitySignIn.ASK_SIGNIN_RESULT_CODE, intent);
                finish();
            }
        });
    }
}
