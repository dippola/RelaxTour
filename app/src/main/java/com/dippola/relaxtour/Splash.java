package com.dippola.relaxtour;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.UpdateDialog;
import com.dippola.relaxtour.onboarding.OnBoarding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionConfig;
import com.qonversion.android.sdk.dto.QEntitlement;
import com.qonversion.android.sdk.dto.QLaunchMode;
import com.qonversion.android.sdk.dto.QonversionError;
import com.qonversion.android.sdk.listeners.QonversionEntitlementsCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Keep
public class Splash extends AppCompatActivity {

    private SharedPreferences preferences;
    Application application;
    DatabaseHandler databaseHandler;

    ConstraintLayout bg;

    TextView title, dippola;
    ImageView img;
    ProgressBar progressBar;

    boolean animFinished, qonversionPermissionCheckFinished, goNextAlready;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        try {
            preferences = EncryptedSharedPreferences.create(
                    "checkFirst",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.d("Splash>>>", "e: " + e.toString());
            throw new RuntimeException(e);
        }
//        preferences = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);

        bg = findViewById(R.id.splash_background);

        application = getApplication();
//        Qonversion.launch(application, getString(R.string.qonversion_key), false);
        final QonversionConfig qonversionConfig = new QonversionConfig.Builder(
                this,
                getString(R.string.qonversion_key),
                QLaunchMode.SubscriptionManagement
        ).build();
        Qonversion.initialize(qonversionConfig);

        title = findViewById(R.id.splash_title);
        dippola = findViewById(R.id.splash_dippola);
        img = findViewById(R.id.splash_img);
        progressBar = findViewById(R.id.splash_progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        databaseHandler.setDB(Splash.this);
        databaseHandler = new DatabaseHandler(Splash.this, Splash.this);
//        databaseHandler = new DatabaseHandler(Splash.this);

        //인터넷 연결상태 확인
        //qonversion 확인
        //app version 확인
        checkInternet();

        checkPermission();
    }

    private void checkInternet() {
        if (NetworkStatus.getNetworkStatus(Splash.this) == 3) {
            qonversionPermissionCheckFinished = true;
            checkFirst();
        } else {
            checkPermission();
        }
    }

    private void checkPermission() {
        Qonversion.getSharedInstance().checkEntitlements(new QonversionEntitlementsCallback() {
            @Override
            public void onSuccess(@NotNull Map<String, QEntitlement> entitlements) {
                final QEntitlement premiumEntitlement = entitlements.get(getString(R.string.product_id));

                if (premiumEntitlement != null && premiumEntitlement.isActive()) {
                    databaseHandler.changeIsProUserFromSplash(2);
                    qonversionPermissionCheckFinished = true;
                    Log.d("Splash>>>", "have permission");
                    checkAppVersion();
                } else {
                    databaseHandler.changeIsProUserFromSplash(1);
                    qonversionPermissionCheckFinished = true;
                    Log.d("Splash>>>", "null permission: ");
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        FirebaseAuth.getInstance().signOut();
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(getString(R.string.default_web_client_id))
                                .requestEmail()
                                .build();
                        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(Splash.this, gso);
                        googleSignInClient.signOut();
                        DatabaseHandler databaseHandler = new DatabaseHandler(Splash.this);
                        databaseHandler.deleteUserProfile();
                    }
                    checkAppVersion();
                }
            }

            @Override
            public void onError(@NotNull QonversionError error) {
                qonversionPermissionCheckFinished = true;
                Log.d("Splash>>>", "qper error: " + error);
                checkAppVersion();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!qonversionPermissionCheckFinished) {
                    qonversionPermissionCheckFinished = true;
                    checkAppVersion();
                }
            }
        }, 5000);
        animation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHandler.closeDatabse();
    }

    private void animation() {
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.favlist_anim_1);
        img.startAnimation(anim1);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.favlist_anim_2);
        title.startAnimation(anim2);

        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animFinished = true;
                progressBar.setVisibility(View.VISIBLE);
                checkAppVersion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void checkFirst() {
        if (qonversionPermissionCheckFinished && animFinished) {
            Log.d("Splash>>>", "1");
            if (!preferences.getBoolean("checkFirst", false)) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("checkFirst", true);
                editor.apply();
                goToOnBoarding();
            } else {
                goToMainActivity();
            }
        }
    }

    private void checkAppVersion() {
        String current_version_withdot = getAppVersion();
        int current_version = Integer.parseInt(current_version_withdot.replace(".", ""));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("app_status").document("app_version").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String new_version_withdot = documentSnapshot.get("new_version").toString();
                int new_version = Integer.parseInt(new_version_withdot.replace(".", ""));
                if (current_version >= new_version) {
                    checkFirst();
                } else {
                    UpdateDialog.showDialog(Splash.this, current_version_withdot, new_version_withdot);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                checkFirst();
            }
        });
    }

    private void goToMainActivity() {
        if (!DatabaseHandler.isHaveNewVersionDB) {
            if (!goNextAlready) {
                goNextAlready = true;
                Intent intent = new Intent(Splash.this, MainActivity.class);
                intent.putExtra("fromSplash", false);
                Log.d("Splash>>>", "2");
                startActivity(intent);
                finish();
            }
        }
    }

    private void goToOnBoarding() {
        if (!DatabaseHandler.isHaveNewVersionDB) {
            if (!goNextAlready) {
                goNextAlready = true;
                Intent intent = new Intent(Splash.this, OnBoarding.class);
                intent.putExtra("fromSplash", true);
                startActivity(intent);
                finish();
            }
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
}
