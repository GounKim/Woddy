package com.example.woddy.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {

    FirestoreManager manager = new FirestoreManager();

    private ArrayList<String> homeBoards;
    private ArrayList<HomeBoardAdapter> pAdapter;

    public HomeAdapter() {
        this.pAdapter = new ArrayList<>();
        this.homeBoards = new ArrayList<>();
    }

    public void addItem(String name, HomeBoardAdapter adapter) {
        homeBoards.add(name);
        pAdapter.add(adapter);
//        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final TextView boardName;
        private final TextView itemMore;
        private final ListView listView;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);

            boardName = itemView.findViewById(R.id.home_board_name);
            itemMore = itemView.findViewById(R.id.home_item_more);
            listView = itemView.findViewById(R.id.home_board_listView);
        }

        public TextView getBoardName() {
            return boardName;
        }
        public TextView getItemMore() {
            return itemMore;
        }
        public ListView getListView() {
            return listView;
        }
    }


    @NonNull
    @NotNull
    @Override
    public HomeAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_item, parent, false);

        Log.d(TAG, "in-----------");

        return new HomeAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeAdapter.Holder holder, int position) {
        holder.boardName.setText(homeBoards.get(position));
        holder.listView.setAdapter(pAdapter.get(position));
        setListViewHeight(holder.listView);
    }

    @Override
    public int getItemCount() {
        return pAdapter.size();
    }

    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int height = 0;
        int width = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(width, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height + (listView.getDividerHeight() * (listAdapter.getCount() -1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
