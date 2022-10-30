package com.dippola.relaxtour.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;

import java.util.ArrayList;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoringViewHolder> {

    ArrayList<OnBoardingItem> arrayList;

    public OnBoardingAdapter(ArrayList<OnBoardingItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public OnBoringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onboarding_item_page, parent, false);
        return new OnBoringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoringViewHolder holder, int position) {
        holder.img.setImageResource(arrayList.get(position).getImg());
        holder.text1.setText(arrayList.get(position).getText1());
        holder.text2.setText(arrayList.get(position).getText2());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class OnBoringViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text1, text2;
        public OnBoringViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.onboarding_item_page_img);
            this.text1 = itemView.findViewById(R.id.onboarding_item_page_text1);
            this.text2 = itemView.findViewById(R.id.onboarding_item_page_text2);
        }
    }
}
