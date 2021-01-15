package com.hongsam.famstory.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.adapter.LetterPaperAdapter;
import com.hongsam.famstory.dialog.LetterReceiverDialog;

import java.util.List;

/*
 * 편지지 고르기 다이얼로그
 * 1/13 , 오나영
 * */

public class LetterPaperDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG_PAPER_DIALOG = "dialog_paper";

    public LetterPaperDialog(){}

    public static LetterPaperDialog getInstance(){
        LetterPaperDialog letterpaperDialog = new LetterPaperDialog();
        return letterpaperDialog;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_letter_paper, container);
        LetterPaperAdapter mAdapter = null;
        RecyclerView mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_letter_paper);
        Button mOkBtn = (Button)v.findViewById(R.id.dialog_paper_ok_btn);
        Button mCancleBtn = (Button)v.findViewById(R.id.dialog_paper_cancle_btn);

        mOkBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
        //화면터치시 꺼짐 막기
        setCancelable(false);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 1000;
        params.height = 1000;
        window.setAttributes(params);

    }
}

