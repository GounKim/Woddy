package com.example.woddy.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.Entity.Posting;
import com.example.woddy.R;
import com.example.woddy.ShowWriting;

import java.util.ArrayList;

// Home Posting Board Adapter (popular writings, recent writings)
public class HomePBAdapter extends BaseAdapter {
    private final int WRITING_SIMPLE = 0;
    private final int WRITING_WITH_IMAGE = 1;
    private final int ITEM_VIEW_TYPE_MAX = 2;

    private ArrayList<Posting> writings;

    HomePBAdapter() {}

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

        LinearLayout layout = null;
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

                    layout = view.findViewById(R.id.home_postingS_layout);
                    writer = view.findViewById(R.id.home_postingS_title);
                    content = view.findViewById(R.id.home_postingS_content);
                    boardName = view.findViewById(R.id.home_postingS_board_name);
                    time = view.findViewById(R.id.home_postingS_time);
                    liked = view.findViewById(R.id.home_postingS_liked);

                    break;

                case WRITING_WITH_IMAGE:    // 이미지 포함형
                    view = inflater.inflate(R.layout.home_item_posting_image, viewGroup, false);

                    layout = view.findViewById(R.id.home_postingI_layout);
                    writer = view.findViewById(R.id.home_postingI_title);
                    content = view.findViewById(R.id.home_postingI_content);
                    boardName = view.findViewById(R.id.home_postingI_board_name);
                    time = view.findViewById(R.id.home_postingI_time);
                    liked = view.findViewById(R.id.home_postingI_liked);
                    imageView = view.findViewById(R.id.home_postingI_image);

                    break;
            }
        }

        Posting writing = getItem(position);

        writer.setText(writing.getTitle());
        content.setText(writing.getContent());
        boardName.setText(writing.getTag());
        time.setText("17:05");
        liked.setText(""+writing.getNumberOfLiked());
        if (writing.getPictures() != null) {
            imageView.setImageURI(Uri.parse(writing.getPictures()));
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), getItem(position) + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ShowWriting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getPictures() == null) {
            return WRITING_SIMPLE;
        } else {
            return WRITING_WITH_IMAGE;
        }
    }
}
