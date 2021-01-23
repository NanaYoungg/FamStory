package com.hongsam.famstrory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.data.TimeLineFamily;
import com.hongsam.famstrory.define.Define;

import java.util.ArrayList;

public class FamilyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<TimeLineFamily> list = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(ArrayList<TimeLineFamily> list, View v, int position);
    }


    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    Context context;

    public FamilyAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public FamilyAdapter.TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType == Define.TIME_LINE_VIEW_OTHER_MSG) {
            view = inflater.inflate(R.layout.time_line_family_item, parent, false);
        } else {
            view = inflater.inflate(R.layout.time_line_my_item, parent, false);
        }
        return new TimeLineViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TimeLineViewHolder) {
            TimeLineFamily family = list.get(position);
            ((TimeLineViewHolder) holder).setItem(family);
        }
        else if(holder instanceof TimeLineViewHolderMy){
            TimeLineFamily family = list.get(position);
            ((TimeLineViewHolderMy) holder).setItem(family);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(TimeLineFamily family) {
        list.add(family);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView nickName;
        TextView showMessage;
        TextView time;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(list, view, pos);
                            ;
                        }
                    }
                }
            });
            name = itemView.findViewById(R.id.name);
            nickName = itemView.findViewById(R.id.nickname);
            showMessage = itemView.findViewById(R.id.show_message);
            time = itemView.findViewById(R.id.get_time);
        }

        public void setItem(TimeLineFamily family) {
            name.setText(family.getName());
            nickName.setText(family.getNickName());
            showMessage.setText(family.getShowMessage());
            time.setText(family.getTime());
        }
    }

    public class TimeLineViewHolderMy extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        TextView getTime;

        public TimeLineViewHolderMy(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.show_message);
            getTime = itemView.findViewById(R.id.get_time);
        }

        public void setItem(TimeLineFamily timeLineFamily){
            name.setText(timeLineFamily.getName());
            message.setText(timeLineFamily.getShowMessage());
            getTime.setText(timeLineFamily.getTime());

        }
    }
}

