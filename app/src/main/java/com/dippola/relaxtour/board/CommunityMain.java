package com.dippola.relaxtour.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.dialog.AskDownloadsDialog;
import com.dippola.relaxtour.dialog.Premium;
import com.google.firebase.auth.FirebaseAuth;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;

import java.util.ArrayList;
import java.util.Map;

public class CommunityMain extends AppCompatActivity {

    private RelativeLayout load;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);

        load = findViewById(R.id.community_main_load);
        load.setVisibility(View.VISIBLE);

        auth = FirebaseAuth.getInstance();

        checkPremium();
    }

    private void checkPremium() {
        Qonversion.checkPermissions(new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NonNull Map<String, QPermission> map) {
                QPermission qPermission = map.get("dippola_relaxtour_premium");
                if (qPermission != null && qPermission.isActive()) {
                    Log.d("CommunityMain>>>", "have permission");
                    checkAuth();
                } else {
                    Log.d("CommunityMain>>>", "null permission: ");
                    finish();
                    startActivity(new Intent(CommunityMain.this, Premium.class));
                }
            }

            @Override
            public void onError(@NonNull QonversionError qonversionError) {
                Log.d("CommunityMain>>>", "qper error: " + qonversionError);
                Toast.makeText(CommunityMain.this, "Load failed with an error.\nError: " + qonversionError, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void checkAuth() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(CommunityMain.this, CommunityLogin.class));
        }
        load.setVisibility(View.GONE);
        loadCommunity();
    }

    private void loadCommunity() {

    }
}
