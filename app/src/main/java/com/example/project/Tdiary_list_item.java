package com.example.project;

import java.util.Date;

public class Tdiary_list_item {
    private int  profile_image; //리스트 아이템의 이미지
    private String nickname; // 리스트 아이템의 닉네임
    private String title; //리스트 아이템의 제목
    private String subject;//리스트 아이템의 내용
    private Date date; //리스트 아이템의 날짜

    public Tdiary_list_item(int profile_image, String nickname, String title, String subject, Date date) {
        this.profile_image = profile_image;
        this.nickname = nickname;
        this.title = title;
        this.subject = subject;
        this.date = date;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
