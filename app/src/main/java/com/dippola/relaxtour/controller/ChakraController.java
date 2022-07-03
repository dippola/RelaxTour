package com.dippola.relaxtour.controller;

import android.util.Log;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.pages.ChakraPage;

public class ChakraController {
    public static class p5t1 extends Thread {
        String pnp;

        public p5t1(String pnp) {
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
                        new p5t2(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p5t2 extends Thread {
        String pnp;
        public p5t2(String pnp) {
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
                        new p5t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopChakra(String pnp) {
        int position = Integer.parseInt(pnp.substring(2, 3));
        if (position == 1) {
            if (MPList.p5p1_1 != null && MPList.p5p1_2 != null) {
                MPList.p5p1_1.stop();
                MPList.p5p1_1.prepareAsync();
                MPList.p5p1_2.stop();
                MPList.p5p1_2.prepareAsync();
//                MPList.p5p1_1.stop();
////                MPList.p5p1_1.prepareAsync();
//                MPList.p5p1_1.release();
//                MPList.p5p1_1 = null;
//                MPList.p5p1_2.stop();
////                MPList.p5p1_2.prepareAsync();
//                MPList.p5p1_2.release();
//                MPList.p5p1_2 = null;
            }
        } else if (position == 2) {
            if (MPList.p5p2_1 != null && MPList.p5p2_2 != null) {
                MPList.p5p2_1.stop();
                MPList.p5p2_1.prepareAsync();
                MPList.p5p2_2.stop();
                MPList.p5p2_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.p5p3_1 != null && MPList.p5p3_2 != null) {
                MPList.p5p3_1.stop();
                MPList.p5p3_1.prepareAsync();
                MPList.p5p3_2.stop();
                MPList.p5p3_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.p5p4_1 != null && MPList.p5p4_2 != null) {
                MPList.p5p4_1.stop();
                MPList.p5p4_1.prepareAsync();
                MPList.p5p4_2.stop();
                MPList.p5p4_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.p5p5_1 != null && MPList.p5p5_2 != null) {
                MPList.p5p5_1.stop();
                MPList.p5p5_1.prepareAsync();
                MPList.p5p5_2.stop();
                MPList.p5p5_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.p5p6_1 != null && MPList.p5p6_2 != null) {
                MPList.p5p6_1.stop();
                MPList.p5p6_1.prepareAsync();
                MPList.p5p6_2.stop();
                MPList.p5p6_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.p5p7_1 != null && MPList.p5p7_2 != null) {
                MPList.p5p7_1.stop();
                MPList.p5p7_1.prepareAsync();
                MPList.p5p7_2.stop();
                MPList.p5p7_2.prepareAsync();
            }
        }
        new p5t1(null).setStop(true);
        new p5t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("1-1")) {
            return 60000-3000;
        }
        if (pnp.equals("1-2")) {
            return 60000-3000;
        }
        if (pnp.equals("1-3")) {
            return 60000-3000;
        }
        if (pnp.equals("1-4")) {
            return 60000-3000;
        }
        if (pnp.equals("1-5")) {
            return 60000-3000;
        }
        if (pnp.equals("1-6")) {
            return 60000-3000;
        }
        return 0;
    }
}
