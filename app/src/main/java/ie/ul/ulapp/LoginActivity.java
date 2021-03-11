package ie.ul.ulapp;

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
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

    public void OnClick (View View) {
        int i = View.getId();
        if (i == R.id.sign_in) {
            EditText email_text = findViewById(R.id.email);
            String email = email_text.getText().toString();
            EditText pass_text = findViewById(R.id.password);
            String password = pass_text.getText().toString();
            signIn(email, password);
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
        } else if(i == R.id.guest) {
        //    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        //    startActivity(intent);
         //   finish();
            signInAnonymously();
        }
    }

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

    protected void checkDatabase() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email;
        if (currentUser != null) {
            email = currentUser.getEmail();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("userTypes").document(email);
            System.out.println("Email: " + email);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // String type = document.getData().toString();
                            //  if (type.equals("student")) {
                            System.out.println("DATA" + document.getData().toString());
                            //    } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            //  }
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
        } else {
            System.out.println("user is null");
        }
    }
}