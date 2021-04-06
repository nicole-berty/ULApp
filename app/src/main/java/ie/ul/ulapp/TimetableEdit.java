package ie.ul.ulapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static ie.ul.ulapp.TimetableActivity.timetable;

public class TimetableEdit extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_EDIT = 2;
    public static final int RESULT_OK_DELETE = 3;

    private Context context;

    private Button deleteBtn;
    private Button submitBtn;
    private EditText eventName;
    private EditText eventLocation;
    private EditText eventSpeaker;
    private Spinner daySpinner;
    private TextView startTv;
    private TextView endTv;

    //request mode
    private int mode;

    private Timetable_Event event;
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
        startTv = findViewById(R.id.start_time);
        endTv = findViewById(R.id.end_time);

        //set the default time
        event = new Timetable_Event();
        event.setStartTime(new Timetable_Time_Keeper(10,0));
        event.setEndTime(new Timetable_Time_Keeper(13,30));

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
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,event.getStartTime().getHour(), event.getStartTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    startTv.setText(hourOfDay + ":" + minute);
                    event.getStartTime().setHour(hourOfDay);
                    event.getStartTime().setMinute(minute);
                }
            };
        });
        endTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context,listener,event.getEndTime().getHour(), event.getEndTime().getMinute(), false);
                dialog.show();
            }

            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endTv.setText(hourOfDay + ":" + minute);
                    event.getEndTime().setHour(hourOfDay);
                    event.getEndTime().setMinute(minute);
                }
            };
        });
    }

    @Override
    public void onClick(View v) {
        ArrayList<Timetable_Event> events = new ArrayList<Timetable_Event>();
        switch (v.getId()){
            case R.id.submit_btn:
                if(mode == TimetableActivity.REQUEST_ADD){
                    inputDataProcessing();
                    Intent i = new Intent(this, Success.class);
                    // add more calendars
                    events.add(event);
                    i.putExtra("schedules",events);
                    setResult(RESULT_OK_ADD,i);
                    ArrayList<Timetable_Event> item = (ArrayList<Timetable_Event>)i.getSerializableExtra("schedules");
                    timetable.add(item, false);
                    // call to save the icon
                    System.out.println("in submit btn");
                    //Gonna pass an index to saveIcon to represent add new event or edit current event. -1 = add new, index = edit current
                    Timetable_Save_Events.saveicon(Timetable_viewer.event_icons, true, -1);
                    i.putExtra("ACTION", "ADD");
                    startActivity(i);
                    finish();
                }
                else if(mode == TimetableActivity.REQUEST_EDIT){
                    inputDataProcessing();
                    Intent i = new Intent(this, Success.class);
                 //   events.add(event);
                    i.putExtra("idx",editIdx);
                    events.add(event);
                    System.out.println("events length " + events.size());
                    i.putExtra("schedules",events);
                    ArrayList<Timetable_Event> item = (ArrayList<Timetable_Event>)i.getSerializableExtra("schedules");
                    for(Timetable_Event it : item) {
                        System.out.println("data in edit: " + it.getEventName());
                    }
                    timetable.add(item, true);
                    //     ArrayList<Timetable_Event> item = (ArrayList<Timetable_Event>)i.getSerializableExtra("schedules");
               //     timetable.remove(editIdx);

                    // call to save the icon
                    System.out.println("in edit btn");

                    //Gonna pass an index to saveIcon to represent add new event or edit current event. -1 = add new, index = edit current
                    Timetable_Save_Events.saveicon(Timetable_viewer.event_icons, false, editIdx);

//everything works, YOU`RE WELCOME

                //    setResult(RESULT_OK_EDIT,i);
                    i.putExtra("ACTION", "EDIT");
                    startActivity(i);
               //     finish();
                }
                break;
            case R.id.delete_btn:
                Intent i = new Intent(this, Success.class);
                i.putExtra("idx",editIdx);
                timetable.remove(editIdx);
            //     setResult(RESULT_OK_DELETE, i);
                i.putExtra("ACTION", "DELETE");
                startActivity(i);
                finish();
                break;
        }
    }

    private void loadScheduleData(){
        Intent i = getIntent();
        editIdx = i.getIntExtra("idx",-1);
        ArrayList<Timetable_Event> events = (ArrayList<Timetable_Event>)i.getSerializableExtra("schedules");
        event = events.get(0);
        eventName.setText(event.getEventName());
        eventLocation.setText(event.getEventLocation());
        eventSpeaker.setText(event.getSpeakerName());
        daySpinner.setSelection(event.getDay());
    }

    private void inputDataProcessing(){
        event.setEventName(eventName.getText().toString());
        event.setEventLocation(eventLocation.getText().toString());
        event.setSpeakerName(eventSpeaker.getText().toString());
        System.out.println("name " + eventName.getText().toString());
        System.out.println("event name " + event.getEventName());
    }
}
