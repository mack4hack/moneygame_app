package bidding.example.com.bidding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PlayDetails extends AppCompatActivity {

    Button more;
    ImageButton close;
    private Animation animShow, animHide;
    private SlidingPanel popup = null;
    private boolean popUpStatus = false;
    private CardView live, toss;
    ListView listView, listDetail;
    TextView heading, match;
     private static String[] GAME = {"TOSS","1ST BALL 1ST INNINGS","1ST BALL 2ND INNINGS","1ST OVER RUNS TEAM-1","1ST OVER RUNS TEAM-2","10 OVER SESSION TEAM-1","10 OVER SESSION TEAM-2","1ST WICKET METHOD TEAM-1","1ST WICKET METHOD TEAM-2","HIGHEST OPENING PARTNERSHIP TEAM-1","RACE TO 50","RUNS AT 1ST WICKET FALL TEAM-1","RUNS AT 1ST WICKET FALL TEAM-2","TO MAKE 50 TEAM-1","TO MAKE 50 TEAM-2","TO MAKE 100 TEAM-1","TO MAKE 100 TEAM-2","INNINGS RUN RATE TEAM-1","INNINGS RUN RATE TEAM-2"};
    private static String[] Positon1 = {"Dot ball \t\t 1.25","Wicket \t\t\t 5.5","Wide ball \t\t 2.5","No ball \t\t 4.5","1 run \t\t\t 1.5","2 runs \t\t\t 3.5","3 runs \t\t\t 5.5","4 runs \t\t\t 6.5","6 runs \t\t\t 10"};
    private static String[] Wicket = {"1. Caught behind \t\t xx","2 Caught in the field \t\t xx","3 LBW \t\t xx","4 Bowled \t\t xx","5 Run out \t\t\t xx","6 Stumped \t\t\t xx","7 Hit wicket \t\t xx","8 Retired hurt \t\t\t xx"};
    private static String[] Highest_Opening = {"1 Team 1\t\t 1.85","2 Team 2 \t\t 1.85","3 Tie \t\t 7"};
    private static String[] Wicket_Fall = {"1\t 0 to 10 \t\t 3","2\t 11 to 20 \t\t 3.5","3\t 21 to 30 \t\t 3.5","4\t 31 to 40 \t\t 4","5\t 41 to 50 \t\t 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        live = (CardView) findViewById(R.id.live_match);
        toss = (CardView) findViewById(R.id.toss);
        toss.setVisibility(View.GONE);

        heading = (TextView) findViewById(R.id.txtheading);
        match = (TextView) findViewById(R.id.txtlive);

        listDetail = (ListView) findViewById(R.id.listDetail);
        listDetail.setVisibility(View.GONE);

        initPopup();

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(100); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        match.startAnimation(anim);

        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PlayDetails.this, PlayDetails.class));
            }
        });
    }

    private void initPopup() {

        try {
            popup = (SlidingPanel) findViewById(R.id.popup_window);

            animShow = AnimationUtils.loadAnimation(this, R.anim.popup_show);
            animHide = AnimationUtils.loadAnimation(this, R.anim.popup_hide);

            more = (Button) findViewById(R.id.btn_more);
            close = (ImageButton) findViewById(R.id.cncl);
            listView = (ListView) findViewById(R.id.listGames);

            more.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    popup.setVisibility(View.VISIBLE);
//                    popup.startAnimation(animShow);
                    popUpStatus = true;
                    more.setEnabled(false);
                    more.setVisibility(View.GONE);
                    close.setEnabled(true);
                    listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,GAME));
                }
            });

           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                   int pos= (int) parent.getItemAtPosition(position);
                   if(position== 0){
                       heading.setText("TOSS");
                       toss.setVisibility(View.VISIBLE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.GONE);
                   }
                   else if(position==1){
                       heading.setText("1ST BALL 1ST INNINGS");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==2){
                       heading.setText("1ST BALL 2ND INNINGS");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==3){
                       heading.setText("1ST OVER RUNS TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==4){
                       heading.setText("1ST OVER RUNS TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==5){
                       heading.setText("10 OVER SESSION TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==6){
                       heading.setText("10 OVER SESSION TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==7){
                       heading.setText("1ST WICKET METHOD TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Wicket));
                   }
                   else if(position==8){
                       heading.setText("1ST WICKET METHOD TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Wicket));
                   }
                   else if(position==9){
                       heading.setText("HIGHEST OPENING PARTNERSHIP");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Highest_Opening));
                   }
                   else if(position==10){
                       heading.setText("RACE TO 50");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Highest_Opening));
                   }
                   else if(position==11){
                       heading.setText("RUNS AT 1ST WICKET FALL TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Wicket_Fall));
                   }
                   else if(position==12){
                       heading.setText("RUNS AT 1ST WICKET FALL TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Wicket_Fall));
                   }
                   else if(position==13){
                       heading.setText("TO MAKE 50 TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==14){
                       heading.setText("TO MAKE 50 TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==15){
                       heading.setText("TO MAKE 100 TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==16){
                       heading.setText("TO MAKE 100 TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==17){
                       heading.setText("INNINGS RUN RATE TEAM-1");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==18){
                       heading.setText("INNINGS RUN RATE TEAM-2");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
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
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.setVisibility(View.GONE);
//                    popup.startAnimation(animHide);
                    popUpStatus = false;
                    close.setEnabled(false);
                    close.setVisibility(View.GONE);
                    more.setVisibility(View.VISIBLE);
                    more.setEnabled(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
