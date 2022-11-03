package com.dippola.relaxtour.onboarding;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;

import java.util.ArrayList;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoringViewHolder> {

    ArrayList<OnBoardingItem> arrayList;
    Context context;
    Activity activity;

    public OnBoardingAdapter(ArrayList<OnBoardingItem> arrayList, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
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
//        holder.img.setImageResource(arrayList.get(position).getImg());
//        Glide.with(context).load(arrayList.get(position).getImg()).circleCrop().into(holder.img);

//        if (position != 2) {
//            holder.lottie.setVisibility(View.GONE);
//            holder.img.setVisibility(View.VISIBLE);
//            Glide.with(context).load(arrayList.get(position).getImg()).transform(new CenterCrop(), new RoundedCorners(800)).into(holder.img);
//        } else {
//            holder.img.setVisibility(View.GONE);
//            holder.lottie.setVisibility(View.VISIBLE);
//        }

//        holder.lottie.setVisibility(View.GONE);

//        Glide.with(context)
//                .applyDefaultRequestOptions(new RequestOptions().override(MainActivity.onboarding_test, MainActivity.onboarding_test))
//                .load(arrayList.get(position).getImg())
//                .transform(new CenterCrop(), new RoundedCorners(800))
//                .into(holder.img);

        if (position != 2) {
            Glide.with(context)
                    .load(arrayList.get(position).getImg())
                    .transform(new CenterCrop(), new RoundedCorners(800))
                    .into(holder.img);
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Glide.with(context)
                    .applyDefaultRequestOptions(new RequestOptions().override(size.x, size.x))
                    .load(arrayList.get(position).getImg())
                    .transform(new CenterCrop(), new RoundedCorners(800))
                    .into(holder.img);
        }

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
        LottieAnimationView lottie;

        public OnBoringViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.onboarding_item_page_img);
            this.text1 = itemView.findViewById(R.id.onboarding_item_page_text1);
            this.text2 = itemView.findViewById(R.id.onboarding_item_page_text2);
//            this.lottie = itemView.findViewById(R.id.onboarding_lottie);
        }
    }
}
