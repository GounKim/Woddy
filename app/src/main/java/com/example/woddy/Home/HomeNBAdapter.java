package com.example.woddy.Home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Posting;
import com.example.woddy.R;

import java.util.ArrayList;

// Home Notice Board Adapter
public class HomeNBAdapter extends BaseAdapter {
    private ArrayList<Posting> notices;

    HomeNBAdapter(ArrayList<Posting> notice) {
        this.notices = notice;
    }

    @Override
    public int getCount() {
        return notices.size();
    }

    @Override
    public Posting getItem(int index) {
        return notices.get(index);
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

        Posting notice = getItem(index);

        title.setText(notice.getTitle());
        content.setText(notice.getContent());

        return view;
    }
}
