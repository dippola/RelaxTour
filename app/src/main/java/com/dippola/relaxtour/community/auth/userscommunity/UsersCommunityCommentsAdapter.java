package com.dippola.relaxtour.community.auth.userscommunity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class UsersCommunityCommentsAdapter extends RecyclerView.Adapter<UsersCommunityCommentsAdapter.ViewHolder> {

    Context context;
    List<UsersCommunityCommentModel> list;

    public UsersCommunityCommentsAdapter(Context context, List<UsersCommunityCommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersCommunityCommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_community_comment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersCommunityCommentsAdapter.ViewHolder holder, int position) {
        int i = position;
        if (!String.valueOf(list.get(i).getTowho()).equals("")) {
            holder.towho.setText("@" + String.valueOf(list.get(i).getTowho()));
        }
        holder.body.setText(String.valueOf(list.get(i).getBody()));
        holder.date.setText(String.valueOf(list.get(i).getDate()).split("\\.")[0].split(":")[0] + ":" + String.valueOf(list.get(i).getDate()).split("\\.")[0].split(":")[1]);

        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommunityMainDetail.class);
                Log.d("CommentsAdapter>>>", "1: " + list.get(i).getParent_id());
                intent.putExtra("parent_id", list.get(i).getParent_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView towho, body, date;
        ConstraintLayout box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.towho = itemView.findViewById(R.id.users_community_comment_item_towho);
            this.body = itemView.findViewById(R.id.users_community_comment_item_body);
            this.date = itemView.findViewById(R.id.users_community_comment_item_date);
            this.box = itemView.findViewById(R.id.users_community_comment_item_body_box);
        }
    }

}
