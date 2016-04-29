package bidding.example.com.bidding;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import bidding.example.com.bidding.AppDB.DbAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;

/**
 * Created by Sandesh on 28-Nov-15.
 */
public class timeService extends Service
{

    Thread thread;
    Timer timer;
    DbAdapter dbAdapter;
    String matchid, matchname, matchformat, matchvenue, matchstart, matchwinner, matchstatus;
    public static final String TIME_SERVER = "time-a.nist.gov";
    CounterClass counterClass;
    public static int flag=0;
    ConnectionDetector connectionDetector;
    String session,currenttime;
    long sec;
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
//                    Date Date2 = sdf.parse(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("time", ""));


                  /*      NTPUDPClient timeClient = new NTPUDPClient();
                        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
                        TimeInfo timeInfo = timeClient.getTime(inetAddress);
                        //long returnTime = timeInfo.getReturnTime();   //local device time
                        long returnTime = timeInfo.getMessage().getReceiveTimeStamp().getTime();   //server time

                        Date time = new Date(returnTime);
                        Log.d("time", "Time from " + TIME_SERVER + ": " + time);*/


                     connectionDetector = new ConnectionDetector(getApplicationContext());
                    if(flag==0){
                        CurrentResult();
//                        counterClass.start();
                        flag=1;
                    }

/*
                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                    editor.putInt("currentMinute", m1);
                    editor.putInt("currentSecond",s1);
                    editor.commit();*/
                    if (getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) == 14 &&
                            getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) == 40) {
                        LuckyNo();
                    }
                    int h= Date1.getHours();
                    int m= Date1.getMinutes();
                    int s= Date1.getSeconds();
