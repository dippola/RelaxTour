package com.dippola.relaxtour.community.admin.detail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.ImageViewer;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;

import java.util.List;

public class AdminDetailCommentAdapter extends RecyclerView.Adapter<AdminDetailCommentAdapter.CustomViewHolder> {
    Context context;
    List<PostCommentModel> list;
    int commentid;

    public AdminDetailCommentAdapter(Context context, List<PostCommentModel> list, int commentid) {
        this.context = context;
        this.list = list;
        this.commentid = commentid;
    }
    @NonNull
    @Override
    public AdminDetailCommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_detail_comment_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDetailCommentAdapter.CustomViewHolder holder, int position) {
        if (list.get(position).getUser_url() == null || list.get(position).getUser_url().equals("")) {
            Glide.with(holder.userimg.getContext()).load(R.drawable.nullpic).into(holder.userimg);
        } else {
            Glide.with(holder.userimg.getContext()).load(list.get(position).getUser_url()).into(holder.userimg);
            holder.userimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageViewer.class);
                    intent.putExtra("url", list.get(position).getUser_url());
                    context.startActivity(intent);
                }
            });
        }
        holder.usernickname.setText(list.get(position).getNickname());
        if (list.get(position).getTo_nickname() == null || list.get(position).getTo_nickname().equals("")) {
            holder.towho.setVisibility(View.GONE);
        } else {
            holder.towho.setText(list.get(position).getTo_nickname());
        }
        holder.body.setText(list.get(position).getBody());
        holder.date.setText(list.get(position).getDate());

        if (commentid != -1) {
            if (list.get(position).getId() == commentid) {
                holder.box.setBackgroundResource(R.color.dialog_layout_line_color);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView userimg;
        TextView usernickname, towho, body, date;
        ConstraintLayout box;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userimg = itemView.findViewById(R.id.admin_detail_comment_item_img);
            this.usernickname = itemView.findViewById(R.id.admin_detail_comment_item_nickname);
            this.towho = itemView.findViewById(R.id.admin_detail_comment_item_towho);
            this.body = itemView.findViewById(R.id.admin_detail_comment_item_body);
            this.date = itemView.findViewById(R.id.admin_detail_comment_ietm_date);
            this.box = itemView.findViewById(R.id.admin_detail_comment_item_box);
        }
    }
}
