package com.hongsam.famstory.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.adapter.LetterPaperAdapter;
import com.hongsam.famstory.data.LetterPaper;

import java.util.ArrayList;

/*
 * 편지지 고르기 다이얼로그
 * 1/13 , 오나영
 * */

public class LetterPaperDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG_PAPER_DIALOG = "dialog_paper";
    private ArrayList<LetterPaper> itemList;
    private int numberOfColumns = 3;

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

        //recycle 관련
        mRecyclerView.setHasFixedSize(true);
        //GridLayoutManager 로 한줄에 3개씩 출력
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        //initData();
        mRecyclerView.setAdapter(new LetterPaperAdapter(getContext(), (ArrayList<LetterPaper>) initData()));

        return v;
    }


    private ArrayList<LetterPaper> initData() {
        itemList = new ArrayList<>();
        itemList.add(new LetterPaper(R.drawable.paper1_preview));
        itemList.add(new LetterPaper(R.drawable.paper2_preview));
        itemList.add(new LetterPaper(R.drawable.paper3_preview));
        itemList.add(new LetterPaper(R.drawable.paper4_preview));
        itemList.add(new LetterPaper(R.drawable.paper5_preview));

        return itemList;

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
        params.height = 1100;
        window.setAttributes(params);

    }
}

