package com.example.woddy;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.PostingSQL;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.Posting.ShowImgPostingAdapter;
import com.example.woddy.Posting.ShowPosting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ScrapListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int WRITING_SIMPLE = 0;
    private final int WRITING_WITH_IMAGE = 1;

    private ArrayList<PostingSQL> postingList = new ArrayList<>();

    public void setItem(ArrayList<PostingSQL> writings) {
        this.postingList = writings;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == WRITING_SIMPLE) {
            view = inflater.inflate(R.layout.item2, parent, false);
            return new SimpleHolder(view);
        } else {
            view = inflater.inflate(R.layout.item, parent, false);
            return new ImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SimpleHolder) {
            PostingSQL posting = postingList.get(position);

            ((SimpleHolder) holder).sBoardName.setText(posting.getBoard());
            ((SimpleHolder) holder).sTagName.setText(posting.getTag());
            ((SimpleHolder) holder).sWriter.setText(posting.getWriter());
            ((SimpleHolder) holder).sTitle.setText(posting.getTitle());
            ((SimpleHolder) holder).sContent.setText(posting.getContent());
            ((SimpleHolder) holder).sContent.setText(posting.getContent());
            ((SimpleHolder) holder).sTime.setText(posting.getPostedTime());

        } else if (holder instanceof ImageHolder) {
            PostingSQL posting = postingList.get(position);

            ((ImageHolder) holder).iBoardName.setText(posting.getBoard());
            ((ImageHolder) holder).iTagName.setText(posting.getTag());
            ((ImageHolder) holder).iWriter.setText(posting.getWriter());
            ((ImageHolder) holder).iTitle.setText(posting.getTitle());
            ((ImageHolder) holder).iContent.setText(posting.getContent());
            ((ImageHolder) holder).iTime.setText(posting.getPostedTime());

            // 이미지 설정
            String[] imageList = posting.getPictures().toArray(new String[0]);
            ((ImageHolder) holder).imgpost_slider.setOffscreenPageLimit(1);
            ((ImageHolder) holder).imgpost_slider.setAdapter(new ShowImgPostingAdapter(imageList));

            ((ImageHolder) holder).imgpost_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentIndicator(position, ((ImageHolder) holder).layoutIndicator);
                }
            });
            setupIndicators(imageList.length, ((ImageHolder) holder).layoutIndicator);
        }
    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (postingList.get(position).getPictures() == null) {
            return WRITING_SIMPLE;
        } else {
            return WRITING_WITH_IMAGE;
        }
    }

    public class SimpleHolder extends RecyclerView.ViewHolder {
        TextView sTitle, sContent, sBoardName, sTagName, sTime, sWriter;

        public SimpleHolder(@NonNull View itemView) {
            super(itemView);
            sTitle = itemView.findViewById(R.id.scrap_posting_title);
            sWriter = itemView.findViewById(R.id.scrap_posting_writer);
            sContent = itemView.findViewById(R.id.scrap_posting_content);
            sBoardName = itemView.findViewById(R.id.scrap_posting_boardName);
            sTagName = itemView.findViewById(R.id.scrap_posting_tag);
            sTime = itemView.findViewById(R.id.scrap_posting_time);

        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutIndicator;
        private ViewPager2 imgpost_slider;
        TextView iTitle, iContent, iBoardName, iTagName, iTime, iWriter;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            iTitle = itemView.findViewById(R.id.scrap_img_posting_title);
            iWriter = itemView.findViewById(R.id.scrap_img_posting_writer);
            iContent = itemView.findViewById(R.id.scrap_img_posting_content);
            iBoardName = itemView.findViewById(R.id.scrap_img_posting_boardName);
            iTagName = itemView.findViewById(R.id.scrap_img_posting_tag);
            iTime = itemView.findViewById(R.id.scrap_img_posting_time);

            //이미지 슬라이더
            layoutIndicator = itemView.findViewById(R.id.scrap_img_posting_layoutIndicators);
            imgpost_slider = itemView.findViewById(R.id.scrap_img_posting_slider);
        }
    }


    private void setupIndicators(int count, LinearLayout layoutIndicator) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(layoutIndicator.getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(layoutIndicator.getContext(),
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0, layoutIndicator);
    }

    private void setCurrentIndicator(int position, LinearLayout layoutIndicator) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        layoutIndicator.getContext(),
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        layoutIndicator.getContext(),
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

}
