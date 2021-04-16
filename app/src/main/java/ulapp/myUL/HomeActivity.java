package ulapp.myUL;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import ulapp.myUL.timetable.TimetableActivity;

public class HomeActivity extends ActionBar implements MyRecyclerViewAdapter.ItemClickListener {

    //Create class variables
    MyRecyclerViewAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
          Check for Broadcast receiver which is created on logging out.
          This will prevent a user from going back to a page that should be only seen by logged in users.
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //Logout has occurred, start login activity and finish this one
                Intent intent2 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        }, intentFilter);

        setContentView(R.layout.activity_home);

        ImageView ulLogo = findViewById(R.id.ulLogo);
        ulLogo.setImageResource(R.drawable.ul_logo);

        //Get the current user from FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //Set the welcome text and its colour
        TextView welcome = findViewById(R.id.textView4);
        welcome.setText("Welcome to the official UL App! Here you'll find all the info you need to navigate the campus and student life.");
        welcome.setTextColor(Color.rgb(4,84,52));

        //Get the list of features in the app - varies based on type of user
        List<String> features = getFeaturesList();
        //Create and display RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Use custom RecyclerViewAdapter class for the adapter and the features list to it
        adapter = new MyRecyclerViewAdapter(this, features);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //Display website resources beneath the Recyclerview in a text view
        TextView link = findViewById(R.id.textView2);
        String linkText = "Visit the <a href='https://www.ul.ie/'>University of Limerick</a> web page.\nCheck out the <a href='https://www.ul.ie/library/'>Library</a> to reserve books and access online services!";

        //Make the link in the TextView clickable
        link.setText(HtmlCompat.fromHtml(linkText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        link.setTextColor(Color.rgb(4,84,52));
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Creates an ArrayList of activity/page names for the RecyclerView. ArrayList values varies depending on whether use is signed in or not
     * @return String ArrayList with page names
     */
    private List<String> getFeaturesList() {
        List<String> features = new ArrayList<>();
        //Add features for all users
        features.add("Info");
        features.add("Menus");
        features.add("Map");
        //If the user is not a guest user, add extra pages to the list
        if(!user.isAnonymous()) {
            features.add("Clubs and Societies");
            features.add("Timetable");
        }
        return features;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent;
        //Take the user to the correct page based on what they clicked in the RecyclerView
        switch (position) {
            case 0:
                intent = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(HomeActivity.this, RestaurantsActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(HomeActivity.this, ClubsSocs.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(HomeActivity.this, TimetableActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}