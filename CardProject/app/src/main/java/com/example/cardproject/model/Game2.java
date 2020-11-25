package com.example.cardproject.model;

import com.example.cardproject.dummy.DummyData;

import java.util.ArrayList;

public class Game2 {
    public static int CARD_COUNT = 12;
    public static int GAME_TIME = 60; // 게임시간(초단위)
    public static int SELECT_CNT = 2;
    public static int RANDOM_COUPLE_SIZE = 2; // 뒤집어지는 카드 쌍 개수
    public static int ANSWER_CNT = RANDOM_COUPLE_SIZE;
    public static int TOTAL_SELECT_CNT;

    private static ArrayList<Integer> mImageDrawables; // 카드 이미지
    private static ArrayList<Card> mCards;

    public static void start() {
        initVariable();
        resetValue();
        shuffleCards();
    }

    private static void initVariable() {
        mImageDrawables = new DummyData().getImageDrawables();
        mCards = new ArrayList<>();
        for(int i = 0; i < CARD_COUNT; i++) {
            mCards.add(new Card());
        }
    }

    private static void resetValue() {
        ANSWER_CNT = RANDOM_COUPLE_SIZE;
        TOTAL_SELECT_CNT = ANSWER_CNT * 2;
    }

    private static void shuffleCards() {
        // 이미지 뽑기
        ArrayList<Integer> randomImages = new ArrayList<>();
        for(int i = 0; i < RANDOM_COUPLE_SIZE; i++) {
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
        for(int i = 0; i < RANDOM_COUPLE_SIZE * 2; i++) {
            int ranNum = (int)(Math.random() * CARD_COUNT);
            if(setImageIndexes.contains(ranNum)) {
                i--;
            } else {
                setImageIndexes.add(ranNum);
            }
        }

        for(int i = 0; i < setImageIndexes.size(); i++) {
            int whatImageIdx = mImageDrawables.get(randomImages.get(i));
            int whereCardIdx = setImageIndexes.get(i);

            mCards.get(whereCardIdx).setDefaultImgId(whatImageIdx);
        }
    }

    private static void changeLevel(int score) {
        if(score < 2000) {
            shuffleCards();
        } else if(score < 4000) {
            RANDOM_COUPLE_SIZE = 3;
            ANSWER_CNT = RANDOM_COUPLE_SIZE;
            shuffleCards();
        } else {
            RANDOM_COUPLE_SIZE = 4;
            shuffleCards();
        }
    }


}
