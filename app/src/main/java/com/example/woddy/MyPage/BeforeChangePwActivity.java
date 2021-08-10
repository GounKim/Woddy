package com.example.woddy.MyPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.woddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class BeforeChangePwActivity extends AppCompatActivity {
    static final String TAG = "BeforePwResetAct";
    TextView idTextView;
    EditText pwEditText;
    Button nextButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_pw_reset);

        idTextView = findViewById(R.id.id_text_view);
        pwEditText = findViewById(R.id.pw_edit_text);
        nextButton = findViewById(R.id.next_button);
        firebaseAuth = FirebaseAuth.getInstance();

        //idTextView에 현재 사용자 id 나타내도록 해야함.

        pwEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(pwEditText.getText().toString())) {
                    nextButton.setBackground(getDrawable(R.drawable.button_round_shape_darkgray));
                } else {
                    nextButton.setBackground(getDrawable(R.drawable.button_round_shape));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idTextView.getText().toString();
                String pw = pwEditText.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(id, pw)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(BeforeChangePwActivity.this, "확인되었습니다", Toast.LENGTH_SHORT).show();
                                //변경화면으로 이동.
                                Intent intent = new Intent(BeforeChangePwActivity.this, ChangePwActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.w(TAG, e.getMessage());
                                Toast.makeText(BeforeChangePwActivity.this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}