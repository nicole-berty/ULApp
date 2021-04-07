package ulapp.myUL;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class RestaurantsActivity extends ActionBar {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        //fragment = (Fragment)findViewById(R.id.fragment);
    }
}