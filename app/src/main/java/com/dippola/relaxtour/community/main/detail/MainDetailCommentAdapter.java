package com.dippola.relaxtour.community.main.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.MainAdapter;
import com.dippola.relaxtour.retrofit.model.MainCommentModel;

import java.util.List;

public class MainDetailCommentAdapter extends RecyclerView.Adapter<MainDetailCommentAdapter.CustomViewHolder> {
    List<MainCommentModel> list;

    public MainDetailCommentAdapter(List<MainCommentModel> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public MainDetailCommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_main_detail_comment_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainDetailCommentAdapter.CustomViewHolder holder, int position) {
        int i = position;
        holder.body.setText(list.get(i).getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView nickname, date, body;
        Button more;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.community_main_detail_comment_item_img);
            nickname = itemView.findViewById(R.id.community_main_detail_comment_item_nickname);
            date = itemView.findViewById(R.id.community_main_detail_comment_item_date);
            body = itemView.findViewById(R.id.community_main_detail_comment_item_body);
            more = itemView.findViewById(R.id.community_main_detail_comment_item_more);
        }
    }
}
