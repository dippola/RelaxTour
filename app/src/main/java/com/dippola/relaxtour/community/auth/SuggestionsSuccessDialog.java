package com.dippola.relaxtour.community.auth;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.bottomsheet_intent.Report;

public class SuggestionsSuccessDialog extends AppCompatActivity {

    private Button ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_auth_suggestions_success_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ok = findViewById(R.id.community_auth_suggestions_success_dialog_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Suggestions.FROM_SUGGESTIONS);
                finish();
            }
        });
    }

}
