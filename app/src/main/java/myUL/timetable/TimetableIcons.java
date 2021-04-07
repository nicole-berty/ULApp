package myUL.timetable;


import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class TimetableIcons implements Serializable {
    private ArrayList<TextView> view;
    private ArrayList<TimetableEvent> calendars;

    public TimetableIcons() {
        this.view = new ArrayList<TextView>();
        this.calendars = new ArrayList<TimetableEvent>();
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
    public void addIcon(TimetableEvent calendar){
        calendars.add(calendar);
    }

    public void removeIcon(TimetableEvent calendar){
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
    public ArrayList<TimetableEvent> getCalendars() {
        return calendars;
    }

}
