package bidding.example.com.bidding;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

public class Container_score_card extends AppCompatActivity {
    TabHost tabHost;
    String teama, teamb, match_id, match_name, date, venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_score_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(Container_score_card.this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        Resources res=getResources();

        match_id=this.getIntent().getStringExtra("match_id");
        match_name=this.getIntent().getStringExtra("match_name");
        teama= this.getIntent().getStringExtra("teama");
        teamb= this.getIntent().getStringExtra("teamb");
        date= this.getIntent().getStringExtra("date");
        venue=this.getIntent().getStringExtra("venue");

        tab1.setIndicator(teama);
        tab1.setContent(new Intent(getApplicationContext(), MatchScoreCard.class).putExtra("match_id", match_id).putExtra("match_name", match_name).putExtra("date",date).putExtra("venue",venue));

        tab2.setIndicator(teamb);;
        tab2.setContent(new Intent(getApplicationContext(), MatchScoreTeamb.class).putExtra("match_id",match_id).putExtra("match_name", match_name).putExtra("date",date).putExtra("venue",venue));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);


    }

}
