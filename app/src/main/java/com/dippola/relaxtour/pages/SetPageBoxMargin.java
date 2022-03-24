package com.dippola.relaxtour.pages;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dippola.relaxtour.R;

public class SetPageBoxMargin {

    public static void setPageBoxMargin(Activity activity, RelativeLayout pageBox) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mg = (int)(size.x * 0.07);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pageBox.getLayoutParams();
        layoutParams.setMargins(mg, mg, mg, mg);
        pageBox.setLayoutParams(layoutParams);
    }

}
