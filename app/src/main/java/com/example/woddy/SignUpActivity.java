package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SignUpActivity extends AppCompatActivity {
    EditText emailEditText;
    EditText pw1EditText;
    EditText pw2EditText;
    EditText nicknameEditText;
    EditText addressEditText;
    Button idCheckButton;
    Button nicknameCheckButton;
    Button nextButton;
    TextView idAvailTextView;
    TextView pwAvailTextView;
    TextView nickAvailTextView;
    Boolean idCheck = false, pwCheck = false, nickCheck = false;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.email_edit_text);
        pw1EditText = findViewById(R.id.password_edit_text);
        pw2EditText = findViewById(R.id.password_confirm_edit_text);
        nicknameEditText = findViewById(R.id.nickname_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        idCheckButton = findViewById(R.id.id_availability_check_button);
        nicknameCheckButton = findViewById(R.id.nickname_availability_check_button);
        nextButton = findViewById(R.id.next_button);

        idAvailTextView = findViewById(R.id.id_avail_text_view);
        pwAvailTextView = findViewById(R.id.pw_avail_text_view);
        nickAvailTextView = findViewById(R.id.nick_avail_text_view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        pw2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwAvailTextView.setVisibility(View.VISIBLE);
                if (pw1EditText.getText().toString().equals(pw2EditText.getText().toString())) {
                    pwAvailTextView.setTextColor(Color.GRAY);
                    pwAvailTextView.setText("비밀번호가 일치합니다.");
                    pwCheck = true;
                } else {
                    pwAvailTextView.setTextColor(Color.RED);
                    pwAvailTextView.setText("비밀번호가 일치하지않습니다.");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        idCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkID();
            }
        });
        nicknameCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNickname();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

//        if (idCheck && pwCheck && nickCheck) {
//            Log.d("taggg", "ggggg");
//            addressEditText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    nextButton.setBackground(getDrawable(R.drawable.button_round_shape));
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
    }

    void checkID() {
        String email_str = emailEditText.getText().toString();
        email_str = email_str.replaceAll("\\.", "1");
        String finalEmail_str = email_str;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("checkOnly").child("email");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                Boolean check = false;
                while (child.hasNext()) {
                    if (child.next().getKey().equals(finalEmail_str)) {
                        check = true;
                    }
                }
                if (check) {
                    idAvailTextView.setVisibility(View.VISIBLE);
                    idAvailTextView.setTextColor(Color.rgb(255, 105, 105));
                    idAvailTextView.setText("중복된 이메일입니다.");
                } else {
                    idAvailTextView.setVisibility(View.VISIBLE);
                    idAvailTextView.setTextColor(Color.GRAY);
                    idAvailTextView.setText("사용 가능한 이메일입니다.");
                    idCheck = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                    nickCheck = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void signUp() {
        String email = emailEditText.getText().toString();
        String pw1 = pw1EditText.getText().toString();
        String pw2 = pw2EditText.getText().toString();
        String nickname = nicknameEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw1) ||
                TextUtils.isEmpty(pw2) || TextUtils.isEmpty(nickname) || TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "빈칸을 모두 채워주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, pw1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String uid = task.getResult().getUser().getUid();
                                UserProfile userProfile = new UserProfile();

                                userProfile.userID = email;
                                userProfile.userPW = pw1;
                                userProfile.nickname = nickname;
                                userProfile.women = false;
                                //address
                                String tmpStr = addressEditText.getText().toString();
                                String[] tmpStrArr = tmpStr.split(" ");
                                userProfile.city = tmpStrArr[0];
                                userProfile.gu = tmpStrArr[1];
                                userProfile.dong = tmpStrArr[2];

                                firebaseDatabase.getReference().child("userProfile").child(uid).setValue(userProfile);

                                //중복 체크를 위한 데이터베이스에 저장 - email
                                String tmpEmail = email.replaceAll("\\.", "1");
                                Map<String, String> chkemail = new HashMap<>();
                                chkemail.put(tmpEmail, "true");
                                firebaseDatabase.getReference().child("checkOnly").child("email").child(tmpEmail).setValue(chkemail);

                                //중복 체크를 위한 데이터베이스에 저장 - nickname
                                String tmpNick = nickname;
                                Map<String, String> chkNick = new HashMap<>();
                                chkNick.put(tmpNick, "true");
                                firebaseDatabase.getReference().child("checkOnly").child("nickname").child(tmpNick).setValue(chkNick);

                                //SignUpSuccessActivity로 화면전환, SignUpActivity는 아예 종료시켜야함.
                                Intent intent = new Intent(SignUpActivity.this, SignUpSuccessActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                if (task.getException() != null) {
                                    Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("error", e.getMessage().toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Log.d("tag", "clicked");
                //Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                //startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}