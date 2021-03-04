package ie.ul.ulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MenuActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private FirebaseAuth mAuth;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        List<String> names = getNameList();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, names);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private List<String> getNameList() {
        List<String> names = new ArrayList<>();
        names.add("Info");
        names.add("Clubs and Societies");
        names.add("Menus");
        names.add("Carpool");
        names.add("Timetable");
        names.add("Parking");
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
                Toast.makeText(this, " You clicked " + adapter.getItem(position) + " Wowwww",
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