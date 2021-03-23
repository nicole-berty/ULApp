package ie.ul.timetable_viewer;

import java.io.Serializable;

public class calendar implements Serializable {
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
    private time_keep startTime;
    private time_keep endTime;

    public calendar() {
        this.startTime = new time_keep();
        this.endTime = new time_keep();
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String professorName) {
        this.eventName = professorName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String classTitle) {
        this.eventName = classTitle;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String classPlace) {
        this.eventLocation = classPlace;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public time_keep getStartTime() {
        return startTime;
    }

    public void setStartTime(time_keep startTime) {
        this.startTime = startTime;
    }

    public time_keep getEndTime() {
        return endTime;
    }

    public void setEndTime(time_keep endTime) {
        this.endTime = endTime;
    }
}