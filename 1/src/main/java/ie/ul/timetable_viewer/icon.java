package ie.ul.timetable_viewer;


import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class icon implements Serializable {
    private ArrayList<TextView> view;
    private ArrayList<calendar> schedules;

    public icon() {
        this.view = new ArrayList<TextView>();
        this.schedules = new ArrayList<calendar>();
    }

    public void addTextView(TextView v){
        view.add(v);
    }

    public void addSchedule(calendar schedule){
        schedules.add(schedule);
    }

    public ArrayList<TextView> getView() {
        return view;
    }

    public ArrayList<calendar> getSchedules() {
        return schedules;
    }
}
