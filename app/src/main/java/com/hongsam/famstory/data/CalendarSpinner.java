package com.hongsam.famstory.data;



import java.util.ArrayList;


import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class CalendarSpinner {
    public ArrayList<String> itemList;
    public int size;
    public CalendarSpinner(){

    }
    public CalendarSpinner(ArrayList<String> itemList, int size) {
        this.itemList = itemList;
        this.size = size;
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<String> itemList) {
        this.itemList = itemList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}


