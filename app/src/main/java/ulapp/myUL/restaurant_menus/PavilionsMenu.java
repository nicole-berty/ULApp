package ulapp.myUL.restaurant_menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import ulapp.myUL.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PavilionsMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PavilionsMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView mondayImage, tuesdayImage, wednesdayImage,
            thursdayImage, fridayImage, saturdayImage, sundayImage;

    public PavilionsMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PavilionsMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static PavilionsMenu newInstance(String param1, String param2) {
        PavilionsMenu fragment = new PavilionsMenu();
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
     * initialises the imageviews, sets the image and adjusts the size of the image.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (400 * scale);
        int dpHeightInPx = (int) (500 * scale);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        View rootView = inflater.inflate(R.layout.fragment_pavilions_menu, container, false);
        mondayImage = rootView.findViewById(R.id.MondayImage);
        tuesdayImage = rootView.findViewById(R.id.TuesdayImage);
        wednesdayImage = rootView.findViewById(R.id.WednesdayImage);
        thursdayImage = rootView.findViewById(R.id.ThursdayImage);
        fridayImage = rootView.findViewById(R.id.FridayImage);
        saturdayImage = rootView.findViewById(R.id.SaturdayImage);
        sundayImage = rootView.findViewById(R.id.SundayImage);
        mondayImage.setImageResource(R.drawable.pavilionmenumonday);
        tuesdayImage.setImageResource(R.drawable.pavilionmenutuesday);
        wednesdayImage.setImageResource(R.drawable.pavilionmenuwednesday);
        thursdayImage.setImageResource(R.drawable.pavilionmenuthursday);
        fridayImage.setImageResource(R.drawable.pavilionmenufriday);
        saturdayImage.setImageResource(R.drawable.pavilionmenusaturday);
        sundayImage.setImageResource(R.drawable.pavilionmenusunday);
        mondayImage.setLayoutParams(layoutParams);
        tuesdayImage.setLayoutParams(layoutParams);
        wednesdayImage.setLayoutParams(layoutParams);
        thursdayImage.setLayoutParams(layoutParams);
        fridayImage.setLayoutParams(layoutParams);
        saturdayImage.setLayoutParams(layoutParams);
        sundayImage.setLayoutParams(layoutParams);
        return rootView;
    }
}