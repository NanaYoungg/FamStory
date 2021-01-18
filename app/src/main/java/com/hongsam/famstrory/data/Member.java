package com.hongsam.famstrory.data;

public class Member {

    private String relation;    // 관계 (할머니, 할아버지, 엄마, 아빠 등..)
    private String name;        // 이름

    public Member() { }

    public Member(String relation, String name) {
        this.relation = relation;
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}