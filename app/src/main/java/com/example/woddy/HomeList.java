package com.example.woddy;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeList {
    String boardName;
    ListView listView;


    public HomeList(String boardName) {
        this.boardName = boardName;
        listView = null;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }


    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
}
