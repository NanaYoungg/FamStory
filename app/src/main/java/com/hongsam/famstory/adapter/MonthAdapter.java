package com.hongsam.famstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.R;
import com.hongsam.famstory.data.MonthCalendar;

import java.util.ArrayList;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
    public ArrayList<MonthCalendar> list = new ArrayList<>();

    Context context;
    public MonthAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.month_calendar_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonthCalendar calendar = list.get(position);
        holder.setItem(calendar);
    }
    public void addItem(MonthCalendar calendar){
        list.add(calendar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        TextView date;
        TextView monthPlan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);
            date = itemView.findViewById(R.id.date);
            monthPlan = itemView.findViewById(R.id.plan);
        }
        public void setItem(MonthCalendar calendar){
            month.setText(calendar.getMonth());
            date.setText(calendar.getDate());
            monthPlan.setText(calendar.getMonthPlan());
        }
    }
}
