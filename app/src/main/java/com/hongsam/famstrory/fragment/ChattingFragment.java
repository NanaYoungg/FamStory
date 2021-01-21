package com.hongsam.famstrory.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.method.TimeKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.hongsam.famstrory.firebase.TimelineDB;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.databinding.ChattingFragmentBinding;

import java.sql.Time;

public class ChattingFragment extends Fragment {
    MainActivity mainActivity;
    View mContentView;
    private ChattingFragmentBinding mb;
    View root;
    private String name,nickname;

    public ChattingFragment(String name, String nickName) {
       this.name = name;
       this.nickname = nickName;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

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
        String user = name+"("+nickname+")";
        mb.user.setText(user);
        TimelineDB timelineDB = new TimelineDB();
        timelineDB.getToken();
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
