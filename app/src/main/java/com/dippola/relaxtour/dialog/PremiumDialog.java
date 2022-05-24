package com.dippola.relaxtour.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class PremiumDialog extends AppCompatActivity {

    Button ok, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Dialog dialog = new Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);

        setInit();
    }

    private void setInit() {
        ok = findViewById(R.id.premium_dialog_ok);
        cancel = findViewById(R.id.premium_dialog_cancel);
        okSetOnClick();
        cancelSetOnClick();
    }

    private void okSetOnClick() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void cancelSetOnClick() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
