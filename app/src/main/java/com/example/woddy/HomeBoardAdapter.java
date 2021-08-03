package com.example.woddy;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;

import java.util.ArrayList;

public class HomeBoardAdapter extends BaseAdapter {

    ArrayList<HomePostings> postingList = new ArrayList<>();
    int whichItem;  // 일반형 = 0 or 이미지형 = 1

    @Override
    public int getCount() {
        return postingList.size();
    }

    @Override
    public HomePostings getItem(int position) {
        return postingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if (getItem(position).getImage().equals("")) {
                view = inflater.inflate(R.layout.home_item_posting_simple, viewGroup, false);
                whichItem = 0;
            } else {
                view = inflater.inflate(R.layout.home_item_posting_image, viewGroup, false);
                whichItem = 1;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = 180;
            view.setLayoutParams(layoutParams);
        }

        HomePostings postings = getItem(position);

        if (whichItem == 0) {
            // 기본형
            TextView writerS = view.findViewById(R.id.home_postingS_writer);
            TextView contentS = view.findViewById(R.id.home_postingS_content);
            TextView boardNameS = view.findViewById(R.id.home_postingS_board_name);
            TextView timeS = view.findViewById(R.id.home_postingS_time);
            TextView likedS = view.findViewById(R.id.home_postingS_liked);

            writerS.setText(postings.getWriter());
            contentS.setText(postings.getShortContent());
            boardNameS.setText(postings.getBoardName());
            timeS.setText(postings.getWrittenTime());
            likedS.setText("" + postings.getLiked());

        } else {
            // 이미지 포함형
            TextView writerI = view.findViewById(R.id.home_postingI_writer);
            TextView contentI = view.findViewById(R.id.home_postingI_content);
            TextView boardNameI = view.findViewById(R.id.home_postingI_board_name);
            TextView timeI = view.findViewById(R.id.home_postingI_time);
            TextView likedI = view.findViewById(R.id.home_postingI_liked);
            ImageView image = view.findViewById(R.id.home_postingI_image);

            writerI.setText(postings.getWriter());
            contentI.setText(postings.getShortContent());
            boardNameI.setText(postings.getBoardName());
            timeI.setText(postings.getWrittenTime());
            likedI.setText("" + postings.getLiked());
            image.setImageURI(Uri.parse(postings.getImage()));
        }

        return view;
    }

    public void addItem(HomePostings homePostings) {
        postingList.add(homePostings);
    }

}
