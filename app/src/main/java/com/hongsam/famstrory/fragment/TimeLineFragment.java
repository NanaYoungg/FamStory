package com.hongsam.famstrory.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.FamilyAdapter;
import com.hongsam.famstrory.data.Emotion;
import com.hongsam.famstrory.data.Member;
import com.hongsam.famstrory.data.TimeLineFamily;
import com.hongsam.famstrory.database.DBFamstory;
import com.hongsam.famstrory.databinding.FragmentTimeLineBinding;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.firebase.TimelineDB;
import com.hongsam.famstrory.util.FirebaseManager;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class TimeLineFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;
    private FragmentTimeLineBinding mb;
    private View root;
    ArrayList<Emotion> emotions;
    private static final String TAG = "TimelineFragment";
    FirebaseDatabase mDb;
    String token;
    FamilyAdapter adapter;
    DatabaseReference mFamRef;

    private String id = "";
    private TimelineDB timelineDB = new TimelineDB();
    sendTimeLineFR timeLineFR = null;

    public interface sendTimeLineFR {
        public void sendName(String name, String nickName);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setHasOptionsMenu(true);

        mDb = FirebaseDatabase.getInstance();
        mFamRef = mDb.getReference("Family");
        Log.e("timelin", "timeline");

    }


    /**
     * View 객체를 얻는 시점
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentTimeLineBinding.inflate(getLayoutInflater());
        root = mb.getRoot();


        Log.e("a", MainActivity.relation + "");
        //getMyID();
        if (container == null)
            return null;

        if (inflater == null)
            return null;


        mainActivity = (MainActivity) getActivity();
        adapter = new FamilyAdapter(getContext());
        mb.familyChatList.setAdapter(adapter);
        DBFamstory dbFamstory = new DBFamstory(getContext());
        emotions = dbFamstory.selectEmotionList();


        return root;
    }

    public void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "파이어베이스 토큰 등록 실패", task.getException());
                            return;
                        }

                        token = task.getResult();
                        Log.e("a", token);
                    }
                });
    }

    public void getMyID() {
        final FirebaseDatabase fireDB = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = fireDB.getReference("Family").child("강력한가족").child("members").child(token);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Member member = snapshot.getValue(Member.class);
                if (member != null) {
                    id = member.getRelation();
                    Log.e("dd", id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    /**
     * 컨트롤 초기화 해주는 함수
     */
    @Override
    public void onResume() {
        super.onResume();
        adapter = new FamilyAdapter(getContext());
        mb.familyChatList.setAdapter(adapter);
        DBFamstory dbFamstory = new DBFamstory(getContext());
        emotions = dbFamstory.selectEmotionList();
        for (int i=0; i<emotions.size(); i++) {
            Log.e("my id", id + "");
            Log.e(TAG, emotions.get(i).getSender() + " " + id);
            String getTime = emotions.get(i).getSendDate().substring(11, 16);
            Log.e("Df",emotions.get(i).getSendDate().substring(8,10)+""+emotions.get(i).getSendDate().substring(8,10));

            if (emotions.get(i).getSender().equals(MainActivity.relation)) {
                //adapter.list.add(new TimeLineFamily(null, "", "", emotion.getMessage(), getTime, Define.TIME_LINE_VIEW_MY_MESSAGE));
                adapter.addItem(new TimeLineFamily(null, emotions.get(i).getSender(), "", emotions.get(i).getMessage(), getTime, Define.TIME_LINE_VIEW_MY_MESSAGE));
            } else {
                //adapter.list.add(new TimeLineFamily(null, "", "", emotion.getMessage(), getTime, Define.TIME_LINE_VIEW_OTHER_MSG));
                adapter.addItem(new TimeLineFamily(null, emotions.get(i).getSender(), "", emotions.get(i).getMessage(), getTime, Define.TIME_LINE_VIEW_OTHER_MSG));
            }
            if(i!=emotions.size()-1) {
                if (!emotions.get(i).getSendDate().substring(8, 10).equals(emotions.get(i + 1).getSendDate().substring(8, 10))) {
                    adapter.addItem(new TimeLineFamily(null, emotions.get(i).getSendDate().substring(0, 9), null, null, null, Define.TIME_LINE_DATE_LINE));
                }
            }
        }

        Toast.makeText(getContext(), "onResume()", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
        for (Emotion emotion : emotions) {
            Log.e("list", emotion.getMessage() + "");
        }
        mb.familyChatList.scrollToPosition(adapter.getItemCount() - 1);
    }

    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }

    /**
     * 각종 리소스 null 처리
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
