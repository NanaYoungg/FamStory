package com.hongsam.famstrory.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.hongsam.famstrory.R;

public class ProfileDialog extends Dialog {

    Context mContext;

    ImageView ivPhoto;
    Button btnOk, btnEdit;

    String imgUrl;

    public ProfileDialog(@NonNull Context context, String imgUrl) {
        super(context);
        mContext = context;
        this.imgUrl = imgUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        ivPhoto = findViewById(R.id.d_custom_iv_photo);
        Glide.with(mContext).load(imgUrl).into(ivPhoto);

        btnOk = findViewById(R.id.d_custom_btn_ok);
        btnEdit = findViewById(R.id.d_custom_btn_edit);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
