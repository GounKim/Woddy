package com.example.woddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.ChattingMsg;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChattingRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int USER_MESSAGE = 0;
    public static final int CHATTER_MESSAGE = 1;
    public static final int DATE_MARK = 2;

    ArrayList<ChattingMsg> chatItemList;
    String chatter;
    String user;

    public ChattingRoomAdapter(String chatter, String user) {
        this.chatter = chatter;
        this.user = user;
        this.chatItemList = new ArrayList<>();
    }

    public void addItem(ChattingMsg chatItem) {
        chatItemList.add(chatItem);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == USER_MESSAGE) {
            view = inflater.inflate(R.layout.chat_room_recycler_user, parent, false);
            return new UserMessageHolder(view);
        } else if (viewType == CHATTER_MESSAGE) {
            view = inflater.inflate(R.layout.chat_room_recycler_chatter, parent, false);
            return new ChatterMessageHolder(view);
        } else {
            view = inflater.inflate(R.layout.chat_room_recycler_date, parent, false);
            return new DateMarkHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserMessageHolder) {
            ((UserMessageHolder)holder).userMsg.setText(chatItemList.get(position).getMessage());
            ((UserMessageHolder)holder).sendTime.setText(chatItemList.get(position).getWrittenTime());
        } else if (holder instanceof ChatterMessageHolder) {
            ((ChatterMessageHolder)holder).chatterMsg.setText(chatItemList.get(position).getMessage());
            ((ChatterMessageHolder)holder).getTime.setText(chatItemList.get(position).getWrittenTime());
        } else {
            ((DateMarkHolder)holder).dateMark.setText(chatItemList.get(position).getWrittenTime()); // 요일로 변경 필요
        }
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatItemList.get(position).getWriter().equals(user)) {
            return USER_MESSAGE;
        }
        else if (chatItemList.get(position).getWriter().equals("userA")) {
            return CHATTER_MESSAGE;
        }
        else {
            return DATE_MARK;
        }
    }

    public class UserMessageHolder extends RecyclerView.ViewHolder {
        TextView userMsg;   // 사용자가 보낸 메시지
        TextView sendTime;  // 메시지 보낸 시간

        public UserMessageHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            userMsg = itemView.findViewById(R.id.user_message);
            sendTime = itemView.findViewById(R.id.send_time);
        }
    }

    public class ChatterMessageHolder extends RecyclerView.ViewHolder {
        TextView chatterMsg;    // 상대방이 보낸 메시지
        TextView getTime;   // 메시지 받은 시간

        public ChatterMessageHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            chatterMsg = itemView.findViewById(R.id.chatter_message);
            getTime = itemView.findViewById(R.id.receive_time);
        }
    }

    public class DateMarkHolder extends RecyclerView.ViewHolder {
        TextView dateMark;
        public DateMarkHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            dateMark = itemView.findViewById(R.id.date_mark);
        }
    }
}
