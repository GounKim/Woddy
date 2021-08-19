package com.example.woddy.Entity;

import java.lang.reflect.Array;

public class UserActivity {
    public static final int WRITEARTICLE = 0;   // 작성한 게시물
    public static final int PRESSLIKE = 1;  // 좋아요 누른 게시물
    public static final int SCRAPPED = 2;   // 스크랩한 게시물

    private String postingNum;
    private String activityName;


    public UserActivity() {
    }

    public UserActivity(String postingNum, int activityName) throws Exception {
        this.postingNum = postingNum;
        if (activityName == WRITEARTICLE) {
            this.activityName = "myPostings";
        } else if (activityName == PRESSLIKE) {
            this.activityName = "likedPostings";
        } else if (activityName == SCRAPPED) {
            this.activityName = "scrappedPostings";
        } else {
            throw new Exception("Not available activity");
        }

    }

    public String getPostingNum() {
        return postingNum;
    }

    public void setPostingNum(String postingNum) {
        this.postingNum = postingNum;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(int activityName) throws Exception {
        if (activityName == WRITEARTICLE) {
            this.activityName = "myPostings";
        } else if (activityName == PRESSLIKE) {
            this.activityName = "likedPostings";
        } else if (activityName == SCRAPPED) {
            this.activityName = "scrappedPostings";
        } else {
            throw new Exception("Not available activity");
        }
    }
}
