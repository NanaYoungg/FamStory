package com.hongsam.famstrory.data;

//편지내용 (보낸이,내용,날짜,사진,편지지)
public class LetterContants {

    private String fcmType;
    private String sender;
    private String contants;
    private String date;
    private String photo;
    private int paperType;

    public LetterContants() {
    }

    public LetterContants(String sender, String contants, String date, String photo, int paperType) {
        this.fcmType = "LetterContants";
        this.sender = sender;
        this.contants = contants;
        this.date = date;
        this.photo = photo;
        this.paperType = paperType;
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

    public String getContants() { return contants; }

    public void setContants(String contants) {
        this.contants = contants;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPaperType() {
        return paperType;
    }

    public void setPaperType(int paperType) {
        this.paperType = paperType;
    }
}
