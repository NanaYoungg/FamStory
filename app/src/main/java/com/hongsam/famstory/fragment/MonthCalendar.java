package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.databinding.CalendarViewMoreLayoutBinding;
import com.hongsam.famstory.databinding.MonthCalendarLayoutBinding;

/**
 * CalendarFragment 에서 월별 일정보기
 * devaspirant0510
 * 2021-01-15
 */
public class MonthCalendar extends Fragment {

    MainActivity mainActivity;
    View mContentView;

    FirebaseDatabase mDb;
    DatabaseReference mFamRef;
    private MonthCalendarLayoutBinding mBinding;
    private View root;
    private RecyclerView monthCalendarList;
    private TextView title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

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
        mBinding = MonthCalendarLayoutBinding.inflate(getLayoutInflater());
        root = mBinding.getRoot();
        mContentView = inflater.inflate(R.layout.month_calendar_layout, null);

        init();

        return root;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     */
    public void init() {
        // 예시) button1 = v.findViewById(R.id.button1);
        monthCalendarList = mBinding.monthCalendarList;
        title = mBinding.title;
    }


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
