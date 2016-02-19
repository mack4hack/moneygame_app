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

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import bidding.example.com.bidding.AppDB.DbAdapter;

public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    Button more;
    ImageButton close;
    private Animation animShow, animHide;
    private SlidingPanel popup = null;
    private boolean popUpStatus = false;
    private CardView live, toss;
    ListView listView, listDetail;
    TextView heading, match, start, score;
    private static String[] GAME = {"TOSS","1ST BALL 1ST INNINGS","1ST BALL 2ND INNINGS","1ST OVER RUNS TEAM-1","1ST OVER RUNS TEAM-2","10 OVER SESSION TEAM-1","10 OVER SESSION TEAM-2","1ST WICKET METHOD TEAM-1","1ST WICKET METHOD TEAM-2","HIGHEST OPENING PARTNERSHIP","RACE TO 50","RUNS AT 1ST WICKET FALL TEAM-1","RUNS AT 1ST WICKET FALL TEAM-2","TO MAKE 50 TEAM-1","TO MAKE 50 TEAM-2","TO MAKE 100 TEAM-1","TO MAKE 100 TEAM-2","INNINGS RUN RATE TEAM-1","INNINGS RUN RATE TEAM-2"};
    private static String[] Positon1 = {"Dot ball \t\t 1.25","Wicket \t\t\t 5.5","Wide ball \t\t 2.5","No ball \t\t 4.5","1 run \t\t\t 1.5","2 runs \t\t\t 3.5","3 runs \t\t\t 5.5","4 runs \t\t\t 6.5","6 runs \t\t\t 10"};
    private static String[] Wicket = {"Caught behind \t\t xx","Caught in the field \t\t xx","LBW \t\t xx","Bowled \t\t xx","Run out \t\t\t xx","Stumped \t\t\t xx","Hit wicket \t\t xx","Retired hurt \t\t\t xx"};
    private static String[] Highest_Opening = {"Team 1\t\t 1.85","Team 2 \t\t 1.85","Tie \t\t 7"};
    private static String[] Wicket_Fall = {"0 to 10 \t\t 3","11 to 20 \t\t 3.5","21 to 30 \t\t 3.5","31 to 40 \t\t 4","41 to 50 \t\t 5"};
    private static String[] First_Over = {"0 to 1 \t\t 8","2 to 3 \t\t 7","4 to 5 \t\t 5.5","6 to 7 \t\t 5","8 to 9 \t\t 5.5"};
    private static String[] Ten_Over = {"Below 55 \t\t 4","56 to 60 \t\t 4","61 to 65 \t\t 5","66 to 70 \t\t 5","71 to 75 \t\t 5"};
    private static String[] Make_50 = {"none \t\t 6.5","1 \t\t 4.5","2 \t\t 1.9","3 \t\t 2.55","4 or more \t\t 3.5"};
    private static String[] Make_100 = {"none \t\t 2.5","1 \t\t 2","2 \t\t 4.5","3 or more \t\t 6.5"};
    private static String[] Run_Rate = {"Below 5.00 \t\t 3","5.01 to 5.5 \t\t 4","5.51 to 6.00 \t\t 3.5","6.01 to 6.5 \t\t 3.5","6.51 to 7.0 \t\t 4.5"};

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

        // Set the title view to show the page number.

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        live = (CardView) view.findViewById(R.id.live_match);
        toss = (CardView) view.findViewById(R.id.toss);
        toss.setVisibility(View.GONE);

        heading = (TextView) view.findViewById(R.id.txtheading);
        match = (TextView) view.findViewById(R.id.txtlive);
        start = (TextView) view.findViewById(R.id.txtstart);
        score = (TextView) view.findViewById(R.id.txtScore);

        listDetail = (ListView) view.findViewById(R.id.listDetail);
        listDetail.setVisibility(View.GONE);

        try {
            DbAdapter dbAdapter = new DbAdapter(getActivity());
            dbAdapter.open();
//            Log.i("page", "" + mPageNumber);
                Cursor cur = dbAdapter.GetDetails(mPageNumber+1);
//            Log.i("cursor", "" + cur.getCount());
                if (cur.getCount() > 0) {
                    cur.moveToFirst();
                    do {
                        heading.setText(cur.getString(2));
                        match.setText(cur.getString(2));
                        start.setText(cur.getString(5));
                    } while (cur.moveToNext());
                }

                dbAdapter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        initPopup(view);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(100); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        match.startAnimation(anim);

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
            more = (Button) view.findViewById(R.id.btn_more);
            close = (ImageButton) view.findViewById(R.id.cncl);
            listView = (ListView) view.findViewById(R.id.listGames);

            animShow = AnimationUtils.loadAnimation(getActivity(), R.anim.popup_show);
            animHide = AnimationUtils.loadAnimation(getActivity(), R.anim.popup_hide);

            /*more.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    popup.setVisibility(View.VISIBLE);
//                    popup.startAnimation(animShow);
                    popUpStatus = true;
                    more.setEnabled(false);
                    more.setVisibility(View.GONE);
                    close.setEnabled(true);
                    listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, GAME));
                }
            });
*/
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
//                    popup.startAnimation(animShow);
                        popUpStatus = true;
                        more.setEnabled(false);
                        more.setVisibility(View.GONE);
                        close.setVisibility(View.VISIBLE);
                        close.setEnabled(true);
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
                        heading.setText("TOSS");
                        toss.setVisibility(View.VISIBLE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.GONE);
                    } else if (position == 1) {
                        heading.setText("1ST BALL 1ST INNINGS");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Positon1));
                    } else if (position == 2) {
                        heading.setText("1ST BALL 2ND INNINGS");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Positon1));
                    } else if (position == 3) {
                        heading.setText("1ST OVER RUNS TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, First_Over));
                    } else if (position == 4) {
                        heading.setText("1ST OVER RUNS TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, First_Over));
                    } else if (position == 5) {
                        heading.setText("10 OVER SESSION TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Ten_Over));
                    } else if (position == 6) {
                        heading.setText("10 OVER SESSION TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Ten_Over));
                    } else if (position == 7) {
                        heading.setText("1ST WICKET METHOD TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Wicket));
                    } else if (position == 8) {
                        heading.setText("1ST WICKET METHOD TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Wicket));
                    } else if (position == 9) {
                        heading.setText("HIGHEST OPENING PARTNERSHIP");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Highest_Opening));
                    } else if (position == 10) {
                        heading.setText("RACE TO 50");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Highest_Opening));
                    } else if (position == 11) {
                        heading.setText("RUNS AT 1ST WICKET FALL TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Wicket_Fall));
                    } else if (position == 12) {
                        heading.setText("RUNS AT 1ST WICKET FALL TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Wicket_Fall));
                    } else if (position == 13) {
                        heading.setText("TO MAKE 50 TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Make_50));
                    } else if (position == 14) {
                        heading.setText("TO MAKE 50 TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Make_50));
                    } else if (position == 15) {
                        heading.setText("TO MAKE 100 TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Make_100));
                    } else if (position == 16) {
                        heading.setText("TO MAKE 100 TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Make_100));
                    } else if (position == 17) {
                        heading.setText("INNINGS RUN RATE TEAM-1");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Run_Rate));
                    } else if (position == 18) {
                        heading.setText("INNINGS RUN RATE TEAM-2");
                        toss.setVisibility(View.GONE);
                        live.setVisibility(View.GONE);
                        listDetail.setVisibility(View.VISIBLE);
                        listDetail.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Run_Rate));
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
//                    popup.startAnimation(animHide);
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
                        startActivity(new Intent(getActivity(), CricketPlaceBet.class));
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
}
