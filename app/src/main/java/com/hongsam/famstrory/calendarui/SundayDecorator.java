package com.hongsam.famstrory.calendarui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.fonts.Font;
import android.text.style.ForegroundColorSpan;

import java.time.format.TextStyle;
import java.util.Calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
/**
 * 일요일 마다 글씨 색 데코레이션
 * 작성: 이승호
 * 2021-01-25
 */
public class SundayDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    public SundayDecorator(){

    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay== Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}
