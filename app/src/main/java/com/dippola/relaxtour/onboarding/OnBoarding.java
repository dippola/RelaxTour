package com.dippola.relaxtour.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;

public class OnBoarding extends AppCompatActivity {

    private OnBoardingAdapter adapter;
    private ViewPager2 viewPager;
    private ArrayList<OnBoardingItem> arrayList = new ArrayList<>();
    private SpringDotsIndicator dot;
    private Button btn;

    private boolean fromSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        fromSplash = getIntent().getBooleanExtra("fromSplash", false);

        setOnBoardingItem();

        viewPager = findViewById(R.id.onboarding_viewpager);
        dot = findViewById(R.id.onboarding_dot);
        btn = findViewById(R.id.onboarding_button);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        dot.attachTo(viewPager);

        setViewPagerListener();
        setOnClickButton();
    }

    private void setOnBoardingItem() {
        ArrayList<OnBoardingItem> lists = new ArrayList<>();

        OnBoardingItem item1 = new OnBoardingItem();
        item1.setImg(R.drawable.premium_title_img);
        item1.setText1("first page title1");
        item1.setText2("first page small text1");

        OnBoardingItem item2 = new OnBoardingItem();
        item2.setImg(R.drawable.premium_title_img);
        item2.setText1("first page title2");
        item2.setText2("first page small text2");

        OnBoardingItem item3 = new OnBoardingItem();
        item3.setImg(R.drawable.premium_title_img);
        item3.setText1("first page title3");
        item3.setText2("first page small text3");

        lists.add(item1);
        lists.add(item2);
        lists.add(item3);

        adapter = new OnBoardingAdapter(lists);
    }

    private void setViewPagerListener() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position != 2) {
                    btn.setText("next");
                } else {
                    btn.setText("get started");
                }
            }
        });
    }

    private void setOnClickButton() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int page = viewPager.getCurrentItem();
                if (page != 2) {
                    viewPager.setCurrentItem(page + 1);
                    if (page + 1 == 2) {
                        btn.setText("get started");
                    }
                } else {
                    closeOnBoarding();
                }
            }
        });
    }

    private void closeOnBoarding() {
        if (fromSplash) {
            startActivity(new Intent(OnBoarding.this, MainActivity.class));
            finish();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
