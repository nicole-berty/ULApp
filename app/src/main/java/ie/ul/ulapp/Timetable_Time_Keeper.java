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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
