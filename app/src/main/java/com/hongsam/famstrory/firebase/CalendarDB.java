package com.hongsam.famstrory.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CalendarDB {
    public String title;
    public String description;
    public String startTime;
    public String endTime;
    public CalendarDB(){

    }
    public CalendarDB(String title, String description, String startTime, String endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

}
