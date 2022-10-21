package com.dippola.relaxtour.controller;

import android.content.Context;
import android.content.Intent;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.View;

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

public class AudioController {
    public static void startTrack(Context context, PageItem pageItem) {

        int page = pageItem.getPage();
        int position = pageItem.getPosition();
        String pnp = pageItem.getPnp();
        MPList.initalMP(pnp, context, pageItem.getSeek());

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
            MPList.initalMP(pageItems.get(i).getPnp(), context, pageItems.get(i).getSeek());
            checkPP(context, pageItems.get(i).getPnp());
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
                MPList.p1p1_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "1-2":
                MPList.p1p2_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "1-3":
                MPList.p1p3_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "1-4":
                MPList.p1p4_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "1-5":
                MPList.p1p5_1.start();
                new RainController.p1t1(pp).start();
                break;
            case "1-6":
                MPList.p1p6_1.start();
                new RainController.p1t1(pp).start();
                break;


            case "2-1":
                MPList.p2p1_1.start();
                new WaterController.p2t1(pp).start();
                break;
            case "2-2":
                MPList.p2p2_1.start();
                new WaterController.p2t1(pp).start();
                break;
            case "2-3":
                MPList.p2p3_1.start();
                new WaterController.p2t1(pp).start();
                break;
            case "2-4":
                MPList.p2p4_1.start();
                new WaterController.p2t1(pp).start();
                break;
            case "2-5":
                MPList.p2p5_1.start();
                new WaterController.p2t1(pp).start();
                break;
            case "2-6":
                MPList.p2p6_1.start();
                new WaterController.p2t1(pp).start();
                break;


            case "3-1":
                MPList.p3p1_1.start();
                new WindController.p3t1(pp).start();
                break;
            case "3-2":
                MPList.p3p2_1.start();
                new WindController.p3t1(pp).start();
                break;
            case "3-3":
                MPList.p3p3_1.start();
                new WindController.p3t1(pp).start();
                break;
            case "3-4":
                MPList.p3p4_1.start();
                new WindController.p3t1(pp).start();
                break;
            case "3-5":
                MPList.p3p5_1.start();
                new WindController.p3t1(pp).start();
                break;
            case "3-6":
                MPList.p3p6_1.start();
                new WindController.p3t1(pp).start();
                break;
            case "3-7":
                MPList.p3p7_1.start();
                new WindController.p3t1(pp).start();
                break;


            case "4-1":
                MPList.p4p1_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-2":
                MPList.p4p2_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-3":
                MPList.p4p3_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-4":
                MPList.p4p4_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-5":
                MPList.p4p5_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-6":
                MPList.p4p6_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-7":
                MPList.p4p7_1.start();
                new NatureController.p4t1(pp).start();
                break;
            case "4-8":
                MPList.p4p8_1.start();
                new NatureController.p4t1(pp).start();
                break;

            case "5-1":
                MPList.p5p1_1.start();
                new ChakraController.p5t1(pp).start();
                break;
            case "5-2":
                MPList.p5p2_1.start();
                new ChakraController.p5t1(pp).start();
                break;
            case "5-3":
                MPList.p5p3_1.start();
                new ChakraController.p5t1(pp).start();
                break;
            case "5-4":
                MPList.p5p4_1.start();
                new ChakraController.p5t1(pp).start();
                break;
            case "5-5":
                MPList.p5p5_1.start();
                new ChakraController.p5t1(pp).start();
                break;
            case "5-6":
                MPList.p5p6_1.start();
                new ChakraController.p5t1(pp).start();
                break;
            case "5-7":
                MPList.p5p7_1.start();
                new ChakraController.p5t1(pp).start();
                break;

            case "6-1":
                MPList.p6p1_1.start();
                new MantraController.p6t1(pp).start();
                break;
            case "6-2":
                MPList.p6p2_1.start();
                new MantraController.p6t1(pp).start();
                break;
            case "6-3":
                MPList.p6p3_1.start();
                new MantraController.p6t1(pp).start();
                break;
            case "6-4":
                MPList.p6p4_1.start();
                new MantraController.p6t1(pp).start();
                break;
            case "6-5":
                MPList.p6p5_1.start();
                new MantraController.p6t1(pp).start();
                break;
            case "6-6":
                MPList.p6p6_1.start();
                new MantraController.p6t1(pp).start();
                break;
            case "6-7":
                MPList.p6p7_1.start();
                new MantraController.p6t1(pp).start();
                break;

            case "7-1":
                MPList.p7p1_1.start();
                new HzController.p7t1(pp).start();
                break;
            case "7-2":
                MPList.p7p2_1.start();
                new HzController.p7t1(pp).start();
                break;
            case "7-3":
                MPList.p7p3_1.start();
                new HzController.p7t1(pp).start();
                break;
            case "7-4":
                MPList.p7p4_1.start();
                new HzController.p7t1(pp).start();
                break;
            case "7-5":
                MPList.p7p5_1.start();
                new HzController.p7t1(pp).start();
                break;
            case "7-6":
                MPList.p7p6_1.start();
                new HzController.p7t1(pp).start();
                break;
            case "7-7":
                MPList.p7p7_1.start();
                new HzController.p7t1(pp).start();
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
        List<String> pnpList = new ArrayList<>();
        pnpList.add("1-1");
        pnpList.add("1-2");
        pnpList.add("1-3");
        pnpList.add("1-4");
        pnpList.add("1-5");
        pnpList.add("1-6");
        pnpList.add("2-1");
        pnpList.add("2-2");
        pnpList.add("2-3");
        pnpList.add("2-4");
        pnpList.add("2-5");
        pnpList.add("2-6");
        pnpList.add("3-1");
        pnpList.add("3-2");
        pnpList.add("3-3");
        pnpList.add("3-4");
        pnpList.add("3-5");
        pnpList.add("3-6");
        pnpList.add("3-7");
        pnpList.add("4-1");
        pnpList.add("4-2");
        pnpList.add("4-3");
        pnpList.add("4-4");
        pnpList.add("4-5");
        pnpList.add("4-6");
        pnpList.add("4-7");
        pnpList.add("4-8");
        pnpList.add("5-1");
        pnpList.add("5-2");
        pnpList.add("5-3");
        pnpList.add("5-4");
        pnpList.add("5-5");
        pnpList.add("5-6");
        pnpList.add("5-7");
        pnpList.add("6-1");
        pnpList.add("6-2");
        pnpList.add("6-3");
        pnpList.add("6-4");
        pnpList.add("6-5");
        pnpList.add("6-6");
        pnpList.add("6-7");
        pnpList.add("6-7");
        pnpList.add("7-1");
        pnpList.add("7-2");
        pnpList.add("7-3");
        pnpList.add("7-4");
        pnpList.add("7-5");
        pnpList.add("7-6");
        pnpList.add("7-7");
        MPList.initalMP(pageItem.getPnp(), context, pageItem.getSeek());
        if (pnpList.contains(pageItem.getPnp())) {
            return playingListindex0_1(pageItem.getPnp()).isPlaying() || playingListindex0_2(pageItem.getPnp()).isPlaying();
        } else {
            if (playingListindex0_1(pageItem.getPnp()) == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static MediaPlayer playingListindex0_1(String pp) {
        switch (pp) {
            case "1-1":
                return MPList.p1p1_1;
            case "1-2":
                return MPList.p1p2_1;
            case "1-3":
                return MPList.p1p3_1;
            case "1-4":
                return MPList.p1p4_1;
            case "1-5":
                return MPList.p1p5_1;
            case "1-6":
                return MPList.p1p6_1;

            case "2-1":
                return MPList.p2p1_1;
            case "2-2":
                return MPList.p2p2_1;
            case "2-3":
                return MPList.p2p3_1;
            case "2-4":
                return MPList.p2p4_1;
            case "2-5":
                return MPList.p2p5_1;
            case "2-6":
                return MPList.p2p6_1;

            case "3-1":
                return MPList.p3p1_1;
            case "3-2":
                return MPList.p3p2_1;
            case "3-3":
                return MPList.p3p3_1;
            case "3-4":
                return MPList.p3p4_1;
            case "3-5":
                return MPList.p3p5_1;
            case "3-6":
                return MPList.p3p6_1;
            case "3-7":
                return MPList.p3p7_1;

            case "4-1":
                return MPList.p4p1_1;
            case "4-2":
                return MPList.p4p2_1;
            case "4-3":
                return MPList.p4p3_1;
            case "4-4":
                return MPList.p4p4_1;
            case "4-5":
                return MPList.p4p5_1;
            case "4-6":
                return MPList.p4p6_1;
            case "4-7":
                return MPList.p4p7_1;
            case "4-8":
                return MPList.p4p8_1;

            case "5-1":
                return MPList.p5p1_1;
            case "5-2":
                return MPList.p5p2_1;
            case "5-3":
                return MPList.p5p3_1;
            case "5-4":
                return MPList.p5p4_1;
            case "5-5":
                return MPList.p5p5_1;
            case "5-6":
                return MPList.p5p6_1;
            case "5-7":
                return MPList.p5p7_1;

            case "6-1":
                return MPList.p6p1_1;
            case "6-2":
                return MPList.p6p2_1;
            case "6-3":
                return MPList.p6p3_1;
            case "6-4":
                return MPList.p6p4_1;
            case "6-5":
                return MPList.p6p5_1;
            case "6-6":
                return MPList.p6p6_1;
            case "6-7":
                return MPList.p6p7_1;

            case "7-1":
                return MPList.p7p1_1;
            case "7-2":
                return MPList.p7p2_1;
            case "7-3":
                return MPList.p7p3_1;
            case "7-4":
                return MPList.p7p4_1;
            case "7-5":
                return MPList.p7p5_1;
            case "7-6":
                return MPList.p7p6_1;
            case "7-7":
                return MPList.p7p7_1;

            default:
                return null;
        }
    }

    public static MediaPlayer playingListindex0_2(String pp) {
        switch (pp) {
            case "1-1":
                return MPList.p1p1_2;
            case "1-2":
                return MPList.p1p2_2;
            case "1-3":
                return MPList.p1p3_2;
            case "1-4":
                return MPList.p1p4_2;
            case "1-5":
                return MPList.p1p5_2;
            case "1-6":
                return MPList.p1p6_2;

            case "2-1":
                return MPList.p2p1_2;
            case "2-2":
                return MPList.p2p2_2;
            case "2-3":
                return MPList.p2p3_2;
            case "2-4":
                return MPList.p2p4_2;
            case "2-5":
                return MPList.p2p5_2;
            case "2-6":
                return MPList.p2p6_2;

            case "3-1":
                return MPList.p3p1_2;
            case "3-2":
                return MPList.p3p2_2;
            case "3-3":
                return MPList.p3p3_2;
            case "3-4":
                return MPList.p3p4_2;
            case "3-5":
                return MPList.p3p5_2;
            case "3-6":
                return MPList.p3p6_2;
            case "3-7":
                return MPList.p3p7_2;

            case "4-1":
                return MPList.p4p1_2;
            case "4-2":
                return MPList.p4p2_2;
            case "4-3":
                return MPList.p4p3_2;
            case "4-4":
                return MPList.p4p4_2;
            case "4-5":
                return MPList.p4p5_2;
            case "4-6":
                return MPList.p4p6_2;
            case "4-7":
                return MPList.p4p7_2;
            case "4-8":
                return MPList.p4p8_2;

            case "5-1":
                return MPList.p5p1_2;
            case "5-2":
                return MPList.p5p2_2;
            case "5-3":
                return MPList.p5p3_2;
            case "5-4":
                return MPList.p5p4_2;
            case "5-5":
                return MPList.p5p5_2;
            case "5-6":
                return MPList.p5p6_2;
            case "5-7":
                return MPList.p5p7_2;

            case "6-1":
                return MPList.p6p1_2;
            case "6-2":
                return MPList.p6p2_2;
            case "6-3":
                return MPList.p6p3_2;
            case "6-4":
                return MPList.p6p4_2;
            case "6-5":
                return MPList.p6p5_2;
            case "6-6":
                return MPList.p6p6_2;
            case "6-7":
                return MPList.p6p7_2;

            case "7-1":
                return MPList.p7p1_2;
            case "7-2":
                return MPList.p7p2_2;
            case "7-3":
                return MPList.p7p3_2;
            case "7-4":
                return MPList.p7p4_2;
            case "7-5":
                return MPList.p7p5_2;
            case "7-6":
                return MPList.p7p6_2;
            case "7-7":
                return MPList.p7p7_2;
            default:
                return null;
        }
    }

    public static void stopPage(int page, String pnp) {
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

    public static void setVolumn(String pp, int i) {
        Log.d("AudioController>>>", "setVoloumn");
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
        if (AudioController.playingListindex0_1(pp) != null && AudioController.playingListindex0_2(pp) != null) {
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
    }

    public static void stopPlayingList(ArrayList<PageItem> pageItem) {//playinglist에 있는 목록만 stop(page)
        for (int i = 0; i < pageItem.size(); i++) {
            if (pageItem.get(i).getPage() == 1) {
                RainController.stopPage1(pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 2) {
                WaterController.stopPage2(pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 3) {
                WindController.stopPage3(pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 4) {
                NatureController.stopPage4(pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 5) {
                ChakraController.stopChakra(pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 6) {
                MantraController.stopMantra(pageItem.get(i).getPnp());
            } else if (pageItem.get(i).getPage() == 7) {
                HzController.stopHz(pageItem.get(i).getPnp());
            }
        }
    }
}
