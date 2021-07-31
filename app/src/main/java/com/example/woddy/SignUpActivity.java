package com.example.woddy;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

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
    private FirebaseStorage firebaseStorage;

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
        firebaseStorage = FirebaseStorage.getInstance();

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
    }

    void checkID() {
        idAvailTextView.setVisibility(View.VISIBLE);
        idAvailTextView.setText("사용 가능한 이메일입니다.");
        //idAvailTextView.setText("중복된 이메일입니다.");
    }

    void checkNickname() {
        nickAvailTextView.setVisibility(View.VISIBLE);
        nickAvailTextView.setText("사용 가능한 닉네임입니다.");
        //nickAvailTextView.setText("중복된 닉네임입니다.");
    }

    void signUp() {
        String email = emailEditText.getText().toString();
        String pw1 = pw1EditText.getText().toString();
        String pw2 = pw2EditText.getText().toString();
        String nickname = nicknameEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw1) ||
                TextUtils.isEmpty(pw2) || TextUtils.isEmpty(nickname)) {
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