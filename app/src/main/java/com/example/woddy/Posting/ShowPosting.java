package com.example.woddy.Posting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.BaseActivity;
import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Comment;
import com.example.woddy.R;

public class ShowPosting extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    EditText edtComment;
    Button btnSend;

    ShowPostingAdapter adapter;
    FirestoreManager manager;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posting);

        recyclerView = findViewById(R.id.show_posting_comment_view);
        edtComment = findViewById(R.id.show_posting_edt_comment);
        btnSend = findViewById(R.id.show_posting_btnSend_comment);

        manager = new FirestoreManager();

        adapter = new ShowPostingAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.VERTICAL, false)); // 상하 스크롤
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String text = edtComment.getText().toString();
        Comment comment = new Comment("user1", text, "");
        adapter.addItem(comment);
        manager.addComment("rQl1Kt8M1KNPPsciD3ah",comment);
    }

}