package com.example.woddy.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private String sql;

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "woddyDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");

        // 사용자 테이블(본인의 정보만 들어감
        sql = "CREATE TABLE user_profile (user_nickname TEXT PRIMARY KEY , " +
                                            "user_local TEXT, " +
                                            "introduce TEXT, " +
                                            "user_image BLOB);";
        db.execSQL(sql);

        // 글(포스팅)
        sql = "CREATE TABLE scrapped_postings (posting_path TEXT PRIMARY KEY, " +
                                    "board_name TEXT NOT NULL, " +
                                    "tag_name TEXT NOT NULL, " +
                                    "writer TEXT NOT NULL, " +
                                    "title TEXT NOT NULL, " +
                                    "content TEXT NOT NULL, " +
                                    "posted_time TEXT NOT NULL);";
        db.execSQL(sql);
        // 글 사진들
        sql = "CREATE TABLE posting_picture (location INTEGER NOT NULL, " +
                "picture TEXT NOT NULL, " +
                "CONSTRAINT posting_picture_fk FOREIGN KEY (location) " +
                "REFERENCES scrapped_postings(posting_path));";
        db.execSQL(sql);

        // 포스팅 활동 목록 (좋아요 / 스크랩 / 즐겨찾기)
        sql = "CREATE TABLE liked_activity (liked_posting TEXT);";
        db.execSQL(sql);

        sql = "CREATE TABLE favorite_activity (favorite_posting TEXT);";
        db.execSQL(sql);


        // 채팅 목록
        sql = "CREATE TABLE chatting (room_num TEXT PRIMARY KEY, " +
                                    "chatter_name TEXT NOT NULL, " +
                                    "chatter_image BLOB);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_profile;");
        db.execSQL("DROP TABLE IF EXISTS posting_picture;");
        db.execSQL("DROP TABLE IF EXISTS scrapped_postings;");
        db.execSQL("DROP TABLE IF EXISTS liked_activity;");
        db.execSQL("DROP TABLE IF EXISTS favorite_activity;");
        db.execSQL("DROP TABLE IF EXISTS chatting;");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_key = ON;");
        super.onOpen(db);
    }
}
