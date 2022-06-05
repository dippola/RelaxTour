package com.dippola.relaxtour.dialog;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class ResetDialog extends AppCompatActivity {

    TextView second;
    Button ok;

    CountDownTimer countDownTimer;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog = new Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
//        dialog.setCancelable(false);
        setFinishOnTouchOutside(false);

        setInit();
        threadSecond();
        onClickOkBtn();
    }

    private void setInit() {
        second = findViewById(R.id.reset_dialog_second);
        ok = findViewById(R.id.reset_dialog_btn);
    }

    private void threadSecond() {
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                second.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                ok.setEnabled(false);
                resetApp();
            }
        }.start();
    }

    private void onClickOkBtn() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                resetApp();
            }
        });
    }

    private void resetApp() {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        startActivity(mainIntent);
        System.exit(0);
    }

    @Override
    public void onBackPressed() {

    }
}
