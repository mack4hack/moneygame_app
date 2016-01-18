package bidding.example.com.bidding;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

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
                        jodiLuckyNo();

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

        timer.schedule(timerTask, 1000, 15 * 60000);


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
    private void jodiLuckyNo()
    {

//                            Toast.makeText(getActivity(),"Api Call For Third",Toast.LENGTH_SHORT).show();


                //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
                String tag_json_obj = "json_obj_req";
                final String TAG = "response";
                final String url = getString(R.string.get_result);//+ URLEncoder.encode("/"+postString);

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
                            int status = 0;
                            if(object.getString("status").equals("true"))
                            {
                                /*for(int i=0;i<number3.length;i++)
                                {
                                    if(number3[i]!=null)
                                    {
                                        if(Integer.parseInt(number3[i]) == Integer.parseInt(object.getString("lucky_number")))
                                        {
//                                            status = 1;
                                            Toast.makeText(getActivity(), "Congratulation, You Win The Bet.", Toast.LENGTH_SHORT).show();
                                            try {
//                                                double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number3[i]) * 85);
//                                                balanceStatus.setProgress((int) result);
                                            }catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }

                                            break;
                                        }

                                    }
                                }*/
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

}
