package com.hongsam.famstrory.data;

//편지내용
public class LetterContants {

    private String sender;
    private String contants;
    private String date;

    public LetterContants(int paper1){}

    public LetterContants(String sender, String contants, String date) {
        this.sender = sender;
        this.contants = contants;
        this.date = date;
    }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public String getContants() { return contants; }

    public void setContants(String contants) { this.contants = contants; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

}
