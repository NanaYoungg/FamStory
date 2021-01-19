package com.hongsam.famstory.firebase;

import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstory.adapter.FamilyAdapter;
import com.hongsam.famstory.data.Keyword;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.data.TimeLineFamily;
import com.hongsam.famstory.define.Define;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TimelineDB<hour> {
    String Family = Define.DB_REFERENCE;
    String User = Define.USER;
    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    DatabaseReference myRef = fireDB.getReference(Family).child(User).child("members");
    private static final String TAG = "TimelineDB";
    java.util.Calendar cal = java.util.Calendar.getInstance();
    int hour = cal.get(java.util.Calendar.HOUR);
    int minute = cal.get(java.util.Calendar.MINUTE);
    String day =  (hour>12)?"오후":"오전";
    int h = (hour>12)?hour-12:hour;


    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
            Keyword keyword = singleSnapshot.getValue(Keyword.class);
/*            mainKeywordList.add(keyword.getStrMain());
            subKeywordList.add(keyword.getSubList());*/
            //keywordList.add(singleSnapshot.getValue(Keyword.class));
        }


    }
    public void getTokenFamily(final FamilyAdapter adapter){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.e(TAG,snapshot.getChildrenCount()+"");
                for(DataSnapshot singleShot : snapshot.getChildren()){
                    Member member = singleShot.getValue(Member.class);
                    adapter.addItem(new TimeLineFamily(null,member.getName(),member.getRelation(),"asdf",day+" "+h+":"+minute));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
