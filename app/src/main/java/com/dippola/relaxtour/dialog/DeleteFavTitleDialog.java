package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;

public class DeleteFavTitleDialog {
    public static AlertDialog alertDialog;
    private static Button okbtn, cancel;

    public static void deleteFavTitleDialog(Context context, String title) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.delete_fav_title_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alertDialog = builder.create();

        alertDialog.show();

        okbtn = layout.findViewById(R.id.delete_fav_title_dialog_button_ok);
        cancel = layout.findViewById(R.id.delete_fav_title_dialog_button_cancel);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.databaseHandler.removeFavList(title);
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
