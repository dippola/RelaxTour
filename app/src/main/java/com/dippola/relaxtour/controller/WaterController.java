package com.dippola.relaxtour.controller;

import com.dippola.relaxtour.MPList;

public class WaterController {
    public static class p2t1 extends Thread {
        String pnp;

        public p2t1(String pnp) {
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
                        new p2t2(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }
    public static class p2t2 extends Thread {
        String pnp;
        public p2t2(String pnp) {
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
                        new p2t1(pnp).start();
                        setStop(true);
                    }
                }
            }
        }
    }

    public static void stopPage2(String pnp) {
        int position = Integer.parseInt(pnp.substring(2, 3));
        if (position == 1) {
            if (MPList.p2p1_1 != null && MPList.p2p1_2 != null) {
                MPList.p2p1_1.stop();
                MPList.p2p1_1.prepareAsync();
                MPList.p2p1_2.stop();
                MPList.p2p1_2.prepareAsync();
            }
        } else if (position == 2) {
            if (MPList.p2p2_1 != null && MPList.p2p2_2 != null) {
                MPList.p2p2_1.stop();
                MPList.p2p2_1.prepareAsync();
                MPList.p2p2_2.stop();
                MPList.p2p2_2.prepareAsync();
            }
        } else if (position == 3) {
            if (MPList.p2p3_1 != null && MPList.p2p3_2 != null) {
                MPList.p2p3_1.stop();
                MPList.p2p3_1.prepareAsync();
                MPList.p2p3_2.stop();
                MPList.p2p3_2.prepareAsync();
            }
        } else if (position == 4) {
            if (MPList.p2p4_1 != null && MPList.p2p4_2 != null) {
                MPList.p2p4_1.stop();
                MPList.p2p4_1.prepareAsync();
                MPList.p2p4_2.stop();
                MPList.p2p4_2.prepareAsync();
            }
        } else if (position == 5) {
            if (MPList.p2p5_1 != null && MPList.p2p5_2 != null) {
                MPList.p2p5_1.stop();
                MPList.p2p5_1.prepareAsync();
                MPList.p2p5_2.stop();
                MPList.p2p5_2.prepareAsync();
            }
        } else if (position == 6) {
            if (MPList.p2p6_1 != null && MPList.p2p6_2 != null) {
                MPList.p2p6_1.stop();
                MPList.p2p6_1.prepareAsync();
                MPList.p2p6_2.stop();
                MPList.p2p6_2.prepareAsync();
            }
        }
        new p2t1(null).setStop(true);
        new p2t2(null).setStop(true);
    }

    private static int getSec(String pnp) {
        if (pnp.equals("2-1")) {
            return 174000-3000;
        } else if (pnp.equals("2-2")) {
            return 60100-3000;
        } else if (pnp.equals("2-3")) {
            return 119900-3000;
        } else if (pnp.equals("2-4")) {
            return 401700-3000;
        } else if (pnp.equals("2-5")) {
            return 120200-3000;
        } else if (pnp.equals("2-6")) {
            return 86700-3000;
        } else {
            return 0;
        }
    }
}
