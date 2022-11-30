package com.dippola.relaxtour.community.main.write;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;
import com.dippola.relaxtour.databasehandler.DatabaseHandler;
import com.dippola.relaxtour.pages.item.FavListItem;
import com.dippola.relaxtour.pages.item.FavTitleItem;

import java.util.List;

public class ShareTitleAdapter extends RecyclerView.Adapter<ShareTitleAdapter.CustomViewHolder> {
    List<FavTitleItem> titleitems;
    Context context;

    RecyclerView.LayoutManager layoutManager;

    public ShareTitleAdapter(List<FavTitleItem> titleitems, Context context) {
        this.titleitems = titleitems;
        this.context = context;
    }
    @NonNull
    @Override
    public ShareTitleAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_share_title_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareTitleAdapter.CustomViewHolder holder, int position) {
        int i = position;
        holder.title.setText(titleitems.get(i).getTitle());

        if (titleitems.get(i).getIsopen() == 1) {
            holder.uandd.setBackgroundResource(R.drawable.fav_page_item_down);
            collapse(holder.hide);
        } else {
            holder.uandd.setBackgroundResource(R.drawable.fav_page_item_up);
            expand(holder.hide);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ShareTitleAdapter>>>", "1: " + titleitems.get(i).getIsopen());
                if (titleitems.get(i).getIsopen() == 2) {
                    titleitems.get(i).setIsopen(1);
                } else {
                    for (int ii = 0; ii < titleitems.size(); ii++) {
                        if (titleitems.get(ii).getIsopen() == 2) {
                            titleitems.get(ii).setIsopen(1);
                        }
                    }
                    titleitems.get(i).setIsopen(2);
                    List<FavListItem> listitems = new DatabaseHandler(context).getFavListItem(titleitems.get(i).getTitle());
                    ShareListAdapter adapter = new ShareListAdapter(listitems, context);
                    layoutManager = new LinearLayoutManager(context);
                    holder.list.setLayoutManager(layoutManager);
                    holder.list.setAdapter(adapter);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleitems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView title;
        CheckBox uandd;
        Button choice;
        RelativeLayout hide;
        RecyclerView list;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.community_write_share_list_item_layout);
            title = itemView.findViewById(R.id.community_write_share_list_item_title);
            uandd = itemView.findViewById(R.id.community_write_share_list_item_uandd);
            choice = itemView.findViewById(R.id.community_write_share_list_item_choice);
            hide = itemView.findViewById(R.id.community_write_share_list_item_hide);
            list = itemView.findViewById(R.id.community_write_share_list_item_recyclerview);
        }
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
