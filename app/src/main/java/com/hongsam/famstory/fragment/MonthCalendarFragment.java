package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstory.activitie.MainActivity;

import com.hongsam.famstory.adapter.MonthAdapter;
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.data.MonthCalendar;
import com.hongsam.famstory.databinding.MonthCalendarLayoutBinding;
import com.hongsam.famstory.define.Define;

import java.util.ArrayList;

/**
 * CalendarFragment 에서 월별 일정보기
 * devaspirant0510
 * 2021-01-15
 */
public class MonthCalendarFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;

    FirebaseDatabase mDb;
    DatabaseReference mFamRef;
    private MonthCalendarLayoutBinding mb;
    private View root;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        Log.e("d","D");
        mDb = FirebaseDatabase.getInstance();
        mFamRef = mDb.getReference("Family");

    }


    /**
     * View 객체를 얻는 시점
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        mainActivity = (MainActivity) getActivity();
        mb = MonthCalendarLayoutBinding.inflate(getLayoutInflater());
        root = mb.getRoot();

        MonthAdapter adapter = new MonthAdapter(getContext());
        getCalendarDB(adapter,2021,1);
        mb.monthCalendarList.setAdapter(adapter);


        return root;
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
    /**
     * 컨트롤 초기화 해주는 함수
     */



    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }

    /**
     * 각종 리소스 null 처리
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
