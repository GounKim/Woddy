package com.example.woddy.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.News;
import com.example.woddy.Entity.Posting;
import com.example.woddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

// Home Notice Board Adapter
public class HomeNBAdapter extends BaseAdapter {
    private ArrayList<News> news;

    public HomeNBAdapter(ArrayList<News> news) {
        this.news = news;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public News getItem(int index) {
        return news.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.home_item_notice, viewGroup, false);
        }

        LinearLayout layout = view.findViewById(R.id.home_notice_layout);
        TextView title = view.findViewById(R.id.home_notice_writer);
        TextView content = view.findViewById(R.id.home_notice_content);

        if (index != getCount() - 1) {
            layout.setBackground(ContextCompat.getDrawable(context, R.drawable.notice_border));
        }

        News news = getItem(index);

        title.setText(news.getTitle());
        content.setText(news.getContent());

        return view;
    }
}
