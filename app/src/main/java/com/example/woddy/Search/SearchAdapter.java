package com.example.woddy.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<SearchData> dataArrayList;
    private Context context;

    // creating a constructor for our variables.
    public SearchAdapter(ArrayList<SearchData> dataArrayList, Context context) {
        this.dataArrayList = dataArrayList;
        this.context = context;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<SearchData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        SearchData modal = dataArrayList.get(position);
       // holder.searchTitle.setText(modal.getSearchTitle());
       // holder.searchMessage.setText(modal.getSearchMessage());
       // holder.searchBoardName.setText(modal.getSearchBoardName());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView searchTitle, searchMessage,searchBoardName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            searchTitle = itemView.findViewById(R.id.search_item_title);
            searchMessage = itemView.findViewById(R.id.search_item_message);
            searchBoardName = itemView.findViewById(R.id.search_item_boardName);
        }
    }
}
