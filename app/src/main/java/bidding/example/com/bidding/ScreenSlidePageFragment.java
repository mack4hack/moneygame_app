/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bidding.example.com.bidding;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import bidding.example.com.bidding.Adapter.ListDetailsAdapter;
import bidding.example.com.bidding.AppDB.DbAdapter;
import bidding.example.com.bidding.GetterSetter.MatchDetailsGetSet;

public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static List<MatchDetailsGetSet> matchDetailsGetSetList = new ArrayList<>();
    public static List<String> matchids = new ArrayList<String>();
    ListDetailsAdapter listDetailsAdapter;
    public static List<MatchDetailsGetSet> matchidrecrd;
    public static List<MatchDetailsGetSet> matchlivercrd;
    public static List<MatchDetailsGetSet> matchtossrcrd;
    public static List<MatchDetailsGetSet> matchfrstbalfrstinrcrd;
    public static List<MatchDetailsGetSet> matchfrstballscndinrcrd;
    public static List<MatchDetailsGetSet> matchfrstoverarcrd;
    public static List<MatchDetailsGetSet> matchfrstoverbrcrd;
    public static List<MatchDetailsGetSet> match10overarcrd;
    public static List<MatchDetailsGetSet> match10overbrcrd;
    public static List<MatchDetailsGetSet> matchfrstwcktarcrd;
    public static List<MatchDetailsGetSet> matchfrstwcktbrcrd;
    public static List<MatchDetailsGetSet> matchhighopnrcrd;
    public static List<MatchDetailsGetSet> matchrace50rcrd;
    public static List<MatchDetailsGetSet> matchrunatwicktarcrd;
    public static List<MatchDetailsGetSet> matchrunatwicktbrcrd;
    public static List<MatchDetailsGetSet> matchmake30arcrd;
    public static List<MatchDetailsGetSet> matchmake30brcrd;
    public static List<MatchDetailsGetSet> matchmake50arcrd;
    public static List<MatchDetailsGetSet> matchmake50brcrd;
    public static List<MatchDetailsGetSet> matchmake100arcrd;
    public static List<MatchDetailsGetSet> matchmake100brcrd;
    public static List<MatchDetailsGetSet> matchinnrunratearcrd;
    public static List<MatchDetailsGetSet> matchinnrunratebrcrd;

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    Button more;
    ImageButton close;
    private Animation animShow, animHide;
    private SlidingPanel popup = null;
    private boolean popUpStatus = false;
    ListView listView, listDetail;
    TextView heading, match, start, score, overs;
    private static String[] GAME = {"Toss","1ST BALL 1ST INNINGS","1ST BALL 2ND INNINGS","1ST OVER RUNS TEAM-1","1ST OVER RUNS TEAM-2","10 OVER SESSION TEAM-1","10 OVER SESSION TEAM-2","1ST WICKET METHOD TEAM-1","1ST WICKET METHOD TEAM-2","HIGHEST OPENING PARTNERSHIP","RACE TO 50","RUNS AT 1ST WICKET FALL TEAM-1","RUNS AT 1ST WICKET FALL TEAM-2","TO MAKE 30 TEAM-1","TO MAKE 30 TEAM-2","TO MAKE 50 TEAM-1","TO MAKE 50 TEAM-2","TO MAKE 100 TEAM-1","TO MAKE 100 TEAM-2","INNINGS RUN RATE TEAM-1","INNINGS RUN RATE TEAM-2"};
    private static String[] Positon1 = {"Dot ball \t\t 1.25","Wicket \t\t\t 5.5","Wide ball \t\t 2.5","No ball \t\t 4.5","1 run \t\t\t 1.5","2 runs \t\t\t 3.5","3 runs \t\t\t 5.5","4 runs \t\t\t 6.5","6 runs \t\t\t 10"};
    private static String[] Wicket = {"Caught behind \t\t xx","Caught in the field \t\t xx","LBW \t\t xx","Bowled \t\t xx","Run out \t\t\t xx","Stumped \t\t\t xx","Hit wicket \t\t xx","Retired hurt \t\t\t xx"};
    private static String[] Highest_Opening = {"Team 1\t\t 1.85","Team 2 \t\t 1.85","Tie \t\t 7"};
    private static String[] Wicket_Fall = {"0 to 10 \t\t 3","11 to 20 \t\t 3.5","21 to 30 \t\t 3.5","31 to 40 \t\t 4","41 to 50 \t\t 5"};
    private static String[] First_Over = {"0 to 1 \t\t 8","2 to 3 \t\t 7","4 to 5 \t\t 5.5","6 to 7 \t\t 5","8 to 9 \t\t 5.5"};
    private static String[] Ten_Over = {"Below 55 \t\t 4","56 to 60 \t\t 4","61 to 65 \t\t 5","66 to 70 \t\t 5","71 to 75 \t\t 5"};
    private static String[] Make_50 = {"none \t\t 6.5","1 \t\t 4.5","2 \t\t 1.9","3 \t\t 2.55","4 or more \t\t 3.5"};
    private static String[] Make_100 = {"none \t\t 2.5","1 \t\t 2","2 \t\t 4.5","3 or more \t\t 6.5"};
    private static String[] Run_Rate = {"Below 5.00 \t\t 3","5.01 to 5.5 \t\t 4","5.51 to 6.00 \t\t 3.5","6.01 to 6.5 \t\t 3.5","6.51 to 7.0 \t\t 4.5"};
    DbAdapter dbAdapter;
    public static String title,id, mid, oddid, odd, ttlchip, pyout, resltbet, gmcls, prtclr, nm, unq, frmt, ven, strt, ta, tb,wnr, sts, gmnm, teama, teamb, head, mtch;
    public static String matchid;
    public static String matchiid;
    public static String matchstatus="";
    public static String mid1="";
    String lid,batteam, ballteam,run;
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.activity_play_details, container, false);

