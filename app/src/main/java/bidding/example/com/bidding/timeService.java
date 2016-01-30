package bidding.example.com.bidding;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sandesh on 28-Nov-15.
 */
public class timeService extends Service
{

    Thread thread;
    Timer timer;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");


                    Date systemDate = Calendar.getInstance().getTime();
                    String myDate = sdf.format(systemDate);
//                  txtCurrentTime.setText(myDate);

                    Date Date1 = sdf.parse(myDate);
                    Date Date2 = sdf.parse(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("time", ""));
                    long diff = Date1.getTime() - Date2.getTime();

                    int Hours = (int) (diff/(1000 * 60 * 60));
                    int Mins = (int) (diff/(1000*60)) % 60;
                    long Secs = (int) (diff / 1000) % 60;

                    long dd=diff/1000;
                    int secondDiff = (int) (dd % 60);

                /*    Calendar c = Calendar.getInstance();
                    c.add(Calendar.HOUR,-Hours);
                    c.add(Calendar.MINUTE,-Mins);
                    c.add(Calendar.SECOND,-secondDiff);
                    Log.i("diff",""+c.getTime());*/

                    int h1 = Date1.getHours();
                    int m1 = Date1.getMinutes();
                    int s1 = Date1.getSeconds();


                    if (h1 == 0) {
                        h1 = 12;
                    } else if (h1 > 12) {
                        h1 = h1 - 12;

                    }
                    if (h1 < 10) {
                        h1 = Integer.parseInt("0" + h1);
                    }
		/*if (m < 10) {
			m = "0" + m;
		}*/
                    if (m1 >= 45) {
                        m1 = 59 - m1;
                    } else if (m1 >= 30) {
                        m1 = 44 - m1;
                    } else if (m1 >= 15) {
                        m1 = 29 - m1;
                    } else if (m1 < 15) {
                        m1 = 14 - m1;
                    }
                    if (m1 < 10) {
                        m1 = Integer.parseInt("0" + m1);
                    }

                    if (s1 == 60) {
                        s1 = 00;
                    } else if (s1 == 0) {
                        s1 = 60;
                    } else if (s1 > 0 && s1 < 60) {
                        s1 = 60 - s1;
                    }
                    if (s1 < 10) {
                        s1 = Integer.parseInt("0" + s1);
                    }
                    /*int diff = getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getInt("min", 0);
                    if(diff>0) {
                        if(m1 > diff) {
                            Log.i("Current Time with diff ", "" + h1 + ":" + (m1 - diff) + ":" + s1);
                        }else
                        {
                            Log.i("Current Time with diff ", "" + h1 + ":" + m1 + ":" + s1);
                        }
                    }
                    else
                    {*/
                        //Log.i("Current Time", "" + h1 + ":" + m1  + ":" + s1);
                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).edit();
                    editor.putInt("currentMinute",m1);
                    editor.putInt("currentSecond",s1);
                    editor.commit();
                    //}
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!thread.isAlive())
                {
                    thread.run();
                }
                else
                {
                    thread.start();
                }
            }
        };
        timerTask.scheduledExecutionTime();


        timer.schedule(timerTask,5000,1000);

        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


}
