package com.hongsam.famstrory.calendarui;

//import android.icu.util.Calendar;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import java.util.Calendar;
//import com.prolificinteractive.materialcalendarview.Calendar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class SundayDecorate implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();
    public SundayDecorate() {

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay==Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}
