package com.dippola.relaxtour.pages;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.pages.adapter.PageAdapter;
import com.dippola.relaxtour.pages.adapter.StoragePageAdapter;
import com.dippola.relaxtour.pages.item.PageItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChakraPage extends Fragment {

    RelativeLayout pageBox;
    public static ArrayList<PageItem> arrayList = new ArrayList<>();
    public static PageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    public static MediaPlayer p5p1_1, p5p1_2;
    public static MediaPlayer p5p2_1, p5p2_2;
    public static MediaPlayer p5p3_1, p5p3_2;
    public static MediaPlayer p5p4_1, p5p4_2;
    public static MediaPlayer p5p5_1, p5p5_2;
    public static MediaPlayer p5p6_1, p5p6_2;
    public static MediaPlayer p5p7_1, p5p7_2;
    public static RelativeLayout load;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page, container, false);

        setAudio(getActivity());
        setInit(rootView);
        setRecyclerView();

        return rootView;
    }

    private void setInit(ViewGroup rootView) {
        pageBox = rootView.findViewById(R.id.page_box);
        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);
        recyclerView = rootView.findViewById(R.id.page_recyclerview);
    }

    private void setRecyclerView() {
        arrayList = MainActivity.databaseHandler.getChakraList();
        adapter = new PageAdapter(arrayList, getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        setChakraVolumn();
    }

    public static void setAudio(Context context) {
        p5p1_1 = new MediaPlayer();
        p5p1_2 = new MediaPlayer();
        p5p2_1 = new MediaPlayer();
        p5p2_2 = new MediaPlayer();
        p5p3_1 = new MediaPlayer();
        p5p3_2 = new MediaPlayer();
        p5p4_1 = new MediaPlayer();
        p5p4_2 = new MediaPlayer();
        p5p5_1 = new MediaPlayer();
        p5p5_2 = new MediaPlayer();
        p5p6_1 = new MediaPlayer();
        p5p6_2 = new MediaPlayer();
        p5p7_1 = new MediaPlayer();
        p5p7_2 = new MediaPlayer();
//        p5p1_1 = MediaPlayer.create(getActivity(), R.raw.audio5to1);
//        p5p1_2 = MediaPlayer.create(getActivity(), R.raw.audio5to1);
        String path3_2 = context.getApplicationInfo().dataDir + "/cache/audio3-2.mp3";
        String path3_3 = context.getApplicationInfo().dataDir + "/cache/audio3-3.mp3";
//        setDataSourceAudio(p3p1, path3_1);
//        setDataSourceAudio(p3p2, path3_2);

//        if (path3_3.isEmpty()) {
//            Log.d("ChakraPage>>>", "null");
//        } else {
//            Log.d("ChakraPage>>>", "have");
//        }
    }

    private static void setDataSourceAudio(MediaPlayer mp, String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                mp.setDataSource(path);
                mp.setLooping(true);
                mp.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setChakraVolumn() {
//        AudioController.setVolumn("5-1", arrayList.get(0).getSeek());
//        AudioController.setVolumn("5-2", arrayList.get(1).getSeek());
    }
}
