package com.dippola.relaxtour.community.main.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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

    public NotificationAdapter(Context context, List<NotificationItem> list, DatabaseHandler databaseHandler, RecyclerView recyclerView, ConstraintLayout zero) {
        this.context = context;
        this.list = list;
        this.databaseHandler = databaseHandler;
        this.recyclerView = recyclerView;
        this.zero = zero;
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
        holder.title.setText(list.get(i).getTitle());
        holder.body.setText(list.get(i).getBody());
        holder.date.setText(list.get(i).getDate());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.deleteCNotification(list.get(i).getBody(), list.get(i).getDate(), list.get(i).getPostid());
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
                Intent intent = new Intent(context, CommunityMainDetail.class);
                intent.putExtra("parent_id", list.get(i).getPostid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout box;
        TextView title, body, date;
        Button delete;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.box = itemView.findViewById(R.id.notificatoin_item_box);
            this.title = itemView.findViewById(R.id.notification_item_title);
            this.body = itemView.findViewById(R.id.notification_item_body);
            this.date = itemView.findViewById(R.id.notification_item_date);
            this.delete = itemView.findViewById(R.id.notification_item_delete);
        }
    }

    private String resultDate(String d1) {
        if (d1.split(" ")[0].equals(getTime().split(" ")[0])) {
            return d1.split(" ")[0];
        } else {
            return d1;
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
