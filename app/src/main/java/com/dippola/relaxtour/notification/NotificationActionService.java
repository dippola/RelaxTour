package com.dippola.relaxtour.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.controller.ChakraController;
import com.dippola.relaxtour.controller.HzController;
import com.dippola.relaxtour.controller.MantraController;
import com.dippola.relaxtour.controller.NatureController;
import com.dippola.relaxtour.controller.RainController;
import com.dippola.relaxtour.controller.WaterController;
import com.dippola.relaxtour.controller.WindController;
import com.dippola.relaxtour.pages.item.PageItem;
import com.dippola.relaxtour.service.TimerService;

import java.util.ArrayList;
import java.util.List;

public class NotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ac = intent.getAction();
        switch (ac) {
            case DefaultNotification.ACTION_PLAY:
                checkOpenService(context);
                if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0), context)) {//재생중
                    MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
                    ArrayList<PageItem> page = new ArrayList<>();
                    for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                        page.add(MainActivity.bottomSheetPlayList.get(i));
                        if (i == MainActivity.bottomSheetPlayList.size() - 1) {
                            AudioController.stopPlayingList(page);
                            DefaultNotification.defauleNotification(context);
                        }
                    }
                } else {//재생중 아님
                    MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
                    List<PageItem> pageItems = new ArrayList<>();
                    for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                        pageItems.add(MainActivity.bottomSheetPlayList.get(i));
                        if (i == MainActivity.bottomSheetPlayList.size() - 1) {
                            //playinglist start
                            AudioController.startPlayingList(context, pageItems);
                        }
                    }
                }
                break;
            case DefaultNotification.ACTION_CLOSE:
                Log.d("NotificationAction>>>", "1");
                if (MainActivity.bottomSheetPlayList.size() != 0) {
                    MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
                    if (NotificationService.isPlaying) {
                        context.stopService(new Intent(context, NotificationService.class));
                    }
                    if (TimerService.isCount) {
                        context.stopService(new Intent(context, TimerService.class));
                    }
                    stopAllSound();
                }
                break;
        }
    }

    private void stopAllSound() {
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            stopPage(MainActivity.bottomSheetPlayList.get(i).getPage(), MainActivity.bottomSheetPlayList.get(i).getPnp());
        }
    }

    private void stopPage(int page, String pnp) {
        if (page == 1) {
            RainController.stopPage1(pnp);
        } else if (page == 2) {
            WaterController.stopPage2(pnp);
        } else if (page == 3) {
            WindController.stopPage3(pnp);
        } else if (page == 4) {
            NatureController.stopPage4(pnp);
        } else if (page == 5) {
            ChakraController.stopChakra(pnp);
        } else if (page == 6) {
            MantraController.stopMantra(pnp);
        } else if (page == 7) {
            HzController.stopHz(pnp);
        }
    }

    private void checkOpenService(Context context) {
        if (!NotificationService.isPlaying) {
            Intent intent = new Intent(context, NotificationService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }
}
