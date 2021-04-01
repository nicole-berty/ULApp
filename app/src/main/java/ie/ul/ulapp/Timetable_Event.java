package ie.ul.ulapp;

import java.io.Serializable;

public class Timetable_Event implements Serializable {
    static final int MON = 0;
    static final int TUE = 1;
    static final int WED = 2;
    static final int THU = 3;
    static final int FRI = 4;
    static final int SAT = 5;
    static final int SUN = 6;

    String eventName="";
    String eventLocation="";
    String speakerName = "";
    private int day = 0;
    private Timetable_Time_Keeper startTime;
    private Timetable_Time_Keeper endTime;

    public Timetable_Event() {
        this.startTime = new Timetable_Time_Keeper();
        this.endTime = new Timetable_Time_Keeper();
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Timetable_Time_Keeper getStartTime() {
        return startTime;
    }

    public void setStartTime(Timetable_Time_Keeper startTime) {
        this.startTime = startTime;
    }

    public Timetable_Time_Keeper getEndTime() {
        return endTime;
    }

    public void setEndTime(Timetable_Time_Keeper endTime) {
        this.endTime = endTime;
    }
}