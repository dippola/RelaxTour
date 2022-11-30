package com.dippola.relaxtour.community.main.write;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.adapter.FavTitleAdapter;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.FavTitleItem;

import java.util.List;

public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.CustomViewHolder> {
    List<FavTitleItem> titleitems;

    public ShareListAdapter(List<FavTitleItem> titleitems) {
        this.titleitems = titleitems;
    }
    @NonNull
    @Override
    public ShareListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_share_list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareListAdapter.CustomViewHolder holder, int position) {
        int i = position;
        holder.title.setText(titleitems.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return titleitems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView title;
        CheckBox uandd;
        Button choice;
        RelativeLayout hide;
        RecyclerView list;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.community_write_share_list_item_layout);
            title = itemView.findViewById(R.id.community_write_share_list_item_title);
            uandd = itemView.findViewById(R.id.community_write_share_list_item_uandd);
            choice = itemView.findViewById(R.id.community_write_share_list_item_choice);
            hide = itemView.findViewById(R.id.community_write_share_list_item_hide);
            list = itemView.findViewById(R.id.community_write_share_list_item_recyclerview);
        }
    }
}
