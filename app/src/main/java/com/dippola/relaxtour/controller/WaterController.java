package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class WaterController {
    public static class p2t1 extends Thread {
        String tid;

        public p2t1(String tid) {
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
                        new p2t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p2t2 extends Thread {
        String tid;
        public p2t2(String tid) {
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
                        new p2t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage2(int position) {
        if (position == 1) {
            if (MPList.t00007_1 != null && MPList.t00007_2 != null) {
                MPList.t00007_1.stop();
                MPList.t00007_1.prepareAsync();
                MPList.t00007_2.stop();
                MPList.t00007_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00008_1 != null && MPList.t00008_2 != null) {
                MPList.t00008_1.stop();
                MPList.t00008_1.prepareAsync();
                MPList.t00008_2.stop();
                MPList.t00008_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00009_1 != null && MPList.t00009_2 != null) {
                MPList.t00009_1.stop();
                MPList.t00009_1.prepareAsync();
                MPList.t00009_2.stop();
                MPList.t00009_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00010_1 != null && MPList.t00010_2 != null) {
                MPList.t00010_1.stop();
                MPList.t00010_1.prepareAsync();
                MPList.t00010_2.stop();
                MPList.t00010_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00011_1 != null && MPList.t00011_2 != null) {
                MPList.t00011_1.stop();
                MPList.t00011_1.prepareAsync();
                MPList.t00011_2.stop();
                MPList.t00011_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00012_1 != null && MPList.t00012_2 != null) {
                MPList.t00012_1.stop();
                MPList.t00012_1.prepareAsync();
                MPList.t00012_2.stop();
                MPList.t00012_2.prepareAsync();
            }
        }
        new p2t1(null).setStop(true);
        new p2t2(null).setStop(true);
    }

    private static int getSec(String tid) {
        if (tid.equals("00007")) {
            return 174000-3000;
        } else if (tid.equals("00008")) {
            return 60100-3000;
        } else if (tid.equals("00009")) {
            return 119900-3000;
        } else if (tid.equals("00010")) {
            return 401700-3000;
        } else if (tid.equals("00011")) {
            return 120200-3000;
        } else if (tid.equals("00012")) {
            return 86700-3000;
        } else {
            return 0;
        }
    }
}
