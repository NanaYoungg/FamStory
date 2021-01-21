package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.hongsam.famstrory.data.Emotion;
import com.hongsam.famstrory.data.Family;
import com.hongsam.famstrory.data.Keyword;
import com.hongsam.famstrory.data.KeywordItem;
import com.hongsam.famstrory.data.Member;
import com.hongsam.famstrory.database.DBFamstory;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.util.FirebaseManager;
import com.hongsam.famstrory.util.SharedManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EmotionFragment extends Fragment {
    private final String TAG = "EmotionFragment";

    MainActivity mainActivity;
    View mContentView;

    LinearLayout layoutLarge, layoutMiddle, layoutSmall;
    RecyclerView rvLarge, rvMiddle, rvSmall;
    EditText etLarge, etMiddle, etSmall;
    TextView tvLarge, tvMiddle, tvSmall;
    Button btnSend;

    DatabaseReference mKeywordRef;

    ArrayList<KeywordItem> largeKeywordList;
    ArrayList<ArrayList<KeywordItem>> middleKeywordList;
    ArrayList<ArrayList<KeywordItem>> smallKeywordList;

    // 임시변수. 가족생성 or 참가 할 때 저장된 값 SharedPreference에서 가져올 것.
    String famName = "테스트가족";
    public int largeIdx = 0;
    public int middleIdx = 0;
    public int smallIdx = 0;
    // 임시변수 끝

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mKeywordRef = FirebaseManager.firebaseDB.getReference("Keyword");
        
        largeKeywordList = new ArrayList<>();
        middleKeywordList = new ArrayList<>();
        smallKeywordList = new ArrayList<>();
        Define.keywordMap = new HashMap<>();

        getKeywords();
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

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {

            layoutLarge = v.findViewById(R.id.f_emotion_layout_large_category);
            layoutMiddle = v.findViewById(R.id.f_emotion_layout_middle_category);
            layoutSmall = v.findViewById(R.id.f_emotion_layout_small_category);

            tvLarge = v.findViewById(R.id.f_emotion_tv_large);
            tvMiddle = v.findViewById(R.id.f_emotion_tv_middle);
            tvSmall = v.findViewById(R.id.f_emotion_tv_small);

            etLarge = v.findViewById(R.id.f_emotion_et_large);
            etMiddle = v.findViewById(R.id.f_emotion_et_middle);
            etSmall = v.findViewById(R.id.f_emotion_et_small);

            etLarge.addTextChangedListener(new EmotionTextWatcher(0));
            etMiddle.addTextChangedListener(new EmotionTextWatcher(1));
            etSmall.addTextChangedListener(new EmotionTextWatcher(2));
            
            rvLarge = v.findViewById(R.id.f_emotion_rv_large);
            rvLarge.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));
            EmotionAdapter mainAdapter = new EmotionAdapter(largeKeywordList, this, 0);
            rvLarge.setAdapter(mainAdapter);

            rvMiddle = v.findViewById(R.id.f_emotion_rv_middle);
            rvMiddle.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

            rvSmall = v.findViewById(R.id.f_emotion_rv_small);
            rvSmall.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

            btnSend = v.findViewById(R.id.f_emotion_btn_send);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 감정표현 보내기
                }
            });

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

        largeKeywordList.clear();
        middleKeywordList.clear();
        smallKeywordList.clear();

        if (Define.keywordMap.isEmpty()) {
            Log.d(TAG, "keywordMap is null -> firebase에서 가져옴");
            mKeywordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                        largeKeywordList.add(new KeywordItem(singleSnapshot.getKey()));

                        Keyword keyword = singleSnapshot.getValue(Keyword.class);
                        middleKeywordList.add(keyword.getMiddleKeyword());
                        smallKeywordList.add(keyword.getSmallKeyword());
                        Define.keywordMap.put(singleSnapshot.getKey(), keyword);
                    }

                    largeKeywordList.add(new KeywordItem("직접입력"));
                    init(mContentView);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Log.d(TAG, "keywordMap is null -> Define.keywordMap 에서 가져옴");
            for (Map.Entry<String, Keyword> entry : Define.keywordMap.entrySet()) {
                largeKeywordList.add(new KeywordItem(entry.getKey()));
                Keyword keyword = entry.getValue();
                middleKeywordList.add(keyword.getMiddleKeyword());
                smallKeywordList.add(keyword.getSmallKeyword());
            }

            largeKeywordList.add(new KeywordItem("직접입력"));
            init(mContentView);
        }
    }

    public void setMiddleKeyword(int position) {

        // 대분류 직접입력때문에 인덱스가 안맞아서 하나 빼줘야됨...
        if (position != 0) {
            position -= 1;
        }

        if (rvMiddle != null) {
            EmotionAdapter subAdapter = new EmotionAdapter(middleKeywordList.get(position), this, 1);
            rvMiddle.setAdapter(subAdapter);
            subAdapter.notifyDataSetChanged();
        }

        if (rvSmall != null) {
            EmotionAdapter subAdapter = new EmotionAdapter(smallKeywordList.get(position), this, 2);
            rvSmall.setAdapter(subAdapter);
            subAdapter.notifyDataSetChanged();
        }
    }

    public void showEditText(int type, boolean flag) {
        switch (type) {
            case 0:
                etLarge.setVisibility(flag ? View.VISIBLE : View.GONE);
                break;
            case 1:
                etMiddle.setVisibility(flag ? View.VISIBLE : View.GONE);
                break;
            case 2:
                etSmall.setVisibility(flag ? View.VISIBLE : View.GONE);
                break;
        }
    }

    public void showChildLayout(boolean flag) {
        if (flag) {
            layoutMiddle.setVisibility(View.VISIBLE);
            layoutSmall.setVisibility(View.VISIBLE);
        } else {
            layoutMiddle.setVisibility(View.GONE);
            layoutSmall.setVisibility(View.GONE);
        }
    }

    public void clearTextView() {
        etLarge.setText("");
        etLarge.setText("");
        etLarge.setText("");

        tvLarge.setText("");
        tvMiddle.setText("");
        tvSmall.setText("");
    }

    public void makeSentence(int type, String msg) {
        switch (type) {
            case 0:
                tvLarge.setText(msg);
                break;
            case 1:
                tvMiddle.setText(msg);
                break;
            case 2:
                tvSmall.setText(msg);
                break;
        }
    }

    public void getFamTokens(final String msg, final String token) {
        if(msg.isEmpty())
            return;

        DatabaseReference ref = FirebaseManager.dbFamRef.child(famName).child("members");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> relationList = new ArrayList<>();
                ArrayList<String> tokenList = new ArrayList<>();

                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    if (!singleSnapshot.getKey().equals(token)) {
                        Log.d(TAG, "saveToken : singleSnapshot.getKey : " + singleSnapshot.getKey());
                        tokenList.add(singleSnapshot.getKey());
                        relationList.add(singleSnapshot.getValue(Member.class).getRelation());
                    }
                }

                sendEmotion(relationList, tokenList, msg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendEmotion(ArrayList<String> relationList, ArrayList<String> tokenList, String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String today = sdf.format(date);

        for (int i = 0; i < tokenList.size(); i++) {
            DatabaseReference ref = FirebaseManager.dbFamRef.child(famName).child("messages").child(tokenList.get(i));
            ref.setValue(new Emotion(relationList.get(i), msg, today));
        }

        Emotion emotionMe = new Emotion("아들", msg, today);
        DBFamstory.getInstance(mainActivity).insertEmotion(emotionMe);
    }

    public void initMap() {
//        //largeKeywordList.add(new KeywordItem("직접입력"));
//        largeKeywordList.add(new KeywordItem("기분"));
//        largeKeywordList.add(new KeywordItem("상태"));
//        largeKeywordList.add(new KeywordItem("행동"));
//        largeKeywordList.add(new KeywordItem("음식"));
//        largeKeywordList.add(new KeywordItem("심부름"));
//
//        ArrayList<KeywordItem> feelItem = new ArrayList<>();
//        feelItem.add(new KeywordItem("직접입력"));
//        feelItem.add(new KeywordItem("약간"));
//        feelItem.add(new KeywordItem("엄청"));
//        feelItem.add(new KeywordItem("별로"));
//        feelItem.add(new KeywordItem("조금"));
//
//        ArrayList<KeywordItem> stateItem = new ArrayList<>();
//        stateItem.add(new KeywordItem("직접입력"));
//        stateItem.add(new KeywordItem("엄청"));
//        stateItem.add(new KeywordItem("많이"));
//        stateItem.add(new KeywordItem("살짝"));
//        stateItem.add(new KeywordItem("조금"));
//
//        ArrayList<KeywordItem> actionItem = new ArrayList<>();
//        actionItem.add(new KeywordItem("직접입력"));
//        actionItem.add(new KeywordItem("집"));
//        actionItem.add(new KeywordItem("친구네집"));
//        actionItem.add(new KeywordItem("시장"));
//        actionItem.add(new KeywordItem("회사"));
//
//        ArrayList<KeywordItem> foodItem = new ArrayList<>();
//        foodItem.add(new KeywordItem("직접입력"));
//        foodItem.add(new KeywordItem("피자"));
//        foodItem.add(new KeywordItem("치킨"));
//        foodItem.add(new KeywordItem("햄버거"));
//
//        ArrayList<KeywordItem> errandItem = new ArrayList<>();
//        errandItem.add(new KeywordItem("직접입력"));
//        errandItem.add(new KeywordItem("과자"));
//        errandItem.add(new KeywordItem("음료수"));
//
//        middleKeywordList.add(feelItem);
//        middleKeywordList.add(stateItem);
//        middleKeywordList.add(actionItem);
//        middleKeywordList.add(foodItem);
//        middleKeywordList.add(errandItem);
//
//        ArrayList<KeywordItem> feelSmall = new ArrayList<>();
//        feelSmall.add(new KeywordItem("직접입력"));
//        feelSmall.add(new KeywordItem("화나요"));
//        feelSmall.add(new KeywordItem("지루해요"));
//        feelSmall.add(new KeywordItem("슬퍼요"));
//        feelSmall.add(new KeywordItem("신나요"));
//
//        ArrayList<KeywordItem> stateSmall = new ArrayList<>();
//        stateSmall.add(new KeywordItem("직접입력"));
//        stateSmall.add(new KeywordItem("아파요"));
//        stateSmall.add(new KeywordItem("배고파요"));
//        stateSmall.add(new KeywordItem("배불러요"));
//        stateSmall.add(new KeywordItem("졸려요"));
//        stateSmall.add(new KeywordItem("피곤해요"));
//
//        ArrayList<KeywordItem> actionSmall = new ArrayList<>();
//        actionSmall.add(new KeywordItem("직접입력"));
//        actionSmall.add(new KeywordItem("가는중이에요"));
//        actionSmall.add(new KeywordItem("퇴근중이에요"));
//        actionSmall.add(new KeywordItem("출근중이에요"));
//
//        ArrayList<KeywordItem> foodSmall = new ArrayList<>();
//        foodSmall.add(new KeywordItem("직접입력"));
//        foodSmall.add(new KeywordItem("요리중이에요"));
//        foodSmall.add(new KeywordItem("먹는중이에요"));
//        foodSmall.add(new KeywordItem("먹고싶어요"));
//        foodSmall.add(new KeywordItem("사가는중이에요"));
//        foodSmall.add(new KeywordItem("땡겨요"));
//
//        ArrayList<KeywordItem> errandSmall = new ArrayList<>();
//        errandSmall.add(new KeywordItem("직접입력"));
//        errandSmall.add(new KeywordItem("사주세요"));
//        errandSmall.add(new KeywordItem("사오세요"));
//        errandSmall.add(new KeywordItem("사줄까요?"));
//        errandSmall.add(new KeywordItem("사갈게요"));
//
//        smallKeywordList.add(feelSmall);
//        smallKeywordList.add(stateSmall);
//        smallKeywordList.add(actionSmall);
//        smallKeywordList.add(foodSmall);
//        smallKeywordList.add(errandSmall);
//
//        for (int i = 0; i < largeKeywordList.size(); i++) {
//            mainMap.put(largeKeywordList.get(i).getKeyword(),new Keyword(middleKeywordList.get(i), smallKeywordList.get(i)));
//        }
//
//        FirebaseManager.firebaseDB.getReference("Keyword").setValue(mainMap);
    }

    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class EmotionTextWatcher implements TextWatcher {

        int type = 0;

        public EmotionTextWatcher(int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (type) {
                case 0:
                    tvLarge.setText(charSequence.toString());
                    break;
                case 1:
                    tvMiddle.setText(charSequence.toString());
                    break;
                case 2:
                    tvSmall.setText(charSequence.toString());
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
