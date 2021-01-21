package com.hongsam.famstory.firebase;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.define.Define;

public class CreateDB {


    public void pushFireBaseDatabase(Bundle bundle){
        String user = Define.USER;
        String getTitle = bundle.getString("title");
        String getText = bundle.getString("text");
        String getDate=bundle.getString("date");
        String getStartTime = bundle.getString("startT");
        String getEndTime = bundle.getString("endT");
        String getType = bundle.getString("type");
        int Year = bundle.getInt("year");
        int Month = bundle.getInt("month");
        int Day = bundle.getInt("day");
        Log.e("d",getText);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Family").child(user).child("CalendarDB")
                .child(Year+"년").child(Month+"월").child(Day+"일");
        Calendar calendarDB = new Calendar(getTitle,getText,getStartTime,getEndTime,getType);
        database.setValue(calendarDB);
    }
}
