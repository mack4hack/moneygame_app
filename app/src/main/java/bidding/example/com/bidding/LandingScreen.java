package bidding.example.com.bidding;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 01);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        startService(new Intent(getBaseContext(), timeService.class));

        Intent intent = new Intent(this, UpdateService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, pintent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.card_view1:
                    startActivity(new Intent(getApplicationContext(),Home.class));
                break;
            case R.id.card_view2:
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                break;
        }
    }


}
