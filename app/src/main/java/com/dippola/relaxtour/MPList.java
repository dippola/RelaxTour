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
    public static MediaPlayer t00049_1, t00049_2;
    public static MediaPlayer t00050_1, t00050_2;
    public static MediaPlayer t00051_1, t00051_2;

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
                    try {
                        t00005_1 = new MediaPlayer();
                        t00005_2 = new MediaPlayer();
                        t00005_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00005.mp3");
                        t00005_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00005.mp3");
                        t00005_1.prepareAsync();
                        t00005_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00006":
                if (t00006_1 == null || t00006_2 == null) {
                    try {
                        t00006_1 = new MediaPlayer();
                        t00006_2 = new MediaPlayer();
                        t00006_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00006.mp3");
                        t00006_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00006.mp3");
                        t00006_1.prepareAsync();
                        t00006_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        t00011_1 = new MediaPlayer();
                        t00011_2 = new MediaPlayer();
                        t00011_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00011.mp3");
                        t00011_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00011.mp3");
                        t00011_1.prepareAsync();
                        t00011_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00012":
                if (t00012_1 == null || t00012_2 == null) {
                    try {
                        t00012_1 = new MediaPlayer();
                        t00012_2 = new MediaPlayer();
                        t00012_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00012.mp3");
                        t00012_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00012.mp3");
                        t00012_1.prepareAsync();
                        t00012_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        t00017_1 = new MediaPlayer();
                        t00017_2 = new MediaPlayer();
                        t00017_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00017.mp3");
                        t00017_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00017.mp3");
                        t00017_1.prepareAsync();
                        t00017_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00018":
                if (t00018_1 == null || t00018_2 == null) {
                    try {
                        t00018_2 = new MediaPlayer();
                        t00018_2 = new MediaPlayer();
                        t00018_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00018.mp3");
                        t00018_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00018.mp3");
                        t00018_1.prepareAsync();
                        t00018_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00019":
                if (t00019_1 == null || t00019_2 == null) {
                    try {
                        t00019_1 = new MediaPlayer();
                        t00019_2 = new MediaPlayer();
                        t00019_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00019.mp3");
                        t00019_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00019.mp3");
                        t00019_1.prepareAsync();
                        t00019_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        t00025_1 = new MediaPlayer();
                        t00025_2 = new MediaPlayer();
                        t00025_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00025.mp3");
                        t00025_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00025.mp3");
                        t00025_1.prepareAsync();
                        t00025_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00026":
                if (t00026_1 == null || t00026_2 == null) {
                    try {
                        t00026_1 = new MediaPlayer();
                        t00026_2 = new MediaPlayer();
                        t00026_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00026.mp3");
                        t00026_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00026.mp3");
                        t00026_1.prepareAsync();
                        t00026_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00027":
                if (t00027_1 == null || t00027_2 == null) {
                    try {
                        t00027_1 = new MediaPlayer();
                        t00027_2 = new MediaPlayer();
                        t00027_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00027.mp3");
                        t00027_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00027.mp3");
                        t00027_1.prepareAsync();
                        t00027_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            case "00049":
                if (t00049_1 == null || t00049_2 == null) {
                    try {
                        t00049_1 = new MediaPlayer();
                        t00049_2 = new MediaPlayer();
                        t00049_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00049.mp3");
                        t00049_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00049.mp3");
                        t00049_1.prepareAsync();
                        t00049_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00050":
                if (t00050_1 == null || t00050_2 == null) {
                    try {
                        t00050_1 = new MediaPlayer();
                        t00050_2 = new MediaPlayer();
                        t00050_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00050.mp3");
                        t00050_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00050.mp3");
                        t00050_1.prepareAsync();
                        t00050_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
            case "00051":
                if (t00051_1 == null || t00051_2 == null) {
                    try {
                        t00051_1 = new MediaPlayer();
                        t00051_2 = new MediaPlayer();
                        t00051_1.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00051.mp3");
                        t00051_2.setDataSource(context.getApplicationInfo().dataDir + "/cache/audio00051.mp3");
                        t00051_1.prepareAsync();
                        t00051_2.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AudioController.setVolumn(tid, volumn);
                }
                break;
        }
    }

//    private static MediaPlayer returnMP1(String tid) {
//        if (tid.equals("00001")) {
//            return t00001_1;
//        } else if (tid.equals("00002")) {
//            return t00002_1;
//        } else if (tid.equals("00003")) {
//            return t00003_1;
//        } else if (tid.equals("00004")) {
//            return t00004_1;
//        } else if (tid.equals("00005")) {
//            return t00005_1;
//        } else if (tid.equals("00006")) {
//            return t00006_1;
//        } else if (tid.equals("00007")) {
//            return t00007_1;
//        } else if (tid.equals("00008")) {
//            return t00008_1;
//        } else if (tid.equals("00009")) {
//            return t00009_1;
//        } else if (tid.equals("00010")) {
//            return t00010_1;
//        } else if (tid.equals("00011")) {
//            return t00011_1;
//        } else if (tid.equals("00012")) {
//            return t00012_1;
//        } else if (tid.equals("00013")) {
//            return t00013_1;
//        } else if (tid.equals("00014")) {
//            return t00014_1;
//        } else if (tid.equals("00015")) {
//            return t00015_1;
//        } else if (tid.equals("00016")) {
//            return t00016_1;
//        } else if (tid.equals("00017")) {
//            return t00017_1;
//        } else if (tid.equals("00018")) {
//            return t00018_1;
//        } else if (tid.equals("00019")) {
//            return t00019_1;
//        } else if (tid.equals("00020")) {
//            return t00020_1;
//        } else if (tid.equals("00021")) {
//            return t00021_1;
//        } else if (tid.equals("00022")) {
//            return t00022_1;
//        } else if (tid.equals("00023")) {
//            return t00023_1;
//        } else if (tid.equals("00024")) {
//            return t00024_1;
//        } else if (tid.equals("00025")) {
//            return t00025_1;
//        } else if (tid.equals("00026")) {
//            return t00026_1;
//        } else if (tid.equals("00027")) {
//            return t00027_1;
//        } else if (tid.equals("00028")) {
//            return t00028_1;
//        } else if (tid.equals("00029")) {
//            return t00029_1;
//        } else if (tid.equals("00030")) {
//            return t00030_1;
//        } else if (tid.equals("00031")) {
//            return t00031_1;
//        } else if (tid.equals("00032")) {
//            return t00032_1;
//        } else if (tid.equals("00033")) {
//            return t00033_1;
//        } else if (tid.equals("00034")) {
//            return t00034_1;
//        } else if (tid.equals("00035")) {
//            return t00035_1;
//        } else if (tid.equals("00036")) {
//            return t00036_1;
//        } else if (tid.equals("00037")) {
//            return t00037_1;
//        } else if (tid.equals("00038")) {
//            return t00038_1;
//        } else if (tid.equals("00039")) {
//            return t00039_1;
//        } else if (tid.equals("00040")) {
//            return t00040_1;
//        } else if (tid.equals("00041")) {
//            return t00041_1;
//        } else if (tid.equals("00042")) {
//            return t00042_1;
//        } else if (tid.equals("00043")) {
//            return t00043_1;
//        } else if (tid.equals("00044")) {
//            return t00044_1;
//        } else if (tid.equals("00045")) {
//            return t00045_1;
//        } else if (tid.equals("00046")) {
//            return t00046_1;
//        } else if (tid.equals("00047")) {
//            return t00047_1;
//        } else if (tid.equals("00048")) {
//            return t00048_1;
//        } else {
//            return null;
//        }
//    }
//
//    private static MediaPlayer returnMP2(String tid) {
//        if (tid.equals("00001")) {
//            return t00001_2;
//        } else if (tid.equals("00002")) {
//            return t00002_2;
//        } else if (tid.equals("00003")) {
//            return t00003_2;
//        } else if (tid.equals("00004")) {
//            return t00004_2;
//        } else if (tid.equals("00005")) {
//            return t00005_2;
//        } else if (tid.equals("00006")) {
//            return t00006_2;
//        } else if (tid.equals("00007")) {
//            return t00007_2;
//        } else if (tid.equals("00008")) {
//            return t00008_2;
//        } else if (tid.equals("00009")) {
//            return t00009_2;
//        } else if (tid.equals("00010")) {
//            return t00010_2;
//        } else if (tid.equals("00011")) {
//            return t00011_2;
//        } else if (tid.equals("00012")) {
//            return t00012_2;
//        } else if (tid.equals("00013")) {
//            return t00013_2;
//        } else if (tid.equals("00014")) {
//            return t00014_2;
//        } else if (tid.equals("00015")) {
//            return t00015_2;
//        } else if (tid.equals("00016")) {
//            return t00016_2;
//        } else if (tid.equals("00017")) {
//            return t00017_2;
//        } else if (tid.equals("00018")) {
//            return t00018_2;
//        } else if (tid.equals("00019")) {
//            return t00019_2;
//        } else if (tid.equals("00020")) {
//            return t00020_2;
//        } else if (tid.equals("00021")) {
//            return t00021_2;
//        } else if (tid.equals("00022")) {
//            return t00022_2;
//        } else if (tid.equals("00023")) {
//            return t00023_2;
//        } else if (tid.equals("00024")) {
//            return t00024_2;
//        } else if (tid.equals("00025")) {
//            return t00025_2;
//        } else if (tid.equals("00026")) {
//            return t00026_2;
//        } else if (tid.equals("00027")) {
//            return t00027_2;
//        } else if (tid.equals("00028")) {
//            return t00028_2;
//        } else if (tid.equals("00029")) {
//            return t00029_2;
//        } else if (tid.equals("00030")) {
//            return t00030_2;
//        } else if (tid.equals("00031")) {
//            return t00031_2;
//        } else if (tid.equals("00032")) {
//            return t00032_2;
//        } else if (tid.equals("00033")) {
//            return t00033_2;
//        } else if (tid.equals("00034")) {
//            return t00034_2;
//        } else if (tid.equals("00035")) {
//            return t00035_2;
//        } else if (tid.equals("00036")) {
//            return t00036_2;
//        } else if (tid.equals("00037")) {
//            return t00037_2;
//        } else if (tid.equals("00038")) {
//            return t00038_2;
//        } else if (tid.equals("00039")) {
//            return t00039_2;
//        } else if (tid.equals("00040")) {
//            return t00040_2;
//        } else if (tid.equals("00041")) {
//            return t00041_2;
//        } else if (tid.equals("00042")) {
//            return t00042_2;
//        } else if (tid.equals("00043")) {
//            return t00043_2;
//        } else if (tid.equals("00044")) {
//            return t00044_2;
//        } else if (tid.equals("00045")) {
//            return t00045_2;
//        } else if (tid.equals("00046")) {
//            return t00046_2;
//        } else if (tid.equals("00047")) {
//            return t00047_2;
//        } else if (tid.equals("00048")) {
//            return t00048_2;
//        } else {
//            return null;
//        }
//    }
}
