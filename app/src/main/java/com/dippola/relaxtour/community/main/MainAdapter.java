package com.dippola.relaxtour.community.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.community.detail.CommunityMainDetail;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainCommentModel;
import com.dippola.relaxtour.retrofit.model.MainModel;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    List<MainModel> arrayList;
    Context context;

    public MainAdapter(Context context, List<MainModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        holder.title.setText(arrayList.get(i).getTitle());
        holder.date.setText(arrayList.get(i).getDate());
        holder.view.setText(arrayList.get(i).getCount());
        holder.like.setText(arrayList.get(i).getLike());
        holder.date.setText(getDateResult(arrayList.get(i).getDate()));

        RetrofitClient.getApiService().getUser(arrayList.get(i).getUserModel()).enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful()) {
                    holder.nickname.setText(response.body().get(0).getNickname());
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
            }
        });

        RetrofitClient.getApiService().getMainAllComment(arrayList.get(i).getId()).enqueue(new Callback<List<MainCommentModel>>() {
            @Override
            public void onResponse(Call<List<MainCommentModel>> call, Response<List<MainCommentModel>> response) {
                if (response.isSuccessful()) {
                    holder.comment.setText(String.valueOf(response.body().size()));
                    finishedLoad();
                }
            }

            @Override
            public void onFailure(Call<List<MainCommentModel>> call, Throwable t) {
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainModel mainModel = new MainModel();
                mainModel.setId(arrayList.get(i).getId());
                mainModel.setParent_user(arrayList.get(i).getParent_user());
                mainModel.setDate(arrayList.get(i).getDate());
                mainModel.setTitle(arrayList.get(i).getTitle());
                mainModel.setBody(arrayList.get(i).getBody());
                mainModel.setImageurl(arrayList.get(i).getImageurl());
                mainModel.setCount(arrayList.get(i).getCount());
                mainModel.setLike(arrayList.get(i).getLike());
                mainModel.setList(arrayList.get(i).getList());
                Intent intent = new Intent(context, CommunityMainDetail.class);
                intent.putExtra("id", arrayList.get(i).getId());
                intent.putExtra("parent_user", arrayList.get(i).getParent_user());
                intent.putExtra("date", arrayList.get(i).getDate());
                intent.putExtra("title", arrayList.get(i).getTitle());
                intent.putExtra("body", arrayList.get(i).getBody());
                intent.putExtra("imageurl", arrayList.get(i).getImageurl());
                intent.putExtra("count", arrayList.get(i).getCount());
                intent.putExtra("like", arrayList.get(i).getLike());
                intent.putExtra("list", arrayList.get(i).getList());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, view, nickname, like, comment;
        ConstraintLayout item;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.community_main_item_title);
            this.date = itemView.findViewById(R.id.community_main_item_date);
            this.view = itemView.findViewById(R.id.community_main_item_view);
            this.nickname = itemView.findViewById(R.id.community_main_item_nickname);
            this.like = itemView.findViewById(R.id.community_main_item_like);
            this.comment = itemView.findViewById(R.id.community_main_item_comment_count);
            this.item = itemView.findViewById(R.id.community_main_item);
        }
    }

    private void finishedLoad() {
        CommunityMain.itemload.setVisibility(View.GONE);
        CommunityMain.itemload.stopShimmer();
        CommunityMain.recyclerView.setVisibility(View.VISIBLE);
        CommunityMain.pagebox.setVisibility(View.VISIBLE);
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
        String[] cut = dateFromServer.split("T");
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String date = format.format(mDate);
        return date;
    }
}
