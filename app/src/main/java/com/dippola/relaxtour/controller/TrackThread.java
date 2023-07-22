package com.dippola.relaxtour.controller;

import android.util.Log;

public class TrackThread {
    public static class t1 extends Thread {
        String tid;

        public t1(String tid) {
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
                    if (i >= TrackSecond.getSec(tid)) {
                        AudioController.playingListindex0_2(tid).start();
                        new t2(tid).start();
                        setStop(true);
                    }
                } else {
                    setStop(true);
                }
            }
        }
    }
    public static class t2 extends Thread {
        String tid;
        public t2(String tid) {
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
                    if (i >= TrackSecond.getSec(tid)) {
                        AudioController.playingListindex0_1(tid).start();
                        new t1(tid).start();
                        setStop(true);
                    }
                } else {
                    setStop(true);
                }
            }
        }
    }
}
