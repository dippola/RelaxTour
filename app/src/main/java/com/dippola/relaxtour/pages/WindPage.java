package com.dippola.relaxtour.pages;

import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.ArrayList;

public class WindPage extends Fragment {

    public static MediaPlayer p3p1_1, p3p1_2;
    public static MediaPlayer p3p2_1, p3p2_2;
    public static MediaPlayer p3p3_1, p3p3_2;
    public static MediaPlayer p3p4_1, p3p4_2;
    public static MediaPlayer p3p5_1, p3p5_2;
    public static MediaPlayer p3p6_1, p3p6_2;
    public static MediaPlayer p3p7_1, p3p7_2;

    RelativeLayout pageBox;
    public static ArrayList<PageItem> arrayList = new ArrayList<>();
    public static PageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

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
        p3p1_1 = MediaPlayer.create(getActivity(), R.raw.audio3to1);
        p3p1_2 = MediaPlayer.create(getActivity(), R.raw.audio3to1);
        p3p2_1 = MediaPlayer.create(getActivity(), R.raw.audio3to2);
        p3p2_2 = MediaPlayer.create(getActivity(), R.raw.audio3to2);
        p3p3_1 = MediaPlayer.create(getActivity(), R.raw.audio3to3);
        p3p3_2 = MediaPlayer.create(getActivity(), R.raw.audio3to3);
        p3p4_1 = MediaPlayer.create(getActivity(), R.raw.audio3to4);
        p3p4_2 = MediaPlayer.create(getActivity(), R.raw.audio3to4);
        p3p5_1 = MediaPlayer.create(getActivity(), R.raw.audio3to5);
        p3p5_2 = MediaPlayer.create(getActivity(), R.raw.audio3to5);
        p3p6_1 = MediaPlayer.create(getActivity(), R.raw.audio3to6);
        p3p6_2 = MediaPlayer.create(getActivity(), R.raw.audio3to6);
        p3p7_1 = MediaPlayer.create(getActivity(), R.raw.audio3to7);
        p3p7_2 = MediaPlayer.create(getActivity(), R.raw.audio3to7);
    }

    private void setInit(ViewGroup rootView) {
        pageBox = rootView.findViewById(R.id.page_box);
        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);
        recyclerView = rootView.findViewById(R.id.page_recyclerview);
    }

    private void setRecyclerView() {
        arrayList = MainActivity.databaseHandler.getWindList();
        adapter = new PageAdapter(arrayList, getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setPage3Volumn();
    }

    private void setPage3Volumn() {
        AudioController.setVolumn("3-1", arrayList.get(0).getSeek());
        AudioController.setVolumn("3-2", arrayList.get(1).getSeek());
        AudioController.setVolumn("3-3", arrayList.get(2).getSeek());
        AudioController.setVolumn("3-4", arrayList.get(3).getSeek());
        AudioController.setVolumn("3-5", arrayList.get(4).getSeek());
        AudioController.setVolumn("3-6", arrayList.get(5).getSeek());
        AudioController.setVolumn("3-7", arrayList.get(6).getSeek());
    }
}
