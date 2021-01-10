package com.hongsam.famstrory.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.interf.CallbackInterface;

public class UpdateDB {
    MainActivity mainActivity;
    public UpdateDB(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void updateDB(String date){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userName = Define.user;
        DatabaseReference myRef = database.getReference("CalendarDB").child(userName).child(date);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CalendarDB calendarDB = snapshot.getValue(CalendarDB.class);
                mainActivity.view_more_text(calendarDB);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CalendarDB calendarDB = snapshot.getValue(CalendarDB.class);
                mainActivity.view_more_text(calendarDB);
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
