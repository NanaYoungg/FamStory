package com.hongsam.famstrory.firebase;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstrory.adapter.FamilyAdapter;
import com.hongsam.famstrory.data.Keyword;
import com.hongsam.famstrory.data.Member;
import com.hongsam.famstrory.data.TimeLineFamily;
import com.hongsam.famstrory.define.Define;

import java.text.SimpleDateFormat;

public class TimelineDB {
    String Family = Define.DB_REFERENCE;
    String User = Define.USER;
    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
    DatabaseReference myRef = fireDB.getReference(Family).child(User).child("members");
    private static final String TAG = "TimelineDB";



    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
            Keyword keyword = singleSnapshot.getValue(Keyword.class);
/*            mainKeywordList.add(keyword.getStrMain());
            subKeywordList.add(keyword.getSubList());*/
            //keywordList.add(singleSnapshot.getValue(Keyword.class));
        }


    }
    public void getToken(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("a",snapshot.getValue()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setFamilyAdapter(final FamilyAdapter adapter){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH시mm분");
                Calendar time = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    time = Calendar.getInstance();
                }
                String getTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    getTime = dateFormat.format(time.getTime());
                }

                Log.e(TAG,snapshot.getChildrenCount()+"");
                for(DataSnapshot singleShot : snapshot.getChildren()){
                    Member member = singleShot.getValue(Member.class);
                    adapter.addItem(new TimeLineFamily(null,member.getName(),member.getRelation(),"메시지 미리보기","",8081));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
