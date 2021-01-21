package com.hongsam.famstrory.data;

import java.util.Map;

/**
 * 가족 모델 클래스
 * 작성자 : 한재훈
 */

public class Family {
    private String famPw;                   // 가족 비밀번호
    private String famTitle;                // 가훈
//    private ArrayList<Member> memberList;   // 구성원 리스트
    private Map<String,Member> memberMap;   // 구성원 리스트

    public Family(){};

    public Family(String famPw, String famTitle) {
        this.famPw = famPw;
        this.famTitle = famTitle;
    }

//    public Family(String famPw, String famTitle, ArrayList<Member> memberList) {
//        this.famPw = famPw;
//        this.famTitle = famTitle;
//        this.memberList = memberList;
//    }
//
    public Family(String famPw, String famTitle, Map<String, Member> memberMap) {
        this.famPw = famPw;
        this.famTitle = famTitle;
        this.memberMap = memberMap;
    }

    //region getter & setter
    public String getFamPw() {
        return famPw;
    }
    public void setFamPw(String famPw) {
        this.famPw = famPw;
    }
    public String getFamTitle() {
        return famTitle;
    }
    public void setFamTitle(String famTitle) {
        this.famTitle = famTitle;
    }
//    public ArrayList<Member> getMemberList() {
//        return memberList;
//    }
//    public void setMemberList(ArrayList<Member> memberList) {
//        this.memberList = memberList;
//    }
    public Map<String, Member> getMemberMap() {
        return memberMap;
    }
    public void setMemberMap(Map<String, Member> memberMap) {
        this.memberMap = memberMap;
    }
    //endregion
}
