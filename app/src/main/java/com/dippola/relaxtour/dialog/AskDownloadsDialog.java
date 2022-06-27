package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.service.DownloadsService;

import java.util.ArrayList;

public class AskDownloadsDialog {
    public static AlertDialog alertDialog;
    private static Button okbtn, cancel, close;
    private static ProgressBar progressBar;
    private static TextView title, count;
    private static LinearLayout buttons1;
    private static ProgressBar button2;
    public static boolean isDownloading = false;

    public static void askDownloadsDialog(Context context, ArrayList<String> pnps) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.ask_downloads_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog).setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (isDownloading) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        });
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        title = layout.findViewById(R.id.ask_downloads_dialog_title);
        buttons1 = layout.findViewById(R.id.ask_downloads_dialog_1);
        button2 = layout.findViewById(R.id.ask_downloads_dialog_2);
        close = layout.findViewById(R.id.ask_downloads_dialog_3);
        okbtn = layout.findViewById(R.id.ask_downloads_dialog_button_ok);
        cancel = layout.findViewById(R.id.ask_downloads_dialog_button_cancel);
        progressBar = layout.findViewById(R.id.ask_downloads_dialog_progressbar);
        count = layout.findViewById(R.id.ask_downloads_dialog_count);
        progressBar.setVisibility(View.INVISIBLE);
        count.setVisibility(View.INVISIBLE);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("please wait for download");
                buttons1.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
                isDownloading = true;
                alertDialog.setCancelable(false);
                okbtn.setEnabled(false);
                cancel.setEnabled(false);
                Intent intent = new Intent(context, DownloadsService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(intent);
                    DownloadsService.downloads(context, progressBar, count, pnps, title, button2, close);
                } else {
                    context.startService(intent);
                    DownloadsService.downloads(context, progressBar, count, pnps, title, button2, close);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
