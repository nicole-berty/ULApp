package ulapp.myUL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class ParkingInfo extends ActionBar {

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
                Intent intent2 = new Intent(ParkingInfo.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        }, intentFilter);

        setContentView(R.layout.activity_parking);
    }
}