package com.example.woddy.MyPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woddy.Entity.User;
import com.example.woddy.PostWriting.AddWritingsActivity;
import com.example.woddy.R;


public class MyPageFragment extends Fragment implements View.OnClickListener {
    TextView nickName;
    TextView introduce;
    TextView local;
    ImageView userImage;
    Button updateProfile;

    public static MyPageFragment newInstance(Bundle ble) {
        Bundle bundle = ble;

        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(ble);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_page, container, false);

        nickName = view.findViewById(R.id.myPage_userNick);
        introduce = view.findViewById(R.id.myPage_userIntroduce);
        local = view.findViewById(R.id.myPage_userLocal);
        userImage = view.findViewById(R.id.chatroom_user_image);
        updateProfile = view.findViewById(R.id.myPage_btn_update_myProfile);

        updateProfile.setOnClickListener(this);

        if (getArguments() != null) {
            Bundle bundle = getArguments().getBundle("");
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myPage_btn_update_myProfile:
                Intent intent = new Intent(getContext(), UpdateProfile.class);
                startActivity(intent);
        }
    }
}