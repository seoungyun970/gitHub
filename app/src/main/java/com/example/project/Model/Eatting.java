package com.example.project.Model;

import android.widget.ImageView;

public class Eatting {
    private String nowTime;
    private String eattingTime;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private String eattingFirst;
    private String eattingSecond;
    private String eattingThird;

    public Eatting(String nowTime, String eattingTime, ImageView imageView1, ImageView imageView2, ImageView imageView3, String eattingFirst, String eattingSecond, String eattingThird) {
        this.nowTime = nowTime;
        this.eattingTime = eattingTime;
        this.imageView1 = imageView1;
        this.imageView2 = imageView2;
        this.imageView3 = imageView3;
        this.eattingFirst = eattingFirst;
        this.eattingSecond = eattingSecond;
        this.eattingThird = eattingThird;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getEattingTime() {
        return eattingTime;
    }

    public void setEattingTime(String eattingTime) {
        this.eattingTime = eattingTime;
    }

    public ImageView getImageView1() {
        return imageView1;
    }

    public void setImageView1(ImageView imageView1) {
        this.imageView1 = imageView1;
    }

    public ImageView getImageView2() {
        return imageView2;
    }

    public void setImageView2(ImageView imageView2) {
        this.imageView2 = imageView2;
    }

    public ImageView getImageView3() {
        return imageView3;
    }

    public void setImageView3(ImageView imageView3) {
        this.imageView3 = imageView3;
    }

    public String getEattingFirst() {
        return eattingFirst;
    }

    public void setEattingFirst(String eattingFirst) {
        this.eattingFirst = eattingFirst;
    }

    public String getEattingSecond() {
        return eattingSecond;
    }

    public void setEattingSecond(String eattingSecond) {
        this.eattingSecond = eattingSecond;
    }

    public String getEattingThird() {
        return eattingThird;
    }

    public void setEattingThird(String eattingThird) {
        this.eattingThird = eattingThird;
    }

    @Override
    public String toString() {
        return "Eatting{" +
                "nowTime='" + nowTime + '\'' +
                ", eattingTime='" + eattingTime + '\'' +
                ", imageView1=" + imageView1 +
                ", imageView2=" + imageView2 +
                ", imageView3=" + imageView3 +
                ", eattingFirst='" + eattingFirst + '\'' +
                ", eattingSecond='" + eattingSecond + '\'' +
                ", eattingThird='" + eattingThird + '\'' +
                '}';
    }
}
