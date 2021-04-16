package ulapp.myUL.timetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;

import ulapp.myUL.ActionBar;
import ulapp.myUL.LoginActivity;
import ulapp.myUL.R;
import ulapp.myUL.Success;

/**
 * Main Activity class for timetable
 */

public class TimetableActivity extends ActionBar implements View.OnClickListener {
    private Context context;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Button addBtn;
    private Button clearBtn;

    public static TimetableDisplay timetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
          Check for Broadcast receiver which is created on logging out.
          This will prevent a user from going back to a page that should be only seen by logged in users.
         */

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //Logout has occurred, start login activity and finish this one
                Intent intent2 = new Intent(TimetableActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        }, intentFilter);

        setContentView(R.layout.timetable_activity);
        init();
    }

    /**
     * Timetable activity initializer
     */
    void init(){
        this.context = this;
        addBtn = findViewById(R.id.add_btn);
        clearBtn = findViewById(R.id.clear_btn);
        Calendar calendar = Calendar.getInstance();
        timetable = findViewById(R.id.current_day);
        timetable.setHeaderHighlight(calendar.get(Calendar.DAY_OF_WEEK));
        initView();
    }

    /**
     * Timetable viewer initializer
     */
    void initView(){
        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);

        timetable.setOnIconSelectEventListener(new TimetableDisplay.OnIconSelectedListener() {
            @Override
            public void OnIconSelected(int idx, ArrayList<TimetableEvent> calendars) {
                Intent i = new Intent(context, TimetableEdit.class);
                i.putExtra("mode",REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", calendars);
                startActivityForResult(i,REQUEST_EDIT);
            }
        });
        loadFromDatabase();
    }

    /**
     * Loads events from the database from the user's document in the timetable collection to display them on the app.
     */
    public static void loadFromDatabase() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
            DocumentReference docIdRef = db.collection("timetable").document(email);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String doc = document.toString();
                            int index = -1;

                            String eventName = "";
                            String eventLocation = "";
                            String speakerName = "";
                            int day = -1;
                            int startHour = -1;
                            int startMin = -1;
                            int endHour = -1;
                            int endMin = -1;

                            //doc returns all the user's events. Need to split these up and parse the information in the correct format
                            String[] parts = doc.split("fields");
                            for(int i = 1; i < parts.length; i++) {
                                String[] values = parts[i].split("string_value:");
                                for (int j = 0; j < values.length; j++) {
                                    if(j == 0) {
                                        String[] splitValues = values[j].split("value");
                                        for (String splitValue : splitValues) {
                                            String temp = splitValue.replace("\\", "");
                                            String[] valSplit = temp.split(":");
                                            for (int l = 0; l < valSplit.length; l++) {
                                                String temp2 = valSplit[l].replaceAll("\"", "");
                                                String temp3 = temp2.replace("}", "");
                                                if (l == 2) {
                                                    index = Integer.parseInt(temp3.trim());
                                                }
                                            }
                                        }
                                    }
                                    if (j == 1) {
                                        String[] splitValues = values[j].split(":");
                                        for (int k = 0; k < splitValues.length; k++) {
                                            String temp = splitValues[k].replaceAll("\"", "");
                                            String[] valSplit = temp.split(",");
                                            for (int l = 0; l < valSplit.length; l++) {
                                                if (l == 0) {
                                                    String temp2 = valSplit[l].replace("}", "");
                                                    String temp3 = temp2.replace("\\", "");
                                                    if (k == 1) {
                                                        eventName = temp3;
                                                    } else if (k == 2) {
                                                        eventLocation = temp3;
                                                    } else if (k == 3) {
                                                        speakerName = temp3;
                                                    } else if (k == 4) {
                                                        day = Integer.parseInt(temp3);
                                                    } else if (k == 6) {
                                                        startHour = Integer.parseInt(temp3);
                                                    } else if (k == 7) {
                                                        startMin = Integer.parseInt(temp3);
                                                    } else if (k == 9) {
                                                        endHour = Integer.parseInt(temp3);
                                                    } else if (k == 10) {
                                                        endMin = Integer.parseInt(temp3.trim());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                JsonObject completeEventJSON = new JsonObject();
                                JsonObject eventIndex = new JsonObject();
                                JsonObject eventDetails = new JsonObject();
                                eventIndex.addProperty("idx", index);
                                JsonArray arr1 = new JsonArray();
                                JsonArray arr2 = new JsonArray();

                                eventDetails.addProperty("eventName", eventName);
                                eventDetails.addProperty("eventLocation", eventLocation);
                                eventDetails.addProperty("speakerName", speakerName);
                                eventDetails.addProperty("day", day);
                                JsonObject startTime = new JsonObject();//startTime
                                startTime.addProperty("hour", startHour);
                                startTime.addProperty("minute", startMin);
                                eventDetails.add("startTime", startTime);
                                JsonObject endTime = new JsonObject();//endTime
                                endTime.addProperty("hour", endHour);
                                endTime.addProperty("minute", endMin);
                                eventDetails.add("endTime", endTime);
                                arr2.add(eventDetails);
                                eventIndex.add("schedule", arr2);
                                arr1.add(eventIndex);
                                completeEventJSON.add("icon", arr1);
                                timetable.load(completeEventJSON.toString());
                            }
                        } else {
                            System.out.println("Doc does not exist");
                        }
                    }
                }
            });
        }
    }

    /**
     * Sets the activity after one of the buttons are clicked.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                Intent i = new Intent(this,TimetableEdit.class);
                i.putExtra("mode",REQUEST_ADD);
                startActivityForResult(i,REQUEST_ADD);
                break;
            case R.id.clear_btn:
                timetable.removeAll();
                Intent intent = new Intent(this, Success.class);
                intent.putExtra("ACTION", "CLEAR");
                startActivity(intent);
                break;
        }
    }
}
