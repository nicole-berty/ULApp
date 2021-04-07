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
 * Use the {@link FeesInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeesInfo extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FeesInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeesInfo.
     */
    public static FeesInfo newInstance(String param1, String param2) {
        FeesInfo fragment = new FeesInfo();
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
        View rootView = inflater.inflate(R.layout.fragment_fees_info, container, false);

        TextView t2 = (TextView) rootView.findViewById(R.id.textView12);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        t2.setText(Html.fromHtml("UL students are required to pay €3000 annually as a registration fee in UL in addition to a small fee for the Student Centre. These can be payed through your Student Portal SI page found <a href=\"https://www.si.ul.ie/urd/sits.urd/run/siw_lgn\">here</a>. There is also an automated telephone payment system " +
                "available for Student Fees on 0035361-529097. <br><br>All new entrants must complete the Online Finance Task on the student portal which will " +
                "determine your fee liability for the year. This must be completed even if you will not be paying any fees. <br><br> Fees are payed bi-annually, due at the beginning of each semester.<br>"));
        return rootView;
    }
}