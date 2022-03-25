package com.dippola.relaxtour.timer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

public class TimerViewPager extends ViewPager {
    private boolean enabled;
    public TimerViewPager(@NonNull Context context) {
        super(context);
    }

    public TimerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (enabled) {
            return super.onInterceptTouchEvent(ev);
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
// ignore move action
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev);
                }
            }
            return false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (enabled) {
            return super.onTouchEvent(ev);
        } else {
            return MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
