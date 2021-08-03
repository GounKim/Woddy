package com.example.woddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.ChattingInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static androidx.recyclerview.widget.RecyclerView.*;

public class ChattingListAdapter extends RecyclerView.Adapter<ChattingListAdapter.clHolder> {
    private Context clContext;  //ChattingList Context
    private ArrayList<ChattingInfo> chattingInfos;
    private String user;


    ChattingListAdapter(Context context, String user) {
        this.clContext = context;
        this.user = user;
        this.chattingInfos = new ArrayList<>();
    }

    public void addItem(ChattingInfo chattingInfo) {
        chattingInfos.add(chattingInfo);
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

                        intent.putExtra("USER", user);
                        intent.putExtra("CHATTER", getChatter(pos, user));
                        intent.putExtra("ROOMNUM", chattingInfos.get(pos).getRoomNumber());

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
        holder.getChatterName().setText(getChatter(position, user));
        holder.getRecentChatt().setText(chattingInfos.get(position).getRecentMsg());
        //holder.getChatterImage().setImageURI(chattingInfos.get(position).getChatterImg());
    }

    @Override
    public int getItemCount() {
        return chattingInfos.size();
    }

    // 대화 상대 분리하기 String[0] = user, String[1] = chatter
    private String getChatter(int pos, String user) {
        List<String> list = (List<String>) chattingInfos.get(pos).getParticipant();
        String chatter = list.get(0).equals(user) ? list.get(1) : list.get(0);
        return chatter;
    }

    public void setCurMsg(ChattingInfo chatInfo) {
        if (chattingInfos.size() == 0) {
            addItem(chatInfo);
        } else {
            for (ChattingInfo info : chattingInfos) { //for문을 통한 전체출력
                if (info.getRoomNumber().equals(chatInfo.getRoomNumber())) {
                    chattingInfos.add(0, chatInfo);
                    chattingInfos.remove(info);
                    notifyDataSetChanged();
                }
            }
        }
    }

}

