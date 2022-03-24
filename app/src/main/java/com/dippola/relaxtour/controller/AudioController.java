package com.dippola.relaxtour.controller;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.ArrayList;
import java.util.List;

public class AudioController {
    public static void startTrack(int page, int position) {

        String pnp = page + "-" + position;

        if (page == 1) {//page1
            if (position == 1) {
                RainPage.p1p1_1.start();
            } else if (position == 2) {
                RainPage.p1p2_1.start();
            }
            new RainController.p1t1(pnp).start();
        } else if (page == 2) {//page2
            if (position == 1) {
                WindPage.p2p1_1.start();
            } else if (position == 2) {
                WindPage.p2p2_1.start();
            }
            new WindController.p2t1(pnp).start();
        } else if (page == 3) {
            ChakraController.startChakra(pnp);
        } else if (page == 4) {
            HzController.startHz(pnp);
        }
    }

    public static void startPlayingList(Context context, List<String> pp) {//playingList에 있는 곡만 다시 재생
        for (int i = 0; i < pp.size(); i++) {
            checkPP(context, pp.get(i));
            checkOpenService(context);
        }
    }

    public static void checkPP(Context context, String pp) {//곡 찾아서 재생

        Intent intent = new Intent(context, NotificationService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        switch (pp) {
            case "1-1":
                RainPage.p1p1_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "1-2":
                RainPage.p1p2_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "2-1":
                WindPage.p2p1_1.start();
                new WindController.p2t1(pp).start();
                break;
            case "2-2":
                WindPage.p2p2_1.start();
                new WindController.p2t1(pp).start();
                break;
            case "3-1":
                ChakraController.startChakra(pp);
                break;
            case "3-2":
                ChakraController.startChakra(pp);
                break;
            case "4-1":
                HzController.startHz(pp);
                break;
            case "4-2":
                HzController.startHz(pp);
                break;
        }
    }

    public static void checkOpenService(Context context) {//service 안켜져있으면 다시 시키
        if (!NotificationService.isPlaying) {
            Intent intent = new Intent(context, NotificationService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }

    public static boolean checkIsPlaying(String pp) {
        List<String> pnpList = new ArrayList<>();
        pnpList.add("1-1");
        pnpList.add("1-2");
        pnpList.add("2-1");
        pnpList.add("2-2");
        pnpList.add("3-1");
        pnpList.add("3-2");
        pnpList.add("4-1");
        pnpList.add("4-2");
        if (pnpList.contains(pp)) {
            Log.d("AudioController>>>", "1");
            return playingListindex0_1(pp).isPlaying() || playingListindex0_2(pp).isPlaying();
        } else {
            if (playingListindex0_1(pp) == null) {
                Log.d("AudioController>>>", "2");
                return false;
            } else {
                Log.d("AudioController>>>", "3");
                return true;
            }
        }

    }

    public static MediaPlayer playingListindex0_1(String pp) {
        switch (pp) {
            case "1-1":
                return RainPage.p1p1_1;
            case "1-2":
                return RainPage.p1p2_1;
            case "2-1":
                return WindPage.p2p1_1;
            case "2-2":
                return WindPage.p2p2_1;
            case "3-1":
                return ChakraPage.p3p1;
            case "3-2":
                return ChakraPage.p3p2;
            case "4-1":
                return HzPage.p4p1;
            case "4-2":
                return HzPage.p4p2;
            default:
                return null;
        }
    }

    public static MediaPlayer playingListindex0_2(String pp) {
        switch (pp) {
            case "1-1":
                return RainPage.p1p1_2;
            case "1-2":
                return RainPage.p1p2_2;
            case "2-1":
                return WindPage.p2p1_2;
            case "2-2":
                return WindPage.p2p2_2;
            case "3-1":
                return ChakraPage.p3p1;
            case "3-2":
                return ChakraPage.p3p2;
            case "4-1":
                return HzPage.p4p1;
            case "4-2":
                return HzPage.p4p2;
            default:
                return null;
        }
    }

    public static void stopPage(int page, String pnp) {
        if (page == 1) {
            RainController.stopPage1();
        } else if (page == 2) {
            WindController.stopPage2();
        } else if (page == 3) {
            ChakraController.stopChakra(page, pnp);
        } else if (page == 4) {
            HzController.stopHz(page, pnp);
        }
    }

    public static void setVolumn(String pp, int i) {
        float float0 = (float) 0.0;
        float float1 = (float) 0.023277352;
        float float2 = (float) 0.04816127;
        float float3 = (float) 0.07489007;
        float float4 = (float) 0.10375938;
        float float5 = (float) 0.13514209;
        float float6 = (float) 0.16951798;
        float float7 = (float) 0.20751876;
        float float8 = (float) 0.25;
        float float9 = (float) 0.29816127;
        float float10 = (float) 0.35375938;
        float float11 = (float) 0.41951796;
        float float12 = (float) 0.5;
        float float13 = (float) 0.60375935;
        float float14 = (float) 0.75;
        float float15 = (float) 1.0;
        if (i == 0) {
            AudioController.playingListindex0_1(pp).setVolume(float0, float0);
            AudioController.playingListindex0_2(pp).setVolume(float0, float0);
        } else if (i == 1) {
            AudioController.playingListindex0_1(pp).setVolume(float1, float1);
            AudioController.playingListindex0_2(pp).setVolume(float1, float1);
        } else if (i == 2) {
            AudioController.playingListindex0_1(pp).setVolume(float2, float2);
            AudioController.playingListindex0_2(pp).setVolume(float2, float2);
        } else if (i == 3) {
            AudioController.playingListindex0_1(pp).setVolume(float3, float3);
            AudioController.playingListindex0_2(pp).setVolume(float3, float3);
        } else if (i == 4) {
            AudioController.playingListindex0_1(pp).setVolume(float4, float4);
            AudioController.playingListindex0_2(pp).setVolume(float4, float4);
        } else if (i == 5) {
            AudioController.playingListindex0_1(pp).setVolume(float5, float5);
            AudioController.playingListindex0_2(pp).setVolume(float5, float5);
        } else if (i == 6) {
            AudioController.playingListindex0_1(pp).setVolume(float6, float6);
            AudioController.playingListindex0_2(pp).setVolume(float6, float6);
        } else if (i == 7) {
            AudioController.playingListindex0_1(pp).setVolume(float7, float7);
            AudioController.playingListindex0_2(pp).setVolume(float7, float7);
        } else if (i == 8) {
            AudioController.playingListindex0_1(pp).setVolume(float8, float8);
            AudioController.playingListindex0_2(pp).setVolume(float8, float8);
        } else if (i == 9) {
            AudioController.playingListindex0_1(pp).setVolume(float9, float9);
            AudioController.playingListindex0_2(pp).setVolume(float9, float9);
        } else if (i == 10) {
            AudioController.playingListindex0_1(pp).setVolume(float10, float10);
            AudioController.playingListindex0_2(pp).setVolume(float10, float10);
        } else if (i == 11) {
            AudioController.playingListindex0_1(pp).setVolume(float11, float11);
            AudioController.playingListindex0_2(pp).setVolume(float11, float11);
        } else if (i == 12) {
            AudioController.playingListindex0_1(pp).setVolume(float12, float12);
            AudioController.playingListindex0_2(pp).setVolume(float12, float12);
        } else if (i == 13) {
            AudioController.playingListindex0_1(pp).setVolume(float13, float13);
            AudioController.playingListindex0_2(pp).setVolume(float13, float13);
        } else if (i == 14) {
            AudioController.playingListindex0_1(pp).setVolume(float14, float14);
            AudioController.playingListindex0_2(pp).setVolume(float14, float14);
        } else if (i == 15) {
            AudioController.playingListindex0_1(pp).setVolume(float15, float15);
            AudioController.playingListindex0_2(pp).setVolume(float15, float15);
        }
    }

    public static void removeLoad() {
        if (ChakraPage.load.getVisibility() == View.VISIBLE) {
            ChakraPage.load.setVisibility(View.GONE);
        } else if (HzPage.load.getVisibility() == View.VISIBLE) {
            HzPage.load.setVisibility(View.GONE);
        }
    }

    public static void stopPlayingList(ArrayList<PageItem> pageItem) {//playinglist에 있는 목록만 stop(page)
        for (int i = 0; i < pageItem.size(); i++) {
            if (pageItem.get(i).getPage() == 1) {
                RainController.stopPage1();
            } else if (pageItem.get(i).getPage() == 2) {
                WindController.stopPage2();
            } else if (pageItem.get(i).getPage() == 3) {
                ChakraController.stopChakra(pageItem.get(i).getPage(), pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 4) {
                HzController.stopHz(pageItem.get(i).getPage(), pageItem.get(i).getPnp());
            }
        }
    }
}
