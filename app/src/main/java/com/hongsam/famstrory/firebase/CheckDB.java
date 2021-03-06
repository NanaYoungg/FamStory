package com.hongsam.famstrory.firebase;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.define.Define;

import static com.hongsam.famstrory.fragment.CalendarFragment.state;


/**
 * 날짜 데이터를 생성자로 받아와 DB에 있는지 확인
 * 2021-1-6 devaspirant0510
 */
public class CheckDB{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userName = MainActivity.famName;

    public void checkDB(int year, int month, int day, Context context, final FragmentManager fm, final Fragment fr,
                        final Button calendarCreateBtn, final Button calendarDeleteBtn, final Button calendarUpdateBtn,
                        final TextView calendarCreateText,final TextView calendarDeleteText,final TextView calendarUpdateText){
        DatabaseReference myRef = database.getReference(Define.DB_REFERENCE).child(userName).child(Define.CALENDAR_DB)
                .child(year+"년").child(month+"월").child(day+"일");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String getData = snapshot.getKey();
                if ("title".equals(getData)){
                    fm.beginTransaction().remove(fr).commit();
                    state = "ok";
                    Log.e("checkFB",Define.DATA_IS_NOT_NULL+"");
                    calendarCreateBtn.setVisibility(View.INVISIBLE);
                    calendarDeleteBtn.setVisibility(View.VISIBLE);
                    calendarUpdateBtn.setVisibility(View.VISIBLE);
                    calendarCreateText.setVisibility(View.INVISIBLE);
                    calendarDeleteText.setVisibility(View.VISIBLE);
                    calendarUpdateText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String getData = snapshot.getKey();
                if ("title".equals(getData)){
                    fm.beginTransaction().remove(fr).commit();
                    state = "ok";
                    calendarCreateBtn.setVisibility(View.INVISIBLE);
                    calendarDeleteBtn.setVisibility(View.VISIBLE);
                    calendarUpdateBtn.setVisibility(View.VISIBLE);
                    calendarCreateText.setVisibility(View.INVISIBLE);
                    calendarDeleteText.setVisibility(View.VISIBLE);
                    calendarUpdateText.setVisibility(View.VISIBLE);

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
