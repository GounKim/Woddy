package com.example.woddy.Album;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woddy.ImgPost.ImgPost;
import com.example.woddy.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<AlbumItem> items = new ArrayList<>();

    private Context mContext;

    private String postingNumber;

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
        TextView album_liked;

        ViewHolder(View itemView) {
            super(itemView);

            album_img = itemView.findViewById(R.id.album_img);
            album_text = itemView.findViewById(R.id.album_text);
            album_liked = itemView.findViewById(R.id.album_liked);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //사용자가 클릭한 아이템의 position을 준다
                    int pos = getAdapterPosition();
                    //포지션이 recylerView의 아이템인지 확인
                    if(pos != RecyclerView.NO_POSITION){
                        //액티비티 전환
                        Intent intent = new Intent(v.getContext(), ImgPost.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("postingNumber", String.valueOf(items.get(pos)));
                        v.getContext().startActivity(intent);
                    }


                }
            });
        }
    }
}
