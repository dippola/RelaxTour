package com.dippola.relaxtour.community.main.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;

import java.util.ArrayList;

public class DetailImageViewer extends AppCompatActivity {

    private ViewPager viewPager;
    private int imagesCount;
    private int clickPosition;
    private SectionsPagerAdapter sectionsPagerAdapter;

    private RelativeLayout cancel;
    private TextView count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_image_viewer);
        imagesCount = getIntent().getIntExtra("imagecount", 0);
        clickPosition = getIntent().getIntExtra("clickposition", 0);
        setInit();
        setViewPager(imagesCount);
    }

    private void setInit() {
        viewPager = findViewById(R.id.detail_image_viewer_viewpager);
        cancel = findViewById(R.id.detail_image_viewer_back);
        count = findViewById(R.id.detail_image_viewer_count_text);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setViewPager(int imagesCount) {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < imagesCount; i++) {
            DetailImageViewerItem detailImageViewerItem = new DetailImageViewerItem();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            detailImageViewerItem.setArguments(bundle);
            sectionsPagerAdapter.addItem(detailImageViewerItem);
        }
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(clickPosition - 1);

        count.setText(viewPager.getCurrentItem() + 1 + "/" + imagesCount);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                count.setText("" + (position+1) + "/" + imagesCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
