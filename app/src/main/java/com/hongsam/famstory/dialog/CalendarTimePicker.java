package com.hongsam.famstory.dialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class CalendarTimePicker extends TimePickerDialog implements TimePickerDialog.OnTimeSetListener{
    public CalendarTimePicker(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
