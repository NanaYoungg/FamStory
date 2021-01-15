package com.hongsam.famstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.LetterPaper;
import com.hongsam.famstory.define.Define;

import java.util.ArrayList;

/*
 * 편지지 목록 어댑터. 다이얼로그로 띄워줄거임
 * 1/13 , 오나영
 * */

public class LetterPaperAdapter extends RecyclerView.Adapter<LetterPaperAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LetterPaper> letterPaperItemList;

    public LetterPaperAdapter(Context context, ArrayList<LetterPaper> letterPaperItemList){
        this.context = context;
        this.letterPaperItemList = letterPaperItemList;
    }

    @NonNull
    @Override
    public LetterPaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.item_letter_paper, parent, false);
        LetterPaperAdapter.ViewHolder viewHolder = new LetterPaperAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull LetterPaperAdapter.ViewHolder holder, int position) {

        holder.paperImage.setImageResource(letterPaperItemList.get(position).getImage(R.drawable.paper1_preview));

//        holder.paperImage.setImageResource(R.drawable.paper1_preview);


    }


    @Override
    public int getItemCount() { return letterPaperItemList.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView paperImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            paperImage = itemView.findViewById(R.id.letter_paper_icon);
        }
    }
}
