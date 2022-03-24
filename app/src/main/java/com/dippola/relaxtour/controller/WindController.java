package com.dippola.relaxtour.controller;

import static com.dippola.relaxtour.pages.WindPage.p2p1_1;
import static com.dippola.relaxtour.pages.WindPage.p2p1_2;
import static com.dippola.relaxtour.pages.WindPage.p2p2_1;
import static com.dippola.relaxtour.pages.WindPage.p2p2_2;

public class WindController {
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
                if (p2p1_1.isPlaying()) {
                    int i = p2p1_1.getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        p2p1_2.start();
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
                if (p2p1_2.isPlaying()) {
                    int i = p2p1_2.getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        p2p1_1.start();
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
        new p2t1(null).setStop(true);
        new p2t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("2-1")) {
            return 69000;
        } else if (pnp.equals("2-2")) {
            return 69000;
        } else {
            return 0;
        }
    }
}
