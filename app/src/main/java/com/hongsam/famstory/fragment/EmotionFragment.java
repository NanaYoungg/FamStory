package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.adapter.EmotionAdapter;
import com.hongsam.famstory.data.Emotion;
import com.hongsam.famstory.data.Keyword;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.util.FirebaseManager;
import com.hongsam.famstory.util.SharedManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmotionFragment extends Fragment {
    private final String TAG = "EmotionFragment";

    MainActivity mainActivity;
    View mContentView;

    FirebaseDatabase mDb;
    DatabaseReference mKeywordRef;

    RecyclerView rvMain,rvSub;
    Button btnSend;
    EditText etMessage;

    ArrayList<String> mainKeywordList;
    ArrayList<ArrayList<String>> subKeywordList;

    // 임시변수. 가족생성 or 참가 할 때 저장된 값 SharedPreference에서 가져올 것.
    String famName = "테스트가족";
    public int mainIdx = 0;
    public int subIdx = 0;
    // 임시변수 끝

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

            etMessage = v.findViewById(R.id.f_emotion_et_input);

            btnSend = v.findViewById(R.id.f_emotion_btn_send);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = etMessage.getText().toString() + " " + subKeywordList.get(mainIdx).get(subIdx);
                    getFamTokens(msg, SharedManager.readString(Define.KEY_FIREBASE_TOKEN, ""));
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
    }

    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
