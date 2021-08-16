package com.example.woddy.Alarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woddy.Chatting.ChattingFragment;
import com.example.woddy.Posting.ShowImgPosting;
import com.example.woddy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {


    String TAG = "AlarmActivity";

    RecyclerView alarm_recyclerview;

    Toolbar mToolbar;
    TextView toolbarTitle;
    ImageView toolbarLogo;

    private Boolean useToolbar = true;
    private Boolean useBackButton = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarm_recyclerview = findViewById(R.id.alarm_recyclerview);
        alarm_recyclerview.setAdapter(new AlarmRecyclerViewAdapter());
        alarm_recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

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

    class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<AlarmDTO> alarmDTOList = new ArrayList<AlarmDTO>();

        AlarmRecyclerViewAdapter(){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseFirestore.getInstance().collection("alarms").whereEqualTo("destinationUid",uid)
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
                                //Log.d(TAG, "Current data: " + value.getData());
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
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm,parent,false);
            //return null;
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            View view = holder.itemView;
            ImageView image = view.findViewById(R.id.alarmitem_imageview);
            TextView text_id = view.findViewById(R.id.alarmitem_textview_id);
            TextView text_message = view.findViewById(R.id.alarmitem_textview_message);

            switch (alarmDTOList.get(position).kind){
                case 0 :
                    //image.setImageResource(R.drawable.ic_baseline_liked_no);
                    String str0 = alarmDTOList.get(position).nickname + getString(R.string.alarm_like);
                    text_message.setText(str0);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("documentPath", alarmDTOList.get(position).getPostingPath());
                            view.getContext().startActivity(intent);
                        }
                    });
                    break;
                case 1 :
                    //image.setImageResource(R.drawable.ic_baseline_liked_no);
                    String str1 = alarmDTOList.get(position).nickname + getString(R.string.alarm_comment)
                            +" of "+alarmDTOList.get(position).message;
                    text_message.setText(str1);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ShowImgPosting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("documentPath", alarmDTOList.get(position).getPostingPath());
                            view.getContext().startActivity(intent);
                        }
                    });
                    break;
                case 2 :
                    //image.setImageResource(R.drawable.ic_baseline_liked_no);
                    String str2 = alarmDTOList.get(position).nickname + getString(R.string.alarm_chatting);
                    text_message.setText(str2);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ChattingFragment.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }
                    });
                    break;
            }
            text_id.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return alarmDTOList.size();
        }
    }
}