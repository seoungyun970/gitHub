package com.example.project.Model;

public class Notice {
    public String noticemenu;
    public String title;
    public String contents;
    public String date;
    public String noticeImageUrl;

    public Notice() {
    }

    public Notice(String noticemenu, String title, String contents, String date) {
        this.noticemenu = noticemenu;
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public String getNoticemenu() {
        return noticemenu;
    }

    public void setNoticemenu(String noticemenu) {
        this.noticemenu = noticemenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
