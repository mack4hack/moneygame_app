package bidding.example.com.bidding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import bidding.example.com.bidding.Adapter.MatchListAdapter;

public class UpcomingList extends AppCompatActivity {

    ListView matches;
    Button btnok;
    MatchListAdapter listMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_list);

        matches = (ListView) findViewById(R.id.listmatch);
        btnok = (Button) findViewById(R.id.btnOk);

        listMatch = new MatchListAdapter(getApplicationContext(), UpcomingMatches.matchListUpcoming);
        matches.setAdapter(listMatch);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
