package com.hongsam.famstory.firebase;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstory.define.Define;

public class CreateDB {


    public void pushFireBaseDatabase(Bundle bundle){
        String user = Define.user;
        String getTitle = bundle.getString("title");
        String getText = bundle.getString("text");
        String getDate=bundle.getString("date");
        String getStartTime = bundle.getString("startT");
        String getEndTime = bundle.getString("endT");
        int Year = bundle.getInt("year");
        int Month = bundle.getInt("month");
        int Day = bundle.getInt("day");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Family").child(user).child("CalendarDB")
                .child(Year+"년").child(Month+"월").child(Day+"일");
        CalendarDB calendarDB = new CalendarDB(getTitle,getText,getStartTime,getEndTime);
        database.setValue(calendarDB);
    }
}
