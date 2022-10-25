package com.dippola.relaxtour.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.adapter.PageAdapter;
import com.dippola.relaxtour.service.DownloadsService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class AskDeleteAllPlaylistDialog {
    public static AlertDialog alertDialog;
    private static Button okbtn, cancel;
    public static void askDeleteAllPlaylistDialog(Context context) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.ask_delete_all_playlist_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        okbtn = layout.findViewById(R.id.ask_delete_all_playlist_dialog_ok);
        cancel = layout.findViewById(R.id.ask_delete_all_playlist_dialog_cancel);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NotificationService.isPlaying) {
                    context.stopService(new Intent(context, NotificationService.class));
                    for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                        AudioController.stopPage(MainActivity.bottomSheetPlayList.get(i).getPage(), MainActivity.bottomSheetPlayList.get(i).getPnp());
                    }
                    MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
                }
                MainActivity.databaseHandler.deleteAllPlayinglistWhenDeleteInBottomSheet();
                for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                    changeIsPlay1(MainActivity.bottomSheetPlayList.get(i).getPage(), MainActivity.bottomSheetPlayList.get(i).getPosition());
                    if (MainActivity.bottomSheetPlayList.get(i).getPage() == 4 && NaturePage.arrayList.size() != 0) {
                        PageAdapter.page4Count();
                    }
                }
                MainActivity.bottomSheetPlayList.clear();
                MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                if (MainActivity.bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    MainActivity.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    MainActivity.upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_up);
                }
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private static void changeIsPlay1(int page, int position) {
        if (page == 1 && RainPage.arrayList.size() != 0) {
            RainPage.arrayList.get(position - 1).setIsplay(1);
            RainPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 2 && WaterPage.arrayList.size() != 0) {
            WaterPage.arrayList.get(position - 1).setIsplay(1);
            WaterPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 3 && WindPage.arrayList.size() != 0) {
            WindPage.arrayList.get(position - 1).setIsplay(1);
            WindPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 4 && NaturePage.arrayList.size() != 0) {
            NaturePage.arrayList.get(position - 1).setIsplay(1);
            NaturePage.adapter.notifyItemChanged(position - 1);
            NaturePage.adapter.notifyDataSetChanged();
        } else if (page == 5 && ChakraPage.arrayList.size() != 0) {
            ChakraPage.arrayList.get(position - 1).setIsplay(1);
            ChakraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 6 && MantraPage.arrayList.size() != 0) {
            MantraPage.arrayList.get(position - 1).setIsplay(1);
            MantraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 7 && HzPage.arrayList.size() != 0) {
            HzPage.arrayList.get(position - 1).setIsplay(1);
            HzPage.adapter.notifyItemChanged(position - 1);
        }
    }
}
