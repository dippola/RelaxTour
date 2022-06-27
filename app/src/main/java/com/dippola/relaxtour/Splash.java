package com.dippola.relaxtour;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.QonversionError;
import com.qonversion.android.sdk.QonversionPermissionsCallback;
import com.qonversion.android.sdk.dto.QPermission;

import java.util.Map;

public class Splash extends AppCompatActivity {

    Application application;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        application = getApplication();
        Qonversion.launch(application, "tvcyUzPvRUyPLwrjhoQwujcuc_vwZC3i", false);
        databaseHandler.setDB(Splash.this);
        databaseHandler = new DatabaseHandler(Splash.this);

        checkPermission();
    }

    private void checkPermission() {
        Qonversion.checkPermissions(new QonversionPermissionsCallback() {
            @Override
            public void onSuccess(@NonNull Map<String, QPermission> map) {
                QPermission qPermission = map.get("dippola_relaxtour_premium");
                if (qPermission != null && qPermission.isActive()) {
                    databaseHandler.changeIsProUser(2);
                    Log.d("Splash>>>", "have permission");
                } else {
                    databaseHandler.changeIsProUser(1);
                    Log.d("Splash>>>", "null permission");
                }
            }

            @Override
            public void onError(@NonNull QonversionError qonversionError) {

            }
        });

        startActivity(new Intent(Splash.this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHandler.closeDatabse();
        databaseHandler.close();
    }
}
