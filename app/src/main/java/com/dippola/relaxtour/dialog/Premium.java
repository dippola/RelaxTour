package com.dippola.relaxtour.dialog;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.NetworkStatus;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionConfig;
import com.qonversion.android.sdk.dto.QLaunchMode;
import com.qonversion.android.sdk.dto.QonversionError;
import com.qonversion.android.sdk.dto.QonversionErrorCode;
import com.qonversion.android.sdk.dto.entitlements.QEntitlement;
import com.qonversion.android.sdk.dto.offerings.QOffering;
import com.qonversion.android.sdk.dto.offerings.QOfferings;
import com.qonversion.android.sdk.dto.products.QProduct;
import com.qonversion.android.sdk.listeners.QonversionEntitlementsCallback;
import com.qonversion.android.sdk.listeners.QonversionOfferingsCallback;
import com.qonversion.android.sdk.listeners.QonversionProductsCallback;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Premium extends AppCompatActivity {

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
        setContentView(R.layout.premium_activity);

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog = new Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);

        application = getApplication();
        activity = Premium.this;

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
                int i = NetworkStatus.getNetworkStatus(getApplicationContext());
                if (i == NetworkStatus.NO) {
                    Toast.makeText(Premium.this, "Internet connection is not present or unstable.\nPlease check and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    startShowQonversion();
                }
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

    private void startShowQonversion() {
        setLoadVisible();
        application = getApplication();
        final QonversionConfig qonversionConfig = new QonversionConfig.Builder(
                this,
                getString(R.string.qonversion_key),
                QLaunchMode.SubscriptionManagement
        ).build();
        Qonversion.initialize(qonversionConfig);

        Qonversion.getSharedInstance().offerings(new QonversionOfferingsCallback() {
            @Override
            public void onSuccess(@NotNull QOfferings offerings) {
                if (offerings.getMain() != null && !offerings.getMain().getProducts().isEmpty()) {
                    QOffering qOffering = offerings.offeringForID(getString(R.string.qoffering_id));
                    if (qOffering != null) {
                        Qonversion.getSharedInstance().products(new QonversionProductsCallback() {
                            @Override
                            public void onSuccess(@NotNull Map<String, QProduct> productsList) {
                                Qonversion.getSharedInstance().purchase(activity, getString(R.string.product_id), new QonversionEntitlementsCallback() {
                                    @Override
                                    public void onSuccess(@NotNull Map<String, QEntitlement> entitlements) {
                                        QEntitlement premiumPermission = entitlements.get(getString(R.string.product_id));
                                        if (premiumPermission != null && premiumPermission.isActive()) {
                                            // handle active permission here
                                            if (MainActivity.databaseHandler == null) {
                                                DatabaseHandler databaseHandler = new DatabaseHandler(Premium.this);
                                                databaseHandler.changeIsProUserFromPremium(2);
                                                databaseHandler.closeDatabse();
                                                databaseHandler.close();
                                            } else {
                                                MainActivity.databaseHandler.changeIsProUserFromPremium(2);
                                            }
                                            startActivity(new Intent(Premium.this, ResetDialog.class));
                                        }
                                    }

                                    @Override
                                    public void onError(@NotNull QonversionError error) {
                                        setLoadGone();
                                        if (error.getCode() != QonversionErrorCode.CanceledPurchase) {
                                            if (error.getCode() == QonversionErrorCode.ProductAlreadyOwned) {
                                                Log.d("Premium>>>", "e1: " + error.getCode());
                                                Qonversion.getSharedInstance().restore(new QonversionEntitlementsCallback() {
                                                    @Override
                                                    public void onSuccess(@NonNull Map<String, QEntitlement> map) {
                                                        QEntitlement qPermission = map.get(getString(R.string.product_id));
                                                        if (qPermission != null && qPermission.isActive()) {
                                                            Log.d("Premium>>>", "restored");
                                                        } else {
                                                            Log.d("Premium>>>", "no restore");
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(@NonNull QonversionError qonversionError) {

                                                    }
                                                });
                                            }
                                            Toast.makeText(Premium.this, "error: " + error.getDescription(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onError(@NotNull QonversionError error) {
                                setLoadGone();
                                Toast.makeText(Premium.this, "error: " + error.getDescription(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(@NotNull QonversionError error) {
                setLoadGone();
                Toast.makeText(Premium.this, "error: " + error.getDescription(), Toast.LENGTH_LONG).show();
            }
        });
    }

}