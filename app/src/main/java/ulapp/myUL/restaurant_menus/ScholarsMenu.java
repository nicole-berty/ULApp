package ulapp.myUL.restaurant_menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import ulapp.myUL.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScholarsMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScholarsMenu extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView scholarsMenu;

    public ScholarsMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScholarsMenu.
     */
    public static ScholarsMenu newInstance(String param1, String param2) {
        ScholarsMenu fragment = new ScholarsMenu();
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

    /**
     * initialises the image view and sets the image
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scholars_menu, container, false);
        scholarsMenu = rootView.findViewById(R.id.ScholarsMenuImage);
        scholarsMenu.setImageResource(R.drawable.scholarsmenu);
        return rootView;
    }
}