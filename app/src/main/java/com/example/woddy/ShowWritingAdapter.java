package com.example.woddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Comment;

import java.util.ArrayList;

public class ShowWritingAdapter extends RecyclerView.Adapter<ShowWritingAdapter.swHolder> {
    ArrayList<Comment> commentList;

    ShowWritingAdapter() {
        this.commentList = new ArrayList<>();
    }

    public void addItem(Comment comment) {
        commentList.add(comment);
        notifyDataSetChanged();
    }

    public class swHolder extends RecyclerView.ViewHolder {
        private final TextView writer;
        private final TextView content;

        public swHolder(@NonNull View itemView) {
            super(itemView);

            writer = itemView.findViewById(R.id.show_writing_comment_writer);
            content = itemView.findViewById(R.id.show_writing_comment_content);
        }

        public TextView getWriter() {
            return writer;
        }

        public TextView getContent() {
            return content;
        }
    }

    @NonNull
    @Override
    public swHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.show_writing_recycler_comment, parent, false);

        return new swHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull swHolder holder, int position) {
        holder.getWriter().setText(commentList.get(position).getWriter());
        holder.getContent().setText(commentList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

}
