package ie.ul.ulapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;

public class   TimetableActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Button addBtn;
    private Button clearBtn;
    private Button loadBtn;

    public static Timetable_viewer timetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity);

        init();
    }

    private void init(){
        this.context = this;
        addBtn = findViewById(R.id.add_btn);
        clearBtn = findViewById(R.id.clear_btn);
        loadBtn = findViewById(R.id.load_btn);
        Calendar calendar = Calendar.getInstance();
        timetable = findViewById(R.id.current_day);
        timetable.setHeaderHighlight(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        initView();
    }

    private void initView(){
        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        timetable.setOnIconSelectEventListener(new Timetable_viewer.OnIconSelectedListener() {
            @Override
            public void OnIconSelected(int idx, ArrayList<Timetable_Event> calendars) {
                Intent i = new Intent(context, TimetableEdit.class);
                i.putExtra("mode",REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", calendars);
                startActivityForResult(i,REQUEST_EDIT);
            }
        });
        loadFromDatabase();
    }

    public void loadFromDatabase() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
        }
        final int[] numEvents = new int[1];
        DocumentReference docIdRef = db.collection("timetable").document("18246702");
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("****************START DOC: " + document.toString() + "*******END DOC******");
                        //for(int i = 0; i < document.toString().length() - 1; i++ ) {
                          //  String[] parts = document.toString().split("\"");
                          //  System.out.println(i);
                        String doc = document.toString();
                        int index = doc.indexOf("idx");
                        int count = 0;

                        String eventName = "";
                        String eventLocation = "";
                        String speakerName = "";
                        int day = -1;
                        int startHour = -1;
                        int startMin = -1;
                        int endHour = -1;
                        int endMin = -1;

                        //    while (index != -1) {
                          //      count++;
                            //    doc = doc.substring(index + 1);
                              //  index = doc.indexOf("idx");
                           // }
                            //System.out.println("Count:" + count);
                           // numEvents[0] = count;
                            String[] parts = doc.split("fields");
                          //  System.out.println("parts length:" + parts.length);
                            for(int i = 0; i < parts.length; i++) {
                                String[] values = parts[i].split("string_value:");
                                //   System.out.println("Values:" + values.length);
                                for (int j = 0; j < values.length; j++) {
                                    System.out.println("parts:" + parts[i]);
                                    if (j == 1) {
                                        //    System.out.println("Post string value: " + values[j]);
                                        String[] splitValues = values[j].split(":");
                                        for (int k = 0; k < splitValues.length; k++) {
                                            String temp = splitValues[k].replaceAll("\"", "");
                                            String[] valSplit = temp.split(",");

                                            for (int l = 0; l < valSplit.length; l++) {
                                                if (l == 0) {
                                                    String temp2 = valSplit[l].replace("}", "");
                                                    String temp3 = temp2.replace("\\", "");
                                                    System.out.println("k: " + k + " vals " + temp3);
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
                            }
                                JsonObject obj3 = new JsonObject();
                                obj3.addProperty("eventName", eventName);
                                obj3.addProperty("eventLocation", eventLocation);
                                obj3.addProperty("speakerName", speakerName);
                                obj3.addProperty("day", day);
                                JsonObject obj4 = new JsonObject();//startTime
                                obj4.addProperty("hour", startHour);
                                obj4.addProperty("minute", startMin);
                                obj3.add("startTime", obj4);
                                JsonObject obj5 = new JsonObject();//endTime
                                obj5.addProperty("hour", endHour);
                                obj5.addProperty("minute", endMin);
                                obj3.add("endTime", obj5);
                            Timetable_Save_Events.loadIcon(obj3);
                      //  }

                     //   for (Map.Entry<String, Object> o : document.entrySet()) {
                       //     String key = o.getKey();
                         //   Object value = o.getValue();
                       // }
                    }
                }
            }
        });
    }

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
                break;
            case R.id.load_btn:
                loadSavedData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == TimetableEdit.RESULT_OK_ADD) {
                    ArrayList<Timetable_Event> item = (ArrayList<Timetable_Event>) data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if (resultCode == TimetableEdit.RESULT_OK_EDIT) {
                    int idx = data.getIntExtra("idx", -1);
                    ArrayList<Timetable_Event> item = (ArrayList<Timetable_Event>) data.getSerializableExtra("schedules");
                    timetable.edit(idx, item);
                }
                /** Edit -> Delete */
                else if (resultCode == TimetableEdit.RESULT_OK_DELETE) {
                    int idx = data.getIntExtra("idx", -1);
                    timetable.remove(idx);
                }
                break;
        }
    }

    /** get json data from SharedPreferences and then restore the timetable */
    private void loadSavedData(){
        timetable.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        String savedData = mPref.getString("timetable_demo","");
        if(savedData == null && savedData.equals("")) return;
        timetable.load(savedData);
        Toast.makeText(this,"loaded!",Toast.LENGTH_SHORT).show();
    }
}
