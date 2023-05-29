package com.dippola.relaxtour.community.translate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dippola.relaxtour.R;

public class SelectLanguageAdapter extends RecyclerView.Adapter<SelectLanguageAdapter.CustomViewHolder>{
    Context context;
    String[] showLanguageList;
    String[] languageList;
    String beforeLanguage;
    int beforeLanguageIndex;
    public static int selectPosition = -1;
    public SelectLanguageAdapter(Context context, String[] showLanguageList, String[] languageList, String beforeLanguage, int beforeLanguageIndex) {
        this.context = context;
        this.showLanguageList = showLanguageList;
        this.languageList = languageList;
        this.beforeLanguage = beforeLanguage;
        this.beforeLanguageIndex = beforeLanguageIndex;
        selectPosition = beforeLanguageIndex;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_language_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textView.setText(showLanguageList[position]);
        holder.checkBox.setChecked(position == selectPosition);
    }

    @Override
    public int getItemCount() {
        return showLanguageList.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        ConstraintLayout box;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.box = itemView.findViewById(R.id.select_language_item_box);
            this.textView = itemView.findViewById(R.id.select_language_item_text);
            this.checkBox = itemView.findViewById(R.id.select_language_item_checkbox);

            this.box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });

            this.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
