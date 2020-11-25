package com.example.cardproject.model;

import com.example.cardproject.R;

public class Card {
    private int frontImgId;
    private boolean isFront;
    private int backImgId;

    public Card() {
        this.frontImgId = R.drawable.card_default;
        this.backImgId = R.drawable.card_back;
        this.isFront = true;
    }

    public int getBackImgId() {
        return backImgId;
    }

    public void setBackImgId(int backImgId) {
        this.backImgId = backImgId;
    }

    public int getFrontImgId() {
        return frontImgId;
    }

    public void setFrontImgId(int frontImgId) {
        this.frontImgId = frontImgId;
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }
}
