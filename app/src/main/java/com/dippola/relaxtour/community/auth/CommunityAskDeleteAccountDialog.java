package com.dippola.relaxtour.community.auth;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class CommunityAskDeleteAccountDialog extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView email, passwordText, error;
    private Button ok, cancel;
    private EditText password;
    private String provider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_ask_delete_account_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        auth = FirebaseAuth.getInstance();
        provider = getIntent().getStringExtra("provider");
        email = findViewById(R.id.community_ask_delete_account_email);
        ok = findViewById(R.id.community_ask_delete_account_ok);
        cancel = findViewById(R.id.community_ask_delete_account_cancel);
        passwordText = findViewById(R.id.community_ask_delete_account_password_text);
        password = findViewById(R.id.community_ask_delete_account_password);
        error = findViewById(R.id.community_ask_delete_account_error);
        if (provider.equals("google")) {
            passwordText.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
        }

        email.setText(getIntent().getStringExtra("email"));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (provider.equals("email")) {
                    error.setText("");
                    if (password.getText().toString().length() == 0) {
                        error.setText("Please enter your email");
                    } else {
                        checkEmailPassword();
                    }
                } else {
                    willFinish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityAskDeleteAccountDialog.this, CommunityAuth.class);
                intent.putExtra("willDelete", false);
                setResult(CommunityAuth.FROM_DELETE_ACCOUNT, intent);
                finish();
            }
        });
    }

    private void checkEmailPassword() {
        AuthCredential authCredential = EmailAuthProvider.getCredential(auth.getCurrentUser().getEmail(), password.getText().toString());
        auth.getCurrentUser().reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                willFinish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setText("Password does not match.");
            }
        });
    }

    private void willFinish() {
        Intent intent = new Intent(CommunityAskDeleteAccountDialog.this, CommunityAuth.class);
        intent.putExtra("willDelete", true);
        intent.putExtra("provider", provider);
        if (provider.equals("email")) {
            intent.putExtra("password", password.getText().toString());
        }
        setResult(CommunityAuth.FROM_DELETE_ACCOUNT, intent);
        finish();
    }
}
