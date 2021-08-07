package com.example.woddy.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.BoardTag;
import com.example.woddy.R;

import java.util.ArrayList;


// Home Favorite Board Adapter
public class HomeFBAdapter extends RecyclerView.Adapter<HomeFBAdapter.fbaHolder> {
    ArrayList<BoardTag> boardTags;

    HomeFBAdapter() {
        this.boardTags = new ArrayList<>();
    }

    public void addItem(BoardTag boardTag) {
        boardTags.add(boardTag);
    }

    public class fbaHolder extends RecyclerView.ViewHolder {
        private final ImageView boardImage;
        private final TextView boardName;
        private final TextView tagName;

        public fbaHolder(@NonNull View itemView) {
            super(itemView);
            boardImage = itemView.findViewById(R.id.home_hscroll_board_image);
            boardName = itemView.findViewById(R.id.home_hscroll_board_name);
            tagName = itemView.findViewById(R.id.home_hscroll_tag_name);
        }

        public ImageView getBoardImage() {
            return boardImage;
        }

        public TextView getBoardName() {
            return boardName;
        }

        public TextView getTagName() {
            return tagName;
        }
    }

    @NonNull
    @Override
    public HomeFBAdapter.fbaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_item_favorite_board, parent, false);

        return new fbaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFBAdapter.fbaHolder holder, int position) {
        //holder.getBoardImage()
        BoardTag bTag = boardTags.get(position);
        holder.getBoardName().setText(bTag.getBoardName());
        holder.getTagName().setText(bTag.getTagName());
    }

    @Override
    public int getItemCount() {
        return boardTags.size();
    }
}

