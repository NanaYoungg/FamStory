package com.hongsam.famstory.firebase;

import android.util.Log;

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

public class UpdateDB {
    CalendarDB calendarDB;
    MainActivity mainActivity;
    public UpdateDB(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void updateDB(int year, int month, final int day, final String date){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userName = Define.user;
        DatabaseReference myRef = database.getReference("family").child(userName).child("CalendarDB").child(year+"년").child(month+"월");
        Query query = myRef.orderByChild(day+"일");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(Objects.equals(snapshot.getKey(),day+"일")) {
                    calendarDB = snapshot.getValue(CalendarDB.class);
                    Log.e("Tag", calendarDB.getTitle());
                    mainActivity.calendarUpdateGetDialogText(calendarDB);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (Objects.equals(snapshot.getKey(),date)) {
                    calendarDB = snapshot.getValue(CalendarDB.class);
                    Log.e("Tag", calendarDB.getTitle());
                    mainActivity.calendarUpdateGetDialogText(calendarDB);
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
