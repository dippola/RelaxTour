package com.dippola.relaxtour.timer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.service.TimerService;

import java.util.ArrayList;

public class TimerDialog extends AppCompatActivity {
    SectionsPagerAdapter sectionsPagerAdapter;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        final Dialog dialog = new Dialog(this);
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.7f;
//
//        //set dialog size
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);

//        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.timer_dialog, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
//        builder.setView(layout);
//        alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//
//        alertDialog.show();



        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        Timer1 timer1 = new Timer1();
        sectionsPagerAdapter.addItem(timer1);
        Timer2 timer2 = new Timer2();
        sectionsPagerAdapter.addItem(timer2);
        viewPager = (ViewPager) findViewById(R.id.timer_viewpager);
        viewPager.setAdapter(sectionsPagerAdapter);

        Log.d("TimerDialog>>>", "isCount: " + TimerService.isCount);
        if (TimerService.isCount) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    //    public static void TimerDialog(Activity activity, Context context) {
////        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.timer_dialog);
////
////        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////
////        final Dialog dialog = new Dialog(this);
////        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
////        layoutParams.copyFrom(dialog.getWindow().getAttributes());
////        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
////        layoutParams.dimAmount = 0.7f;
////
////        //set dialog size
////        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
////        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        dialog.getWindow().setAttributes(layoutParams);
//
//        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout layout = (LinearLayout) vi.inflate(R.layout.timer_dialog, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
//        builder.setView(layout);
//        alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//
//        alertDialog.show();
//
//
//
//        sectionsPagerAdapter = new SectionsPagerAdapter(((AppCompatActivity)activity).getSupportFragmentManager());
//        Timer1 timer1 = new Timer1();
//        sectionsPagerAdapter.addItem(timer1);
//        Timer2 timer2 = new Timer2();
//        sectionsPagerAdapter.addItem(timer2);
//        viewPager = (ViewPager) activity.findViewById(R.id.timer_viewpager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//
//        Log.d("TimerDialog>>>", "isCount: " + TimerService.isCount);
//        if (TimerService.isCount) {
//            viewPager.setCurrentItem(1);
//        } else {
//            viewPager.setCurrentItem(0);
//        }
//    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addItem(Fragment item) {
            items.add(item);
        }
        @Override
        public Fragment getItem(int i) {
            return items.get(i);
        }
        @Override
        public int getCount() {
            return items.size();
        }
    }
}
