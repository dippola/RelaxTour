package com.dippola.relaxtour.dialog;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.adapter.PageAdapter;
import com.dippola.relaxtour.pages.item.DownloadItem;
import com.dippola.relaxtour.service.DownloadService;
import com.dippola.relaxtour.service.DownloadsService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class AskDownloadDialog {
    public static AlertDialog alertDialog;
    private static Button okbtn, cancel;
    private static CheckBox checkBox;

    public static void askDownloadDialog(Context context, ProgressBar progressBar, ImageView img, ImageView download, String tid, SeekBar seekBar) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.ask_download_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        okbtn = layout.findViewById(R.id.ask_download_dialog_ok);
        cancel = layout.findViewById(R.id.ask_download_dialog_cancel);
        checkBox = layout.findViewById(R.id.ask_download_dialog_checkbox);


        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences;
                try {
                    sharedPreferences = EncryptedSharedPreferences.create(
                            "download_dialog_checkbox",
                            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                            context,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkBox.isChecked()) {
                    editor.putBoolean("isChecked", true);
                } else {
                    editor.putBoolean("isChecked", false);
                }
                editor.apply();
                PageAdapter.openDownloadService(context, progressBar, img, download, tid);
                DownloadItem downloadItem = new DownloadItem(tid);
                DownloadService.downloadList.add(downloadItem);
                DownloadService.setOnClickDownload(context, progressBar, img, download, tid, seekBar, downloadItem);
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
