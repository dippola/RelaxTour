package com.dippola.relaxtour;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.dippola.relaxtour.controller.AudioController;

public class MPList {
    //page1
    public static MediaPlayer p1p1_1, p1p1_2;
    public static MediaPlayer p1p2_1, p1p2_2;
    public static MediaPlayer p1p3_1, p1p3_2;
    public static MediaPlayer p1p4_1, p1p4_2;
    public static MediaPlayer p1p5_1, p1p5_2;
    public static MediaPlayer p1p6_1, p1p6_2;

    //page2
    public static MediaPlayer p2p1_1, p2p1_2;
    public static MediaPlayer p2p2_1, p2p2_2;
    public static MediaPlayer p2p3_1, p2p3_2;
    public static MediaPlayer p2p4_1, p2p4_2;
    public static MediaPlayer p2p5_1, p2p5_2;
    public static MediaPlayer p2p6_1, p2p6_2;

    //page3
    public static MediaPlayer p3p1_1, p3p1_2;
    public static MediaPlayer p3p2_1, p3p2_2;
    public static MediaPlayer p3p3_1, p3p3_2;
    public static MediaPlayer p3p4_1, p3p4_2;
    public static MediaPlayer p3p5_1, p3p5_2;
    public static MediaPlayer p3p6_1, p3p6_2;
    public static MediaPlayer p3p7_1, p3p7_2;

    //page4
    public static MediaPlayer p4p1_1, p4p1_2;
    public static MediaPlayer p4p2_1, p4p2_2;
    public static MediaPlayer p4p3_1, p4p3_2;
    public static MediaPlayer p4p4_1, p4p4_2;
    public static MediaPlayer p4p5_1, p4p5_2;
    public static MediaPlayer p4p6_1, p4p6_2;
    public static MediaPlayer p4p7_1, p4p7_2;
    public static MediaPlayer p4p8_1, p4p8_2;

    //page5
    public static MediaPlayer p5p1_1, p5p1_2;
    public static MediaPlayer p5p2_1, p5p2_2;
    public static MediaPlayer p5p3_1, p5p3_2;
    public static MediaPlayer p5p4_1, p5p4_2;
    public static MediaPlayer p5p5_1, p5p5_2;
    public static MediaPlayer p5p6_1, p5p6_2;
    public static MediaPlayer p5p7_1, p5p7_2;

    //page6
    public static MediaPlayer p6p1_1, p6p1_2;
    public static MediaPlayer p6p2_1, p6p2_2;
    public static MediaPlayer p6p3_1, p6p3_2;
    public static MediaPlayer p6p4_1, p6p4_2;
    public static MediaPlayer p6p5_1, p6p5_2;
    public static MediaPlayer p6p6_1, p6p6_2;
    public static MediaPlayer p6p7_1, p6p7_2;

    //page7
    public static MediaPlayer p7p1_1, p7p1_2;
    public static MediaPlayer p7p2_1, p7p2_2;
    public static MediaPlayer p7p3_1, p7p3_2;
    public static MediaPlayer p7p4_1, p7p4_2;
    public static MediaPlayer p7p5_1, p7p5_2;
    public static MediaPlayer p7p6_1, p7p6_2;
    public static MediaPlayer p7p7_1, p7p7_2;

