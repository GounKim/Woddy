package com.example.woddy.Entity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woddy.DB.FirestoreManager;
import com.example.woddy.Info.InfoBoardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

//정보 탭에 들어갈 게시글
public class News {
    private int postingNumber; //글 번호
    private String title; //글 제목
    private String content; //글 내용
    private ArrayList<String> pictures; //글에 첨부된 사진
    private Date postedTime; //작성 시간

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }
}
