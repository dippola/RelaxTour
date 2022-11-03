package com.dippola.relaxtour.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;

public class OnBoarding extends AppCompatActivity {

    private OnBoardingAdapter adapter;
    private ViewPager2 viewPager;
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
        item1.setImg(R.drawable.onboarding_1);
        item1.setText1("Play sound");
        item1.setText2("Choose a variety of sounds. You can add sound to the playlist with the combination you want.\nThe sounds of nature, Chakra, Mantra\nEnjoy the perfect meditation sound with a combination of different sounds.");

        OnBoardingItem item2 = new OnBoardingItem();
        item2.setImg(R.drawable.onboarding_2);
        item2.setText1("Add bookmark");
        item2.setText2("Add the combination of sounds you often hear to bookmark. You can play your play list with a single click on the bookmark.");

        OnBoardingItem item3 = new OnBoardingItem();
        item3.setImg(R.drawable.onboarding_3);
        item3.setText1("Community");
        item3.setText2("There is a community where you can share the experiences and information of meditators with other users. Share your meditation story and your play list.");

        lists.add(item1);
        lists.add(item2);
        lists.add(item3);

        adapter = new OnBoardingAdapter(lists, OnBoarding.this, OnBoarding.this);
    }

    private void setViewPagerListener() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position != 2) {
                    btn.setText("Next");
                } else {
                    btn.setText("Get started");
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
                        btn.setText("Get started");
                    }
                } else {
                    btn.setEnabled(false);
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
