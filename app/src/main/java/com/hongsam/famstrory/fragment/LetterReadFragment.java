package com.hongsam.famstrory.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.define.Define;

import static android.app.Activity.RESULT_OK;

public class LetterReadFragment extends Fragment {

    private final int GET_GALLERY_IMAGE = 200;

    MainActivity mainActivity;
    View mContentView;
    ImageButton mBackBtn;

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

        mContentView = inflater.inflate(R.layout.fragment_letter_write, container, false);

        init(mContentView);

        return mContentView;
    }


    /*
     * 액티비티와 사용자의 상호작용 함수
     * */
    public void onResume() {
        super.onResume();

        //편지 목록으로 전환
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
            }
        });

    }


        /**
         * 컨트롤 초기화 해주는 함수
         * */
    public void init(View v) {
        if (v != null) {

        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        mBackBtn = mContentView.findViewById(R.id.letter_read_back_btn);
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