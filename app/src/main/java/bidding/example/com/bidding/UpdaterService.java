package bidding.example.com.bidding;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
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

import java.util.Date;
import java.util.Timer;

/**
 * Created by root on 3/17/16.
 */
public class UpdaterService extends Service {

    Updater updater;
    BroadcastReceiver broadcaster;
    Intent intent;
    static final public String BROADCAST_ACTION = "com.pavan.broadcast";
    Timer timer;
    Thread thread;
    String id, batteam, ballteam, run;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        updater = new Updater();
        Log.d("acd", "Created");
//        showMSg("Created");

        intent = new Intent(BROADCAST_ACTION);

    }

    @Override
    public synchronized int onStartCommand(Intent intent, int flags, int startId) {

        if (!updater.isRunning()) {
            updater.start();
            Log.d("acd", "Started");
//            showMSg("Started");
            updater.isRunning = true;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();

        if (updater.isRunning) {
            updater.interrupt();
//            Log.d("acd", "Destroyed");
//            showMSg("Destroyed");
            updater.isRunning = false;
            updater = null;
        }

    }

    class Updater extends Thread {

        public boolean isRunning = false;
        public long DELAY = 120000;

        int i = 0;

        @Override
        public void run() {
            super.run();

            isRunning = true;
            while (isRunning) {
                Log.d("acd", "Running...");
                // sendbroadcast
//                sendResult(i + "");
//                i++;

                LiveScore();
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }
            } // while end
        } // run end

        public boolean isRunning() {
            return this.isRunning;
        }

    } // inner class end

    public void sendResult(String message) {
        intent.putExtra("counter", message);
        intent.putExtra("time", new Date().toLocaleString());
        sendBroadcast(intent);
    }

    public void showMSg(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


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
//                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).edit();
                        intent.putExtra("id", id);
                        intent.putExtra("batteam", batteam);
                        intent.putExtra("ballteam", ballteam);
                        intent.putExtra("run", run);
                        sendBroadcast(intent);

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

} // outer class end
