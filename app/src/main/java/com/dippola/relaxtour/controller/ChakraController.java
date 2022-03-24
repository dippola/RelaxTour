package com.dippola.relaxtour.controller;

import com.dippola.relaxtour.pages.ChakraPage;

public class ChakraController {
    public static void startChakra(String pnp) {
        if (pnp.equals("3-1")) {
            ChakraPage.p3p1.start();
        } else if (pnp.equals("3-2")) {
            ChakraPage.p3p2.start();
        }
    }

    public static void stopChakra(int page, String pnp) {
        if (pnp.equals("3-1")) {
            if (ChakraPage.p3p1 != null) {
                ChakraPage.p3p1.stop();
                ChakraPage.p3p1.prepareAsync();
            }
        } else if (pnp.equals("3-2")) {
            if (ChakraPage.p3p2 != null) {
                ChakraPage.p3p2.stop();
                ChakraPage.p3p2.prepareAsync();
            }
        }
    }
}
