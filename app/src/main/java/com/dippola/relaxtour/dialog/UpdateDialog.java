package com.dippola.relaxtour.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.R;

public class UpdateDialog {
    public static AlertDialog alertDialog;
    private static Button ok;
    private static TextView currentVersion, newVersion;

    public static void showDialog(Context context, String cv, String nv) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.update_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        ok = layout.findViewById(R.id.update_dialog_btn);
        currentVersion = layout.findViewById(R.id.update_dialog_current_version);
        newVersion = layout.findViewById(R.id.update_dialog_new_version);

        currentVersion.setText("Current Version: " + cv);
        newVersion.setText("New Version: " + nv);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.dippola.relaxtour"));
                context.startActivity(intent);
            }
        });
    }
}
