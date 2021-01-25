package com.hongsam.famstrory.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hongsam.famstrory.R;

public class YesNoDialog extends Dialog {

    TextView tvTitle, tvContents;
    Button btnYes, btnNo;

    String title, contents;

    private YesNoDialogListener yesNoDialogListener;

    public interface YesNoDialogListener {
        void onYes();
        void onNo();
    }

    public void setOnYesNoListener(YesNoDialogListener yesNoDialogListener) {
        this.yesNoDialogListener = yesNoDialogListener;
    }

    public YesNoDialog(@NonNull Context context) {
        super(context);
    }

    public YesNoDialog(@NonNull Context context, String title, String contents) {
        super(context);

        this.title = title;
        this.contents = contents;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_yes_no);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        tvTitle = findViewById(R.id.d_yes_no_tv_title);
        tvContents = findViewById(R.id.d_yes_no_tv_contents);

        btnYes = findViewById(R.id.d_yes_no_btn_yes);
        btnNo = findViewById(R.id.d_yes_no_btn_no);

        tvTitle.setText(title);
        tvContents.setText(contents);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesNoDialogListener.onYes();
                dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesNoDialogListener.onNo();
                dismiss();
            }
        });
    }
}
