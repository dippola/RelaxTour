package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class RainController {
    public static class p1t1 extends Thread {
        String tid;

        public p1t1(String tid) {
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
                        new p1t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p1t2 extends Thread {
        String tid;
        public p1t2(String tid) {
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
                        new p1t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage1(int position) {
        if (position == 1) {
            if (MPList.t00001_1 != null && MPList.t00001_2 != null) {
                MPList.t00001_1.stop();
                MPList.t00001_1.prepareAsync();
                MPList.t00001_2.stop();
                MPList.t00001_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00002_1 != null && MPList.t00002_2 != null) {
                MPList.t00002_1.stop();
                MPList.t00002_1.prepareAsync();
                MPList.t00002_2.stop();
                MPList.t00002_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00003_1 != null && MPList.t00003_2 != null) {
                MPList.t00003_1.stop();
                MPList.t00003_1.prepareAsync();
                MPList.t00003_2.stop();
                MPList.t00003_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00004_1 != null && MPList.t00004_2 != null) {
                MPList.t00004_1.stop();
                MPList.t00004_1.prepareAsync();
                MPList.t00004_2.stop();
                MPList.t00004_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00005_1 != null && MPList.t00005_2 != null) {
                MPList.t00005_1.stop();
                MPList.t00005_1.prepareAsync();
                MPList.t00005_2.stop();
                MPList.t00005_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00006_1 != null && MPList.t00006_2 != null) {
                MPList.t00006_1.stop();
                MPList.t00006_1.prepareAsync();
                MPList.t00006_2.stop();
                MPList.t00006_2.prepareAsync();
            }
        }
        new p1t1(null).setStop(true);
        new p1t2(null).setStop(true);
    }

    private static int getSec(String tid) {
        if (tid.equals("00001")) {
            return 73100-3000;
        } else if (tid.equals("00002")) {
            return 85500-3000;
        } else if (tid.equals("00003")) {
            return 110900-3000;
        } else if (tid.equals("00004")) {
            return 141800-3000;
        } else if (tid.equals("00005")) {
            return 111300-3000;
        } else if (tid.equals("00006")) {
            return 215100-3000;
        } else {
            return 0;
        }
    }
}
