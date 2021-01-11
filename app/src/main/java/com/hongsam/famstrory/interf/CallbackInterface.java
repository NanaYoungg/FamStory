package com.hongsam.famstrory.interf;

import com.hongsam.famstrory.firebase.CalendarDB;

public interface CallbackInterface {
    public void view_more_text(CalendarDB data);

    public void isDateNull(String date);
}