package com.dippola.relaxtour.pages.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_playlist_item, parent, false);
        FavListAdapter.CustomViewHolder holder = new FavListAdapter.CustomViewHolder(view);
        Log.d(">>>FavListAdapter", "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavListAdapter.CustomViewHolder holder, int position) {
        int positions = position;

        Log.d(">>>FavListAdapter", "onBindViewHolder");
//        databaseHandler = new DatabaseHandler(context);
//        arrayList = databaseHandler.getFavListItem(title);

        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
        holder.button.setImageBitmap(bitmap1);

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
        ImageView button;
        SeekBar seekBar;
        Button btn;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.bottom_sheet_playlist_item_img);
            this.seekBar = itemView.findViewById(R.id.bottom_sheet_playlist_item_seekbar);
            this.btn = itemView.findViewById(R.id.bottom_sheet_playlist_item_delete_btn);
        }
    }
}
