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
    //page1
    public static MediaPlayer p1p1_1, p1p1_2;
    public static MediaPlayer p1p2_1, p1p2_2;
    public static MediaPlayer p1p3_1, p1p3_2;
    public static MediaPlayer p1p4_1, p1p4_2;
    public static MediaPlayer p1p5_1, p1p5_2;
    public static MediaPlayer p1p6_1, p1p6_2;

    //page2
    public static MediaPlayer p2p1_1, p2p1_2;
    public static MediaPlayer p2p2_1, p2p2_2;
    public static MediaPlayer p2p3_1, p2p3_2;
    public static MediaPlayer p2p4_1, p2p4_2;
    public static MediaPlayer p2p5_1, p2p5_2;
    public static MediaPlayer p2p6_1, p2p6_2;

    //page3
    public static MediaPlayer p3p1_1, p3p1_2;
    public static MediaPlayer p3p2_1, p3p2_2;
    public static MediaPlayer p3p3_1, p3p3_2;
    public static MediaPlayer p3p4_1, p3p4_2;
    public static MediaPlayer p3p5_1, p3p5_2;
    public static MediaPlayer p3p6_1, p3p6_2;
    public static MediaPlayer p3p7_1, p3p7_2;

    //page4
    public static MediaPlayer p4p1_1, p4p1_2;
    public static MediaPlayer p4p2_1, p4p2_2;
    public static MediaPlayer p4p3_1, p4p3_2;
    public static MediaPlayer p4p4_1, p4p4_2;
    public static MediaPlayer p4p5_1, p4p5_2;
    public static MediaPlayer p4p6_1, p4p6_2;
    public static MediaPlayer p4p7_1, p4p7_2;
    public static MediaPlayer p4p8_1, p4p8_2;

    //page5
    public static MediaPlayer p5p1_1, p5p1_2;
    public static MediaPlayer p5p2_1, p5p2_2;
    public static MediaPlayer p5p3_1, p5p3_2;
    public static MediaPlayer p5p4_1, p5p4_2;
    public static MediaPlayer p5p5_1, p5p5_2;
    public static MediaPlayer p5p6_1, p5p6_2;
    public static MediaPlayer p5p7_1, p5p7_2;

    //page6
    public static MediaPlayer p6p1_1, p6p1_2;
    public static MediaPlayer p6p2_1, p6p2_2;
    public static MediaPlayer p6p3_1, p6p3_2;
    public static MediaPlayer p6p4_1, p6p4_2;
    public static MediaPlayer p6p5_1, p6p5_2;
    public static MediaPlayer p6p6_1, p6p6_2;
    public static MediaPlayer p6p7_1, p6p7_2;

    //page7
    public static MediaPlayer p7p1_1, p7p1_2;
    public static MediaPlayer p7p2_1, p7p2_2;
    public static MediaPlayer p7p3_1, p7p3_2;
    public static MediaPlayer p7p4_1, p7p4_2;
    public static MediaPlayer p7p5_1, p7p5_2;
    public static MediaPlayer p7p6_1, p7p6_2;
    public static MediaPlayer p7p7_1, p7p7_2;

    public static void initalMP(String tid, Context context, int volumn) {
        switch (tid) {
            case "00001":
                if (p1p1_1 == null || p1p1_2 == null) {
                    p1p1_1 = MediaPlayer.create(context, R.raw.audio00001);
                    p1p1_2 = MediaPlayer.create(context, R.raw.audio00001);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00002":
                if (p1p2_1 == null || p1p2_2 == null) {
                    p1p2_1 = MediaPlayer.create(context, R.raw.audio00002);
                    p1p2_2 = MediaPlayer.create(context, R.raw.audio00002);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00003":
                if (p1p3_1 == null || p1p3_2 == null) {
                    p1p3_1 = MediaPlayer.create(context, R.raw.audio00003);
                    p1p3_2 = MediaPlayer.create(context, R.raw.audio00003);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00004":
                if (p1p4_1 == null || p1p4_2 == null) {
                    p1p4_1 = MediaPlayer.create(context, R.raw.audio00004);
                    p1p4_2 = MediaPlayer.create(context, R.raw.audio00004);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00005":
                if (p1p5_1 == null || p1p5_2 == null) {
                    p1p5_1 = MediaPlayer.create(context, R.raw.audio00005);
                    p1p5_2 = MediaPlayer.create(context, R.raw.audio00005);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00006":
                if (p1p6_1 == null || p1p6_2 == null) {
                    p1p6_1 = MediaPlayer.create(context, R.raw.audio00006);
                    p1p6_2 = MediaPlayer.create(context, R.raw.audio00006);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00007":
                if (p2p1_1 == null || p2p1_2 == null) {
                    p2p1_1 = MediaPlayer.create(context, R.raw.audio00007);
                    p2p1_2 = MediaPlayer.create(context, R.raw.audio00007);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00008":
                if (p2p2_1 == null || p2p2_2 == null) {
                    p2p2_1 = MediaPlayer.create(context, R.raw.audio00008);
                    p2p2_2 = MediaPlayer.create(context, R.raw.audio00008);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00009":
                if (p2p3_1 == null || p2p3_2 == null) {
                    p2p3_1 = MediaPlayer.create(context, R.raw.audio00009);
                    p2p3_2 = MediaPlayer.create(context, R.raw.audio00009);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00010":
                if (p2p4_1 == null || p2p4_2 == null) {
                    p2p4_1 = MediaPlayer.create(context, R.raw.audio00010);
                    p2p4_2 = MediaPlayer.create(context, R.raw.audio00010);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00011":
                if (p2p5_1 == null || p2p5_2 == null) {
                    p2p5_1 = MediaPlayer.create(context, R.raw.audio00011);
                    p2p5_2 = MediaPlayer.create(context, R.raw.audio00011);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00012":
                if (p2p6_1 == null || p2p6_2 == null) {
                    p2p6_1 = MediaPlayer.create(context, R.raw.audio00012);
                    p2p6_2 = MediaPlayer.create(context, R.raw.audio00012);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00013":
                if (p3p1_1 == null || p3p1_2 == null) {
                    p3p1_1 = MediaPlayer.create(context, R.raw.audio00013);
                    p3p1_2 = MediaPlayer.create(context, R.raw.audio00013);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00014":
                if (p3p2_1 == null || p3p2_2 == null) {
                    p3p2_1 = MediaPlayer.create(context, R.raw.audio00014);
                    p3p2_2 = MediaPlayer.create(context, R.raw.audio00014);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00015":
                if (p3p3_1 == null || p3p3_2 == null) {
                    p3p3_1 = MediaPlayer.create(context, R.raw.audio00015);
                    p3p3_2 = MediaPlayer.create(context, R.raw.audio00015);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00016":
                if (p3p4_1 == null || p3p4_2 == null) {
                    p3p4_1 = MediaPlayer.create(context, R.raw.audio00016);
                    p3p4_2 = MediaPlayer.create(context, R.raw.audio00016);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00017":
                if (p3p5_1 == null || p3p5_2 == null) {
                    p3p5_1 = MediaPlayer.create(context, R.raw.audio00017);
                    p3p5_2 = MediaPlayer.create(context, R.raw.audio00017);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00018":
                if (p3p6_1 == null || p3p6_2 == null) {
                    p3p6_1 = MediaPlayer.create(context, R.raw.audio00018);
                    p3p6_2 = MediaPlayer.create(context, R.raw.audio00018);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00019":
                if (p3p7_1 == null || p3p7_2 == null) {
                    p3p7_1 = MediaPlayer.create(context, R.raw.audio00019);
                    p3p7_2 = MediaPlayer.create(context, R.raw.audio00019);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00020":
                if (p4p1_1 == null || p4p1_2 == null) {
                    p4p1_1 = MediaPlayer.create(context, R.raw.audio00020);
                    p4p1_2 = MediaPlayer.create(context, R.raw.audio00020);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00021":
                if (p4p2_1 == null || p4p2_2 == null) {
                    p4p2_1 = MediaPlayer.create(context, R.raw.audio00021);
                    p4p2_2 = MediaPlayer.create(context, R.raw.audio00021);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00022":
                if (p4p3_1 == null || p4p3_2 == null) {
                    p4p3_1 = MediaPlayer.create(context, R.raw.audio00022);
                    p4p3_2 = MediaPlayer.create(context, R.raw.audio00022);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00023":
                if (p4p4_1 == null || p4p4_2 == null) {
                    p4p4_1 = MediaPlayer.create(context, R.raw.audio00023);
                    p4p4_2 = MediaPlayer.create(context, R.raw.audio00023);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00024":
                if (p4p5_1 == null || p4p5_2 == null) {
                    p4p5_1 = MediaPlayer.create(context, R.raw.audio00024);
                    p4p5_2 = MediaPlayer.create(context, R.raw.audio00024);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00025":
                if (p4p6_1 == null || p4p6_2 == null) {
                    p4p6_1 = MediaPlayer.create(context, R.raw.audio00025);
                    p4p6_2 = MediaPlayer.create(context, R.raw.audio00025);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00026":
                if (p4p7_1 == null || p4p7_2 == null) {
                    p4p7_1 = MediaPlayer.create(context, R.raw.audio00026);
                    p4p7_2 = MediaPlayer.create(context, R.raw.audio00026);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00027":
                if (p4p8_1 == null || p4p8_2 == null) {
                    p4p8_1 = MediaPlayer.create(context, R.raw.audio00027);
                    p4p8_2 = MediaPlayer.create(context, R.raw.audio00027);
                    AudioController.setVolumn(tid, volumn);
                }
                break;

            case "00028":
                if (p5p1_1 == null || p5p1_2 == null) {
                    p5p1_1 = MediaPlayer.create(context, R.raw.audio00028);
                    p5p1_2 = MediaPlayer.create(context, R.raw.audio00028);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00029":
                if (p5p2_1 == null || p5p2_2 == null) {
                    try {
                        p5p2_1 = new MediaPlayer();
                        p5p2_2 = new MediaPlayer();
                        p5p2_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00029.mp3");
                        p5p2_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00029.mp3");
                        p5p2_1.prepareAsync();
                        p5p2_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00030":
                if (p5p3_1 == null || p5p3_2 == null) {
                    try {
                        p5p3_1 = new MediaPlayer();
                        p5p3_2 = new MediaPlayer();
                        p5p3_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00030.mp3");
                        p5p3_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00030.mp3");
                        p5p3_1.prepareAsync();
                        p5p3_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00031":
                if (p5p4_1 == null || p5p4_2 == null) {
                    try {
                        p5p4_1 = new MediaPlayer();
                        p5p4_2 = new MediaPlayer();
                        p5p4_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00031.mp3");
                        p5p4_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00031.mp3");
                        p5p4_1.prepareAsync();
                        p5p4_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00032":
                if (p5p5_1 == null || p5p5_2 == null) {
                    try {
                        p5p5_1 = new MediaPlayer();
                        p5p5_2 = new MediaPlayer();
                        p5p5_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00032.mp3");
                        p5p5_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00032.mp3");
                        p5p5_1.prepareAsync();
                        p5p5_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00033":
                if (p5p6_1 == null || p5p6_2 == null) {
                    try {
                        p5p6_1 = new MediaPlayer();
                        p5p6_2 = new MediaPlayer();
                        p5p6_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00033.mp3");
                        p5p6_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00033.mp3");
                        p5p6_1.prepareAsync();
                        p5p6_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00034":
                if (p5p7_1 == null || p5p7_2 == null) {
                    try {
                        p5p7_1 = new MediaPlayer();
                        p5p7_2 = new MediaPlayer();
                        p5p7_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00034.mp3");
                        p5p7_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00034.mp3");
                        p5p7_1.prepareAsync();
                        p5p7_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;

            case "00035":
                if (p6p1_1 == null || p6p1_2 == null) {
                    p6p1_1 = MediaPlayer.create(context, R.raw.audio00035);
                    p6p1_2 = MediaPlayer.create(context, R.raw.audio00035);
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00036":
                if (p6p2_1 == null || p6p2_2 == null) {
                    try {
                        p6p2_1 = new MediaPlayer();
                        p6p2_2 = new MediaPlayer();
                        p6p2_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00036.mp3");
                        p6p2_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00036.mp3");
                        p6p2_1.prepareAsync();
                        p6p2_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00037":
                if (p6p3_1 == null || p6p3_2 == null) {
                    try {
                        p6p3_1 = new MediaPlayer();
                        p6p3_2 = new MediaPlayer();
                        p6p3_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00037.mp3");
                        p6p3_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00037.mp3");
                        p6p3_1.prepareAsync();
                        p6p3_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00038":
                if (p6p4_1 == null || p6p4_2 == null) {
                    try {
                        p6p4_1 = new MediaPlayer();
                        p6p4_2 = new MediaPlayer();
                        p6p4_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00038.mp3");
                        p6p4_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00038.mp3");
                        p6p4_1.prepareAsync();
                        p6p4_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "39":
                if (p6p5_1 == null || p6p5_2 == null) {
                    try {
                        p6p5_1 = new MediaPlayer();
                        p6p5_2 = new MediaPlayer();
                        p6p5_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00039.mp3");
                        p6p5_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00039.mp3");
                        p6p5_1.prepareAsync();
                        p6p5_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00040":
                if (p6p6_1 == null || p6p6_2 == null) {
                    try {
                        p6p6_1 = new MediaPlayer();
                        p6p6_2 = new MediaPlayer();
                        p6p6_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00040.mp3");
                        p6p6_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00040.mp3");
                        p6p6_1.prepareAsync();
                        p6p6_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00041":
                if (p6p7_1 == null || p6p7_2 == null) {
                    try {
                        p6p7_1 = new MediaPlayer();
                        p6p7_2 = new MediaPlayer();
                        p6p7_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00041.mp3");
                        p6p7_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00041.mp3");
                        p6p7_1.prepareAsync();
                        p6p7_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;

            case "00042":
                if (p7p1_1 == null || p7p1_2 == null) {
                    p7p1_1 = MediaPlayer.create(context, R.raw.audio00042);
                    p7p1_2 = MediaPlayer.create(context, R.raw.audio00042);
                    AudioController.setVolumn(tid, volumn);
                }
            case "00043":
                if (p7p2_1 == null || p7p2_2 == null) {
                    try {
                        p7p2_1 = new MediaPlayer();
                        p7p2_2 = new MediaPlayer();
                        p7p2_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00043.mp3");
                        p7p2_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00043.mp3");
                        p7p2_1.prepareAsync();
                        p7p2_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00044":
                if (p7p3_1 == null || p7p3_2 == null) {
                    try {
                        p7p3_1 = new MediaPlayer();
                        p7p3_2 = new MediaPlayer();
                        p7p3_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00044.mp3");
                        p7p3_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00044.mp3");
                        p7p3_1.prepareAsync();
                        p7p3_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00045":
                if (p7p4_1 == null || p7p4_2 == null) {
                    try {
                        p7p4_1 = new MediaPlayer();
                        p7p4_2 = new MediaPlayer();
                        p7p4_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00045.mp3");
                        p7p4_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00045.mp3");
                        p7p4_1.prepareAsync();
                        p7p4_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00046":
                if (p7p5_1 == null || p7p5_2 == null) {
                    try {
                        p7p5_1 = new MediaPlayer();
                        p7p5_2 = new MediaPlayer();
                        p7p5_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00046.mp3");
                        p7p5_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00046.mp3");
                        p7p5_1.prepareAsync();
                        p7p5_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00047":
                if (p7p6_1 == null || p7p6_2 == null) {
                    try {
                        p7p6_1 = new MediaPlayer();
                        p7p6_2 = new MediaPlayer();
                        p7p6_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00047.mp3");
                        p7p6_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00047.mp3");
                        p7p6_1.prepareAsync();
                        p7p6_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00048":
                if (p7p7_1 == null || p7p7_2 == null) {
                    try {
                        p7p7_1 = new MediaPlayer();
                        p7p7_2 = new MediaPlayer();
                        p7p7_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00048.mp3");
                        p7p7_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00048.mp3");
                        p7p7_1.prepareAsync();
                        p7p7_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;

        }
    }
}
