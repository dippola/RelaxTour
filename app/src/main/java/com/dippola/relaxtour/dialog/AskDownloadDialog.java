package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.service.DownloadService;
import com.dippola.relaxtour.service.DownloadsService;

import java.util.ArrayList;

public class AskDownloadDialog {
    public static AlertDialog alertDialog;
    private static Button okbtn, cancel;
    private static ProgressBar progressBar;
    private static TextView count;

    public static void askDownloadDialog(Context context, ArrayList<String> pnps, int position) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.ask_download_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();

        alertDialog.show();

        okbtn = layout.findViewById(R.id.ask_download_dialog_button_ok);
        cancel = layout.findViewById(R.id.ask_download_dialog_button_cancel);
        progressBar = layout.findViewById(R.id.ask_download_dialog_progressbar);
        count = layout.findViewById(R.id.ask_download_dialog_count);
        progressBar.setVisibility(View.INVISIBLE);
        count.setVisibility(View.INVISIBLE);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(false);
                okbtn.setEnabled(false);
                cancel.setEnabled(false);
                Intent intent = new Intent(context, DownloadsService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(intent);
                    DownloadsService.downloads(context, progressBar, count, pnps);
                } else {
                    context.startService(intent);
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
