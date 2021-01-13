package com.hongsam.famstory.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.define.Define;

public class DeleteDB {
    MainActivity mainActivity;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userName = Define.user;
    DatabaseReference myRef = database.getReference("CalendarDB").child(userName);
    public void databaseDelete(String date){
        myRef.child(date).removeValue();

    }
}
