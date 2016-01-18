package bidding.example.com.bidding;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Notification_Activity extends AppCompatActivity {

    TextView timeslot, lucky_no;
    String _time, _lucky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);

        _time = this.getIntent().getStringExtra("timeslot");
        _lucky = this.getIntent().getStringExtra("lucky_number");

        timeslot = (TextView) findViewById(R.id.txttime);
        timeslot.setText(_time);

        lucky_no = (TextView) findViewById(R.id.txtluckyno);
        lucky_no.setText(_lucky);

    }

}
