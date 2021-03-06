package com.hongsam.famstrory.firebase;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.CalendarData;
import com.hongsam.famstrory.define.Define;

public class CreateDB {


    public void pushFireBaseDatabase(Bundle bundle){
        String user = MainActivity.famName;
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

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Define.DB_REFERENCE).child(user).child(Define.CALENDAR_DB)
                .child(Year+"년").child(Month+"월").child(Day+"일");
        CalendarData calendarDataDB = new CalendarData(getTitle,getText,getStartTime,getEndTime,getType);
        database.setValue(calendarDataDB);
    }
}