//        getActivity().setTitle(getString(R.string.title_activity_screen_slide));
        // Set the title view to show the page number.

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        heading = (TextView) view.findViewById(R.id.txtheading);
        match = (TextView) view.findViewById(R.id.txtlive);
        start = (TextView) view.findViewById(R.id.txtstart);
        score = (TextView) view.findViewById(R.id.txtScore);
        overs = (TextView) view.findViewById(R.id.txtOver);
        more = (Button) view.findViewById(R.id.btn_more);

        listDetail = (ListView) view.findViewById(R.id.listDetail);
//        listDetail.setVisibility(View.GONE);

        matchiid=ScreenSlide.match_id;
        getMatchDetailsOdds();

        initPopup(view);

        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
//                startActivity(new Intent(PlayDetails.this, PlayDetails.class));
            }
        });
    }

    private void initPopup(View view) {

        try {
            popup = (SlidingPanel) view.findViewById(R.id.popup_window);

            close = (ImageButton) view.findViewById(R.id.cncl);
            listView = (ListView) view.findViewById(R.id.listGames);

            animShow = AnimationUtils.loadAnimation(getActivity(), R.anim.popup_show);
            animHide = AnimationUtils.loadAnimation(getActivity(), R.anim.popup_hide);

            more.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent me) {
                    if (me.getAction() == MotionEvent.ACTION_DOWN) {
//                        me.getX();
//                        me.getY();
//                        Log.i("myTag", "Action Down " + me.getX() + "," + me.getY());
                    } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
//                        params.setMargins((int) me.getRawX() - v.getWidth() / 2, (int) (me.getRawY() - v.getHeight() * 1.5), (int) me.getRawX() - v.getWidth() / 2, (int) (me.getRawY() - v.getHeight() * 1.5));
//                        v.setLayoutParams(params);
                        popup.setVisibility(View.VISIBLE);
                        popup.startAnimation(animShow);
                        popUpStatus = true;
                        more.setEnabled(false);
                        more.setVisibility(View.GONE);
                        close.setVisibility(View.VISIBLE);
                        close.setEnabled(true);
                        String[] GAME = {"Toss","1st Ball "+ta,"1st Ball "+tb,"1st Over Runs "+ta,"1st Over Runs "+tb,"10 Over Session "+ta,"10 Over Session "+tb,"1st Wicket Method "+ta,"1st Wicket Method "+tb,"Highest Opening Partnership","Race To 50","Runs At 1st Wicket Fall "+ta,"Runs At 1st Wicket Fall "+tb,"To Make 30 "+ta,"To Make 30 "+tb,"To Make 50 "+ta,"To Make 50 "+tb,"To Make 100 "+ta,"To Make 100 "+tb,"Innings Run Rate "+ta,"Innings Run Rate "+tb};
                        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, GAME));
                    }
                    return true;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                   int pos= (int) parent.getItemAtPosition(position);
                    if (position == 0) {
                        heading.setText("Toss");
//                        toss.setVisibility(View.VISIBLE);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchtossrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 1) {
                        heading.setText("1st Ball "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchfrstbalfrstinrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 2) {
                        heading.setText("1st Ball "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchfrstballscndinrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 3) {
                        heading.setText("1st Over Runs "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchfrstoverarcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 4) {
                        heading.setText("1st Over Runs "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchfrstoverbrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 5) {
                        heading.setText("10 Over Session "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), match10overarcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 6) {
                        heading.setText("10 Over Session "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), match10overbrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 7) {
                        heading.setText("1st Wicket Method "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchfrstwcktarcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 8) {
                        heading.setText("1st Wicket Method "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchfrstwcktbrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 9) {
                        heading.setText("Highest Opening Partnership");
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchhighopnrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 10) {
                        heading.setText("Race To 50");
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchrace50rcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 11) {
                        heading.setText("Runs At 1st Wicket Fall "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchrunatwicktarcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 12) {
                        heading.setText("Runs At 1st Wicket Fall "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchrunatwicktbrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 13) {
                        heading.setText("To Make 30 "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchmake30arcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 14) {
                        heading.setText("To Make 30 "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchmake30brcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 15) {
                        heading.setText("To Make 50 "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchmake50arcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 16) {
                        heading.setText("To Make 50 "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchmake50brcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 17) {
                        heading.setText("To Make 100 "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchmake100arcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 18) {
                        heading.setText("To Make 100 "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchmake100brcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 19) {
                        heading.setText("Innings Run Rate "+ta);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchinnrunratearcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    } else if (position == 20) {
                        heading.setText("Innings Run Rate "+tb);
//                        live.setVisibility(View.GONE);
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchinnrunratebrcrd);
                        listDetail.setAdapter(listDetailsAdapter);
                    }
                    popup.setVisibility(View.GONE);
//                   popup.startAnimation(animHide);
                    popUpStatus = false;
                    close.setEnabled(false);
                    close.setVisibility(View.GONE);
                    more.setVisibility(View.VISIBLE);
                    more.setEnabled(true);
                }
            });
            close.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        popup.setVisibility(View.GONE);
                        popup.startAnimation(animHide);
                        popUpStatus = false;
                        close.setEnabled(false);
                        close.setVisibility(View.GONE);
                        more.setVisibility(View.VISIBLE);
                        more.setEnabled(true);
                    }
                    return true;
                }
            });

            listDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MatchDetailsGetSet item;
                    if(!matchstatus.equals("completed")) {
                        if (heading.getText().toString().equals("Toss")) {
                            item = matchtossrcrd.get(position);
                        } else if (heading.getText().toString().equals("1st Ball " + ta)) {
                            item = matchfrstbalfrstinrcrd.get(position);
                        } else if (heading.getText().toString().equals("1st Ball " + tb)) {
                            item = matchfrstballscndinrcrd.get(position);
                        } else if (heading.getText().toString().equals("1st Over Runs " + ta)) {
                            item = matchfrstoverarcrd.get(position);
                        } else if (heading.getText().toString().equals("1st Over Runs " + tb)) {
                            item = matchfrstoverbrcrd.get(position);
                        } else if (heading.getText().toString().equals("10 Over Session " + ta)) {
                            item = match10overarcrd.get(position);
                        } else if (heading.getText().toString().equals("10 Over Session " + tb)) {
                            item = match10overbrcrd.get(position);
                        } else if (heading.getText().toString().equals("1st Wicket Method " + ta)) {
                            item = matchfrstwcktarcrd.get(position);
                        } else if (heading.getText().toString().equals("1st Wicket Method " + tb)) {
                            item = matchfrstwcktbrcrd.get(position);
                        } else if (heading.getText().toString().equals("Highest Opening Partnership")) {
                            item = matchhighopnrcrd.get(position);
                        } else if (heading.getText().toString().equals("Race To 50")) {
                            item = matchrace50rcrd.get(position);
                        } else if (heading.getText().toString().equals("Runs At 1st Wicket Fall " + ta)) {
                            item = matchrunatwicktarcrd.get(position);
                        } else if (heading.getText().toString().equals("Runs At 1st Wicket Fall " + tb)) {
                            item = matchrunatwicktbrcrd.get(position);
                        } else if (heading.getText().toString().equals("To Make 30 " + ta)) {
                            item = matchmake30arcrd.get(position);
                        } else if (heading.getText().toString().equals("To Make 30 " + tb)) {
                            item = matchmake30brcrd.get(position);
                        } else if (heading.getText().toString().equals("To Make 50 " + ta)) {
                            item = matchmake50arcrd.get(position);
                        } else if (heading.getText().toString().equals("To Make 50 " + tb)) {
                            item = matchmake50brcrd.get(position);
                        } else if (heading.getText().toString().equals("To Make 100 " + ta)) {
                            item = matchmake100arcrd.get(position);
                        } else if (heading.getText().toString().equals("To Make 100 " + tb)) {
                            item = matchmake100brcrd.get(position);
                        } else if (heading.getText().toString().equals("Innings Run Rate " + ta)) {
                            item = matchinnrunratearcrd.get(position);
                        } else if (heading.getText().toString().equals("Innings Run Rate " + tb)) {
                            item = matchinnrunratebrcrd.get(position);
                        } else {
                            item = matchlivercrd.get(position);
                        }


                        startActivity(new Intent(getActivity(), CricketPlaceBet.class).putExtra("odd", item.getOdd()).putExtra("match", item.getName()).putExtra("matchid", item.getMatchid()).putExtra("date", item.getStrt()).putExtra("game", heading.getText().toString()).putExtra("oddid", item.getOddid()).putExtra("mid", item.getMid()).putExtra("partclr", item.getPrtclr()));
                    }
                    else{
                        Toast.makeText(getActivity(),"Match completed, cannot place bet now",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    private void getMatchDetailsOdds(){
        String tag_json_obj = "json_obj_req";
        final String TAG = "response";
        final String url = getString(R.string.get_match_odds);
        Log.i("url", "" + url);


        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.d(TAG, response.toString());
//                dbAdapter= new DbAdapter(getActivity());
//                dbAdapter.open();

                matchidrecrd = new ArrayList<>();
                matchlivercrd = new ArrayList<>();
                matchtossrcrd = new ArrayList<>();
                matchfrstbalfrstinrcrd = new ArrayList<>();
                matchfrstballscndinrcrd = new ArrayList<>();
                matchfrstoverarcrd = new ArrayList<>();
                matchfrstoverbrcrd = new ArrayList<>();
                match10overarcrd = new ArrayList<>();
                match10overbrcrd = new ArrayList<>();
                matchfrstwcktarcrd = new ArrayList<>();
                matchfrstwcktbrcrd = new ArrayList<>();
                matchhighopnrcrd = new ArrayList<>();
                matchrace50rcrd = new ArrayList<>();
                matchrunatwicktarcrd = new ArrayList<>();
                matchrunatwicktbrcrd = new ArrayList<>();
                matchmake30arcrd = new ArrayList<>();
                matchmake30brcrd = new ArrayList<>();
                matchmake50arcrd = new ArrayList<>();
                matchmake50brcrd = new ArrayList<>();
                matchmake100arcrd = new ArrayList<>();
                matchmake100brcrd = new ArrayList<>();
                matchinnrunratearcrd = new ArrayList<>();
                matchinnrunratebrcrd = new ArrayList<>();

                try {
                    JSONObject object = new JSONObject(response);

                    if(object.getString("status").equals("true"))
                    {
                        JSONObject jsonObject = object.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("Match_Odds");
                        for(int i=0; i<jsonArray.length(); i++){
                            MatchDetailsGetSet matchDetailsGetSet = new MatchDetailsGetSet();
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            id = object1.getString("id");
                            matchid = object1.getString("match_id");
                            mid = object1.getString("m_id");
                            oddid = object1.getString("odd_id");
                            odd = object1.getString("odds");
                            ttlchip = object1.getString("total_chips");
                            pyout = object1.getString("payout");
                            resltbet = object1.getString("result_bet");
                            gmcls = object1.getString("game_close");
                            prtclr = object1.getString("perticulars");
                            nm = object1.getString("name");
                            unq = object1.getString("unique");
                            frmt = object1.getString("format");
                            ven = object1.getString("venue");
                            String time = object1.getString("start_date");
                            time = time.replace("T", " ");
                            time = time.replace("-", "/");
                            String [] split = time.split("\\u002B");
                            strt =split[0];
                            teama = object1.getString("team_a");
                            teamb = object1.getString("team_b");
                            wnr = object1.getString("winner_team");
                            sts = object1.getString("status");
                            gmnm = object1.getString("game_name");
//                            dbAdapter.InsertOdds(id,matchid,mid,oddid,odd,ttlchip,pyout,resltbet,gmcls,prtclr,nm,unq,frmt,ven,strt,teama,teamb,wnr,sts,gmnm);

                            matchDetailsGetSet.setId(id);
                            matchDetailsGetSet.setMatchid(matchid);
                            matchDetailsGetSet.setMid(mid);
                            matchDetailsGetSet.setOddid(oddid);
                            matchDetailsGetSet.setOdd(odd);
                            matchDetailsGetSet.setTtlchip(ttlchip);
                            matchDetailsGetSet.setPayout(pyout);
                            matchDetailsGetSet.setResultbet(resltbet);
                            matchDetailsGetSet.setGameclose(gmcls);
                            matchDetailsGetSet.setPrtclr(prtclr);
                            matchDetailsGetSet.setName(nm);
                            matchDetailsGetSet.setUnique(unq);
                            matchDetailsGetSet.setFormat(frmt);
                            matchDetailsGetSet.setVenue(ven);
                            matchDetailsGetSet.setStrt(strt);
                            matchDetailsGetSet.setTeama(teama);
                            matchDetailsGetSet.setTeamb(teamb);
                            matchDetailsGetSet.setWinner(wnr);
                            matchDetailsGetSet.setStatus(sts);
                            matchDetailsGetSet.setGamename(gmnm);

                            matchDetailsGetSetList.add(matchDetailsGetSet);
                            matchids.add(matchid);

                            if(matchid.equals(matchiid)){

                                matchidrecrd.add(matchDetailsGetSet);
//                                Log.i("matchrcrd",""+matchidrecrd.size());

                            }
                        }

                        for(int j=0; j < matchidrecrd.size(); j++){
                            MatchDetailsGetSet mids = matchidrecrd.get(j);

                            if(j==0) {
                                mid1 = mids.getMid();
                            }
                            if (mid1.equals(mids.getMid().toString())) {
//                                        m.setTeamb("team_a");
                                matchlivercrd.add(mids);
                                matchstatus = mids.getStatus();
                                head = mids.getGamename();
                                head = head.replace("_", " ");
                                heading.setText("Match Win Loss");
                                title=mids.getName();
                                match.setText(mids.getName());
                                String date="";
                                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                df.setTimeZone (TimeZone.getTimeZone("IST"));
                                SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                df1.setTimeZone (TimeZone.getTimeZone ("IST"));
                                Date dt = df.parse(mids.getStrt());
                                date = df1.format(dt);
                                start.setText(date);
                                ta = mids.getTeama();
                                tb=mids.getTeamb();
//                                Log.i("matchlive",""+mid1+mids.getMid().toString());
//                                mid1="";
                            }
                            if(mids.getMid().equals("2")){
                                matchtossrcrd.add(mids);
                            }
                            if(mids.getMid().equals("3")){
                                matchfrstbalfrstinrcrd.add(mids);

                            }
                            if(mids.getMid().equals("4")){
                                matchfrstballscndinrcrd.add(mids);
                            }
                            if(mids.getMid().equals("5")){
                                matchfrstoverarcrd.add(mids);

                            }
                            if(mids.getMid().equals("6")){
                                matchfrstoverbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("7")){
                                match10overarcrd.add(mids);

                            }
                            if(mids.getMid().equals("8")){
                                match10overbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("9")){
                                matchfrstwcktarcrd.add(mids);

                            }
                            if(mids.getMid().equals("10")){
                                matchfrstwcktbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("11")){
                                matchhighopnrcrd.add(mids);

                            }
                            if(mids.getMid().equals("12")){
                                matchrace50rcrd.add(mids);

                            }
                            if(mids.getMid().equals("13")){
                                matchrunatwicktarcrd.add(mids);

                            }
                            if(mids.getMid().equals("14")){
                                matchrunatwicktbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("15")){
                                matchmake30arcrd.add(mids);

                            }
                            if(mids.getMid().equals("16")){
                                matchmake30brcrd.add(mids);

                            }
                            if(mids.getMid().equals("17")){
                                matchmake50arcrd.add(mids);

                            }
                            if(mids.getMid().equals("18")){
                                matchmake50brcrd.add(mids);

                            }
                            if(mids.getMid().equals("19")){
                                matchmake100arcrd.add(mids);

                            }
                            if(mids.getMid().equals("20")){
                                matchmake100brcrd.add(mids);

                            }
                            if(mids.getMid().equals("21")){
                                matchinnrunratearcrd.add(mids);

                            }
                            if(mids.getMid().equals("22")){
                                matchinnrunratebrcrd.add(mids);

                            }


                        }
                        listDetailsAdapter = new ListDetailsAdapter(getActivity(), matchlivercrd);
                        listDetail.setAdapter(listDetailsAdapter);
                        getActivity().startService(new Intent(getActivity(), OddsService.class));

                        if(matchstatus.equals("started")){
                            getActivity().startService(new Intent(getActivity(), UpdateService.class));
                            Log.i("score",""+getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("run", ""));
                            score.setText(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("batteam", "")+": "+getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("run", ""));
                        }
                        if(matchstatus.equals("completed")){
                            LiveScore();
                        }
                    }
//                    dbAdapter.close();

                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                if(error instanceof TimeoutError)
                {

                }
                else {

                }
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    //get lucky no if Jodi bet is placed
    private void LiveScore()
    {
        String tag_json_obj = "json_obj_req";
        final String TAG = "response";
        final String url = getString(R.string.live_score)+ScreenSlide.match_id;

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
//                            pDialog.hide();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("true")) {
                        JSONObject jsonObject = object.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("Live_Score");
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            id= object1.getString("id");
                            batteam= object1.getString("batting_team");
                            ballteam = object1.getString("bowling_team");
                            run= object1.getString("runs_str");

                        }
                        score.setText(run);
                    } else {
                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                            pDialog.hide();
            }
        }) {

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }
}
