package com.dippola.relaxtour.community.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.retrofit.RetrofitClient;
import com.dippola.relaxtour.retrofit.model.MainModel;

import java.util.List;

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
        MainViewHolder holder = new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.date.setText(arrayList.get(position).getDate());
        holder.view.setText(arrayList.get(position).getCount());

        //get user uid검색에서 id검색으로 바꾸기
//        RetrofitClient.getApiService().getUser()
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, view, nickname;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.community_main_item_title);
            this.date = itemView.findViewById(R.id.community_main_item_date);
            this.view = itemView.findViewById(R.id.community_main_item_view);
            this.nickname = itemView.findViewById(R.id.community_main_item_nickname);
        }
    }
}
