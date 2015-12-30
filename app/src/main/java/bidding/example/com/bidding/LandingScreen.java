package bidding.example.com.bidding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LandingScreen extends AppCompatActivity implements View.OnClickListener {

    CardView gameOne,gameTwo;
    TextView gameLotteryTotalPlayed,gameCricketTotalPlayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameOne = (CardView) findViewById(R.id.card_view1);
        gameTwo = (CardView) findViewById(R.id.card_view2);

        gameLotteryTotalPlayed = (TextView) findViewById(R.id.total_lottery_bet);
        gameCricketTotalPlayed = (TextView) findViewById(R.id.total_cricket_bet);

        gameOne.setOnClickListener(this);
        gameTwo.setOnClickListener(this);

        gameLotteryTotalPlayed.setText("Total Game Played : "+getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getString("total_game",""));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.card_view1:
                    startActivity(new Intent(getApplicationContext(),Home.class));
                break;
            case R.id.card_view2:
                Toast.makeText(getApplicationContext(),"Game-2",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
