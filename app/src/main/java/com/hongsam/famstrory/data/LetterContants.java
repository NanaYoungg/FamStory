package com.hongsam.famstrory.data;

//편지내용 (보낸이,내용,날짜,사진)
public class LetterContants {

    private String sender;
    private String contants;
    private String date;
//    private String photo;

    public LetterContants(int paper1){}

    public LetterContants(String sender, String contants, String date) {
        this.sender = this.sender;
        this.contants = this.contants;
        this.date = this.date;
//        this.photo = photo;
    }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public String getContants() { return contants; }

    public void setContants(String contants) { this.contants = contants; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
//
//    public String getPhoto() { return photo; }
//
//    public void setPhoto(String photo) { this.photo = photo; }

}
