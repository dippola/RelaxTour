package com.dippola.relaxtour.setting;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.notification.NotificationService;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.PageItem;

import java.io.File;
import java.util.ArrayList;

public class StorageManageAdapter extends  RecyclerView.Adapter<StorageManageAdapter.CustomViewHolder> {
    ArrayList<PageItem> arrayList = new ArrayList<>();
    Context context;

    public StorageManageAdapter(Context context, ArrayList<PageItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storage_manage_dialog_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        int i = position;
        holder.page.setText(getPageName(arrayList.get(position).getPage()));
        holder.position.setText(arrayList.get(position).getName());
        setImage(holder.img, position);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("storage_manage_checkbox", MODE_PRIVATE);
                boolean isChecked = sharedPreferences.getBoolean("isChecked", false);
                if (isChecked) {
                    StorageManageDialog.progressBar.setVisibility(View.VISIBLE);
                    String path = context.getApplicationInfo().dataDir + "/cache/" + "audio" + arrayList.get(i).getTid() + ".mp3";
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                        PageItem pageItem = arrayList.get(i);
                        int index = arrayList.indexOf(arrayList.get(i));
                        arrayList.remove(index);
                        notifyItemRemoved(index);
                        notifyDataSetChanged();
                        Log.d(">>>MainActivity", "deleted");
                        resetPage(pageItem, context);
                        if (arrayList.size() == 0) {
                            StorageManageDialog.nullScreen.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.d(">>>MainActivity", "no have");
                    }
                } else {
                    StorageManageDialogAskSure.storageManageDialogAskSure(context, arrayList, StorageManageAdapter.this, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView page;
        TextView position;
        Button deleteBtn;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.storage_manage_dialog_item_img);
            this.page = itemView.findViewById(R.id.storage_manage_dialog_item_page);
            this.position = itemView.findViewById(R.id.storage_manage_dialog_item_position);
            this.deleteBtn = itemView.findViewById(R.id.storage_manage_dialog_item_delete_btn);
        }
    }

    public static void resetPage(PageItem pageItem, Context context) {
        if (pageItem.getPage() == 1 && RainPage.arrayList.size() != 0) {
            RainPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 2 && WaterPage.arrayList.size() != 0) {
            WaterPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 3 && WindPage.arrayList.size() != 0) {
            WindPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 4 && NaturePage.arrayList.size() != 0) {
            NaturePage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 5 && ChakraPage.arrayList.size() != 0) {
            ChakraPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 6 && MantraPage.arrayList.size() != 0) {
            MantraPage.adapter.notifyDataSetChanged();
        } else if (pageItem.getPage() == 7 && HzPage.arrayList.size() != 0) {
            HzPage.adapter.notifyDataSetChanged();
        }
        deleteInBottomPlayList(pageItem, context);
    }

    public static void deleteInBottomPlayList(PageItem pageItem, Context context) {
        if (MainActivity.bottomSheetPlayList.size() != 0) {
            for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {//storage에서 삭제할때 playlist에 있으면 지우기
                if (MainActivity.bottomSheetPlayList.get(i).getTid().equals(pageItem.getTid())) {
                    MainActivity.databaseHandler.deletePlayingList(pageItem.getTid());
                    AudioController.stopPage(pageItem.getPage(), pageItem.getPosition());
                    int index = MainActivity.bottomSheetPlayList.indexOf(MainActivity.bottomSheetPlayList.get(i));
                    MainActivity.bottomSheetPlayList.remove(index);
                    MainActivity.bottomSheetAdapter.notifyItemRemoved(index);
                    MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                    if (MainActivity.bottomSheetPlayList.size() == 0) {
                        stopServiceWhenPlaylistZero(context);
                    }
                    changePageItemBackground(pageItem.getPage(), pageItem.getPosition());
                }
            }
        }
        Toast.makeText(context, "Successed delete sound.", Toast.LENGTH_SHORT).show();
        StorageManageDialog.progressBar.setVisibility(View.GONE);
    }

    public static void stopServiceWhenPlaylistZero(Context context) {
        if (MainActivity.bottomSheetPlayList.size() == 0) {
            MainActivity.pands.setBackgroundResource(R.drawable.bottom_sheet_play);
            if (NotificationService.isPlaying) {
                context.stopService(new Intent(context, NotificationService.class));
            }
        }
    }

    public static void changePageItemBackground(int page, int position) {
        if (page == 1 && RainPage.arrayList.size() != 0) {
            RainPage.arrayList.get(position - 1).setIsplay(1);
            RainPage.adapter.notifyItemChanged(position - 1);
//            Page1.adapter.notifyDataSetChanged();
        } else if (page == 2 && WaterPage.arrayList.size() != 0) {
            WaterPage.arrayList.get(position - 1).setIsplay(1);
            WaterPage.adapter.notifyItemChanged(position - 1);
//            Page2.adapter.notifyDataSetChanged();
        } else if (page == 3 && WindPage.arrayList.size() != 0) {
            WindPage.arrayList.get(position - 1).setIsplay(1);
            WindPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 4 && NaturePage.arrayList.size() != 0) {
            NaturePage.arrayList.get(position - 1).setIsplay(1);
            NaturePage.adapter.notifyItemChanged(position - 1);
        } else if (page == 5 && ChakraPage.arrayList.size() != 0) {
            ChakraPage.arrayList.get(position - 1).setIsplay(1);
            ChakraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 6 && MantraPage.arrayList.size() != 0) {
            MantraPage.arrayList.get(position - 1).setIsplay(1);
            MantraPage.adapter.notifyItemChanged(position - 1);
        } else if (page == 7 && HzPage.arrayList.size() != 0) {
            HzPage.arrayList.get(position - 1).setIsplay(1);
            HzPage.adapter.notifyItemChanged(position - 1);
        }
    }

    private void setImage(ImageView img, int position) {
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
        } else {
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

    public static String getPageName(int page) {
        if (page == 1) {
            return "Rain";
        } else if (page == 2) {
            return "Water";
        } else if (page == 3) {
            return "Wind";
        } else if (page == 4) {
            return "Nature";
        } else if (page == 5) {
            return "chakra";
        } else if (page == 6) {
            return "Mantra";
        } else if (page == 7) {
            return "hz";
        } else {
            return "";
        }
    }
}
