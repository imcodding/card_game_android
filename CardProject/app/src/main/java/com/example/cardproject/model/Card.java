package com.example.cardproject.model;

import com.example.cardproject.R;

public class Card {
    private int defaultImgId;
    private boolean isFront;
    private int backImgId;

    public Card() {
        this.defaultImgId = R.drawable.card_default;
        this.backImgId = R.drawable.card_back;
        this.isFront = true;
    }

    public int getBackImgId() {
        return backImgId;
    }

    public void setBackImgId(int backImgId) {
        this.backImgId = backImgId;
    }

    public int getDefaultImgId() {
        return defaultImgId;
    }

    public void setDefaultImgId(int defaultImgId) {
        this.defaultImgId = defaultImgId;
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }
}
