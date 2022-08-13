package com.dippola.relaxtour.pages;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.dippola.relaxtour.dialog.InfoDialog;
import com.dippola.relaxtour.pages.adapter.PageAdapter;
import com.dippola.relaxtour.pages.adapter.StoragePageAdapter;
import com.dippola.relaxtour.pages.item.PageItem;
import com.dippola.relaxtour.pages.item.ViewTypeCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChakraPage extends Fragment {

    RelativeLayout pageBox;
    public static ArrayList<PageItem> arrayList = new ArrayList<>();
    public static PageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ImageView info;

    public static RelativeLayout load;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.page, container, false);

        setInit(rootView);
        setRecyclerView();

        return rootView;
    }

    private void setInit(ViewGroup rootView) {
        pageBox = rootView.findViewById(R.id.page_box);
        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);
        recyclerView = rootView.findViewById(R.id.page_recyclerview);
        info = rootView.findViewById(R.id.page_info);
        info.setVisibility(View.VISIBLE);
        setOnClickInfo();
    }

    private void setRecyclerView() {
        arrayList = MainActivity.databaseHandler.getPageList(5);
        adapter = new PageAdapter(arrayList, getActivity(), ViewTypeCode.ViewType.PAGE567);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        params.topMargin = 40;
        recyclerView.setLayoutParams(params);
    }

    private void setOnClickInfo() {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InfoDialog.class));
            }
        });
    }
}
