package ie.ul.timetable_viewer;

import java.io.Serializable;

public class time_keep implements Serializable {
    private int hour = 0;
    private int minute = 0;

    public time_keep(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public time_keep() { }

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
