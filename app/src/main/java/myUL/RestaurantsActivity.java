package myUL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class RestaurantsActivity extends MenuActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        //fragment = (Fragment)findViewById(R.id.fragment);
    }
}