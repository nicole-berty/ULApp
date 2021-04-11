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

        ImageView ULImage = findViewById(R.id.InfoPageImage);
        ImageView contactInfoImage = findViewById(R.id.contactInfoImage);

        ImageView parkingImage = findViewById(R.id.ParkingInfoImage);

        ULImage.setImageResource(R.drawable.ul_logo);
        contactInfoImage.setImageResource(R.drawable.contact);
        contactInfoImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intent = new Intent(InfoActivity.this, ContactInfo.class);
            startActivity(intent);
            }
        });

        parkingImage.setImageResource(R.drawable.parking);
        parkingImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, ParkingInfo.class);
                startActivity(intent);
            }
        });
        checkDatabase();
    }

    public void addGuides() {
        ImageView guidesImage = findViewById(R.id.GuidesInfoImage);
        guidesImage.setImageResource(R.drawable.guides);
        guidesImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, GuidesActivity.class);
                startActivity(intent);
            }
        });
    }
    public void checkDatabase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        //User is logged in
        if (currentUser != null && !currentUser.isAnonymous()) {
            email = currentUser.getEmail();
            if (email != null || !email.equals("")) {
                //Connect to Firebase FireStore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("userTypes").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                String[] values = document.getData().toString().split("=");
                                String userType = values[1].replace("}", "");
                                Log.d("TAG", "USER TYPE data: " + userType);
                                if (userType.equals("student")) {
                                    Log.d("tag", "am student");
                                    addGuides();
                                } else {
                                    Log.d("tag", "am not");
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