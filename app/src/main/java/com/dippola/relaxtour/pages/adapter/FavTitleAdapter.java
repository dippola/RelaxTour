package com.dippola.relaxtour.pages.adapter;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.MainActivity;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.controller.AudioController;
import com.dippola.relaxtour.dialog.AskDownloadsDialog;
import com.dippola.relaxtour.dialog.DeleteFavTitleDialog;
import com.dippola.relaxtour.dialog.Premium;
import com.dippola.relaxtour.dialog.RestoreDialog;
import com.dippola.relaxtour.pages.ChakraPage;
import com.dippola.relaxtour.pages.FavPage;
import com.dippola.relaxtour.pages.HzPage;
import com.dippola.relaxtour.pages.MantraPage;
import com.dippola.relaxtour.pages.NaturePage;
import com.dippola.relaxtour.pages.RainPage;
import com.dippola.relaxtour.pages.WaterPage;
import com.dippola.relaxtour.pages.WindPage;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.FavTitleItem;
import com.dippola.relaxtour.pages.item.PageItem;
import com.qonversion.android.sdk.Qonversion;
import com.qonversion.android.sdk.dto.QEntitlement;
import com.qonversion.android.sdk.dto.QonversionError;
import com.qonversion.android.sdk.listeners.QonversionEntitlementsCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Keep
public class FavTitleAdapter extends RecyclerView.Adapter<FavTitleAdapter.CustomViewHolder> {
    ArrayList<FavTitleItem> arrayList;
    Context context;
    Activity activity;
    RecyclerView.LayoutManager layoutManager;
    public static FavListAdapter favListAdapter;

