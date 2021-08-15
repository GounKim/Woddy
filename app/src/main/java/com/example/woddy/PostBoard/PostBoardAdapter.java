package com.example.woddy.PostBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Posting;
import com.example.woddy.R;

import java.util.ArrayList;

public class PostBoardAdapter extends RecyclerView.Adapter<PostBoardAdapter.ViewHolder> {

    private ArrayList<Posting> PostingList;
    private ArrayList<String> postingPath;

    public PostBoardAdapter() {

    }

    public void setItems(ArrayList<Posting> PostingList, ArrayList<String> postingPath) {
        this.PostingList = PostingList;
        this.postingPath = postingPath;
    }

    @NonNull
    @Override
    public PostBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_board_item, parent, false);
        PostBoardAdapter.ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    @Override

    public void onBindViewHolder(final ViewHolder holder, int position) {

        Posting data = PostingList.get(position);

        holder.writer.setText(data.getWriter());
        holder.content.setText(data.getContent());
//        holder.boardName.setText();
//        holder.writtenTime.setText(data.getPostedTime());
        holder.likeNum.setText(data.getNumberOfLiked() + "");
    }

    @Override
    public int getItemCount() {
        return postingPath.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView writer;
        public TextView content;
        public TextView boardName;
        public TextView writtenTime;
        public TextView likeNum;

        public ViewHolder(View itemView) {
            super(itemView);

            writer = (TextView) itemView.findViewById(R.id.writer);
            content = (TextView) itemView.findViewById(R.id.message);
            boardName = (TextView) itemView.findViewById(R.id.boardName);
            writtenTime = (TextView) itemView.findViewById(R.id.writtenTime);
            likeNum = (TextView) itemView.findViewById(R.id.like_num);
        }
    }
}
