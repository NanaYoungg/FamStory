package com.hongsam.famstrory.data;

import java.util.ArrayList;

/**
 * 감정표현 키워드 모델 클래스
 * 작성자 : 한재훈
 * */

public class Keyword {
    private ArrayList<KeywordItem> middleKeyword;
    private ArrayList<KeywordItem> smallKeyword;

    public Keyword() { }

    public Keyword(ArrayList<KeywordItem> middleKeyword, ArrayList<KeywordItem> smallKeyword) {
        this.middleKeyword = middleKeyword;
        this.smallKeyword = smallKeyword;
    }

    public ArrayList<KeywordItem> getMiddleKeyword() {
        return middleKeyword;
    }

    public void setMiddleKeyword(ArrayList<KeywordItem> middleKeyword) {
        this.middleKeyword = middleKeyword;
    }

    public ArrayList<KeywordItem> getSmallKeyword() {
        return smallKeyword;
    }

    public void setSmallKeyword(ArrayList<KeywordItem> smallKeyword) {
        this.smallKeyword = smallKeyword;
    }
}

