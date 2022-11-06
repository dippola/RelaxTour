package com.dippola.relaxtour.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.RestoreDialog;
import com.dippola.relaxtour.dialog.credit_dialog.CreditDialog;
import com.dippola.relaxtour.dialog.Premium;
import com.dippola.relaxtour.onboarding.OnBoarding;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;

import java.util.Map;

public class SettingDialog extends AppCompatActivity {

    RelativeLayout howToUse, storageManage, credit, load, size;
    Button premiumBtn, already;
    SwitchCompat notifiSwitch;
    TextView version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
        onClickCredit();

        checkPermission();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) load.getLayoutParams();
        params.height = size.getHeight();
        load.setLayoutParams(params);
    }

    private void setInit() {
        howToUse = findViewById(R.id.setting_how_to_use);
        premiumBtn = findViewById(R.id.setting_go_to_premium);
        already = findViewById(R.id.setting_already_premium);
        storageManage = findViewById(R.id.setting_storage_manage);
        notifiSwitch = findViewById(R.id.setting_notification_switch);
        version = findViewById(R.id.setting_version_text);
        version.setText(getAppVersion());
        credit = findViewById(R.id.setting_credit);
        load = findViewById(R.id.setting_load);
        size = findViewById(R.id.setting_dialog_layout_size);
    }

    private void onClickHowToUse() {
        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingDialog.this, OnBoarding.class);
                intent.putExtra("fromSplash", false);
                startActivity(intent);
            }
        });
    }

    private void onClickPremiumBtn() {
        premiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load.setVisibility(View.VISIBLE);
                setFinishOnTouchOutside(false);
                MainActivity.bottomSheetBehavior.setDraggable(false);
                Qonversion.restore(new QonversionPermissionsCallback() {
                    @Override
                    public void onSuccess(@NonNull Map<String, QPermission> map) {
                        QPermission qPermission = map.get("dippola_relaxtour_premium");
                        if (qPermission != null && qPermission.isActive()) {
                            Log.d("Premium>>>", "restored");
                            RestoreDialog.restoreDialog(SettingDialog.this);
                        } else {
                            Log.d("Premium>>>", "no restore");
                            SettingDialog.this.startActivity(new Intent(SettingDialog.this, Premium.class));
                            load.setVisibility(View.GONE);
                            setFinishOnTouchOutside(true);
                            MainActivity.bottomSheetBehavior.setDraggable(true);
                        }
                    }

                    @Override
                    public void onError(@NonNull QonversionError qonversionError) {
                        Toast.makeText(SettingDialog.this, "Error: " + qonversionError.getDescription(), Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                        setFinishOnTouchOutside(true);
                        MainActivity.bottomSheetBehavior.setDraggable(true);
                    }
                });
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

    private void onClickCredit() {
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingDialog.this, CreditDialog.class));
            }
        });
    }

    private void checkPermission() {
        if (MainActivity.databaseHandler.getIsProUser() == 1) {
            nullPermission();
        } else if (MainActivity.databaseHandler.getIsProUser() == 2) {
            havePermission();
        }
    }

    private String getAppVersion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    private void havePermission() {
        premiumBtn.setVisibility(View.GONE);
        already.setVisibility(View.VISIBLE);
    }

    private void nullPermission() {
        premiumBtn.setVisibility(View.VISIBLE);
        already.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (load.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }
}