    public static void initalMP(String pnp, Context context, int volumn) {
        switch (pnp) {
            case "1-1":
                if (p1p1_1 == null || p1p1_2 == null) {
                    p1p1_1 = MediaPlayer.create(context, R.raw.audio1to1);
                    p1p1_2 = MediaPlayer.create(context, R.raw.audio1to1);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "1-2":
                if (p1p2_1 == null || p1p2_2 == null) {
                    p1p2_1 = MediaPlayer.create(context, R.raw.audio1to2);
                    p1p2_2 = MediaPlayer.create(context, R.raw.audio1to2);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "1-3":
                if (p1p3_1 == null || p1p3_2 == null) {
                    p1p3_1 = MediaPlayer.create(context, R.raw.audio1to3);
                    p1p3_2 = MediaPlayer.create(context, R.raw.audio1to3);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "1-4":
                if (p1p4_1 == null || p1p4_2 == null) {
                    p1p4_1 = MediaPlayer.create(context, R.raw.audio1to4);
                    p1p4_2 = MediaPlayer.create(context, R.raw.audio1to4);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "1-5":
                if (p1p5_1 == null || p1p5_2 == null) {
                    p1p5_1 = MediaPlayer.create(context, R.raw.audio1to5);
                    p1p5_2 = MediaPlayer.create(context, R.raw.audio1to5);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "1-6":
                if (p1p6_1 == null || p1p6_2 == null) {
                    p1p6_1 = MediaPlayer.create(context, R.raw.audio1to6);
                    p1p6_2 = MediaPlayer.create(context, R.raw.audio1to6);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "2-1":
                if (p2p1_1 == null || p2p1_2 == null) {
                    p2p1_1 = MediaPlayer.create(context, R.raw.audio2to1);
                    p2p1_2 = MediaPlayer.create(context, R.raw.audio2to1);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "2-2":
                if (p2p2_1 == null || p2p2_2 == null) {
                    p2p2_1 = MediaPlayer.create(context, R.raw.audio2to2);
                    p2p2_2 = MediaPlayer.create(context, R.raw.audio2to2);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "2-3":
                if (p2p3_1 == null || p2p3_2 == null) {
                    p2p3_1 = MediaPlayer.create(context, R.raw.audio2to3);
                    p2p3_2 = MediaPlayer.create(context, R.raw.audio2to3);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "2-4":
                if (p2p4_1 == null || p2p4_2 == null) {
                    p2p4_1 = MediaPlayer.create(context, R.raw.audio2to4);
                    p2p4_2 = MediaPlayer.create(context, R.raw.audio2to4);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "2-5":
                if (p2p5_1 == null || p2p5_2 == null) {
                    p2p5_1 = MediaPlayer.create(context, R.raw.audio2to5);
                    p2p5_2 = MediaPlayer.create(context, R.raw.audio2to5);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "2-6":
                if (p2p6_1 == null || p2p6_2 == null) {
                    p2p6_1 = MediaPlayer.create(context, R.raw.audio2to6);
                    p2p6_2 = MediaPlayer.create(context, R.raw.audio2to6);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-1":
                if (p3p1_1 == null || p3p1_2 == null) {
                    p3p1_1 = MediaPlayer.create(context, R.raw.audio3to1);
                    p3p1_2 = MediaPlayer.create(context, R.raw.audio3to1);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-2":
                if (p3p2_1 == null || p3p2_2 == null) {
                    p3p2_1 = MediaPlayer.create(context, R.raw.audio3to2);
                    p3p2_2 = MediaPlayer.create(context, R.raw.audio3to2);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-3":
                if (p3p3_1 == null || p3p3_2 == null) {
                    p3p3_1 = MediaPlayer.create(context, R.raw.audio3to3);
                    p3p3_2 = MediaPlayer.create(context, R.raw.audio3to3);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-4":
                if (p3p4_1 == null || p3p4_2 == null) {
                    p3p4_1 = MediaPlayer.create(context, R.raw.audio3to4);
                    p3p4_2 = MediaPlayer.create(context, R.raw.audio3to4);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-5":
                if (p3p5_1 == null || p3p5_2 == null) {
                    p3p5_1 = MediaPlayer.create(context, R.raw.audio3to5);
                    p3p5_2 = MediaPlayer.create(context, R.raw.audio3to5);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-6":
                if (p3p6_1 == null || p3p6_2 == null) {
                    p3p6_1 = MediaPlayer.create(context, R.raw.audio3to6);
                    p3p6_2 = MediaPlayer.create(context, R.raw.audio3to6);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "3-7":
                if (p3p7_1 == null || p3p7_2 == null) {
                    p3p7_1 = MediaPlayer.create(context, R.raw.audio3to7);
                    p3p7_2 = MediaPlayer.create(context, R.raw.audio3to7);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-1":
                if (p4p1_1 == null || p4p1_2 == null) {
                    p4p1_1 = MediaPlayer.create(context, R.raw.audio4to1);
                    p4p1_2 = MediaPlayer.create(context, R.raw.audio4to1);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-2":
                if (p4p2_1 == null || p4p2_2 == null) {
                    p4p2_1 = MediaPlayer.create(context, R.raw.audio4to2);
                    p4p2_2 = MediaPlayer.create(context, R.raw.audio4to2);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-3":
                if (p4p3_1 == null || p4p3_2 == null) {
                    p4p3_1 = MediaPlayer.create(context, R.raw.audio4to3);
                    p4p3_2 = MediaPlayer.create(context, R.raw.audio4to3);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-4":
                if (p4p4_1 == null || p4p4_2 == null) {
                    p4p4_1 = MediaPlayer.create(context, R.raw.audio4to4);
                    p4p4_2 = MediaPlayer.create(context, R.raw.audio4to4);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-5":
                if (p4p5_1 == null || p4p5_2 == null) {
                    p4p5_1 = MediaPlayer.create(context, R.raw.audio4to5);
                    p4p5_2 = MediaPlayer.create(context, R.raw.audio4to5);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-6":
                if (p4p6_1 == null || p4p6_2 == null) {
                    p4p6_1 = MediaPlayer.create(context, R.raw.audio4to6);
                    p4p6_2 = MediaPlayer.create(context, R.raw.audio4to6);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-7":
                if (p4p7_1 == null || p4p7_2 == null) {
                    p4p7_1 = MediaPlayer.create(context, R.raw.audio4to7);
                    p4p7_2 = MediaPlayer.create(context, R.raw.audio4to7);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
            case "4-8":
                if (p4p8_1 == null || p4p8_2 == null) {
                    p4p8_1 = MediaPlayer.create(context, R.raw.audio4to8);
                    p4p8_2 = MediaPlayer.create(context, R.raw.audio4to8);
                    AudioController.setVolumn(pnp, volumn);
                }
                break;
        }
    }
}
