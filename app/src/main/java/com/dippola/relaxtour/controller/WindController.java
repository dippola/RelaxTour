package com.dippola.relaxtour.controller;
import static com.dippola.relaxtour.pages.WindPage.p3p1_1;
import static com.dippola.relaxtour.pages.WindPage.p3p1_2;
import static com.dippola.relaxtour.pages.WindPage.p3p2_1;
import static com.dippola.relaxtour.pages.WindPage.p3p2_2;
import static com.dippola.relaxtour.pages.WindPage.p3p3_1;
import static com.dippola.relaxtour.pages.WindPage.p3p3_2;
import static com.dippola.relaxtour.pages.WindPage.p3p4_1;
import static com.dippola.relaxtour.pages.WindPage.p3p4_2;
import static com.dippola.relaxtour.pages.WindPage.p3p5_1;
import static com.dippola.relaxtour.pages.WindPage.p3p5_2;
import static com.dippola.relaxtour.pages.WindPage.p3p6_1;
import static com.dippola.relaxtour.pages.WindPage.p3p6_2;
import static com.dippola.relaxtour.pages.WindPage.p3p7_1;
import static com.dippola.relaxtour.pages.WindPage.p3p7_2;

public class WindController {
    public static class p3t1 extends Thread {
        String pnp;
        public p3t1(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (p3p1_1.isPlaying()) {
                    int i = p3p1_1.getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        p3p1_2.start();
                        new p3t2(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p3t2 extends Thread {
        String pnp;
        public p3t2(String pnp) {
            this.pnp = pnp;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (p3p1_2.isPlaying()) {
                    int i = p3p1_2.getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        p3p1_1.start();
                        new p3t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage3() {
        p3p1_1.stop();
        p3p1_1.prepareAsync();
        p3p1_2.stop();
        p3p1_2.prepareAsync();
        p3p2_1.stop();
        p3p2_1.prepareAsync();
        p3p2_2.stop();
        p3p2_2.prepareAsync();

        p3p3_1.stop();
        p3p3_1.prepareAsync();
        p3p3_2.stop();
        p3p3_2.prepareAsync();
        p3p4_1.stop();
        p3p4_1.prepareAsync();
        p3p4_2.stop();
        p3p4_2.prepareAsync();

        p3p5_1.stop();
        p3p5_1.prepareAsync();
        p3p5_2.stop();
        p3p5_2.prepareAsync();
        p3p6_1.stop();
        p3p6_1.prepareAsync();
        p3p6_2.stop();
        p3p6_2.prepareAsync();

        p3p7_1.stop();
        p3p7_1.prepareAsync();
        p3p7_2.stop();
        p3p7_2.prepareAsync();
        new p3t1(null).setStop(true);
        new p3t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("3-1")) {
            return 125900-3000;
        } else if (pnp.equals("3-2")) {
            return 180000-3000;
        } else if (pnp.equals("3-3")) {
            return 137700-3000;
        } else if (pnp.equals("3-4")) {
            return 97900-3000;
        } else if (pnp.equals("3-5")) {
            return 40000-3000;
        } else if (pnp.equals("3-6")) {
            return 121400-3000;
        } else if (pnp.equals("3-7")) {
            return 129400-3000;
        } else {
            return 0;
        }
    }
}
