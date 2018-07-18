package com.example.dell.disktopfloatingviewtest.db;

/*
 * 一个数据类
 * */
public class Data {
    public int user_img;
    public int news_tit;
    public int news_txt;
    public String send_time;

    public Data(int user_img, int news_tit, int news_txt, String send_time) {
        this.user_img = user_img;
        this.news_tit = news_tit;
        this.news_txt = news_txt;
        this.send_time = send_time;
    }
}
