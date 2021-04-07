package myUL.guides;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import myUL.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuidesOther#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuidesOther extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GuidesOther() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuidesOther.
     */
    public static GuidesOther newInstance(String param1, String param2) {
        GuidesOther fragment = new GuidesOther();
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
        View rootView = inflater.inflate(R.layout.fragment_guides_other, container, false);

        TextView t2 = (TextView) rootView.findViewById(R.id.textView9);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        t2.setText(Html.fromHtml("More Information and Guidance for life at UL can be found on the <a href=\"https://ulsites.ul.ie/access/student-support-services\">Student Support Services page.</a><br>For additional assistance, use the contact information below:<br>"));
        return rootView;
    }
}