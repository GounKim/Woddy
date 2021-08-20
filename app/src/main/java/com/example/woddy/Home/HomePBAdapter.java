package com.example.woddy.Home;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.woddy.Entity.Posting;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.R;
import com.example.woddy.Posting.ShowPosting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// Home Posting Board Adapter (popular writings, recent writings)
public class HomePBAdapter extends BaseAdapter {
    private final int WRITING_SIMPLE = 0;
    private final int WRITING_WITH_IMAGE = 1;
    private final int ITEM_VIEW_TYPE_MAX = 2;

    private ArrayList<Posting> writingList = new ArrayList<>();
    private ArrayList<String> postingPath = new ArrayList<>();

    public void setItem(ArrayList<Posting> writings, ArrayList<String> postingPath) {
        this.writingList = writings;
        this.postingPath = postingPath;
    }

    public ArrayList<Posting> getItem() {
        return writingList;
    }

    @Override
    public int getCount() {
        return writingList.size();
    }

    @Override
    public Posting getItem(int position) {
        return writingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        int viewType = getItemViewType(position);


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            switch (viewType) {
                case WRITING_SIMPLE:    // 기본형
                    view = inflater.inflate(R.layout.home_item_posting_simple, viewGroup, false);
                    SimpleViewHolder sHolder = new SimpleViewHolder();

                    sHolder.sLayout = view.findViewById(R.id.home_postingS_layout);
                    sHolder.sTitle = view.findViewById(R.id.home_postingS_title);
                    sHolder.sContent = view.findViewById(R.id.home_postingS_content);
                    sHolder.sBoardName = view.findViewById(R.id.home_postingS_board_name);
                    sHolder.sTime = view.findViewById(R.id.home_postingS_time);
                    sHolder.sLiked = view.findViewById(R.id.home_postingS_liked);

                    view.setTag(sHolder);
                    break;

                case WRITING_WITH_IMAGE:    // 이미지 포함형
                    view = inflater.inflate(R.layout.home_item_posting_image, viewGroup, false);
                    ImageViewHolder iHolder = new ImageViewHolder();

                    iHolder.iLayout = view.findViewById(R.id.home_postingI_layout);
                    iHolder.iTitle = view.findViewById(R.id.home_postingI_title);
                    iHolder.iContent = view.findViewById(R.id.home_postingI_content);
                    iHolder.iBoardName = view.findViewById(R.id.home_postingI_board_name);
                    iHolder.iTime = view.findViewById(R.id.home_postingI_time);
                    iHolder.iLiked = view.findViewById(R.id.home_postingI_liked);
                    iHolder.iImageView = view.findViewById(R.id.home_postingI_image);

                    view.setTag(iHolder);
                    break;
            }
        }

        Posting writing = getItem(position);
        String path = postingPath.get(position);
        String[] tmpPath = path.split("/");
        String boardName = tmpPath[1];
        String tagName = tmpPath[3];

        if (writing != null) {
            switch (viewType) {
                case WRITING_SIMPLE:    // 기본형
                    SimpleViewHolder sHolder = (SimpleViewHolder) view.getTag();
                    sHolder.sTitle.setText(writing.getTitle());
                    sHolder.sContent.setText(writing.getContent());
                    sHolder.sBoardName.setText(boardName + " #" + tagName);
                    sHolder.sTime.setText(timestamp(writing.getPostedTime()));
                    sHolder.sLiked.setText("" + writing.getNumberOfLiked());

                    sHolder.sLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("documentPath", postingPath.get(position));
                            view.getContext().startActivity(intent);
                        }
                    });

                    break;

                case WRITING_WITH_IMAGE:    // 이미지 포함형
                    ImageViewHolder iHolder = (ImageViewHolder) view.getTag();
                    iHolder.iTitle.setText(writing.getTitle());
                    iHolder.iContent.setText(writing.getContent());
                    iHolder.iBoardName.setText(boardName + " #" + tagName);
                    iHolder.iTime.setText(timestamp(writing.getPostedTime()));
                    iHolder.iLiked.setText("" + writing.getNumberOfLiked());

                    if (writing.getPictures() != null) {
                        FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                        StorageReference storageRef = storage.getReference(writing.getPictures().get(0)); // 스토리지 공간을 참조해서 이미지를 가져옴

                        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Glide.with(viewGroup.getContext())
                                            .load(task.getResult())
                                            .into(iHolder.iImageView);
                                } else {
                                    Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                                }
                            }
                        });
                    }

                    iHolder.iLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("documentPath", postingPath.get(position));
                            view.getContext().startActivity(intent);
                        }
                    });

                    break;
            }
        }

        return view;
    }

    static class ImageViewHolder {
        LinearLayout iLayout;
        TextView iTitle;
        TextView iContent;
        TextView iBoardName;
        TextView iTime;
        TextView iLiked;
        ImageView iImageView;
    }

    static class SimpleViewHolder {
        LinearLayout sLayout;
        TextView sTitle;
        TextView sContent;
        TextView sBoardName;
        TextView sTime;
        TextView sLiked;
    }


    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getPictures() == null | getItem(position).getPictures().size() == 0) {
            return WRITING_SIMPLE;
        } else {
            return WRITING_WITH_IMAGE;
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
