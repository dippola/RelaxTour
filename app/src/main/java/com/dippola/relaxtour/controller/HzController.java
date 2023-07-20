package com.dippola.relaxtour.controller;

import android.media.AudioTrack;
import android.util.Log;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.pages.HzPage;

@Keep
public class HzController {
    public static class p7t1 extends Thread {
        String pnp;

        public p7t1(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (AudioController.playingListindex0_1(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_1(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_2(pnp).start();
                        new p7t2(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p7t2 extends Thread {
        String pnp;
        public p7t2(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (AudioController.playingListindex0_2(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_2(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_1(pnp).start();
                        new p7t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopHz(int position) {
        if (position == 1) {
            if (MPList.p7p1_1 != null && MPList.p7p1_2 != null) {
                MPList.p7p1_1.stop();
                MPList.p7p1_1.prepareAsync();
                MPList.p7p1_2.stop();
                MPList.p7p1_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.p7p2_1 != null && MPList.p7p2_2 != null) {
                MPList.p7p2_1.stop();
                MPList.p7p2_1.prepareAsync();
                MPList.p7p2_2.stop();
                MPList.p7p2_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.p7p3_1 != null && MPList.p7p3_2 != null) {
                MPList.p7p3_1.stop();
                MPList.p7p3_1.prepareAsync();
                MPList.p7p3_2.stop();
                MPList.p7p3_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.p7p4_1 != null && MPList.p7p4_2 != null) {
                MPList.p7p4_1.stop();
                MPList.p7p4_1.prepareAsync();
                MPList.p7p4_2.stop();
                MPList.p7p4_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.p7p5_1 != null && MPList.p7p5_2 != null) {
                MPList.p7p5_1.stop();
                MPList.p7p5_1.prepareAsync();
                MPList.p7p5_2.stop();
                MPList.p7p5_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.p7p6_1 != null && MPList.p7p6_2 != null) {
                MPList.p7p6_1.stop();
                MPList.p7p6_1.prepareAsync();
                MPList.p7p6_2.stop();
                MPList.p7p6_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.p7p7_1 != null && MPList.p7p7_2 != null) {
                MPList.p7p7_1.stop();
                MPList.p7p7_1.prepareAsync();
                MPList.p7p7_2.stop();
                MPList.p7p7_2.prepareAsync();
            }
        }
        new p7t1(null).setStop(true);
        new p7t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("7-1")) {
            return 600000-2200;
        } else if (pnp.equals("7-2")) {
            return 600000-2200;
        } else if (pnp.equals("7-3")) {
            return 600000-2200;
        } else if (pnp.equals("7-4")) {
            return 600000-2200;
        } else if (pnp.equals("7-5")) {
            return 600000-2200;
        } else if (pnp.equals("7-6")) {
            return 600000-2200;
        } else if (pnp.equals("7-7")) {
            return 600000-2200;
        } else {
            return 0;
        }
    }
}
