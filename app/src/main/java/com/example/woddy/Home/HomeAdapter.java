package com.example.woddy.Home;

import android.content.ClipData;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int NOTICE = 0;
    private final int POPULAR = 1;
    private final int RECENT = 2;
    private final int FAVORITE = 3;

    private String[] homeBoards = new String[]{"알려드려요", "현재 인기글", "최신글", "즐겨찾기한 게시판"};
    private ArrayList<Object> adapterList;

    public HomeAdapter() {
        adapterList = new ArrayList<>();
    }

    public void addItem(Object adapter) {
        adapterList.add(adapter);
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<Object> adapterList) {
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }

    public ArrayList<Object> getItem() {
        return adapterList;
    }

    public class VerticalScrollHolder extends RecyclerView.ViewHolder {
        private final TextView boardName;
        private final TextView itemMore;
        private final ListView listView;

        public VerticalScrollHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            boardName = itemView.findViewById(R.id.home_board_name);
            boardName.bringToFront();
            itemMore = itemView.findViewById(R.id.home_item_more);
            listView = itemView.findViewById(R.id.home_board_listView);
        }
    }

    public class HorizontalScrollHolder extends RecyclerView.ViewHolder {
        private final TextView boardName;
        private final RecyclerView horiView;

        public HorizontalScrollHolder(@NonNull View itemView) {
            super(itemView);
            boardName = itemView.findViewById(R.id.home_board_nameR);
            boardName.bringToFront();
            horiView = itemView.findViewById(R.id.home_board_hori_recycler);
        }
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == FAVORITE) {
            view = inflater.inflate(R.layout.home_item_hscroll, parent, false);
            return new HorizontalScrollHolder(view);
        } else {
            view = inflater.inflate(R.layout.home_item, parent, false);
            return new VerticalScrollHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        if (position < adapterList.size()) {
            if (holder instanceof VerticalScrollHolder) {
                ((VerticalScrollHolder) holder).boardName.setText(homeBoards[position]);
                ((VerticalScrollHolder) holder).itemMore.setId(position);

                // 각 ListView에 adapter 연결
                try {
                    Object obj = adapterList.get(position);
                    String objName = obj.getClass().getSimpleName().trim();
                    if (objName.equals("HomeNBAdapter")) {
                        HomeNBAdapter nbAdapter = (HomeNBAdapter) obj;
                        ((VerticalScrollHolder) holder).listView.setAdapter(nbAdapter);
                    } else if (objName.equals("HomePBAdapter")) {
                        HomePBAdapter pbAdapter = (HomePBAdapter) obj;
                        ((VerticalScrollHolder) holder).listView.setAdapter(pbAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setListViewHeight(((VerticalScrollHolder) holder).listView);

                ((VerticalScrollHolder) holder).itemMore.setOnClickListener(new View.OnClickListener() {
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

            } else {
                ((HorizontalScrollHolder) holder).boardName.setText(homeBoards[position]);
                try {
                    Object obj = adapterList.get(position);
                    HomeFBAdapter fbAdapter = (HomeFBAdapter) obj;
                    ((HorizontalScrollHolder) holder).horiView
                            .setLayoutManager(new LinearLayoutManager(context, ((HorizontalScrollHolder) holder).horiView.HORIZONTAL, false)); // 상하 스크롤
                    ((HorizontalScrollHolder) holder).horiView.setAdapter(fbAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
