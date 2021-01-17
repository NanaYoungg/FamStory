package com.hongsam.famstrory.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.LetterContants;
import com.hongsam.famstrory.data.LetterPaper;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.LetterPaperDialog;
import com.hongsam.famstrory.dialog.LetterReceiverDialog;
import com.hongsam.famstrory.util.FirebaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/*
 * 편지 쓰기 화면 (편지목록의 플로팅버튼 -> 편지읽기)
 * 1/4 , 오나영
 * */

public class LetterWriteFragment extends Fragment implements LetterReceiverDialog.OnInputSelected {

    private final int GET_GALLERY_IMAGE = 200;
    private static final String TAG = "LetterWriteFragment";

    private MainActivity mainActivity;
    private View mContentView;
    private ImageButton mBackBtn, mPhoto, mAddReciverBtn, mAddPaperBtn;
    private ImageView mPhotoView, mBackgound;
    private EditText mContants;
    private ConstraintLayout mConstraintLayout;
    private ScrollView mScrollView;
    private InputMethodManager imm;
    private Button mSendBtn;
    private TextView mToTv, mWriteDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년MM월dd일"); // 날짜 포맷
    private ArrayList<LetterPaper> mArrayList;

    private DatabaseReference mDatabase;
    private String testfamily = "테스트가족";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

   //파이어베이스에서 데이터를 추가하거나 조회하려면 DatabaseReference의 인스턴스가 필요
    mDatabase = FirebaseDatabase.getInstance().getReference("Family").child(testfamily);

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

        mContentView = inflater.inflate(R.layout.fragment_letter_write, container, false);

        init(mContentView);

        return mContentView;
    }



    /*
     * 액티비티와 사용자의 상호작용 함수
     * */
    public void onResume() {
        super.onResume();


    }


    /**
     * 컨트롤 초기화 해주는 함수
     */
    public void init(View v) {
        if (v != null) {
            mBackBtn = mContentView.findViewById(R.id.letter_write_back_btn);
            mContants = mContentView.findViewById(R.id.contants_tv);
            mPhotoView = mContentView.findViewById(R.id.photo_iv);
            mPhoto = mContentView.findViewById(R.id.gallery_img_btn);
            mConstraintLayout = mContentView.findViewById(R.id.fragment_letter_write);
            mScrollView = mContentView.findViewById(R.id.letter_write_scroll);
            mBackgound = mContentView.findViewById(R.id.letter_read_img_view);
            mSendBtn = mContentView.findViewById(R.id.letter_send_btn);
            mToTv = mContentView.findViewById(R.id.f_letter_receiever_tv);
            mAddReciverBtn = mContentView.findViewById(R.id.f_receiver_add_img_btn);
            mAddPaperBtn = mContentView.findViewById(R.id.letter_paper_img_btn);
            mWriteDate = mContentView.findViewById(R.id.letter_write_date);

            mArrayList = new ArrayList<>();

        //toolbar의 뒤로가기 버튼
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
            }
        });

        //갤러리에서 사진 가져오기 버튼
        mPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        //화면 터치시 키보드 내리기
        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mContants.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(mScrollView.getWindowToken(), 0);
            }
        });

        //받는이 선택하기
        mAddReciverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LetterReceiverDialog mletterReceiverDialog = LetterReceiverDialog.getInstance();
                mletterReceiverDialog.show(getFragmentManager(), LetterReceiverDialog.TAG_EVENT_DIALOG);
            }
        });
           
        //편지지 선택하기
        mAddPaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LetterPaperDialog mletterPaperDialog = LetterPaperDialog.getInstance();
                mletterPaperDialog.show(getFragmentManager(), LetterPaperDialog.TAG_PAPER_DIALOG);
            }
        });

        //편지 보내는 현재 날짜
        Date date = new Date();
        String time = mFormat.format(date);
        mWriteDate.setText(time); // 현재 날짜로 설정



        //편지보내기
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getSender = mToTv.getText().toString();
                String getContants = mContants.getText().toString();
                String getDate = mWriteDate.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("sender", getSender);
                result.put("contants", getContants);
                result.put("date", getDate);

                Map<String, LetterContants> letterContantsMap = new HashMap<>();
                letterContantsMap.put("1",new LetterContants(getSender, getContants, getDate));

                writeNewLetter(letterContantsMap);
//                writeNewUser("1",getUserName,getUserEmail);

                mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                Toast.makeText(getContext(), "편지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        }
    }

    //편지내용 DB저장
    private void writeNewLetter(Map<String, LetterContants> letterContantsMap) {

        FirebaseManager.dbFamRef.child(testfamily).child("LetterContants").setValue(letterContantsMap);

    }

    //스피너 받는사람 다이얼로그에서 받아오기
    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: found incoming input: " + input);

        mToTv.setText(input);
    }





    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }


    //갤러리에서 선택한 이미지 뿌려주기
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            mPhotoView.setImageURI(selectedImageUri);
        }
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
