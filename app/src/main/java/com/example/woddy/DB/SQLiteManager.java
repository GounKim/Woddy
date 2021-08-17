package com.example.woddy.DB;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.PostingSQL;

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
        sqlite.execSQL("INSERT INTO scrapped_postings VALUES ('"
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

    public void insertLiked(String postingPath) {
        sqlite = helper.getWritableDatabase();
        sqlite.execSQL("INSERT INTO liked_activity VALUES ('" + postingPath +"');");

        sqlite.close();
    }

    public ArrayList<PostingSQL> getScrapPost() {
        sqlite = helper.getReadableDatabase();
        try {
            String sql = "SELECT * FROM scrapped_postings";
            ArrayList<PostingSQL> postingList = new ArrayList<>();
            PostingSQL posting = null;
            Cursor cursor = sqlite.rawQuery(sql, null);
            if (cursor != null) {
                while( cursor.moveToNext() ) {
                    posting.setPostingPath(cursor.getString(0));
                    posting.setPictures(getPictures(posting.getPostingPath()));
                    posting.setBoard(cursor.getString(1));
                    posting.setTag(cursor.getString(2));
                    posting.setWriter(cursor.getString(3));
                    posting.setTitle(cursor.getString(4));
                    posting.setContent(cursor.getString(5));
                    posting.setPostedTime(cursor.getString(6));

                    postingList.add(posting);
                }
                return postingList;
            }
        } catch (Exception e) {
            Log.e(TAG, "Getting Scrapped Posting error: ", e);
        }
        return null;
    }

    public ArrayList<String> getPictures(String location) {
        ArrayList<String> pictureList = new ArrayList<>();
        sqlite = helper.getReadableDatabase();
        try {
            String sql = "SELECT picture FROM scrapped_postings WHERE location = '" + location + "'";
            Cursor cursor = sqlite.rawQuery(sql, null);
            if (cursor != null) {
                while( cursor.moveToNext() ) {
                    pictureList.add(cursor.getString(0));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Getting Scrapped Posting error: ", e);
        }
        return pictureList;
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


}
