package com.dippola.relaxtour.pages.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.controller.SeekController;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.item.PageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.CustomViewHolder> {

    ArrayList<PageItem> arrayList;
    Context context;
    DatabaseHandler databaseHandler;

    public PageAdapter(ArrayList<PageItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        databaseHandler = new DatabaseHandler(context);
        int positions = position;

        if (arrayList.get(position).getImgdefault() != null) {
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
            if (arrayList.get(position).getIsplay() == 1) {
                holder.img.setImageBitmap(bitmap1);
            } else {
                holder.img.setImageBitmap(bitmap2);
            }
        }





//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {//LIGHT모드
//            if (arrayList.get(position).getIsplay() == 1) {
//                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
//                holder.img.setImageBitmap(bitmap1);
//            } else {
//                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
//                holder.img.setImageBitmap(bitmap2);
//            }
//        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {//dark모드
//            if (arrayList.get(position).getIsplay() == 1) {
//                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getDarkdefault(), 0, arrayList.get(position).getDarkdefault().length);
//                holder.img.setImageBitmap(bitmap1);
//            } else {
//                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
//                holder.img.setImageBitmap(bitmap2);
//            }
//        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {//system모드
//            Configuration config = context.getResources().getConfiguration();
//            Log.d("PageAdapter>>>", "get config : " + config.uiMode);
//
//
////            if (config.uiMode == Configuration.UI_MODE_NIGHT_NO) {//system light모드
////                Log.d("PageAdapter>>>", "1");
////                if (arrayList.get(position).getIsplay() == 1) {
////                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
////                    holder.img.setImageBitmap(bitmap1);
////                } else {
////                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
////                    holder.img.setImageBitmap(bitmap2);
////                }
////            } else if (config.uiMode == Configuration.UI_MODE_NIGHT_YES) {//system dark모드
////                Log.d("PageAdapter>>>", "2");
////                if (arrayList.get(position).getIsplay() == 1) {
////                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getDarkdefault(), 0, arrayList.get(position).getDarkdefault().length);
////                    holder.img.setImageBitmap(bitmap1);
////                } else {
////                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
////                    holder.img.setImageBitmap(bitmap2);
////                }
////            }
//        }

        if (arrayList.get(position).getPage() != 4) {
            holder.img.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.img.setMinimumHeight(MainActivity.pageitem_height_size);
            holder.download.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.download.setMinimumHeight(MainActivity.pageitem_height_size);
            holder.download.setMaxHeight(MainActivity.pageitem_height_size);
            holder.download.bringToFront();
            holder.progressBar.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.progressBar.setMinimumHeight(MainActivity.pageitem_height_size);
        } else if (arrayList.get(position).getPage() == 4) {
            holder.img.setMinimumWidth(MainActivity.pageitem_4_width_size);
            holder.img.setMinimumHeight(MainActivity.pageitem_4_height_size);
            holder.download.setMinimumWidth(MainActivity.pageitem_4_width_size);
            holder.download.setMinimumHeight(MainActivity.pageitem_4_height_size);
            holder.download.setMaxHeight(MainActivity.pageitem_4_height_size);
            holder.download.bringToFront();
            holder.progressBar.setMinimumWidth(MainActivity.pageitem_4_width_size);
            holder.progressBar.setMinimumHeight(MainActivity.pageitem_4_height_size);
        }

        holder.download.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.GONE);

        if (arrayList.get(position).getNeeddownload() == 2) {
            String path = context.getApplicationInfo().dataDir + "/cache/" + "audio" + arrayList.get(position).getPage() + "-" + arrayList.get(position).getPosition() + ".mp3";
            File file = new File(path);
            if (file.exists()) {
                holder.download.setVisibility(View.GONE);
            } else {
                holder.download.setImageBitmap(databaseHandler.getPageicon("download"));
                holder.download.setVisibility(View.VISIBLE);
                holder.img.setEnabled(false);
            }
        }

//        if (arrayList.get(position).getIsplay() == 1) {
//            holder.img.setImageBitmap(bitmap1);
//        } else {
//            holder.img.setImageBitmap(bitmap2);
//        }

        holder.seekBar.setProgress(arrayList.get(position).getSeek());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(positions).getIsplay() == 1) {//해당 아이템이 playing중이 아닐때
                    Bitmap bitmapremove = BitmapFactory.decodeByteArray(arrayList.get(positions).getImg(), 0, arrayList.get(positions).getImg().length);
                    holder.img.setImageBitmap(bitmapremove);
                    for (int i = 0; i < arrayList.size(); i++) {//같은 page에 재생중인게 있으면 없애기
                        int isplay = arrayList.get(i).getIsplay();
                        if (isplay == 2) {//change
                            int index = checkPlayinglistPosition(arrayList.get(i).getPage());
                            MainActivity.bottomSheetPlayList.remove(index);
                            MainActivity.bottomSheetAdapter.notifyItemRemoved(index);
                            arrayList.get(i).setIsplay(1);
                            notifyItemChanged(i);
                            notifyDataSetChanged();
                            AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(positions).getPnp());
                            break;
                        }
                    }
                    //add 해당 아이템 재생목록에 추가
                    MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
                    arrayList.get(positions).setIsplay(2);
                    MainActivity.bottomSheetPlayList.add(arrayList.get(positions));
                    databaseHandler.setPlay1(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());

                    if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0).getPnp())) {//이미 재생중인게 있을때
                        AudioController.startTrack(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    } else {//재생중인게 없을때
                        List<String> pp = new ArrayList<>();
                        for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {
                            pp.add(MainActivity.bottomSheetPlayList.get(ii).getPnp());
                            if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                                AudioController.startPlayingList(context, pp);
                            }
                        }
                    }
                    checkOpenService();
                } else {//해당 아이템이 playing중일때
                    //remove
                    Bitmap bitmapadd = BitmapFactory.decodeByteArray(arrayList.get(positions).getImgdefault(), 0, arrayList.get(positions).getImgdefault().length);
                    holder.img.setImageBitmap(bitmapadd);
                    databaseHandler.deletePlayingList(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                        if (MainActivity.bottomSheetPlayList.get(i).getPnp().equals(arrayList.get(positions).getPnp())) {
                            MainActivity.bottomSheetPlayList.remove(i);
                            MainActivity.bottomSheetAdapter.notifyItemRemoved(i);
                            MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    arrayList.get(positions).setIsplay(1);
                    AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(positions).getPnp());
                    stopServiceWhenPlaylistZero(context);
                }
            }
        });

        holder.seekBar.setMax(MainActivity.maxVolumn);
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {//터치
                SeekController.pageMoving = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {//변화
                if (SeekController.pageMoving) {
                    arrayList.get(positions).setSeek(seekBar.getProgress());
//                    notifyItemChanged(positions);
//                    notifyDataSetChanged();
                    float volume = (float) (1 - (Math.log(SeekController.MAX_VOLUME - i) / Math.log(SeekController.MAX_VOLUME)));
                    String pp = arrayList.get(positions).getPnp();
                    SeekController.changeVolumn(pp, volume);
                    SeekController.changeSeekInPage(context, arrayList.get(positions), seekBar.getProgress());
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {//끝
                notifyItemChanged(positions);
                notifyDataSetChanged();
                SeekController.pageMoving = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        SeekBar seekBar;
        ImageView download;
        ProgressBar progressBar;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.page_item_img);
            this.seekBar = itemView.findViewById(R.id.page_item_seekbar);
            this.download = itemView.findViewById(R.id.page_item_download);
            this.progressBar = itemView.findViewById(R.id.page_item_progress);
        }
    }

    private void checkOpenService() {
        if (!NotificationService.isPlaying) {
            Intent intent = new Intent(context, NotificationService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
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

    private int checkPlayinglistPosition(int page) {
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            int playlistpage = MainActivity.bottomSheetPlayList.get(i).getPage();
            if (playlistpage == page) {
                return i;
            }
        }
        return -1;
    }
}
