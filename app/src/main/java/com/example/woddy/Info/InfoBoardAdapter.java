package com.example.woddy.Info;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woddy.Entity.News;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class InfoBoardAdapter extends RecyclerView.Adapter<InfoBoardAdapter.ViewHolder> {

    private ArrayList<News> items;
    private ArrayList<String> documentPath;

    private String boardName;
    private String tagName;

    public InfoBoardAdapter(String boardName, String tagName) {
        this.items = new ArrayList<>();
        this.documentPath = new ArrayList<>();
        this.boardName = boardName;
        this.tagName = tagName;
    }

    @NonNull
    @NotNull
    @Override
    public InfoBoardAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.albumboard_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InfoBoardAdapter.ViewHolder viewHolder, int position) {
        News news = items.get(position);

        if (!news.getPictures().isEmpty()) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference(news.getPictures().get(0));

            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(viewHolder.itemView.getContext())
                                .load(task.getResult())
                                .into(viewHolder.infoImageView);
                    } else {
                        Log.d("TAG", "Image Load in MyPage failed.", task.getException());
                    }
                }
            });

            viewHolder.infoTitle.setText(news.getTitle());
            viewHolder.infoText.setText(news.getContent());
            viewHolder.infoTime.setText(timestamp(news.getPostedTime()));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<News> items, ArrayList<String> documentPath) {
        this.items = items;
        this.documentPath = documentPath;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView infoImageView, infoHeart;
        TextView infoTitle, infoText, infoTime, infoLiked;

        ViewHolder(View itemView) {
            super(itemView);

            infoImageView = itemView.findViewById(R.id.album_img);
            infoTitle = itemView.findViewById(R.id.album_item_title);
            infoText = itemView.findViewById(R.id.album_item_content);
            infoTime = itemView.findViewById(R.id.album_item_time);
            infoHeart = itemView.findViewById(R.id.album_item_heart);
            infoLiked = itemView.findViewById(R.id.album_item_liked);

            infoHeart.setVisibility(View.INVISIBLE);
            infoLiked.setVisibility(View.INVISIBLE);

//            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        Intent intent = new Intent(v.getContext(), ShowImgPosting.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("documentPath", documentPath.get(pos));
//                        v.getContext().startActivity(intent);
//                    }
//                }
//            });
        }
    }

    private String timestamp(Date date) {    // 타임스탬프 생성
        TimeZone timeZone;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREAN);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }
}
