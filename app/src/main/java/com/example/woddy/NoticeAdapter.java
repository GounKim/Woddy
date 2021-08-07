package com.example.woddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private ArrayList<NoticeItem> mNoticeList;


    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        holder.onBind(mNoticeList.get(position));
    }

    public void setNoticeList(ArrayList<NoticeItem> list){
        this.mNoticeList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView writer;
        TextView message;
        TextView boardName;
        TextView time;
        TextView heartNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            writer = (TextView) itemView.findViewById(R.id.writer);
            message = (TextView) itemView.findViewById(R.id.message);
            boardName = (TextView)itemView.findViewById(R.id.boardName);
            time = (TextView)itemView.findViewById(R.id.time);
            heartNum = (TextView)itemView.findViewById(R.id.heartNum);
        }

        void onBind(NoticeItem item){
            writer.setText(item.getWriter());
            message.setText(item.getMessage());
            boardName.setText(item.getBoardName());
            time.setText(item.getTime());
            heartNum.setText(item.getHeartNum());
        }
    }
}
