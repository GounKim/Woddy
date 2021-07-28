package com.example.woddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LogInActivity extends AppCompatActivity {
    final int RC_SIGN_IN = 1;
    EditText idEditText;
    EditText pwEditText;
    Button loginButton;
    Button signUpButton;
    SignInButton googleLoginButton;
    FirebaseAuth firebaseAuth;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);
        idEditText = findViewById(R.id.id_edit_text);
        pwEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);
        googleLoginButton = findViewById(R.id.google_login_button);
        firebaseAuth = FirebaseAuth.getInstance();

        setGoogleButtonText(googleLoginButton, "Google 계정으로 로그인");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(this);
        if (gsa != null) {
            //이미 로그인된 사용자
        }

        //login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpid = idEditText.getText().toString();
                String tmppw = pwEditText.getText().toString();
                if (tmpid.matches("") || tmppw.matches("")) {
                    Toast.makeText(LogInActivity.this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    signIn();
                }
            }
        });

        //google login button click listener
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //google login button setText fun
    private void setGoogleButtonText(SignInButton googleLoginButton, String buttonText) {
        int i = 0;
        while (i < googleLoginButton.getChildCount()) {
            View v = googleLoginButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setGravity(Gravity.CENTER);
                return;
            }
            i++;
        }
    }

    void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

    }

    void signIn() {
        String email = idEditText.getText().toString().trim();
        String pw = pwEditText.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LogInActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}