package myUL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//dummy class which allows DB to update behind it
public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        //Use intents to pass the activity name to this activity and then update textview accordingly
        TextView successText = findViewById(R.id.success);

        String action = getIntent().getStringExtra("ACTION");

        switch (action) {
            case "ADD":
                successText.setText("Success! Now you can go back and add even more events to your timetable!");
                break;
            case "EDIT":
                successText.setText("Success! You have edited the event`s details!");
                break;
            case "DELETE":
                successText.setText("Success! You have deleted the event from your timetable!");
                break;
            default:
                successText.setText("Success! You have deleted all of the events in your timetable! Now you can go back and make some more.");
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TimetableActivity.class);
        startActivity(intent);
        finish();
    }
}