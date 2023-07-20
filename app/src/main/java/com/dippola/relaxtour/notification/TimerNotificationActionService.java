package com.dippola.relaxtour.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

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
import com.dippola.relaxtour.timer.Timer2;

import java.util.ArrayList;
import java.util.List;

public class TimerNotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ac = intent.getAction();
        switch (ac) {
            case DefaultNotification.ACTION_PLAY:
                checkOpenService(context);
                if (MainActivity.bottomSheetPlayList.size() != 0) {
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
                } else {
                    Toast.makeText(context, "null playinglist", Toast.LENGTH_SHORT).show();
                }
                break;
            case DefaultNotification.ACTION_CLOSE:
                if (MainActivity.bottomSheetPlayList.size() != 0) {
                    MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
                    stopAllSound();
                }
                if (NotificationService.isPlaying) {
                    context.stopService(new Intent(context, NotificationService.class));
                }
                if (TimerService.isCount) {
                    Log.d("TimerAction>>>", "1");
                    Timer2.stopTimer(context);
                }
                break;
        }
    }

    private void stopAllSound() {
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            stopPage(MainActivity.bottomSheetPlayList.get(i).getPage(), MainActivity.bottomSheetPlayList.get(i).getPosition());
        }
    }

    private void stopPage(int page, int position) {
        if (page == 1) {
            RainController.stopPage1(position);
        } else if (page == 2) {
            WaterController.stopPage2(position);
        } else if (page == 3) {
            WindController.stopPage3(position);
        } else if (page == 4) {
            NatureController.stopPage4(position);
        } else if (page == 5) {
            ChakraController.stopChakra(position);
        } else if (page == 6) {
            MantraController.stopMantra(position);
        } else if (page == 7) {
            HzController.stopHz(position);
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
