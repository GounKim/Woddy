package com.example.woddy.Posting;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ShowImgPostingAdapter extends RecyclerView.Adapter<ShowImgPostingAdapter.MyViewHolder> {
    private String[] sliderImage;

    public ShowImgPostingAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ShowImgPostingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_slider, parent, false);
        return new ShowImgPostingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowImgPostingAdapter.MyViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position], holder.mImageView.getContext());
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageSlider);
        }

        public void bindSliderImage(String imageURL, Context context) {
            FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
            StorageReference storageRef = storage.getReference(imageURL); // 스토리지 공간을 참조해서 이미지를 가져옴

            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Glide.with(context)
                                .load(task.getResult())
                                .into(mImageView);
                    } else {
                        Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                    }
                }
            });

        }
    }
}
