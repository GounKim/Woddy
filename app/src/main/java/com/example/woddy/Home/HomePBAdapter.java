package com.example.woddy.Home;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.woddy.Entity.Posting;
import com.example.woddy.R;

import java.util.ArrayList;

// Home Posting Board Adapter (popular writings, recent writings)
public class HomePBAdapter extends BaseAdapter {
    private final int WRITING_SIMPLE = 0;
    private final int WRITING_WITH_IMAGE = 1;
    private final int ITEM_VIEW_TYPE_MAX = 2;

    private Posting[] writings;

    HomePBAdapter(Posting[] writing) {
        this.writings = writing;
    }

    @Override
    public int getCount() {
        return writings.length;
    }

    @Override
    public Posting getItem(int index) {
        return writings[index];
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        int viewType = getItemViewType(index) ;
        TextView writer = null;
        TextView content = null;
        TextView boardName = null;
        TextView time = null;
        TextView liked = null;
        ImageView imageView = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            switch (viewType) {
                case WRITING_SIMPLE:    // 기본형
                    view = inflater.inflate(R.layout.home_item_posting_simple, viewGroup, false);

                    writer = view.findViewById(R.id.home_postingS_writer);
                    content = view.findViewById(R.id.home_postingS_content);
                    boardName = view.findViewById(R.id.home_postingS_board_name);
                    time = view.findViewById(R.id.home_postingS_time);
                    liked = view.findViewById(R.id.home_postingS_liked);

                    break;

                case WRITING_WITH_IMAGE:    // 이미지 포함형
                    view = inflater.inflate(R.layout.home_item_posting_image, viewGroup, false);

                    writer = view.findViewById(R.id.home_postingI_writer);
                    content = view.findViewById(R.id.home_postingI_content);
                    boardName = view.findViewById(R.id.home_postingI_board_name);
                    time = view.findViewById(R.id.home_postingI_time);
                    liked = view.findViewById(R.id.home_postingI_liked);
                    imageView = view.findViewById(R.id.home_postingI_image);

                    break;
            }
        }

        Posting writing = getItem(index);

        writer.setText(writing.getWriter());
        content.setText(writing.getContent());
        boardName.setText(writing.getTag());
        time.setText("17:05");
        liked.setText("200");
        if (writing.getPictures() != null) {
            imageView.setImageURI(Uri.parse(writing.getPictures()));
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int index) {
        if (getItem(index).getPictures() == null) {
            return WRITING_SIMPLE;
        } else {
            return WRITING_WITH_IMAGE;
        }
    }
}
