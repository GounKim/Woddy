package com.example.woddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.InitDBdata;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    TextView textView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = view.findViewById(R.id.text);
        button = view.findViewById(R.id.button2);

//        try {
//            InitDBdata init = new InitDBdata();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        textView.setText("");
        FirestoreManager firestoreManager = new FirestoreManager();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                firestoreManager.getChatRoomList("user1");
            }
        });

        return view;
    }
}