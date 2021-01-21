package com.hongsam.famstory.data;

/**
 * 감정표현 메세지 모델 클래스
 * 작성자 : 한재훈
 */

public class Emotion {
    private String fcmType;
    private String sender;
    private String message;
    private String sendDate;

    public Emotion() { }

    public Emotion(String sender, String message, String sendDate) {
        this.fcmType = "Emotion";
        this.sender = sender;
        this.message = message;
        this.sendDate = sendDate;
    }

    public String getFcmType() {
        return fcmType;
    }

    public void setFcmType(String fcmType) {
        this.fcmType = fcmType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
