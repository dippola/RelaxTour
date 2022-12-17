package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.R;

public class FixServerDialog {
    public static AlertDialog alertDialog;
    private static Button ok;
    private static TextView startTime, endTime;

    public static void showDialog(Context context, String st, String et) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.fix_server_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        ok = layout.findViewById(R.id.fix_server_dialog_btn);
        startTime = layout.findViewById(R.id.fix_server_dialog_start_time);
        endTime = layout.findViewById(R.id.fix_server_dialog_end_time);

        startTime.setText(st);
        endTime.setText(et);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
