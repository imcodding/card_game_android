package com.example.cardproject.model;

import com.example.cardproject.dummy.DummyData;

import java.util.ArrayList;

public class Game {
    private final int cardCount;
    private final int gameTime;
    private int randomCoupleSize;
    private int answerCount;
    private int totalSelectCount;
    private ArrayList<Integer> mImageDrawables; // 카드 이미지
    private ArrayList<Card> mCards;

    public Game() {
        cardCount = 12;
        gameTime = 60;
        randomCoupleSize = 2;
        answerCount = randomCoupleSize;
        totalSelectCount = answerCount * 2;

        mImageDrawables = new DummyData().getImageDrawables();
        mCards = new ArrayList<>();
        for(int i = 0; i < cardCount; i++) {
            mCards.add(new Card());
        }
    }

    public void ready() {
        shuffleCards();
    }

    private void shuffleCards() {
        // 이미지 뽑기
        ArrayList<Integer> randomImages = new ArrayList<>();
        for(int i = 0; i < randomCoupleSize; i++) {
            int ranNum = (int)(Math.random() * mImageDrawables.size()); // 이미지 개수
            if(randomImages.contains(ranNum)) {
                i--;
            } else {
                randomImages.add(ranNum);
            }
        }
        randomImages.addAll(randomImages); // 이미지 종류 2개, 개수 4개

        // 실제 12개 중에 이미지가 변경될 index
        // 12개의 카드 중에 이미지를 어디에다가 넣을건지.
        ArrayList<Integer> setImageIndexes = new ArrayList<>();
        for(int i = 0; i < randomCoupleSize * 2; i++) {
            int ranNum = (int)(Math.random() * cardCount);
            if(setImageIndexes.contains(ranNum)) {
                i--;
            } else {
                setImageIndexes.add(ranNum);
            }
        }

        for(int i = 0; i < setImageIndexes.size(); i++) {
            int whatImageIdx = mImageDrawables.get(randomImages.get(i));
            int whereCardIdx = setImageIndexes.get(i);

            mCards.get(whereCardIdx).setFrontImgId(whatImageIdx);
        }
    }

    public void changeLevel(int score) {
        if(score < 2000) {
            shuffleCards();
        } else if(score < 4000) {
            randomCoupleSize = 3;
            answerCount = randomCoupleSize;
            totalSelectCount = randomCoupleSize * 2;
            shuffleCards();
        } else {
            randomCoupleSize = 4;
            answerCount = randomCoupleSize;
            totalSelectCount = randomCoupleSize * 2;
            shuffleCards();
        }
    }

    public ArrayList<Card> getCards() {
        return mCards;
    }

    public int getGameTime() {
        return gameTime;
    }

    public Card getCardItem(int pos) {
        return mCards.get(pos);
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getTotalSelectCount() {
        return totalSelectCount;
    }
}
