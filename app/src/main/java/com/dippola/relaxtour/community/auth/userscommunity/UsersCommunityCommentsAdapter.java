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
import java.util.Locale;
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
        holder.date.setText(getDateResult(list.get(i).getDate()));

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

    private String getDateResult(String dateFromServer) {
        //3번째 글 2022-11-24 21:06
        String nowTime = getTime();
        String postTime = changeTime(dateFromServer);
        if (nowTime.split(" ")[0].equals(postTime.split(" ")[0])) {
            return postTime.split(" ")[1].split(":")[0] + ":" + postTime.split(" ")[1].split(":")[1];
        } else {
            return postTime.split(" ")[0];
        }
    }

    private String changeTime(String dateFromServer) {
        String[] cut = dateFromServer.split(" ");
        String[] cut1 = cut[1].split("\\.");
        String result = cut[0] + " " + cut1[0];
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oldFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormat.parse(result);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newFormat.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormat.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dueDateAsNormal;
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = format.format(mDate);
        return date;
    }

}
