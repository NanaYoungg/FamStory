package com.hongsam.famstory.define;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.Member;

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

        public void findViewById(View itemView) {
        }

        public void init(int position) {
        }
    }

    public static class ViewHolderMember extends BasicViewHolder {

        ArrayList<Member> memberList;
        TextView tvName;
        TextView tvCall;
        EditText etEdit;
        Button btnEdit;

        public ViewHolderMember(@NonNull View itemView, Context context, Object dataList) {
            super(itemView, context, dataList);
            memberList = (ArrayList<Member>) mDataList;
        }

        @Override
        public void findViewById(View itemView) {
            super.findViewById(itemView);
            tvName = itemView.findViewById(R.id.i_member_tv_name);
            tvCall = itemView.findViewById(R.id.i_member_tv_call);
            etEdit = itemView.findViewById(R.id.i_member_et_edit);
            btnEdit = itemView.findViewById(R.id.i_member_btn_edit);
        }

        @Override
        public void init(final int position) {
            super.init(position);

            tvName.setText(memberList.get(position).getName());

            tvCall.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    etEdit.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    tvCall.setVisibility(View.GONE);
                    ((MainActivity) mContext).showKeyboard(etEdit, true);
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
                    ((MainActivity) mContext).showKeyboard(etEdit, false);
                }
            });

        }

    }
/*    public static class FamilyViewHolder extends BasicViewHolder{

        public FamilyViewHolder(@NonNull View itemView, Context context, Object dataList) {
            super(itemView, context, dataList);
        }
        TextView Name;
        TextView NickName;

    }*/


}