//                    Log.i("hour",""+h);
                    if (h==10 && m==56 && s==0){
//                        getMatchDetails();
                    }
                }catch (Exception e)
                {
                    LuckyNo();
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

        timer.schedule(timerTask, 5000, 1000);

        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private void CurrentResult()
    {
        if(connectionDetector.isConnectingToInternet())
        {
            //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
            String tag_json_obj = "json_obj_req";
            final String TAG = "response";
            final String url = getString(R.string.get_current_result);//+ URLEncoder.encode("/"+postString);

            //          final ProgressDialog pDialog = new ProgressDialog(getActivity());
            //pDialog.setMessage("Loading...");
            //pDialog.show();

            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    //      pDialog.hide();
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("status").equals("true"))
                        {
                            JSONObject innerObject = object.getJSONObject("data");
//                            JSONObject obj= innerObject.getJSONObject("lucky_number");
                            innerObject.getString("start");
                            session=  innerObject.getString("end");
                            String time=innerObject.getString("current");
                            String split[]=time.split(" ");
                            String split1[]=split[1].split(" ");
                            String splithrs[]=split1[0].split(":");
                            Log.i("min", "" + splithrs[1]);


                            int m1=Integer.parseInt(splithrs[1]);
                            int s1=Integer.parseInt(splithrs[2]);
                            if ( m1>= 45) {
                                m1 = 59 - m1;
                            } else if (m1 >= 30) {
                                m1 = 44 - m1;
                            } else if (m1 >= 15) {
                                m1 = 29 - m1;
                            } else if (m1 < 15) {
                                m1 = 14 - m1;
                            }

                            if (s1 == 60) {
                                s1 = 00;
                            } else if (s1 == 0) {
                                s1 = 60;
                            } else if (s1 > 0 && s1 < 60) {
                                s1 = 60 - s1;
                            }

                            currenttime = String.valueOf(m1)+":"+splithrs[2];
                            Log.i("current time",""+currenttime);

                            long timelong=((m1*60)+s1)*1000;

                            sec=timelong;
                            counterClass = new CounterClass(sec,1000);
                            counterClass.start();


                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(getApplicationContext(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                    VolleyLog.d("CUrrent Result", "Error: " + error.getMessage());
                    //    pDialog.hide();
                }
            }) ;

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
            AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
        else
        {
            Toast.makeText(getApplicationContext(),getString(R.string.internet_err),Toast.LENGTH_SHORT).show();
        }

    }
    private void LuckyNo()
    {

        String tag_json_obj = "json_obj_req";
        final String TAG = "response";
        final String url = getString(R.string.get_result);//+ URLEncoder.encode("/"+postString);
        Log.i("url", "" + url);
        //          final ProgressDialog pDialog = new ProgressDialog(getActivity());
        //pDialog.setMessage("Loading...");
        //pDialog.show();

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                //      pDialog.hide();
                try {
                    JSONObject object = new JSONObject(response);
                    int status = 0;
                    if(object.getString("status").equals("true"))
                    {

                        // Prepare intent which is triggered if the
                        // notification is selected
//                                JSONObject jsonObject = object.getJSONObject("data");
                        Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);
                        intent.putExtra("lucky_number",object.getString("lucky_number"));
                        intent.putExtra("timeslot", object.getString("draw_time"));
//                                intent.putExtra("timeslot",object.getString("end"));
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                        // Build notification
                        // Actions are just fake
                        Notification noti = new NotificationCompat.Builder(getApplicationContext())
                                .setContentTitle("Lucky Number " + object.getString("lucky_number") + " for "+object.getString("draw_time"))
                                .setContentText("").setSmallIcon(R.drawable.rsz_new_icon)
                                .setContentIntent(pIntent).build();
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        // hide the notification after its selected
                        noti.flags |= Notification.FLAG_AUTO_CANCEL;

                        notificationManager.notify(0, noti);

                        if(status == 0)
                        {

                        }

                    }
                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError)
                {

                }
                else {

                }
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //    pDialog.hide();
            }
        }) ;
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void getMatchDetails(){
        String tag_json_obj = "json_obj_req";
        final String TAG = "response";
        final String url = getString(R.string.get_match_details);
        Log.i("url", "" + url);

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                dbAdapter= new DbAdapter(getApplicationContext());
                dbAdapter.open();
                try {
                    JSONObject object = new JSONObject(response);
                    int status = 0;
                    if(object.getString("status").equals("true"))
                    {
                        JSONObject jsonObject = object.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.names();
                        for(int i=0; i<jsonObject.length(); i++){
                            JSONObject object1 = jsonObject.getJSONObject(jsonArray.getString(i));
                            matchid = object1.getString("id");
                            matchname = object1.getString("s_name");
                            matchformat = object1.getString("format");
                            matchvenue = object1.getString("venue");
                            String time = object1.getString("start_date");
                            time = time.replace("T", " ");
                            time = time.replace("-", "/");
                            String [] split = time.split("\\u002B");
                            matchstart =split[0];
                            matchwinner = object1.getString("winner_team");
                            matchstatus = object1.getString("status");
                            dbAdapter.InsertDetails(matchid, matchname, matchformat, matchvenue, matchstart, matchwinner, matchstatus);
                        }

                    }
                    dbAdapter.close();

                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError)
                {

                }
                else {

                }
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) ;
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public class CounterClass extends CountDownTimer
    {
        String hms;
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            try {

                int min=(int)TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - (int)TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                int sec=(int)TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - (int)TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                editor.putInt("currentMinute", min);
                editor.putInt("currentSecond",sec);
                editor.commit();
//                mTimer.setText("" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) + ":" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0));

               /* if (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) == 14 &&
                        (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) >= 51 &&
                                getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) <= 60)) {
                    countDown(mCurrentResult, 14);
                }*/

                if(min == 00 &&
                        sec == 01 ){

                    sec=900000;
                    counterClass = new CounterClass(sec,1000);
                    counterClass.start();

                }

                if (min == 14 && sec== 50 && sec >= 40) {
                    LuckyNo();
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {

            this.start();
        }
    }
}
