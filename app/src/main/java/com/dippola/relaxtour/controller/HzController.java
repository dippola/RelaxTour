package com.dippola.relaxtour.controller;

import com.dippola.relaxtour.pages.HzPage;

public class HzController {
    public static void startHz(String pnp) {
        if (pnp.equals("4-1")) {
            HzPage.p4p1.start();
        } else if (pnp.equals("4-2")) {
            HzPage.p4p2.start();
        }
    }

    public static void stopHz(int page, String pnp) {
        if (pnp.equals("4-1")) {
            if (HzPage.p4p1 != null) {
                HzPage.p4p1.stop();
                HzPage.p4p1.prepareAsync();
            }
        } else if (pnp.equals("4-2")) {
            if (HzPage.p4p2 != null) {
                HzPage.p4p2.stop();
                HzPage.p4p2.prepareAsync();
            }
        }
    }
}
