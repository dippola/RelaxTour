package com.dippola.relaxtour.community.main.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dippola.relaxtour.R;
import com.jsibbold.zoomage.ZoomageView;

public class DetailImageViewerItem extends Fragment {

    private ZoomageView zoomageView;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.detail_image_viewer_item, container, false);

        zoomageView = rootView.findViewById(R.id.detail_image_viewer_item_zoomage);

        Glide.with(zoomageView.getContext()).load(CommunityMainDetail.imageList.get(getArguments().getInt("position"))).into(zoomageView);

        return rootView;
    }
}
