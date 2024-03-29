package com.dippola.relaxtour.timer;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.notification.DefaultNotification;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.service.TimerService;

public class Timer2 extends Fragment {
    public static int myProgress = 0;
    public static ProgressBar progressBarView;
    public static Button btn_stop, btn_close;
    public static TextView tv_time;
    public static int progress;
    public static int endTime;

    public Timer2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.timer2, container, false);

        progressBarView = (ProgressBar) rootView.findViewById(R.id.progress_count1);
        btn_stop = (Button) rootView.findViewById(R.id.countstart1);
        btn_close = rootView.findViewById(R.id.count_go_back);
        tv_time = (TextView) rootView.findViewById(R.id.text_timer1);

//        if (TimerService.isCount) {
//            tv_time.setText(TimerService.setNewTime);
//        }

        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TimerService.isCount = false;
//                getActivity().stopService(new Intent(getActivity(), TimerService.class));
//                Timer1.mTimerRunning = false;
//                DefaultNotification.defauleNotification(getActivity());
//                NotificationService.closeNotification(getActivity());
//
//                //stop audio
//                if (NotificationService.isPlaying) {
//                    getActivity().stopService(new Intent(getActivity(), NotificationService.class));
//                }
                stopTimer(getActivity());
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

    public static void stopTimer(Context context) {
        TimerService.isCount = false;
        context.stopService(new Intent(context, TimerService.class));
        Timer1.mTimerRunning = false;
//        DefaultNotification.defauleNotification(context);
//        NotificationService.closeNotification(context);

        //stop audio
//        if (NotificationService.isPlaying) {
//            context.stopService(new Intent(context, NotificationService.class));
//        }
    }
}
