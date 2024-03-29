package com.dippola.relaxtour.pages;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.dippola.relaxtour.pages.item.ViewTypeCode;

import java.util.ArrayList;

public class RainPage extends Fragment {

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

        setInit(rootView);
        setRecyclerView();

        return rootView;
    }

    private void setInit(ViewGroup rootView) {
        pageBox = rootView.findViewById(R.id.page_box);
        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);
        recyclerView = rootView.findViewById(R.id.page_recyclerview);
        hint = rootView.findViewById(R.id.page_scroll_hint);
    }

    private void setRecyclerView() {
        arrayList = MainActivity.databaseHandler.getPageList(1);
        adapter = new PageAdapter(arrayList, getActivity(), ViewTypeCode.ViewType.PAGE123);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        hint.setVisibility(View.VISIBLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
////                    if (!recyclerView.canScrollVertically(-1)) {
////                        Log.d("RainPage>>>", "최상단");
////                    } else if (!recyclerView.canScrollVertically(1)) {
////                        Log.d("RainPage>>>", "하단");
////                    }
//                    if (!recyclerView.canScrollVertically(1)) {
//                        hint.setVisibility(View.GONE);
//                    } else {
//                        hint.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//        }
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
}
