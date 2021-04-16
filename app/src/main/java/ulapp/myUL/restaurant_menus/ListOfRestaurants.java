package ulapp.myUL.restaurant_menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ulapp.myUL.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfRestaurants#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfRestaurants extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

    public ListOfRestaurants() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOfRestaurants.
     */

    public static ListOfRestaurants newInstance(String param1, String param2) {
        ListOfRestaurants fragment = new ListOfRestaurants();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_restaurants, container, false);
    }

    /**
     * initialises recyclerview and sets adapter
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerviewRestuarants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new ListOfRestaurantsAdapter(this.getContext(), getImageList(), getActionList()));

    }

    /**
     * returns list of restaurant icons
     */
    private List<Integer> getImageList() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.stablesclub);
        images.add(R.drawable.scholarsclub);
        images.add(R.drawable.paddocks);
        images.add(R.drawable.pavilion);
        images.add(R.drawable.sportsbar);
        return images;
    }

    /**
     * returns list of actions to restaurant fragments
     */
    private List<Integer> getActionList() {
        List<Integer> actions = new ArrayList<>();
        actions.add(R.id.action_listOfRestaurants_to_stablesClubMenu);
        actions.add(R.id.action_listOfRestaurants_to_scholarsMenu);
        actions.add(R.id.action_listOfRestaurants_to_paddocksMenu);
        actions.add(R.id.action_listOfRestaurants_to_pavilionsMenu);
        actions.add(R.id.action_listOfRestaurants_to_sportsBarMenu);
        return actions;
    }
}