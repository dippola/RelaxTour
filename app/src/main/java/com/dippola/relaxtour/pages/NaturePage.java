package com.dippola.relaxtour.pages;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.ArrayList;

public class NaturePage extends Fragment {

    public static MediaPlayer p4p1_1, p4p1_2;
    public static MediaPlayer p4p2_1, p4p2_2;
    public static MediaPlayer p4p3_1, p4p3_2;
    public static MediaPlayer p4p4_1, p4p4_2;
    public static MediaPlayer p4p5_1, p4p5_2;
    public static MediaPlayer p4p6_1, p4p6_2;
    public static MediaPlayer p4p7_1, p4p7_2;
    public static MediaPlayer p4p8_1, p4p8_2;

    RelativeLayout pageBox;
    public static ArrayList<PageItem> arrayList = new ArrayList<>();
    public static PageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    ImageView hint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page, container, false);

        setAudio();
        setInit(rootView);
        setRecyclerView();

        return rootView;
    }

    private void setAudio() {
        p4p1_1 = MediaPlayer.create(getActivity(), R.raw.audio4to1);
        p4p1_2 = MediaPlayer.create(getActivity(), R.raw.audio4to1);
        p4p2_1 = MediaPlayer.create(getActivity(), R.raw.audio4to2);
        p4p2_2 = MediaPlayer.create(getActivity(), R.raw.audio4to2);

        p4p3_1 = MediaPlayer.create(getActivity(), R.raw.audio4to3);
        p4p3_2 = MediaPlayer.create(getActivity(), R.raw.audio4to3);
        p4p4_1 = MediaPlayer.create(getActivity(), R.raw.audio4to4);
        p4p4_2 = MediaPlayer.create(getActivity(), R.raw.audio4to4);

        p4p5_1 = MediaPlayer.create(getActivity(), R.raw.audio4to5);
        p4p5_2 = MediaPlayer.create(getActivity(), R.raw.audio4to5);
        p4p6_1 = MediaPlayer.create(getActivity(), R.raw.audio4to6);
        p4p6_2 = MediaPlayer.create(getActivity(), R.raw.audio4to6);

        p4p7_1 = MediaPlayer.create(getActivity(), R.raw.audio4to7);
        p4p7_2 = MediaPlayer.create(getActivity(), R.raw.audio4to7);
        p4p8_1 = MediaPlayer.create(getActivity(), R.raw.audio4to8);
        p4p8_2 = MediaPlayer.create(getActivity(), R.raw.audio4to8);
    }

    private void setInit(ViewGroup rootView) {
        pageBox = rootView.findViewById(R.id.page_box);
        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);
        recyclerView = rootView.findViewById(R.id.page_recyclerview);
        hint = rootView.findViewById(R.id.page_scroll_hint);
    }

    private void setRecyclerView() {
        arrayList = MainActivity.databaseHandler.getNatureList();
        adapter = new PageAdapter(arrayList, getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setPage4Volumn();

        hint.setVisibility(View.VISIBLE);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    hint.setVisibility(View.GONE);
                } else {
                    hint.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setPage4Volumn() {
        AudioController.setVolumn("4-1", arrayList.get(0).getSeek());
        AudioController.setVolumn("4-2", arrayList.get(1).getSeek());
        AudioController.setVolumn("4-3", arrayList.get(2).getSeek());
        AudioController.setVolumn("4-4", arrayList.get(3).getSeek());
        AudioController.setVolumn("4-5", arrayList.get(4).getSeek());
        AudioController.setVolumn("4-6", arrayList.get(5).getSeek());
        AudioController.setVolumn("4-7", arrayList.get(6).getSeek());
        AudioController.setVolumn("4-8", arrayList.get(7).getSeek());
    }
}
