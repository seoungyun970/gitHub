package com.example.project;

public class DiaryBoard {
    String title;
    String content;

    DiaryBoard(){ }

    public DiaryBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public String gettitle(){return  title;}
    public String getcontent(){return  content;}
}
