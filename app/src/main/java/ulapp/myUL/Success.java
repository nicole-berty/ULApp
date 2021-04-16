package ulapp.myUL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ulapp.myUL.timetable.TimetableActivity;

//dummy class which allows DB to update behind it
public class Success extends ActionBar {

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
                Intent intent2 = new Intent(Success.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        }, intentFilter);
        setContentView(R.layout.activity_success);
        //Use intents to pass the activity name to this activity and then update TextView accordingly
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