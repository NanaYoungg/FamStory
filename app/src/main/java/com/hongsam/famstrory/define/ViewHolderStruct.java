package com.hongsam.famstrory.define;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.Member;
import com.hongsam.famstrory.database.DBFamstory;

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
        TextView tvRelation;
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
            tvRelation = itemView.findViewById(R.id.i_member_tv_relation);
            tvName = itemView.findViewById(R.id.i_member_tv_name);
            tvCall = itemView.findViewById(R.id.i_member_tv_call);
            etEdit = itemView.findViewById(R.id.i_member_et_edit);
            btnEdit = itemView.findViewById(R.id.i_member_btn_edit);
        }

        @Override
        public void init(final int position) {
            super.init(position);

            tvRelation.setText(memberList.get(position).getRelation());
            tvName.setText(memberList.get(position).getName());
            tvCall.setText(memberList.get(position).getCall());

            tvCall.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    tvName.setVisibility(View.GONE);
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

                    DBFamstory.getInstance(mContext).updateMemberCall(tvRelation.getText().toString(), etEdit.getText().toString());
                    //Define.memberList.get(position).setCall(etEdit.getText().toString());

                    tvCall.setText(etEdit.getText().toString());
                    tvCall.setVisibility(View.VISIBLE);
                    tvName.setVisibility(View.VISIBLE);
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


