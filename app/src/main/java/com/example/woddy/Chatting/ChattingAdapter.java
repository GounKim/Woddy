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
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.ChattingInfo;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.clHolder> {
    FirestoreManager manager = new FirestoreManager();

    private ArrayList<ChattingInfo> chattingInfos;  // 채팅방 목록
    String userUid;

    // 생성자
    ChattingAdapter(String userUid) {
        this.userUid = userUid;
        this.chattingInfos = new ArrayList<>();
    }

    // 채팅방(item) 하나 추가하기
    public void addItem(ChattingInfo chattingInfo) {
        chattingInfos.add(chattingInfo);
        notifyDataSetChanged();
    }

    public class clHolder extends ViewHolder {
        private final TextView chatterName; // 상대방 이름
        private final TextView recentChat;  // 최근 메시지
        private final ImageView chatterImage;   // 상대방 이미지

        public clHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            chatterName = itemView.findViewById(R.id.chatter_name);
            recentChat = itemView.findViewById(R.id.recent_chat);
            chatterImage = itemView.findViewById(R.id.chatter_image);

            // 채팅방 하나 클릭할 경우 채팅방으로 이동
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition(); // 클릭한 채팅방의 화면상 위치 가져오기
                    if (pos != RecyclerView.NO_POSITION) {
                        Context context = itemView.getContext();
                        Intent intent = new Intent(context, ChattingRoom.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        // 채팅방(chattingRoom)에 전달할 정보 (채팅 상대방이름, 방 번호, 상대방 이미지)
                        String[] chatterInfo = getChatterInfo(pos);
                        intent.putExtra("CHATTER", getChatterName().getText());
                        intent.putExtra("ROOMNUM", chattingInfos.get(pos).getRoomNumber());
                        intent.putExtra("IMAGE", chatterInfo[1]);

                        context.startActivity(intent);  // 화면이동
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
        // 현재 위치의 채팅방 정보를 가져와서 설정(상대방이름, 최근 메시지, 상대방이미지)
        ChattingInfo chatInfo = chattingInfos.get(position);
        String[] chatterInfo = getChatterInfo(position);
        holder.getRecentChatt().setText(chatInfo.getRecentMsg());

        // 채팅 상대방 uid로 상대방의 nickname을 알아냄
        manager.findUserWithUid(chatterInfo[0])
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            // 찾은 nickname을 TextView에 출력함.
                            String chatterNick = task.getResult().get("nickname").toString();
                            holder.getChatterName().setText(chatterNick);
                        } else {
                            Log.d(TAG, "finding chatter failed.");
                        }
                    }
                });

        // 상대방의 이미지가 설정이 되어있을 경우(기본설정이 아닐 때)
        if (chatterInfo[1] != null) {
            // 스토리지 공간을 참조해서 이미지를 가져옴
            FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
            StorageReference storageRef = storage.getReference(chatterInfo[1]);

            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // 가져온 이미지를 imageView에 보여줌
                        Glide.with(holder.itemView.getContext())
                                .load(task.getResult())
                                .into(holder.chatterImage);
                    } else {
                        Log.d(TAG, "Image Load in MyPage failed.", task.getException());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return chattingInfos.size();
    }

    // chattingInfo에는 대화상대 2명이 존재하기 때문에 채팅 상대방의 정보만 가져올 필요가 있음.
    // 대화 상대 분리하기 (리턴값 index = 0: 채팅 상대 이름, index = 1: 채팅 상대 이미지)
    private String[] getChatterInfo(int pos) {
        String[] chatterInfo = new String[2];
        ChattingInfo chatInfo = chattingInfos.get(pos);

        List<String> nick = (List<String>) chatInfo.getParticipant();
        List<String> img = (List<String>) chatInfo.getParticipantImg();

        if (nick.get(0).equals(userUid)) {
            chatterInfo[0] = nick.get(1);
            chatterInfo[1] = img.get(1);
        } else {
            chatterInfo[0] = nick.get(0);
            chatterInfo[1] = img.get(0);
        }

        return chatterInfo;
    }

    // 최신 메시지 변경적용(메시지를 받으면 가장 위로 위치변경)
    public void setCurMsg(ChattingInfo chatInfo) {
//        Boolean find = false;   // 최신 메시지를 받은 채팅방이 존재하는지 확인

        if (chattingInfos.size() == 0) {    // 채팅방이 아무것도 없을 경우 - 추가
            addItem(chatInfo);
        } else {    // 채팅방이 존재할경우 - 최신 메시지를 받은 채팅방 존재 여부 확인
            // Iterator을 이용 - arrayList 내부 삭제 작업 시 오류 방지
            Iterator<ChattingInfo> iter = chattingInfos.iterator();
            while (iter.hasNext()) {
                ChattingInfo info = iter.next();
                // 이미 존재하는 방일 경우
                if (info.getRoomNumber().equals(chatInfo.getRoomNumber())) {
                    iter.remove();
//                    find = true;    // 찾았음을 알림
                }
            }
            chattingInfos.add(0, chatInfo);
            notifyDataSetChanged();
        }
    }

}


//            if (find) { // 찾았으면 채팅방 가장 앞에 추가
//                chattingInfos.add(0, chatInfo);
//                notifyDataSetChanged();
//            } else {    // 못찾았으면 뒤에 추가
//                addItem(chatInfo);
//            }
