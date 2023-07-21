package com.dippola.relaxtour;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import com.dippola.relaxtour.controller.AudioController;

import java.io.IOException;

public class MPList {
//    //page1
//    public static MediaPlayer p1p1_1, p1p1_2;
//    public static MediaPlayer p1p2_1, p1p2_2;
//    public static MediaPlayer p1p3_1, p1p3_2;
//    public static MediaPlayer p1p4_1, p1p4_2;
//    public static MediaPlayer p1p5_1, p1p5_2;
//    public static MediaPlayer p1p6_1, p1p6_2;
//
//    //page2
//    public static MediaPlayer p2p1_1, p2p1_2;
//    public static MediaPlayer p2p2_1, p2p2_2;
//    public static MediaPlayer p2p3_1, p2p3_2;
//    public static MediaPlayer p2p4_1, p2p4_2;
//    public static MediaPlayer p2p5_1, p2p5_2;
//    public static MediaPlayer p2p6_1, p2p6_2;
//
//    //page3
//    public static MediaPlayer p3p1_1, p3p1_2;
//    public static MediaPlayer p3p2_1, p3p2_2;
//    public static MediaPlayer p3p3_1, p3p3_2;
//    public static MediaPlayer p3p4_1, p3p4_2;
//    public static MediaPlayer p3p5_1, p3p5_2;
//    public static MediaPlayer p3p6_1, p3p6_2;
//    public static MediaPlayer p3p7_1, p3p7_2;
//
//    //page4
//    public static MediaPlayer p4p1_1, p4p1_2;
//    public static MediaPlayer p4p2_1, p4p2_2;
//    public static MediaPlayer p4p3_1, p4p3_2;
//    public static MediaPlayer p4p4_1, p4p4_2;
//    public static MediaPlayer p4p5_1, p4p5_2;
//    public static MediaPlayer p4p6_1, p4p6_2;
//    public static MediaPlayer p4p7_1, p4p7_2;
//    public static MediaPlayer p4p8_1, p4p8_2;
//
//    //page5
//    public static MediaPlayer p5p1_1, p5p1_2;
//    public static MediaPlayer p5p2_1, p5p2_2;
//    public static MediaPlayer p5p3_1, p5p3_2;
//    public static MediaPlayer p5p4_1, p5p4_2;
//    public static MediaPlayer p5p5_1, p5p5_2;
//    public static MediaPlayer p5p6_1, p5p6_2;
//    public static MediaPlayer p5p7_1, p5p7_2;
//
//    //page6
//    public static MediaPlayer p6p1_1, p6p1_2;
//    public static MediaPlayer p6p2_1, p6p2_2;
//    public static MediaPlayer p6p3_1, p6p3_2;
//    public static MediaPlayer p6p4_1, p6p4_2;
//    public static MediaPlayer p6p5_1, p6p5_2;
//    public static MediaPlayer p6p6_1, p6p6_2;
//    public static MediaPlayer p6p7_1, p6p7_2;
//
//    //page7
//    public static MediaPlayer p7p1_1, p7p1_2;
//    public static MediaPlayer p7p2_1, p7p2_2;
//    public static MediaPlayer p7p3_1, p7p3_2;
//    public static MediaPlayer p7p4_1, p7p4_2;
//    public static MediaPlayer p7p5_1, p7p5_2;
//    public static MediaPlayer p7p6_1, p7p6_2;
//    public static MediaPlayer p7p7_1, p7p7_2;

