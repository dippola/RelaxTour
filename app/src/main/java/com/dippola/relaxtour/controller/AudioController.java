package com.dippola.relaxtour.controller;

import android.content.Context;
import android.content.Intent;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.ArrayList;
import java.util.List;

@Keep
public class AudioController {
    public static void startTrack(Context context, PageItem pageItem) {

        int page = pageItem.getPage();
        int position = pageItem.getPosition();
        String pnp = pageItem.getPage() + "-" + pageItem.getPosition();
        MPList.initalMP(pageItem.getTid(), context, pageItem.getSeek());

        if (page == 1) {
            if (position == 1) {
                MPList.p1p1_1.start();
            } else if (position == 2) {
                MPList.p1p2_1.start();
            } else if (position == 3) {
                MPList.p1p3_1.start();
            } else if (position == 4) {
                MPList.p1p4_1.start();
            } else if (position == 5) {
                MPList.p1p5_1.start();
            } else if (position == 6) {
                MPList.p1p6_1.start();
            }
            new RainController.p1t1(pnp).start();
        } else if (page == 2) {
            if (position == 1) {
                MPList.p2p1_1.start();
            } else if (position == 2) {
                MPList.p2p2_1.start();
            } else if (position == 3) {
                MPList.p2p3_1.start();
            } else if (position == 4) {
                MPList.p2p4_1.start();
            } else if (position == 5) {
                MPList.p2p5_1.start();
            } else if (position == 6) {
                MPList.p2p6_1.start();
            }
            new WaterController.p2t1(pnp).start();
        } else if (page == 3) {
            if (position == 1) {
                MPList.p3p1_1.start();
            } else if (position == 2) {
                MPList.p3p2_1.start();
            } else if (position == 3) {
                MPList.p3p3_1.start();
            } else if (position == 4) {
                MPList.p3p4_1.start();
            } else if (position == 5) {
                MPList.p3p5_1.start();
            } else if (position == 6) {
                MPList.p3p6_1.start();
            } else if (position == 7) {
                MPList.p3p7_1.start();
            }
            new WindController.p3t1(pnp).start();
        } else if (page == 4) {
            if (position == 1) {
                MPList.p4p1_1.start();
            } else if (position == 2) {
                MPList.p4p2_1.start();
            } else if (position == 3) {
                MPList.p4p3_1.start();
            } else if (position == 4) {
                MPList.p4p4_1.start();
            } else if (position == 5) {
                MPList.p4p5_1.start();
            } else if (position == 6) {
                MPList.p4p6_1.start();
            } else if (position == 7) {
                MPList.p4p7_1.start();
            } else if (position == 8) {
                MPList.p4p8_1.start();
            }
            new NatureController.p4t1(pnp).start();
        } else if (page == 5) {
            if (position == 1) {
                MPList.p5p1_1.start();
            } else if (position == 2) {
                MPList.p5p2_1.start();
            } else if (position == 3) {
                MPList.p5p3_1.start();
            } else if (position == 4) {
                MPList.p5p4_1.start();
            } else if (position == 5) {
                MPList.p5p5_1.start();
            } else if (position == 6) {
                MPList.p5p6_1.start();
            } else if (position == 7) {
                MPList.p5p7_1.start();
            }
            new ChakraController.p5t1(pnp).start();
        } else if (page == 6) {
            if (position == 1) {
                MPList.p6p1_1.start();
            } else if (position == 2) {
                MPList.p6p2_1.start();
            } else if (position == 3) {
                MPList.p6p3_1.start();
            } else if (position == 4) {
                MPList.p6p4_1.start();
            } else if (position == 5) {
                MPList.p6p5_1.start();
            } else if (position == 6) {
                MPList.p6p6_1.start();
            } else if (position == 7) {
                MPList.p6p7_1.start();
            }
            new MantraController.p6t1(pnp).start();
        } else if (page == 7) {
            if (position == 1) {
                MPList.p7p1_1.start();
            } else if (position == 2) {
                MPList.p7p2_1.start();
            } else if (position == 3) {
                MPList.p7p3_1.start();
            } else if (position == 4) {
                MPList.p7p4_1.start();
            } else if (position == 5) {
                MPList.p7p5_1.start();
            } else if (position == 6) {
                MPList.p7p6_1.start();
            } else if (position == 7) {
                MPList.p7p7_1.start();
            }
            new HzController.p7t1(pnp).start();
        }
    }

    public static void startPlayingList(Context context, List<PageItem> pageItems) {//playingList에 있는 곡만 다시 재생
        for (int i = 0; i < pageItems.size(); i++) {
            MPList.initalMP(pageItems.get(i).getTid(), context, pageItems.get(i).getSeek());
            checkPP(context, pageItems.get(i).getTid());
            checkOpenService(context);
        }
    }

