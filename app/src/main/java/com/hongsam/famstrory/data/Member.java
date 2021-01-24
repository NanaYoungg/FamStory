package com.hongsam.famstrory.data;

/**
 * 구성원 모델 클래스
 * 작성자 : 한재훈
 */

public class Member {

    private String fcmType;
    private String relation;    // 관계 (할머니, 할아버지, 엄마, 아빠 등..)
    private String name;        // 이름
    private String call;        // 호칭
    private String token;       // 토큰 (실제로 사용될지는 모르겠으나, 추후를 대비하여...)

    public Member() { }

    public Member(String relation, String name, String token) {
        this.fcmType = "Member";
        this.relation = relation;
        this.name = name;
        this.call = relation;
        this.token = token;
    }

    // region getter & setter
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

    public String getFcmType() {
        return fcmType;
    }

    public void setFcmType(String fcmType) {
        this.fcmType = fcmType;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // endregion
}