package com.hongsam.famstrory.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.CalendarData;
import com.hongsam.famstrory.define.Define;

public class UpdateDB {
    CalendarData calendarDataDB;
    MainActivity mainActivity;
    public UpdateDB(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void updateDB(int year, int month, final int day){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userName = MainActivity.famName;
        DatabaseReference myRef = database.getReference(Define.DB_REFERENCE).child(userName)
                .child(Define.CALENDAR_DB).child(year+"년").child(month+"월");

        Query query = myRef.orderByChild(day+"일");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String getData = snapshot.getKey();
                if ((day+"일").equals(getData)){
                    calendarDataDB = snapshot.getValue(CalendarData.class);
                    mainActivity.calendarUpdateGetDialogText(calendarDataDB);
                }
                //if(Objects.equals(snapshot.getKey(),"title")) {
//                    calendarDB = snapshot.getValue(CalendarDB.class);
//                    Log.e("Tag", calendarDB.getTitle());
//                    mainActivity.calendarUpdateGetDialogText(calendarDB);
                //}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String getData = snapshot.getKey();
                if ((day+"일").equals(getData)){
                    calendarDataDB = snapshot.getValue(CalendarData.class);
                    mainActivity.calendarUpdateGetDialogText(calendarDataDB);
                }
                //if (Objects.equals(snapshot.getKey(),"title")) {
/*                    calendarDB = snapshot.getValue(CalendarDB.class);
                    Log.e("Tag", calendarDB.getTitle());
                    mainActivity.calendarUpdateGetDialogText(calendarDB);*/
                //}
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
