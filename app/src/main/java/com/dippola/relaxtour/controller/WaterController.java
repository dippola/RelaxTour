package com.dippola.relaxtour.controller;

import static com.dippola.relaxtour.pages.RainPage.p1p1_1;
import static com.dippola.relaxtour.pages.RainPage.p1p1_2;
import static com.dippola.relaxtour.pages.RainPage.p1p2_1;
import static com.dippola.relaxtour.pages.RainPage.p1p2_2;
import static com.dippola.relaxtour.pages.WaterPage.p2p1_1;
import static com.dippola.relaxtour.pages.WaterPage.p2p1_2;
import static com.dippola.relaxtour.pages.WaterPage.p2p2_1;
import static com.dippola.relaxtour.pages.WaterPage.p2p2_2;
import static com.dippola.relaxtour.pages.WaterPage.p2p3_1;
import static com.dippola.relaxtour.pages.WaterPage.p2p3_2;
import static com.dippola.relaxtour.pages.WaterPage.p2p4_1;
import static com.dippola.relaxtour.pages.WaterPage.p2p4_2;
import static com.dippola.relaxtour.pages.WaterPage.p2p5_1;
import static com.dippola.relaxtour.pages.WaterPage.p2p5_2;
import static com.dippola.relaxtour.pages.WaterPage.p2p6_1;
import static com.dippola.relaxtour.pages.WaterPage.p2p6_2;

public class WaterController {
    public static class p2t1 extends Thread {
        String pnp;

        public p2t1(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (p1p1_1.isPlaying()) {
                    int i = p1p1_1.getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        p1p1_2.start();
                        new p2t2(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p2t2 extends Thread {
        String pnp;
        public p2t2(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (p1p1_2.isPlaying()) {
                    int i = p1p1_2.getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        p1p1_1.start();
                        new p2t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage2() {
        p2p1_1.stop();
        p2p1_1.prepareAsync();
        p2p1_2.stop();
        p2p1_2.prepareAsync();
        p2p2_1.stop();
        p2p2_1.prepareAsync();
        p2p2_2.stop();
        p2p2_2.prepareAsync();

        p2p3_1.stop();
        p2p3_1.prepareAsync();
        p2p3_2.stop();
        p2p3_2.prepareAsync();
        p2p4_1.stop();
        p2p4_1.prepareAsync();
        p2p4_2.stop();
        p2p4_2.prepareAsync();

        p2p5_1.stop();
        p2p5_1.prepareAsync();
        p2p5_2.stop();
        p2p5_2.prepareAsync();
        p2p6_1.stop();
        p2p6_1.prepareAsync();
        p2p6_2.stop();
        p2p6_2.prepareAsync();
        new p2t1(null).setStop(true);
        new p2t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("2-1")) {
            return 174000-3000;
        } else if (pnp.equals("2-2")) {
            return 60100-3000;
        } else if (pnp.equals("2-3")) {
            return 119900-3000;
        } else if (pnp.equals("2-4")) {
            return 401700-3000;
        } else if (pnp.equals("2-5")) {
            return 120200-3000;
        } else if (pnp.equals("2-6")) {
            return 86700-3000;
        } else {
            return 0;
        }
    }
}
