package com.dippola.relaxtour.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.dippola.relaxtour.notification.NotificationService;

public class CheckOpenService {
    public static void checkOpenService(Context context) {
        if (!NotificationService.isPlaying) {
            Intent intent = new Intent(context, NotificationService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }
}
