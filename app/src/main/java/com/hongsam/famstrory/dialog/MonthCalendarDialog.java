package com.hongsam.famstory.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstory.adapter.MonthAdapter;
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.data.MonthCalendar;
import com.hongsam.famstory.databinding.MonthCalendarLayoutBinding;
import com.hongsam.famstory.define.Define;

import java.util.ArrayList;

public class MonthCalendarDialog extends Dialog {
    private View root;
    private MonthCalendarLayoutBinding mb;
    private int Year,Month;
    private Dialog dialog;
    public MonthCalendarDialog(@NonNull Context context,int Year,int Month) {
        super(context);
        this.Year = Year;
        this.Month = Month;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = MonthCalendarLayoutBinding.inflate(getLayoutInflater());
        root = mb.getRoot();
        setContentView(root);
        MonthAdapter adapter = new MonthAdapter(getContext());
        mb.title.setText(Year+"년 "+Month+"월 일정");
        getCalendarDB(adapter,Year,Month);
        mb.monthCalendarList.setAdapter(adapter);
    }
    public void getCalendarDB(final MonthAdapter adapter, int Year, final int Month){
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = fireDB.getReference(Define.DB_REFERENCE).child(Define.USER).child(Define.CALENDAR_DB).child(Year+"년").child(Month+"월");
        final ArrayList<String> monthList = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot val:snapshot.getChildren()){
                    String day = val.getKey();
                    Calendar calendar = val.getValue(Calendar.class);

                    adapter.list.add(new MonthCalendar(day,"일정명 : "+calendar.getTitle(),"세부내용 : "+calendar.getDescription()));
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
