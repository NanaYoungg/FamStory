package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.databinding.ChattingFragmentBinding;

public class ChattingFragment extends Fragment {
    MainActivity mainActivity;
    View mContentView;
    private ChattingFragmentBinding mb;
    View root;
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
        mb = ChattingFragmentBinding.inflate(getLayoutInflater());
        root = mb.getRoot();
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();

        mContentView = inflater.inflate(R.layout.fragment_setting, container,false);


        return root;
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
