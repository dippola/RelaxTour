package com.dippola.relaxtour.controller;

import android.util.Log;

import com.dippola.relaxtour.MPList;

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
                if (AudioController.playingListindex0_1(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_1(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_2(pnp).start();
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
                if (AudioController.playingListindex0_2(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_2(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_1(pnp).start();
                        new p3t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage3(String pnp) {
        int position = Integer.parseInt(pnp.substring(2, 3));
        if (position == 1) {
            if (MPList.p3p1_1 != null && MPList.p3p1_2 != null) {
                MPList.p3p1_1.stop();
                MPList.p3p1_1.prepareAsync();
                MPList.p3p1_2.stop();
                MPList.p3p1_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.p3p2_1 != null && MPList.p3p2_2 != null) {
                MPList.p3p2_1.stop();
                MPList.p3p2_1.prepareAsync();
                MPList.p3p2_2.stop();
                MPList.p3p2_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.p3p3_1 != null && MPList.p3p3_2 != null) {
                MPList.p3p3_1.stop();
                MPList.p3p3_1.prepareAsync();
                MPList.p3p3_2.stop();
                MPList.p3p3_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.p3p4_1 != null && MPList.p3p4_2 != null) {
                MPList.p3p4_1.stop();
                MPList.p3p4_1.prepareAsync();
                MPList.p3p4_2.stop();
                MPList.p3p4_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.p3p5_1 != null && MPList.p3p5_2 != null) {
                MPList.p3p5_1.stop();
                MPList.p3p5_1.prepareAsync();
                MPList.p3p5_2.stop();
                MPList.p3p5_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.p3p6_1 != null && MPList.p3p6_2 != null) {
                MPList.p3p6_1.stop();
                MPList.p3p6_1.prepareAsync();
                MPList.p3p6_2.stop();
                MPList.p3p6_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.p3p7_1 != null && MPList.p3p7_2 != null) {
                MPList.p3p7_1.stop();
                MPList.p3p7_1.prepareAsync();
                MPList.p3p7_2.stop();
                MPList.p3p7_2.prepareAsync();
            }
        }
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
