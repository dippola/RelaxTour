package com.dippola.relaxtour.controller;

import androidx.annotation.Keep;

import com.dippola.relaxtour.MPList;

@Keep
public class ChakraController {
    public static class p5t1 extends Thread {
        String tid;

        public p5t1(String tid) {
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
                        new p5t2(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p5t2 extends Thread {
        String tid;
        public p5t2(String tid) {
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
                        new p5t1(tid).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopChakra(int position) {
        if (position == 1) {
            if (MPList.t00028_1 != null && MPList.t00028_2 != null) {
                MPList.t00028_1.stop();
                MPList.t00028_1.prepareAsync();
                MPList.t00028_2.stop();
                MPList.t00028_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.t00029_1 != null && MPList.t00029_2 != null) {
                MPList.t00029_1.stop();
                MPList.t00029_1.prepareAsync();
                MPList.t00029_2.stop();
                MPList.t00029_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.t00030_1 != null && MPList.t00030_2 != null) {
                MPList.t00030_1.stop();
                MPList.t00030_1.prepareAsync();
                MPList.t00030_2.stop();
                MPList.t00030_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.t00031_1 != null && MPList.t00031_2 != null) {
                MPList.t00031_1.stop();
                MPList.t00031_1.prepareAsync();
                MPList.t00031_2.stop();
                MPList.t00031_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.t00032_1 != null && MPList.t00032_2 != null) {
                MPList.t00032_1.stop();
                MPList.t00032_1.prepareAsync();
                MPList.t00032_2.stop();
                MPList.t00032_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.t00033_1 != null && MPList.t00033_2 != null) {
                MPList.t00033_1.stop();
                MPList.t00033_1.prepareAsync();
                MPList.t00033_2.stop();
                MPList.t00033_2.prepareAsync();
            }
        } else if (position == 7) {
            if (MPList.t00034_1 != null && MPList.t00034_2 != null) {
                MPList.t00034_1.stop();
                MPList.t00034_1.prepareAsync();
                MPList.t00034_2.stop();
                MPList.t00034_2.prepareAsync();
            }
        }
        new p5t1(null).setStop(true);
        new p5t2(null).setStop(true);
    }

    private static int getSec(String tid) {
        if (tid.equals("00028")) {
            return 660000-3000;
        }
        if (tid.equals("00029")) {
            return 660000-3000;
        }
        if (tid.equals("00030")) {
            return 660000-3000;
        }
        if (tid.equals("00031")) {
            return 660000-3000;
        }
        if (tid.equals("00032")) {
            return 660000-3000;
        }
        if (tid.equals("00033")) {
            return 660000-3000;
        }
        if (tid.equals("00034")) {
            return 660000-3000;
        }
        return 0;
    }
}
