package com.example.woddy.Album;
//앨범게시판 아이템에 저장할 데이터
public class AlbumItem {

    private String url;
    private String title;
    private Integer liked;

    public AlbumItem(String url, String title, Integer liked){
        this.url = url;
        this.title = title;
        this.liked = liked;
    }

    public String getUrl(){ return url; }

    public String getTitle() {
        return title;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
