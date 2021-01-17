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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.EmotionAdapter;
import com.hongsam.famstrory.data.Keyword;

import java.util.ArrayList;

public class EmotionFragment extends Fragment {
    private final String TAG = "EmotionFragment";

    MainActivity mainActivity;
    View mContentView;

    FirebaseDatabase mDb;
    DatabaseReference mKeywordRef;

    RecyclerView rvMain,rvSub;

    //ArrayList<Keyword> keywordList;
    ArrayList<String> mainKeywordList;
    ArrayList<ArrayList<String>> subKeywordList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

        mDb = FirebaseDatabase.getInstance();
        mKeywordRef = mDb.getReference("Keyword");

        getKeywords();
        //keywordList = new ArrayList<>();
        mainKeywordList = new ArrayList<>();
        subKeywordList = new ArrayList<>();

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

        mContentView = inflater.inflate(R.layout.fragment_emotion, null);

        //init(mContentView);
        //initDefault();

        init(mContentView);

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {

            rvMain = v.findViewById(R.id.f_emotion_rv_main);
            rvMain.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
            EmotionAdapter mainAdapter = new EmotionAdapter(mainKeywordList, this, true);
            rvMain.setAdapter(mainAdapter);

            rvSub = v.findViewById(R.id.f_emotion_rv_sub);
            rvSub.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

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
     * db에서 키워드를 불러오는 함수
     */
    public void getKeywords() {
        if (mKeywordRef != null) {
            mKeywordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                        Keyword keyword = singleSnapshot.getValue(Keyword.class);
                        mainKeywordList.add(keyword.getStrMain());
                        subKeywordList.add(keyword.getSubList());
                        //keywordList.add(singleSnapshot.getValue(Keyword.class));
                    }

                    init(mContentView);
//                    for (int i = 0; i < keywordList.size(); i++) {
//                        mainKeywordList.add(keywordList.get(i).getStrMain());
//                        subKeywordList.add(keywordList.get(i).getSubList());
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void setSubKeyword(int position) {
        if (rvSub != null) {
            EmotionAdapter subAdapter = new EmotionAdapter(subKeywordList.get(position), this, false);
            rvSub.setAdapter(subAdapter);
            subAdapter.notifyDataSetChanged();
        }
    }

    public void initDefault() {
        ArrayList<Keyword> list = new ArrayList<>();

        ArrayList feelList = new ArrayList();
        feelList.add("약간");
        feelList.add("매우");
        feelList.add("별로");
        feelList.add("조금");

        ArrayList stateList = new ArrayList();
        stateList.add("출발");
        stateList.add("도착");
        stateList.add("가는중");

        ArrayList foodList = new ArrayList();
        foodList.add("사가는중");
        foodList.add("먹고싶음");
        foodList.add("요리중");

        ArrayList actionList = new ArrayList();
        actionList.add("뛰는중");
        actionList.add("걷는중");
        actionList.add("자는중");

        ArrayList buyList = new ArrayList();
        buyList.add("사와");
        buyList.add("사줘");
        buyList.add("사줄까");

        list.add(new Keyword("기분", feelList));
        list.add(new Keyword("상태", stateList));
        list.add(new Keyword("음식", foodList));
        list.add(new Keyword("행동", actionList));
        list.add(new Keyword("심부름", buyList));


        if (mKeywordRef != null) {
            mKeywordRef.setValue(list);
        }
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
