package com.dippola.relaxtour.controller;

import android.util.Log;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
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
                if (AudioController.playingListindex0_1(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_1(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_2(pnp).start();
                        new p4t2(pnp).start();
                        setStop(true);
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
                if (AudioController.playingListindex0_2(pnp).isPlaying()) {
                    int i = AudioController.playingListindex0_2(pnp).getCurrentPosition();
                    if (i >= getSec(pnp)) {
                        AudioController.playingListindex0_1(pnp).start();
                        new p4t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage4(String pnp) {
        int position = Integer.parseInt(pnp.substring(2, 3));
        if (position == 1) {
            if (MPList.p4p1_1 != null && MPList.p4p1_2 != null) {
                MPList.p4p1_1.stop();
                MPList.p4p1_1.prepareAsync();
                MPList.p4p1_2.stop();
                MPList.p4p1_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.p4p2_1 != null && MPList.p4p2_2 != null) {
                MPList.p4p2_1.stop();
                MPList.p4p2_1.prepareAsync();
                MPList.p4p2_2.stop();
                MPList.p4p2_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.p4p3_1 != null && MPList.p4p3_2 != null) {
                MPList.p4p3_1.stop();
                MPList.p4p3_1.prepareAsync();
                MPList.p4p3_2.stop();
                MPList.p4p3_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.p4p4_1 != null && MPList.p4p4_2 != null) {
                MPList.p4p4_1.stop();
                MPList.p4p4_1.prepareAsync();
                MPList.p4p4_2.stop();
                MPList.p4p4_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.p4p5_1 != null && MPList.p4p5_2 != null) {
                MPList.p4p5_1.stop();
                MPList.p4p5_1.prepareAsync();
                MPList.p4p5_2.stop();
                MPList.p4p5_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.p4p6_1 != null && MPList.p4p6_2 != null) {
                MPList.p4p6_1.stop();
                MPList.p4p6_1.prepareAsync();
                MPList.p4p6_2.stop();
                MPList.p4p6_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.p4p7_1 != null && MPList.p4p7_2 != null) {
                MPList.p4p7_1.stop();
                MPList.p4p7_1.prepareAsync();
                MPList.p4p7_2.stop();
                MPList.p4p7_2.prepareAsync();
            }
        } else if (position == 8) {
            if (MPList.p4p8_1 != null && MPList.p4p8_2 != null) {
                MPList.p4p8_1.stop();
                MPList.p4p8_1.prepareAsync();
                MPList.p4p8_2.stop();
                MPList.p4p8_2.prepareAsync();
            }
        }

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
