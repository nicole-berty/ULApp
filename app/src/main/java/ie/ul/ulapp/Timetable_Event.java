package ie.ul.ulapp;

import java.io.Serializable;

public class Timetable_Event implements Serializable {

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

    /**
     * Returns the speaker name
     * @return speakerName
     */
    public String getSpeakerName() {
        return speakerName;
    }

    /**
     * Sets the Speaker Name
     * @param speakerName
     */
    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    /**
     * Returns the event name
     * @return String eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the eventName
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Returns the event location
     * @return eventLocation
     */
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * Set eventLocation
     * @param eventLocation
     */
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * Returns the day the event is on
     * @return day
     */
    public int getDay() {
        return day;
    }


    /**
     * Sets what day to use
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Returns the start time of the event
     * @return startTime
     */
    public Timetable_Time_Keeper getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the event
     * @param startTime
     */
    public void setStartTime(Timetable_Time_Keeper startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the event
     * @return endTime
     */
    public Timetable_Time_Keeper getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the event
     * @param endTime
     */
    public void setEndTime(Timetable_Time_Keeper endTime) {
        this.endTime = endTime;
    }
}