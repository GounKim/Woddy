package com.example.woddy.Chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.Entity.ChattingMsg;
import com.example.woddy.R;
import com.google.type.DateTime;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChattingRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int USER_MESSAGE = 0;
    public static final int CHATTER_MESSAGE = 1;
    public static final int DATE_MARK = 2;
    public static final int INTRO_MARK = 3;

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
        } else if (viewType == DATE_MARK){
            view = inflater.inflate(R.layout.chat_room_recycler_date, parent, false);
            return new DateMarkHolder(view);
        } else {
            view = inflater.inflate(R.layout.chat_room_recycler_date, parent, false);
            return new IntroMarkHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserMessageHolder) {
            ((UserMessageHolder)holder).userMsg.setText(chatItemList.get(position).getMessage());
            ((UserMessageHolder)holder).sendTime.setText(timestamp(chatItemList.get(position).getWrittenTime()));
        } else if (holder instanceof ChatterMessageHolder) {
            ((ChatterMessageHolder)holder).chatterMsg.setText(chatItemList.get(position).getMessage());
            ((ChatterMessageHolder)holder).getTime.setText(timestamp(chatItemList.get(position).getWrittenTime()));
        } else if (holder instanceof DateMarkHolder) {
            ((DateMarkHolder)holder).dateMark.setText(datestamp(chatItemList.get(position).getWrittenTime())); // 요일로 변경 필요
        } else {
            ((IntroMarkHolder)holder).introMark.setText(chatItemList.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChattingMsg item = chatItemList.get(position);

        if (item.getWriter() != null && item.getWriter().equals(user)) {
            return USER_MESSAGE;
        }
        else if (item.getWriter() != null && item.getWriter().equals(chatter)) {
            return CHATTER_MESSAGE;
        }
        else if (item.getWrittenTime() == null){
            return INTRO_MARK;
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

    public class IntroMarkHolder extends RecyclerView.ViewHolder {
        TextView introMark;
        public IntroMarkHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            introMark = itemView.findViewById(R.id.date_mark);
        }
    }

    private String timestamp(Date date) {    // 타임스탬프 생성
        TimeZone timeZone;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREAN);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    private String datestamp(Date date) {    // 타임스탬프 생성
        TimeZone timeZone;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.KOREAN);
        timeZone = TimeZone.getTimeZone("Asia/Seoul");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }
}
