package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.ItemAdapter;
import com.hongsam.famstrory.data.Model;

import java.util.ArrayList;
import java.util.List;

public class LetterListFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;

    RecyclerView recyclerView;
    List<Model> itemList;

    public LetterListFragment(){

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
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();

        mContentView = inflater.inflate(R.layout.fragment_letter_list, container, false);

        recyclerView=mContentView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initData();

        recyclerView.setAdapter(new ItemAdapter(initData(),getContext()));

        return mContentView;
    }

    private List<Model> initData() {

        itemList=new ArrayList<>();
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));
        itemList.add(new Model(R.drawable.thumbnail,"video 1"));

        return itemList;
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