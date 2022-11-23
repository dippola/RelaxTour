package com.dippola.relaxtour.community.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainCommentModel;
import com.dippola.relaxtour.retrofit.model.MainModel;
import com.dippola.relaxtour.retrofit.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    List<MainModel> arrayList;

    public MainAdapter(List<MainModel> arrayList) {
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
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, view, nickname, like, comment;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.community_main_item_title);
            this.date = itemView.findViewById(R.id.community_main_item_date);
            this.view = itemView.findViewById(R.id.community_main_item_view);
            this.nickname = itemView.findViewById(R.id.community_main_item_nickname);
            this.like = itemView.findViewById(R.id.community_main_item_like);
            this.comment = itemView.findViewById(R.id.community_main_item_comment_count);
        }
    }

    private void finishedLoad() {
        CommunityMain.itemload.setVisibility(View.GONE);
        CommunityMain.itemload.stopShimmer();
        CommunityMain.recyclerView.setVisibility(View.VISIBLE);
        CommunityMain.pagebox.setVisibility(View.VISIBLE);
    }
}
