package com.dippola.relaxtour.pages.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.dialog.AskDownloadsDialog;
import com.dippola.relaxtour.dialog.ConstainProTrackDialog;
import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
import com.dippola.relaxtour.dialog.EditFavTitleDialog;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.FavTitleItem;
import com.dippola.relaxtour.pages.item.PageItem;

import java.io.File;
import java.util.ArrayList;

public class FavTitleAdapter extends RecyclerView.Adapter<FavTitleAdapter.CustomViewHolder> {
    ArrayList<FavTitleItem> arrayList;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    public static FavListAdapter favListAdapter;

    public FavTitleAdapter(ArrayList<FavTitleItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavTitleAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_page_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavTitleAdapter.CustomViewHolder holder, int position) {
        int i = position;

        holder.title.setText(arrayList.get(position).getTitle());

        if (arrayList.get(i).getIsopen() == 1) {
            holder.recyclerView.setVisibility(View.GONE);
            holder.uandd.setChecked(false);
            holder.uandd.setBackgroundResource(R.drawable.fav_page_item_down);
        } else {
            holder.recyclerView.setVisibility(View.VISIBLE);
            holder.uandd.setChecked(true);
            holder.uandd.setBackgroundResource(R.drawable.fav_page_item_up);
        }

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.load.setVisibility(View.VISIBLE);
                checkDownloadAready(arrayList.get(i).getTitle(), i);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditFavTitleDialog.editFavTitleDialog(context, arrayList.get(i).getTitle(), i);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFavTitleDialog.deleteFavTitleDialog(context, arrayList.get(i).getTitle());
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrayList.get(i).getIsopen() == 1) {
                    ArrayList<FavListItem> favListItemArrayList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }
                    arrayList.get(i).setIsopen(2);

                    favListAdapter = new FavListAdapter(favListItemArrayList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                } else {
                    ArrayList<FavListItem> favListItemArrayList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }

                    favListAdapter = new FavListAdapter(favListItemArrayList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                }


                notifyItemRangeChanged(0, arrayList.size() - 1);
                notifyDataSetChanged();
            }
        });

        holder.uandd.setChecked(false);
        holder.uandd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrayList.get(i).getIsopen() == 1) {
                    ArrayList<FavListItem> favListItemArrayList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }
                    arrayList.get(i).setIsopen(2);

                    favListAdapter = new FavListAdapter(favListItemArrayList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                } else {
                    ArrayList<FavListItem> favListItemArrayList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }

                    favListAdapter = new FavListAdapter(favListItemArrayList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                }


                notifyItemRangeChanged(0, arrayList.size() - 1);
                notifyDataSetChanged();
            }
        });

