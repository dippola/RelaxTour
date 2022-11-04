package com.dippola.relaxtour.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.R;

public class RestoreDialog {

    public static AlertDialog alertDialog;
    public static Button btn;

    public static void restoreDialog(Context context) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.restore_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        btn = layout.findViewById(R.id.restore_dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager = context.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                ComponentName componentName = intent.getComponent();
                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                context.startActivity(mainIntent);
                System.exit(0);
            }
        });
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.restore_dialog);
//
//        Button button = findViewById(R.id.restore_dialog_btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PackageManager packageManager = getPackageManager();
//                Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
//                ComponentName componentName = intent.getComponent();
//                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
//                startActivity(mainIntent);
//                System.exit(0);
//            }
//        });
//    }
}
