package com.example.woddy.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {
    private String sql;

    public SQLiteManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
        sql = "CREATE TABLE posting (posting_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "tag_name TEXT NOT NULL, " +
                                    "writer TEXT NOT NULL, " +
                                    "title TEXT NOT NULL, " +
                                    "content TEXT NOT NULL, " +
                                    "posted_time TEXT NOT NULL);";
        db.execSQL(sql);
        // 글 사진들
        sql = "CREATE TABLE posting_picture (location INTEGER NOT NULL, " +
                "picture BLOB NOT NULL, " +
                "CONSTRAINT posting_picture_fk FOREIGN KEY (location) " +
                "REFERENCES posting(posting_id));";
        db.execSQL(sql);

        // 포스팅 활동 목록 (글작성 / 좋아요 / 스크랩 / 즐겨찾기)
        sql = "CREATE TABLE writing_activity (my_posting INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE liked_activity (liked_posting INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE scrapped_activity (scrapped_posting INTEGER);";
        db.execSQL(sql);

        sql = "CREATE TABLE favorite_activity (favorite_posting INTEGER);";
        db.execSQL(sql);


        // 채팅 목록
        sql = "CREATE TABLE chatting (room_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "chatter_name TEXT NOT NULL, " +
                                    "chatter_image BLOB);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE user_profile;");
        db.execSQL("DROP TABLE posting_picture;");
        db.execSQL("DROP TABLE posting;");
        db.execSQL("DROP TABLE writing_activity;");
        db.execSQL("DROP TABLE liked_activity;");
        db.execSQL("DROP TABLE scrapped_activity;");
        db.execSQL("DROP TABLE favorite_activity;");
        db.execSQL("DROP TABLE chatting;");
    }
}
