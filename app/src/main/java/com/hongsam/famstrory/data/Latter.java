package com.hongsam.famstrory.data;

public class Latter {

    //    int image;
    String sender;
    String contants;
    String date;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContants() {
        return contants;
    }

    public void setContants(String contants) {
        this.contants = contants;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Latter(String sender, String contants, String date) {
        this.sender = sender;
        this.contants = contants;
        this.date = date;
    }
}
