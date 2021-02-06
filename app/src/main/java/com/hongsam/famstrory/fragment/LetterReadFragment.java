package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.LetterContants;
import com.hongsam.famstrory.data.LetterList;
import com.hongsam.famstrory.define.Define;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.hongsam.famstrory.util.FirebaseManager.dbFamRef;


/*
 * 편지 읽기 화면 (편지목록 -> 편지읽기)
 * 1/6 , 오나영
 * */

public class LetterReadFragment extends Fragment {

    private MainActivity mainActivity;
    private View mContentView;

    private ImageButton mBackBtn, mdeletBtn;
    private TextView tvSender, tvDate, tvContants;
    private ImageView mPhoto, mPaper;

    private String testfamily = "테스트가족";
    private LetterList mLetterItem;

    private FirebaseDatabase mDB;


    private ArrayList<LetterContants> itemList;

    private LetterContants letterContants;

    public LetterReadFragment() {
    }

    public LetterReadFragment(LetterContants letterContants) {
        this.letterContants = letterContants;
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
            tvSender = mContentView.findViewById(R.id.f_letter_sender_tv);
            tvDate = mContentView.findViewById(R.id.letter_read_date);
            mPhoto = mContentView.findViewById(R.id.read_photo_iv);
            tvContants = mContentView.findViewById(R.id.read_contants_tv);
            mdeletBtn = mContentView.findViewById(R.id.trash_img_btn);
            mPaper = mContentView.findViewById(R.id.letter_read_img_view);


            //toolbar의 뒤로가기 버튼
            mBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                }
            });


            //삭제 버튼
            mdeletBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //휴지통 이미지버튼 누를시 DB에서 삭제
                    dbFamRef.child("LetterContants").removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "삭제 완료", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() { // DB에서 Fail날경우는 거의 없음..
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            tvSender.setText(letterContants.getSender());
            tvContants.setText(letterContants.getContants());
            tvDate.setText(letterContants.getDate());
            Log.d("이미지야", letterContants.getPhoto());
            try {
                Glide.with(getContext()).load(new URL(letterContants.getPhoto())).into(mPhoto);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            mPhoto.setImageResource(letterContants.getPhoto());
            mPaper.setImageResource(Define.LETTER_PAPAER_ARRAY[letterContants.getPaperType()]);

        }
    }


}







