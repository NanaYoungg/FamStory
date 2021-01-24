package com.hongsam.famstrory.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

public class YesNoDialog extends Dialog {

    public YesNoDialog(@NonNull Context context) {
        super(context);
    }

    public YesNoDialog(@NonNull Context context, String title, String contents) {
        super(context);
    }
}
