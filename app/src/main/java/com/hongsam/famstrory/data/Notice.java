package com.hongsam.famstrory.data;

/**
 * 가족 전체에게 공지를 보낼때 쓰는 함수
 * ex) 가족 참여 알림
 * 작성자 : 한재훈
 */

public class Notice {
    private String fcmType;
    private String contents;

    public Notice() {}

    public Notice(String contents) {
        this.fcmType = "Notice";
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFcmType() {
        return fcmType;
    }

    public void setFcmType(String fcmType) {
        this.fcmType = fcmType;
    }
}
