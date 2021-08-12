package com.example.woddy.Notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.R;

import java.util.ArrayList;

class NoticeAdapter extends RecyclerView.Adapter<NoticeView> {

    private ArrayList<NoticeItem> verticalDatas;

    public void setData(ArrayList<NoticeItem> list){
        verticalDatas = list;
    }

    @Override
    public NoticeView onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_item, parent, false);

        NoticeView holder = new NoticeView(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NoticeView holder, int position) {
        NoticeItem data = verticalDatas.get(position);

        holder.writer.setText(data.getWriter());
        holder.message.setText(data.getMessage());
        holder.boardName.setText(data.getBoardName());

    }

    @Override
    public int getItemCount() {
        return verticalDatas.size();
    }
}
