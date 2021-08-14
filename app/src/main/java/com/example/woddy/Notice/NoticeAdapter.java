package com.example.woddy.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private ArrayList<NoticeItem> noticeDatas;

    public NoticeAdapter(Context context, ArrayList<NoticeItem> notices) {
        this.noticeDatas = notices;
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        NoticeAdapter.ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    @Override

    public void onBindViewHolder(final ViewHolder holder, int position) {

        NoticeItem data = noticeDatas.get(position);

        holder.writer.setText(data.getWriter());
        holder.message.setText(data.getMessage());
        holder.boardName.setText(data.getBoardName());
        holder.writtenTime.setText(data.getWrittenTime());
        holder.likeNum.setText(data.getLikeNum());
    }

    @Override
    public int getItemCount() {
        return noticeDatas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView writer;
        public TextView message;
        public TextView boardName;
        public TextView writtenTime;
        public TextView likeNum;

        public ViewHolder(View itemView) {
            super(itemView);

            writer = (TextView) itemView.findViewById(R.id.writer);
            message = (TextView) itemView.findViewById(R.id.message);
            boardName = (TextView) itemView.findViewById(R.id.boardName);
            writtenTime = (TextView) itemView.findViewById(R.id.writtenTime);
            likeNum = (TextView) itemView.findViewById(R.id.like_num);
        }
    }
}
