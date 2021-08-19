package com.example.woddy.MyPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woddy.BaseActivity;
import com.example.woddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class ChangePwActivity extends BaseActivity {

    static final String TAG = "ChangePwAct";

    EditText curPwEditText;
    EditText newPwEditTExt;
    EditText newPw2EditTExt;
    TextView pwCheckTextView;
    Button pwChangeButton;
    ImageView toolbarLogoImage;

    FirebaseUser user;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        setMyTitle("비밀번호 변경");

        curPwEditText = findViewById(R.id.cur_pw_edit_text);
        newPwEditTExt = findViewById(R.id.new_pw_edit_text);
        newPw2EditTExt = findViewById(R.id.new_pw2_edit_text);
        pwCheckTextView = findViewById(R.id.pw_check_text_view);
        pwChangeButton = findViewById(R.id.pw_change_button);
        toolbarLogoImage = findViewById(R.id.toolbar_logo);

        user = FirebaseAuth.getInstance().getCurrentUser();

        String emailID = user.getEmail();

        toolbarLogoImage.setVisibility(View.GONE);

        newPw2EditTExt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwCheckTextView.setVisibility(View.VISIBLE);
                if (newPwEditTExt.getText().toString().equals(newPw2EditTExt.getText().toString())) {
                    pwCheckTextView.setTextColor(Color.GRAY);
                    pwCheckTextView.setText("비밀번호가 일치합니다.");
                    pwChangeButton.setBackground(getDrawable(R.drawable.button_round_shape));
                } else {
                    pwCheckTextView.setTextColor(Color.RED);
                    pwCheckTextView.setText("비밀번호가 일치하지않습니다.");
                    pwChangeButton.setBackground(getDrawable(R.drawable.button_round_shape_darkgray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPw = curPwEditText.getText().toString();
                String newPw = newPwEditTExt.getText().toString();
                AuthCredential authCredential = EmailAuthProvider.getCredential(emailID, curPw);
                user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPw)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            //변경 완료
                                            Toast.makeText(ChangePwActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                            //스택의 액티비티 모두 종료하고 로그인 화면으로 이동? or 마이페이지로 이동
                                            finish();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(ChangePwActivity.this, "비밀번호 변경에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                            Log.w(TAG, e.getMessage());
                                        }
                                    });
                        } else {
                            Toast.makeText(ChangePwActivity.this, "현재 비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}