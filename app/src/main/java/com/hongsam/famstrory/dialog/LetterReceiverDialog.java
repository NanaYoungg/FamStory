package com.hongsam.famstrory.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hongsam.famstrory.R;

public class LetterReceiverDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public LetterReceiverDialog(){}

    public static LetterReceiverDialog getInstance(){
        LetterReceiverDialog letterReceiverDialog = new LetterReceiverDialog();
        return letterReceiverDialog;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_letter_receiver, container);
        Button mOkBtn = (Button)v.findViewById(R.id.dialog_receiver_ok_btn);
        mOkBtn.setOnClickListener(this);
        //화면터치시 꺼짐 막기
        setCancelable(false);
        return v;
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}