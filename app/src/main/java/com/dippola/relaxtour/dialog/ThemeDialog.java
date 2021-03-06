package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.ThemeHelper;

//public class ThemeDialog extends AppCompatActivity {
//
//    RadioGroup rg;
//    RadioButton light, dark, system;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.theme_dialog);
//
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
////        layoutParams.copyFrom(getWindow().getAttributes());
////        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
////        layoutParams.dimAmount = 0.7f;
////
////        //set dialog size
////        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
////        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        getWindow().setAttributes(layoutParams);
//
//        rg = findViewById(R.id.theme_dialog_radio_group);
//        light = findViewById(R.id.theme_dialog_light);
//        dark = findViewById(R.id.theme_dialog_dark);
//        system = findViewById(R.id.theme_dialog_system);
//
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                Log.d("Themehelper>>>", "changed: " + i);
//            }
//        });
//    }
//}

public class ThemeDialog {
    public static RadioGroup rg;
    public static RadioButton light, dark, system;
    public static AlertDialog alertDialog;
    public static Button okbtn;
    public static void themeDialog(Context context) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.theme_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        alertDialog.show();


        rg = layout.findViewById(R.id.theme_dialog_radio_group);
        light = layout.findViewById(R.id.theme_dialog_light);
        dark = layout.findViewById(R.id.theme_dialog_dark);
        system = layout.findViewById(R.id.theme_dialog_system);
        okbtn = layout.findViewById(R.id.theme_dialog_okbtn);

        SharedPreferences sharedPreferences = context.getSharedPreferences("modeTable", Context.MODE_PRIVATE);
        String mode = sharedPreferences.getString("mode", "default");
        if (mode.equals("light")) {
            light.setChecked(true);
        } else if (mode.equals("dark")) {
            dark.setChecked(true);
        } else {
            system.setChecked(true);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.theme_dialog_light) {
                    ThemeHelper.applyTheme("light");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mode", "light");
                    editor.apply();
                    alertDialog.cancel();
                } else if (i == R.id.theme_dialog_dark) {
                    ThemeHelper.applyTheme("dark");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mode", "dark");
                    editor.apply();
                    alertDialog.cancel();
                } else {
                    ThemeHelper.applyTheme("system");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mode", "system");
                    editor.apply();
                    alertDialog.cancel();
                }
            }
        });

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }
}