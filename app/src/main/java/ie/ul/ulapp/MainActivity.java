package ie.ul.ulapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.api.core.ApiFuture;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference timetable = db.collection("timetable_1");
    boolean user_timetable_exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClickSignIn(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (requestCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("Sign in successful! \n + " +
                        "name = " + user.getDisplayName() + "\n" +
                        "email = " + user.getEmail() + "\n" +
                        "id = " + user.getUid());
            } else {
                if (response == null) {
                    System.out.println("Sign in cancelled");
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    System.out.println("No Internet Connection");
                    return;
                }
            }
        }
    }

// migs
    public void createEvent(View view, String name, String location, String color, boolean repeat, String date_time_start, String date_time_end) {
        // id_num needs to come from the database
        String id_num = "12345678";
        String[] id_num_dataBase = {"18219365", "12345678", "12345679"};

        // Checking if database exists
        for (String i : id_num_dataBase) {
            // if id_num is already created then add new event
            if (i.equals(id_num)) {
                CollectionReference new_timetable = db.collection("timetable_1");
                Map<String, Object> event = new HashMap<>();
                event.put("name", name);
                event.put("location", location);
                event.put("color", color);
                event.put("repeat", repeat);
                event.put("date_time_start", date_time_start);
                event.put("date_time_end", date_time_end);
                timetable.document(id_num).set(event);
            }
        }
    }

    public void editEvent(View view, String name, String location, String color, boolean repeat, String date_time_start, String date_time_end){
        // id_num needs to come from the database
        String id_num = "12345678";
        String[] id_num_dataBase = {"18219365", "12345678", "12345679"};

        // Checking if database exists
        for (String i : id_num_dataBase) {
            // if id_num is already created then edit the event
            if (i.equals(id_num)) {
                CollectionReference new_timetable = db.collection("timetable_1");
                Map<String, Object> event = new HashMap<>();
                event.put("name", name);
                event.put("location", location);
                event.put("color", color);
                event.put("repeat", repeat);
                event.put("date_time_start", date_time_start);
                event.put("date_time_end", date_time_end);
                timetable.document(id_num).set(event);
            }
        }
    }

//    public void deleteEvent(CollectionReference collection, int batchSize){
//        try
//        {
//            ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
//            int deleted = 0;
//
//            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//            for(QueryDocumentSnapshot document : documents) {
//                document.getReference().delete();
//                ++deleted;
//            }
//            if(deleted >= batchSize) {
//                deleteEvent(collection, batchSize);
//            }
//        } catch (Exception e){
//            System.out.println("Error in deleting event : " + e.getMessage());
//        }
//    }

    public void onClickGetData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("drinks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId());
                                System.out.println(document.getData());
                            }
                        } else {
                            Log.w("tag", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}