package com.hongsam.famstory.define;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.dialog.ProfileDialog;

import java.util.ArrayList;

public class ViewHolderStruct {

    public static class BasicViewHolder extends RecyclerView.ViewHolder {

        View mView;
        Object mDataList;
        Context mContext;

        public BasicViewHolder(@NonNull View itemView, Context context, Object dataList) {
            super(itemView);
            mView = itemView;
            mDataList = dataList;
            mContext = context;
            findViewById(itemView);
        }

        public void findViewById(View itemView) {}
        public void init(int position){}
    }

    public static class ViewHolderMember extends BasicViewHolder{

        ArrayList<Member> memberList;
        ImageView ivPhoto;
        TextView tvName;
        TextView tvCall;
        EditText etEdit;
        Button btnEdit;

        public ViewHolderMember(@NonNull View itemView, Context context, Object dataList) {
            super(itemView, context, dataList);
            memberList = (ArrayList<Member>)mDataList;
        }

        @Override
        public void findViewById(View itemView) {
            super.findViewById(itemView);

            ivPhoto = itemView.findViewById(R.id.i_member_iv_photo);
            tvName = itemView.findViewById(R.id.i_member_tv_name);
            tvCall = itemView.findViewById(R.id.i_member_tv_call);
            etEdit = itemView.findViewById(R.id.i_member_et_edit);
            btnEdit = itemView.findViewById(R.id.i_member_btn_edit);
        }

        @Override
        public void init(final int position) {
            super.init(position);

            Glide.with(mView).load(memberList.get(position).getPhotoUrl()).into(ivPhoto);
            tvName.setText(memberList.get(position).getName());
            tvCall.setText(memberList.get(position).getCall());

            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProfileDialog profileDialog = new ProfileDialog(mContext, memberList.get(position).getPhotoUrl());
                    profileDialog.show();
                }
            });

            tvCall.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    etEdit.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    tvCall.setVisibility(View.GONE);
                    ((MainActivity)mContext).showKeyboard(etEdit, true);
                    return true;
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etEdit.getText().length() == 0) {
                        Toast.makeText(mContext, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    tvCall.setText(etEdit.getText().toString());
                    tvCall.setVisibility(View.VISIBLE);
                    etEdit.setVisibility(View.GONE);
                    btnEdit.setVisibility(View.GONE);
                    ((MainActivity)mContext).showKeyboard(etEdit, false);
                }
            });

        }

    }

}
