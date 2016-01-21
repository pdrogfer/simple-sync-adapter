package com.kavy.simplesyncadapter;

/**
 * Created by pedro on 21/01/16.
 */
public class DateModel {

    private String day;  // Year, month and day
    private String hour; // the exact time of now

    public DateModel(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "DateModel{" +
                "day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
