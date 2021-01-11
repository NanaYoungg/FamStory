package com.hongsam.famstrory.firebase;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.define.Define;

public class CreateDB {


    public void pushFireBaseDatabase(Bundle bundle){
        String user = Define.user;
        String getTitle = bundle.getString("title");
        String getText = bundle.getString("text");
        String getDate=bundle.getString("date");
        String getStartTime = bundle.getString("startT");
        String getEndTime = bundle.getString("endT");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("CalendarDB").child(user);
        CalendarDB calendarDB = new CalendarDB(getTitle,getText,getStartTime,getEndTime);
        database.child(getDate).setValue(calendarDB);
    }
}
