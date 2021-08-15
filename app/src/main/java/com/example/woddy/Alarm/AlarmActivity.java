package com.example.woddy.Alarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woddy.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarm_recyclerview = findViewById(R.id.alarm_recyclerview);
        alarm_recyclerview.setAdapter(new AlarmRecyclerViewAdapter());
        alarm_recyclerview.setLayoutManager(new LinearLayoutManager(this));
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
            return null;
//            return CustomViewHolder(view);
//
//            class CustomViewHolder(View view) extends RecyclerView.ViewHolder(view){
//
//            }
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
                    String str0 = alarmDTOList.get(position).userId + getString(R.string.alarm_like);
                    text_message.setText(str0);
                case 1 :
                    //image.setImageResource(R.drawable.ic_baseline_liked_no);
                    String str1 = alarmDTOList.get(position).userId + getString(R.string.alarm_comment)
                            +" of "+alarmDTOList.get(position).message;
                    text_message.setText(str1);
                case 2 :
                    //image.setImageResource(R.drawable.ic_baseline_liked_no);
                    String str2 = alarmDTOList.get(position).userId + getString(R.string.alarm_chatting);
                    text_message.setText(str2);
            }
            text_id.setVisibility(View.INVISIBLE);

        }

        @Override
        public int getItemCount() {
            return alarmDTOList.size();
        }
    }
}