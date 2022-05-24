package com.dippola.relaxtour.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.PremiumDialog;

public class SettingDialog extends AppCompatActivity {

    RelativeLayout howToUse, storageManage;
    Button premiumBtn;
    SwitchCompat notifiSwitch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Dialog dialog = new Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.7f;
//
//        //set dialog size
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);

        setInit();
        onClickHowToUse();
        onClickPremiumBtn();
        onClickStorageManage();
        setNotifiSwitch();
    }

    private void setInit() {
        howToUse = findViewById(R.id.setting_how_to_use);
        premiumBtn = findViewById(R.id.setting_go_to_premium);
        storageManage = findViewById(R.id.setting_storage_manage);
        notifiSwitch = findViewById(R.id.setting_notification_switch);
    }

    private void onClickHowToUse() {

    }

    private void onClickPremiumBtn() {
        premiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingDialog.this, PremiumDialog.class));
            }
        });
    }

    private void onClickStorageManage() {
        storageManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingDialog.this, StorageManageDialog.class));
            }
        });
    }

    private void setNotifiSwitch() {
        DatabaseHandler databaseHandler = new DatabaseHandler(SettingDialog.this);
        if (databaseHandler.getNotificationAgree() == 1) {
            notifiSwitch.setChecked(true);
        } else {
            notifiSwitch.setChecked(false);
        }
        notifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Log.d("SettingDialog>>>", "true");
                    databaseHandler.changeNotificationAgree(0);
                } else {
                    databaseHandler.changeNotificationAgree(1);
                }
            }
        });
    }
}
