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

    /**
     * Adds a text view
     * @param v
     */
    public void addTextView(TextView v){
        view.add(v);
    }

    /**
     * Adds new Icon
     * @param calendar
     */
    public void addIcon(Timetable_Event calendar){
        calendars.add(calendar);
    }

    public void removeIcon(Timetable_Event calendar){
        calendars.remove(calendar);
    }

    /**
     * Returns an ArrayList of text views
     * @return view
     */
    public ArrayList<TextView> getView() {
        return view;
    }

    /**
     * Returns an ArrayList of calendars
     * @return calendars
     */
    public ArrayList<Timetable_Event> getCalendars() {
        return calendars;
    }

}
