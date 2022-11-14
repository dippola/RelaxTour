package com.dippola.relaxtour.community.signIn;

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

public class CommunitySignInAnotherProviderDialog extends AppCompatActivity {

    private Button ok;
    private TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_signin_another_provider_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ok = findViewById(R.id.community_signin_another_ok);
        title = findViewById(R.id.community_signin_another_text);

        title.setText("This email has been Sign Up\nto " + getIntent().getStringExtra("showProvider") + ".");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
