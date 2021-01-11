package com.hongsam.famstrory.firebase;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.fragment.CalendarViewMoreFragment;

import java.util.ArrayList;

import static com.hongsam.famstrory.fragment.CalendarFragment.cal_cr_btn;
import static com.hongsam.famstrory.fragment.CalendarFragment.cal_de_btn;
import static com.hongsam.famstrory.fragment.CalendarFragment.cal_up_btn;
import static com.hongsam.famstrory.fragment.CalendarFragment.dbGetDate;
import static com.hongsam.famstrory.fragment.CalendarFragment.state;

public class CheckDB extends Fragment{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userName = Define.user;
    CalendarDB calendarDB;
    ArrayList<String> list = new ArrayList<>();
    int view =1 ;
    public void checkDB(final String date, Context context,final FragmentManager fm, final Fragment fr){

        DatabaseReference myRef = database.getReference("CalendarDB").child(userName).child(date);
        Query query = myRef.orderByChild(date);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String getData = snapshot.getKey();
                if ("title".equals(getData)){
                    fm.beginTransaction().remove(fr).commit();
                    state = "ok";
                    cal_cr_btn.setVisibility(View.INVISIBLE);
                    cal_de_btn.setVisibility(View.VISIBLE);
                    cal_up_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String getData = snapshot.getKey();
                if ("title".equals(getData)){
                    fm.beginTransaction().remove(fr).commit();
                    state = "ok";
                    cal_cr_btn.setVisibility(View.INVISIBLE);
                    cal_de_btn.setVisibility(View.VISIBLE);
                    cal_up_btn.setVisibility(View.VISIBLE);
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