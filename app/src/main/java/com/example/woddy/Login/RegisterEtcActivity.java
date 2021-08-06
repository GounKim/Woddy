package com.example.woddy.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RegisterEtcActivity extends AppCompatActivity {
    EditText nicknameEditText;
    EditText addressEditText;
    Button nicknameCheckButton;
    Button nextButton;
    TextView nickAvailTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_etc);

        nicknameEditText = findViewById(R.id.nickname_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        nicknameCheckButton = findViewById(R.id.nickname_availability_check_button);
        nextButton = findViewById(R.id.next_button);
        nickAvailTextView = findViewById(R.id.nick_avail_text_view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        nicknameCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNickname();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    void register() {
        String nickname = nicknameEditText.getText().toString();

        if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "빈칸을 모두 채워주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String userUid = firebaseAuth.getCurrentUser().getUid();
            String tmpStr = addressEditText.getText().toString();
            String[] tmpStrArr = tmpStr.split(" ");
            String tmpCity = tmpStrArr[0];
            String tmpGu = tmpStrArr[1];
            String tmpDong = tmpStrArr[2];

            Map<String, Object> nickMap = new HashMap<>();
            nickMap.put("nickname", nickname);
            Map<String, Object> cityMap = new HashMap<>();
            cityMap.put("city", tmpCity);
            Map<String, Object> guMap = new HashMap<>();
            guMap.put("gu", tmpGu);
            Map<String, Object> dongMap = new HashMap<>();
            dongMap.put("dong", tmpDong);

            DatabaseReference reference = firebaseDatabase.getReference("userProfile").child(userUid);
            reference.updateChildren(nickMap);
            reference.updateChildren(cityMap);
            reference.updateChildren(guMap);
            reference.updateChildren(dongMap);

            //SignUpSuccessActivity로 화면전환, SignUpActivity는 아예 종료시켜야함.
            Intent intent = new Intent(RegisterEtcActivity.this, SignUpSuccessActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(RegisterEtcActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("error", e.getMessage().toString());
        }
    }

    void checkNickname() {
        String nick_str = nicknameEditText.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("checkOnly").child("nickname");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                Boolean check = false;
                while (child.hasNext()) {
                    if (child.next().getKey().equals(nick_str)) {
                        check = true;
                    }
                }
                if (check) {
                    nickAvailTextView.setVisibility(View.VISIBLE);
                    nickAvailTextView.setTextColor(Color.rgb(255, 105, 105));
                    nickAvailTextView.setText("중복된 닉네임입니다.");
                } else {
                    nickAvailTextView.setVisibility(View.VISIBLE);
                    nickAvailTextView.setTextColor(Color.GRAY);
                    nickAvailTextView.setText("사용 가능한 닉네임입니다.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}