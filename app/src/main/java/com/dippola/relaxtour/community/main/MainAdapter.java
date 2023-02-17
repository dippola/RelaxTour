package com.dippola.relaxtour.community.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.ImageViewer;
import com.dippola.relaxtour.community.main.detail.CommunityMainDetail;
import com.dippola.relaxtour.community.signIn.CommunitySignIn;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.dialog.Premium;
import com.dippola.relaxtour.retrofit.model.PostModelView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    List<PostModelView> arrayList;
    Context context;
    RequestOptions userr, imgr;
    ActivityResultLauncher<Intent> launcher;

    public MainAdapter(Context context, List<PostModelView> arrayList, RequestOptions userr, RequestOptions imgr, ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.arrayList = arrayList;
        this.userr = userr;
        this.imgr = imgr;
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_main_item, parent, false);
        MainAdapter.MainViewHolder holder = new MainAdapter.MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, int position) {
        int i = position;

        if (CommunityMain.isLoading) {
            holder.load.setVisibility(View.VISIBLE);
            holder.item.setVisibility(View.GONE);
        } else {
            holder.load.setVisibility(View.GONE);
            holder.item.setVisibility(View.VISIBLE);
            if (arrayList.get(i).getCategory().equals("qna")) {
                holder.textq.setVisibility(View.VISIBLE);
            }

            if (arrayList.get(i).getUser_image() == null || arrayList.get(i).getUser_image().length() == 0) {
                holder.userimageload.setVisibility(View.GONE);
                Glide.with(holder.userImage.getContext()).load(context.getResources().getDrawable(R.drawable.nullpic)).apply(userr).into(holder.userImage);
            } else {
                Glide.with(holder.userImage.getContext()).load(arrayList.get(i).getUser_image()).apply(userr).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.userimageload.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.userImage);
            }

            if (arrayList.get(i).getImageurlcount() == 0) {
                holder.imageurllayout.setVisibility(View.GONE);
            } else {
                Glide.with(holder.firstimg.getContext()).load(String.valueOf(arrayList.get(i).getImageurl().split("●")[1])).apply(imgr).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.imgload.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.firstimg);
                if (arrayList.get(i).getImageurlcount() == 2) {
                    holder.imageurlcount.setVisibility(View.GONE);
                } else {
                    holder.imageurlcount.setText("+" + String.valueOf(arrayList.get(i).getImageurlcount() - 2));
                }
            }

            holder.title.setText(arrayList.get(i).getTitle());
            holder.view.setText(String.valueOf(arrayList.get(i).getView()));
            holder.like.setText(String.valueOf(arrayList.get(i).getLike()));
            holder.date.setText(getDateResult(arrayList.get(i).getDate()));
            holder.commentcount.setText(String.valueOf(arrayList.get(i).getCommentcount()));
            holder.nickname.setText(String.valueOf(arrayList.get(i).getNickname()));


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (new DatabaseHandler(context).getIsProUser() == 1) {
                        Toast.makeText(context, "Premium rights are required to access the community.", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Premium.class));
                    } else if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        Toast.makeText(context, "Login is required for community use.", Toast.LENGTH_SHORT).show();
                        launcher.launch(new Intent(context, CommunitySignIn.class));
                    } else {
                        Intent intent = new Intent(context, CommunityMainDetail.class);
                        intent.putExtra("parent_id", arrayList.get(i).getParent_id());
                        intent.putExtra("parent_user", arrayList.get(i).getParent_user());
                        launcher.launch(intent);
                    }

//                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//                        Toast.makeText(context, "Login is required for community use.", Toast.LENGTH_SHORT).show();
//                        launcher.launch(new Intent(context, CommunitySignIn.class));
//                    } else {
//                        Intent intent = new Intent(context, CommunityMainDetail.class);
//                        intent.putExtra("parent_id", arrayList.get(i).getParent_id());
//                        intent.putExtra("parent_user", arrayList.get(i).getParent_user());
//                        context.startActivity(intent);
//                    }
                }
            });

            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageViewer.class);
                    if (arrayList.get(i).getUser_image() != null) {
                        intent.putExtra("url", arrayList.get(i).getUser_image());
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getParent_id();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage, firstimg;
        TextView title, date, view, nickname, like, commentcount, imageurlcount, textq;
        ConstraintLayout item, imageurllayout;
        ProgressBar imgload, userimageload;
//        ShimmerFrameLayout load;
        ConstraintLayout load;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.community_main_item_userimage);
            this.firstimg = itemView.findViewById(R.id.community_main_item_imageurlcount_img);
            this.title = itemView.findViewById(R.id.community_main_item_title);
            this.date = itemView.findViewById(R.id.community_main_item_date);
            this.view = itemView.findViewById(R.id.community_main_item_view);
            this.nickname = itemView.findViewById(R.id.community_main_item_nickname);
            this.like = itemView.findViewById(R.id.community_main_item_like);
            this.commentcount = itemView.findViewById(R.id.community_main_item_comment_count);
            this.item = itemView.findViewById(R.id.community_main_item);
            this.imageurlcount = itemView.findViewById(R.id.community_main_item_imageurlcount_text);
            this.imageurllayout = itemView.findViewById(R.id.community_main_item_imageurlcount_layout);
            this.imgload = itemView.findViewById(R.id.community_main_item_img_load);
            this.userimageload = itemView.findViewById(R.id.community_main_item_userimageload);
            this.textq = itemView.findViewById(R.id.community_main_item_q);
            this.load = itemView.findViewById(R.id.community_main_item_loadinitem);
        }
    }


    private String getDateResult(String dateFromServer) {
        //3번째 글 2022-11-24 21:06
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
