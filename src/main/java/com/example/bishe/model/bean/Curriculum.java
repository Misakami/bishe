package com.example.bishe.model.bean;

public class Curriculum {
    String title; //课程名字
    String content;  //所有的描述内容
    int day;         //周几
    int[] section = new int[12]; //第几节有课
    int[] weak = new int[30]; //第几周有课

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int[] getSection() {
        return section;
    }

    public void setSection(int[] section) {
        this.section = section;
    }

    public int[] getWeak() {
        return weak;
    }

    public void setWeak(int[] weak) {
        this.weak = weak;
    }
}