    public FavTitleAdapter(ArrayList<FavTitleItem> arrayList, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FavTitleAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_page_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        ViewGroup.LayoutParams params = holder.contr.getLayoutParams();
        params.width = (int) (parent.getWidth() * 0.4);
        holder.contr.setLayoutParams(params);
        ViewGroup.LayoutParams params1 = holder.editlayout.getLayoutParams();
        params.width = (int) (parent.getWidth() * 0.4);
        holder.editlayout.setLayoutParams(params);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavTitleAdapter.CustomViewHolder holder, int position) {
        int i = position;

        holder.title.setText(arrayList.get(position).getTitle());

        if (arrayList.get(i).getIsopen() == 1) {
            collapse(holder.hide);
            holder.uandd.setChecked(false);
            holder.uandd.setBackgroundResource(R.drawable.fav_page_item_down);
        } else {
            expand(holder.hide);
            holder.uandd.setChecked(true);
            holder.uandd.setBackgroundResource(R.drawable.fav_page_item_up);
        }

        if (arrayList.get(i).getIsedit() == 1) {
            holder.contr.setVisibility(View.VISIBLE);
            holder.editlayout.setVisibility(View.INVISIBLE);
            holder.title.setVisibility(View.VISIBLE);
            holder.editText.setVisibility(View.INVISIBLE);
            holder.play.setVisibility(View.VISIBLE);
            holder.linearLayout.setEnabled(true);
            holder.uandd.setEnabled(true);
            holder.editBtn.setEnabled(true);
            holder.delete.setEnabled(true);
            holder.editok.setEnabled(false);
            holder.editcancel.setEnabled(false);
        } else {
            holder.contr.setVisibility(View.INVISIBLE);
            holder.editlayout.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.INVISIBLE);
            holder.play.setVisibility(View.GONE);
            holder.editText.setVisibility(View.VISIBLE);
            holder.editText.setText(arrayList.get(position).getTitle());
            holder.linearLayout.setEnabled(false);
            holder.uandd.setEnabled(false);
            holder.editBtn.setEnabled(false);
            holder.delete.setEnabled(false);
            holder.editok.setEnabled(true);
            holder.editcancel.setEnabled(true);
        }

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.load.setVisibility(View.VISIBLE);
                checkDownloadAready(arrayList.get(i).getTitle());
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EditFavTitleDialog.editFavTitleDialog(context, arrayList.get(i).getTitle(), i);
                if (arrayList.get(i).getIsedit() == 1) {
                    arrayList.get(i).setIsedit(2);
                    MainActivity.databaseHandler.changeFavTitleIsEdit(arrayList.get(i).getTitle(), 2);
                    holder.linearLayout.setEnabled(false);
                    holder.uandd.setEnabled(false);
                    holder.title.setVisibility(View.INVISIBLE);
                    holder.play.setVisibility(View.GONE);
                    holder.editText.setVisibility(View.VISIBLE);
                    holder.editText.setText(arrayList.get(i).getTitle());
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.fav_edit_close);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            holder.contr.setVisibility(View.INVISIBLE);
                            holder.editBtn.setEnabled(false);
                            holder.delete.setEnabled(false);
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.fav_edit_show);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    holder.editlayout.setVisibility(View.VISIBLE);
                                    holder.editok.setEnabled(true);
                                    holder.editcancel.setEnabled(true);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            holder.editlayout.startAnimation(anim);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    holder.contr.startAnimation(animation);
                    favListAdapter.notifyDataSetChanged();
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFavTitleDialog.deleteFavTitleDialog(context, arrayList.get(i).getTitle());
            }
        });

        holder.editok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MainActivity.databaseHandler.isContainsTitleAlreadyWhenEditTitle(arrayList.get(i).getTitle(), holder.editText.getText().toString())) {
                    InputMethodManager manager = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
                    if (manager.isAcceptingText()) {
                        manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    if (!arrayList.get(i).getTitle().equals(holder.editText.getText().toString())) {
                        for (int i = 0; i < FavListAdapter.editList.size(); i++) {
                            FavListAdapter.editList.get(i).setFavtitlename(holder.editText.getText().toString());
                        }
                    }
                    MainActivity.databaseHandler.changeFavListToEdit(arrayList.get(i).getTitle(), holder.editText.getText().toString(), FavListAdapter.editList);
                    arrayList.get(i).setTitle(holder.editText.getText().toString());
                    arrayList.get(i).setIsedit(1);
                    MainActivity.databaseHandler.changeFavTitleIsEdit(arrayList.get(i).getTitle(), 1);
                    holder.title.setVisibility(View.VISIBLE);
                    holder.editText.setVisibility(View.INVISIBLE);
                    holder.play.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.fav_edit_close);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            holder.editlayout.setVisibility(View.INVISIBLE);
                            holder.editok.setEnabled(false);
                            holder.editcancel.setEnabled(false);
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.fav_edit_show);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    holder.contr.setVisibility(View.VISIBLE);
                                    holder.linearLayout.setEnabled(true);
                                    holder.uandd.setEnabled(true);
                                    holder.editBtn.setEnabled(true);
                                    holder.delete.setEnabled(true);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            holder.contr.startAnimation(anim);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    holder.editlayout.startAnimation(animation);

                    holder.title.setText(holder.editText.getText().toString());

                    ArrayList<FavListItem> favListItemArrayList;
                    ArrayList<FavListItem> favListItemEditList;
                    favListItemArrayList = FavListAdapter.editList;
                    favListItemEditList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    favListAdapter = new FavListAdapter(favListItemArrayList, favListItemEditList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                    Toast.makeText(context, "The modification is complete.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "'" + holder.editText.getText().toString() + "' A playlist with the same name already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.editcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(i).getIsedit() == 2) {
                    arrayList.get(i).setIsedit(1);
                    MainActivity.databaseHandler.changeFavTitleIsEdit(arrayList.get(i).getTitle(), 1);
                    holder.title.setVisibility(View.VISIBLE);
                    holder.editText.setVisibility(View.INVISIBLE);
                    holder.play.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.fav_edit_close);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            holder.editlayout.setVisibility(View.INVISIBLE);
                            holder.editok.setEnabled(false);
                            holder.editcancel.setEnabled(false);
                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.fav_edit_show);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    holder.contr.setVisibility(View.VISIBLE);
                                    holder.linearLayout.setEnabled(true);
                                    holder.uandd.setEnabled(true);
                                    holder.editBtn.setEnabled(true);
                                    holder.delete.setEnabled(true);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            holder.contr.startAnimation(anim);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    holder.editlayout.startAnimation(animation);
                    favListAdapter.notifyDataSetChanged();
                }

            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < FavPage.favTitleItemArrayList.size(); i++) {
                    if (FavPage.favTitleItemArrayList.get(i).getIsedit() == 2) {
                        FavPage.favTitleItemArrayList.get(i).setIsedit(1);
                        MainActivity.databaseHandler.changeIsOpenWhenFavPageOnPause();
                        break;
                    }
                }

                if (arrayList.get(i).getIsopen() == 1) {
                    ArrayList<FavListItem> favListItemArrayList;
                    ArrayList<FavListItem> favListItemEditList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    favListItemEditList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeFavListIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }
                    arrayList.get(i).setIsopen(2);

                    favListAdapter = new FavListAdapter(favListItemArrayList, favListItemEditList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                } else {
                    ArrayList<FavListItem> favListItemArrayList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeFavListIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }

                    favListAdapter = new FavListAdapter(favListItemArrayList, favListItemArrayList, context);
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
                for (int i = 0; i < FavPage.favTitleItemArrayList.size(); i++) {
                    if (FavPage.favTitleItemArrayList.get(i).getIsedit() == 2) {
                        FavPage.favTitleItemArrayList.get(i).setIsedit(1);
                        MainActivity.databaseHandler.changeIsOpenWhenFavPageOnPause();
                        break;
                    }
                }

                if (arrayList.get(i).getIsopen() == 1) {
                    ArrayList<FavListItem> favListItemArrayList;
                    ArrayList<FavListItem> favListItemEditList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    favListItemEditList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeFavListIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }
                    arrayList.get(i).setIsopen(2);

                    favListAdapter = new FavListAdapter(favListItemArrayList, favListItemEditList, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(favListAdapter);
                } else {
                    ArrayList<FavListItem> favListItemArrayList;
                    favListItemArrayList = MainActivity.databaseHandler.getFavListItem(arrayList.get(i).getTitle());
                    MainActivity.databaseHandler.changeFavListIsOpen(arrayList.get(i).getIsopen(), arrayList.get(i).getTitle());

                    for (int ii = 0; ii < arrayList.size(); ii++) {
                        if (arrayList.get(ii).getIsopen() == 2) {
                            arrayList.get(ii).setIsopen(1);
                        }
                    }

                    favListAdapter = new FavListAdapter(favListItemArrayList, favListItemArrayList, context);
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
        Button play, editBtn, delete, editok, editcancel;
        EditText editText;
        CheckBox uandd;
        RecyclerView recyclerView;
        LinearLayout linearLayout;
        RelativeLayout hide;
        RelativeLayout contr, editlayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.fav_page_item_title);
            this.play = itemView.findViewById(R.id.fav_page_item_play);
            this.editBtn = itemView.findViewById(R.id.fav_page_item_edit);
            this.editlayout = itemView.findViewById(R.id.fav_page_item_edit_layout);
            this.delete = itemView.findViewById(R.id.fav_page_item_delete);
            this.editText = itemView.findViewById(R.id.fav_page_item_edit_title);
            this.editok = itemView.findViewById(R.id.fav_page_item_ok);
            this.editcancel = itemView.findViewById(R.id.fav_page_item_cancel);
            this.uandd = itemView.findViewById(R.id.fav_page_item_uandd);
            this.recyclerView = itemView.findViewById(R.id.fav_page_inside_recyclerview);
            this.linearLayout = itemView.findViewById(R.id.fav_page_item_linear);
            this.contr = itemView.findViewById(R.id.fav_page_item_controller_layout);
            this.hide = itemView.findViewById(R.id.fav_page_item_hide);
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

    private void checkDownloadAready(String title) {
        ArrayList<FavListItem> favListItems = new ArrayList<>();
        favListItems = MainActivity.databaseHandler.getFavListItem(title);

        if (checkWillShowConstainProTrackDialog(favListItems)) {
            Qonversion.getSharedInstance().restore(new QonversionEntitlementsCallback() {
                @Override
                public void onSuccess(@NonNull Map<String, QEntitlement> map) {
                    QEntitlement qPermission = map.get(context.getString(R.string.product_id));
                    if (qPermission != null && qPermission.isActive()) {
                        Log.d("Premium>>>", "restored");
                        RestoreDialog.restoreDialog(context);
                    } else {
                        Log.d("Premium>>>", "no restore");
                        Toast.makeText(context, "Premium sound is included.\nUpgrade to premium version is required.", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Premium.class));
                        MainActivity.load.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(@NonNull QonversionError qonversionError) {
                    Toast.makeText(context, "Error: " + qonversionError.getDescription(), Toast.LENGTH_SHORT).show();
                    MainActivity.load.setVisibility(View.GONE);
                }
            });
        } else {
            if (checkNeedDownload(favListItems).size() != 0) {
                AskDownloadsDialog.askDownloadsDialog(context, checkNeedDownload(favListItems));
            } else {
                favListPlay(title);
            }
        }
    }

    private boolean checkWillShowConstainProTrackDialog(ArrayList<FavListItem> favListItems) {
        if (MainActivity.databaseHandler.getIsProUser() == 1) {
            for (int i = 0; i < favListItems.size(); i++) {
                if (favListItems.get(i).getIspro() == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<String> checkNeedDownload(ArrayList<FavListItem> favListItems) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < favListItems.size(); i++) {
            if (favListItems.get(i).getNeeddownload() == 2) {
                String path = context.getApplicationInfo().dataDir + "/cache/audio" + favListItems.get(i).getTid() + "to" + favListItems.get(i).getPosition() + ".mp3";
                File file = new File(path);
                if (!file.exists()) {
                    if (file.getName().contains("audio")) {
                        list.add(favListItems.get(i).getTid());
                    }
                }
            }
        }
        return list;
    }

    public void favListPlay(String title) {
        if (MainActivity.bottomSheetPlayList.size() != 0) {//만약 playinglist에 재생목록이 있다면
            ArrayList<Integer> pagelist = new ArrayList<>();
            ArrayList<Integer> positionlist = new ArrayList<>();
            for (int ii = 0; ii < MainActivity.bottomSheetPlayList.size(); ii++) {
                pagelist.add(MainActivity.bottomSheetPlayList.get(ii).getPage());
                positionlist.add(MainActivity.bottomSheetPlayList.get(ii).getPosition());
                AudioController.stopPage(MainActivity.bottomSheetPlayList.get(ii).getPage(), MainActivity.bottomSheetPlayList.get(ii).getPosition());
                if (ii == MainActivity.bottomSheetPlayList.size() - 1) {
                    MainActivity.bottomSheetPlayList.clear();
//                    MainActivity.bottomSheetAdapter.notifyItemRangeRemoved(0, MainActivity.bottomSheetPlayList.size() - 1);
//                    MainActivity.bottomSheetAdapter.notifyDataSetChanged();
                    MainActivity.databaseHandler.deleteAllPlayinglist(pagelist, positionlist, title);
                }
            }
            Log.d("FavTitleAdapter>>>", "2");
        } else {//playinglist에 기존목록 없다면
            MainActivity.databaseHandler.addFavListInPlayinglist(title);
        }
        ArrayList<PageItem> pageItems = new ArrayList<>();
        for (int i = 0; i < MainActivity.bottomSheetPlayList.size(); i++) {
//            SeekController.changeVolumn(MainActivity.bottomSheetPlayList.get(i).getPnp(), MainActivity.bottomSheetPlayList.get(i).getSeek());
            AudioController.setVolumn(MainActivity.bottomSheetPlayList.get(i).getTid(), MainActivity.bottomSheetPlayList.get(i).getSeek());
            pageItems.add(MainActivity.bottomSheetPlayList.get(i));
            changePageImage(MainActivity.bottomSheetPlayList.get(i).getPage(), MainActivity.bottomSheetPlayList.get(i).getPosition() - 1);
            MainActivity.databaseHandler.changePageSeekWhenPlayInFavTitle(MainActivity.bottomSheetPlayList.get(i).getTid(), MainActivity.bottomSheetPlayList.get(i).getSeek());
            if (i == MainActivity.bottomSheetPlayList.size() - 1) {
                AudioController.startPlayingList(context, pageItems);
                AudioController.checkOpenService(context);
                MainActivity.pands.setBackgroundResource(R.drawable.bottom_pause);
            }
        }
        PageAdapter.page4Count();
        MainActivity.load.setVisibility(View.GONE);
        Log.d("FavTitleAdapter>>>", "finished");
    }

    private void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
