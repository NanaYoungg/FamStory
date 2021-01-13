package com.hongsam.famstory.data;

public class Member {

    String name;
    String call;
    String photoUrl;

    public Member(String name, String call, String photoUrl) {
        this.name = name;
        this.call = call;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
