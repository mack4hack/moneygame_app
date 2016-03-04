package bidding.example.com.bidding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(63, 81, 181)));
            getActionBar().hide();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_splash);

        Thread thread=new Thread(){
            public void run()
            {
                try {
                    sleep(5 * 1000);
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}