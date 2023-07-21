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
        String tid = pageItem.getTid();
        MPList.initalMP(pageItem.getTid(), context, pageItem.getSeek());

        if (page == 1) {
            if (position == 1) {
                MPList.t00001_1.start();
            } else if (position == 2) {
                MPList.t00002_1.start();
            } else if (position == 3) {
                MPList.t00003_1.start();
            } else if (position == 4) {
                MPList.t00004_1.start();
            } else if (position == 5) {
                MPList.t00005_1.start();
            } else if (position == 6) {
                MPList.t00006_1.start();
            }
            new RainController.p1t1(tid).start();
        } else if (page == 2) {
            if (position == 1) {
                MPList.t00007_1.start();
            } else if (position == 2) {
                MPList.t00008_1.start();
            } else if (position == 3) {
                MPList.t00009_1.start();
            } else if (position == 4) {
                MPList.t00010_1.start();
            } else if (position == 5) {
                MPList.t00011_1.start();
            } else if (position == 6) {
                MPList.t00012_1.start();
            }
            new WaterController.p2t1(tid).start();
        } else if (page == 3) {
            if (position == 1) {
                MPList.t00013_1.start();
            } else if (position == 2) {
                MPList.t00014_1.start();
            } else if (position == 3) {
                MPList.t00015_1.start();
            } else if (position == 4) {
                MPList.t00016_1.start();
            } else if (position == 5) {
                MPList.t00017_1.start();
            } else if (position == 6) {
                MPList.t00018_1.start();
            } else if (position == 7) {
                MPList.t00019_1.start();
            }
            new WindController.p3t1(tid).start();
        } else if (page == 4) {
            if (position == 1) {
                MPList.t00020_1.start();
            } else if (position == 2) {
                MPList.t00021_1.start();
            } else if (position == 3) {
                MPList.t00022_1.start();
            } else if (position == 4) {
                MPList.t00023_1.start();
            } else if (position == 5) {
                MPList.t00024_1.start();
            } else if (position == 6) {
                MPList.t00025_1.start();
            } else if (position == 7) {
                MPList.t00026_1.start();
            } else if (position == 8) {
                MPList.t00027_1.start();
            }
            new NatureController.p4t1(tid).start();
        } else if (page == 5) {
            if (position == 1) {
                MPList.t00028_1.start();
            } else if (position == 2) {
                MPList.t00029_1.start();
            } else if (position == 3) {
                MPList.t00030_1.start();
            } else if (position == 4) {
                MPList.t00031_1.start();
            } else if (position == 5) {
                MPList.t00032_1.start();
            } else if (position == 6) {
                MPList.t00033_1.start();
            } else if (position == 7) {
                MPList.t00034_1.start();
            }
            new ChakraController.p5t1(tid).start();
        } else if (page == 6) {
            if (position == 1) {
                MPList.t00035_1.start();
            } else if (position == 2) {
                MPList.t00036_1.start();
            } else if (position == 3) {
                MPList.t00037_1.start();
            } else if (position == 4) {
                MPList.t00038_1.start();
            } else if (position == 5) {
                MPList.t00039_1.start();
            } else if (position == 6) {
                MPList.t00040_1.start();
            } else if (position == 7) {
                MPList.t00041_1.start();
            }
            new MantraController.p6t1(tid).start();
        } else if (page == 7) {
            if (position == 1) {
                MPList.t00042_1.start();
            } else if (position == 2) {
                MPList.t00043_1.start();
            } else if (position == 3) {
                MPList.t00044_1.start();
            } else if (position == 4) {
                MPList.t00045_1.start();
            } else if (position == 5) {
                MPList.t00046_1.start();
            } else if (position == 6) {
                MPList.t00047_1.start();
            } else if (position == 7) {
                MPList.t00048_1.start();
            }
            new HzController.p7t1(tid).start();
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
            case "00001":
                MPList.t00001_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "00002":
                MPList.t00002_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "00003":
                MPList.t00003_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "00004":
                MPList.t00004_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "00005":
                MPList.t00005_1.start();
                new RainController.p1t1(tid).start();
                break;
            case "00006":
                MPList.t00006_1.start();
                new RainController.p1t1(tid).start();
                break;


            case "00007":
                MPList.t00007_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "00008":
                MPList.t00008_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "00009":
                MPList.t00009_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "00010":
                MPList.t00010_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "00011":
                MPList.t00011_1.start();
                new WaterController.p2t1(tid).start();
                break;
            case "00012":
                MPList.t00012_1.start();
                new WaterController.p2t1(tid).start();
                break;


            case "00013":
                MPList.t00013_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "00014":
                MPList.t00014_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "00015":
                MPList.t00015_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "00016":
                MPList.t00016_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "00017":
                MPList.t00017_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "00018":
                MPList.t00018_1.start();
                new WindController.p3t1(tid).start();
                break;
            case "00019":
                MPList.t00019_1.start();
                new WindController.p3t1(tid).start();
                break;


            case "00020":
                MPList.t00020_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00021":
                MPList.t00021_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00022":
                MPList.t00022_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00023":
                MPList.t00023_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00024":
                MPList.t00024_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00025":
                MPList.t00025_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00026":
                MPList.t00026_1.start();
                new NatureController.p4t1(tid).start();
                break;
            case "00027":
                MPList.t00027_1.start();
                new NatureController.p4t1(tid).start();
                break;

            case "00028":
                MPList.t00028_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "00029":
                MPList.t00029_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "00030":
                MPList.t00030_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "00031":
                MPList.t00031_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "00032":
                MPList.t00032_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "00033":
                MPList.t00033_1.start();
                new ChakraController.p5t1(tid).start();
                break;
            case "00034":
                MPList.t00034_1.start();
                new ChakraController.p5t1(tid).start();
                break;

            case "00035":
                MPList.t00035_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "00036":
                MPList.t00036_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "00037":
                MPList.t00037_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "00038":
                MPList.t00038_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "00039":
                MPList.t00039_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "00040":
                MPList.t00040_1.start();
                new MantraController.p6t1(tid).start();
                break;
            case "00041":
                MPList.t00041_1.start();
                new MantraController.p6t1(tid).start();
                break;

            case "00042":
                MPList.t00042_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "00043":
                MPList.t00043_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "00044":
                MPList.t00044_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "00045":
                MPList.t00045_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "00046":
                MPList.t00046_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "00047":
                MPList.t00047_1.start();
                new HzController.p7t1(tid).start();
                break;
            case "00048":
                MPList.t00048_1.start();
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
                return MPList.t00001_1;
            case "00002":
                return MPList.t00002_1;
            case "00003":
                return MPList.t00003_1;
            case "00004":
                return MPList.t00004_1;
            case "00005":
                return MPList.t00005_1;
            case "00006":
                return MPList.t00006_1;

            case "00007":
                return MPList.t00007_1;
            case "00008":
                return MPList.t00008_1;
            case "00009":
                return MPList.t00009_1;
            case "00010":
                return MPList.t00010_1;
            case "00011":
                return MPList.t00011_1;
            case "00012":
                return MPList.t00012_1;

            case "00013":
                return MPList.t00013_1;
            case "00014":
                return MPList.t00014_1;
            case "00015":
                return MPList.t00015_1;
            case "00016":
                return MPList.t00016_1;
            case "00017":
                return MPList.t00017_1;
            case "00018":
                return MPList.t00018_1;
            case "00019":
                return MPList.t00019_1;

            case "00020":
                return MPList.t00020_1;
            case "00021":
                return MPList.t00021_1;
            case "00022":
                return MPList.t00022_1;
            case "00023":
                return MPList.t00023_1;
            case "00024":
                return MPList.t00024_1;
            case "00025":
                return MPList.t00025_1;
            case "00026":
                return MPList.t00026_1;
            case "00027":
                return MPList.t00027_1;

            case "00028":
                return MPList.t00028_1;
            case "00029":
                return MPList.t00029_1;
            case "00030":
                return MPList.t00030_1;
            case "00031":
                return MPList.t00031_1;
            case "00032":
                return MPList.t00032_1;
            case "00033":
                return MPList.t00033_1;
            case "00034":
                return MPList.t00034_1;

            case "00035":
                return MPList.t00035_1;
            case "00036":
                return MPList.t00036_1;
            case "00037":
                return MPList.t00037_1;
            case "00038":
                return MPList.t00038_1;
            case "00039":
                return MPList.t00039_1;
            case "00040":
                return MPList.t00040_1;
            case "00041":
                return MPList.t00041_1;

            case "00042":
                return MPList.t00042_1;
            case "00043":
                return MPList.t00043_1;
            case "00044":
                return MPList.t00044_1;
            case "00045":
                return MPList.t00045_1;
            case "00046":
                return MPList.t00046_1;
            case "00047":
                return MPList.t00047_1;
            case "00048":
                return MPList.t00048_1;

            default:
                return null;
        }
    }

    public static MediaPlayer playingListindex0_2(String pp) {
        switch (pp) {
            case "00001":
                return MPList.t00001_2;
            case "00002":
                return MPList.t00002_2;
            case "00003":
                return MPList.t00003_2;
            case "00004":
                return MPList.t00004_2;
            case "00005":
                return MPList.t00005_2;
            case "00006":
                return MPList.t00006_2;

            case "00007":
                return MPList.t00007_2;
            case "00008":
                return MPList.t00008_2;
            case "00009":
                return MPList.t00009_2;
            case "00010":
                return MPList.t00010_2;
            case "00011":
                return MPList.t00011_2;
            case "00012":
                return MPList.t00012_2;

            case "00013":
                return MPList.t00013_2;
            case "00014":
                return MPList.t00014_2;
            case "00015":
                return MPList.t00015_2;
            case "00016":
                return MPList.t00016_2;
            case "00017":
                return MPList.t00017_2;
            case "00018":
                return MPList.t00018_2;
            case "00019":
                return MPList.t00019_2;

            case "00020":
                return MPList.t00020_2;
            case "00021":
                return MPList.t00021_2;
            case "00022":
                return MPList.t00022_2;
            case "00023":
                return MPList.t00023_2;
            case "00024":
                return MPList.t00024_2;
            case "00025":
                return MPList.t00025_2;
            case "00026":
                return MPList.t00026_2;
            case "00027":
                return MPList.t00027_2;

            case "00028":
                return MPList.t00028_2;
            case "00029":
                return MPList.t00029_2;
            case "00030":
                return MPList.t00030_2;
            case "00031":
                return MPList.t00031_2;
            case "00032":
                return MPList.t00032_2;
            case "00033":
                return MPList.t00033_2;
            case "00034":
                return MPList.t00034_2;

            case "00035":
                return MPList.t00035_2;
            case "00036":
                return MPList.t00036_2;
            case "00037":
                return MPList.t00037_2;
            case "00038":
                return MPList.t00038_2;
            case "00039":
                return MPList.t00039_2;
            case "00040":
                return MPList.t00040_2;
            case "00041":
                return MPList.t00041_2;

            case "00042":
                return MPList.t00042_2;
            case "00043":
                return MPList.t00043_2;
            case "00044":
                return MPList.t00044_2;
            case "00045":
                return MPList.t00045_2;
            case "00046":
                return MPList.t00046_2;
            case "00047":
                return MPList.t00047_2;
            case "00048":
                return MPList.t00048_2;
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
