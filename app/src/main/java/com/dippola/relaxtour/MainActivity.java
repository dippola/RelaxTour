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
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dippola.relaxtour.board.BoardMain;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.AddFavDialog;
import com.dippola.relaxtour.dialog.AskDeleteAllPlaylistDialog;
import com.dippola.relaxtour.dialog.ThemeDialog;
import com.dippola.relaxtour.maintablayout.MainTabAdapter;
import com.dippola.relaxtour.maintablayout.MainTabItem;
import com.dippola.relaxtour.notification.DefaultNotification;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.FavPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
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
    public static int pageitem_width_size;
    public static int pageitem_height_size;
    public static int pageitem_4_width_size;
    public static int pageitem_4_height_size;
    AudioManager audioManager;
    public static int maxVolumn;

    //theme
    SharedPreferences sharedPreferences;

    //top button
    Button setting, timer, mode, board;
    public static TextView maincount;
    public static Button cancel;

    //page view pager
    public static ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;

    //tablayout
    RecyclerView tabRecycler;
    Button tabLeft, tabRight;

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

    public static RelativeLayout load;

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

        load = findViewById(R.id.activity_main_load);
        load.bringToFront();
        load.setVisibility(View.GONE);

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
        board = findViewById(R.id.activity_main_board);

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

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BoardMain.class));
            }
        });
    }

    private void setAudioManager() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolumn = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    private void setTabLayout() {
        tabRecycler = findViewById(R.id.activity_main_tablayout);
        tabLeft = findViewById(R.id.activity_main_tab_left);
        tabRight = findViewById(R.id.activity_main_tab_right);
        ArrayList<MainTabItem> tabsList = new ArrayList<>();
        tabsList.add(new MainTabItem(R.drawable.tabicon_fav_default, "fav", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_rain_default, "rain", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_water_default, "water", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_wind_default, "wind", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_nature_default, "nature", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_chakra_default, "chakra", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_mantra_default, "mantra", false));
        tabsList.add(new MainTabItem(R.drawable.tabicon_hz_default, "hz", false));

        if (viewPager.getCurrentItem() == 0) {
            tabsList.get(0).setImg(R.drawable.tabicon_fav);
            tabsList.get(0).setOpen(true);
        } else if (viewPager.getCurrentItem() == 1) {
            tabsList.get(1).setImg(R.drawable.tabicon_rain);
            tabsList.get(1).setOpen(true);
        } else if (viewPager.getCurrentItem() == 2) {
            tabsList.get(2).setImg(R.drawable.tabicon_water);
            tabsList.get(2).setOpen(true);
        } else if (viewPager.getCurrentItem() == 3) {
            tabsList.get(3).setImg(R.drawable.tabicon_wind);
            tabsList.get(3).setOpen(true);
        } else if (viewPager.getCurrentItem() == 4) {
            tabsList.get(4).setImg(R.drawable.tabicon_nature);
            tabsList.get(4).setOpen(true);
        } else if (viewPager.getCurrentItem() == 5) {
            tabsList.get(5).setImg(R.drawable.tabicon_chakra);
            tabsList.get(5).setOpen(true);
        } else if (viewPager.getCurrentItem() == 6) {
            tabsList.get(6).setImg(R.drawable.tabicon_mantra);
            tabsList.get(6).setOpen(true);
        } else if (viewPager.getCurrentItem() == 7) {
            tabsList.get(7).setImg(R.drawable.tabicon_hz);
            tabsList.get(7).setOpen(true);
        }

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        tabRecycler.setLayoutManager(layoutManager);
        MainTabAdapter mainTabAdapter = new MainTabAdapter(tabsList, MainActivity.this);
        mainTabAdapter.setHasStableIds(true);
        tabRecycler.setAdapter(mainTabAdapter);

        if (!tabRecycler.canScrollHorizontally(-1)) {
            tabLeft.setVisibility(View.INVISIBLE);
        } else if (!tabRecycler.canScrollHorizontally(1)) {
            tabRight.setVisibility(View.INVISIBLE);
        } else {
            tabLeft.setVisibility(View.VISIBLE);
            tabRight.setVisibility(View.VISIBLE);
        }

        tabRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!tabRecycler.canScrollHorizontally(-1)) {
                    tabLeft.setVisibility(View.INVISIBLE);
                } else if (!tabRecycler.canScrollHorizontally(1)) {
                    tabRight.setVisibility(View.INVISIBLE);
                } else {
                    tabLeft.setVisibility(View.VISIBLE);
                    tabRight.setVisibility(View.VISIBLE);
                }
            }
        });

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
                            tabsList.get(i).setImg(R.drawable.tabicon_fav_default);
                        } else if (i == 1) {
                            tabsList.get(i).setImg(R.drawable.tabicon_rain_default);
                        } else if (i == 2) {
                            tabsList.get(i).setImg(R.drawable.tabicon_water_default);
                        } else if (i == 3) {
                            tabsList.get(i).setImg(R.drawable.tabicon_wind_default);
                        } else if (i == 4) {
                            tabsList.get(i).setImg(R.drawable.tabicon_nature_default);
                        } else if (i == 5) {
                            tabsList.get(i).setImg(R.drawable.tabicon_chakra_default);
                        } else if (i == 6) {
                            tabsList.get(i).setImg(R.drawable.tabicon_mantra_default);
                        } else if (i == 7) {
                            tabsList.get(i).setImg(R.drawable.tabicon_hz_default);
                        }
                        mainTabAdapter.notifyItemChanged(i);
                    } else {//선택된 page
                        tabsList.get(position).setOpen(true);
                        if (position == 0) {
                            tabsList.get(position).setImg(R.drawable.tabicon_fav);
                        } else if (position == 1) {
                            tabsList.get(position).setImg(R.drawable.tabicon_rain);
                        } else if (position == 2) {
                            tabsList.get(position).setImg(R.drawable.tabicon_water);
                        } else if (position == 3) {
                            tabsList.get(position).setImg(R.drawable.tabicon_wind);
                        } else if (position == 4) {
                            tabsList.get(position).setImg(R.drawable.tabicon_nature);
                        } else if (position == 5) {
                            tabsList.get(position).setImg(R.drawable.tabicon_chakra);
                        } else if (position == 6) {
                            tabsList.get(position).setImg(R.drawable.tabicon_mantra);
                        } else if (position == 7) {
                            tabsList.get(position).setImg(R.drawable.tabicon_hz);
                        }
                        mainTabAdapter.notifyItemChanged(position);
                        tabRecycler.smoothScrollToPosition(position);
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
        WaterPage page2 = new WaterPage();
        sectionsPagerAdapter.addItem(page2);
        WindPage page3 = new WindPage();
        sectionsPagerAdapter.addItem(page3);
        NaturePage page4 = new NaturePage();
        sectionsPagerAdapter.addItem(page4);
        ChakraPage chakraPage = new ChakraPage();
        sectionsPagerAdapter.addItem(chakraPage);
        MantraPage mantraPage = new MantraPage();
        sectionsPagerAdapter.addItem(mantraPage);
        HzPage hzPage = new HzPage();
        sectionsPagerAdapter.addItem(hzPage);
        viewPager = findViewById(R.id.activity_main_viewpager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(6);
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
        int y = (int) (size.y * 0.5);
        pageitem_width_size = (int) (size.x * 0.37);
        pageitem_height_size = (int) (pageitem_width_size * 0.75);
        pageitem_4_width_size = (int) (size.x * 0.25);
        pageitem_4_height_size = (int) (pageitem_4_width_size * 0.75);

        bottomRecyclerView.setMinimumHeight(y);

        if (NotificationService.isPlaying) {
            pands.setBackgroundResource(R.drawable.bottom_pause);
        } else {
            pands.setBackgroundResource(R.drawable.bottom_sheet_play);
        }

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
                    if (AudioController.checkIsPlaying(bottomSheetPlayList.get(0), MainActivity.this)) {//재생중
                        pands.setBackgroundResource(R.drawable.bottom_sheet_play);
                        ArrayList<PageItem> pageItems = new ArrayList<>();
                        for (int i = 0; i < bottomSheetPlayList.size(); i++) {
                            pageItems.add(bottomSheetPlayList.get(i));
                            if (i == bottomSheetPlayList.size() - 1) {
                                AudioController.stopPlayingList(pageItems);
                                DefaultNotification.defauleNotification(MainActivity.this);
                            }
                        }
                    } else {//재생중 아님
                        pands.setBackgroundResource(R.drawable.bottom_pause);
                        List<PageItem> pageItems = new ArrayList<>();
                        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                            pageItems.add(bottomSheetPlayList.get(i));
                            MPList.initalMP(bottomSheetPlayList.get(i).getPnp(), MainActivity.this, bottomSheetPlayList.get(i).getSeek());
                            if (i == bottomSheetPlayList.size() - 1) {
                                //playinglist start
                                AudioController.startPlayingList(MainActivity.this, pageItems);
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
                    AddFavDialog.addTitleDialog(MainActivity.this);
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

        deletePlayingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskDeleteAllPlaylistDialog.askDeleteAllPlaylistDialog(MainActivity.this);
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

}