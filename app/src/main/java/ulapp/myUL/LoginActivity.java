package ulapp.myUL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Gets instance of FirebaseAuth for connecting to the database
        mAuth = FirebaseAuth.getInstance();
        //Get the currently logged in user
        FirebaseUser user = mAuth.getCurrentUser();
        //If the user is not null, i.e. is logged in
        if(user != null) {
            if (user.isAnonymous()) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            System.out.println("user is null");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        checkDatabase();
    }

    /**
     * Handles click events for buttons
     * @param View UI element for event handling
     */
    public void OnClick (View View) {
        //get id of UI element that was clicked
        int i = View.getId();
        //If sign in button was clicked, get email and password from EditText inputs and call signIn()
        if (i == R.id.sign_in) {
            EditText email_text = findViewById(R.id.email);
            String email = email_text.getText().toString();
            EditText pass_text = findViewById(R.id.password);
            String password = pass_text.getText().toString();
            if(email.equals(null) || password.equals(null) || email.equals("") || password.equals("")) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields to log in!", Toast.LENGTH_LONG).show();
            } else {
                signIn(email, password);
            }
        //If register button clicked, register new user with FirebaseAuth methods
        } else if (i == R.id.register) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN
            );
        //If guest button clicked, call signInAnonymously()
        } else if(i == R.id.guest) {
            signInAnonymously();
        }
    }

    /**
     * Signs user in as an anonymous user in the the database
     */
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInAnonymously:success");
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInAnonymously:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Signs in the user using the email and password given. If the user exists in the database, sign in will be successful and
     * checkDatabase() called to take user to correct activity, otherwise user will see a toast and stay on login activity
     * @param email email address of the user
     * @param password password of the user
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            checkDatabase();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("Sign in successful!\n" + "name = " + user.getEmail() + "\nid=" + user.getUid());
                Intent intent = new Intent(LoginActivity.this, UserType.class);
                startActivity(intent);
                finish();
            } else {
                if (response == null) {
                    System.out.println("Sign in cancelled");
                }
                if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    System.out.println("No Internet Connection");
                }
            }
        }
    }

    /**
     * Checks the database to see if the current user has a user type in the DB. If they do, takes the user to the Home activity.
     * If they don't takes the user to the user type activity to assign their user type
     */
    protected void checkDatabase() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email;

        //User is logged in
        if (currentUser != null) {
            email = currentUser.getEmail();
            System.out.println("Email: " + email);

            //Connect to Firebase FireStore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            //Access the userTypes collection and search for the document with the user's email
            DocumentReference docRef = db.collection("userTypes").document(email);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        //If Document with user's email exists, go to the home activity
                        if (document.exists()) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        // Otherwise, go to User type activity
                        } else {
                            Intent intent = new Intent(LoginActivity.this, UserType.class);
                            startActivity(intent);
                        }
                        finish();
                    } else {
                        Log.w("tag", "Error getting info", task.getException());
                    }
                }
            });
        //The current user is null/not logged in
        } else {
            System.out.println("user is null");
        }
    }
}