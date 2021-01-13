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
import com.hongsam.famstory.define.Define;

import java.util.Objects;

public class ReadDB {
    public static String TAG = "DB Read";

    MainActivity mainActivity;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userName = Define.user;
    DatabaseReference myRef = database.getReference("CalendarDB").child(userName);

    CalendarDB calendarDB;

    public ReadDB(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    /*
       Calendar에서 onclick 이벤트가 발생했을때 파이어베이스가 비어있는지 확인
    */
    public void databaseRead(final String date){


        Query query = myRef.orderByChild(date);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(Objects.equals(snapshot.getKey(), date)) {
                    calendarDB = snapshot.getValue(CalendarDB.class);
                    mainActivity.isDataNull(date);
                    mainActivity.view_more_text(calendarDB);
                }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (Objects.equals(snapshot.getKey(), date)) {
                    calendarDB = snapshot.getValue(CalendarDB.class);
                    mainActivity.isDataNull(date);
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
