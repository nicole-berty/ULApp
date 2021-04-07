package ulapp.myUL.timetable;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ulapp.myUL.R;
import ulapp.myUL.Success;

import static ulapp.myUL.timetable.TimetableActivity.timetable;

public class TimetableEdit extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_OK_ADD = 1;

    private Context context;

    private Button deleteBtn;
    private Button submitBtn;
    private EditText eventName;
    private EditText eventLocation;
    private EditText eventSpeaker;
    private Spinner daySpinner;
    private TextView startTime;
    private TextView endTime;

    //request mode
    private int mode;

    private TimetableEvent event;
    private int editIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_edit_event);
        init();
    }


    /**
     * initializer for getting information about buttons, event details etc.
     */
    private void init(){
        this.context = this;
        deleteBtn = findViewById(R.id.delete_btn);
        submitBtn = findViewById(R.id.submit_btn);
        eventName = findViewById(R.id.event_name);
        eventLocation = findViewById(R.id.event_location);
        eventSpeaker = findViewById(R.id.event_speaker);
        daySpinner = findViewById(R.id.day_spinner);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);

        //set the default time
        event = new TimetableEvent();
        event.setStartTime(new TimetableTimeKeeper(10,0));
        event.setEndTime(new TimetableTimeKeeper(12,0));

        checkMode();
        initView();
    }

    /** check whether the mode is ADD or EDIT */
    private void checkMode(){
        Intent i = getIntent();
        mode = i.getIntExtra("mode",TimetableActivity.REQUEST_ADD);

        if(mode == TimetableActivity.REQUEST_EDIT){
            loadScheduleData();
            deleteBtn.setVisibility(View.VISIBLE);
        }
    }
    private void initView(){
        submitBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                event.setDay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,event.getStartTime().getHour(), event.getStartTime().getMinute(), true);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hrstr = hourOfDay < 10 ? //If hour is lower than 10, add a zero on the left
                            '0' + String.valueOf(hourOfDay) : String.valueOf(hourOfDay);
                    String minstr = minute < 10 ? //If minute is lower than 10, add a zero on the left
                            '0' + String.valueOf(minute) : String.valueOf(minute);
                    startTime.setText(hrstr + ":" + minstr);
                    event.getStartTime().setHour(hourOfDay);
                    event.getStartTime().setMinute(minute);


                }
            };
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,event.getEndTime().getHour(), event.getEndTime().getMinute(), true);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hrstr = hourOfDay < 10 ? //If hour is lower than 10, add a zero on the left
                            '0' + String.valueOf(hourOfDay) : String.valueOf(hourOfDay);
                    String minstr = minute < 10 ? //If minute is lower than 10, add a zero on the left
                            '0' + String.valueOf(minute) : String.valueOf(minute);
                    endTime.setText(hrstr + ":" + minstr);
                    event.getEndTime().setHour(hourOfDay);
                    event.getEndTime().setMinute(minute);
                }
            };
        });
    }

    /**
     * Listener for when buttons are clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        ArrayList<TimetableEvent> events = new ArrayList<>();
        switch (v.getId()){
            /**
             * Submit button to submit new events or edited events.
             */
            case R.id.submit_btn:
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                if(mode == TimetableActivity.REQUEST_ADD){
                    Date start = null;
                    Date end = null;
                    try {
                        start = parser.parse( event.getStartTime().getHour() + ":" + event.getStartTime().getMinute());
                        end = parser.parse( event.getEndTime().getHour() + ":" + event.getEndTime().getMinute());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(start.after(end)) {
                        Context context = getApplicationContext();
                        CharSequence text = "The end time must be after the start time, try again!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        inputDataProcessing();
                        Intent i = new Intent(this, Success.class);
                        // add more calendars
                        events.add(event);
                        i.putExtra("schedules", events);
                        setResult(RESULT_OK_ADD, i);
                        ArrayList<TimetableEvent> item = (ArrayList<TimetableEvent>) i.getSerializableExtra("schedules");
                        timetable.add(item);
                        TimetableSaveEvents.saveEvent(TimetableDisplay.event_icons, true, -1);
                        i.putExtra("ACTION", "ADD");
                        startActivity(i);
                        finish();
                    }
                }
                else if(mode == TimetableActivity.REQUEST_EDIT){
                    Date start = null;
                    try {
                        start = parser.parse( event.getStartTime().getHour() + ":" + event.getStartTime().getMinute());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date end = null;
                    try {
                        end = parser.parse( event.getEndTime().getHour() + ":" + event.getEndTime().getMinute());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(start.after(end)) {
                        Context context = getApplicationContext();
                        CharSequence text = "The end time must be after the start time, try again!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        inputDataProcessing();
                        Intent i = new Intent(this, Success.class);
                        i.putExtra("idx", editIdx);
                        events.add(event);
                        //System.out.println("events length " + events.size());
                        i.putExtra("schedules", events);
                        ArrayList<TimetableEvent> item = (ArrayList<TimetableEvent>) i.getSerializableExtra("schedules");
                        for (TimetableEvent it : item) {
                            System.out.println("data in edit: " + it.getEventName());
                        }
                        timetable.add(item);
                        TimetableSaveEvents.saveEvent(TimetableDisplay.event_icons, false, editIdx);
                        i.putExtra("ACTION", "EDIT");
                        startActivity(i);
                    }
                }
                break;
            /**
             * Delete Button for deleting one event
             */
            case R.id.delete_btn:
                Intent i = new Intent(this, Success.class);
                i.putExtra("idx",editIdx);
                timetable.remove(editIdx);
                i.putExtra("ACTION", "DELETE");
                startActivity(i);
                finish();
                break;
        }
    }


    /**
     * Loading events from the database.
     */
    private void loadScheduleData(){
        Intent i = getIntent();
        editIdx = i.getIntExtra("idx",-1);
        ArrayList<TimetableEvent> events = (ArrayList<TimetableEvent>)i.getSerializableExtra("schedules");
        event = events.get(0);
        eventName.setText(event.getEventName());
        eventLocation.setText(event.getEventLocation());
        eventSpeaker.setText(event.getSpeakerName());
        daySpinner.setSelection(event.getDay());

        String startHr = event.getStartTime().getHour() < 10 ? //If hour is lower than 10, add a zero on the left
                '0' + String.valueOf(event.getStartTime().getHour()) : String.valueOf(event.getStartTime().getHour());
        String startMin =  event.getStartTime().getMinute() < 10 ? //If minute is lower than 10, add a zero on the left
                '0' + String.valueOf( event.getStartTime().getMinute()) : String.valueOf( event.getStartTime().getMinute());

        startTime.setText(startHr + ":" + startMin);

        String endHr = event.getEndTime().getHour() < 10 ? //If hour is lower than 10, add a zero on the left
                '0' + String.valueOf(event.getEndTime().getHour()) : String.valueOf(event.getEndTime().getHour());
        String endMin =  event.getEndTime().getMinute() < 10 ? //If minute is lower than 10, add a zero on the left
                '0' + String.valueOf( event.getEndTime().getMinute()) : String.valueOf( event.getEndTime().getMinute());
        endTime.setText(endHr + ":" + endMin);
    }

    /**
     * Listeners to take inputted information for events. 
     */
    private void inputDataProcessing(){
        event.setEventName(eventName.getText().toString());
        event.setEventLocation(eventLocation.getText().toString());
        event.setSpeakerName(eventSpeaker.getText().toString());
    }
}
