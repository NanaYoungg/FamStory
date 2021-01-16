package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
<<<<<<< HEAD:app/src/main/java/com/hongsam/famstory/fragment/LetterReadFragment.java
import com.hongsam.famstory.define.Define;

/*
 * 편지 읽기 화면 (편지목록 -> 편지읽기)
 * 1/6 , 오나영
 * */
=======
>>>>>>> 3c773d7511568c0509dfb8f46feb3f3792a06073:app/src/main/java/com/hongsam/famstrory/fragment/LetterReadFragment.java

public class LetterReadFragment extends Fragment {

    private MainActivity mainActivity;
    private View mContentView;
    private ImageButton mBackBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

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

        mContentView = inflater.inflate(R.layout.fragment_letter_read, container, false);

        init(mContentView);

        return mContentView;
    }


    /*
     * 액티비티와 사용자의 상호작용 함수
     * */
    public void onResume() {
        super.onResume();

    }


        /**
         * 컨트롤 초기화 해주는 함수
         * */
    public void init(View v) {
        if (v != null) {
        mBackBtn = mContentView.findViewById(R.id.letter_read_back_btn);

            //toolbar의 뒤로가기 버튼
            mBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                }
            });



        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
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
