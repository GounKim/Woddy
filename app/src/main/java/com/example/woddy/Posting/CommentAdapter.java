package com.example.woddy.Posting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Comment;
import com.example.woddy.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.swHolder> {
    ArrayList<Comment> commentList;

    CommentAdapter() {
        this.commentList = new ArrayList<>();
    }

    public void addItem(Comment comment) {
        commentList.add(comment);
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<Comment> commentList) {
        this.commentList = commentList;
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
        View view = inflater.inflate(R.layout.show_posting_comment_recycler, parent, false);

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
