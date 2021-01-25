package com.hongsam.famstrory.calendarui;

import android.graphics.Typeface;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * 모든 날짜 글씨스타일 두껍게함
 * 작성 : 이승호
 * 2021-1-25
 */
public class CalendarBold implements DayViewDecorator {
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
    }
}
