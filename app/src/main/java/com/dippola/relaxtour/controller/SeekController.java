package com.dippola.relaxtour.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.adapter.FavListAdapter;
import com.dippola.relaxtour.pages.adapter.FavTitleAdapter;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.List;

@Keep
public class SeekController {

    public final static int MAX_VOLUME = 16;

    public static boolean pageMoving;
    public static boolean bottomMoving;
    public static boolean favMoving;

    public static void changeVolumn(String tid, float volumn) {
        if (AudioController.playingListindex0_1(tid) != null) {
            AudioController.playingListindex0_1(tid).setVolume(volumn, volumn);
            AudioController.playingListindex0_2(tid).setVolume(volumn, volumn);
        }
    }

    public static void changeSeekInPage(Context context, PageItem pageItem, int progress) {

        if (MainActivity.bottomSheetPlayList.size() != 0 ) {
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                if (MainActivity.bottomSheetPlayList.get(i).getTid().equals(pageItem.getTid())) {
                    MainActivity.bottomSheetPlayList.get(i).setSeek(progress);
                    MainActivity.bottomSheetAdapter.notifyItemChanged(i);
                    MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }

        MainActivity.databaseHandler.changePageSeek(progress, pageItem.getTid());
    }

    public static void changeSeekInBottom(Context context, PageItem pageItem, int progress) {
        int position = pageItem.getPosition() - 1;
        if (pageItem.getPage() == 1 && RainPage.arrayList.size() != 0) {
            RainPage.arrayList.get(position).setSeek(progress);
            RainPage.adapter.notifyItemChanged(position);
            RainPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 2 && WaterPage.arrayList.size() != 0) {
            WaterPage.arrayList.get(position).setSeek(progress);
            WaterPage.adapter.notifyItemChanged(position);
            WaterPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 3 && WindPage.arrayList.size() != 0) {
            WindPage.arrayList.get(position).setSeek(progress);
            WindPage.adapter.notifyItemChanged(position);
            WindPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 4 && NaturePage.arrayList.size() != 0) {
            NaturePage.arrayList.get(position).setSeek(progress);
            NaturePage.adapter.notifyItemChanged(position);
            NaturePage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 5 && ChakraPage.arrayList.size() != 0) {
            ChakraPage.arrayList.get(position).setSeek(progress);
            ChakraPage.adapter.notifyItemChanged(position);
            ChakraPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 6 && MantraPage.arrayList.size() != 0) {
            MantraPage.arrayList.get(position).setSeek(progress);
            MantraPage.adapter.notifyItemChanged(position);
            MantraPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 6 && HzPage.arrayList.size() != 0) {
            HzPage.arrayList.get(position).setSeek(progress);
            HzPage.adapter.notifyItemChanged(position);
            HzPage.adapter.notifyDataSetChanged();
        }

        MainActivity.databaseHandler.changePageSeek(progress, pageItem.getTid());
    }
}
