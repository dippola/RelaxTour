package com.dippola.relaxtour.community.admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.CustomViewHolder> {

    Context context;
    List<ReportModel> arrayList;

    ItemClickListener itemClickListener;

    public AdminAdapter(Context context, List<ReportModel> arrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AdminAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_report_item, parent, false);
        AdminAdapter.CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.CustomViewHolder holder, int position) {
        int i = position;
        holder.date.setText(arrayList.get(i).getDate());
        holder.from.setText("from: " + arrayList.get(i).getFrom());
        if (arrayList.get(i).getFrom().equals("post")) {
            holder.commentid.setVisibility(View.GONE);
            holder.postid.setText("post id: " + String.valueOf(arrayList.get(i).getPostid()));
        } else {
            holder.postid.setVisibility(View.GONE);
            holder.commentid.setText("comment id: " + String.valueOf(arrayList.get(i).getCommentid()));
        }
        holder.choice.setText("choice: " + String.valueOf(arrayList.get(i).getChoice()));
        holder.decUser.setText("decUser: " + String.valueOf(arrayList.get(i).getDecUser()));
        if (arrayList.get(i).getEdit().equals("")) {
            holder.edit.setText("null");
        } else {
            holder.edit.setText(arrayList.get(i).getEdit());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout item;
        TextView date, from, choice, decUser, postid, commentid, edit;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView.findViewById(R.id.admin_report_item);
            this.date = itemView.findViewById(R.id.admin_report_item_date);
            this.from = itemView.findViewById(R.id.admin_report_item_from);
            this.choice = itemView.findViewById(R.id.admin_report_item_choice);
            this.decUser = itemView.findViewById(R.id.admin_report_item_decuser);
            this.postid = itemView.findViewById(R.id.admin_report_item_postid);
            this.commentid = itemView.findViewById(R.id.admin_report_item_commentid);
            this.edit = itemView.findViewById(R.id.admin_report_item_edit);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
