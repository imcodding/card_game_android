package com.example.cardproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardproject.R;
import com.example.cardproject.model.Record;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {

    ArrayList<Record> recordList;

    public RecordAdapter(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, null);
        RecordHolder holder = new RecordHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder holder, int position) {
        Record record = recordList.get(position);

        holder.tvRank.setText(String.valueOf(position+1));
        holder.tvScore.setText(String.valueOf(record.getScore()));
        holder.tvNickname.setText(record.getNickname());
        holder.tvDate.setText(record.getDate());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class RecordHolder extends RecyclerView.ViewHolder {

        TextView tvScore;
        TextView tvRank;
        TextView tvNickname;
        TextView tvDate;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);

            tvScore = itemView.findViewById(R.id.game_tv_score);
            tvRank = itemView.findViewById(R.id.tv_rank);
            tvNickname = itemView.findViewById(R.id.tv_nickname);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

    public void addItem(Record record) {
        recordList.add(0, record);
    }
}
