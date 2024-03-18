package com.example.newapplication;

import java.util.Random;

public class GuessNum {
    public static final Random random = new Random();
    private int maxAttempts;
    private int min;
    private int max;

    private int value;
    private int attemptsLeft;

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public GuessNum(Difficulty difficulty){
        changeDifficulty(difficulty);
        setRandomValue();
        attemptsLeft = maxAttempts;
    }

    public void changeDifficulty(Difficulty difficulty){
        switch (difficulty) {
            case EASY:
                min = 10;
                max = 100;
                this.maxAttempts = 5;
                break;
            case MEDIUM:
                min = 100;
                max = 1000;
                this.maxAttempts = 7;
                break;
            case HARD:
                min = 1000;
                max = 10000;
                this.maxAttempts = 10;
        }
        restart();
    }

    public void restart(){
        attemptsLeft = maxAttempts;
        setRandomValue();
    }

    public void setRandomValue(){
        value = random.nextInt(max) + min;
    }

    public int compare(int userSuggestedValue){
        if(attemptsLeft > 0){
            attemptsLeft--;
            return userSuggestedValue - value;
        }
        else throw new NoAttemptsException();
    }
}
