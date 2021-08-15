package com.example.woddy.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.woddy.Entity.Posting;

import java.util.ArrayList;

public class SQLiteManager {
    SQLiteHelper helper;
    SQLiteDatabase sqlite;

    final static String USER = "user1";


    public SQLiteManager(Context context) {
        this.helper = new SQLiteHelper(context, "woodyDB", null, 1);
    }

    public void insertPosting(String boardName, String tagName, Posting posting) {
        sqlite = helper.getWritableDatabase();
        sqlite.execSQL("INSERT INTO postings " +
                "VALUES ('"
                + posting.getPostingNumber() + "', '"
                + boardName + "', '"
                + tagName + "', '"
                + posting.getWriter() + "', '"
                + posting.getTitle() + "', '"
                + posting.getContent() + "','"
                + posting.getPostedTime() + "');");

        if (!posting.getPictures().isEmpty()) {
            insertPicture(posting.getPostingNumber(), posting.getPictures());
        }

        sqlite.close();
    }

    public void insertPicture(String postingNum, ArrayList<String> pictures) {
        for (int index = 0; index < pictures.size(); index++) {
            sqlite.execSQL("INSERT INTO posting_picture " +
                    "VALUES ('" + postingNum + "' , '" + pictures.get(index) + "')");
        }
    }

    public int postingCount() {
        sqlite = helper.getReadableDatabase();
        int cnt;
        Cursor cursor = sqlite.rawQuery("select * from postings WHERE writer = '" + USER +"'", null);
        cnt = cursor.getCount();

        sqlite.close();
        return cnt;
    }

    public String getUerName() {
        sqlite = helper.getReadableDatabase();
        Cursor cursor = sqlite.rawQuery("select user_nickname from user_profile", null);
        String user = cursor.getString(0);

        sqlite.close();
        return user;
    }

    public void insertScrap() {

    }

}
