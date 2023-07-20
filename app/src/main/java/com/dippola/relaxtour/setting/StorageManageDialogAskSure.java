package com.dippola.relaxtour.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.item.PageItem;

import java.io.File;
import java.util.ArrayList;

public class StorageManageDialogAskSure {
    public static AlertDialog alertDialog;

    private static CheckBox checkBox;
    private static Button okbtn, cancel;

    public static void storageManageDialogAskSure(Context context, ArrayList<PageItem> arrayList, RecyclerView.Adapter adapter, int i) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.storage_manage_dialog_ask_sure, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        checkBox = layout.findViewById(R.id.storage_manage_dialog_ask_sure_checkbox);
        okbtn = layout.findViewById(R.id.storage_manage_dialog_ask_sure_ok);
        cancel = layout.findViewById(R.id.storage_manage_dialog_ask_sure_cancel);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("storage_manage_checkbox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkBox.isChecked()) {
                    editor.putBoolean("isChecked", true);
                } else {
                    editor.putBoolean("isChecked", false);
                }
                editor.apply();

                StorageManageDialog.progressBar.setVisibility(View.VISIBLE);
                String path = context.getApplicationInfo().dataDir + "/cache/" + "audio" + arrayList.get(i).getTid() + ".mp3";
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    PageItem pageItem = arrayList.get(i);
                    int index = arrayList.indexOf(arrayList.get(i));
                    arrayList.remove(index);
                    adapter.notifyItemRemoved(index);
                    adapter.notifyDataSetChanged();
                    Log.d(">>>MainActivity", "deleted");
                    StorageManageAdapter.resetPage(pageItem, context);
                    if (arrayList.size() == 0) {
                        StorageManageDialog.nullScreen.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d(">>>MainActivity", "no have");
                }
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
