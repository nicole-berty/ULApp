package ulapp.myUL.guides;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ulapp.myUL.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VPNSetup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VPNSetup extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public VPNSetup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VPNSetup.
     */
     public static VPNSetup newInstance(String param1, String param2) {
         VPNSetup fragment = new VPNSetup();
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
        View rootView = inflater.inflate(R.layout.fragment_frag__vpn_setup, container, false);

        TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        t2.setText(Html.fromHtml("STEP 1) Download the installer from <a href=\"http://www.ul.ie/secure/FortiClientOnlineInstaller.exe\">this</a> link. <br><br>STEP 2) Run the installer, selecting 'VPN only' when prompted and finish the installation.  <br><br> STEP 3) When FortiClient is launched, select CONFIGURE VPN. Enter the following information in order: UL:ulssl.ul.ie and then click 'Save Login' Click apply and then close.  <br><br>STEP 4)Download and then run the putty ssh client from <a href=\"https://the.earth.li/~sgtatham/putty/latest/w64/putty.exe\">here</a> and input your desired IP, select open and enter your CSIS credentials. <br><br> For more information, read the <a href=\"http://garryowen.csisdmz.ul.ie/~cs4115/admin/fortinetSetupInstructions.pdf\">ForitClient Setup Instructions.</a><br>"));
        return rootView;
    }
}