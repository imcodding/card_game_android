package com.example.cardproject.model;

import java.io.Serializable;

public class Record implements Serializable {
    private int score;
    private String date;
    private String nickname;

    public Record(int score, String date, String nickname) {
        this.score = score;
        this.date = date;
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
