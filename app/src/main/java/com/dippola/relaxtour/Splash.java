package com.dippola.relaxtour;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.UpdateDialog;
import com.dippola.relaxtour.onboarding.OnBoarding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;
import com.qonversion.android.sdk.dto.products.QProductRenewState;

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

    boolean anim, qper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        preferences = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);

        bg = findViewById(R.id.splash_background);

        application = getApplication();
        Qonversion.launch(application, "tvcyUzPvRUyPLwrjhoQwujcuc_vwZC3i", false);
        databaseHandler.setDB(Splash.this);
        databaseHandler = new DatabaseHandler(Splash.this);

        title = findViewById(R.id.splash_title);
        dippola = findViewById(R.id.splash_dippola);
        img = findViewById(R.id.splash_img);
        progressBar = findViewById(R.id.splash_progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        checkPermission();
    }

    private void checkPermission() {
        Qonversion.checkPermissions(new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NonNull Map<String, QPermission> map) {
                QPermission qPermission = map.get("dippola_relaxtour_premium");

                if (qPermission != null && qPermission.isActive()) {
                    databaseHandler.changeIsProUser(2);
                    qper = true;
                    Log.d("Splash>>>", "have permission");
                    checkAppVersion();
                } else {
                    databaseHandler.changeIsProUser(1);
                    qper = true;
                    Log.d("Splash>>>", "null permission: ");
                    checkAppVersion();
                }
            }

            @Override
            public void onError(@NonNull QonversionError qonversionError) {
                qper = true;
                Log.d("Splash>>>", "qper error: " + qonversionError);
                checkAppVersion();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!qper) {
                    qper = true;
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
        databaseHandler.close();
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
                anim = true;
                progressBar.setVisibility(View.VISIBLE);
                checkAppVersion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void checkFirst() {
        if (qper && anim) {
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
        String current_version = getAppVersion();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("app_status").document("app_version").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String new_version = documentSnapshot.get("new_version").toString();
                if (current_version.equals(new_version)) {
                    checkFirst();
                } else {
                    UpdateDialog.showDialog(Splash.this, current_version, new_version);
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
        Intent intent = new Intent(Splash.this, MainActivity.class);
        intent.putExtra("fromSplash", false);
        startActivity(intent);
        finish();
    }

    private void goToOnBoarding() {
        Intent intent = new Intent(Splash.this, OnBoarding.class);
        intent.putExtra("fromSplash", true);
        startActivity(intent);
        finish();
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
