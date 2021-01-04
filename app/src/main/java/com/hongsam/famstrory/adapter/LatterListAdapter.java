package com.hongsam.famstrory.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.data.Letter;

import java.util.ArrayList;
import java.util.List;

public class LatterListAdapter extends RecyclerView.Adapter<LatterListAdapter.ViewHolder> {

    private OnItemClickListener mListener = null ;
    private List<Letter> mList = null ;

    //버튼 리스너 관련
    public interface OnItemClickListener{
        void onDeleteClick(View v, int pos);
    }


    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    // 생성자에서 데이터 리스트 객체를 전달받음.
    public LatterListAdapter(List<Letter> list) { mList = list ; }
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public LatterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.letter_list_item, parent, false) ;
        LatterListAdapter.ViewHolder vh = new LatterListAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(LatterListAdapter.ViewHolder holder, int position) {
        holder.sender.setText(mList.get(position).getSender());
        holder.contants.setText(String.valueOf(mList.get(position).getContents()));
        holder.date.setText(mList.get(position).getDate());

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mList.size() ;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sender;
        TextView date;
        TextView contants;
        ImageButton deleteBtn;

        ViewHolder(View itemView) {
            super(itemView) ;
            sender = itemView.findViewById(R.id.sender_tv);
            contants = itemView.findViewById(R.id.contents_tv);
            date = itemView.findViewById(R.id.date_tv);
            deleteBtn = itemView.findViewById(R.id.delete_img_btn);

            //지우기 버튼
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onDeleteClick(v, pos);
                            mList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), mList.size());
                        }
                    }
                }
            });


        }
    }

    public void changeItem(ArrayList<Letter> newList){
        mList = newList;
        notifyDataSetChanged();
    }

}