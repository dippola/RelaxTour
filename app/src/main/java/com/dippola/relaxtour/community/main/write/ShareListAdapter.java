package com.dippola.relaxtour.community.main.write;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.pages.adapter.FavTitleAdapter;
import com.dippola.relaxtour.pages.item.FavListItem;

import java.util.List;

public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.ViewHolder> {
    List<FavListItem> listitems;
    Context context;

    public ShareListAdapter(List<FavListItem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }
    @NonNull
    @Override
    public ShareListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_share_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareListAdapter.ViewHolder holder, int position) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(listitems.get(position).getImg(), 0, listitems.get(position).getImg().length);
            holder.img.setImageBitmap(bitmap1);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(listitems.get(position).getDark(), 0, listitems.get(position).getDark().length);
            holder.img.setImageBitmap(bitmap2);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            Configuration config = context.getResources().getConfiguration();
            int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO://system light 모드
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(listitems.get(position).getImg(), 0, listitems.get(position).getImg().length);
                    holder.img.setImageBitmap(bitmap1);
                    break;
                case Configuration.UI_MODE_NIGHT_YES://system dark 모드
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(listitems.get(position).getDark(), 0, listitems.get(position).getDark().length);
                    holder.img.setImageBitmap(bitmap2);
                    break;
            }
        }

        holder.name.setText(listitems.get(position).getName());
        holder.seekBar.setProgress(listitems.get(position).getSeek());
        holder.seekBar.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        SeekBar seekBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.community_share_list_item_img);
            name = itemView.findViewById(R.id.community_share_list_item_name);
            seekBar = itemView.findViewById(R.id.community_share_list_item_seekbar);
        }
    }
}
