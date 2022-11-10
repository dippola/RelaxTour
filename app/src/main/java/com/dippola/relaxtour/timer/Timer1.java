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
    public static Button wheelstart, cancel;
    private String[] numpickhour;
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
        wheelstart = rootView.findViewById(R.id.wheel_start1);
        cancel = rootView.findViewById(R.id.wheel_cancel);

        numpickhour = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
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

        wheelstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimerService.class);
                String max1 = Timer2.hourintent.getText().toString();
                String max2 = Timer2.minintent.getText().toString();
                int max11 = Integer.parseInt(max1);
                int max22 = Integer.parseInt(max2);
                if (max11 == 0 && max22 != 0) {
                    int max1122 = max22;
                    String maxintent = Integer.toString(max1122);
//                    Timer2.et_timer.setText(maxintent);
                    Timer2.et_timer = maxintent;
                    Log.d("Timer1>>>", "check et_timer: " + Timer2.et_timer);
//                    fn_countdown();
                    if (Build.VERSION.SDK_INT >= 26) {
                        getActivity().startForegroundService(intent);
                    } else {
                        getActivity().startService(intent);
                    }
                    Log.i(">>>countdown", "start");
                    TimerDialog.viewPager.setCurrentItem(1);
                }
                if (max11 != 0 && max22 == 0) {
                    int max1122 = max11;
                    String maxintent = Integer.toString(max1122);
//                    Timer2.et_timer.setText(maxintent);
                    Timer2.et_timer = maxintent;
//                    fn_countdown();
                    if (Build.VERSION.SDK_INT >= 26) {
                        getActivity().startForegroundService(intent);
                    } else {
                        getActivity().startService(intent);
                    }
                    Log.i(">>>countdown", "start");
                    TimerDialog.viewPager.setCurrentItem(1);
                }
                if (max11 == 0 && max22 == 0) {
                    Toast.makeText(getActivity(), "please choice time", Toast.LENGTH_SHORT).show();
                }
                if (max11 != 0 && max22 != 0) {
                    int max1122 = max11 + max22;
                    String maxintent = Integer.toString(max1122);
//                    Timer2.et_timer.setText(maxintent);
//                    fn_countdown();
                    Timer2.et_timer = maxintent;
                    if (Build.VERSION.SDK_INT >= 26) {
                        getActivity().startForegroundService(intent);
                    } else {
                        getActivity().startService(intent);
                    }
                    Log.i(">>>countdown", "start");
                    TimerDialog.viewPager.setCurrentItem(1);
                }
            }
        });

        wheelViewhour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                //i = oldVal, i1 = newVal
                int hour = i1 * 3600;
                String hourintent1 = Integer.toString(hour);
                Timer2.hourintent.setText(hourintent1);
            }
        });

        wheelViewmin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int mint = i1;
                if (i1 == 0) {
                    Timer2.minintent.setText("0");
                } else if (i1 == 1) {
                    Timer2.minintent.setText("5");
//                    Timer2.minintent.setText("300");
                } else if (i1 == 2) {
                    Timer2.minintent.setText("600");
                } else if (i1 == 3) {
                    Timer2.minintent.setText("900");
                } else if (i1 == 4) {
                    Timer2.minintent.setText("1200");
                } else if (i1 == 5) {
                    Timer2.minintent.setText("1500");
                } else if (i1 == 6) {
                    Timer2.minintent.setText("1800");
                } else if (i1 == 7) {
                    Timer2.minintent.setText("2100");
                } else if (i1 == 8) {
                    Timer2.minintent.setText("2400");
                } else if (i1 == 9) {
                    Timer2.minintent.setText("2700");
                } else if (i1 == 10) {
                    Timer2.minintent.setText("3000");
                } else if (i1 == 11) {
                    Timer2.minintent.setText("3300");
                }
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
