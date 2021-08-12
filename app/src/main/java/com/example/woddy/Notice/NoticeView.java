package com.example.woddy.Notice;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.R;


class NoticeView extends RecyclerView.ViewHolder {

    public TextView writer;
    public TextView message;
    public TextView boardName;

    public NoticeView(View itemView) {
        super(itemView);

        writer = (TextView) itemView.findViewById(R.id.writer);
        message = (TextView) itemView.findViewById(R.id.message);
        boardName = (TextView)itemView.findViewById(R.id.boardName);

    }

}