package com.hongsam.famstrory.data;

public class MonthCalendar {
    String month;
    String date;
    String monthPlan;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonthPlan() {
        return monthPlan;
    }

    public void setMonthPlan(String monthPlan) {
        this.monthPlan = monthPlan;
    }

    public MonthCalendar(String month, String date, String monthPlan) {
        this.month = month;
        this.date = date;
        this.monthPlan = monthPlan;
    }
}
