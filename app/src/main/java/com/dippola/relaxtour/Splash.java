package com.dippola.relaxtour;

import android.app.Application;
import android.content.Intent;
import android.content.IntentSender;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionConfig;
import com.qonversion.android.sdk.dto.QLaunchMode;
import com.qonversion.android.sdk.dto.QonversionError;
import com.qonversion.android.sdk.dto.entitlements.QEntitlement;
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

    boolean animFinished, qonversionPermissionCheckFinished, checkAppVersionFinished;
    public static boolean goNextAlready;

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

        animation();

        checkInternet();
    }

    private void checkInternet() {
        if (NetworkStatus.getNetworkStatus(Splash.this) == NetworkStatus.NO) {
            qonversionPermissionCheckFinished = true;
            checkAppVersionFinished = true;
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
                qonversionPermissionCheckFinished = true;
                if (premiumEntitlement != null && premiumEntitlement.isActive()) {
                    databaseHandler.changeIsProUserFromSplash(2);
                    Log.d("Splash>>>", "have permission");
                } else {
                    databaseHandler.changeIsProUserFromSplash(1);
                    Log.d("Splash>>>", "null permission");
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
                }
//                checkAppVersion();
                checkAppVersionTest();
            }

            @Override
            public void onError(@NotNull QonversionError error) {
                qonversionPermissionCheckFinished = true;
                Log.d("Splash>>>", "qper error: " + error);
//                checkAppVersion();
                checkAppVersionTest();
            }
        });
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
                checkFirst();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void checkFirst() {
        if (qonversionPermissionCheckFinished && checkAppVersionFinished && animFinished) {
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

    private void checkAppVersionTest() {
        if (!checkAppVersionFinished) {
            checkAppVersionFinished = true;
            AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(Splash.this);
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        if (!goNextAlready) {
                            goNextAlready = true;
                        }
                        Toast.makeText(Splash.this, "The new version has been released!\nPlease update to the latest version on the Play Store.", Toast.LENGTH_LONG).show();
                        UpdateDialog.showDialog(Splash.this);
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, launcher, AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build());
                    } else {
                        checkFirst();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    checkFirst();
                }
            });
        }
    }

    ActivityResultLauncher<IntentSenderRequest> launcher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_CANCELED) {
            } else if (result.getResultCode() == RESULT_OK) {
                UpdateDialog.closeDialog();
                UpdateDialog.showDialog2(Splash.this);
            }
        }
    });

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Splash>>>", "back pressed");
    }

    private void goToMainActivity() {
        if (!goNextAlready) {
            goNextAlready = true;
            Intent intent = new Intent(Splash.this, MainActivity.class);
            intent.putExtra("fromSplash", false);
            startActivity(intent);
            finish();
        }
    }

    private void goToOnBoarding() {
        if (!goNextAlready) {
            goNextAlready = true;
            Intent intent = new Intent(Splash.this, OnBoarding.class);
            intent.putExtra("fromSplash", true);
            startActivity(intent);
            finish();
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
