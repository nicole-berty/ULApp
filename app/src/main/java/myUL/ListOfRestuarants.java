package myUL;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfRestuarants#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfRestuarants extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;

    public ListOfRestuarants() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOfRestuarants.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOfRestuarants newInstance(String param1, String param2) {
        ListOfRestuarants fragment = new ListOfRestuarants();
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
        return inflater.inflate(R.layout.fragment_list_of_restuarants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerviewRestuarants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new ListOfRestaurantsAdapter(this.getContext(), getImageList(), getActionList()));

    }

    private List<Integer> getImageList() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.stablesclub);
        images.add(R.drawable.scholarsclub);
        images.add(R.drawable.paddocks);
        images.add(R.drawable.pavilion);
        images.add(R.drawable.sportsbar);
        return images;
    }

    private List<Integer> getActionList() {
        List<Integer> actions = new ArrayList<>();
        actions.add(R.id.action_listOfRestuarants_to_stablesClubMenu);
        actions.add(R.id.action_listOfRestuarants_to_scholarsMenu);
        actions.add(R.id.action_listOfRestuarants_to_paddocksMenu);
        actions.add(R.id.action_listOfRestuarants_to_pavilionsMenu);
        actions.add(R.id.action_listOfRestuarants_to_sportsBarMenu);
        return actions;
    }
}