//        holder.uandd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
////                    holder.uandd.setBackgroundResource(R.drawable.bottom_up);
//                    holder.uandd.setBackgroundResource(R.drawable.bottom_down);
//                } else {
//                    holder.uandd.setBackgroundResource(R.drawable.bottom_up);
////                    holder.uandd.setBackgroundResource(R.drawable.bottom_down);
//                }
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button play, edit, delete;
        CheckBox uandd;
        RecyclerView recyclerView;
        LinearLayout linearLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.fav_page_item_title);
            this.play = itemView.findViewById(R.id.fav_page_item_play);
            this.edit = itemView.findViewById(R.id.fav_page_item_edit);
            this.delete = itemView.findViewById(R.id.fav_page_item_delete);
            this.uandd = itemView.findViewById(R.id.fav_page_item_uandd);
            this.recyclerView = itemView.findViewById(R.id.fav_page_inside_recyclerview);
            this.linearLayout = itemView.findViewById(R.id.fav_page_item_linear);
            this.recyclerView.setVisibility(View.GONE);
            if (this.uandd.isChecked()) {
                this.recyclerView.setVisibility(View.VISIBLE);
            } else {
                this.recyclerView.setVisibility(View.GONE);
            }
        }
    }

    private void changePageImage(int page, int position) {
        if (page == 1 && RainPage.arrayList.size() != 0) {
            RainPage.arrayList.get(position).setIsplay(2);
            RainPage.adapter.notifyItemChanged(position);
            RainPage.adapter.notifyDataSetChanged();
        } else if (page == 2 && WaterPage.arrayList.size() != 0) {
            WaterPage.arrayList.get(position).setIsplay(2);
            WaterPage.adapter.notifyItemChanged(position);
            WaterPage.adapter.notifyDataSetChanged();
        } else if (page == 3 && WindPage.arrayList.size() != 0) {
            WindPage.arrayList.get(position).setIsplay(2);
            WindPage.adapter.notifyItemChanged(position);
            WindPage.adapter.notifyDataSetChanged();
        } else if (page == 4 && NaturePage.arrayList.size() != 0) {
            NaturePage.arrayList.get(position).setIsplay(2);
            NaturePage.adapter.notifyItemChanged(position);
            NaturePage.adapter.notifyDataSetChanged();
        } else if (page == 5 && ChakraPage.arrayList.size() != 0) {
            ChakraPage.arrayList.get(position).setIsplay(2);
            ChakraPage.adapter.notifyItemChanged(position);
            ChakraPage.adapter.notifyDataSetChanged();
        } else if (page == 6 && MantraPage.arrayList.size() != 0) {
            MantraPage.arrayList.get(position).setIsplay(2);
            MantraPage.adapter.notifyItemChanged(position);
            MantraPage.adapter.notifyDataSetChanged();
        } else if (page == 7 && HzPage.arrayList.size() != 0) {
            HzPage.arrayList.get(position).setIsplay(2);
            HzPage.adapter.notifyItemChanged(position);
            HzPage.adapter.notifyDataSetChanged();
        }
    }

    private void checkDownloadAready(String title, int position) {
        ArrayList<FavListItem> favListItems = new ArrayList<>();
        favListItems = MainActivity.databaseHandler.getFavListItem(title);
//        for (int i = 0; i < favListItems.size(); i++) {
//            if (favListItems.get(i).getNeeddownload() == 2) {
//                String path = context.getApplicationInfo().dataDir + "/cache/audio" + favListItems.get(i).getPnp() + ".mp3";
//                File file = new File(path);
//                if (!file.exists()) {
//                    pnps.add(favListItems.get(i).getPnp());
//                }
//            }
//            if (i == favListItems.size() - 1) {
//                if (pnps.size() != 0) {
//                    AskDownloadsDialog.askDownloadsDialog(context, pnps, position);
//                } else {
//                    favListPlay(position);
//                }
//            }
//        }

        if (checkWillShowConstainProTrackDialog(favListItems)) {
            ConstainProTrackDialog.showDialog(context);
        } else {
            if (checkNeedDownload(favListItems).size() != 0) {
                AskDownloadsDialog.askDownloadsDialog(context, checkNeedDownload(favListItems), position);
            } else {
                favListPlay(position);
            }
        }
    }

    private boolean checkWillShowConstainProTrackDialog(ArrayList<FavListItem> favListItems) {
        boolean needShow = false;
        for (int i = 0; i < favListItems.size(); i++) {
            if (favListItems.get(i).getIspro() == 2 && MainActivity.databaseHandler.getIsProUser() == 1) {
                needShow = true;
                break;
            }
        }
        return needShow;
    }

    private ArrayList<String> checkNeedDownload(ArrayList<FavListItem> favListItems) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < favListItems.size(); i++) {
            if (favListItems.get(i).getNeeddownload() == 2) {
                String path = context.getApplicationInfo().dataDir + "/cache/audio" + favListItems.get(i).getPnp() + ".mp3";
                File file = new File(path);
                if (!file.exists()) {
                    list.add(favListItems.get(i).getPnp());
                }
            }
        }
        return list;
    }

    public void favListPlay(int position) {
        if (MainActivity.bottomSheetPlayList.size() != 0) {//만약 playinglist에 재생목록이 있다면
            ArrayList<Integer> pagelist = new ArrayList<>();
            ArrayList<Integer> positionlist = new ArrayList<>();
            for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {
                pagelist.add(MainActivity.bottomSheetPlayList.get(ii).getPage());
                positionlist.add(MainActivity.bottomSheetPlayList.get(ii).getPosition());
                AudioController.stopPage(MainActivity.bottomSheetPlayList.get(ii).getPage(), MainActivity.bottomSheetPlayList.get(ii).getPnp());
                if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                    MainActivity.bottomSheetPlayList.clear();
                    MainActivity.bottomSheetAdapter.notifyItemRangeRemoved(0, MainActivity.bottomSheetPlayList.size() - 1);
                    MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                    MainActivity.databaseHandler.deleteAllPlayinglist(pagelist, positionlist, arrayList.get(position).getTitle());
                }
            }
        } else {//playinglist에 기존목록 없다면
            MainActivity.databaseHandler.addFavListInPlayinglist(arrayList.get(position).getTitle());
        }
        ArrayList<PageItem> pageItems = new ArrayList<>();
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
            pageItems.add(MainActivity.bottomSheetPlayList.get(i));
            changePageImage(MainActivity.bottomSheetPlayList.get(i).getPage(), MainActivity.bottomSheetPlayList.get(i).getPosition() - 1);
            if (i == MainActivity.bottomSheetPlayList.size() - 1) {
                AudioController.startPlayingList(context, pageItems);
                AudioController.checkOpenService(context);
                MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
            }
        }
        PageAdapter.page4Count();
    }
}
