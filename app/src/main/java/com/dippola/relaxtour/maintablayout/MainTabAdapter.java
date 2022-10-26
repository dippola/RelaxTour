package com.dippola.relaxtour.maintablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.ThemeHelper;

import java.util.ArrayList;

public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.CustomViewHolder> {
    ArrayList<MainTabItem> arrayList;
    Context context;

    public MainTabAdapter(ArrayList<MainTabItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        holder.title2.bringToFront();
        holder.img.setImageResource(arrayList.get(pos).getImg());
        holder.title.setText(arrayList.get(pos).getTitle());
        holder.title2.setText(arrayList.get(pos).getTitle());
        if (arrayList.get(position).getOpen()) {
            holder.title2.setTextColor(Color.WHITE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.tab_anim_1);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.anim.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            holder.anim.startAnimation(animation);
        } else {
            holder.title2.setTextColor(Color.GRAY);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.tab_anim_2);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.anim.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            holder.anim.startAnimation(animation);
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
        RelativeLayout button, anim;
        ImageView img;
        TextView title, title2;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.main_tab_button);
            img = itemView.findViewById(R.id.main_tab_img);
            title = itemView.findViewById(R.id.main_tab_text);
            anim = itemView.findViewById(R.id.main_tab_anim);
            title2 = itemView.findViewById(R.id.main_tab_text2);
        }
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).hashCode();
    }
}