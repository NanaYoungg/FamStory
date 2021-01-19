package com.hongsam.famstory.firebase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.define.Define;

public class ReadDB {
    public static String TAG = "DB Read";

    MainActivity mainActivity;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userName = Define.user;


    Calendar calendarDB;

    public ReadDB(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    /*
       Calendar 에서 onclick 이벤트가 발생했을때 파이어베이스가 비어있는지 확인
    */
    public void databaseRead(int year,int month,final int day){


        DatabaseReference myRef = database.getReference("Family").child(userName).child("CalendarDB")
                .child(year+"년").child(month+"월");
        Query query = myRef.orderByChild(day+"일");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if((day+"일").equals(snapshot.getKey())) {
                    calendarDB = snapshot.getValue(Calendar.class);
                    mainActivity.view_more_text(calendarDB);
                }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if ((day+"일").equals(snapshot.getKey())) {
                    calendarDB = snapshot.getValue(Calendar.class);
                    mainActivity.view_more_text(calendarDB);

                }
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
