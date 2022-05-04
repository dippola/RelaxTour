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
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;
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

        if (arrayList.get(position).getPage() == 1 || arrayList.get(position).getPage() == 2 || arrayList.get(position).getPage() == 3) {//1,2,3page
            holder.img.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.img.setMinimumHeight(MainActivity.pageitem_height_size);
            holder.download.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.download.setMinimumHeight(MainActivity.pageitem_height_size);
            holder.download.setMaxHeight(MainActivity.pageitem_height_size);
            holder.download.bringToFront();
            holder.progressBar.setMinimumWidth(MainActivity.pageitem_width_size);
            holder.progressBar.setMinimumHeight(MainActivity.pageitem_height_size);
        } else if (arrayList.get(position).getPage() == 4) {//4page
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


        holder.name.setText(arrayList.get(position).getName());
        holder.seekBar.setProgress(arrayList.get(position).getSeek());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(positions).getPage() == 1 || arrayList.get(positions).getPage() == 2 || arrayList.get(positions).getPage() == 3) {//1,2,3page
                    page123(positions, holder.img);
                } else if (arrayList.get(positions).getPage() == 4) {//4page nature일때
                    page4(positions, holder.img);
                } else {

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
        TextView name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.page_item_img);
            this.seekBar = itemView.findViewById(R.id.page_item_seekbar);
            this.download = itemView.findViewById(R.id.page_item_download);
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

    private void page123(int positions, ImageView img) {
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
                    AudioController.stopPage(arrayList.get(positions).getPage(), arrayList.get(positions).getPnp());//지운트랙 재생정지
                    break;
                }
            }
            //add 해당 아이템 재생목록에 추가
            MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
            arrayList.get(positions).setIsplay(2);//새로 재생시킬 트랙 isplay 2로 바꾸기
            MainActivity.bottomSheetPlayList.add(arrayList.get(positions));//bottom playlist에 새로 재생할거 넣기
            databaseHandler.setPlay1(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db에서 기존꺼,새로운거 isplay바꾸고 bottom list에도 새로운걸로 변경
            MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());//bottom list 새로고침

            if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0).getPnp())) {//다른page에 이미 재생중인게 있을때 (pands버튼이 재생중일때)
                AudioController.startTrack(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//새로 재생할 트랙 찾아서 재생
            } else {//재생중인게 없을때(pands버튼이 재생 중이 아닐때)
                List<String> pp = new ArrayList<>();
                for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {//bottom list에 모든 트랙 pnp 수집
                    pp.add(MainActivity.bottomSheetPlayList.get(ii).getPnp());
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
                databaseHandler.setPlay1(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//db에서 기존꺼,새로운거 isplay바꾸고 bottom list에도 새로운걸로 변경
                MainActivity.bottomSheetAdapter.notifyItemInserted(MainActivity.bottomSheetPlayList.size());//bottom list 새로고침

                if (AudioController.checkIsPlaying(MainActivity.bottomSheetPlayList.get(0).getPnp())) {//다른page에 이미 재생중인게 있을때 (pands버튼이 재생중일때)
                    AudioController.startTrack(arrayList.get(positions).getPage(), arrayList.get(positions).getPosition());//새로 재생할 트랙 찾아서 재생
                } else {//재생중인게 없을때(pands버튼이 재생 중이 아닐때)
                    List<String> pp = new ArrayList<>();
                    for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {//bottom list에 모든 트랙 pnp 수집
                        pp.add(MainActivity.bottomSheetPlayList.get(ii).getPnp());
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
}
