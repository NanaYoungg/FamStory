package com.hongsam.famstrory.calendarui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.time.LocalDate;
import java.util.Date;

public class OneDayDecorator implements DayViewDecorator {
    private CalendarDay date;
    public OneDayDecorator(){
        date = CalendarDay.today();
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date!= null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.GREEN));
    }

    public void setDate(Date date){
        this.date =CalendarDay.from(date.getYear(),date.getMonth(),date.getDay());
    }
}
