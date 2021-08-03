//package com.example.woddy;
//
//import android.app.LauncherActivity;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.FrameLayout;
//import android.widget.GridLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//
//import java.util.ArrayList;
//import java.util.zip.Inflater;
//
//import static android.content.ContentValues.TAG;
//
//public class HomeListAdapter extends BaseAdapter {
//
//    //private ArrayList<HomeList> list = new ArrayList<>();
//    private String[] homeBoards = new String[]{"알려드려요", "현재 인기글", "최신글", "내가 즐겨찾기한 게시판"};
//    private ArrayList<HomeBoardAdapter> pAdapter = new ArrayList<>();
//
//    @Override
//    public int getCount() {
//        return pAdapter.size();
//    }
//
//    @Override
//    public BaseAdapter getItem(int position) {
//        return pAdapter.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        Context context = viewGroup.getContext();
//        ViewHolder holder;
//
//        if (view == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.home_item, viewGroup,false);
//            holder = new ViewHolder();
//
//            holder.boardName = view.findViewById(R.id.home_board_name);
//            holder.itemMore = view.findViewById(R.id.home_item_more);
//            holder.boardListView = view.findViewById(R.id.home_board_listView);
//            //holder.boardListView.setVerticalScrollBarEnabled(false);
//            view.setTag(holder);
//        }
//        else {
//            holder = (ViewHolder) view.getTag();
//        }
//        Log.d(TAG, "position = " + position + ", publisher = " + getItem(position));
//
//        HomeBoardAdapter adapter = pAdapter.get(position);
//
//        if (adapter != null) {
//            holder.boardName.setText(homeBoards[position]);
//            holder.boardListView.setAdapter(pAdapter.get(position));
//        }
//
//        Log.d(TAG, "getView----------");
//
//        return view;
//    }
//
//    static class ViewHolder {
//        TextView boardName;
//        TextView itemMore;
//        ListView boardListView;
//    }
//
//    public void addItem(HomeBoardAdapter boardAdapter) {
//        pAdapter.add(boardAdapter);
//    }
//
//    public static void setListViewHeight(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//
//        int height = 0;
//        int width = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(width, View.MeasureSpec.UNSPECIFIED);
//            height += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = height + (listView.getDividerHeight() * (listAdapter.getCount() -1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }
//
///*
//    public void initPostings(HomeBoardAdapter adapter) {
//        HomePostings p1 = new HomePostings("writer1", "content1", "board1", 100,"18:30");
//        adapter.addItem(p1);
//        HomePostings p2 = new HomePostings("writer2", "content2", "board2", 123,"8:30", String.valueOf(R.drawable.sample_image2));
//        adapter.addItem(p2);
//        HomePostings p3 = new HomePostings("writer3", "content3", "board1", 10,"5:51");
//        adapter.addItem(p3);
//        HomePostings p4 = new HomePostings("writer4", "content4", "board3", 140,"13:13", String.valueOf(R.drawable.sample_image4));
//        adapter.addItem(p4);
//        HomePostings p5 = new HomePostings("writer5", "content5", "board2", 301,"20:02");
//        adapter.addItem(p5);
//
//        HomePostings[] hplist = new HomePostings[]{p1, p2, p3, p4, p5};
//        HomeList hl1 = new HomeList(homeBoards[0], hplist);
//        addItem(hl1);
////        HomeList hl2 = new HomeList(homeBoards[1], hplist);
////        addItem(hl2);
////        HomeList hl3 = new HomeList(homeBoards[2], hplist);
////        addItem(hl3);
////        HomeList hl4 = new HomeList(homeBoards[3], hplist);
////        addItem(hl4);
//    }
//
// */
//}
