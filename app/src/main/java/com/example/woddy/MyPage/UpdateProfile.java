package com.example.woddy.MyPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.woddy.BaseActivity;
import com.example.woddy.Entity.User;
import com.example.woddy.R;

public class UpdateProfile extends BaseActivity {
    ImageView profileImage;
    Button btnUpdate;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        setTitle("마이페이지");

        profileImage = findViewById(R.id.updateProfile_userImage);
        btnUpdate = findViewById(R.id.updateProfile_btn_complete);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("profileImage", profileImage.toString());

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_content, MyPageFragment.newInstance(bundle)).commit();

                finish();
            }
        });

    }
}