package com.hongsam.famstory.dialog;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;

import android.content.Context;


public class CalendarDeleteAlertDialog extends AlertDialog.Builder {


    public CalendarDeleteAlertDialog(Context context) {
        super(context);
    }

    public CalendarDeleteAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

}
