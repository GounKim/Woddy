package com.example.woddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<AlbumItem> items = new ArrayList<>();

    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albumboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder viewHolder, int position) {

        AlbumItem item = items.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getUrl())
                .into(viewHolder.album_img);

        viewHolder.album_text.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<AlbumItem> items) {
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView album_img;
        TextView album_text;

        ViewHolder(View itemView) {
            super(itemView);

            album_img = itemView.findViewById(R.id.album_img);
            album_text = itemView.findViewById(R.id.album_text);
        }
    }
}
