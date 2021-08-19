package com.example.woddy.Chatting;

import static android.content.ContentValues.TAG;

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

import com.bumptech.glide.Glide;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.clHolder> {
    private ArrayList<ChattingInfo> chattingInfos;
    private String user;

    ChattingAdapter(String user) {
        this.user = user;
        this.chattingInfos = new ArrayList<>();
    }

    public void addItem(ChattingInfo chattingInfo) {
        chattingInfos.add(chattingInfo);
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<ChattingInfo> chattingInfos){
        this.chattingInfos = chattingInfos;
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
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, ChattingRoom.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        String[] chatterInfo = getChatterInfo(pos, user);
                        intent.putExtra("USER", user);
                        intent.putExtra("CHATTER", chatterInfo[0]);
                        intent.putExtra("ROOMNUM", chattingInfos.get(pos).getRoomNumber());
                        intent.putExtra("IMAGE", chatterInfo[1]);

                        context.startActivity(intent);
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
        ChattingInfo chatInfo = chattingInfos.get(position);

        String[] chatterInfo = getChatterInfo(position, user);
        holder.getChatterName().setText(chatterInfo[0]);
        holder.getRecentChatt().setText(chatInfo.getRecentMsg());

//        if (chatterInfo[1] != null | chatterInfo[1] != "") {
//            FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
//            StorageReference storageRef = storage.getReference(chatterInfo[1]); // 스토리지 공간을 참조해서 이미지를 가져옴
//
//            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        Glide.with(holder.itemView.getContext())
//                                .load(task.getResult())
//                                .into(holder.chatterImage);
//                    } else {
//                        Log.d(TAG, "Image Load in MyPage failed.", task.getException());
//                    }
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return chattingInfos.size();
    }

    // 대화 상대 분리하기 (리턴값 index = 0: 채팅 상대 이름, index = 1: 채팅 상대 이미지)
    private String[] getChatterInfo(int pos, String user) {
        String[] chatterInfo = new String[2];
        ChattingInfo chatInfo = chattingInfos.get(pos);

        List<String> nick = (List<String>) chatInfo.getParticipant();
//        List<String> img = (List<String>) chatInfo.getParticipantImg();

        if (nick.get(0).equals(user)) {
            chatterInfo[0] = nick.get(1);
//            chatterInfo[1] = img.get(1);
        } else {
            chatterInfo[0] = nick.get(0);
//            chatterInfo[1] = img.get(0);
        }

        return chatterInfo;
    }

    public void setCurMsg(ChattingInfo chatInfo) {
        Boolean find = false;

        if (chattingInfos.size() == 0) {
            addItem(chatInfo);
        } else {
            Iterator<ChattingInfo> iter = chattingInfos.iterator();
            while (iter.hasNext()) {
                ChattingInfo info = iter.next();
                if (info.getRoomNumber().equals(chatInfo.getRoomNumber())) {
                    iter.remove();
                    find = true;
                }
            }
            if(find) {
                chattingInfos.add(0, chatInfo);
                notifyDataSetChanged();
            } else {
                addItem(chatInfo);
            }
        }
    }

}

