package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;

public class LetterListFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;
    Toolbar mtoolbar;
    ImageButton mbackBtn;
    ImageButton mdeleteBtn;

    FirebaseDatabase mDb;
    DatabaseReference mFamRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

//        mDb = FirebaseDatabase.getInstance();
//        mFamRef = mDb.getReference("Latter");

    }


    /**
     * View 객체를 얻는 시점
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();

        mContentView = inflater.inflate(R.layout.fragment_letter_list, container, false);
//        //뒤로가기 버튼 -> 타임라인으로
//        mbackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.onFragmentChanged(0);
//            }
//        });

        setHasOptionsMenu(true);

        init(mContentView);

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {
            mtoolbar = v.findViewById(R.id.toolbar);
            mbackBtn = v.findViewById(R.id.back_btn);
            mdeleteBtn = v.findViewById(R.id.trash_btn);
        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
//        mbackBtn.setImageResource(R.drawable.back_btn_customise);

    }

    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
