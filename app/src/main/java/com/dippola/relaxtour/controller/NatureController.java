package com.dippola.relaxtour.controller;

import static com.dippola.relaxtour.pages.NaturePage.p4p1_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p1_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p2_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p2_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p3_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p3_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p4_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p4_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p5_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p5_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p6_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p6_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p7_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p7_2;
import static com.dippola.relaxtour.pages.NaturePage.p4p8_1;
import static com.dippola.relaxtour.pages.NaturePage.p4p8_2;

import android.util.Log;

public class NatureController {
    public static class p4t1 extends Thread {
        String pnp;

        public p4t1(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                Log.d("NatureController>>>", "p4t1 start");
                if (AudioController.playingListindex0_1(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_1(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_2(pnp).start();
                        new p4t2(pnp).start();
                        setStop(true);
                        Log.d("NatureController>>>", "p4t1 stop");
                    }
                }
            }
        }
    }
    public static class p4t2 extends Thread {
        String pnp;
        public p4t2(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                Log.d("NatureController>>>", "p4t2 start");
                if (AudioController.playingListindex0_2(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_2(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_1(pnp).start();
                        new p4t1(pnp).start();
                        setStop(true);
                        Log.d("NatureController>>>", "p4t2 stop");
                    }
                }
            }
        }
    }

    public static void stopPage4() {
        p4p1_1.stop();
        p4p1_1.prepareAsync();
        p4p1_2.stop();
        p4p1_2.prepareAsync();

        p4p2_1.stop();
        p4p2_1.prepareAsync();
        p4p2_2.stop();
        p4p2_2.prepareAsync();

        p4p3_1.stop();
        p4p3_1.prepareAsync();
        p4p3_2.stop();
        p4p3_2.prepareAsync();

        p4p4_1.stop();
        p4p4_1.prepareAsync();
        p4p4_2.stop();
        p4p4_2.prepareAsync();

        p4p5_1.stop();
        p4p5_1.prepareAsync();
        p4p5_2.stop();
        p4p5_2.prepareAsync();

        p4p6_1.stop();
        p4p6_1.prepareAsync();
        p4p6_2.stop();
        p4p6_2.prepareAsync();

        p4p7_1.stop();
        p4p7_1.prepareAsync();
        p4p7_2.stop();
        p4p7_2.prepareAsync();

        p4p8_1.stop();
        p4p8_1.prepareAsync();
        p4p8_2.stop();
        p4p8_2.prepareAsync();
        new RainController.p1t1(null).setStop(true);
        new RainController.p1t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("4-1")) {
            return 73100-3000;
        } else if (pnp.equals("4-2")) {
            return 75020-3000;
        } else if (pnp.equals("4-3")) {
            return 45500-3000;
        } else if (pnp.equals("4-4")) {
            return 52700-3000;
        } else if (pnp.equals("4-5")) {
            return 21900-3000;
        } else if (pnp.equals("4-6")) {
            return 73800-3000;
        } else if (pnp.equals("4-7")) {
            return 180000-3000;
        } else if (pnp.equals("4-8")) {
            return 146700-3000;
        } else {
            return 0;
        }
    }
}
