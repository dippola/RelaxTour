package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;

public class AddFavDialog {
    public static AlertDialog alertDialog;

    private static EditText editText;
    private static Button okbtn, cancel;

    public static void addTitleDialog(Context context) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.add_title_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        editText = layout.findViewById(R.id.add_title_edittext);
        okbtn = layout.findViewById(R.id.add_title_button_ok);
        cancel = layout.findViewById(R.id.add_title_button_cancel);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.bottomSheetPlayList.size() != 0) {
                    if (editText.getText().toString().length() != 0) {
                        MainActivity.databaseHandler.checkTitleAlready(alertDialog.getContext(), editText.getText().toString());
                    } else {
                        Toast.makeText(context, "please edit play list name", Toast.LENGTH_SHORT).show();
                    }
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
