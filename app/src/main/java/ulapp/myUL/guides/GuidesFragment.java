package ulapp.myUL.guides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ulapp.myUL.R;

/** corresponds to the guide fragment, from this stems the other 5 guide fragments
 */
public class GuidesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GuidesFragment() {

    }


    public static GuidesFragment newInstance(String param1, String param2) {
        GuidesFragment fragment = new GuidesFragment();
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
        return inflater.inflate(R.layout.fragment_guides, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){
        super.onViewCreated(view, SavedInstanceState);

        Button feesInfo = view.findViewById(R.id.button);
        feesInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_blank_Start_to_fees_info));
            }
        });

        Button emailInfo = view.findViewById(R.id.button2);
        emailInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_blank_Start_to_fragment_Email));
            }
        });

        Button vpnSetup = view.findViewById(R.id.button3);
        vpnSetup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_blank_Start_to_frag_VPNsetup));
            }
        });

        Button wifiSetup = view.findViewById(R.id.button4);
        wifiSetup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_blank_Start_to_wifi_Setup));
            }
        });

        Button otherInfo = view.findViewById(R.id.button5);
        otherInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_blank_Start_to_other));
            }
        });
    }
}

