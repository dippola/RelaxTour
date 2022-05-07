package com.dippola.relaxtour.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.adapter.FavTitleAdapter;
import com.dippola.relaxtour.pages.item.FavTitleItem;

import java.util.ArrayList;

public class FavPage extends Fragment {

    RelativeLayout pageBox;
    public static RelativeLayout message;

    public static ArrayList<FavTitleItem> favTitleItemArrayList = new ArrayList<>();
    public static FavTitleAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fav_page, container, false);

        pageBox = rootView.findViewById(R.id.page_box);
//        SetPageBoxMargin.setPageBoxMargin(getActivity(), pageBox);

        setRecyclerView(rootView);
        setMessage(rootView);

        return rootView;
    }

    private void setRecyclerView(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.fav_page_recyclerview);
        favTitleItemArrayList = MainActivity.databaseHandler.getFavTitleList();
        adapter = new FavTitleAdapter(favTitleItemArrayList, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setMessage(ViewGroup rootView) {
        message = rootView.findViewById(R.id.fav_page_null_message);
        message.setVisibility(View.GONE);
        if (favTitleItemArrayList.size() == 0) {
            message.setVisibility(View.VISIBLE);
        }
    }
}
