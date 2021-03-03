package ie.ul.ulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

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

    public void onClick(View view) {
        if(view.getId() == R.id.sign_out) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
    }

    private List<String> getNameList() {
        List<String> names = new ArrayList<>();
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        names.add("kevin");
        return names;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, " You clicked " + adapter.getItem(position) + " on the row number " + position,
                Toast.LENGTH_SHORT).show();
    }
}