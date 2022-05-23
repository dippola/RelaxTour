package com.dippola.relaxtour.controller;

import android.media.AudioTrack;
import android.util.Log;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.pages.HzPage;

public class HzController {
    public static void stopHz(String pnp) {
        if (MPList.p7.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
            MPList.p7.stop();
            Log.d("HzController>>>", "stop");
        }
    }
}
