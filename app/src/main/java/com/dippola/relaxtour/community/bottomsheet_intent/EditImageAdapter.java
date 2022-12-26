package com.dippola.relaxtour.community.bottomsheet_intent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.dippola.relaxtour.R;

import java.util.List;

public class EditImageAdapter extends RecyclerView.Adapter<EditImageAdapter.CustomViewHolder>{
    List<UriAndFromModel> urllist;
    Context context;
    ConstraintLayout.LayoutParams params;
    public EditImageAdapter(List<UriAndFromModel> urllist, Context context, ConstraintLayout.LayoutParams params) {
        this.urllist = urllist;
        this.context = context;
        this.params = params;
    }
    @NonNull
    @Override
    public EditImageAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_write_recyclerview_item, parent, false);
        EditImageAdapter.CustomViewHolder holder = new EditImageAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditImageAdapter.CustomViewHolder holder, int position) {
        int i = position;
        holder.img.setLayoutParams(params);
        if (urllist.get(i) == null) {
            holder.box.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(urllist.get(i).getUrl().toString()).transform(new CenterCrop(), new RoundedCorners(40)).into(holder.img);
            holder.box.setVisibility(View.VISIBLE);
        }
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPost.deleteUrlList.add(urllist.get(i).getUrl());
                int index = urllist.indexOf(urllist.get(i));
                urllist.remove(index);
                notifyItemRemoved(index);
                notifyDataSetChanged();
                EditPost.imagecount.setText(urllist.size() + "/5");
            }
        });
    }

    @Override
    public int getItemCount() {
        return urllist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout box;
        ImageView img;
        Button cancel;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            box = itemView.findViewById(R.id.community_write_recyclerview_item_box);
            img = itemView.findViewById(R.id.community_write_recyclerview_item_img);
            cancel = itemView.findViewById(R.id.community_write_recyclerview_item_cancel);
        }
    }
}
