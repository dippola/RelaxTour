package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class HzController {
    public static class p7t1 extends Thread {
        String tid;

        public p7t1(String tid) {
            this.tid = tid;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (AudioController.playingListindex0_1(tid).isPlaying()) {
                    int i = AudioController.playingListindex0_1(tid).getCurrentPosition();
                    if (i >= getSec(tid)) {
                        AudioController.playingListindex0_2(tid).start();
                        new p7t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p7t2 extends Thread {
        String tid;
        public p7t2(String tid) {
            this.tid = tid;
        }
        private boolean stop;
        public void setStop(boolean stop) {
            this.stop = stop;
        }

        @Override
        public void run() {
            while (!stop) {
                if (AudioController.playingListindex0_2(tid).isPlaying()) {
                    int i = AudioController.playingListindex0_2(tid).getCurrentPosition();
                    if (i >= getSec(tid)) {
                        AudioController.playingListindex0_1(tid).start();
                        new p7t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopHz(int position) {
        if (position == 1) {
            if (MPList.t00042_1 != null && MPList.t00042_2 != null) {
                MPList.t00042_1.stop();
                MPList.t00042_1.prepareAsync();
                MPList.t00042_2.stop();
                MPList.t00042_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00043_1 != null && MPList.t00043_2 != null) {
                MPList.t00043_1.stop();
                MPList.t00043_1.prepareAsync();
                MPList.t00043_2.stop();
                MPList.t00043_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00044_1 != null && MPList.t00044_2 != null) {
                MPList.t00044_1.stop();
                MPList.t00044_1.prepareAsync();
                MPList.t00044_2.stop();
                MPList.t00044_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00045_1 != null && MPList.t00045_2 != null) {
                MPList.t00045_1.stop();
                MPList.t00045_1.prepareAsync();
                MPList.t00045_2.stop();
                MPList.t00045_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00046_1 != null && MPList.t00046_2 != null) {
                MPList.t00046_1.stop();
                MPList.t00046_1.prepareAsync();
                MPList.t00046_2.stop();
                MPList.t00046_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00047_1 != null && MPList.t00047_2 != null) {
                MPList.t00047_1.stop();
                MPList.t00047_1.prepareAsync();
                MPList.t00047_2.stop();
                MPList.t00047_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.t00048_1 != null && MPList.t00048_2 != null) {
                MPList.t00048_1.stop();
                MPList.t00048_1.prepareAsync();
                MPList.t00048_2.stop();
                MPList.t00048_2.prepareAsync();
            }
        }
        new p7t1(null).setStop(true);
        new p7t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("00042")) {
            return 600000-2200;
        } else if (pnp.equals("00043")) {
            return 600000-2200;
        } else if (pnp.equals("00044")) {
            return 600000-2200;
        } else if (pnp.equals("00045")) {
            return 600000-2200;
        } else if (pnp.equals("00046")) {
            return 600000-2200;
        } else if (pnp.equals("00047")) {
            return 600000-2200;
        } else if (pnp.equals("00048")) {
            return 600000-2200;
        } else {
            return 0;
        }
    }
}
