package ulapp.myUL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ulapp.myUL.guides.GuidesActivity;

public class InfoActivity extends ActionBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Set the names of the ImageView in the activity

        ImageView ULImage = findViewById(R.id.InfoPageImage);
        ImageView contactInfoImage = findViewById(R.id.contactInfoImage);
        ImageView parkingImage = findViewById(R.id.ParkingInfoImage);
        ULImage.setImageResource(R.drawable.ul_logo);
        contactInfoImage.setImageResource(R.drawable.contact);
        parkingImage.setImageResource(R.drawable.parking);

        //Set the intent for when each ImageView is clicked
        contactInfoImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intent = new Intent(InfoActivity.this, ContactInfo.class);
            startActivity(intent);
            }
        });

        parkingImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, ParkingInfo.class);
                startActivity(intent);
            }
        });

        //Call function to check if user is a student or not
        checkIfStudent();
    }

    /**
     * Add the guides ImageView button to the page. This method is only called if the user is a student registered in the userTypes collection of the database
     */
    public void addGuides() {
        ImageView guidesImage = findViewById(R.id.GuidesInfoImage);
        //Set ImageView picture
        guidesImage.setImageResource(R.drawable.guides);

        //User will be taken to the GuidesActivity when clicking the guides image
        guidesImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, GuidesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method which queries the Firebase Firestore database if the current user is not anonymous to see if they are a student in the user types collection
     */
    public void checkIfStudent() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        //User is logged in and registered on the database
        if (currentUser != null && !currentUser.isAnonymous()) {
            email = currentUser.getEmail();
            if (email != null || !email.equals("")) {
                //Connect to Firebase Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                //Get a reference to the userTypes collection in Firestore
                DocumentReference docRef = db.collection("userTypes").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            //The document exists which means the user's email is in the userTypes collection
                            if (document.exists()) {
                                //The document data is returned as an object map. Convert to string, split it on the = and remove the }
                                String[] values = document.getData().toString().split("=");
                                String userType = values[1].replace("}", "");
                                //If the user is a student, add the guides button to the info page
                                if (userType.equals("student")) {
                                    addGuides();
                                }
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }
        }
    }
}