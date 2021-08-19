package com.example.woddy.PostBoard.Album;

import static android.content.ContentValues.TAG;

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
import com.example.woddy.Entity.Posting;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<Posting> items;
    private ArrayList<String> documentPath;

    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.albumboard_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder viewHolder, int position) {

        Posting posting = items.get(position);

        if (!posting.getPictures().isEmpty()) {
            FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
            StorageReference storageRef = storage.getReference(posting.getPictures().get(0)); // 스토리지 공간을 참조해서 이미지를 가져옴

            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(viewHolder.itemView.getContext())
                                .load(task.getResult())
                                .into(viewHolder.album_img);
                    } else {
                        Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                    }
                }
            });
        }

        viewHolder.album_title.setText(posting.getTitle());
        viewHolder.album_text.setText(posting.getTitle());
        viewHolder.album_time.setText(timestamp(posting.getPostedTime()));
        viewHolder.album_liked.setText(posting.getNumberOfLiked() + "");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Posting> items, ArrayList<String> documentPath) {
        this.items = items;
        this.documentPath = documentPath;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView album_img;
        TextView album_title, album_text, album_time;
        TextView album_liked;

        ViewHolder(View itemView) {
            super(itemView);

            album_img = itemView.findViewById(R.id.album_img);
            album_title = itemView.findViewById(R.id.album_item_title);
            album_text = itemView.findViewById(R.id.album_item_content);
            album_time = itemView.findViewById(R.id.album_item_time);
            album_liked = itemView.findViewById(R.id.album_item_liked);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //사용자가 클릭한 아이템의 position을 준다
                    int pos = getAdapterPosition();
                    //포지션이 recylerView의 아이템인지 확인
                    if (pos != RecyclerView.NO_POSITION) {
                        //액티비티 전환
                        Intent intent = new Intent(v.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("documentPath", documentPath.get(pos));
                        v.getContext().startActivity(intent);
                    }


                }
            });
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
