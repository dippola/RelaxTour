package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class WindController {
    public static class p3t1 extends Thread {
        String tid;

        public p3t1(String tid) {
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
                        new p3t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p3t2 extends Thread {
        String tid;
        public p3t2(String tid) {
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
                        new p3t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage3(int position) {
        if (position == 1) {
            if (MPList.t00013_1 != null && MPList.t00013_2 != null) {
                MPList.t00013_1.stop();
                MPList.t00013_1.prepareAsync();
                MPList.t00013_2.stop();
                MPList.t00013_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00014_1 != null && MPList.t00014_2 != null) {
                MPList.t00014_1.stop();
                MPList.t00014_1.prepareAsync();
                MPList.t00014_2.stop();
                MPList.t00014_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00015_1 != null && MPList.t00015_2 != null) {
                MPList.t00015_1.stop();
                MPList.t00015_1.prepareAsync();
                MPList.t00015_2.stop();
                MPList.t00015_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00016_1 != null && MPList.t00016_2 != null) {
                MPList.t00016_1.stop();
                MPList.t00016_1.prepareAsync();
                MPList.t00016_2.stop();
                MPList.t00016_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00017_1 != null && MPList.t00017_2 != null) {
                MPList.t00017_1.stop();
                MPList.t00017_1.prepareAsync();
                MPList.t00017_2.stop();
                MPList.t00017_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00018_1 != null && MPList.t00018_2 != null) {
                MPList.t00018_1.stop();
                MPList.t00018_1.prepareAsync();
                MPList.t00018_2.stop();
                MPList.t00018_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.t00019_1 != null && MPList.t00019_2 != null) {
                MPList.t00019_1.stop();
                MPList.t00019_1.prepareAsync();
                MPList.t00019_2.stop();
                MPList.t00019_2.prepareAsync();
            }
        }
        new p3t1(null).setStop(true);
        new p3t2(null).setStop(true);
    }

    private static int getSec(String tid) {
        if (tid.equals("00013")) {
            return 125900-3000;
        } else if (tid.equals("00014")) {
            return 180000-3000;
        } else if (tid.equals("00015")) {
            return 137700-3000;
        } else if (tid.equals("00016")) {
            return 97900-3000;
        } else if (tid.equals("00017")) {
            return 40000-3000;
        } else if (tid.equals("00018")) {
            return 121400-3000;
        } else if (tid.equals("00019")) {
            return 129400-3000;
        } else {
            return 0;
        }
    }
}
