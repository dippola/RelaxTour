package com.dippola.relaxtour.pages.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MPList;
import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.controller.SeekController;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.AskDownloadDialog;
import com.dippola.relaxtour.dialog.PremiumDialog;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.item.DownloadItem;
import com.dippola.relaxtour.pages.item.PageItem;
import com.dippola.relaxtour.service.DownloadService;

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

        setPageImageTheme(position, holder.img);

        if (arrayList.get(position).getPage() != 1 && arrayList.get(position).getPage() != 2 && arrayList.get(position).getPage() != 3) {
            if (arrayList.get(position).getPage() == 4) {
                holder.img.setMinimumWidth(MainActivity.pageitem_4_width_size);
                holder.img.setMinimumHeight(MainActivity.pageitem_4_height_size);
            }
            holder.img.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.img.setMinimumHeight(MainActivity.pageitem_height_size);
        } else {
            holder.img.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.img.setMinimumHeight(MainActivity.pageitem_height_size);
        }

        holder.progressBar.setVisibility(View.GONE);

        if (databaseHandler.getIsProUser() == 1) {
            if (arrayList.get(positions).getIspro() == 2) {
                holder.download.setVisibility(View.GONE);
                holder.download.setEnabled(false);
                holder.img.setEnabled(false);
                holder.seekBar.setEnabled(false);
                holder.pro.setEnabled(true);
                setBackgroundPro(arrayList.get(positions).getPage(), holder.pro);
            } else if (arrayList.get(positions).getIspro() == 1) {
                if (arrayList.get(positions).getNeeddownload() == 1) {
                    holder.pro.setVisibility(View.GONE);
                    holder.pro.setEnabled(false);
                    holder.download.setVisibility(View.GONE);
                    holder.download.setEnabled(false);
                    holder.img.setEnabled(true);
                    holder.seekBar.setEnabled(true);
                } else if (arrayList.get(positions).getNeeddownload() == 2) {
                    File file = new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(positions).getPage() + "to" + arrayList.get(positions).getPosition() + ".mp3");
                    if (file.exists()) {
                        holder.download.setVisibility(View.GONE);
                        holder.download.setEnabled(false);
                        holder.img.setEnabled(true);
                        holder.seekBar.setEnabled(true);
                    } else {
                        setBackgroundDownload(arrayList.get(positions).getPage(), holder.download);
                        holder.download.setVisibility(View.VISIBLE);
                        holder.download.setEnabled(true);
                        holder.img.setEnabled(false);
                        holder.seekBar.setEnabled(false);
                    }
                }
            }
        } else if (databaseHandler.getIsProUser() == 2) {
            holder.pro.setVisibility(View.GONE);
            holder.pro.setEnabled(false);
            if (arrayList.get(positions).getNeeddownload() == 1) {
                holder.download.setVisibility(View.GONE);
                holder.download.setEnabled(false);
                holder.img.setEnabled(true);
                holder.seekBar.setEnabled(true);
            } else if (arrayList.get(positions).getNeeddownload() == 2) {
                if (new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(positions).getPage() + "to" + arrayList.get(positions).getPosition() + ".mp3").exists()) {
                    holder.download.setVisibility(View.GONE);
                    holder.seekBar.setEnabled(true);
                } else {
                    setBackgroundDownload(arrayList.get(positions).getPage(), holder.download);
                    holder.download.setVisibility(View.VISIBLE);
                    holder.download.setEnabled(true);
                    holder.img.setEnabled(false);
                    holder.seekBar.setEnabled(false);
                }
            }
        }

