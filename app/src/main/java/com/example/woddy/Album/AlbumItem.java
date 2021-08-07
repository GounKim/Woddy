package com.example.woddy.Album;
//앨범게시판 아이템에 저장할 데이터
public class AlbumItem {

    private String postingNumber;
    private String url;
    private String title;
    private String liked;

    public AlbumItem(String postingNumber, String url, String title, String liked){
        this.postingNumber = postingNumber;
        this.url = url;
        this.title = title;
        this.liked = liked;
    }

    public String getUrl(){ return url; }

    public String getTitle() {
        return title;
    }

    public String getLiked() {
        return liked;
    }

    public String getPostingNumber() {
        return postingNumber;
    }

    public void setPostingNumber(String postingNumber) {
        this.postingNumber = postingNumber;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
