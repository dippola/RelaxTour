package com.dippola.relaxtour.community.main.notification;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomViewHolder> {

    Context context;
    List<NotificationItem> list;
    DatabaseHandler databaseHandler;
    RecyclerView recyclerView;
    ConstraintLayout zero;
    RequestOptions requestOptions;

    public NotificationAdapter(Context context, List<NotificationItem> list, DatabaseHandler databaseHandler, RecyclerView recyclerView, ConstraintLayout zero, RequestOptions requestOptions) {
        this.context = context;
        this.list = list;
        this.databaseHandler = databaseHandler;
        this.recyclerView = recyclerView;
        this.zero = zero;
        this.requestOptions = requestOptions;
    }

    @NonNull
    @Override
    public NotificationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.CustomViewHolder holder, int position) {
        int i = position;

        String[] s = list.get(i).title.split("●");
        Log.d("NotificationAdapter>>>", "check1: " + s[0]);
        if (s[0].equals("comment")) {
            holder.title.setText(s[4]);
            holder.nickname.setText(s[3]);
            if (s[2].equals("")) {
                Glide.with(holder.img.getContext()).load(R.drawable.nullpic).apply(requestOptions).into(holder.img);
            } else {
                Glide.with(holder.img.getContext()).load(s[2]).apply(requestOptions).into(holder.img);
            }
        } else if (s[0].equals("update")) {
            holder.title.setText("A new version has been released.");
            holder.img.setVisibility(View.GONE);
            holder.nickname.setVisibility(View.GONE);
        } else if (s[0].equals("admin")) {
            ConstraintSet set = new ConstraintSet();
            set.clone(holder.box);
            set.connect(holder.date.getId(), ConstraintSet.TOP, holder.body.getId(), ConstraintSet.BOTTOM);
            set.applyTo(holder.box);
            holder.title.setText(s[4]);
            holder.nickname.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
            holder.body.setMaxLines(50);
        } else if (s[0].equals("admin_profile")) {
            ConstraintSet set = new ConstraintSet();
            set.clone(holder.box);
            set.connect(holder.date.getId(), ConstraintSet.TOP, holder.body.getId(), ConstraintSet.BOTTOM);
            set.applyTo(holder.box);
            holder.title.setText(s[3]);
            holder.nickname.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
            holder.body.setMaxLines(50);
        }

        if (s[0].equals("admin")) {
            holder.body.setText(list.get(i).getBody());
        }
        holder.body.setText(list.get(i).getBody());
        holder.date.setText(resultDate(list.get(i).getDate()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteCNotification(list.get(i).getBody(), list.get(i).getDate());
                list.remove(i);
                notifyItemRemoved(i);
                if (list.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    zero.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s[0].equals("comment")) {
                    Intent intent = new Intent(context, CommunityMainDetail.class);
                    intent.putExtra("parent_id", Integer.parseInt(s[1]));
                    context.startActivity(intent);
                } else if (s[0].equals("update")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("market://details?id=com.dippola.relaxtour"));
                    context.startActivity(intent);
                } else if (s[0].equals("admin")) {
                    Intent intent = new Intent(context, AdminNotificationDialog.class);
                    intent.putExtra("body", list.get(i).getBody());
                    context.startActivity(intent);
                } else if (s[0].equals("admin_profile")) {
                    Intent intent = new Intent(context, AdminNotificationDialog.class);
                    intent.putExtra("body", list.get(i).getBody());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout box;
        TextView title, body, date, nickname;
        Button delete;
        ImageView img;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.box = itemView.findViewById(R.id.notificatoin_item_box);
            this.title = itemView.findViewById(R.id.notification_item_title);
            this.body = itemView.findViewById(R.id.notification_item_body);
            this.date = itemView.findViewById(R.id.notification_item_date);
            this.delete = itemView.findViewById(R.id.notification_item_delete);
            this.nickname = itemView.findViewById(R.id.notification_item_nickname);
            this.img = itemView.findViewById(R.id.notification_item_img);
        }
    }

    private String resultDate(String d1) {
        if (d1.split(" ")[0].equals(getTime().split(" ")[0])) {
            return d1.split(" ")[1].split(":")[0] + ":" + d1.split(" ")[1].split(":")[1];
        } else {
            return d1.split(" ")[0];
        }
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = format.format(mDate);
        return date;
    }
}
