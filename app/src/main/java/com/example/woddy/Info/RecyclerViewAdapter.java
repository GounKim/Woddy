package com.example.woddy.Info;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.Depart;
import com.example.woddy.Entity.News;
import com.example.woddy.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Depart> items;
    private ArrayList<String> documentPath;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int prePosition = -1;

    private String boardName;
//    private String tagName;

    public RecyclerViewAdapter(String boardName) {
        this.items = new ArrayList<>();
        this.documentPath = new ArrayList<>();
        this.boardName = boardName;
//        this.tagName = tagName;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_depart, parent, false);
        return new DepartRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        DepartRecyclerView departRecyclerView = (DepartRecyclerView) holder;
        departRecyclerView.onBind(items.get(position), position, selectedItems);
        departRecyclerView.setOnViewHolderItemClickListener(new OnViewHolderItemClickListener() {
            @Override
            public void onViewHolderItemClick() {
                if (selectedItems.get(position)) {
                    selectedItems.delete(position);
                } else {
                    selectedItems.delete(prePosition);
                    selectedItems.put(position, true);
                }
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                prePosition = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Depart> items, ArrayList<String> documentPath) {
        this.items = items;
        this.documentPath = documentPath;
    }

    void addItem(Depart data) {
        items.add(data);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
