package com.hongsam.famstrory.data;

public class Member {
    private String memName;
    private String memCall;

    public Member() {}

    public Member(String memName, String memCall) {
        this.memName = memName;
        this.memCall = memCall;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemCall() {
        return memCall;
    }

    public void setMemCall(String memCall) {
        this.memCall = memCall;
    }
}
