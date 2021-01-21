package com.hongsam.famstrory.firebase;

import android.content.Context;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hongsam.famstrory.data.CalendarSpinner;
import com.hongsam.famstrory.define.Define;

import java.util.ArrayList;


public class SpinnerDB {

    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    String userName = Define.USER;
    String calDB = Define.CALENDAR_DB;
    String family = Define.DB_REFERENCE;
    DatabaseReference myRef = fireDB.getReference();
    Context context;


    public SpinnerDB(Context context) {
        this.context = context;
    }

    //List<String> itemList = new ArrayList<>();
    //ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,itemList);
    public void setSpinner(final ArrayAdapter<String> adapter) {
        myRef = fireDB.getReference(family).child(userName).child(calDB);
        Query query = myRef.orderByChild("SpinnerItem");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CalendarSpinner calendarSpinner = snapshot.getValue(CalendarSpinner.class);
                if (calendarSpinner != null) {
                    ArrayList<String> list = calendarSpinner.getItemList();
                    if (list != null) {
                        for (String val : list) {
                            adapter.add(val);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CalendarSpinner calendarSpinner = snapshot.getValue(CalendarSpinner.class);
                assert calendarSpinner != null;
                ArrayList<String> list = calendarSpinner.getItemList();
                if (list != null) {

                    for (String val : list) {
                        adapter.add(val);
                    }
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

    public void firstPutSpinnerItem(ArrayAdapter<String> arrayAdapter, ArrayList<String> itemList, String item) {

        myRef = fireDB.getReference(family).child(userName).child(calDB).child("SpinnerItem");

        CalendarSpinner calendarSpinner = new CalendarSpinner();

        calendarSpinner.setItemList(itemList);
        calendarSpinner.setSize(itemList.size());
        myRef.setValue(calendarSpinner);
    }
    public void putSpinnerItem(ArrayAdapter<String> arrayAdapter, ArrayList<String> itemList){
        myRef = fireDB.getReference(family).child(userName).child(calDB).child("SpinnerItem");

        CalendarSpinner calendarSpinner = new CalendarSpinner();

        calendarSpinner.setItemList(itemList);
        calendarSpinner.setSize(itemList.size());
        myRef.setValue(calendarSpinner);

    }
    public void clearAllSpinner(){
        myRef = fireDB.getReference(family).child(userName).child(calDB).child("SpinnerItem");
        myRef.removeValue();
    }
}


