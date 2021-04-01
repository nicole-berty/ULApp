package ie.ul.ulapp;

import java.io.Serializable;

public class Timetable_Time_Keeper implements Serializable {
    private int hour = 0;
    private int minute = 0;

    public Timetable_Time_Keeper(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Timetable_Time_Keeper() { }

    /**
     * Returns the event hour
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Sets the event hour
     * @param hour
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Returns the event minutes
     * @return minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets the event minute
     * @param minute
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }
}
