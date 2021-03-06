package com.dippola.relaxtour.pages.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.SeekController;
import com.dippola.relaxtour.pages.item.FavListItem;

import java.util.ArrayList;

public class FavListAdapter  extends RecyclerView.Adapter<FavListAdapter.CustomViewHolder> {
    Context context;
    public static ArrayList<FavListItem> arrayList = new ArrayList<>();

    public FavListAdapter(ArrayList<FavListItem> arrayList, Context context) {
        FavListAdapter.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favlist_item, parent, false);
        FavListAdapter.CustomViewHolder holder = new FavListAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavListAdapter.CustomViewHolder holder, int position) {
        int positions = position;
//        databaseHandler = new DatabaseHandler(context);
//        arrayList = databaseHandler.getFavListItem(title);

        if (arrayList.size() != 0) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                holder.img.setImageBitmap(bitmap1);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
                holder.img.setImageBitmap(bitmap2);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                Configuration config = context.getResources().getConfiguration();
                int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO://system light ??????
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                        holder.img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark ??????
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
                        holder.img.setImageBitmap(bitmap2);
                        break;
                }
            }
        }




//        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
//        holder.img.setImageBitmap(bitmap1);

        holder.name.setText(arrayList.get(position).getName());
        holder.seekBar.setProgress(arrayList.get(position).getSeek());
        holder.seekBar.setMax(MainActivity.maxVolumn);

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (SeekController.favMoving) {
                    arrayList.get(positions).setSeek(seekBar.getProgress());
                    float volume = (float) (1 - (Math.log(SeekController.MAX_VOLUME - i) / Math.log(SeekController.MAX_VOLUME)));
                    String pp = arrayList.get(positions).getPnp();
                    SeekController.changeVolumn(pp, volume);
                    SeekController.changeSeekInFavList(context, arrayList.get(positions), i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekController.favMoving = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                notifyItemChanged(positions);
                notifyDataSetChanged();
                SeekController.favMoving = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        SeekBar seekBar;
        TextView name;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.favlist_item_img);
            this.seekBar = itemView.findViewById(R.id.favlist_item_seekbar);
            this.name = itemView.findViewById(R.id.favlist_item_name);
        }
    }
}
