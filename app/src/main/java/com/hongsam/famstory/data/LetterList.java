package com.hongsam.famstory.data;
//편지 목록 (보낸이,내용,날짜)
public class LetterList {

    private String sender;
    private String contants;
    private String date;

    public LetterList(){}

    public LetterList(String sender, String contants, String date) {
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

//    @Override
//    public String toString() {
//        return "LetterList{" +
//                "sender='" + sender + '\'' +
//                ", contants='" + contants + '\'' +
//                ", date='" + date + '\'' +
//                '}';
//    }
}
