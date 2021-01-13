package com.hongsam.famstory.data;

import java.util.ArrayList;

//편지 DB
public class Letter {
    //가족이름
    private String famName;
    //편지내용
    private ArrayList<LetterContants> letterContants;

    public Letter(String famName, LetterContants letterContants){};

    public Letter(String famName, ArrayList<LetterContants> letterContants) {
        this.famName = famName;
        this.letterContants = letterContants;
    }

    public String getFamName() { return famName; }
    public void setFamName(String famName) {
        this.famName = famName;
    }
    public ArrayList<LetterContants> getLetterList() {
        return letterContants;
    }
    public void setLetterList(ArrayList<LetterContants> letterContants) {
        this.letterContants = letterContants;
    }
}