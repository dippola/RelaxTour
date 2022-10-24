package com.dippola.relaxtour.dialog.credit_dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;

import java.util.ArrayList;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CustomViewHolder> {

    ArrayList<CreditItem> arrayList = new ArrayList<>();

    public CreditAdapter(ArrayList<CreditItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_dialog_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.track.setText(arrayList.get(position).getTrack());
        holder.url.setText(arrayList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView track, url;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.track = itemView.findViewById(R.id.credit_dialog_item_track);
            this.url = itemView.findViewById(R.id.credit_dialog_item_url);
        }
    }
}
