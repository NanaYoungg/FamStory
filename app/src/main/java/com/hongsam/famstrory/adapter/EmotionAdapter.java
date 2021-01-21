package com.hongsam.famstrory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.data.KeywordItem;
import com.hongsam.famstrory.fragment.EmotionFragment;

import java.util.ArrayList;

/**
 * 감정표현 리사이클러뷰 어댑터
 * 작성자 : 한재훈
 * */

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.ViewHolder> {

    private final int ITEM_LARGE = 0;
    private final int ITEM_MIDDLE = 1;
    private final int ITEM_SMALL = 2;

    private final int USER_WRITE = 0;

    private ArrayList<KeywordItem> mList = null;
    private EmotionFragment emotionFragment = null;

    int itemFlag = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ToggleButton btn_item;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            btn_item = itemView.findViewById(R.id.i_emotion_btn_item);
            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (itemFlag == ITEM_LARGE) {
                        if (position == USER_WRITE) {
                            emotionFragment.showChildLayout(false);
                            emotionFragment.showEditText(ITEM_LARGE, true);
                            emotionFragment.clearTextView();
                        } else {
                            emotionFragment.showChildLayout(true);

                            emotionFragment.showEditText(ITEM_LARGE, false);

                            emotionFragment.setMiddleKeyword(position);
                            emotionFragment.largeIdx = position;
                        }
                    } else if(itemFlag == ITEM_MIDDLE){
                        if (position == USER_WRITE) {
                            emotionFragment.showEditText(ITEM_MIDDLE, true);
                        } else {
                            emotionFragment.showEditText(ITEM_MIDDLE, false);
                            emotionFragment.inputText(ITEM_MIDDLE, mList.get(position).getKeyword());
                            emotionFragment.middleIdx = position;
                        }
                    } else if (itemFlag == ITEM_SMALL) {
                        if (position == USER_WRITE) {
                            emotionFragment.showEditText(ITEM_SMALL, true);
                        } else {
                            emotionFragment.showEditText(ITEM_SMALL, false);
                            emotionFragment.inputText(ITEM_SMALL, mList.get(position).getKeyword());
                            emotionFragment.smallIdx = position;
                        }
                    }

                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).setIsClick(false);
                    }
                    mList.get(position).setIsClick(true);

                    notifyDataSetChanged();
                }
            });
        }
    }

    public EmotionAdapter(ArrayList<KeywordItem> list, EmotionFragment emotionFragment, int itemFlag) {
        mList = list;
        this.emotionFragment = emotionFragment;
        this.itemFlag = itemFlag;
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
        if (mList.get(position).getIsClick()) {
            holder.btn_item.setChecked(true);
        } else {
            holder.btn_item.setChecked(false);
        }

        String text = mList.get(position).getKeyword();
        holder.btn_item.setText(text);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
