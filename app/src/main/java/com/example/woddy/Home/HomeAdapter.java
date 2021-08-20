package com.example.woddy.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final int NOTICE = 0;
    private final int POPULAR = 1;
    private final int RECENT = 2;
    private final int FAVORITE = 3;

    private String[] homeBoards = new String[]{"알려드려요", "현재 인기글", "최신글"};
    private ArrayList<HomePBAdapter> adapterList;

    public HomeAdapter() {
        adapterList = new ArrayList<>();
    }

    public void addItem(HomePBAdapter adapter) {
        adapterList.add(adapter);
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<HomePBAdapter> adapterList) {
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }

    public ArrayList<HomePBAdapter> getItem() {
        return adapterList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView boardName;
        private final TextView itemMore;
        private final ListView listView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            boardName = itemView.findViewById(R.id.home_board_name);
            boardName.bringToFront();
            itemMore = itemView.findViewById(R.id.home_item_more);
            listView = itemView.findViewById(R.id.home_board_listView);
        }
    }


    @NonNull
    @NotNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if (position < adapterList.size()) {
            holder.boardName.setText(homeBoards[position]);
            holder.itemMore.setId(position);

            // 각 ListView에 adapter 연결
            try {
                Object obj = adapterList.get(position);
                String objName = obj.getClass().getSimpleName().trim();
                if (objName.equals("HomePBAdapter")) {
                    HomePBAdapter pbAdapter = (HomePBAdapter) obj;
                    holder.listView.setAdapter(pbAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setListViewHeight(holder.listView);

            holder.itemMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case NOTICE:
                            Toast.makeText(context, "공지", Toast.LENGTH_SHORT).show();
                            break;
                        case POPULAR:
                            Toast.makeText(context, "인기글", Toast.LENGTH_SHORT).show();
                            break;
                        case RECENT:
                            Toast.makeText(context, "최신글", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return homeBoards.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        params.height = height + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
