package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.define.Define;

/*
 * 편지 읽기 화면 (편지목록 -> 편지읽기)
 * 1/6 , 오나영
 * */

public class LetterReadFragment extends Fragment {

    private MainActivity mainActivity;
    private View mContentView;
    private ImageButton mBackBtn;
    private TextView mFromTv, mDate, mContants;
    private ImageView mPhoto;

    private String testfamily = "테스트가족";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

    }

    /**
     * View 객체를 얻는 시점
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();

        mContentView = inflater.inflate(R.layout.fragment_letter_read, container, false);

        init(mContentView);

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     */
    public void init(View v) {
        if (v != null) {
            mBackBtn = mContentView.findViewById(R.id.letter_read_back_btn);
            mFromTv = mContentView.findViewById(R.id.f_letter_sender_tv);
            mDate = mContentView.findViewById(R.id.letter_read_date);
            mPhoto = mContentView.findViewById(R.id.read_photo_iv);
            mContants = mContentView.findViewById(R.id.read_contants_tv);

            //toolbar의 뒤로가기 버튼
            mBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                }
            });


        }
    }
//    private void readUser(){
//
//        FirebaseManager.dbFamRef.child(testfamily).child("Lettercontants").child("1").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                if(dataSnapshot.getValue(LetterContants.class) != null){
//                    LetterContants post = dataSnapshot.getValue(LetterContants.class);
//                    Log.d("FireBaseData", "getData" + post.toString());
//                } else {
//                    Toast.makeText(getContext(), "데이터 없음", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
//            }
//        });
//    }

    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
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
