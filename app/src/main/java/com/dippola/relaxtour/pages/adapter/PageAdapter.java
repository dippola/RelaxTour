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
import com.dippola.relaxtour.pages.item.ViewTypeCode;
import com.dippola.relaxtour.service.DownloadService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<PageItem> arrayList;
    Context context;
    int viewTypeCode;
    DatabaseHandler databaseHandler;

    public PageAdapter(ArrayList<PageItem> arrayList, Context context, int viewTypeCode) {
        this.arrayList = arrayList;
        this.context = context;
        this.viewTypeCode = viewTypeCode;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
//        Page4ViewHolder holder = new Page4ViewHolder(view);

        View view;
        if (viewTypeCode == ViewTypeCode.ViewType.PAGE123) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
            return new Page123ViewHolder(view);
        } else if (viewTypeCode == ViewTypeCode.ViewType.PAGE4) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item4, parent, false);
            return new Page4ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item567, parent, false);
            return new Page567ViewHolder(view);
        }

//        return holder;
        //61memo
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        databaseHandler = new DatabaseHandler(context);
        int positions = position;

        if (holder instanceof Page123ViewHolder) {
            setViewHolder(
                    0,
                    position,
                    ((Page123ViewHolder) holder).img,
                    ((Page123ViewHolder) holder).download,
                    ((Page123ViewHolder) holder).pro,
                    ((Page123ViewHolder) holder).seekBar,
                    ((Page123ViewHolder) holder).progressBar,
                    ((Page123ViewHolder) holder).name

            );
        } else if (holder instanceof Page4ViewHolder) {
            setViewHolder(
                    1,
                    position,
                    ((Page4ViewHolder) holder).img,
                    ((Page4ViewHolder) holder).download,
                    ((Page4ViewHolder) holder).pro,
                    ((Page4ViewHolder) holder).seekBar,
                    ((Page4ViewHolder) holder).progressBar,
                    ((Page4ViewHolder) holder).name
            );
        } else if (holder instanceof Page567ViewHolder) {
            setViewHolder(
                    2,
                    position,
                    ((Page567ViewHolder) holder).img,
                    ((Page567ViewHolder) holder).download,
                    ((Page567ViewHolder) holder).pro,
                    ((Page567ViewHolder) holder).seekBar,
                    ((Page567ViewHolder) holder).progressBar,
                    ((Page567ViewHolder) holder).name
            );
        }

