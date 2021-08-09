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

    private ArrayList<Posting> writings;

    HomePBAdapter(ArrayList<Posting> writing) {
        this.writings = writing;
    }

    public void addItem(Posting posting) {
        writings.add(posting);
    }

    @Override
    public int getCount() {
        return writings.size();
    }

    @Override
    public Posting getItem(int position) {
        return writings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        int viewType = getItemViewType(position) ;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            switch (viewType) {
                case WRITING_SIMPLE:    // 기본형
                    view = inflater.inflate(R.layout.home_item_posting_simple, viewGroup, false);

                    LinearLayout layoutS = view.findViewById(R.id.home_postingS_layout);
                    TextView writerS = view.findViewById(R.id.home_postingS_title);
                    TextView contentS = view.findViewById(R.id.home_postingS_content);
                    TextView boardNameS = view.findViewById(R.id.home_postingS_board_name);
                    TextView timeS = view.findViewById(R.id.home_postingS_time);
                    TextView likedS = view.findViewById(R.id.home_postingS_liked);

                    Posting writing = getItem(position);

                    writerS.setText(writing.getTitle());
                    contentS.setText(writing.getContent());
                    boardNameS.setText(writing.getTag());
                    timeS.setText(timestamp(writing.getPostedTime()));
                    likedS.setText(""+writing.getNumberOfLiked());

                    layoutS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(), getItem(position) + "", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }
                    });

                    break;

                case WRITING_WITH_IMAGE:    // 이미지 포함형
                    view = inflater.inflate(R.layout.home_item_posting_image, viewGroup, false);

                    LinearLayout layoutI = view.findViewById(R.id.home_postingI_layout);
                    TextView writerI = view.findViewById(R.id.home_postingI_title);
                    TextView contentI = view.findViewById(R.id.home_postingI_content);
                    TextView boardNameI = view.findViewById(R.id.home_postingI_board_name);
                    TextView timeI = view.findViewById(R.id.home_postingI_time);
                    TextView likedI = view.findViewById(R.id.home_postingI_liked);
                    ImageView imageViewI = view.findViewById(R.id.home_postingI_image);

                    writing = getItem(position);

                    writerI.setText(writing.getTitle());
                    contentI.setText(writing.getContent());
                    boardNameI.setText(writing.getTag());
                    timeI.setText(timestamp(writing.getPostedTime()));
                    likedI.setText(""+writing.getNumberOfLiked());

                    if (writing.getPictures() != "" | writing.getPictures() != null) {
                        FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
                        StorageReference storageRef = storage.getReference(writing.getPictures()); // 스토리지 공간을 참조해서 이미지를 가져옴

                        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Glide.with(context)
                                            .load(task.getResult())
                                            .into(imageViewI);
                                } else {
                                    Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                                }
                            }
                        });
                    }

                    layoutI.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(), getItem(position) + "", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }
                    });

                    break;
            }
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getPictures() == null | getItem(position).getPictures() == "") {
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
