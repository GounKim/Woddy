package com.example.woddy.Posting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woddy.R;

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
            Glide.with(context)
                    .load(imageURL)
                    .into(mImageView);
        }
    }
}
