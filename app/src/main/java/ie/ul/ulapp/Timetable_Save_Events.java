package ie.ul.ulapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Timetable_Save_Events {

    public static String saveicon(HashMap<Integer, Timetable_icons> event_icon){

        //Connect to DB and get the current user's email
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
        }

        JsonObject obj1 = new JsonObject();
        final JsonArray arr1 = new JsonArray();
        // This is empty when calendar is empty.
        int[] event_index = getSortedKeySet(event_icon);

        final JsonObject obj2 = new JsonObject();
      //  JsonObject obj3 = new JsonObject();
        System.out.println("Length of arr: " + event_index.length);

        for (final int index : event_index) {
            System.out.println(index);
            //  obj2.addProperty("idx", index);

            final CollectionReference db2 = db.collection("timetable");
            final int[] idx = new int[1];
            DocumentReference docIdRef = db.collection("timetable").document("18246702");
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if (document.get("index") != null) {
                                Log.d("TAG", "your field exist");
                                Map<String, Object> calendar_index = new HashMap<>();
                                idx[0] = Integer.parseInt(document.get("index").toString()) + 1;
                                calendar_index.put("index", idx[0]);
                                db2.document("18246702").update(calendar_index);
                                System.out.println(document.get("index"));
                                addToDatabase(idx[0]);
                            } else {
                                Log.d("TAG", "your field does not exist");
                                Map<String, Object> calendar_index = new HashMap<>();
                                calendar_index.put("index", 1);
                                idx[0] = 1;
                                db2.document("18246702").update(calendar_index);
                                addToDatabase(1);
                            }
                        } else {
                            Map<String, Object> calendar_index = new HashMap<>();
                            calendar_index.put("index", 1);
                            idx[0] = 1;
                            db.collection("timetable").document("18246702")
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
                            addToDatabase(1);
                        }
                    }
                }
            });
            obj2.addProperty("idx", index);
            arr1.add(obj2);
        }
        obj1.add("icon",arr1);
      //  System.out.println("Obj1: " + obj1.toString());
       // CollectionReference db2 = db.collection("timetable");
        //Create a HashMap with the user's email and their type as chosen from the spinner
       // Map<String, Object> calendar_activity = new HashMap<>();
        //calendar_activity.put(String.valueOf(idx), obj1.toString());

        return obj1.toString();
    }

    public static void addToDatabase(int index) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final CollectionReference db2 = db.collection("timetable");
        JsonArray arr2 = new JsonArray();//5
    //    ArrayList<Timetable_Event> calendars = Objects.requireNonNull(event_icon.get(index)).getCalendars();
        JsonObject obj2 = new JsonObject();
        obj2.addProperty("idx", index);
      //  for (Timetable_Event calendar : calendars) {
        Timetable_Event calendar = new Timetable_Event();
            JsonObject obj3 = new JsonObject();
            obj3.addProperty("eventName", calendar.eventName);
            obj3.addProperty("eventLocation", calendar.eventLocation);
            obj3.addProperty("speakerName", calendar.speakerName);
            obj3.addProperty("day", calendar.getDay());
            JsonObject obj4 = new JsonObject();//startTime
            obj4.addProperty("hour", calendar.getStartTime().getHour());
            obj4.addProperty("minute", calendar.getStartTime().getMinute());
            obj3.add("startTime", obj4);
            JsonObject obj5 = new JsonObject();//endTime
            obj5.addProperty("hour", calendar.getEndTime().getHour());
            obj5.addProperty("minute", calendar.getEndTime().getMinute());
            obj3.add("endTime", obj5);
            arr2.add(obj3);


            System.out.println("Obj3: " + obj3.toString());
       //     System.out.println("Obj2: " + obj2.toString());
            System.out.println("Obj5: " + obj5.toString());

            Map<String, Object> calendar_activity = new HashMap<>();
            calendar_activity.put(obj2.toString(), obj3.toString());
            db2.document("18246702").update(calendar_activity);

       // }
        //    obj2.add("schedule", arr2);
        //     arr1.add(obj2);

    }

    public static HashMap<Integer, Timetable_icons> loadIcon(String json){
        HashMap<Integer, Timetable_icons> icon = new HashMap<Integer, Timetable_icons>();
        final JsonParser parser = new JsonParser();
        JsonObject obj1 = (JsonObject)parser.parse(json);
        System.out.println(json);
        //Connect to Firebase FireStore
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        //Access the userTypes collection and search for the document with the user's email
//        DocumentReference docRef = db.collection("timetable").document("18246702");
//
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    //If Document with user's email exists, go to the home activity
//                    if (document.exists()) {
//                        JsonObject obj1 = (JsonObject)parser.parse(document.get("18246702"));
//                    } else {
//                        Intent intent = new Intent(LoginActivity.this, UserType.class);
//                        startActivity(intent);
//                    }
//                    finish();
//                } else {
//                    Log.w("tag", "Error getting info", task.getException());
//                }
//            }
//        });
        JsonArray arr1 = obj1.getAsJsonArray("icon");
        for(int i = 0 ; i < arr1.size(); i++){
            Timetable_icons icon1 = new Timetable_icons();
            JsonObject obj2 = (JsonObject)arr1.get(i);
            int idx = obj2.get("idx").getAsInt();
            JsonArray arr2 = (JsonArray)obj2.get("schedule");
            for(int k = 0 ; k < arr2.size(); k++){
                Timetable_Event schedule = new Timetable_Event();
                JsonObject obj3 = (JsonObject)arr2.get(k);
                schedule.setEventName(obj3.get("eventName").getAsString());
                schedule.setEventLocation(obj3.get("eventLocation").getAsString());
                schedule.setSpeakerName(obj3.get("speakerName").getAsString());
                schedule.setDay(obj3.get("day").getAsInt());
                Timetable_Time_Keeper startTime = new Timetable_Time_Keeper();
                JsonObject obj4 = (JsonObject)obj3.get("startTime");
                startTime.setHour(obj4.get("hour").getAsInt());
                startTime.setMinute(obj4.get("minute").getAsInt());
                Timetable_Time_Keeper endTime = new Timetable_Time_Keeper();
                JsonObject obj5 = (JsonObject)obj3.get("endTime");
                endTime.setHour(obj5.get("hour").getAsInt());
                endTime.setMinute(obj5.get("minute").getAsInt());
                schedule.setStartTime(startTime);
                schedule.setEndTime(endTime);
                icon1.addIcon(schedule);
            }
            icon.put(idx,icon1);
        }
        System.out.println(icon);
        return icon;
    }


    static private int[] getSortedKeySet(HashMap<Integer, Timetable_icons> icon){
        int[] orders = new int[icon.size()];
        int i = 0;
        for(int key : icon.keySet()){
            orders[i++] = key;
        }
        Arrays.sort(orders);
        return orders;
    }
}
