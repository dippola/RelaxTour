package com.dippola.relaxtour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.AddTitleDialog;
import com.dippola.relaxtour.dialog.ThemeDialog;
import com.dippola.relaxtour.maintablayout.MainTabAdapter;
import com.dippola.relaxtour.maintablayout.MainTabItem;
import com.dippola.relaxtour.notification.DefaultNotification;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.FavPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.PageItem;
import com.dippola.relaxtour.service.CheckOpenService;
import com.dippola.relaxtour.service.GetStateKillApp;
import com.dippola.relaxtour.service.TimerService;
import com.dippola.relaxtour.setting.SettingDialog;
import com.dippola.relaxtour.timer.TimerDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHandler databaseHandler;
    public static int pageitemsize;
    AudioManager audioManager;
    public static int maxVolumn;

    //theme
    SharedPreferences sharedPreferences;

    //top button
    Button setting, timer, mode;
    public static TextView maincount;
    public static Button cancel;

    //page view pager
    public static ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;

    //tablayout
    RecyclerView tabRecycler;

    //bottom sheet
    RelativeLayout bottomSheetTitleBar;
    BottomSheetBehavior bottomSheetBehavior;
    RelativeLayout include, pandsBackground;
    RecyclerView bottomRecyclerView;
    public static Button pands;
    Button upAndDown, deletePlayingList, addfav;
    public static ArrayList<PageItem> bottomSheetPlayList = new ArrayList<>();
    public static BottomSheetAdapter bottomSheetAdapter;
    RecyclerView.LayoutManager layoutManager;

    RelativeLayout bottomOutside;

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("modeTable", MODE_PRIVATE);
        String mode = sharedPreferences.getString("mode", "default");
        ThemeHelper.applyTheme(mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGetStateKillApp();

        bottomOutside = findViewById(R.id.activity_main_bottom_outside);
        bottomOutside.setVisibility(View.GONE);
        bottomOutside.bringToFront();
        bottomOutside.setClickable(false);

//        testButton();
        setAudioManager();
        setTopButton();
        setDatabaseHandler();
        setViewPager();
        setTabLayout();
        setBottomSheet();

//        sharedPreferences = getSharedPreferences("modeTable", MODE_PRIVATE);
//        String mode = sharedPreferences.getString("mode", "default");
//        ThemeHelper.applyTheme(mode);
//        ThemeDialog.themeDialog(MainActivity.this);
    }

//    private void testButton() {
//        Button testButton1 = findViewById(R.id.testButton1);
//        Button testButton2 = findViewById(R.id.testButton2);
//        Button testButton3 = findViewById(R.id.testButton3);
////        testButton1.setVisibility(View.GONE);
////        testButton2.setVisibility(View.GONE);
////        testButton3.setVisibility(View.GONE);
//
//        testButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ThemeHelper.applyTheme("light");
//            }
//        });
//
//        testButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ThemeHelper.applyTheme("dark");
//            }
//        });
//
//        testButton3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ThemeHelper.applyTheme("default");
//            }
//        });
//    }

    private void startGetStateKillApp() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.startForegroundService(new Intent(MainActivity.this, GetStateKillApp.class));
