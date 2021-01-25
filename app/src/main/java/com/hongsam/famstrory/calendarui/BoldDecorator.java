package com.hongsam.famstrory.calendarui;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 *
 */
public class BoldDecorator implements DayViewDecorator {
    CalendarDay maxDay;
    CalendarDay minDay;


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return (day.getMonth() == maxDay.getMonth() && day.getDay() <= maxDay.getDay())
                || (day.getMonth() == minDay.getMonth() && day.getDay() >= minDay.getDay())
                || (minDay.getMonth() < day.getMonth() && day.getMonth() < maxDay.getMonth());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(Typeface.BOLD);
        view.addSpan(new RelativeSizeSpan(1.4f));
    }
}