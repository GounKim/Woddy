package com.example.woddy.Alarm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.woddy.Chatting.ChattingRoom;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.Posting.ShowPosting;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {

    FirestoreManager manager = new FirestoreManager();

    String TAG = "AlarmActivity";

    RecyclerView alarm_recyclerview;
    SwipeRefreshLayout swipeRefresh;

    Toolbar mToolbar;
    TextView toolbarTitle;
    ImageView toolbarLogo;

    private Boolean useToolbar = true;
    private Boolean useBackButton = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        toolbarTitle.setText("알림");

        swipeRefresh = findViewById(R.id.swipeRefresh);
        alarm_recyclerview = findViewById(R.id.alarm_recyclerview);
        RecyclerView.Adapter adapter = new AlarmRecyclerViewAdapter();

        adapter.setHasStableIds(true);
        alarm_recyclerview.setAdapter(adapter);
        alarm_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                alarm_recyclerview.setAdapter(adapter);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    //리사이클뷰 설정
    class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<AlarmDTO> alarmDTOList = new ArrayList<AlarmDTO>();

        AlarmRecyclerViewAdapter(){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Create a reference to the cities collection
            Query query = FirebaseFirestore.getInstance().collection("alarms").orderBy("timestamp", Query.Direction.DESCENDING);
            query.whereEqualTo("destinationUid",uid)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            alarmDTOList.clear();

                            if (error != null) {
                                Log.w(TAG, "Listen failed.", error);
                                return;
                            }

                            if(value == null)return;
                            else if (value != null) {
                                for(DocumentSnapshot snapshot : value.getDocuments()){
                                    alarmDTOList.add(snapshot.toObject(AlarmDTO.class));
                                }
                                notifyDataSetChanged();
                            }

                        }
                    });
        }

        @NonNull
        @Override
        //알림 목록의 아이템 설정
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm,parent,false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        //알림 목록 설정
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            View view = holder.itemView;
            TextView text_message = view.findViewById(R.id.alarmitem_textview_message);
            AlarmDTO currentAlarm = alarmDTOList.get(position);

            //알림 종류 마다 다른 화면 전환
            switch (currentAlarm.kind){
                case 0 : //좋아요 알림
                    String str0 = currentAlarm.nickname + getString(R.string.alarm_like);
                    text_message.setText(str0);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(view.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("documentPath", currentAlarm.getPostingPath());
                                view.getContext().startActivity(intent);
                            }catch(RuntimeException e){
                                Intent intent = new Intent(view.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("documentPath", currentAlarm.getPostingPath());
                                view.getContext().startActivity(intent);
                            }
                        }
                    });
                    break;
                case 1 : //댓글 알림
                    String str1 = currentAlarm.nickname + getString(R.string.alarm_comment)
                            +System.lineSeparator()+'"'+currentAlarm.message+'"';
                    text_message.setText(str1);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(view.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("documentPath", currentAlarm.getPostingPath());
                                view.getContext().startActivity(intent);
                            }catch(RuntimeException e){
                                Intent intent = new Intent(view.getContext(), ShowPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("documentPath", currentAlarm.getPostingPath());
                                view.getContext().startActivity(intent);
                            }
                        }
                    });
                    break;
                case 2 : //채팅 알림
                    String str2 = currentAlarm.nickname + getString(R.string.alarm_chatting);
                    text_message.setText(str2);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            manager.findUserWithNick(currentAlarm.nickname)
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            String chatterUid = queryDocumentSnapshots.getDocuments().get(0).getId();
                                            Log.d("alarm", chatterUid);

                                            Intent intent = new Intent(view.getContext(), ChattingRoom.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("ROOMNUM", currentAlarm.getRoomNum());
                                            intent.putExtra("USER", FirebaseAuth.getInstance().getCurrentUser().getUid());//사용자
                                            intent.putExtra("CHATTER", chatterUid);
                                            view.getContext().startActivity(intent);
                                        }
                                    });
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            String chatterUid = task.getResult().getDocuments().get(0).get
//                                            Log.d("alarm", chatterUid);
//
//                                            Intent intent = new Intent(view.getContext(), ChattingRoom.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            intent.putExtra("ROOMNUM", currentAlarm.getRoomNum());
//                                            intent.putExtra("USER", FirebaseAuth.getInstance().getCurrentUser().getUid());//사용자
//                                            intent.putExtra("CHATTER", chatterUid);
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("error", "fail");
//                                        }
//                                    });
                        }
                    });
                    break;
            }
        }
        @Override
        public int getItemCount() {
            return alarmDTOList.size();
        }
    }

//    //채팅 알림일 때 화면 전환
//    public void chatIntent(View view, AlarmDTO currentAlarm) {
//
//        String chatterUid = manager.findUserWithNick(currentAlarm.nickname)
//                .getResult().getDocuments().get(0).getId();
//
//        intent.putExtra("ROOMNUM", currentAlarm.getRoomNum());
//        intent.putExtra("USER", FirebaseAuth.getInstance().getCurrentUser().getUid());//사용자
//        intent.putExtra("CHATTER", currentAlarm.getNickname());
//
//        manager.findUserWithUid(currentAlarm.destinationUid)
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        String nickname = (String) documentSnapshot.get("nickname"); //푸시 받는 사람 닉네임
//                        Intent intent = new Intent(view.getContext(), ChattingRoom.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("ROOMNUM", currentAlarm.getRoomNum());
//                        intent.putExtra("USER", nickname);
//                        intent.putExtra("CHATTER", currentAlarm.getNickname());
//                        Log.d("onclick", currentAlarm.getRoomNum());
//                        view.getContext().startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "Fail to find writer info");
//                    }
//                });
//    }

    // 툴바 기본 설정
    @Override
    public void setContentView(int layoutResID) {
        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_alarm, null);
        super.setContentView(fullView);

        mToolbar = findViewById(R.id.chatting_room_toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarLogo = findViewById(R.id.toolbar_logo);
        ;

        // 툴바 사용 여부 결정
        if (useToolbar()) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);    // 커스터마이징하기
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(useBackButton());  // 뒤로가기 버튼
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 타이틀 설정
    protected void setMyTitle(String title) {
        toolbarTitle.setText(title);
    }

    // 툴바 사용여부 (사용 기본)
    protected boolean useToolbar() {
        return useToolbar;
    }

    // 뒤로가기 버튼 사용여부 (사용 기본)
    protected boolean useBackButton() {
        return useBackButton;
    }
}