package com.dippola.relaxtour.timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.service.TimerService;

public class Timer1 extends Fragment {
    NumberPicker wheelViewhour, wheelViewmin;
    public static Button timerStart, cancel;
    private String[] numpickmin;

    SharedPreferences sptime;

    public static Boolean mTimerRunning;

    public Timer1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.timer1, container, false);

        wheelViewhour = rootView.findViewById(R.id.wheel_hour1);
        wheelViewmin = rootView.findViewById(R.id.wheel_min1);
        timerStart = rootView.findViewById(R.id.wheel_start1);
        cancel = rootView.findViewById(R.id.wheel_cancel);

        numpickmin = new String[]{"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

        wheelViewhour.setMinValue(0);
        wheelViewhour.setMaxValue(12);
        wheelViewhour.setValue(0);
        wheelViewhour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        wheelViewhour.setDisplayedValues(numpickhour);
        wheelViewmin.setMinValue(0);
        wheelViewmin.setMaxValue(11);
        wheelViewmin.setDisplayedValues(numpickmin);
        wheelViewmin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimerService.class);
                int max11 = TimerService.timerHour;
                int max22 = TimerService.timerMin;
                if (max11 == 0 && max22 == 0) {
                    Toast.makeText(getActivity(), "please choice time", Toast.LENGTH_SHORT).show();
                } else {
                    TimerService.et_timer = Integer.toString(max11+max22);
                    TimerDialog.viewPager.setCurrentItem(1);
                    if (Build.VERSION.SDK_INT >= 26) {
                        getActivity().startForegroundService(intent);
                    } else {
                        getActivity().startService(intent);
                    }
                }
            }
        });

        wheelViewhour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //i = oldVal, i1 = newVal
                int hour = i1 * 3600;
                TimerService.timerHour = hour;
            }
        });

        wheelViewmin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                TimerService.timerMin = i1 * 300;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }
}