    public static void checkPP(Context context, String tid) {//곡 찾아서 재생

        Intent intent = new Intent(context, NotificationService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        switch (tid) {
            case "1-1":
                MPList.p1p1_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "1-2":
                MPList.p1p2_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "1-3":
                MPList.p1p3_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "1-4":
                MPList.p1p4_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "1-5":
                MPList.p1p5_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "1-6":
                MPList.p1p6_1.start();
                new RainController.p1t1(tid).start();
                break;


            case "2-1":
                MPList.p2p1_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "2-2":
                MPList.p2p2_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "2-3":
                MPList.p2p3_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "2-4":
                MPList.p2p4_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "2-5":
                MPList.p2p5_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "2-6":
                MPList.p2p6_1.start();
                new WaterController.p2t1(tid).start();
                break;


            case "3-1":
                MPList.p3p1_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "3-2":
                MPList.p3p2_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "3-3":
                MPList.p3p3_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "3-4":
                MPList.p3p4_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "3-5":
                MPList.p3p5_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "3-6":
                MPList.p3p6_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "3-7":
                MPList.p3p7_1.start();
                new WindController.p3t1(tid).start();
                break;


            case "4-1":
                MPList.p4p1_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-2":
                MPList.p4p2_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-3":
                MPList.p4p3_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-4":
                MPList.p4p4_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-5":
                MPList.p4p5_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-6":
                MPList.p4p6_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-7":
                MPList.p4p7_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "4-8":
                MPList.p4p8_1.start();
                new NatureController.p4t1(tid).start();
                break;

            case "5-1":
                MPList.p5p1_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "5-2":
                MPList.p5p2_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "5-3":
                MPList.p5p3_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "5-4":
                MPList.p5p4_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "5-5":
                MPList.p5p5_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "5-6":
                MPList.p5p6_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "5-7":
                MPList.p5p7_1.start();
                new ChakraController.p5t1(tid).start();
                break;

            case "6-1":
                MPList.p6p1_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "6-2":
                MPList.p6p2_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "6-3":
                MPList.p6p3_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "6-4":
                MPList.p6p4_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "6-5":
                MPList.p6p5_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "6-6":
                MPList.p6p6_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "6-7":
                MPList.p6p7_1.start();
                new MantraController.p6t1(tid).start();
                break;

            case "7-1":
                MPList.p7p1_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "7-2":
                MPList.p7p2_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "7-3":
                MPList.p7p3_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "7-4":
                MPList.p7p4_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "7-5":
                MPList.p7p5_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "7-6":
                MPList.p7p6_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "7-7":
                MPList.p7p7_1.start();
                new HzController.p7t1(tid).start();
                break;

//            case "4-1":
//                NaturePage.p4p1_1.start();
//                new NatureController


//            case "4-1":
//                HzController.startHz(pp);
//                break;
//            case "4-2":
//                HzController.startHz(pp);
//                break;
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

    public static boolean checkIsPlaying(PageItem pageItem, Context context) {//pp가 재생중인지 반환
        List<String> tidList = new ArrayList<>();
        tidList.add("00001");
        tidList.add("00002");
        tidList.add("00003");
        tidList.add("00004");
        tidList.add("00005");
        tidList.add("00006");
        tidList.add("00007");
        tidList.add("00008");
        tidList.add("00009");
        tidList.add("00010");
        tidList.add("00011");
        tidList.add("00012");
        tidList.add("00013");
        tidList.add("00014");
        tidList.add("00015");
        tidList.add("00016");
        tidList.add("00017");
        tidList.add("00018");
        tidList.add("00019");
        tidList.add("00020");
        tidList.add("00021");
        tidList.add("00022");
        tidList.add("00023");
        tidList.add("00024");
        tidList.add("00025");
        tidList.add("00026");
        tidList.add("00027");
        tidList.add("00028");
        tidList.add("00029");
        tidList.add("00030");
        tidList.add("00031");
        tidList.add("00032");
        tidList.add("00033");
        tidList.add("00034");
        tidList.add("00035");
        tidList.add("00036");
        tidList.add("00037");
        tidList.add("00038");
        tidList.add("00039");
        tidList.add("00040");
        tidList.add("00041");
        tidList.add("00042");
        tidList.add("00043");
        tidList.add("00044");
        tidList.add("00045");
        tidList.add("00046");
        tidList.add("00047");
        tidList.add("00048");
        MPList.initalMP(pageItem.getTid(), context, pageItem.getSeek());
        if (tidList.contains(pageItem.getTid())) {
            return playingListindex0_1(pageItem.getTid()).isPlaying() || playingListindex0_2(pageItem.getTid()).isPlaying();
        } else {
            if (playingListindex0_1(pageItem.getTid()) == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static MediaPlayer playingListindex0_1(String tid) {
        switch (tid) {
            case "00001":
                return MPList.p1p1_1;
            case "00002":
                return MPList.p1p2_1;
            case "00003":
                return MPList.p1p3_1;
            case "00004":
                return MPList.p1p4_1;
            case "00005":
                return MPList.p1p5_1;
            case "00006":
                return MPList.p1p6_1;

            case "00007":
                return MPList.p2p1_1;
            case "00008":
                return MPList.p2p2_1;
            case "00009":
                return MPList.p2p3_1;
            case "00010":
                return MPList.p2p4_1;
            case "00011":
                return MPList.p2p5_1;
            case "00012":
                return MPList.p2p6_1;

            case "00013":
                return MPList.p3p1_1;
            case "00014":
                return MPList.p3p2_1;
            case "00015":
                return MPList.p3p3_1;
            case "00016":
                return MPList.p3p4_1;
            case "00017":
                return MPList.p3p5_1;
            case "00018":
                return MPList.p3p6_1;
            case "00019":
                return MPList.p3p7_1;

            case "00020":
                return MPList.p4p1_1;
            case "00021":
                return MPList.p4p2_1;
            case "00022":
                return MPList.p4p3_1;
            case "00023":
                return MPList.p4p4_1;
            case "00024":
                return MPList.p4p5_1;
            case "00025":
                return MPList.p4p6_1;
            case "00026":
                return MPList.p4p7_1;
            case "00027":
                return MPList.p4p8_1;

            case "00028":
                return MPList.p5p1_1;
            case "00029":
                return MPList.p5p2_1;
            case "00030":
                return MPList.p5p3_1;
            case "00031":
                return MPList.p5p4_1;
            case "00032":
                return MPList.p5p5_1;
            case "00033":
                return MPList.p5p6_1;
            case "00034":
                return MPList.p5p7_1;

            case "00035":
                return MPList.p6p1_1;
            case "00036":
                return MPList.p6p2_1;
            case "00037":
                return MPList.p6p3_1;
            case "00038":
                return MPList.p6p4_1;
            case "00039":
                return MPList.p6p5_1;
            case "00040":
                return MPList.p6p6_1;
            case "00041":
                return MPList.p6p7_1;

            case "00042":
                return MPList.p7p1_1;
            case "00043":
                return MPList.p7p2_1;
            case "00044":
                return MPList.p7p3_1;
            case "00045":
                return MPList.p7p4_1;
            case "00046":
                return MPList.p7p5_1;
            case "00047":
                return MPList.p7p6_1;
            case "00048":
                return MPList.p7p7_1;

            default:
                return null;
        }
    }

    public static MediaPlayer playingListindex0_2(String pp) {
        switch (pp) {
            case "00001":
                return MPList.p1p1_2;
            case "00002":
                return MPList.p1p2_2;
            case "00003":
                return MPList.p1p3_2;
            case "00004":
                return MPList.p1p4_2;
            case "00005":
                return MPList.p1p5_2;
            case "00006":
                return MPList.p1p6_2;

            case "00007":
                return MPList.p2p1_2;
            case "00008":
                return MPList.p2p2_2;
            case "00009":
                return MPList.p2p3_2;
            case "00010":
                return MPList.p2p4_2;
            case "00011":
                return MPList.p2p5_2;
            case "00012":
                return MPList.p2p6_2;

            case "00013":
                return MPList.p3p1_2;
            case "00014":
                return MPList.p3p2_2;
            case "00015":
                return MPList.p3p3_2;
            case "00016":
                return MPList.p3p4_2;
            case "00017":
                return MPList.p3p5_2;
            case "00018":
                return MPList.p3p6_2;
            case "00019":
                return MPList.p3p7_2;

            case "00020":
                return MPList.p4p1_2;
            case "00021":
                return MPList.p4p2_2;
            case "00022":
                return MPList.p4p3_2;
            case "00023":
                return MPList.p4p4_2;
            case "00024":
                return MPList.p4p5_2;
            case "00025":
                return MPList.p4p6_2;
            case "00026":
                return MPList.p4p7_2;
            case "00027":
                return MPList.p4p8_2;

            case "00028":
                return MPList.p5p1_2;
            case "00029":
                return MPList.p5p2_2;
            case "00030":
                return MPList.p5p3_2;
            case "00031":
                return MPList.p5p4_2;
            case "00032":
                return MPList.p5p5_2;
            case "00033":
                return MPList.p5p6_2;
            case "00034":
                return MPList.p5p7_2;

            case "00035":
                return MPList.p6p1_2;
            case "00036":
                return MPList.p6p2_2;
            case "00037":
                return MPList.p6p3_2;
            case "00038":
                return MPList.p6p4_2;
            case "00039":
                return MPList.p6p5_2;
            case "00040":
                return MPList.p6p6_2;
            case "00041":
                return MPList.p6p7_2;

            case "00042":
                return MPList.p7p1_2;
            case "00043":
                return MPList.p7p2_2;
            case "00044":
                return MPList.p7p3_2;
            case "00045":
                return MPList.p7p4_2;
            case "00046":
                return MPList.p7p5_2;
            case "00047":
                return MPList.p7p6_2;
            case "00048":
                return MPList.p7p7_2;
            default:
                return null;
        }
    }

    public static void stopPage(int page, int position) {
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

    public static void setVolumn(String tid, int i) {
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
        if (AudioController.playingListindex0_1(tid) != null && AudioController.playingListindex0_2(tid) != null) {
            if (i == 0) {
                AudioController.playingListindex0_1(tid).setVolume(float0, float0);
                AudioController.playingListindex0_2(tid).setVolume(float0, float0);
            } else if (i == 1) {
                AudioController.playingListindex0_1(tid).setVolume(float1, float1);
                AudioController.playingListindex0_2(tid).setVolume(float1, float1);
            } else if (i == 2) {
                AudioController.playingListindex0_1(tid).setVolume(float2, float2);
                AudioController.playingListindex0_2(tid).setVolume(float2, float2);
            } else if (i == 3) {
                AudioController.playingListindex0_1(tid).setVolume(float3, float3);
                AudioController.playingListindex0_2(tid).setVolume(float3, float3);
            } else if (i == 4) {
                AudioController.playingListindex0_1(tid).setVolume(float4, float4);
                AudioController.playingListindex0_2(tid).setVolume(float4, float4);
            } else if (i == 5) {
                AudioController.playingListindex0_1(tid).setVolume(float5, float5);
                AudioController.playingListindex0_2(tid).setVolume(float5, float5);
            } else if (i == 6) {
                AudioController.playingListindex0_1(tid).setVolume(float6, float6);
                AudioController.playingListindex0_2(tid).setVolume(float6, float6);
            } else if (i == 7) {
                AudioController.playingListindex0_1(tid).setVolume(float7, float7);
                AudioController.playingListindex0_2(tid).setVolume(float7, float7);
            } else if (i == 8) {
                AudioController.playingListindex0_1(tid).setVolume(float8, float8);
                AudioController.playingListindex0_2(tid).setVolume(float8, float8);
            } else if (i == 9) {
                AudioController.playingListindex0_1(tid).setVolume(float9, float9);
                AudioController.playingListindex0_2(tid).setVolume(float9, float9);
            } else if (i == 10) {
                AudioController.playingListindex0_1(tid).setVolume(float10, float10);
                AudioController.playingListindex0_2(tid).setVolume(float10, float10);
            } else if (i == 11) {
                AudioController.playingListindex0_1(tid).setVolume(float11, float11);
                AudioController.playingListindex0_2(tid).setVolume(float11, float11);
            } else if (i == 12) {
                AudioController.playingListindex0_1(tid).setVolume(float12, float12);
                AudioController.playingListindex0_2(tid).setVolume(float12, float12);
            } else if (i == 13) {
                AudioController.playingListindex0_1(tid).setVolume(float13, float13);
                AudioController.playingListindex0_2(tid).setVolume(float13, float13);
            } else if (i == 14) {
                AudioController.playingListindex0_1(tid).setVolume(float14, float14);
                AudioController.playingListindex0_2(tid).setVolume(float14, float14);
            } else if (i == 15) {
                AudioController.playingListindex0_1(tid).setVolume(float15, float15);
                AudioController.playingListindex0_2(tid).setVolume(float15, float15);
            }
        }
    }

    public static void stopPlayingList(ArrayList<PageItem> pageItem) {//playinglist에 있는 목록만 stop(page)
        for (int i = 0; i < pageItem.size(); i++) {
            if (pageItem.get(i).getPage() == 1) {
                RainController.stopPage1(pageItem.get(i).getPosition());
            } else if (pageItem.get(i).getPage() == 2) {
                WaterController.stopPage2(pageItem.get(i).getPosition());
            } else if (pageItem.get(i).getPage() == 3) {
                WindController.stopPage3(pageItem.get(i).getPosition());
            } else if (pageItem.get(i).getPage() == 4) {
                NatureController.stopPage4(pageItem.get(i).getPosition());
            } else if (pageItem.get(i).getPage() == 5) {
                ChakraController.stopChakra(pageItem.get(i).getPosition());
            } else if (pageItem.get(i).getPage() == 6) {
                MantraController.stopMantra(pageItem.get(i).getPosition());
            } else if (pageItem.get(i).getPage() == 7) {
                HzController.stopHz(pageItem.get(i).getPosition());
            }
        }
    }
}
