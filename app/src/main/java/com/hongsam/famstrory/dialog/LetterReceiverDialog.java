package com.hongsam.famstrory.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.fragment.LetterWriteFragment;

/*
 * 편지 받는이 선택하기 다이얼로그
 * 1/7 , 오나영
 * */

public class LetterReceiverDialog extends DialogFragment {

    public static final String TAG_EVENT_DIALOG = "dialog_event";


    public LetterReceiverDialog() {
    }

    public static LetterReceiverDialog getInstance() {
        LetterReceiverDialog letterReceiverDialog = new LetterReceiverDialog();
        return letterReceiverDialog;
    }

    //LetterWriteFragment에 데이터를 넘겨주기 위한 인터페이스
    public interface MyFragmentInterfacer {
        void onButtonClick(String input);
    }

    private MyFragmentInterfacer fragmentInterfacer;


    public void setFragmentInterfacer(MyFragmentInterfacer fragmentInterfacer) {
        this.fragmentInterfacer = fragmentInterfacer;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_letter_receiver, container);
        final Spinner mSpinner = (Spinner) v.findViewById(R.id.dialog_receiver);

        //스피너 String-array와 연동
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.member_call));
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        Button mCancleBtn = (Button) v.findViewById(R.id.dialog_receiver_cancle_btn);

        //확인버튼 누를시 스피너 값 LetterWriteFragment에 전달
        Button mOkBtn = (Button) v.findViewById(R.id.dialog_receiver_ok_btn);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "test.", Toast.LENGTH_SHORT).show();
                //선택한 스피너값 String으로 받기
                String input = mSpinner.getSelectedItem().toString();
                fragmentInterfacer.onButtonClick(input);
                getDialog().dismiss();
            }

        });

//        mOkBtn.setOnClickListener(this);

        mCancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
//        화면터치시 꺼짐 막기
        setCancelable(false);


        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 1000;
        params.height = 1000;
        window.setAttributes(params);
    }

}

