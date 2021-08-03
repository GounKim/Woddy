package com.example.woddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ChattingListAdapter extends RecyclerView.Adapter<ChattingListAdapter.clHolder> {
    Context clContext;  //ChattingList Context
    ArrayList<String> chatterNameList;
    ArrayList<String> recentChatList;
    ArrayList<Uri> cImagePList;  // Chatter Image Path List


    ChattingListAdapter(Context context, ArrayList<String> chatterList, ArrayList<String> rChatList, ArrayList<Uri> cImageList) {
        this.clContext = context;
        this.chatterNameList = chatterList;
        this.recentChatList = rChatList;
        this.cImagePList = cImageList;
    }

    public void addItem(String chatter, String rChatt, Uri cImage) {
        chatterNameList.add(chatter);
        recentChatList.add(rChatt);
        cImagePList.add(cImage);
        notifyDataSetChanged();
    }

    public class clHolder extends ViewHolder {
        private final TextView chatterName;
        private final TextView recentChat;
        private final ImageView chatterImage;

        public clHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatterName = itemView.findViewById(R.id.chatter_name);
            recentChat = itemView.findViewById(R.id.recent_chat);
            chatterImage = itemView.findViewById(R.id.chatter_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(clContext, ChattingRoom.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("CHATTER", chatterNameList.get(pos));

                        clContext.startActivity(intent);
                    }
                }
            });

        }

        public TextView getChatterName() {
            return chatterName;
        }
        public TextView getRecentChatt() {
            return recentChat;
        }
        public ImageView getChatterImage() {
            return chatterImage;
        }
    }

    @NonNull
    @NotNull
    @Override
    public clHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_list_recycler, parent, false);

        return new clHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull clHolder holder, int position) {
        holder.getChatterName().setText(chatterNameList.get(position));
        holder.getRecentChatt().setText(recentChatList.get(position));
        holder.getChatterImage().setImageURI(cImagePList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatterNameList.size();
    }
}

