package com.hongsam.famstory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.adapter.RecyclerAdapter;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.define.Define;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;

    ImageView ivMain;
    RecyclerView rvMember;

    EditText etTitle;
    TextView tvTitle;
    Button btnTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }


    /**
     * View 객체를 얻는 시점
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();

        mContentView = inflater.inflate(R.layout.fragment_profile, null);

        init(mContentView);

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {

            etTitle = v.findViewById(R.id.f_profile_et_title);
            tvTitle = v.findViewById(R.id.f_profile_tv_title);
            btnTitle = v.findViewById(R.id.f_profile_btn_title);

            tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    etTitle.setVisibility(View.VISIBLE);
                    btnTitle.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.GONE);
                    mainActivity.showKeyboard(etTitle, true);
                    return true;
                }
            });

            btnTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (etTitle.getText().length() == 0) {
                        Toast.makeText(mainActivity, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    tvTitle.setText(etTitle.getText().toString());
                    tvTitle.setVisibility(View.VISIBLE);
                    etTitle.setVisibility(View.GONE);
                    btnTitle.setVisibility(View.GONE);

                    mainActivity.showKeyboard(etTitle, false);
                }
            });

            ivMain = mContentView.findViewById(R.id.f_profile_iv_main);
            ivMain.setClipToOutline(true);

            ArrayList<Member> memberList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Member member = new Member("홍길동"+i, "호칭"+i, "https://firebasestorage.googleapis.com/v0/b/hongkathon.appspot.com/o/selfie.png?alt=media&token=2b623453-a82f-4a33-8195-d6ae948ebc59");
                memberList.add(member);
            }

            rvMember = mContentView.findViewById(R.id.f_profile_rv_member);
            RecyclerAdapter rvAdapterB = new RecyclerAdapter(mainActivity, R.layout.item_member, Define.VIEWTYPE_MEMBER, memberList);
            rvMember.setAdapter(rvAdapterB);

            // 예시) button1 = v.findViewById(R.id.button1);

        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }

    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
