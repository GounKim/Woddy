package com.example.woddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Entity.Comment;

import java.util.Date;

public class ShowWriting extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    EditText edtComment;
    Button btnSend;

    ShowWritingAdapter adapter;
    FirestoreManager manager;

    @Override
    protected boolean useBottomNavi() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_writing);

        recyclerView = findViewById(R.id.show_writing_comment_view);
        edtComment = findViewById(R.id.show_writing_edt_comment);
        btnSend = findViewById(R.id.show_writing_btnSend_comment);

        manager = new FirestoreManager();

        adapter = new ShowWritingAdapter();
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