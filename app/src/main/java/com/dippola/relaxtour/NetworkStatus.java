package com.dippola.relaxtour;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {
    public static final int WIFI = 1;
    public static final int MOBILE = 2;
    public static final int NO = 3;

    public static int getNetworkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                return MOBILE;
            }
        }
        return NO;
    }
}
