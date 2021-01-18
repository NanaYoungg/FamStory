package com.hongsam.famstory.data;

import java.util.ArrayList;

/**
 * 감정표현 키워드 모델 클래스
 * 작성자 : 한재훈
 * */

public class Keyword {
    private String strMain;  // 메인주제
    private ArrayList<String> subList;

    public Keyword() { }

    public Keyword(String strMain, ArrayList<String> subList) {
        this.strMain = strMain;
        this.subList = subList;
    }

    public String getStrMain() {
        return strMain;
    }
    public void setStrMain(String strMain) {
        this.strMain = strMain;
    }
    public ArrayList<String> getSubList() {
        return subList;
    }
    public void setSubList(ArrayList<String> subList) {
        this.subList = subList;
    }
}
