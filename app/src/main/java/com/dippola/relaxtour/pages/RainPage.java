package com.dippola.relaxtour.pages;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class RainPage extends Fragment {

    public static MediaPlayer p1p1_1, p1p1_2;
    public static MediaPlayer p1p2_1, p1p2_2;
    public static MediaPlayer p1p3_1, p1p3_2;
    public static MediaPlayer p1p4_1, p1p4_2;
    public static MediaPlayer p1p5_1, p1p5_2;
    public static MediaPlayer p1p6_1, p1p6_2;

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
        p1p1_1 = MediaPlayer.create(getActivity(), R.raw.audio1to1);
        p1p1_2 = MediaPlayer.create(getActivity(), R.raw.audio1to1);
        p1p2_1 = MediaPlayer.create(getActivity(), R.raw.audio1to2);
        p1p2_2 = MediaPlayer.create(getActivity(), R.raw.audio1to2);
        p1p3_1 = MediaPlayer.create(getActivity(), R.raw.audio1to3);
        p1p3_2 = MediaPlayer.create(getActivity(), R.raw.audio1to3);
        p1p4_1 = MediaPlayer.create(getActivity(), R.raw.audio1to4);
        p1p4_2 = MediaPlayer.create(getActivity(), R.raw.audio1to4);
        p1p5_1 = MediaPlayer.create(getActivity(), R.raw.audio1to5);
        p1p5_2 = MediaPlayer.create(getActivity(), R.raw.audio1to5);
        p1p6_1 = MediaPlayer.create(getActivity(), R.raw.audio1to6);
        p1p6_2 = MediaPlayer.create(getActivity(), R.raw.audio1to6);
    }

    private void setInit(ViewGroup rootView) {
        pageBox = rootView.findViewById(R.id.page_box);
        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);
        recyclerView = rootView.findViewById(R.id.page_recyclerview);
    }

    private void setRecyclerView() {
        arrayList = MainActivity.databaseHandler.getRainList();
        adapter = new PageAdapter(arrayList, getActivity());
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setPage1Volumn();
    }

    private void setPage1Volumn() {
        AudioController.setVolumn("1-1", arrayList.get(0).getSeek());
        AudioController.setVolumn("1-2", arrayList.get(1).getSeek());
    }
}
