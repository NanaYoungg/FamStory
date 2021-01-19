package com.hongsam.famstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.fragment.EmotionFragment;

import java.util.ArrayList;

/**
 * 감정표현 리사이클러뷰 어댑터
 * 작성자 : 한재훈
 * */

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.ViewHolder> {

    private ArrayList<String> mList = null;
    private EmotionFragment emotionFragment = null;

    boolean mainFlag = false; // true - 메인, false - sub

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn_item;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            btn_item = itemView.findViewById(R.id.i_emotion_btn_item);
            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (mainFlag) {
                        emotionFragment.setSubKeyword(position);
                        emotionFragment.mainIdx = position;
                    } else {
                        emotionFragment.subIdx = position;
                    }
                }
            });
        }
    }

    public EmotionAdapter(ArrayList<String> list, EmotionFragment emotionFragment, Boolean mainFlag) {
        mList = list;
        this.emotionFragment = emotionFragment;
        this.mainFlag = mainFlag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_emotion, parent, false);
        EmotionAdapter.ViewHolder holder = new EmotionAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String text = mList.get(position);
        holder.btn_item.setText(text);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
