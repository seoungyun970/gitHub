package com.example.project;

public class Notice {
    private String noticemenu;
    private String title;
    private String contents;
    private String date;

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

    @Override
    public String toString() {
        return "Notice{" +
                "noticemenu='" + noticemenu + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
