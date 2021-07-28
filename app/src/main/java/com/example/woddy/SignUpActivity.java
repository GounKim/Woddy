package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.TextUtils;
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
    Button idCheckButton;
    Button nicknameCheckButton;
    Button nextButton;
    TextView idAvailTextView;
    TextView pwAvailTextView;
    TextView nickAvailTextView;

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
        idCheckButton = findViewById(R.id.id_availability_check_button);
        nicknameCheckButton = findViewById(R.id.nickname_availability_check_button);
        nextButton = findViewById(R.id.next_button);

        idAvailTextView = findViewById(R.id.id_avail_text_view);
        pwAvailTextView = findViewById(R.id.pw_avail_text_view);
        nickAvailTextView = findViewById(R.id.nick_avail_text_view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (pw1EditText.getText().toString().equals(pw2EditText.getText().toString())) {
            pwAvailTextView.setVisibility(View.VISIBLE);
            pwAvailTextView.setText("비밀번호가 일치합니다.");
        } else {
            pwAvailTextView.setVisibility(View.VISIBLE);
            pwAvailTextView.setText("비밀번호가 일치하지 않습니다.");
        }

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
                                StorageReference storageReference = firebaseStorage.getReference()
                                        .child("userProfileImage")
                                        .child("gs://awoddy-5a5b2.appspot.com/userProfileImage/user.png");
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
}