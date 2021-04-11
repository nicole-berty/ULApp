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

    MyRecyclerViewAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        TextView welcome = findViewById(R.id.textView4);
        welcome.setText("Welcome to the official UL App! Here you'll find all the info you need to navigate the campus and student life.");
        welcome.setTextColor(Color.rgb(4,84,52));

        //Create and display RecyclerView
        List<String> names = getNameList();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, names);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //Display website resources beneath the Recyclerview
        TextView link = findViewById(R.id.textView2);
        String linkText = "Visit the <a href='https://www.ul.ie/'>University of Limerick</a> web page.\nCheck out the <a href='https://www.ul.ie/library/'>Library</a> to reserve books and access online services!";
        link.setText(HtmlCompat.fromHtml(linkText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        link.setTextColor(Color.rgb(4,84,52));
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * Creates an ArrayList of activity/page names for the RecyclerView. ArrayList values varies depending on whether use is signed in or not
     * @return String ArrayList with page names
     */
    private List<String> getNameList() {
        List<String> names = new ArrayList<>();
        names.add("Info");
        names.add("Menus");
        names.add("Map");
        //If the user is registered on the database, add extra pages to the list
        if(!user.isAnonymous()) {
            names.add("Clubs and Societies");
            names.add("Timetable");
        }
        return names;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent;
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