//        holder.pro.setVisibility(View.GONE);
//        holder.pro.setEnabled(false);
//        if (arrayList.get(position).getNeeddownload() == 1) {
//            holder.download.setVisibility(View.GONE);
//            holder.download.setEnabled(false);
//            holder.img.setEnabled(true);
//            holder.seekBar.setEnabled(true);
//        } else if (arrayList.get(position).getNeeddownload() == 2) {
//            if (new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(position).getPage() + "to" + arrayList.get(position).getPosition() + ".mp3").exists()) {
//                holder.download.setVisibility(View.GONE);
//                holder.seekBar.setEnabled(true);
//            } else {
//                setBackgroundDownload(arrayList.get(position).getPage(), holder.download);
//                holder.download.setVisibility(View.VISIBLE);
//                holder.download.setEnabled(true);
//                holder.img.setEnabled(false);
//                holder.seekBar.setEnabled(false);
//            }
//        }

        holder.name.setText(arrayList.get(position).getName());
        holder.seekBar.setProgress(arrayList.get(position).getSeek());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(positions).getPage() != 4) {//1,2,3page
                    page(positions, holder.img);
                } else {//4page nature??????
                    page4(positions, holder.img);
                }
                page4Count();
            }
        });

        holder.seekBar.setMax(MainActivity.maxVolumn);
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {//??????
                SeekController.pageMoving = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {//??????
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
            public void onStopTrackingTouch(SeekBar seekBar) {//???
                notifyItemChanged(positions);
                notifyDataSetChanged();
                SeekController.pageMoving = false;
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("download_dialog_checkbox", MODE_PRIVATE);
                boolean isChecked = sharedPreferences.getBoolean("isChecked", false);
                if (isChecked) {
                    openDownloadService(context, holder.progressBar, holder.img, holder.download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    DownloadItem downloadItem = new DownloadItem(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    DownloadService.downloadList.add(downloadItem);
                    DownloadService.setOnClickDownload(context, holder.progressBar, holder.img, holder.download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition(), holder.seekBar, downloadItem);
                } else {
                    AskDownloadDialog.askDownloadDialog(context, holder.progressBar, holder.img, holder.download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition(), holder.seekBar);
                }
            }
        });

        holder.pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PremiumDialog.class));
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
        ImageView download, pro;
        ProgressBar progressBar;
        TextView name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.page_item_img);
            this.seekBar = itemView.findViewById(R.id.page_item_seekbar);
            this.download = itemView.findViewById(R.id.page_item_download);
            this.pro = itemView.findViewById(R.id.page_item_pro);
            this.progressBar = itemView.findViewById(R.id.page_item_progress);
            this.name = itemView.findViewById(R.id.page_item_name);
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

    private int checkPlayinglistPosition(int page) {//?????????????????? ?????? ?????? ???????????? ????????? bottomSheetPlayList????????? ????????? ????????????
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            int playlistpage = MainActivity.bottomSheetPlayList.get(i).getPage();
            if (playlistpage == page) {
                return i;
            }
        }
        return -1;
    }

    private void setPageImageTheme(int position, ImageView img) {
        if (arrayList.get(position).getIsplay() == 1) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
                img.setImageBitmap(bitmap1);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDarkdefault(), 0, arrayList.get(position).getDarkdefault().length);
                img.setImageBitmap(bitmap2);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                Configuration config = context.getResources().getConfiguration();
                int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO://system light ??????
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark ??????
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDarkdefault(), 0, arrayList.get(position).getDarkdefault().length);
                        img.setImageBitmap(bitmap2);
                        break;
                }
            }
        } else {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                img.setImageBitmap(bitmap1);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
                img.setImageBitmap(bitmap2);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                Configuration config = context.getResources().getConfiguration();
                int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO://system light ??????
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark ??????
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
                        img.setImageBitmap(bitmap2);
                        break;
                }
            }
        }
    }

    private void setPageImageOnClickChangeImage(int position, ImageView img) {
        if (arrayList.get(position).getIsplay() == 1) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                img.setImageBitmap(bitmap1);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
                img.setImageBitmap(bitmap2);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                Configuration config = context.getResources().getConfiguration();
                int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO://system light ??????
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark ??????
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDark(), 0, arrayList.get(position).getDark().length);
                        img.setImageBitmap(bitmap2);
                        break;
                }
            }
        } else {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
                img.setImageBitmap(bitmap1);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDarkdefault(), 0, arrayList.get(position).getDarkdefault().length);
                img.setImageBitmap(bitmap2);
            } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                Configuration config = context.getResources().getConfiguration();
                int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO://system light ??????
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark ??????
                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(arrayList.get(position).getDarkdefault(), 0, arrayList.get(position).getDarkdefault().length);
                        img.setImageBitmap(bitmap2);
                        break;
                }
            }
        }
    }

    private void page(int positions, ImageView img) {
        for (int count = 0; count < MainActivity.bottomSheetPlayList.size(); count++) {
            MPList.initalMP(MainActivity.bottomSheetPlayList.get(count).getPnp(), context, MainActivity.bottomSheetPlayList.get(count).getSeek());
        }
        MPList.initalMP(arrayList.get(positions).getPnp(), context, arrayList.get(positions).getSeek());
        if (arrayList.get(positions).getIsplay() == 1) {//?????? ???????????? playing?????? ?????????
            setPageImageOnClickChangeImage(positions, img);
            for (int i = 0; i < arrayList.size(); i++) {//?????? page??? ??????????????? ????????? ?????????
                if (arrayList.get(i).getIsplay() == 2) {//??????page??? ??????????????? ?????????
                    int index = checkPlayinglistPosition(arrayList.get(i).getPage());//?????????????????? ?????? ?????? ???????????? ????????? bottomSheetPlayList????????? ????????? ????????????
                    MainActivity.bottomSheetPlayList.remove(index);//?????????????????? ?????? ?????? ???????????? ?????? ?????????
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(index);//????????????
                    arrayList.get(i).setIsplay(1);//page??? ?????? ???????????? isplay 1??? ?????????
                    notifyItemChanged(i);//??????page ????????????
                    notifyDataSetChanged();
                    AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(i).getPnp());//???????????? ????????????
                    break;
                }
            }
            //add ?????? ????????? ??????????????? ??????
            MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
            arrayList.get(positions).setIsplay(2);//?????? ???????????? ?????? isplay 2??? ?????????
            MainActivity.bottomSheetPlayList.add(arrayList.get(positions));//bottom playlist??? ?????? ???????????? ??????
            databaseHandler.setPlay1(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db?????? ?????????,???????????? isplay????????? bottom list?????? ??????????????? ??????
            MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());//bottom list ????????????

            if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0), context)) {//??????page??? ?????? ??????????????? ????????? (pands????????? ???????????????)
                AudioController.startTrack(context, arrayList.get(positions));//?????? ????????? ?????? ????????? ????????? ??????
            } else {//??????????????? ?????????(pands????????? ?????? ?????? ?????????)
                List<PageItem> pp = new ArrayList<>();
                for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {//bottom list??? ?????? ?????? pnp ??????
                    pp.add(MainActivity.bottomSheetPlayList.get(ii));
                    if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                        AudioController.startPlayingList(context, pp);//bottom list??? ?????? ?????? ??? ??????
                    }
                }
            }
            checkOpenService();
        } else {//?????? ???????????? playing?????????
            //remove
            setPageImageOnClickChangeImage(positions, img);
            databaseHandler.deletePlayingList(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db bottom list?????? ????????? page db??? isplay 1??? ??????
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                if (MainActivity.bottomSheetPlayList.get(i).getPnp().equals(arrayList.get(positions).getPnp())) {//bottom list??? ?????? ???????????? ?????????????????? ?????????
                    MainActivity.bottomSheetPlayList.remove(i);
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(i);//????????? ????????????
                    MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                    break;
                }
            }
            arrayList.get(positions).setIsplay(1);
            AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(positions).getPnp());
            stopServiceWhenPlaylistZero(context);
        }
    }

    private void page4(int positions, ImageView img) {
        for (int count = 0; count < MainActivity.bottomSheetPlayList.size(); count++) {
            MPList.initalMP(MainActivity.bottomSheetPlayList.get(count).getPnp(), context, MainActivity.bottomSheetPlayList.get(count).getSeek());
        }
        MPList.initalMP(arrayList.get(positions).getPnp(), context, arrayList.get(positions).getSeek());
        if (arrayList.get(positions).getIsplay() == 1) {
            int count = 0;
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                if (MainActivity.bottomSheetPlayList.get(i).getPage() == 4) {
                    count += 1;
                }
            }
            if (count < 3) {
                setPageImageOnClickChangeImage(positions, img);
                MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
                arrayList.get(positions).setIsplay(2);//?????? ???????????? ?????? isplay 2??? ?????????
                MainActivity.bottomSheetPlayList.add(arrayList.get(positions));//bottom playlist??? ?????? ???????????? ??????
                databaseHandler.setIsPlayPage4(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db?????? ?????????,???????????? isplay????????? bottom list?????? ??????????????? ??????
                MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());//bottom list ????????????

                if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0), context)) {//??????page??? ?????? ??????????????? ????????? (pands????????? ???????????????)
                    AudioController.startTrack(context, arrayList.get(positions));//?????? ????????? ?????? ????????? ??????
                } else {//??????????????? ?????????(pands????????? ?????? ?????? ?????????)
                    List<PageItem> pp = new ArrayList<>();
                    for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {//bottom list??? ?????? ?????? pnp ??????
                        pp.add(MainActivity.bottomSheetPlayList.get(ii));
                        if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                            AudioController.startPlayingList(context, pp);//bottom list??? ?????? ?????? ??? ??????
                        }
                    }
                }
            } else {
                Toast.makeText(context, "limit 3 over", Toast.LENGTH_SHORT).show();
            }
        } else {
            setPageImageOnClickChangeImage(positions, img);
            databaseHandler.deletePlayingList(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db bottom list?????? ????????? page db??? isplay 1??? ??????
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                if (MainActivity.bottomSheetPlayList.get(i).getPnp().equals(arrayList.get(positions).getPnp())) {//bottom list??? ?????? ???????????? ?????????????????? ?????????
                    MainActivity.bottomSheetPlayList.remove(i);
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(i);//????????? ????????????
                    MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                    break;
                }
            }
            arrayList.get(positions).setIsplay(1);
            AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(positions).getPnp());
            stopServiceWhenPlaylistZero(context);
        }
    }

    public static void page4Count() {
        int count = 0;
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            if (MainActivity.bottomSheetPlayList.get(i).getPage() == 4) {
                count += 1;
            }
        }
        if (NaturePage.count != null) {
            NaturePage.count.setText(count + " / 3");
        }
    }

    public static void openDownloadService(Context context, ProgressBar progressBar, ImageView img, ImageView download, int page, int position) {
        if (!DownloadService.isDownloadOpen) {
            DownloadService downloadService = new DownloadService(progressBar, img, download, page, position);
            Intent intent = new Intent(context, downloadService.getClass());
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }

    private void setBackgroundPro(int page, ImageView img) {
        if (page != 1 && page != 2 && page != 3) {
            if (page == 4) {
                img.setImageBitmap(databaseHandler.getPageicon("pro"));
                img.setMinimumWidth(MainActivity.pageitem_4_width_size);
                img.setMinimumHeight(MainActivity.pageitem_4_height_size);
                img.setMaxHeight(MainActivity.pageitem_height_size);
            }
            if (page == 5 || page == 6 || page == 7) {
                img.setImageBitmap(databaseHandler.getPageicon("circlepro"));
            }
        } else {
            img.setImageBitmap(databaseHandler.getPageicon("pro"));
            img.setMinimumWidth(MainActivity.pageitem_width_size);
            img.setMinimumHeight(MainActivity.pageitem_height_size);
            img.setMaxHeight(MainActivity.pageitem_height_size);
        }
    }

    private void setBackgroundDownload(int page, ImageView img) {
        if (page != 1 && page != 2 && page != 3) {
            if (page == 4) {
                img.setImageBitmap(databaseHandler.getPageicon("download"));
                img.setMinimumWidth(MainActivity.pageitem_4_width_size);
                img.setMinimumHeight(MainActivity.pageitem_4_height_size);
                img.setMaxHeight(MainActivity.pageitem_4_height_size);
            }
            if (page == 5 || page == 6 || page == 7) {
                img.setImageBitmap(databaseHandler.getPageicon("circledownload"));
            }
        } else {
            img.setImageBitmap(databaseHandler.getPageicon("download"));
            img.setMinimumWidth(MainActivity.pageitem_width_size);
            img.setMinimumHeight(MainActivity.pageitem_height_size);
            img.setMaxHeight(MainActivity.pageitem_height_size);
        }
    }
}
