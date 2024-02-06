package com.dippola.relaxtour.community.main.detail;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.List;

public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.CustomViewHolder> {
    List<String> list;
    List<PageItem> allTidList;
    DatabaseHandler databaseHandler;
    Context context;

    public ShareListAdapter(List<String> list, DatabaseHandler databaseHandler, Context context, List<PageItem> allTidList) {
        this.list = list;
        this.databaseHandler = databaseHandler;
        this.context = context;
        this.allTidList = allTidList;
    }
    @NonNull
    @Override
    public ShareListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_share_list_item, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareListAdapter.CustomViewHolder holder, int position) {
        String item = list.get(position);
        String split[] = item.split("-");
        String tid = split[0];
        String seek = split[1];
        holder.seekBar.setEnabled(false);
        for (int i = 0; i < allTidList.size(); i++) {
            if (allTidList.get(i).getTid().equals(tid)) {
                holder.name.setText(allTidList.get(i).getName());
                setImg(holder.img, databaseHandler.getTrackImageLight(allTidList.get(i).getTid()), databaseHandler.getTrackImageDark(allTidList.get(i).getTid()));
                holder.seekBar.setMax(MainActivity.maxVolumn);
                holder.seekBar.setProgress(Integer.parseInt(seek));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        SeekBar seekBar;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.community_share_list_item_img);
            name = itemView.findViewById(R.id.community_share_list_item_name);
            seekBar = itemView.findViewById(R.id.community_share_list_item_seekbar);
        }
    }

    private void setImg(ImageView img, byte[] light, byte[] dark) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(light, 0, light.length);
            img.setImageBitmap(bitmap1);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(dark, 0, dark.length);
            img.setImageBitmap(bitmap2);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            Configuration config = context.getResources().getConfiguration();
            int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO://system light 모드
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(light, 0, light.length);
                    img.setImageBitmap(bitmap1);
                    break;
                case Configuration.UI_MODE_NIGHT_YES://system dark 모드
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(dark, 0, dark.length);
                    img.setImageBitmap(bitmap2);
                    break;
            }
        } else {
            Configuration config = context.getResources().getConfiguration();
            int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO://system light 모드
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(light, 0, light.length);
                    img.setImageBitmap(bitmap1);
                    break;
                case Configuration.UI_MODE_NIGHT_YES://system dark 모드
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(dark, 0, dark.length);
                    img.setImageBitmap(bitmap2);
                    break;
            }
        }
    }
}
