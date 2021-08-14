package com.example.woddy.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.DB.SQLiteManager;
import com.example.woddy.Entity.User;
import com.example.woddy.Entity.Profile;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class SignUpActivity extends AppCompatActivity {
    final String TAG = "SignUp";
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
        pwAvailTextView = findViewById(R.id.pw_check_text_view);
        nickAvailTextView = findViewById(R.id.nick_avail_text_view);

        firebaseAuth = FirebaseAuth.getInstance();

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
                    Log.d(TAG, "pwCheck" + pwCheck.toString());
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
        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nextButton.setBackground(getDrawable(R.drawable.button_round_shape_grayish));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "dggg");
                if (idCheck && pwCheck && nickCheck) {
                    Log.d(TAG, "id, pw, nick check completed.");
                    if (TextUtils.isEmpty(addressEditText.getText().toString())) {
                        nextButton.setBackground(getDrawable(R.drawable.button_round_shape_grayish));
                    } else {
                        nextButton.setBackground(getDrawable(R.drawable.button_round_shape));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    void checkID() {
        String email_str = emailEditText.getText().toString();

        FirestoreManager fsManager = new FirestoreManager();
        fsManager.findEmail(email_str)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() == 0) {
                            idAvailTextView.setVisibility(View.VISIBLE);
                            idAvailTextView.setTextColor(Color.GRAY);
                            idAvailTextView.setText("사용 가능한 이메일입니다.");
                            idCheck = true;
                            Log.d(TAG, "idCheck" + idCheck.toString());
                        } else {
                            idAvailTextView.setVisibility(View.VISIBLE);
                            idAvailTextView.setTextColor(Color.rgb(255, 105, 105));
                            idAvailTextView.setText("중복된 이메일입니다.");
                        }
                    }
                });
    }

    void checkNickname() {
        String nick_str = nicknameEditText.getText().toString();

        FirestoreManager fsManager = new FirestoreManager();
        fsManager.findNickname(nick_str)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() == 0) {
                            nickAvailTextView.setVisibility(View.VISIBLE);
                            nickAvailTextView.setTextColor(Color.GRAY);
                            nickAvailTextView.setText("사용 가능한 닉네임입니다.");
                            nickCheck = true;
                            Log.d(TAG, "nickCheck" + nickCheck.toString());
                        } else {
                            nickAvailTextView.setVisibility(View.VISIBLE);
                            nickAvailTextView.setTextColor(Color.rgb(255, 105, 105));
                            nickAvailTextView.setText("중복된 닉네임입니다.");
                        }
                    }
                });
    }

    void signUp() {
        String email = emailEditText.getText().toString();
        String pw1 = pw1EditText.getText().toString();
        String pw2 = pw2EditText.getText().toString();
        String nickname = nicknameEditText.getText().toString();
        //address
        String address = addressEditText.getText().toString();
        String city = "", gu = "", dong = "";

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw1) ||
                TextUtils.isEmpty(pw2) || TextUtils.isEmpty(nickname) ||
                TextUtils.isEmpty(address)) {
            Toast.makeText(this, "빈칸을 모두 채워주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        String[] tmpStrArr = address.split(" ");
        if (tmpStrArr.length != 3) {
            Toast.makeText(SignUpActivity.this, "주소를 다시 입력해주세요.", Toast.LENGTH_LONG).show();
            return;
        }
        city = tmpStrArr[0];
        gu = tmpStrArr[1];
        dong = tmpStrArr[2];

        try {
            String finalCity = city;
            String finalGu = gu;
            String finalDong = dong;
            String finalLocal = city + " " + gu + " " + dong;
            firebaseAuth.createUserWithEmailAndPassword(email, pw1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String uid = task.getResult().getUser().getUid();

                                Profile profile = new Profile(email, nickname, finalCity, finalGu, finalDong);
                                User user = new User(nickname, finalLocal, "UserProfileImages/user.png");

                                FirestoreManager fsManager = new FirestoreManager();
                                fsManager.addProfile(uid, profile); //userProfile 컬렉션에 저장
                                fsManager.addUser(user); //user 컬렉션에 저장

                                FirebaseAuth.getInstance().signOut();
//
//                                SQLiteManager sqLiteManager = new SQLiteManager(getApplicationContext(), "woddyDB", null, 1);
//                                sqLiteManager

                                //SignUpSuccessActivity로 화면전환, SignUpActivity는 아예 종료시켜야함.
                                Intent intent = new Intent(SignUpActivity.this, SignUpSuccessActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    Toast.makeText(SignUpActivity.this, "6자리 이상의 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, e.toString());
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Log.w(TAG, e.getMessage().toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}