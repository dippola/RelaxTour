package com.dippola.relaxtour.community.auth;

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

public class SuggestionsSuccessDialog extends AppCompatActivity {

    private Button ok, cancel;
    private TextView title, body;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth_suggestions_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setFinishOnTouchOutside(false);

        title = findViewById(R.id.community_auth_suggestions_dialog_title);
        body = findViewById(R.id.community_auth_suggestions_dialog_body);
        ok = findViewById(R.id.community_auth_suggestions_dialog_ok);
        cancel = findViewById(R.id.community_auth_suggestions_dialog_cancel);

        from = getIntent().getStringExtra("from");

        if (from.equals("ask")) {
            title.setText("Suggestions");
            body.setText("Are you sure you want to\nsend the suggestions?");
        } else {
            cancel.setVisibility(View.GONE);
            title.setText("Completed sending suggestions");
            body.setText("We will actively reflect\nyour suggestions and update them.");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equals("ask")) {
                    Intent intent = new Intent(SuggestionsSuccessDialog.this, Suggestions.class);
                    intent.putExtra("willSend", true);
                    setResult(Suggestions.FROM_ASK, intent);
                    finish();
                } else {
                    setResult(Suggestions.FROM_SUCCESS);
                    finish();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equals("ask")) {
                    Intent intent = new Intent(SuggestionsSuccessDialog.this, Suggestions.class);
                    intent.putExtra("willSend", false);
                    setResult(Suggestions.FROM_ASK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (from.equals("ask")) {
            Intent intent = new Intent(SuggestionsSuccessDialog.this, Suggestions.class);
            intent.putExtra("willSend", false);
            setResult(Suggestions.FROM_ASK, intent);
            finish();
        } else {
            setResult(Suggestions.FROM_SUCCESS);
            finish();
        }
    }
}
