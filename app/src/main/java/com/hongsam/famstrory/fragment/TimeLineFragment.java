package com.hongsam.famstrory.fragment;

import android.annotation.SuppressLint;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.FamilyAdapter;
import com.hongsam.famstrory.data.Emotion;
import com.hongsam.famstrory.data.TimeLineFamily;
import com.hongsam.famstrory.database.DBFamstory;
import com.hongsam.famstrory.databinding.FragmentTimeLineBinding;
import com.hongsam.famstrory.firebase.TimelineDB;


import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class TimeLineFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;
    private FragmentTimeLineBinding mb;
    private View root;
    FirebaseDatabase mDb;
    DatabaseReference mFamRef;

    private TimelineDB timelineDB = new TimelineDB();
    sendTimeLineFR timeLineFR = null;
    public interface sendTimeLineFR{
        public void sendName(String name,String nickName);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setHasOptionsMenu(true);

        mDb = FirebaseDatabase.getInstance();
        mFamRef = mDb.getReference("Family");
        Log.e("timelin","timeline");


    }


    /**
     * View 객체를 얻는 시점
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mb = FragmentTimeLineBinding.inflate(getLayoutInflater());
        root  = mb.getRoot();

        if (container == null)
            return null;

        if (inflater == null)
            return null;


        mainActivity = (MainActivity) getActivity();
        FamilyAdapter adapter =new FamilyAdapter(getContext());
        mContentView = inflater.inflate(R.layout.fragment_time_line, container,false);
        mb.familyChatList.setAdapter(adapter);
        DBFamstory dbFamstory = new DBFamstory(getContext());
        ArrayList<Emotion> emotions  = dbFamstory.selectEmotionList();

        for(Emotion emotion:emotions){

            adapter.addItem(new TimeLineFamily(null,emotion.getSender(),"",emotion.getMessage(),emotion.getSendDate(),8080));
        }

        //timelineDB.setFamilyAdapter(adapter);
        adapter.setOnItemClickListener(new FamilyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<TimeLineFamily> list , View v, int position) {

                TimeLineFamily getItem = list.get(position);
                mainActivity.sendName(getItem.getName(),getItem.getNickName());

            }
        });
        return root;
    }



    /**
     * 컨트롤 초기화 해주는 함수
     * */


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
