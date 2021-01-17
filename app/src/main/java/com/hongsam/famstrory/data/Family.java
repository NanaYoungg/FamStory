package com.hongsam.famstrory.data;

import java.util.ArrayList;

public class Family {
    private String famPw;
    private ArrayList<Member> memberList;

    public Family(){};

    public Family(String famPw, ArrayList<Member> memberList) {
        this.famPw = famPw;
        this.memberList = memberList;
    }

    //region getter & setter
    public String getFamPw() {
        return famPw;
    }
    public void setFamPw(String famPw) {
        this.famPw = famPw;
    }
    public ArrayList<Member> getMemberList() {
        return memberList;
    }
    public void setMemberList(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }
    //endregion
}
