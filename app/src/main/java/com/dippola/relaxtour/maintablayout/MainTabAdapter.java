package com.dippola.relaxtour.maintablayout;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;

import java.util.ArrayList;

public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.CustomViewHolder> {
    ArrayList<MainTabItem> arrayList;
    Activity activity;

    public MainTabAdapter(ArrayList<MainTabItem> arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MainTabAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainTabAdapter.CustomViewHolder holder, int position) {
        int pos = position;
        holder.img.setImageResource(arrayList.get(pos).getImg());
        holder.title.setText(arrayList.get(pos).getTitle());
        if (arrayList.get(position).getOpen()) {
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.title.setTextColor(Color.GRAY);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.viewPager.setCurrentItem(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout button;
        ImageView img;
        TextView title;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.main_tab_button);
            img = itemView.findViewById(R.id.main_tab_img);
            title = itemView.findViewById(R.id.main_tab_text);
        }
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }
}