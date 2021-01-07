package com.hongsam.famstrory.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;

import java.util.ArrayList;

public class LetterReceiverDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public LetterReceiverDialog(){}

    public static LetterReceiverDialog getInstance(){
        LetterReceiverDialog letterReceiverDialog = new LetterReceiverDialog();
        return letterReceiverDialog;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_letter_receiver, container);
        Spinner mSpinner = (Spinner)v.findViewById(R.id.dialog_receiver);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.testSpinner));
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        Button mOkBtn = (Button)v.findViewById(R.id.dialog_receiver_ok_btn);
        Button mCancleBtn = (Button)v.findViewById(R.id.dialog_receiver_cancle_btn);
        mOkBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
        //화면터치시 꺼짐 막기
        setCancelable(false);

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