package com.hongsam.famstrory.adapter;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
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
    public ArrayList<TimeLineFamily> list = new ArrayList<>();

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        if (viewType == Define.TIME_LINE_VIEW_OTHER_MSG) {
            view = inflater.inflate(R.layout.time_line_family_item, parent, false);
            return new TimeLineViewHolder(view);
        } else if (viewType == Define.TIME_LINE_VIEW_MY_MESSAGE) {
            view = inflater.inflate(R.layout.time_line_my_item, parent, false);
            return new TimeLineViewHolderMy(view);
        }
        else if (viewType == Define.TIME_LINE_DATE_LINE){
            view = inflater.inflate(R.layout.timeline_boundary,parent,false);
            return new TimeLineBoundary(view);
        }
        else{
            view = inflater.inflate(R.layout.time_line_my_item,parent,false);
            return new TimeLineViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TimeLineViewHolder) {
            ((TimeLineViewHolder) holder).setItem(list.get(position));
        }
        else if(holder instanceof TimeLineViewHolderMy){
            ((TimeLineViewHolderMy) holder).setItem(list.get(position));
        }
        else if(holder instanceof TimeLineBoundary){
            ((TimeLineBoundary) holder).timeline_boundary.setText(list.get(position).getName());
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
        TextView user_name;
        TextView user_showMessage;
        TextView user_time;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = itemView.findViewById(R.id.name);
            user_showMessage = itemView.findViewById(R.id.show_message);
            user_time = itemView.findViewById(R.id.get_time);
        }

        public void setItem(TimeLineFamily family) {
            user_name.setText(family.getName());
            user_showMessage.setText(family.getShowMessage());
            user_time.setText(family.getTime());
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
    public class TimeLineBoundary extends RecyclerView.ViewHolder{
        TextView timeline_boundary;
        public TimeLineBoundary(@NonNull View itemView) {
            super(itemView);
            timeline_boundary = itemView.findViewById(R.id.name);
        }

    }
}

