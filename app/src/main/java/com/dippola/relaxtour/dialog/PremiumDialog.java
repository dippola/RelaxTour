package com.dippola.relaxtour.dialog;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.setting.SettingDialog;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionErrorCode;
import com.qonversion.android.sdk.QonversionOfferingsCallback;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.QonversionProductsCallback;
import com.qonversion.android.sdk.dto.QPermission;
import com.qonversion.android.sdk.dto.offerings.QOffering;
import com.qonversion.android.sdk.dto.offerings.QOfferings;
import com.qonversion.android.sdk.dto.products.QProduct;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PremiumDialog extends AppCompatActivity {

    Button ok, close;
    TextView cancel;
    Application application;
    Activity activity;
    RelativeLayout load;
    ImageView img;

    Dialog dialog;

    boolean isLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premium_dialog);

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog = new Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);

        application = getApplication();
        activity = PremiumDialog.this;

        setInit();
    }

    private void setInit() {
        ok = findViewById(R.id.premium_dialog_ok);
        close = findViewById(R.id.premium_dialog_close);
        img = findViewById(R.id.premium_dialog_title_img);
        cancel = findViewById(R.id.premium_dialog_cancel);
        load = findViewById(R.id.premium_dialog_load);
        load.setVisibility(View.GONE);
        okSetOnClick();
        cancelSetOnClick();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();
        params.width = MainActivity.premium_dialog_title_img_size;
        params.height = MainActivity.premium_dialog_title_img_size;
        img.setLayoutParams(params);
    }

    private void okSetOnClick() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoadVisible();
                application = getApplication();
                Qonversion.launch(application, "tvcyUzPvRUyPLwrjhoQwujcuc_vwZC3i", false);
                Qonversion.offerings(new QonversionOfferingsCallback() {
                    @Override
                    public void onSuccess(@NotNull QOfferings offerings) {
                        QOffering offering = offerings.offeringForID("offering_id");
                        if (offering != null) {
                            Qonversion.products(new QonversionProductsCallback() {
                                @Override
                                public void onSuccess(@NotNull Map<String, QProduct> productsList) {
                                    Qonversion.purchase(activity, "dippola_relaxtour_premium", new QonversionPermissionsCallback() {
                                        @Override
                                        public void onSuccess(@NotNull Map<String, QPermission> permissions) {
                                            QPermission premiumPermission = permissions.get("dippola_relaxtour_premium");
                                            if (premiumPermission != null && premiumPermission.isActive()) {
                                                // handle active permission here
                                                if (MainActivity.databaseHandler == null) {
                                                    DatabaseHandler databaseHandler = new DatabaseHandler(PremiumDialog.this);
                                                    databaseHandler.changeIsProUser(2);
                                                    databaseHandler.closeDatabse();
                                                    databaseHandler.close();
                                                } else {
                                                    MainActivity.databaseHandler.changeIsProUser(2);
                                                }

                                                startActivity(new Intent(PremiumDialog.this, ResetDialog.class));
                                            }
                                        }

                                        @Override
                                        public void onError(@NotNull QonversionError error) {
                                            setLoadGone();
                                            if (error.getCode() != QonversionErrorCode.CanceledPurchase) {
                                                Toast.makeText(PremiumDialog.this, "error: " + error.getDescription(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onError(@NotNull QonversionError error) {
                                    setLoadGone();
                                    Toast.makeText(PremiumDialog.this, "error: " + error.getDescription(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onError(@NotNull QonversionError error) {
                        setLoadGone();
                        Toast.makeText(PremiumDialog.this, "error: " + error.getDescription(), Toast.LENGTH_LONG).show();
                    }
                });
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

    private void setLoadVisible() {
        isLoading = true;
        load.setVisibility(View.VISIBLE);
        load.setClickable(true);
        setFinishOnTouchOutside(false);
    }

    private void setLoadGone() {
        isLoading = false;
        load.setVisibility(View.GONE);
        setFinishOnTouchOutside(true);
    }

    @Override
    public void onBackPressed() {
        if (!isLoading) {
            super.onBackPressed();
        }
    }
}
