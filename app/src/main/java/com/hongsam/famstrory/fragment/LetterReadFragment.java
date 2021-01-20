package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.LetterListAdapter;
import com.hongsam.famstrory.data.LetterContants;
import com.hongsam.famstrory.data.LetterList;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.LetterReceiverDialog;
import com.hongsam.famstrory.util.FirebaseManager;

import static com.hongsam.famstrory.fragment.LetterWriteFragment.TEST_FAMILY;

/*
 * 편지 읽기 화면 (편지목록 -> 편지읽기)
 * 1/6 , 오나영
 * */

public class LetterReadFragment extends Fragment {

    private MainActivity mainActivity;
    private View mContentView;
    private ImageButton mBackBtn;
    private TextView mFromTv, mDate, mContants;
    private ImageView mPhoto, mdeletBtn;

    private String testfamily = "테스트가족";
    private LetterList mLetterItem;

    private FirebaseDatabase mDB;

    public LetterReadFragment() {
    }


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
            mdeletBtn = mContentView.findViewById(R.id.trash_img_btn);

            //toolbar의 뒤로가기 버튼
            mBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                }
            });

//
//            mdeletBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //휴지통 이미지버튼 누를시 DB에서 삭제
//                    mDB.getInstance().getReference("Family").child(TEST_FAMILY).child("LetterContants").removeValue()
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(getContext(), "삭제 완료", Toast.LENGTH_LONG).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() { // DB에서 Fail날경우는 거의 없음..
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//            });
        }
    }
}