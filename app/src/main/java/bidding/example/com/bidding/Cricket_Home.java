package bidding.example.com.bidding;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bidding.example.com.bidding.Chart.SampleObject;

/**
 * Created by Gaurav on 18/01/16.
 */
public class Cricket_Home extends Fragment {



    private CardView cbet,chistory,caccnt,cprvsgame,cupcmngmtch,ccnclbet,ccnclldbet,cscrcrd;
    ProgressDialog pDialog;

    public static String res;
    public static List<SampleObject> sampleObjects = new ArrayList<SampleObject>();
    public Cricket_Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cricket_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cbet= (CardView) view.findViewById(R.id.bet);
        chistory = (CardView) view.findViewById(R.id.history);
        caccnt= (CardView) view.findViewById(R.id.accounts);
        cprvsgame= (CardView) view.findViewById(R.id.previous_game_rslt);
        cupcmngmtch= (CardView) view.findViewById(R.id.upcoming);
        ccnclbet= (CardView) view.findViewById(R.id.cancel_bet);
        ccnclldbet = (CardView) view.findViewById(R.id.cnclld_bets);
        cscrcrd = (CardView) view.findViewById(R.id.score_crd);

        cbet.setCardBackgroundColor(Color.parseColor("#FFAB00"));
        chistory.setCardBackgroundColor(Color.parseColor("#7986CB"));
        caccnt.setCardBackgroundColor(Color.parseColor("#7986CB"));
        cprvsgame.setCardBackgroundColor(Color.parseColor("#FFAB00"));
        cupcmngmtch.setCardBackgroundColor(Color.parseColor("#FFAB00"));
        ccnclbet.setCardBackgroundColor(Color.parseColor("#7986CB"));
        ccnclldbet.setCardBackgroundColor(Color.parseColor("#7986CB"));
        cscrcrd.setCardBackgroundColor(Color.parseColor("#FFAB00"));
    }
}
