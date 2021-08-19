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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Posting.ShowImgPosting;
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

public class PostingListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int WRITING_SIMPLE = 0;
    private final int WRITING_WITH_IMAGE = 1;

    private ArrayList<Posting> postingList =  new ArrayList<>();
    private ArrayList<String> postingPath = new ArrayList<>();

    public void setItem(ArrayList<Posting> writings, ArrayList<String> postingPath) {
        this.postingList = writings;
        this.postingPath = postingPath;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == WRITING_SIMPLE) {
            view = inflater.inflate(R.layout.home_item_posting_simple, parent, false);
            return new SimpleHolder(view);
        } else {
            view = inflater.inflate(R.layout.home_item_posting_image, parent, false);
            return new ImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SimpleHolder) {
            Posting posting = postingList.get(position);
            ((SimpleHolder) holder).sTitle.setText(posting.getTitle());
            ((SimpleHolder) holder).sContent.setText(posting.getContent());
            ((SimpleHolder) holder).sTitle.setText(posting.getTitle());
            ((SimpleHolder) holder).sContent.setText(posting.getContent());
//            ((SimpleHolder) holder).sBoardName.setText(posting.getTag());
            ((SimpleHolder) holder).sTime.setText(timestamp(posting.getPostedTime()));
            ((SimpleHolder) holder).sLiked.setText(""+posting.getNumberOfLiked());

        } else if (holder instanceof ImageHolder) {
            Posting posting = postingList.get(position);

            ((ImageHolder) holder).iTitle.setText(posting.getTitle());
            ((ImageHolder) holder).iContent.setText(posting.getContent());
//            ((ImageHolder) holder).iBoardName.setText(posting.getTag());
            ((ImageHolder) holder).iTime.setText(timestamp(posting.getPostedTime()));
            ((ImageHolder) holder).iLiked.setText(""+posting.getNumberOfLiked());

            // 이미지 설정
            if (posting.getPictures() != null) {
                FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                StorageReference storageRef = storage.getReference(posting.getPictures().get(0)); // 스토리지 공간을 참조해서 이미지를 가져옴

                storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Glide.with(holder.itemView.getContext())
                                    .load(task.getResult())
                                    .into(((ImageHolder) holder).iImageView);
                        } else {
                            Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (postingList.get(position).getPictures() == null
                | postingList.get(position).getPictures().size() == 0) {
            return WRITING_SIMPLE;
        } else {
            return WRITING_WITH_IMAGE;
        }
    }

    public class SimpleHolder extends RecyclerView.ViewHolder {
        LinearLayout sLayout;
        TextView sTitle, sContent, sBoardName, sTime, sLiked;

        public SimpleHolder(@NonNull View itemView) {
            super(itemView);
            sLayout = itemView.findViewById(R.id.home_postingS_layout);
            sTitle = itemView.findViewById(R.id.home_postingS_title);
            sContent = itemView.findViewById(R.id.home_postingS_content);
            sBoardName = itemView.findViewById(R.id.home_postingS_board_name);
            sTime = itemView.findViewById(R.id.home_postingS_time);
            sLiked = itemView.findViewById(R.id.home_postingS_liked);

            sLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("documentPath", postingPath.get(pos));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        LinearLayout iLayout;
        TextView iTitle, iContent, iBoardName, iTime, iLiked;
        ImageView iImageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            iLayout = itemView.findViewById(R.id.home_postingI_layout);
            iTitle = itemView.findViewById(R.id.home_postingI_title);
            iContent = itemView.findViewById(R.id.home_postingI_content);
            iBoardName = itemView.findViewById(R.id.home_postingI_board_name);
            iTime = itemView.findViewById(R.id.home_postingI_time);
            iLiked = itemView.findViewById(R.id.home_postingI_liked);
            iImageView = itemView.findViewById(R.id.home_postingI_image);

            iLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("documentPath", postingPath.get(pos));
                    view.getContext().startActivity(intent);
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
