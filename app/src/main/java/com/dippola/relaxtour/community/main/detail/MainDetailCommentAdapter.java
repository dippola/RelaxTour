package com.dippola.relaxtour.community.main.detail;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.ImageViewer;
import com.dippola.relaxtour.community.auth.CommunityAuth;
import com.dippola.relaxtour.community.main.MainAdapter;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.retrofit.model.MainCommentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainDetailCommentAdapter extends RecyclerView.Adapter<MainDetailCommentAdapter.CustomViewHolder> {
    List<MainCommentModel> list;
    Context context;

    public MainDetailCommentAdapter(List<MainCommentModel> list, Context context) {
        this.list = list;
        this.context = context;
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
        if (list.get(i).getUser_url().equals("")) {
            Glide.with(context).load(R.drawable.nullpic).transform(new CircleCrop()).into(holder.img);
        } else {
            Glide.with(context).load(list.get(i).getUser_url()).transform(new CircleCrop()).into(holder.img);
        }
        holder.date.setText(getDateResult(list.get(i).getDate()));
        holder.nickname.setText(list.get(i).getNickname());
        if (list.get(i).getTo_id() != 0) {
            holder.tonickname.setVisibility(View.VISIBLE);
            holder.tonickname.setText(String.valueOf("@" + list.get(i).getTo_nickname()));
        } else {
            holder.tonickname.setVisibility(View.GONE);
        }

        holder.nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!list.get(i).getNickname().equals(new DatabaseHandler(context).getUserModel().getNickname())) {
                    CommunityMainDetail.towho.setText(list.get(i).getNickname());
                    CommunityMainDetail.towhoid = list.get(i).getParent_user();
                    CommunityMainDetail.towho.setVisibility(View.VISIBLE);
                    CommunityMainDetail.editComment.requestFocus();
                    CommunityMainDetail.showKeyboard(view);
                }
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageViewer.class);
                if (list.get(i).getUser_url() != null) {
                    intent.putExtra("url", list.get(i).getUser_url());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView nickname, date, body, tonickname;
        Button more;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.community_main_detail_comment_item_img);
            this.nickname = itemView.findViewById(R.id.community_main_detail_comment_item_nickname);
            this.date = itemView.findViewById(R.id.community_main_detail_comment_item_date);
            this.body = itemView.findViewById(R.id.community_main_detail_comment_item_body);
            this.more = itemView.findViewById(R.id.community_main_detail_comment_item_more);
            this.tonickname = itemView.findViewById(R.id.community_main_detail_comment_item_tonickname);
        }
    }

    private String getDateResult(String dateFromServer) {
        //2022-12-02T14:19:41.556741Z
        String postTime = changeTime(dateFromServer);
        String date[] = postTime.split(" ");
        String time[] = date[1].split("\\:");
        return date[0] + " " + time[0] + ":" + time[1];
    }
    private String changeTime(String dateFromServer) {
        String[] cut = dateFromServer.split("T");
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
}
