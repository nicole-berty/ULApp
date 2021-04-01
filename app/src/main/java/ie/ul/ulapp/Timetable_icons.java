package ie.ul.ulapp;


import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class Timetable_icons implements Serializable {
    private ArrayList<TextView> view;
    private ArrayList<Timetable_Event> calendars;

    public Timetable_icons() {
        this.view = new ArrayList<TextView>();
        this.calendars = new ArrayList<Timetable_Event>();
    }

    public void addTextView(TextView v){
        view.add(v);
    }

    public void addIcon(Timetable_Event calendar){
        calendars.add(calendar);
    }

    public ArrayList<TextView> getView() {
        return view;
    }

    public ArrayList<Timetable_Event> getCalendars() {
        return calendars;
    }

}
