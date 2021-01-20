package com.hongsam.famstrory.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.LetterList;
import com.hongsam.famstrory.databinding.FragmentLetterReadBinding;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.LetterReceiverDialog;
import com.hongsam.famstrory.fragment.LetterReadFragment;
import com.hongsam.famstrory.fragment.LetterWriteFragment;

import java.util.ArrayList;

/*
 * 편지 목록 리싸이클 뷰의 어댑터.    (보낸이,내용,날짜)
 * 1/5 , 오나영
 * */

public class LetterListAdapter extends RecyclerView.Adapter<LetterListAdapter.ViewHolder> {

    private ArrayList<LetterList> letterItemList;
    private Context context;


    public LetterListAdapter(ArrayList<LetterList> letterItemList, Context context) {
        this.letterItemList = letterItemList;
        this.context = context;

    }


    @NonNull
    @Override
    public LetterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LetterListAdapter.ViewHolder holder, final int position) {

        holder.sender.setText(letterItemList.get(position).getSender());
        holder.contants.setText(letterItemList.get(position).getContants());
        holder.date.setText(letterItemList.get(position).getDate());

//        LetterReadFragment letterReadFragment = new LetterReadFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("sender", current.sender);
//        bundle.putString("contants", current.contants);
//        bundle.putString("date", current.date);
//        letterReadFragment.setArguments(bundle);

    }

    @Override
    public int getItemCount() {
        return letterItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sender;
        public TextView contants;
        public TextView date;
        public LinearLayout linearLayout;
        public RelativeLayout viewBackgound;
        public CardView cardView;

        //뷰홀더 생성자로 전달되는 뷰 객체 참조
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //뷰객체에 들어있는 텍스트뷰 등 참고
            sender = itemView.findViewById(R.id.ltter_list_sender_tv);
            contants = itemView.findViewById(R.id.ltter_list_contents_tv);
            date = itemView.findViewById(R.id.ltter_list_date_tv);

            linearLayout = itemView.findViewById(R.id.layout_id);
//            viewBackgound = itemView.findViewById(R.id.view_background);
            cardView = itemView.findViewById(R.id.letter_cardView);

//            카드뷰 클릭시 해당 내용 읽기로 이동
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //값 넘겨주기


                    //화면 넘어가기 함수
                    ((MainActivity) context).changeFragment(Define.FRAGMENT_ID_LETTER_READ);
                }
            });
        }

    }


}


