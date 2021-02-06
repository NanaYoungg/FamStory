package com.hongsam.famstrory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.data.LetterPaper;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.fragment.LetterWriteFragment;

import java.util.ArrayList;

/*
 * 편지지 목록 어댑터. 다이얼로그로 띄워줄거임
 * 1/13 , 오나영
 * */

public class LetterPaperAdapter extends RecyclerView.Adapter<LetterPaperAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LetterPaper> letterPaperItemList;
    private LetterWriteFragment letterWriteFragment;

    int id;

    public LetterPaperAdapter(Context context, ArrayList<LetterPaper> letterPaperItemList, LetterWriteFragment letterWriteFragment){
        this.context = context;
        this.letterPaperItemList = letterPaperItemList;
        this.letterWriteFragment = letterWriteFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.item_letter_paper, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.paperImage.setImageResource(letterPaperItemList.get(position).getImage());

    }


    @Override
    public int getItemCount() { return letterPaperItemList.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView paperImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            paperImage = itemView.findViewById(R.id.letter_paper_icon);

            //리소스값 바로 받아서 실시간으로 뿌려주기
            paperImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    id = Define.LETTER_PAPAER_ARRAY[getAdapterPosition()];
                    id = letterPaperItemList.get(getAdapterPosition()).getImage();
                    letterWriteFragment.setLetterPaper(id);
                }
            });
        }

    }
}
