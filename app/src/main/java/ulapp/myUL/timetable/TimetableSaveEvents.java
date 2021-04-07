package ulapp.myUL.timetable;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TimetableSaveEvents {

    public static void saveEvent(final HashMap<Integer, TimetableIcons> event_icon, final boolean add, final int editIdx){

        //Connect to DB and get the current user's email
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
            DocumentReference docIdRef = db.collection("timetable").document(email);
            final String finalEmail = email;
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    final int[] event_index = getSortedKeySet(event_icon);
                    if (task.isSuccessful()) {
                        final DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if (document.get("index") != null) {
                                addToDatabase(event_icon, event_index, add, editIdx);

                            } else {
                                Map<String, Object> calendar_index = new HashMap<>();
                                calendar_index.put("index", 0);
                                db.collection("timetable").document(finalEmail)
                                        .set(calendar_index).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "DocumentSnapshot successfully written!");
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });
                                addToDatabase(event_icon, event_index, add, -1);

                            }
                        } else {
                            Map<String, Object> calendar_index = new HashMap<>();
                            calendar_index.put("index", 0);
                            db.collection("timetable").document(finalEmail)
                                    .set(calendar_index).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                    addToDatabase(event_icon, event_index, add, -1);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error writing document", e);
                                        }
                                    });

                        }
                    }
                }
            });
        }
    }

    public static void addToDatabase(final HashMap<Integer, TimetableIcons> final_event_icon, final int[] icon_index, final boolean add, final int editIdx ) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
            final DocumentReference docIdRef = db.collection("timetable").document(email);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (int i : icon_index) {
                            ArrayList<TimetableEvent> calendars = Objects.requireNonNull(final_event_icon.get(i)).getCalendars();
                            int index;
                            DocumentSnapshot document = task.getResult();

                            if(add) {
                                index = Integer.parseInt(document.get("index").toString()) + 1;
                                Map<String, Object> calendar_index = new HashMap<>();
                                calendar_index.put("index", index);
                                docIdRef.update(calendar_index);
                            } else {
                                index = editIdx;
                            }
                            JsonObject eventIndex = new JsonObject();
                            eventIndex.addProperty("idx", index);

                            for (TimetableEvent calendar : calendars) {
                                JsonObject eventDetails = new JsonObject();
                                eventDetails.addProperty("eventName", calendar.eventName);
                                eventDetails.addProperty("eventLocation", calendar.eventLocation);
                                eventDetails.addProperty("speakerName", calendar.speakerName);
                                eventDetails.addProperty("day", calendar.getDay());
                                JsonObject startTime = new JsonObject();//startTime
                                startTime.addProperty("hour", calendar.getStartTime().getHour());
                                startTime.addProperty("minute", calendar.getStartTime().getMinute());
                                eventDetails.add("startTime", startTime);
                                JsonObject endTime = new JsonObject();//endTime
                                endTime.addProperty("hour", calendar.getEndTime().getHour());
                                endTime.addProperty("minute", calendar.getEndTime().getMinute());
                                eventDetails.add("endTime", endTime);

                                Map<String, Object> calendar_activity = new HashMap<>();
                                calendar_activity.put(eventIndex.toString(), eventDetails.toString());
                                docIdRef.update(calendar_activity);
                            }
                        }
                    }
                }
            });
        }
    }

    public static HashMap<Integer, TimetableIcons> loadEvent(String json){
        HashMap<Integer, TimetableIcons> icon = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonObject event = (JsonObject)parser.parse(json);
        JsonArray eventIDDetails = event.getAsJsonArray("icon");
        for(int i = 0 ; i < eventIDDetails.size(); i++){
            TimetableIcons icon1 = new TimetableIcons();
            JsonObject eventIndex = (JsonObject)eventIDDetails.get(i);
            int idx = eventIndex.get("idx").getAsInt();
            JsonArray eventDetails = (JsonArray)eventIndex.get("schedule");
            for(int k = 0 ; k < eventDetails.size(); k++){
                TimetableEvent event1 = new TimetableEvent();
                JsonObject eventDetails1 = (JsonObject)eventDetails.get(k);
                event1.setEventName(eventDetails1.get("eventName").getAsString());
                event1.setEventLocation(eventDetails1.get("eventLocation").getAsString());
                event1.setSpeakerName(eventDetails1.get("speakerName").getAsString());
                event1.setDay(eventDetails1.get("day").getAsInt());
                TimetableTimeKeeper startTime = new TimetableTimeKeeper();
                JsonObject eventStart = (JsonObject)eventDetails1.get("startTime");
                startTime.setHour(eventStart.get("hour").getAsInt());
                startTime.setMinute(eventStart.get("minute").getAsInt());
                TimetableTimeKeeper endTime = new TimetableTimeKeeper();
                JsonObject eventEnd = (JsonObject)eventDetails1.get("endTime");
                endTime.setHour(eventEnd.get("hour").getAsInt());
                endTime.setMinute(eventEnd.get("minute").getAsInt());
                event1.setStartTime(startTime);
                event1.setEndTime(endTime);
                icon1.addIcon(event1);
            }
            icon.put(idx,icon1);
        }
        return icon;
    }


    static private int[] getSortedKeySet(HashMap<Integer, TimetableIcons> icon){
        int[] orders = new int[icon.size()];
        int i = 0;
        for(int key : icon.keySet()){
            orders[i++] = key;
        }
        Arrays.sort(orders);
        return orders;
    }
}
