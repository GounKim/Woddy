package com.example.woddy.DB;

import static android.content.ContentValues.TAG;

import static com.example.woddy.DB.FirestoreManager.USER_UID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.woddy.Entity.Posting;
import com.example.woddy.Entity.PostingSQL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SQLiteManager {
    SQLiteHelper helper;
    SQLiteDatabase sqlite;

    final static String userUid = USER_UID;


    public SQLiteManager(Context context) {
        this.helper = new SQLiteHelper(context, "woddyDB", null, 1);
    }

    public void setUser(String uid, String userNick, String userImage) {
        sqlite = helper.getWritableDatabase();
        if (!findUser()) {
            sqlite.execSQL("INSERT INTO user_profile VALUES ('"
                    + uid + "', '"
                    + userNick + "', '"
                    + userImage + "');");
        } else {
            if (userNick == null) {
                sqlite.execSQL("UPDATE user_profile " +
                        "SET user_image_path = '" + userImage + "' " +
                        "WHERE user_uid = '" + uid + "'");
            } else {
                sqlite.execSQL("UPDATE user_profile " +
                        "SET nick_name = '" + userNick + "' " +
                        "WHERE user_uid = '" + uid + "'");
            }
        }
        sqlite.close();
    }

    public String getUserNick() {
        String nickname = null;
        sqlite = helper.getReadableDatabase();
        try {
            String sql = "SELECT nick_name FROM user_profile WHERE user_uid = '" + userUid + "';";
            Cursor cursor = sqlite.rawQuery(sql, null);
            if (cursor != null) {
                while( cursor.moveToNext() ) {
                    nickname = cursor.getString(0);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Getting Scrapped Posting error: ", e);
        }

        sqlite.close();
        return nickname;
    }

    public String getUserImage() {
        String nickname = null;
        sqlite = helper.getReadableDatabase();
        try {
            String sql = "SELECT user_image_path FROM user_profile WHERE user_uid = '" + USER_UID + "'";
            Cursor cursor = sqlite.rawQuery(sql, null);
            if (cursor != null) {
                while( cursor.moveToNext() ) {
                    nickname = cursor.getString(0);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Getting Scrapped Posting error: ", e);
        }
        return nickname;
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

    public ArrayList<String> getLiked() {
        sqlite = helper.getReadableDatabase();
        ArrayList<String> pathList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM liked_activity";
            Cursor cursor = sqlite.rawQuery(sql, null);
            if (cursor != null) {
                while( cursor.moveToNext() ) {
                    pathList.add(cursor.getString(0));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Getting Scrapped Posting error: ", e);
        }

        sqlite.close();
        return pathList;
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
        sqlite.close();
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

    public boolean findUser() {
        int cnt;
        Cursor cursor = sqlite.rawQuery("select * from user_profile where user_uid = '" + userUid + "';", null);
        cnt = cursor.getCount();

        if (cnt == 0) {
            return false;
        }

        return true;
    }



}