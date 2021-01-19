package com.hongsam.famstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.TimeLineFamily;

import java.util.ArrayList;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.TimeLineViewHolder> {
    ArrayList<TimeLineFamily> list = new ArrayList<>();
    public interface OnItemClickListener {
        void onItemClick(ArrayList<TimeLineFamily> list,View v, int position) ;
    }



    private OnItemClickListener mListener = null ;
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    Context context;
    MainActivity mainActivity;
    public FamilyAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public FamilyAdapter.TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_line_family_item, parent, false);

        return new TimeLineViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FamilyAdapter.TimeLineViewHolder holder, int position) {
        TimeLineFamily family = list.get(position);
        holder.setItem(family);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void  addItem(TimeLineFamily family){
        list.add(family);
    }
    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView nickName;
        TextView showMessage;
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if (mListener != null) {
                            mListener.onItemClick(list,view,pos); ;
                        }


                    }

                }
            });
            name = itemView.findViewById(R.id.name);
            nickName = itemView.findViewById(R.id.nickname);
            showMessage = itemView.findViewById(R.id.show_message);

        }
        public void setItem(TimeLineFamily family){
            name.setText(family.getName());
            nickName.setText(family.getNickName());
            showMessage.setText(family.getShowMessage());
        }
    }
}

