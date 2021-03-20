package ie.ul.restuarantmenus;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaddocksMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaddocksMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView breakfastImage, lunchImage, dinnerImage, paddocksImage;

    public PaddocksMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaddocksMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static PaddocksMenu newInstance(String param1, String param2) {
        PaddocksMenu fragment = new PaddocksMenu();
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
        final View rootView = inflater.inflate(R.layout.fragment_paddocks_menu, container, false);
        breakfastImage = rootView.findViewById(R.id.BreakfastImage);
        lunchImage = rootView.findViewById(R.id.LunchImage);
        dinnerImage = rootView.findViewById(R.id.DinnerImage);
        paddocksImage = rootView.findViewById(R.id.paddocksIcon);
        paddocksImage.setImageResource(R.drawable.paddocks);
        breakfastImage.setImageResource(R.drawable.paddocks_breakfast);
        lunchImage.setImageResource(R.drawable.paddocks_lunch);
        dinnerImage.setImageResource(R.drawable.paddocks_dinner);
        return rootView;
    }
}