package com.dippola.relaxtour.timer;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.service.TimerService;

import java.util.ArrayList;

public class TimerDialog extends AppCompatActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_dialog);

        final Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;

        //set dialog size
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
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
