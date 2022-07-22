package com.hgc.coolweather;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hgc.coolweather.db.City;
import com.hgc.coolweather.db.County;
import com.hgc.coolweather.db.Province;

import java.util.List;

/**
 * @Description TODO
 * @systemUser gchan2
 * @Author hanguangchuan
 * @Date 07-04-2022 周一 9:35
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;

    private final List<String> dataList;

    public CustomAdapter(List<String> dataList, OnItemClickListener onItemClickListener) {
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.getTextView().setText(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);

            // 添加点击事件
            itemView.setOnClickListener(v -> {
                onItemClickListener.onClick(this.getAdapterPosition());
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