//        setPageImageTheme(position, holder.img);
//
//        if (arrayList.get(position).getPage() != 1 && arrayList.get(position).getPage() != 2 && arrayList.get(position).getPage() != 3) {
//            if (arrayList.get(position).getPage() == 4) {
//                holder.img.setMinimumWidth(MainActivity.pageitem_4_width_size);
//                holder.img.setMinimumHeight(MainActivity.pageitem_4_height_size);
//            }
//            holder.img.setMinimumWidth(MainActivity.pageitem_width_size);
//            holder.img.setMinimumHeight(MainActivity.pageitem_height_size);
//        } else {
//            holder.img.setMinimumWidth(MainActivity.pageitem_width_size);
//            holder.img.setMinimumHeight(MainActivity.pageitem_height_size);
//        }
//
//        holder.progressBar.setVisibility(View.GONE);
//
//        if (databaseHandler.getIsProUser() == 1) {
//            if (arrayList.get(positions).getIspro() == 2) {
//                holder.download.setVisibility(View.GONE);
//                holder.download.setEnabled(false);
//                holder.img.setEnabled(false);
//                holder.seekBar.setEnabled(false);
//                holder.pro.setEnabled(true);
//                setBackgroundPro(arrayList.get(positions).getPage(), holder.pro);
//            } else if (arrayList.get(positions).getIspro() == 1) {
//                if (arrayList.get(positions).getNeeddownload() == 1) {
//                    holder.pro.setVisibility(View.GONE);
//                    holder.pro.setEnabled(false);
//                    holder.download.setVisibility(View.GONE);
//                    holder.download.setEnabled(false);
//                    holder.img.setEnabled(true);
//                    holder.seekBar.setEnabled(true);
//                } else if (arrayList.get(positions).getNeeddownload() == 2) {
//                    File file = new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(positions).getPage() + "to" + arrayList.get(positions).getPosition() + ".mp3");
//                    if (file.exists()) {
//                        holder.download.setVisibility(View.GONE);
//                        holder.download.setEnabled(false);
//                        holder.img.setEnabled(true);
//                        holder.seekBar.setEnabled(true);
//                    } else {
//                        setBackgroundDownload(arrayList.get(positions).getPage(), holder.download);
//                        holder.download.setVisibility(View.VISIBLE);
//                        holder.download.setEnabled(true);
//                        holder.img.setEnabled(false);
//                        holder.seekBar.setEnabled(false);
//                    }
//                }
//            }
//        } else if (databaseHandler.getIsProUser() == 2) {
//            holder.pro.setVisibility(View.GONE);
//            holder.pro.setEnabled(false);
//            if (arrayList.get(positions).getNeeddownload() == 1) {
//                holder.download.setVisibility(View.GONE);
//                holder.download.setEnabled(false);
//                holder.img.setEnabled(true);
//                holder.seekBar.setEnabled(true);
//            } else if (arrayList.get(positions).getNeeddownload() == 2) {
//                if (new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(positions).getPage() + "to" + arrayList.get(positions).getPosition() + ".mp3").exists()) {
//                    holder.download.setVisibility(View.GONE);
//                    holder.seekBar.setEnabled(true);
//                } else {
//                    setBackgroundDownload(arrayList.get(positions).getPage(), holder.download);
//                    holder.download.setVisibility(View.VISIBLE);
//                    holder.download.setEnabled(true);
//                    holder.img.setEnabled(false);
//                    holder.seekBar.setEnabled(false);
//                }
//            }
//        }
//
////        holder.pro.setVisibility(View.GONE);
////        holder.pro.setEnabled(false);
////        if (arrayList.get(position).getNeeddownload() == 1) {
////            holder.download.setVisibility(View.GONE);
////            holder.download.setEnabled(false);
////            holder.img.setEnabled(true);
////            holder.seekBar.setEnabled(true);
////        } else if (arrayList.get(position).getNeeddownload() == 2) {
////            if (new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(position).getPage() + "to" + arrayList.get(position).getPosition() + ".mp3").exists()) {
////                holder.download.setVisibility(View.GONE);
////                holder.seekBar.setEnabled(true);
////            } else {
////                setBackgroundDownload(arrayList.get(position).getPage(), holder.download);
////                holder.download.setVisibility(View.VISIBLE);
////                holder.download.setEnabled(true);
////                holder.img.setEnabled(false);
////                holder.seekBar.setEnabled(false);
////            }
////        }
//
//        holder.name.setText(arrayList.get(position).getName());
//        holder.seekBar.setProgress(arrayList.get(position).getSeek());
//
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (arrayList.get(positions).getPage() != 4) {//1,2,3page
//                    page(positions, holder.img);
//                } else {//4page nature일때
//                    page4(positions, holder.img);
//                }
//                page4Count();
//            }
//        });
//
//        holder.seekBar.setMax(MainActivity.maxVolumn);
//        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {//터치
//                SeekController.pageMoving = true;
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {//변화
//                if (SeekController.pageMoving) {
//                    arrayList.get(positions).setSeek(seekBar.getProgress());
////                    notifyItemChanged(positions);
////                    notifyDataSetChanged();
//                    float volume = (float) (1 - (Math.log(SeekController.MAX_VOLUME - i) / Math.log(SeekController.MAX_VOLUME)));
//                    String pp = arrayList.get(positions).getPnp();
//                    SeekController.changeVolumn(pp, volume);
//                    SeekController.changeSeekInPage(context, arrayList.get(positions), seekBar.getProgress());
//                }
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {//끝
//                notifyItemChanged(positions);
//                notifyDataSetChanged();
//                SeekController.pageMoving = false;
//            }
//        });
//
//        holder.download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences = context.getSharedPreferences("download_dialog_checkbox", MODE_PRIVATE);
//                boolean isChecked = sharedPreferences.getBoolean("isChecked", false);
//                if (isChecked) {
//                    openDownloadService(context, holder.progressBar, holder.img, holder.download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
//                    DownloadItem downloadItem = new DownloadItem(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
//                    DownloadService.downloadList.add(downloadItem);
//                    DownloadService.setOnClickDownload(context, holder.progressBar, holder.img, holder.download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition(), holder.seekBar, downloadItem);
//                } else {
//                    AskDownloadDialog.askDownloadDialog(context, holder.progressBar, holder.img, holder.download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition(), holder.seekBar);
//                }
//            }
//        });
//
//        holder.pro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, PremiumDialog.class));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Page123ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        SeekBar seekBar;
        ImageView download, pro;
        ProgressBar progressBar;
        TextView name;

        public Page123ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.page_item_img);
            this.seekBar = itemView.findViewById(R.id.page_item_seekbar);
            this.download = itemView.findViewById(R.id.page_item_download);
            this.pro = itemView.findViewById(R.id.page_item_pro);
            this.progressBar = itemView.findViewById(R.id.page_item_progress);
            this.name = itemView.findViewById(R.id.page_item_name);
        }
    }

    public static class Page4ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        SeekBar seekBar;
        ImageView download, pro;
        ProgressBar progressBar;
        TextView name;

        public Page4ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.page_item4_img);
            this.seekBar = itemView.findViewById(R.id.page_item4_seekbar);
            this.download = itemView.findViewById(R.id.page_item4_download);
            this.pro = itemView.findViewById(R.id.page_item4_pro);
            this.progressBar = itemView.findViewById(R.id.page_item4_progress);
            this.name = itemView.findViewById(R.id.page_item4_name);
        }
    }

    public static class Page567ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        SeekBar seekBar;
        ImageView download, pro;
        ProgressBar progressBar;
        TextView name;

        public Page567ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.page_item567_img);
            this.seekBar = itemView.findViewById(R.id.page_item567_seekbar);
            this.download = itemView.findViewById(R.id.page_item567_download);
            this.pro = itemView.findViewById(R.id.page_item567_pro);
            this.progressBar = itemView.findViewById(R.id.page_item567_progress);
            this.name = itemView.findViewById(R.id.page_item567_name);
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

    private int checkPlayinglistPosition(int page) {//같은페이지에 있는 다른 재생중인 트랙의 bottomSheetPlayList에서의 포지션 알아오기
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
                    case Configuration.UI_MODE_NIGHT_NO://system light 모드
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark 모드
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
                    case Configuration.UI_MODE_NIGHT_NO://system light 모드
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark 모드
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
                    case Configuration.UI_MODE_NIGHT_NO://system light 모드
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark 모드
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
                    case Configuration.UI_MODE_NIGHT_NO://system light 모드
                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(arrayList.get(position).getImgdefault(), 0, arrayList.get(position).getImgdefault().length);
                        img.setImageBitmap(bitmap1);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES://system dark 모드
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
        if (arrayList.get(positions).getIsplay() == 1) {//해당 아이템이 playing중이 아닐때
            setPageImageOnClickChangeImage(positions, img);
            for (int i = 0; i < arrayList.size(); i++) {//같은 page에 재생중인게 있으면 없애기
                if (arrayList.get(i).getIsplay() == 2) {//같은page에 재생중인게 있으면
                    int index = checkPlayinglistPosition(arrayList.get(i).getPage());//같은페이지에 있는 다른 재생중인 트랙의 bottomSheetPlayList에서의 포지션 알아오기
                    MainActivity.bottomSheetPlayList.remove(index);//같은페이지에 있는 다른 재생중인 트랙 지우고
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(index);//새로고침
                    arrayList.get(i).setIsplay(1);//page에 있는 지운트랙 isplay 1로 바꾸고
                    notifyItemChanged(i);//해당page 새로고침
                    notifyDataSetChanged();
                    AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(i).getPnp());//지운트랙 재생정지
                    break;
                }
            }
            //add 해당 아이템 재생목록에 추가
            MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
            arrayList.get(positions).setIsplay(2);//새로 재생시킬 트랙 isplay 2로 바꾸기
            MainActivity.bottomSheetPlayList.add(arrayList.get(positions));//bottom playlist에 새로 재생할거 넣기
            databaseHandler.setPlay1(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db에서 기존꺼,새로운거 isplay바꾸고 bottom list에도 새로운걸로 변경
            MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());//bottom list 새로고침

            if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0), context)) {//다른page에 이미 재생중인게 있을때 (pands버튼이 재생중일때)
                AudioController.startTrack(context, arrayList.get(positions));//새로 재생할 트랙 찾아서 다같이 재생
            } else {//재생중인게 없을때(pands버튼이 재생 중이 아닐때)
                List<PageItem> pp = new ArrayList<>();
                for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {//bottom list에 모든 트랙 pnp 수집
                    pp.add(MainActivity.bottomSheetPlayList.get(ii));
                    if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                        AudioController.startPlayingList(context, pp);//bottom list에 있는 목록 다 재생
                    }
                }
            }
            checkOpenService();
        } else {//해당 아이템이 playing중일때
            //remove
            setPageImageOnClickChangeImage(positions, img);
            databaseHandler.deletePlayingList(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db bottom list에서 지우고 page db에 isplay 1로 변경
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                if (MainActivity.bottomSheetPlayList.get(i).getPnp().equals(arrayList.get(positions).getPnp())) {//bottom list에 있는 트랙이랑 누른트랙이랑 같을때
                    MainActivity.bottomSheetPlayList.remove(i);
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(i);//지우고 새로고침
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
                arrayList.get(positions).setIsplay(2);//새로 재생시킬 트랙 isplay 2로 바꾸기
                MainActivity.bottomSheetPlayList.add(arrayList.get(positions));//bottom playlist에 새로 재생할거 넣기
                databaseHandler.setIsPlayPage4(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db에서 기존꺼,새로운거 isplay바꾸고 bottom list에도 새로운걸로 변경
                MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());//bottom list 새로고침

                if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0), context)) {//다른page에 이미 재생중인게 있을때 (pands버튼이 재생중일때)
                    AudioController.startTrack(context, arrayList.get(positions));//새로 재생할 트랙 찾아서 재생
                } else {//재생중인게 없을때(pands버튼이 재생 중이 아닐때)
                    List<PageItem> pp = new ArrayList<>();
                    for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {//bottom list에 모든 트랙 pnp 수집
                        pp.add(MainActivity.bottomSheetPlayList.get(ii));
                        if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                            AudioController.startPlayingList(context, pp);//bottom list에 있는 목록 다 재생
                        }
                    }
                }
            } else {
                Toast.makeText(context, "limit 3 over", Toast.LENGTH_SHORT).show();
            }
        } else {
            setPageImageOnClickChangeImage(positions, img);
            databaseHandler.deletePlayingList(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db bottom list에서 지우고 page db에 isplay 1로 변경
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
                if (MainActivity.bottomSheetPlayList.get(i).getPnp().equals(arrayList.get(positions).getPnp())) {//bottom list에 있는 트랙이랑 누른트랙이랑 같을때
                    MainActivity.bottomSheetPlayList.remove(i);
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(i);//지우고 새로고침
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
            }
            if (page == 5 || page == 6 || page == 7) {
                img.setImageBitmap(databaseHandler.getPageicon("circlepro"));
            }
        } else {
            img.setImageBitmap(databaseHandler.getPageicon("pro"));
        }
    }

    private void setBackgroundDownload(int page, ImageView img) {
        if (page != 1 && page != 2 && page != 3) {
            if (page == 4) {
                img.setImageBitmap(databaseHandler.getPageicon("download"));
            }
            if (page == 5 || page == 6 || page == 7) {
                img.setImageBitmap(databaseHandler.getPageicon("circledownload"));
            }
        } else {
            img.setImageBitmap(databaseHandler.getPageicon("download"));
        }
    }

    private void setViewHolder(int viewTypeCode, int positions, ImageView img, ImageView download, ImageView pro, SeekBar seekBar, ProgressBar progressBar, TextView name) {
        setPageImageTheme(positions, img);

        if (viewTypeCode == ViewTypeCode.ViewType.PAGE123) {
            setImageSizeCode0(img, download, pro);
        } else if (viewTypeCode == ViewTypeCode.ViewType.PAGE4) {
            setImageSizeCode1(img, download, pro);
        } else {
            setImageSizeCode2(img, download, pro);
        }

        progressBar.setVisibility(View.GONE);

        if (databaseHandler.getIsProUser() == 1) {
            if (arrayList.get(positions).getIspro() == 2) {
                download.setVisibility(View.GONE);
                download.setEnabled(false);
                img.setEnabled(false);
                seekBar.setEnabled(false);
                pro.setEnabled(true);
                setBackgroundPro(arrayList.get(positions).getPage(), pro);
            } else if (arrayList.get(positions).getIspro() == 1) {
                if (arrayList.get(positions).getNeeddownload() == 1) {
                    pro.setVisibility(View.GONE);
                    pro.setEnabled(false);
                    download.setVisibility(View.GONE);
                    download.setEnabled(false);
                    img.setEnabled(true);
                    seekBar.setEnabled(true);
                } else if (arrayList.get(positions).getNeeddownload() == 2) {
                    File file = new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(positions).getPage() + "to" + arrayList.get(positions).getPosition() + ".mp3");
                    if (file.exists()) {
                        download.setVisibility(View.GONE);
                        download.setEnabled(false);
                        img.setEnabled(true);
                        seekBar.setEnabled(true);
                    } else {
                        setBackgroundDownload(arrayList.get(positions).getPage(), download);
                        download.setVisibility(View.VISIBLE);
                        download.setEnabled(true);
                        img.setEnabled(false);
                        seekBar.setEnabled(false);
                    }
                }
            }
        } else if (databaseHandler.getIsProUser() == 2) {
            pro.setVisibility(View.GONE);
            pro.setEnabled(false);
            if (arrayList.get(positions).getNeeddownload() == 1) {
                download.setVisibility(View.GONE);
                download.setEnabled(false);
                img.setEnabled(true);
                seekBar.setEnabled(true);
            } else if (arrayList.get(positions).getNeeddownload() == 2) {
                if (new File(context.getApplicationInfo().dataDir + "/cache/audio" + arrayList.get(positions).getPage() + "to" + arrayList.get(positions).getPosition() + ".mp3").exists()) {
                    download.setVisibility(View.GONE);
                    seekBar.setEnabled(true);
                } else {
                    setBackgroundDownload(arrayList.get(positions).getPage(), download);
                    download.setVisibility(View.VISIBLE);
                    download.setEnabled(true);
                    img.setEnabled(false);
                    seekBar.setEnabled(false);
                }
            }
        }

        name.setText(arrayList.get(positions).getName());
        seekBar.setProgress(arrayList.get(positions).getSeek());

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(positions).getPage() != 4) {//1,2,3page
                    page(positions, img);
                } else {//4page nature일때
                    page4(positions, img);
                }
                page4Count();
            }
        });

        seekBar.setMax(MainActivity.maxVolumn);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("download_dialog_checkbox", MODE_PRIVATE);
                boolean isChecked = sharedPreferences.getBoolean("isChecked", false);
                if (isChecked) {
                    openDownloadService(context, progressBar, img, download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    DownloadItem downloadItem = new DownloadItem(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());
                    DownloadService.downloadList.add(downloadItem);
                    DownloadService.setOnClickDownload(context, progressBar, img, download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition(), seekBar, downloadItem);
                } else {
                    AskDownloadDialog.askDownloadDialog(context, progressBar, img, download, arrayList.get(positions).getPage(), arrayList.get(positions).getPosition(), seekBar);
                }
            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PremiumDialog.class));
            }
        });
    }

    private void setImageSizeCode0(ImageView img, ImageView download, ImageView pro) {
        img.setMinimumWidth(MainActivity.pageitem_code0_width_size);
        img.setMinimumHeight(MainActivity.pageitem_code0_height_size);
        download.setMinimumWidth(MainActivity.pageitem_code0_width_size);
        download.setMinimumHeight(MainActivity.pageitem_code0_height_size);
        pro.setMinimumWidth(MainActivity.pageitem_code0_width_size);
        pro.setMinimumHeight(MainActivity.pageitem_code0_height_size);
    }

    private void setImageSizeCode1(ImageView img, ImageView download, ImageView pro) {
        img.setMinimumWidth(MainActivity.pageitem_code1_width_size);
        img.setMinimumHeight(MainActivity.pageitem_code1_height_size);
        download.setMinimumWidth(MainActivity.pageitem_code1_width_size);
        download.setMinimumHeight(MainActivity.pageitem_code1_height_size);
        pro.setMinimumWidth(MainActivity.pageitem_code1_width_size);
        pro.setMinimumHeight(MainActivity.pageitem_code1_height_size);
    }

    private void setImageSizeCode2(ImageView img, ImageView download, ImageView pro) {
        img.setMinimumWidth(MainActivity.pageitem_code1_width_size);
        img.setMinimumHeight(MainActivity.pageitem_code1_height_size);
        download.setMinimumWidth(MainActivity.pageitem_code1_width_size);
        download.setMinimumHeight(MainActivity.pageitem_code1_height_size);
        pro.setMinimumWidth(MainActivity.pageitem_code1_width_size);
        pro.setMinimumHeight(MainActivity.pageitem_code1_height_size);
    }
}