    public static MediaPlayer t00001_1, t00001_2;
    public static MediaPlayer t00002_1, t00002_2;
    public static MediaPlayer t00003_1, t00003_2;
    public static MediaPlayer t00004_1, t00004_2;
    public static MediaPlayer t00005_1, t00005_2;
    public static MediaPlayer t00006_1, t00006_2;
    public static MediaPlayer t00007_1, t00007_2;
    public static MediaPlayer t00008_1, t00008_2;
    public static MediaPlayer t00009_1, t00009_2;
    public static MediaPlayer t00010_1, t00010_2;
    public static MediaPlayer t00011_1, t00011_2;
    public static MediaPlayer t00012_1, t00012_2;
    public static MediaPlayer t00013_1, t00013_2;
    public static MediaPlayer t00014_1, t00014_2;
    public static MediaPlayer t00015_1, t00015_2;
    public static MediaPlayer t00016_1, t00016_2;
    public static MediaPlayer t00017_1, t00017_2;
    public static MediaPlayer t00018_1, t00018_2;
    public static MediaPlayer t00019_1, t00019_2;
    public static MediaPlayer t00020_1, t00020_2;
    public static MediaPlayer t00021_1, t00021_2;
    public static MediaPlayer t00022_1, t00022_2;
    public static MediaPlayer t00023_1, t00023_2;
    public static MediaPlayer t00024_1, t00024_2;
    public static MediaPlayer t00025_1, t00025_2;
    public static MediaPlayer t00026_1, t00026_2;
    public static MediaPlayer t00027_1, t00027_2;
    public static MediaPlayer t00028_1, t00028_2;
    public static MediaPlayer t00029_1, t00029_2;
    public static MediaPlayer t00030_1, t00030_2;
    public static MediaPlayer t00031_1, t00031_2;
    public static MediaPlayer t00032_1, t00032_2;
    public static MediaPlayer t00033_1, t00033_2;
    public static MediaPlayer t00034_1, t00034_2;
    public static MediaPlayer t00035_1, t00035_2;
    public static MediaPlayer t00036_1, t00036_2;
    public static MediaPlayer t00037_1, t00037_2;
    public static MediaPlayer t00038_1, t00038_2;
    public static MediaPlayer t00039_1, t00039_2;
    public static MediaPlayer t00040_1, t00040_2;
    public static MediaPlayer t00041_1, t00041_2;
    public static MediaPlayer t00042_1, t00042_2;
    public static MediaPlayer t00043_1, t00043_2;
    public static MediaPlayer t00044_1, t00044_2;
    public static MediaPlayer t00045_1, t00045_2;
    public static MediaPlayer t00046_1, t00046_2;
    public static MediaPlayer t00047_1, t00047_2;
    public static MediaPlayer t00048_1, t00048_2;

