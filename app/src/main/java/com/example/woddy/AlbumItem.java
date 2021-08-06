package com.example.woddy;
//앨범게시판 아이템에 저장할 데이터
public class AlbumItem {

    private String url;
    private String title;

    public AlbumItem(String url, String title){
        this.url = url;
        this.title = title;
    }

    public String getUrl(){
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
