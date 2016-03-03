package bidding.example.com.bidding;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 1/18/16.
 */
public class UpdateService extends Service {

    Timer timer;
    Thread thread;
    String id, batteam, ballteam, run;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        timer= new Timer();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED)
                    {
                        Log.i("service", "start");
                        LiveScore();

                    }

                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Log.i("Timer Check", "Timer Task Checkk");
                    if (!thread.isAlive()) {

                        Log.i("Thread is sleeped ", "Thread Started");
                        //  thread.interrupt();;
                        thread.run();
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        timer.schedule(timerTask, 1000, 60000);


        return START_STICKY;
    }


    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i("Low Memory", "Low Memory");
        try {
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //get lucky no if Jodi bet is placed
    private void LiveScore()
    {
        String tag_json_obj = "json_obj_req";
        final String TAG = "response";
        final String url = getString(R.string.live_score)+ScreenSlide.match_id;

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
//                            pDialog.hide();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("true")) {
                      JSONObject jsonObject = object.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("Live_Score");
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            id= object1.getString("id");
                            batteam= object1.getString("batting_team");
                            ballteam = object1.getString("bowling_team");
                            run= object1.getString("runs_str");

                        }
                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).edit();
                        editor.putString("id", id);
                        editor.putString("batteam", batteam);
                        editor.putString("ballteam", ballteam);
                        editor.putString("run", run);
                        editor.commit();

                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                            pDialog.hide();
            }
        }) {

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
