
package com.company;

import java.util.ArrayList;

public class Player {

    private String name;
    private int miss = 0;
    private ArrayList<String> mined = new ArrayList<String>();
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public int getMiss() {
        return miss;
    }

    public void addMiss(int miss) {
        this.miss += miss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMined() {
        return mined;
    }

    public void setMined(ArrayList<String> mined) {
        this.mined = mined;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLongestWord() {
        int longestLength = 0;
        String longest = null;
        for(int i = 0; i<getMined().size(); i++) {
            if(getMined().get(i).length() > longestLength) {
                longest = getMined().get(i);
                longestLength = longest.length();
            }
        }
        return longest;
    }

    public int addScore(int score) {
        this.score += score;
        return this.score;
    }

}
