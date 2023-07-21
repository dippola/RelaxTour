package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class NatureController {
    public static class p4t1 extends Thread {
        String tid;

        public p4t1(String tid) {
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
                        new p4t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p4t2 extends Thread {
        String tid;
        public p4t2(String tid) {
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
                        new p4t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage4(int position) {
        if (position == 1) {
            if (MPList.t00020_1 != null && MPList.t00020_2 != null) {
                MPList.t00020_1.stop();
                MPList.t00020_1.prepareAsync();
                MPList.t00020_2.stop();
                MPList.t00020_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00021_1 != null && MPList.t00021_2 != null) {
                MPList.t00021_1.stop();
                MPList.t00021_1.prepareAsync();
                MPList.t00021_2.stop();
                MPList.t00021_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00022_1 != null && MPList.t00022_2 != null) {
                MPList.t00022_1.stop();
                MPList.t00022_1.prepareAsync();
                MPList.t00022_2.stop();
                MPList.t00022_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00023_1 != null && MPList.t00023_2 != null) {
                MPList.t00023_1.stop();
                MPList.t00023_1.prepareAsync();
                MPList.t00023_2.stop();
                MPList.t00023_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00024_1 != null && MPList.t00024_2 != null) {
                MPList.t00024_1.stop();
                MPList.t00024_1.prepareAsync();
                MPList.t00024_2.stop();
                MPList.t00024_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00025_1 != null && MPList.t00025_2 != null) {
                MPList.t00025_1.stop();
                MPList.t00025_1.prepareAsync();
                MPList.t00025_2.stop();
                MPList.t00025_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.t00026_1 != null && MPList.t00026_2 != null) {
                MPList.t00026_1.stop();
                MPList.t00026_1.prepareAsync();
                MPList.t00026_2.stop();
                MPList.t00026_2.prepareAsync();
            }
        } else if (position == 8) {
            if (MPList.t00027_1 != null && MPList.t00027_2 != null) {
                MPList.t00027_1.stop();
                MPList.t00027_1.prepareAsync();
                MPList.t00027_2.stop();
                MPList.t00027_2.prepareAsync();
            }
        }

        new RainController.p1t1(null).setStop(true);
        new RainController.p1t2(null).setStop(true);
    }

    private static int getSec(String tid) {
        if (tid.equals("00020")) {
            return 73100-3000;
        } else if (tid.equals("00021")) {
            return 75020-3000;
        } else if (tid.equals("00022")) {
            return 45500-3000;
        } else if (tid.equals("00023")) {
            return 52700-3000;
        } else if (tid.equals("00024")) {
            return 21900-3000;
        } else if (tid.equals("00025")) {
            return 73800-3000;
        } else if (tid.equals("00026")) {
            return 180000-3000;
        } else if (tid.equals("00027")) {
            return 146700-3000;
        } else {
            return 0;
        }
    }
}
