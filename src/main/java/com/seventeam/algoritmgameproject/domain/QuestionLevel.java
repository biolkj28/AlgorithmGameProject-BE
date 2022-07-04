package com.seventeam.algoritmgameproject.domain;

public enum QuestionLevel {

    EASY(0),NORMAL(1),HARD(2);

    private final int value;

    QuestionLevel(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
