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
     private static String[] GAME = {"TOSS","1ST BALL 1ST INNINGS","1ST BALL 2ND INNINGS","1ST OVER RUNS TEAM-1","1ST OVER RUNS TEAM-2","10 OVER SESSION TEAM-1","10 OVER SESSION TEAM-2","1ST WICKET METHOD TEAM-1","1ST WICKET METHOD TEAM-2","HIGHEST OPENING PARTNERSHIP TEAM-1","HIGHEST OPENING PARTNERSHIP TEAM-2","RUNS AT 1ST WICKET FALL TEAM-1","RUNS AT 1ST WICKET FALL TEAM-2","TO MAKE 50 TEAM-1","TO MAKE 50 TEAM-2","TO MAKE 100 TEAM-1","TO MAKE 100 TEAM-2","INNINGS RUN RATE TEAM-1","INNINGS RUN RATE TEAM-2"};
    private static String[] Positon1 = {"Dot ball \t\t 1.25","Wicket \t\t\t 5.5","Wide ball \t\t 2.5","No ball \t\t 4.5","1 run \t\t\t 1.5","2 runs \t\t\t 3.5","3 runs \t\t\t 5.5","4 runs \t\t\t 6.5","6 runs \t\t\t 10"};

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
                    popup.startAnimation(animShow);
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
                       heading.setText("1ST BALL 1ST INNINGS/ 2ND INNINGS");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   else if(position==2){
                       heading.setText("1ST BALL 1ST INNINGS/ 2ND INNINGS");
                       toss.setVisibility(View.GONE);
                       live.setVisibility(View.GONE );
                       listDetail.setVisibility(View.VISIBLE);
                       listDetail.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Positon1));
                   }
                   popup.setVisibility(View.GONE);
                   popup.startAnimation(animHide);
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
                    popup.startAnimation(animHide);
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
