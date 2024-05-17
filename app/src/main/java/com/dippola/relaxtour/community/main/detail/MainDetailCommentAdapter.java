package com.dippola.relaxtour.community.main.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.ImageViewer;
import com.dippola.relaxtour.community.translate.Translate;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.Premium;
import com.dippola.relaxtour.retrofit.model.PostCommentModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainDetailCommentAdapter extends RecyclerView.Adapter<MainDetailCommentAdapter.CustomViewHolder> {
    List<PostCommentModel> list;
    Context context;
    int myid;
    FirebaseUser mAuth;
    ActivityResultLauncher<Intent> launcher;
    DatabaseHandler databaseHandler;

    public MainDetailCommentAdapter(List<PostCommentModel> list, Context context, int myid, FirebaseUser mAuth, ActivityResultLauncher<Intent> launcher, DatabaseHandler databaseHandler) {
        this.list = list;
        this.context = context;
        this.myid = myid;
        this.mAuth = mAuth;
        this.launcher = launcher;
        this.databaseHandler = databaseHandler;
    }
    @NonNull
    @Override
    public MainDetailCommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_main_detail_comment_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainDetailCommentAdapter.CustomViewHolder holder, int position) {
        int i = position;

        if (CommunityMainDetail.isCommentLoad) {
            holder.itemLoad.setVisibility(View.VISIBLE);
            holder.item.setVisibility(View.GONE);
        } else {
            holder.item.setVisibility(View.VISIBLE);
            holder.itemLoad.setVisibility(View.GONE);
            holder.body.setText(list.get(i).getBody());
            if (list.get(i).getUser_url() == null || list.get(i).getUser_url().equals("")) {
                Glide.with(context).load(R.drawable.nullpic).transform(new CircleCrop()).into(holder.img);
            } else {
                Glide.with(context).load(list.get(i).getUser_url()).transform(new CircleCrop()).into(holder.img);
            }
            holder.date.setText(getDateResult(list.get(i).getDate()));
            holder.nickname.setText(list.get(i).getNickname());
            if (list.get(i).getTo_id() != 0) {
                holder.tonickname.setVisibility(View.VISIBLE);
                holder.tonickname.setText(String.valueOf("@" + list.get(i).getTo_nickname()));
            } else {
                holder.tonickname.setVisibility(View.GONE);
            }

            holder.nickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!list.get(i).getNickname().equals(new DatabaseHandler(context).getUserModel().getNickname())) {
                        CommunityMainDetail.towho.setText(list.get(i).getNickname());
                        CommunityMainDetail.towhoid = list.get(i).getParent_user();
                        CommunityMainDetail.towho.setVisibility(View.VISIBLE);
                        CommunityMainDetail.editComment.clearFocus();
                        CommunityMainDetail.editComment.requestFocus();
                        CommunityMainDetail.showKeyboard(view, context);
                    }
                }
            });

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageViewer.class);
                    if (list.get(i).getUser_url() != null) {
                        intent.putExtra("url", list.get(i).getUser_url());
                    }
                    context.startActivity(intent);
                }
            });

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (databaseHandler.getIsProUser() == 1) {
                        context.startActivity(new Intent(context, Premium.class));
                    } else if (mAuth == null) {
                        Toast.makeText(context, "Sign in is required", Toast.LENGTH_SHORT).show();
                    } else {
                        CommunityMainDetail.bottomFrom = "comment";
                        CommunityMainDetail.comment_parent_user = list.get(i).getParent_user();
                        CommunityMainDetail.comment_parent_id = list.get(i).getId();
                        CommunityMainDetail.comment_index = i;
                        CommunityMainDetail.comment_body = list.get(i).getBody();
                        list.get(i).getId();
                        CommunityMainDetail.setBottomSheetBehavior(context);
                        if (CommunityMainDetail.bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                            CommunityMainDetail.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }
                }
            });

            holder.recomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommunityMainDetail.towho.setText(list.get(i).getNickname());
                    CommunityMainDetail.towhoid = list.get(i).getParent_user();
                    CommunityMainDetail.towho.setVisibility(View.VISIBLE);
                    CommunityMainDetail.editComment.clearFocus();
                    CommunityMainDetail.editComment.requestFocus();
                    CommunityMainDetail.showKeyboard(view, context);
                }
            });

            holder.translate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.translate.setVisibility(View.GONE);
                    holder.transLoad.setVisibility(View.VISIBLE);
                    SharedPreferences sharedPreferences;
                    try {
                        sharedPreferences = EncryptedSharedPreferences.create(
                                "languageTable",
                                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                                context.getApplicationContext(),
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                        );
                    } catch (GeneralSecurityException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    String nowLang = sharedPreferences.getString("nowLanguage", "device_language");
                    if (nowLang.equals("device_language")) {
                        nowLang = Locale.getDefault().getLanguage();
                    }

                    Translate.translateComment(context, holder.body.getText().toString(), nowLang, holder.body, holder.transLoad, holder.transReturn);
                }
            });

            holder.transReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.body.setText(list.get(i).getBody());
                    holder.transReturn.setVisibility(View.GONE);
                    holder.translate.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView nickname, date, body, tonickname, recomment;
        Button more;
        ConstraintLayout item;
        ShimmerFrameLayout itemLoad;
        ImageView translate, transReturn;
        ProgressBar transLoad;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.community_main_detail_comment_item_img);
            this.nickname = itemView.findViewById(R.id.community_main_detail_comment_item_nickname);
            this.date = itemView.findViewById(R.id.community_main_detail_comment_item_date);
            this.body = itemView.findViewById(R.id.community_main_detail_comment_item_body);
            this.more = itemView.findViewById(R.id.community_main_detail_comment_item_more);
            this.recomment = itemView.findViewById(R.id.community_main_detail_comment_item_recomment);
            this.tonickname = itemView.findViewById(R.id.community_main_detail_comment_item_tonickname);
            this.item = itemView.findViewById(R.id.community_main_detail_comment_item);
            this.itemLoad = itemView.findViewById(R.id.community_main_detail_comment_item_loadii);
            this.translate = itemView.findViewById(R.id.community_main_detail_comment_item_translate);
            this.transReturn = itemView.findViewById(R.id.community_main_detail_comment_item_translate_return);
            this.transLoad = itemView.findViewById(R.id.community_main_detail_comment_item_translate_load);
        }
    }

    private String getDateResult(String dateFromServer) {
        //2022-12-02T14:19:41.556741Z
//        String postTime = changeTime(dateFromServer);
//        String date[] = postTime.split(" ");
//        String time[] = date[1].split("\\:");
//        return date[0] + " " + time[0] + ":" + time[1];
        String nowTime = getTime();
        String postTime = changeTime(dateFromServer);
        if (nowTime.split(" ")[0].equals(postTime.split(" ")[0])) {
            return postTime.split(" ")[1].split(":")[0] + ":" + postTime.split(" ")[1].split(":")[1];
        } else {
            return postTime.split(" ")[0];
        }
    }
    private String changeTime(String dateFromServer) {
        String[] cut = dateFromServer.split(" ");
        String[] cut1 = cut[1].split("\\.");
        String result = cut[0] + " " + cut1[0];
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oldFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormat.parse(result);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newFormat.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormat.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dueDateAsNormal;
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = format.format(mDate);
        return date;
    }
}
