package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class MantraController {
    public static class p6t1 extends Thread {
        String tid;

        public p6t1(String tid) {
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
                        new p6t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p6t2 extends Thread {
        String tid;
        public p6t2(String tid) {
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
                        new p6t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopMantra(int position) {
        if (position == 1) {
            if (MPList.t00035_1 != null && MPList.t00035_2 != null) {
                MPList.t00035_1.stop();
                MPList.t00035_1.prepareAsync();
                MPList.t00035_2.stop();
                MPList.t00035_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00036_1 != null && MPList.t00036_2 != null) {
                MPList.t00036_1.stop();
                MPList.t00036_1.prepareAsync();
                MPList.t00036_2.stop();
                MPList.t00036_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00037_1 != null && MPList.t00037_2 != null) {
                MPList.t00037_1.stop();
                MPList.t00037_1.prepareAsync();
                MPList.t00037_2.stop();
                MPList.t00037_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00038_1 != null && MPList.t00038_2 != null) {
                MPList.t00038_1.stop();
                MPList.t00038_1.prepareAsync();
                MPList.t00038_2.stop();
                MPList.t00038_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00039_1 != null && MPList.t00039_2 != null) {
                MPList.t00039_1.stop();
                MPList.t00039_1.prepareAsync();
                MPList.t00039_2.stop();
                MPList.t00039_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00040_1 != null && MPList.t00040_2 != null) {
                MPList.t00040_1.stop();
                MPList.t00040_1.prepareAsync();
                MPList.t00040_2.stop();
                MPList.t00040_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.t00041_1 != null && MPList.t00041_2 != null) {
                MPList.t00041_1.stop();
                MPList.t00041_1.prepareAsync();
                MPList.t00041_2.stop();
                MPList.t00041_2.prepareAsync();
            }
        }
        new p6t1(null).setStop(true);
        new p6t2(null).setStop(true);
    }

    private static int getSec(String tid) {
        if (tid.equals("00035")) {
            return 226600-3000;
        }
        if (tid.equals("00036")) {
            return 226600-3000;
        }
        if (tid.equals("00037")) {
            return 226600-3000;
        }
        if (tid.equals("00038")) {
            return 226600-3000;
        }
        if (tid.equals("00039")) {
            return 226600-3000;
        }
        if (tid.equals("00040")) {
            return 226600-3000;
        }
        if (tid.equals("00041")) {
            return 226600-3000;
        }
        return 0;
    }
}
