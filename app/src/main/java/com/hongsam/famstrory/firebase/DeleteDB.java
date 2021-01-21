package com.hongsam.famstrory.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.define.Define;

public class DeleteDB {
    MainActivity mainActivity;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userName = Define.USER;
    DatabaseReference myRef = database.getReference("Family").child(userName).child("CalendarDB");
    public void databaseDelete(int Year,int Month,int Day){
        myRef.child(Year+"년").child(Month+"월").child(Day+"일").removeValue();
    }
}
