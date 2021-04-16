package ulapp.myUL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
/**
 * simple start point fragment with a scroll view of buttons that lead to the club/soc fragments
 */
    //this is the initial fragment with a list view
public class StartPointClubSocs extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StartPointClubSocs() {
        // Required empty public constructor
    }

    public static StartPointClubSocs newInstance(String param1, String param2) {
        StartPointClubSocs fragment = new StartPointClubSocs();
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
        return inflater.inflate(R.layout.fragment_start_point_club_socs, container, false);
    }
    //this is where navigation between fragments is set up
    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){
        super.onViewCreated(view, SavedInstanceState);

        Button button1 = view.findViewById(R.id.American_Football);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_americanFootball));
            }
        });

        Button button2 = view.findViewById(R.id.archery);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_archery));
            }
        });

        Button button3 = view.findViewById(R.id.athletics);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_athletics));
            }
        });

        Button button4 = view.findViewById(R.id.badminton);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_badminton));
            }
        });

        Button button5 = view.findViewById(R.id.basketball);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_basketball));
            }
        });

        Button button6 = view.findViewById(R.id.boxing);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_boxing));
            }
        });

        Button button7 = view.findViewById(R.id.brazJiuJit);
        button7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_brazJiuJit));
            }
        });

        Button button8 = view.findViewById(R.id.cheerlead);
        button8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_cheerlead));
            }
        });

        Button button9 = view.findViewById(R.id.esport);
        button9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_esport));
            }
        });

        Button button10 = view.findViewById(R.id.fencing);
        button10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_fencing));
            }
        });

        Button button11 = view.findViewById(R.id.GAA);
        button11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_GAA));
            }
        });

        Button button12 = view.findViewById(R.id.handball);
        button12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_handball));
            }
        });

        Button button13 = view.findViewById(R.id.kayak);
        button13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_kayak));
            }
        });

        Button button14 = view.findViewById(R.id.ladiesRug);
        button14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ladiesRug));
            }
        });

        Button button15 = view.findViewById(R.id.ladiesSoc);
        button15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ladiesSoc));
            }
        });

        Button button16 = view.findViewById(R.id.mensSoc);
        button16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_mensSoc));
            }
        });

        Button button17 = view.findViewById(R.id.mensRug);
        button17.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_mensRug));
            }
        });

        Button button18 = view.findViewById(R.id.mountainBike);
        button18.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_mountainBike));
            }
        });

        Button button19 = view.findViewById(R.id.outdoor);
        button19.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_outdoor));
            }
        });

        Button button20 = view.findViewById(R.id.parkour);
        button20.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_parkour));
            }
        });

        Button button21 = view.findViewById(R.id.rowing);
        button21.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_rowing));
            }
        });

        Button button22 = view.findViewById(R.id.skydiving);
        button22.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_skydiving));
            }
        });

        Button button23 = view.findViewById(R.id.socialSoc);
        button23.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_socialSoc));
            }
        });

        Button button24 = view.findViewById(R.id.subAq);
        button24.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_subAq));
            }
        });

        Button button25 = view.findViewById(R.id.surf);
        button25.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_surf));
            }
        });

        Button button26 = view.findViewById(R.id.swim);
        button26.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_swim));
            }
        });

        Button button27 = view.findViewById(R.id.tableTennis);
        button27.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_tableTennis));
            }
        });

        Button button28 = view.findViewById(R.id.taeKwon);
        button28.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_taeKwon));
            }
        });

        Button button29 = view.findViewById(R.id.tagRug);
        button29.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_tagRug));
            }
        });

        Button button30 = view.findViewById(R.id.trampo);
        button30.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_trampo));
            }
        });

        Button button31 = view.findViewById(R.id.ultFriz);
        button31.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ultFriz));
            }
        });

        Button button32 = view.findViewById(R.id.windsports);
        button32.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_windsports));
            }
        });

        Button button33 = view.findViewById(R.id.africa);
        button33.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_africa));
            }
        });
        Button button34 = view.findViewById(R.id.AMSI);
        button34.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_AMSI));
            }
        });

        Button button35 = view.findViewById(R.id.anime);
        button35.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_anime));
            }
        });

        Button button36 = view.findViewById(R.id.archite);
        button36.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_archite));
            }
        });
        Button button37 = view.findViewById(R.id.astro);
        button37.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_astro));
            }
        });

        Button button38 = view.findViewById(R.id.christUn);
        button38.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_christUn));
            }
        });

        Button button39 = view.findViewById(R.id.comedy);
        button39.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_comedy));
            }
        });

        Button button40 = view.findViewById(R.id.computer);
        button40.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_comedy));
            }
        });

        Button button41 = view.findViewById(R.id.crafts);
        button41.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_crafts));
            }
        });

        Button button42 = view.findViewById(R.id.cumannGael);
        button42.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_cumannGael));
            }
        });

        Button button43 = view.findViewById(R.id.dance);
        button43.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_dance));
            }
        });

        Button button44 = view.findViewById(R.id.drama);
        button44.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_drama));
            }
        });

        Button button45 = view.findViewById(R.id.ecAndInv);
        button45.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ecAndInv));
            }
        });

        Button button46 = view.findViewById(R.id.enactusSoc);
        button46.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_enactusSoc));
            }
        });

        Button button47 = view.findViewById(R.id.entrepr);
        button47.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_entrepr));
            }
        });

        Button button48 = view.findViewById(R.id.environ);
        button48.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_environ));
            }
        });

        Button button49 = view.findViewById(R.id.fem);
        button49.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_fem));
            }
        });

        Button button50 = view.findViewById(R.id.film);
        button50.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_film));
            }
        });

        Button button51 = view.findViewById(R.id.friends);
        button51.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_friends));
            }
        });

        Button button52 = view.findViewById(R.id.games);
        button52.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_games));
            }
        });

        Button button53 = view.findViewById(R.id.gameDev);
        button53.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_gameDev));
            }
        });

        Button button54 = view.findViewById(R.id.horseRace);
        button54.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_horseRace));
            }
        });

        Button button55 = view.findViewById(R.id.international);
        button55.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_international));
            }
        });

        Button button56 = view.findViewById(R.id.islamic);
        button56.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_islamic));
            }
        });

        Button button57 = view.findViewById(R.id.law);
        button57.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_law));
            }
        });

        Button button58 = view.findViewById(R.id.medicine);
        button58.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_medicine));
            }
        });

        Button button59 = view.findViewById(R.id.music);
        button59.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_music));
            }
        });

        Button button60 = view.findViewById(R.id.musicTheatre);
        button60.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_musicTheatre));
            }
        });

        Button button61 = view.findViewById(R.id.ograFianna);
        button61.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ograFianna));
            }
        });

        Button button62 = view.findViewById(R.id.outInUl);
        button62.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_outInUl));
            }
        });

        Button button63 = view.findViewById(R.id.photo);
        button63.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_photo));
            }
        });

        Button button64 = view.findViewById(R.id.racing_motor);
        button64.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_racing_motor));
            }
        });

        Button button65 = view.findViewById(R.id.strength_cond);
        button65.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_strength_cond));
            }
        });

        Button button66 = view.findViewById(R.id.surgical);
        button66.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_surgical));
            }
        });

        Button button67 = view.findViewById(R.id.ulGive);
        button67.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ulGive));
            }
        });

        Button button68 = view.findViewById(R.id.ULFM);
        button68.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_ULFM));
            }
        });

        Button button69 = view.findViewById(R.id.wiSTEM);
        button69.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_wiSTEM));
            }
        });
        Button button70 = view.findViewById(R.id.yoga);
        button70.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_yoga));
            }
        });

        Button button71 = view.findViewById(R.id.fineG);
        button71.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate((R.id.action_startPointClubSocs_to_fineG));
            }
        });



    }
}
