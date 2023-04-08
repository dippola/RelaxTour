package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class MantraController {
    public static class p6t1 extends Thread {
        String pnp;

        public p6t1(String pnp) {
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
                        new p6t2(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p6t2 extends Thread {
        String pnp;
        public p6t2(String pnp) {
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
                        new p6t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopMantra(String pnp) {
        int position = Integer.parseInt(pnp.substring(2, 3));
        if (position == 1) {
            if (MPList.p6p1_1 != null && MPList.p6p1_2 != null) {
                MPList.p6p1_1.stop();
                MPList.p6p1_1.prepareAsync();
                MPList.p6p1_2.stop();
                MPList.p6p1_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.p6p2_1 != null && MPList.p6p2_2 != null) {
                MPList.p6p2_1.stop();
                MPList.p6p2_1.prepareAsync();
                MPList.p6p2_2.stop();
                MPList.p6p2_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.p6p3_1 != null && MPList.p6p3_2 != null) {
                MPList.p6p3_1.stop();
                MPList.p6p3_1.prepareAsync();
                MPList.p6p3_2.stop();
                MPList.p6p3_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.p6p4_1 != null && MPList.p6p4_2 != null) {
                MPList.p6p4_1.stop();
                MPList.p6p4_1.prepareAsync();
                MPList.p6p4_2.stop();
                MPList.p6p4_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.p6p5_1 != null && MPList.p6p5_2 != null) {
                MPList.p6p5_1.stop();
                MPList.p6p5_1.prepareAsync();
                MPList.p6p5_2.stop();
                MPList.p6p5_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.p6p6_1 != null && MPList.p6p6_2 != null) {
                MPList.p6p6_1.stop();
                MPList.p6p6_1.prepareAsync();
                MPList.p6p6_2.stop();
                MPList.p6p6_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.p6p7_1 != null && MPList.p6p7_2 != null) {
                MPList.p6p7_1.stop();
                MPList.p6p7_1.prepareAsync();
                MPList.p6p7_2.stop();
                MPList.p6p7_2.prepareAsync();
            }
        }
        new p6t1(null).setStop(true);
        new p6t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("6-1")) {
            return 226600-3000;
        }
        if (pnp.equals("6-2")) {
            return 226600-3000;
        }
        if (pnp.equals("6-3")) {
            return 226600-3000;
        }
        if (pnp.equals("6-4")) {
            return 226600-3000;
        }
        if (pnp.equals("6-5")) {
            return 226600-3000;
        }
        if (pnp.equals("6-6")) {
            return 226600-3000;
        }
        if (pnp.equals("6-7")) {
            return 226600-3000;
        }
        return 0;
    }
}
