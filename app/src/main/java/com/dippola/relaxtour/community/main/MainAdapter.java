package com.dippola.relaxtour.community.main;

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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.retrofit.model.MainModelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    List<MainModelView> arrayList;
    Context context;

    public MainAdapter(Context context, List<MainModelView> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_main_item, parent, false);
        MainAdapter.MainViewHolder holder = new MainAdapter.MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, int position) {
        int i = position;
        if (arrayList.get(i).getUser_image() == null || arrayList.get(i).getUser_image().length() == 0) {
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.nullpic)).transform(new CircleCrop()).into(holder.userImage);
        } else {
            Glide.with(context).load(arrayList.get(i).getUser_image()).transform(new CircleCrop()).into(holder.userImage);
        }
        holder.title.setText(arrayList.get(i).getTitle());
        holder.view.setText(String.valueOf(arrayList.get(i).getView()));
        holder.like.setText(String.valueOf(arrayList.get(i).getLike()));
        holder.date.setText(getDateResult(arrayList.get(i).getDate()));
        holder.commentcount.setText(String.valueOf(arrayList.get(i).getCommentcount()));
        holder.nickname.setText(String.valueOf(arrayList.get(i).getNickname()));


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommunityMainDetail.class);
                intent.putExtra("parent_id", arrayList.get(i).getParent_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView title, date, view, nickname, like, commentcount;
        ConstraintLayout item;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.community_main_item_userimage);
            this.title = itemView.findViewById(R.id.community_main_item_title);
            this.date = itemView.findViewById(R.id.community_main_item_date);
            this.view = itemView.findViewById(R.id.community_main_item_view);
            this.nickname = itemView.findViewById(R.id.community_main_item_nickname);
            this.like = itemView.findViewById(R.id.community_main_item_like);
            this.commentcount = itemView.findViewById(R.id.community_main_item_comment_count);
            this.item = itemView.findViewById(R.id.community_main_item);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String date = format.format(mDate);
        return date;
    }
}
