package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.define.Define;

public class LetterWriteFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;
    ImageButton mbackBtn;
    TextView msender;
    String result;

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

        mbackBtn = mContentView.findViewById(R.id.letter_write_back_btn);
        msender = mContentView.findViewById(R.id.sender_tv);


        //toolbar의 뒤로가기 버튼
        mbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
            }
        });



        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {
            // 예시) button1 = v.findViewById(R.id.button1);
        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
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
