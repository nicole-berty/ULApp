package ulapp.myUL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserType extends AppCompatActivity {

    //Initialise class variables
    private static final String TAG = "DocSnippets";
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check for Broadcast receiver which is created on logging out. This will prevent a user from going back to a page that should be only seen by logged in users.
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //Logout has occurred, start login activity and finish this one
                Intent intent2 = new Intent(UserType.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        }, intentFilter);

        setContentView(R.layout.activity_user_type);

        final ImageView imageView = findViewById(R.id.imageView);

        //Create spinner which will have the user types on it
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.user_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Set what happens when each spinner item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //Change the image that is shown to the user based on the item selected in the spinner
                switch(position) {
                    case 0:
                        imageView.setImageResource(R.drawable.student);
                        type = "student";
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.lecturer);
                        type = "lecturer";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Takes the user to the home page of the app after pressing the button
     * @param v UI element view
     */
    public void goToHome(View v) {
        //Connect to DB and get the current user's email
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        if (user != null) {
            email = user.getEmail();
            //Create a HashMap with the user's email and their type as chosen from the spinner
            Map<String, Object> userType = new HashMap<>();
            userType.put(email, type);

            //In the userTypes collection on the database, on the document with the user's email, set the HashMap as the field
            db.collection("userTypes").document(email)
                    .set(userType).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

            //Go to the home activity and call finish() so the user can't go back to this page - only users without user type should be here
            Intent intent = new Intent(UserType.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}