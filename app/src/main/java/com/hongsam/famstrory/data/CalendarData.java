package com.hongsam.famstrory.data;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CalendarData {
    public String title;
    public String description;
    public String startTime;
    public String endTime;
    public String type;
    public CalendarData(){

    }
    public CalendarData(String title, String description, String startTime, String endTime, String type) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
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

    public String getType(){
        return type;
    }

}
