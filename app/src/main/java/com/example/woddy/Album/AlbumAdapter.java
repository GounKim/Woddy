package com.example.woddy.Album;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<Posting> items;
    private ArrayList<String> documentPath;
    private Context mContext;

    private String postingNumber;
    private String pictures;
    private String title;
    private String numberOfLiked;


    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albumboard, parent, false);
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

        viewHolder.album_text.setText(posting.getTitle());
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
                        Intent intent = new Intent(v.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("documentPath", documentPath.get(pos));
                        v.getContext().startActivity(intent);
                    }


                }
            });
        }
    }
}
