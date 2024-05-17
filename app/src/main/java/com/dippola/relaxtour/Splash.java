package com.dippola.relaxtour;

import android.app.Application;
import android.content.ClipData;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

import com.dippola.relaxtour.community.main.write.CommunityWrite;
import com.dippola.relaxtour.community.main.write.UriAndFileNameModel;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionConfig;
import com.qonversion.android.sdk.dto.QLaunchMode;
import com.qonversion.android.sdk.dto.QUser;
import com.qonversion.android.sdk.dto.QonversionError;
import com.qonversion.android.sdk.dto.entitlements.QEntitlement;
import com.qonversion.android.sdk.dto.offerings.QOfferings;
import com.qonversion.android.sdk.dto.products.QProduct;
import com.qonversion.android.sdk.listeners.QonversionEntitlementsCallback;
import com.qonversion.android.sdk.listeners.QonversionOfferingsCallback;
import com.qonversion.android.sdk.listeners.QonversionProductsCallback;
import com.qonversion.android.sdk.listeners.QonversionUserCallback;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        preferences = ESPreference.getEncryptedSharedPreference(Splash.this, "checkFirst");

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
                checkAppVersion();
            }

            @Override
            public void onError(@NotNull QonversionError error) {
                qonversionPermissionCheckFinished = true;
                Log.d("Splash>>>", "qper error: " + error);
//                checkAppVersion();
                checkAppVersion();
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

    private void checkAppVersion() {
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

//            databaseHandler.changeIsProUserFromSplash(2);

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

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash);
//        test();
//    }
//
//    private void test() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
////        Intent intent = new Intent();
//        intent.setType("image/*");
////        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
////        intent.setAction(Intent.ACTION_GET_CONTENT);
//        launcher.launch(intent);
//    }
//
//    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if (result.getResultCode() == RESULT_OK) {
//                if (result.getData() != null) {
//                    String print = result.getData().getData().toString();
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getData().getData());
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
//                        byte[] imageData = baos.toByteArray();
//                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("community/test");
//                        storageReference.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Log.d("Splash>>>", "8");
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("Splash>>>", "9");
//                            }
//                        });
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }
//    });
//

}
