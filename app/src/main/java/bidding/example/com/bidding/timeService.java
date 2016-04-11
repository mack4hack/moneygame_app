package bidding.example.com.bidding;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

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

import bidding.example.com.bidding.AppDB.DbAdapter;

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

}