    public static void initalMP(String tid, Context context, int volumn) {
        switch (tid) {
            case "00001":
                if (t00001_1 == null || t00001_2 == null) {
                    t00001_1 = MediaPlayer.create(context, R.raw.audio00001);
                    t00001_2 = MediaPlayer.create(context, R.raw.audio00001);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00002":
                if (t00002_1 == null || t00002_2 == null) {
                    t00002_1 = MediaPlayer.create(context, R.raw.audio00002);
                    t00002_2 = MediaPlayer.create(context, R.raw.audio00002);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00003":
                if (t00003_1 == null || t00003_2 == null) {
                    t00003_1 = MediaPlayer.create(context, R.raw.audio00003);
                    t00003_2 = MediaPlayer.create(context, R.raw.audio00003);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00004":
                if (t00004_1 == null || t00004_2 == null) {
                    t00004_1 = MediaPlayer.create(context, R.raw.audio00004);
                    t00004_2 = MediaPlayer.create(context, R.raw.audio00004);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00005":
                if (t00005_1 == null || t00005_2 == null) {
                    t00005_1 = MediaPlayer.create(context, R.raw.audio00005);
                    t00005_2 = MediaPlayer.create(context, R.raw.audio00005);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00006":
                if (t00006_1 == null || t00006_2 == null) {
                    t00006_1 = MediaPlayer.create(context, R.raw.audio00006);
                    t00006_2 = MediaPlayer.create(context, R.raw.audio00006);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00007":
                if (t00007_1 == null || t00007_2 == null) {
                    t00007_1 = MediaPlayer.create(context, R.raw.audio00007);
                    t00007_2 = MediaPlayer.create(context, R.raw.audio00007);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00008":
                if (t00008_1 == null || t00008_2 == null) {
                    t00008_1 = MediaPlayer.create(context, R.raw.audio00008);
                    t00008_2 = MediaPlayer.create(context, R.raw.audio00008);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00009":
                if (t00009_1 == null || t00009_2 == null) {
                    t00009_1 = MediaPlayer.create(context, R.raw.audio00009);
                    t00009_2 = MediaPlayer.create(context, R.raw.audio00009);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00010":
                if (t00010_1 == null || t00010_2 == null) {
                    t00010_1 = MediaPlayer.create(context, R.raw.audio00010);
                    t00010_2 = MediaPlayer.create(context, R.raw.audio00010);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00011":
                if (t00011_1 == null || t00011_2 == null) {
                    t00011_1 = MediaPlayer.create(context, R.raw.audio00011);
                    t00011_2 = MediaPlayer.create(context, R.raw.audio00011);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00012":
                if (t00012_1 == null || t00012_2 == null) {
                    t00012_1 = MediaPlayer.create(context, R.raw.audio00012);
                    t00012_2 = MediaPlayer.create(context, R.raw.audio00012);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00013":
                if (t00013_1 == null || t00013_2 == null) {
                    t00013_1 = MediaPlayer.create(context, R.raw.audio00013);
                    t00013_2 = MediaPlayer.create(context, R.raw.audio00013);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00014":
                if (t00014_1 == null || t00014_2 == null) {
                    t00014_1 = MediaPlayer.create(context, R.raw.audio00014);
                    t00014_2 = MediaPlayer.create(context, R.raw.audio00014);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00015":
                if (t00015_1 == null || t00015_2 == null) {
                    t00015_1 = MediaPlayer.create(context, R.raw.audio00015);
                    t00015_2 = MediaPlayer.create(context, R.raw.audio00015);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00016":
                if (t00016_1 == null || t00016_2 == null) {
                    t00016_1 = MediaPlayer.create(context, R.raw.audio00016);
                    t00016_2 = MediaPlayer.create(context, R.raw.audio00016);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00017":
                if (t00017_1 == null || t00017_2 == null) {
                    t00017_1 = MediaPlayer.create(context, R.raw.audio00017);
                    t00017_2 = MediaPlayer.create(context, R.raw.audio00017);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00018":
                if (t00018_1 == null || t00018_2 == null) {
                    t00018_1 = MediaPlayer.create(context, R.raw.audio00018);
                    t00018_2 = MediaPlayer.create(context, R.raw.audio00018);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00019":
                if (t00019_1 == null || t00019_2 == null) {
                    t00019_1 = MediaPlayer.create(context, R.raw.audio00019);
                    t00019_2 = MediaPlayer.create(context, R.raw.audio00019);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00020":
                if (t00020_1 == null || t00020_2 == null) {
                    t00020_1 = MediaPlayer.create(context, R.raw.audio00020);
                    t00020_2 = MediaPlayer.create(context, R.raw.audio00020);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00021":
                if (t00021_1 == null || t00021_2 == null) {
                    t00021_1 = MediaPlayer.create(context, R.raw.audio00021);
                    t00021_2 = MediaPlayer.create(context, R.raw.audio00021);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00022":
                if (t00022_1 == null || t00022_2 == null) {
                    t00022_1 = MediaPlayer.create(context, R.raw.audio00022);
                    t00022_2 = MediaPlayer.create(context, R.raw.audio00022);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00023":
                if (t00023_1 == null || t00023_2 == null) {
                    t00023_1 = MediaPlayer.create(context, R.raw.audio00023);
                    t00023_2 = MediaPlayer.create(context, R.raw.audio00023);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00024":
                if (t00024_1 == null || t00024_2 == null) {
                    t00024_1 = MediaPlayer.create(context, R.raw.audio00024);
                    t00024_2 = MediaPlayer.create(context, R.raw.audio00024);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00025":
                if (t00025_1 == null || t00025_2 == null) {
                    t00025_1 = MediaPlayer.create(context, R.raw.audio00025);
                    t00025_2 = MediaPlayer.create(context, R.raw.audio00025);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00026":
                if (t00026_1 == null || t00026_2 == null) {
                    t00026_1 = MediaPlayer.create(context, R.raw.audio00026);
                    t00026_2 = MediaPlayer.create(context, R.raw.audio00026);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00027":
                if (t00027_1 == null || t00027_2 == null) {
                    t00027_1 = MediaPlayer.create(context, R.raw.audio00027);
                    t00027_2 = MediaPlayer.create(context, R.raw.audio00027);
                    AudioController.setVolumn(tid, volumn);
                }
                break;

            case "00028":
                if (t00028_1 == null || t00028_2 == null) {
                    t00028_1 = MediaPlayer.create(context, R.raw.audio00028);
                    t00028_2 = MediaPlayer.create(context, R.raw.audio00028);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00029":
                if (t00029_1 == null || t00029_2 == null) {
                    try {
                        t00029_1 = new MediaPlayer();
                        t00029_2 = new MediaPlayer();
                        t00029_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00029.mp3");
                        t00029_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00029.mp3");
                        t00029_1.prepareAsync();
                        t00029_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00030":
                if (t00030_1 == null || t00030_2 == null) {
                    try {
                        t00030_1 = new MediaPlayer();
                        t00030_2 = new MediaPlayer();
                        t00030_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00030.mp3");
                        t00030_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00030.mp3");
                        t00030_1.prepareAsync();
                        t00030_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00031":
                if (t00031_1 == null || t00031_2 == null) {
                    try {
                        t00031_1 = new MediaPlayer();
                        t00031_2 = new MediaPlayer();
                        t00031_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00031.mp3");
                        t00031_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00031.mp3");
                        t00031_1.prepareAsync();
                        t00031_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00032":
                if (t00032_1 == null || t00032_2 == null) {
                    try {
                        t00032_1 = new MediaPlayer();
                        t00032_2 = new MediaPlayer();
                        t00032_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00032.mp3");
                        t00032_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00032.mp3");
                        t00032_1.prepareAsync();
                        t00032_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00033":
                if (t00033_1 == null || t00033_2 == null) {
                    try {
                        t00033_1 = new MediaPlayer();
                        t00033_2 = new MediaPlayer();
                        t00033_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00033.mp3");
                        t00033_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00033.mp3");
                        t00033_1.prepareAsync();
                        t00033_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00034":
                if (t00034_1 == null || t00034_2 == null) {
                    try {
                        t00034_1 = new MediaPlayer();
                        t00034_2 = new MediaPlayer();
                        t00034_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00034.mp3");
                        t00034_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00034.mp3");
                        t00034_1.prepareAsync();
                        t00034_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;

            case "00035":
                if (t00035_1 == null || t00035_2 == null) {
                    t00035_1 = MediaPlayer.create(context, R.raw.audio00035);
                    t00035_2 = MediaPlayer.create(context, R.raw.audio00035);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00036":
                if (t00036_1 == null || t00036_2 == null) {
                    try {
                        t00036_1 = new MediaPlayer();
                        t00036_2 = new MediaPlayer();
                        t00036_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00036.mp3");
                        t00036_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00036.mp3");
                        t00036_1.prepareAsync();
                        t00036_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00037":
                if (t00037_1 == null || t00037_2 == null) {
                    try {
                        t00037_1 = new MediaPlayer();
                        t00037_2 = new MediaPlayer();
                        t00037_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00037.mp3");
                        t00037_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00037.mp3");
                        t00037_1.prepareAsync();
                        t00037_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00038":
                if (t00038_1 == null || t00038_2 == null) {
                    try {
                        t00038_1 = new MediaPlayer();
                        t00038_2 = new MediaPlayer();
                        t00038_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00038.mp3");
                        t00038_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00038.mp3");
                        t00038_1.prepareAsync();
                        t00038_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00039":
                if (t00039_1 == null || t00039_2 == null) {
                    try {
                        t00039_1 = new MediaPlayer();
                        t00039_2 = new MediaPlayer();
                        t00039_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00039.mp3");
                        t00039_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00039.mp3");
                        t00039_1.prepareAsync();
                        t00039_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00040":
                if (t00040_1 == null || t00040_2 == null) {
                    try {
                        t00040_1 = new MediaPlayer();
                        t00040_2 = new MediaPlayer();
                        t00040_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00040.mp3");
                        t00040_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00040.mp3");
                        t00040_1.prepareAsync();
                        t00040_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00041":
                if (t00041_1 == null || t00041_2 == null) {
                    try {
                        t00041_1 = new MediaPlayer();
                        t00041_2 = new MediaPlayer();
                        t00041_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00041.mp3");
                        t00041_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00041.mp3");
                        t00041_1.prepareAsync();
                        t00041_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;

            case "00042":
                if (t00042_1 == null || t00042_2 == null) {
                    t00042_1 = MediaPlayer.create(context, R.raw.audio00042);
                    t00042_2 = MediaPlayer.create(context, R.raw.audio00042);
                    AudioController.setVolumn(tid, volumn);
                }
            case "00043":
                if (t00043_1 == null || t00043_2 == null) {
                    try {
                        t00043_1 = new MediaPlayer();
                        t00043_2 = new MediaPlayer();
                        t00043_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00043.mp3");
                        t00043_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00043.mp3");
                        t00043_1.prepareAsync();
                        t00043_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00044":
                if (t00044_1 == null || t00044_2 == null) {
                    try {
                        t00044_1 = new MediaPlayer();
                        t00044_2 = new MediaPlayer();
                        t00044_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00044.mp3");
                        t00044_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00044.mp3");
                        t00044_1.prepareAsync();
                        t00044_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00045":
                if (t00045_1 == null || t00045_2 == null) {
                    try {
                        t00045_1 = new MediaPlayer();
                        t00045_2 = new MediaPlayer();
                        t00045_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00045.mp3");
                        t00045_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00045.mp3");
                        t00045_1.prepareAsync();
                        t00045_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00046":
                if (t00046_1 == null || t00046_2 == null) {
                    try {
                        t00046_1 = new MediaPlayer();
                        t00046_2 = new MediaPlayer();
                        t00046_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00046.mp3");
                        t00046_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00046.mp3");
                        t00046_1.prepareAsync();
                        t00046_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00047":
                if (t00047_1 == null || t00047_2 == null) {
                    try {
                        t00047_1 = new MediaPlayer();
                        t00047_2 = new MediaPlayer();
                        t00047_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00047.mp3");
                        t00047_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00047.mp3");
                        t00047_1.prepareAsync();
                        t00047_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00048":
                if (t00048_1 == null || t00048_2 == null) {
                    try {
                        t00048_1 = new MediaPlayer();
                        t00048_2 = new MediaPlayer();
                        t00048_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00048.mp3");
                        t00048_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00048.mp3");
                        t00048_1.prepareAsync();
                        t00048_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;

        }

//        switch (tid) {
//            case "00001":
//                if (t00001_1 == null || t00001_2 == null) {
//                    t00001_1 = MediaPlayer.create(context, R.raw.audio00001);
//                    t00001_2 = MediaPlayer.create(context, R.raw.audio00001);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00002":
//                if (t00002_1 == null || t00002_2 == null) {
//                    t00002_1 = MediaPlayer.create(context, R.raw.audio00002);
//                    t00002_2 = MediaPlayer.create(context, R.raw.audio00002);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00003":
//                if (t00003_1 == null || t00003_2 == null) {
//                    t00003_1 = MediaPlayer.create(context, R.raw.audio00003);
//                    t00003_2 = MediaPlayer.create(context, R.raw.audio00003);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00004":
//                if (t00004_1 == null || t00004_2 == null) {
//                    t00004_1 = MediaPlayer.create(context, R.raw.audio00004);
//                    t00004_2 = MediaPlayer.create(context, R.raw.audio00004);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00005":
//                if (t00005 == null || p1p5_2 == null) {
//                    p1p5_1 = MediaPlayer.create(context, R.raw.audio00005);
//                    p1p5_2 = MediaPlayer.create(context, R.raw.audio00005);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00006":
//                if (p1p6_1 == null || p1p6_2 == null) {
//                    p1p6_1 = MediaPlayer.create(context, R.raw.audio00006);
//                    p1p6_2 = MediaPlayer.create(context, R.raw.audio00006);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00007":
//                if (p2p1_1 == null || p2p1_2 == null) {
//                    p2p1_1 = MediaPlayer.create(context, R.raw.audio00007);
//                    p2p1_2 = MediaPlayer.create(context, R.raw.audio00007);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00008":
//                if (p2p2_1 == null || p2p2_2 == null) {
//                    p2p2_1 = MediaPlayer.create(context, R.raw.audio00008);
//                    p2p2_2 = MediaPlayer.create(context, R.raw.audio00008);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00009":
//                if (p2p3_1 == null || p2p3_2 == null) {
//                    p2p3_1 = MediaPlayer.create(context, R.raw.audio00009);
//                    p2p3_2 = MediaPlayer.create(context, R.raw.audio00009);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00010":
//                if (p2p4_1 == null || p2p4_2 == null) {
//                    p2p4_1 = MediaPlayer.create(context, R.raw.audio00010);
//                    p2p4_2 = MediaPlayer.create(context, R.raw.audio00010);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00011":
//                if (p2p5_1 == null || p2p5_2 == null) {
//                    p2p5_1 = MediaPlayer.create(context, R.raw.audio00011);
//                    p2p5_2 = MediaPlayer.create(context, R.raw.audio00011);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00012":
//                if (p2p6_1 == null || p2p6_2 == null) {
//                    p2p6_1 = MediaPlayer.create(context, R.raw.audio00012);
//                    p2p6_2 = MediaPlayer.create(context, R.raw.audio00012);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00013":
//                if (p3p1_1 == null || p3p1_2 == null) {
//                    p3p1_1 = MediaPlayer.create(context, R.raw.audio00013);
//                    p3p1_2 = MediaPlayer.create(context, R.raw.audio00013);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00014":
//                if (p3p2_1 == null || p3p2_2 == null) {
//                    p3p2_1 = MediaPlayer.create(context, R.raw.audio00014);
//                    p3p2_2 = MediaPlayer.create(context, R.raw.audio00014);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00015":
//                if (p3p3_1 == null || p3p3_2 == null) {
//                    p3p3_1 = MediaPlayer.create(context, R.raw.audio00015);
//                    p3p3_2 = MediaPlayer.create(context, R.raw.audio00015);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00016":
//                if (p3p4_1 == null || p3p4_2 == null) {
//                    p3p4_1 = MediaPlayer.create(context, R.raw.audio00016);
//                    p3p4_2 = MediaPlayer.create(context, R.raw.audio00016);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00017":
//                if (p3p5_1 == null || p3p5_2 == null) {
//                    p3p5_1 = MediaPlayer.create(context, R.raw.audio00017);
//                    p3p5_2 = MediaPlayer.create(context, R.raw.audio00017);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00018":
//                if (p3p6_1 == null || p3p6_2 == null) {
//                    p3p6_1 = MediaPlayer.create(context, R.raw.audio00018);
//                    p3p6_2 = MediaPlayer.create(context, R.raw.audio00018);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00019":
//                if (p3p7_1 == null || p3p7_2 == null) {
//                    p3p7_1 = MediaPlayer.create(context, R.raw.audio00019);
//                    p3p7_2 = MediaPlayer.create(context, R.raw.audio00019);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00020":
//                if (p4p1_1 == null || p4p1_2 == null) {
//                    p4p1_1 = MediaPlayer.create(context, R.raw.audio00020);
//                    p4p1_2 = MediaPlayer.create(context, R.raw.audio00020);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00021":
//                if (p4p2_1 == null || p4p2_2 == null) {
//                    p4p2_1 = MediaPlayer.create(context, R.raw.audio00021);
//                    p4p2_2 = MediaPlayer.create(context, R.raw.audio00021);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00022":
//                if (p4p3_1 == null || p4p3_2 == null) {
//                    p4p3_1 = MediaPlayer.create(context, R.raw.audio00022);
//                    p4p3_2 = MediaPlayer.create(context, R.raw.audio00022);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00023":
//                if (p4p4_1 == null || p4p4_2 == null) {
//                    p4p4_1 = MediaPlayer.create(context, R.raw.audio00023);
//                    p4p4_2 = MediaPlayer.create(context, R.raw.audio00023);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00024":
//                if (p4p5_1 == null || p4p5_2 == null) {
//                    p4p5_1 = MediaPlayer.create(context, R.raw.audio00024);
//                    p4p5_2 = MediaPlayer.create(context, R.raw.audio00024);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00025":
//                if (p4p6_1 == null || p4p6_2 == null) {
//                    p4p6_1 = MediaPlayer.create(context, R.raw.audio00025);
//                    p4p6_2 = MediaPlayer.create(context, R.raw.audio00025);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00026":
//                if (p4p7_1 == null || p4p7_2 == null) {
//                    p4p7_1 = MediaPlayer.create(context, R.raw.audio00026);
//                    p4p7_2 = MediaPlayer.create(context, R.raw.audio00026);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00027":
//                if (p4p8_1 == null || p4p8_2 == null) {
//                    p4p8_1 = MediaPlayer.create(context, R.raw.audio00027);
//                    p4p8_2 = MediaPlayer.create(context, R.raw.audio00027);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//
//            case "00028":
//                if (p5p1_1 == null || p5p1_2 == null) {
//                    p5p1_1 = MediaPlayer.create(context, R.raw.audio00028);
//                    p5p1_2 = MediaPlayer.create(context, R.raw.audio00028);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00029":
//                if (p5p2_1 == null || p5p2_2 == null) {
//                    try {
//                        p5p2_1 = new MediaPlayer();
//                        p5p2_2 = new MediaPlayer();
//                        p5p2_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00029.mp3");
//                        p5p2_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00029.mp3");
//                        p5p2_1.prepareAsync();
//                        p5p2_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00030":
//                if (p5p3_1 == null || p5p3_2 == null) {
//                    try {
//                        p5p3_1 = new MediaPlayer();
//                        p5p3_2 = new MediaPlayer();
//                        p5p3_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00030.mp3");
//                        p5p3_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00030.mp3");
//                        p5p3_1.prepareAsync();
//                        p5p3_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00031":
//                if (p5p4_1 == null || p5p4_2 == null) {
//                    try {
//                        p5p4_1 = new MediaPlayer();
//                        p5p4_2 = new MediaPlayer();
//                        p5p4_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00031.mp3");
//                        p5p4_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00031.mp3");
//                        p5p4_1.prepareAsync();
//                        p5p4_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00032":
//                if (p5p5_1 == null || p5p5_2 == null) {
//                    try {
//                        p5p5_1 = new MediaPlayer();
//                        p5p5_2 = new MediaPlayer();
//                        p5p5_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00032.mp3");
//                        p5p5_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00032.mp3");
//                        p5p5_1.prepareAsync();
//                        p5p5_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00033":
//                if (p5p6_1 == null || p5p6_2 == null) {
//                    try {
//                        p5p6_1 = new MediaPlayer();
//                        p5p6_2 = new MediaPlayer();
//                        p5p6_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00033.mp3");
//                        p5p6_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00033.mp3");
//                        p5p6_1.prepareAsync();
//                        p5p6_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00034":
//                if (p5p7_1 == null || p5p7_2 == null) {
//                    try {
//                        p5p7_1 = new MediaPlayer();
//                        p5p7_2 = new MediaPlayer();
//                        p5p7_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00034.mp3");
//                        p5p7_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00034.mp3");
//                        p5p7_1.prepareAsync();
//                        p5p7_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//
//            case "00035":
//                if (p6p1_1 == null || p6p1_2 == null) {
//                    p6p1_1 = MediaPlayer.create(context, R.raw.audio00035);
//                    p6p1_2 = MediaPlayer.create(context, R.raw.audio00035);
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00036":
//                if (p6p2_1 == null || p6p2_2 == null) {
//                    try {
//                        p6p2_1 = new MediaPlayer();
//                        p6p2_2 = new MediaPlayer();
//                        p6p2_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00036.mp3");
//                        p6p2_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00036.mp3");
//                        p6p2_1.prepareAsync();
//                        p6p2_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00037":
//                if (p6p3_1 == null || p6p3_2 == null) {
//                    try {
//                        p6p3_1 = new MediaPlayer();
//                        p6p3_2 = new MediaPlayer();
//                        p6p3_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00037.mp3");
//                        p6p3_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00037.mp3");
//                        p6p3_1.prepareAsync();
//                        p6p3_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00038":
//                if (p6p4_1 == null || p6p4_2 == null) {
//                    try {
//                        p6p4_1 = new MediaPlayer();
//                        p6p4_2 = new MediaPlayer();
//                        p6p4_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00038.mp3");
//                        p6p4_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00038.mp3");
//                        p6p4_1.prepareAsync();
//                        p6p4_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "39":
//                if (p6p5_1 == null || p6p5_2 == null) {
//                    try {
//                        p6p5_1 = new MediaPlayer();
//                        p6p5_2 = new MediaPlayer();
//                        p6p5_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00039.mp3");
//                        p6p5_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00039.mp3");
//                        p6p5_1.prepareAsync();
//                        p6p5_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00040":
//                if (p6p6_1 == null || p6p6_2 == null) {
//                    try {
//                        p6p6_1 = new MediaPlayer();
//                        p6p6_2 = new MediaPlayer();
//                        p6p6_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00040.mp3");
//                        p6p6_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00040.mp3");
//                        p6p6_1.prepareAsync();
//                        p6p6_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00041":
//                if (p6p7_1 == null || p6p7_2 == null) {
//                    try {
//                        p6p7_1 = new MediaPlayer();
//                        p6p7_2 = new MediaPlayer();
//                        p6p7_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00041.mp3");
//                        p6p7_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00041.mp3");
//                        p6p7_1.prepareAsync();
//                        p6p7_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//
//            case "00042":
//                if (p7p1_1 == null || p7p1_2 == null) {
//                    p7p1_1 = MediaPlayer.create(context, R.raw.audio00042);
//                    p7p1_2 = MediaPlayer.create(context, R.raw.audio00042);
//                    AudioController.setVolumn(tid, volumn);
//                }
//            case "00043":
//                if (p7p2_1 == null || p7p2_2 == null) {
//                    try {
//                        p7p2_1 = new MediaPlayer();
//                        p7p2_2 = new MediaPlayer();
//                        p7p2_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00043.mp3");
//                        p7p2_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00043.mp3");
//                        p7p2_1.prepareAsync();
//                        p7p2_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00044":
//                if (p7p3_1 == null || p7p3_2 == null) {
//                    try {
//                        p7p3_1 = new MediaPlayer();
//                        p7p3_2 = new MediaPlayer();
//                        p7p3_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00044.mp3");
//                        p7p3_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00044.mp3");
//                        p7p3_1.prepareAsync();
//                        p7p3_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00045":
//                if (p7p4_1 == null || p7p4_2 == null) {
//                    try {
//                        p7p4_1 = new MediaPlayer();
//                        p7p4_2 = new MediaPlayer();
//                        p7p4_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00045.mp3");
//                        p7p4_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00045.mp3");
//                        p7p4_1.prepareAsync();
//                        p7p4_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00046":
//                if (p7p5_1 == null || p7p5_2 == null) {
//                    try {
//                        p7p5_1 = new MediaPlayer();
//                        p7p5_2 = new MediaPlayer();
//                        p7p5_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00046.mp3");
//                        p7p5_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00046.mp3");
//                        p7p5_1.prepareAsync();
//                        p7p5_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00047":
//                if (p7p6_1 == null || p7p6_2 == null) {
//                    try {
//                        p7p6_1 = new MediaPlayer();
//                        p7p6_2 = new MediaPlayer();
//                        p7p6_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00047.mp3");
//                        p7p6_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00047.mp3");
//                        p7p6_1.prepareAsync();
//                        p7p6_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//            case "00048":
//                if (p7p7_1 == null || p7p7_2 == null) {
//                    try {
//                        p7p7_1 = new MediaPlayer();
//                        p7p7_2 = new MediaPlayer();
//                        p7p7_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00048.mp3");
//                        p7p7_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00048.mp3");
//                        p7p7_1.prepareAsync();
//                        p7p7_2.prepareAsync();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    AudioController.setVolumn(tid, volumn);
//                }
//                break;
//
//        }
    }
}
