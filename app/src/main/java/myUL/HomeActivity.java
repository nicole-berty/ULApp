package myUL;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MenuActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView ulLogo = findViewById(R.id.ulLogo);
        ulLogo.setImageResource(R.drawable.ul_logo);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

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
        link.setMovementMethod(LinkMovementMethod.getInstance());

    }

    /**
     * Creates an ArrayList of activity/page names for the RecyclerView. ArrayList values varies depending on whether use is signed in or not
     * @return String ArrayList with page names
     */
    private List<String> getNameList() {
        List<String> names = new ArrayList<>();
        names.add("Info");
        names.add("Clubs and Societies");
        names.add("Menus");
        //If the user is registered on the database, add extra pages to the list
        if(!user.isAnonymous()) {
            names.add("Carpool");
            names.add("Timetable");
        }
        names.add("Map");
        return names;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, " You clicked " + adapter.getItem(position) + " on the row number " + position,
                Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
            case 1:
                Toast.makeText(this, " You clicked " + adapter.getItem(position),
                        Toast.LENGTH_SHORT).show();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            default:
                break;
        }
    }
}