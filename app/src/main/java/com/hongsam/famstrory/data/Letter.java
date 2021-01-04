package com.hongsam.famstrory.data;

public class Letter {

    private String sender;
    private String Contents;
    private String Date;

    public String getSender(){
        return sender;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public String getContents(){
        return Contents;
    }

    public void setContents(String contents){
        Contents = contents;
    }

    public String getDate(){
        return Date;
    }

    public void setDate(String date){
        Date = date;
    }

    public Letter(String sender, String contents, String date){
        this.sender = sender;
        Contents = contents;
        Date = date;
    }

}
