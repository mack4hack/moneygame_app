package bidding.example.com.bidding;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 1/18/16.
 */
public class UpdateService extends Service {

    Timer timer;
    Thread thread;

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
        final String url = getString(R.string.live_score);

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
//                            pDialog.hide();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("true")) {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();

                map.put("match_id", ScreenSlide.match_id);

                for (String key : map.keySet()) {
                    Log.i("vales", "key = " + key + " val = " + map.get(key));
                }
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
