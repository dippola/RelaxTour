package com.dippola.relaxtour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.controller.SeekController;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.PageItem;

import java.util.ArrayList;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.CustomViewHolder> {
    Context context;
    ArrayList<PageItem> arrayList;

    public BottomSheetAdapter(ArrayList<PageItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_playlist_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        int positions = position;

//        if (arrayList.size() != 0) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
//            holder.button.setImageBitmap(bitmap);
//        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
        holder.button.setImageBitmap(bitmap);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.seekBar.setProgress(arrayList.get(position).getSeek());
        holder.seekBar.setMax(MainActivity.maxVolumn);

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int getposition = arrayList.get(positions).getPosition();
                int getpage = arrayList.get(positions).getPage();
                int index = arrayList.indexOf(arrayList.get(positions));
                MainActivity.databaseHandler.deletePlayingList(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(positions).getPnp());
                arrayList.remove(index);
                MainActivity.bottomSheetAdapter.notifyItemRemoved(index);
                MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                if (MainActivity.bottomSheetPlayList.size() == 0) {
                    stopServiceWhenPlaylistZero(context);
                }
                changePageItemBackground(getpage, getposition);
            }
        });

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (SeekController.bottomMoving) {
                    arrayList.get(positions).setSeek(seekBar.getProgress());
                    float volume = (float) (1 - (Math.log(SeekController.MAX_VOLUME - i) / Math.log(SeekController.MAX_VOLUME)));
                    String pp = arrayList.get(positions).getPnp();
                    SeekController.changeVolumn(pp, volume);
                    SeekController.changeSeekInBottom(context, arrayList.get(positions), seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekController.bottomMoving = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                notifyItemChanged(positions);
                notifyDataSetChanged();
                SeekController.bottomMoving = false;
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
        Button delete_btn;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.bottom_sheet_playlist_item_img);
            this.seekBar = itemView.findViewById(R.id.bottom_sheet_playlist_item_seekbar);
            this.delete_btn = itemView.findViewById(R.id.bottom_sheet_playlist_item_delete_btn);
        }
    }

    private void stopServiceWhenPlaylistZero(Context context) {
        if (MainActivity.bottomSheetPlayList.size() == 0) {
            MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
            if (NotificationService.isPlaying) {
                context.stopService(new Intent(context, NotificationService.class));
            }
        }
    }

    private void changePageItemBackground(int page, int position) {
        if (page == 1) {
            RainPage.arrayList.get(position - 1).setIsplay(1);
            RainPage.adapter.notifyItemChanged(position - 1);
//            Page1.adapter.notifyDataSetChanged();
        } else if (page == 2) {
            WindPage.arrayList.get(position - 1).setIsplay(1);
            WindPage.adapter.notifyItemChanged(position - 1);
//            Page2.adapter.notifyDataSetChanged();
        } else if (page == 3) {
            ChakraPage.arrayList.get(position - 1).setIsplay(1);
            ChakraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 4) {
            HzPage.arrayList.get(position - 1).setIsplay(1);
            HzPage.adapter.notifyItemChanged(position - 1);
        }
    }
}