//        } else {
//            this.startService(new Intent(MainActivity.this, GetStateKillApp.class));
//        }
        this.startService(new Intent(MainActivity.this, GetStateKillApp.class));
    }

    private void setTopButton() {
        setting = findViewById(R.id.activity_main_setting);
        mode = findViewById(R.id.activity_main_mode);
        timer = findViewById(R.id.activity_main_timer);
        maincount = findViewById(R.id.activity_main_maincount);
        cancel = findViewById(R.id.activity_main_timer_cancel);

        if (TimerService.isCount) {
            cancel.setVisibility(View.VISIBLE);
            maincount.setVisibility(View.VISIBLE);
        } else {
            cancel.setVisibility(View.GONE);
            maincount.setVisibility(View.GONE);
        }

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TimerDialog.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingDialog.class));
            }
        });

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ThemeDialog.class));
                ThemeDialog.themeDialog(MainActivity.this);
            }
        });
    }

    private void setAudioManager() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolumn = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    private void setTabLayout() {
        tabRecycler = findViewById(R.id.activity_main_tablayout);
        ArrayList<MainTabItem> tabsList = new ArrayList<>();
        tabsList.add(new MainTabItem(R.drawable.fav_tab_default, "fav", false));
        tabsList.add(new MainTabItem(R.drawable.rain_tab, "rain", true));
        tabsList.add(new MainTabItem(R.drawable.wind_tab_default, "wind", false));
        tabsList.add(new MainTabItem(R.drawable.cakra_tab_default, "chakra", false));
        tabsList.add(new MainTabItem(R.drawable.hz_tab_default, "mandra", false));

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        tabRecycler.setLayoutManager(layoutManager);
        MainTabAdapter mainTabAdapter =new MainTabAdapter(tabsList, MainActivity.this);
        mainTabAdapter.setHasStableIds(true);
        tabRecycler.setAdapter(mainTabAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabsList.size(); i++) {
                    if (i != position) {//선택안된page
                        tabsList.get(i).setOpen(false);
                        if (i == 0) {
                            tabsList.get(i).setImg(R.drawable.fav_tab_default);
                        } else if (i == 1) {
                            tabsList.get(i).setImg(R.drawable.rain_tab_default);
                        } else if (i == 2) {
                            tabsList.get(i).setImg(R.drawable.wind_tab_default);
                        } else if (i == 3) {
                            tabsList.get(i).setImg(R.drawable.cakra_tab_default);
                        } else if (i == 4) {
                            tabsList.get(i).setImg(R.drawable.hz_tab_default);
                        }
                        mainTabAdapter.notifyItemChanged(i);
                    } else {//선택된 page
                        tabsList.get(position).setOpen(true);
                        if (position == 0) {
                            tabsList.get(position).setImg(R.drawable.fav_tab);
                        } else if (position == 1) {
                            tabsList.get(position).setImg(R.drawable.rain_tab);
                        } else if (position == 2) {
                            tabsList.get(position).setImg(R.drawable.wind_tab);
                        } else if (position == 3) {
                            tabsList.get(position).setImg(R.drawable.cakra_tab);
                        } else if (position == 4) {
                            tabsList.get(position).setImg(R.drawable.hz_tab);
                        }
                        mainTabAdapter.notifyItemChanged(position);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setViewPager() {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        FavPage favPage = new FavPage();
        sectionsPagerAdapter.addItem(favPage);
        RainPage page1 = new RainPage();
        sectionsPagerAdapter.addItem(page1);
        WindPage page2 = new WindPage();
        sectionsPagerAdapter.addItem(page2);
        ChakraPage chakraPage = new ChakraPage();
        sectionsPagerAdapter.addItem(chakraPage);
        HzPage hzPage = new HzPage();
        sectionsPagerAdapter.addItem(hzPage);
        viewPager = findViewById(R.id.activity_main_viewpager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(1);
    }

    private void setBottomSheet() {
        this.bottomSheetTitleBar = findViewById(R.id.bottom_sheet_top_bar);
        include = findViewById(R.id.activity_main_include_bottom_sheet);
        this.pands = findViewById(R.id.bottom_sheet_pands);
        bottomSheetBehavior = BottomSheetBehavior.from(include);
        pandsBackground = findViewById(R.id.bottom_sheet_pands_background);
        pandsBackground.bringToFront();
        bottomRecyclerView = findViewById(R.id.bottom_sheet_recyclerview);
        this.deletePlayingList = findViewById(R.id.bottom_delete_playing_list);
        this.upAndDown = findViewById(R.id.bottom_upanddown);
        this.addfav = findViewById(R.id.bottom_sheet_add_fav);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int y = (int)(size.y * 0.5);
        pageitemsize = (int)(size.x * 0.37);
        bottomRecyclerView.setMinimumHeight(y);

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomOutside.setVisibility(View.VISIBLE);
            upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_down);
        } else {
            bottomOutside.setVisibility(View.GONE);
            upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_up);
        }

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomOutside.setVisibility(View.VISIBLE);
                } else {
                    bottomOutside.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_up);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_down);
                }
            }
        });

        pands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetPlayList.size() == 0) {
                    Toast.makeText(MainActivity.this, "null play list", Toast.LENGTH_SHORT).show();
                } else {
                    CheckOpenService.checkOpenService(MainActivity.this);
                    if (AudioController.checkIsPlaying(bottomSheetPlayList.get(0).getPnp())) {//재생중
                        Log.d("MainActivity>>>", "1");
                        pands.setBackgroundResource(R.drawable.bottom_sheet_play);
                        ArrayList<PageItem> page = new ArrayList<>();
                        for (int i = 0; i < bottomSheetPlayList.size(); i++) {
                            page.add(bottomSheetPlayList.get(i));
                            if (i == bottomSheetPlayList.size() - 1) {
                                AudioController.stopPlayingList(page);
                                DefaultNotification.defauleNotification(MainActivity.this);
                            }
                        }
                    } else {//재생중 아님
                        Log.d("MainActivity>>>", "2");
                        pands.setBackgroundResource(R.drawable.bottom_pause);
                        List<String> pp = new ArrayList<>();
                        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                            pp.add(bottomSheetPlayList.get(i).getPnp());
                            if (i == bottomSheetPlayList.size() - 1) {
                                //playinglist start
                                AudioController.startPlayingList(MainActivity.this, pp);
                            }
                        }
                    }
                }
            }
        });

        upAndDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_up);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_down);
                }
            }
        });

        addfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetPlayList.size() != 0) {
                    AddTitleDialog.addTitleDialog(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "null playinglist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomSheetPlayList = databaseHandler.getBottomSheetPlayingList();
        bottomSheetAdapter = new BottomSheetAdapter(bottomSheetPlayList, MainActivity.this);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        bottomRecyclerView.setLayoutManager(layoutManager);
        bottomRecyclerView.setAdapter(bottomSheetAdapter);

        if (bottomSheetPlayList.size() == 0) {
            pands.setBackgroundResource(R.drawable.bottom_sheet_play_default);
        } else {
            pands.setBackgroundResource(R.drawable.bottom_sheet_play);
        }

        deletePlayingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NotificationService.isPlaying) {
                    Intent intent = new Intent(MainActivity.this, NotificationService.class);
                    stopService(intent);
                }
                if (bottomSheetPlayList.size() != 0) {
                    databaseHandler.deleteAllPlayingListTest();
                }
            }
        });

        bottomOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    upAndDown.setBackgroundResource(R.drawable.bottom_sheet_button_up);
                }
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

    private void setDatabaseHandler() {
        databaseHandler.setDB(MainActivity.this);
        databaseHandler = new DatabaseHandler(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("MainActivity>>>", "onKeyDown: ");
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }
}