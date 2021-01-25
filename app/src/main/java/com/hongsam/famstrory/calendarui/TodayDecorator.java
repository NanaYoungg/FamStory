package com.hongsam.famstrory.calendarui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.lang.reflect.Type;
import java.util.Calendar;

/**
 * 오늘 날짜 데코레이션
 * 작성: 이승호
 * 2021-01-25
 */
public class TodayDecorator implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();
    Drawable drawable;
    private CalendarDay date;
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        date = CalendarDay.today();

        return day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#d245d2")));
    }
}
