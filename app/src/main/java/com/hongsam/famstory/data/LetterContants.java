package com.hongsam.famstory.data;

//편지내용
public class LetterContants {

    private String receiver;
    private String contants;
    private String date;

    public LetterContants(int paper1){}

    public LetterContants(String receiver, String contants, String date) {
        this.receiver = receiver;
        this.contants = contants;
        this.date = date;
    }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getContants() { return contants; }

    public void setContants(String contants) { this.contants = contants; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

}
