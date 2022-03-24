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

    public static MediaPlayer p2p1_1, p2p1_2;
    public static MediaPlayer p2p2_1, p2p2_2;

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
        p2p1_1 = MediaPlayer.create(getActivity(), R.raw.p2p1);
        p2p1_2 = MediaPlayer.create(getActivity(), R.raw.p2p1);
        p2p2_1 = MediaPlayer.create(getActivity(), R.raw.p2p2);
        p2p2_2 = MediaPlayer.create(getActivity(), R.raw.p2p2);
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
        setPage2Volumn();
    }

    private void setPage2Volumn() {
        AudioController.setVolumn("2-1", arrayList.get(0).getSeek());
        AudioController.setVolumn("2-2", arrayList.get(1).getSeek());
    }
}
