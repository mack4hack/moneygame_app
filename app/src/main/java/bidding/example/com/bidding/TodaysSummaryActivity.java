package bidding.example.com.bidding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bidding.example.com.bidding.Adapter.TodaysSummaryAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.HistoryGetSet;

/**
 * Created by root on 4/28/16.
 */
public class TodaysSummaryActivity extends AppCompatActivity {
    private ListView mTodaysSummary;
    TextView totAmnt, totRslt;
    private List<HistoryGetSet> todaySummary = new ArrayList<>();
    private TodaysSummaryAdapter todaysHistoryAdapter;
    String data="", date1;
    Double ttlAmnt=0.0, ttlResult=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_todays_summary);


        mTodaysSummary = (ListView) findViewById(R.id.today_summary);
        totAmnt = (TextView) findViewById(R.id.ttlAmnt);
        totRslt = (TextView) findViewById(R.id.ttlReslt);


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());

        try {
            data = getIntent().getStringExtra("date");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(!data.equals("")){
            String dte= getIntent().getStringExtra("date");
            String date="";
            try {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                Date dt = df1.parse(dte);
                date = df.format(dt);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            date1=date;
            getDetails(getString(R.string.get_acc_by_date) +getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id","") + "&date=" +date);
        }
        else{
            date1=formattedDate;
            String url=getString(R.string.get_acc_by_date) + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "") + "&date=" + formattedDate;
            getDetails(getString(R.string.get_acc_by_date) + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "") + "&date=" + formattedDate);
            Log.i("url", "" + url);
        }


        mTodaysSummary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistoryGetSet item = todaySummary.get(i);
                //item.getTimeSlotId();
                Log.i("userid",""+item.getTimeSlotId()+" id "+getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                Intent intent = new Intent(TodaysSummaryActivity.this,TansactionWiseHistory.class);
                intent.putExtra("date",date1);
                intent.putExtra("timeslot",item.getTimeSlotId());
                startActivity(intent);
            }
        });
    }

    private void getDetails(String url)
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(TodaysSummaryActivity.this);
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";

            final ProgressDialog pDialog = new ProgressDialog(TodaysSummaryActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            final String TAG = "login";
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    pDialog.hide();
                    try {
                        Log.i("response", "" + response);
                        if(response != null)
                        {

                            JSONObject object = new JSONObject(response);
                            object.getString("status");
                            if(object.getString("status").equals("true"))
                            {
                                JSONObject innerObject = object.getJSONObject("data");
                                JSONArray jsonArray = innerObject.getJSONArray("data_weekly");

                                if(jsonArray.length()!=0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject childObject = jsonArray.getJSONObject(i);
                                        HistoryGetSet item = new HistoryGetSet();

                                        if (childObject.getString("bet_amount").equals("null")) {
//                                            item.setAmount("0");
                                        } else {
                                            item.setTransactionNo(childObject.getString("balance"));
                                            item.setTime(childObject.getString("draw_time"));
                                            item.setTimeSlotId(childObject.getString("timeslot_id"));
                                            item.setAmount(childObject.getString("bet_amount"));
                                            item.setResult(childObject.getString("payout"));
                                            todaySummary.add(item);
                                            String amnt = childObject.getString("bet_amount");
                                            if (amnt.contains(",")) {
                                                amnt = amnt.replace(",", "");
                                            }
                                            ttlAmnt += Double.parseDouble(amnt);
                                            if(!childObject.getString("payout").equals("null")) {
                                                String rslt = childObject.getString("payout");
                                                if (rslt.contains(",")) {
                                                    rslt = rslt.replace(",", "");
                                                }
                                                ttlResult += Double.parseDouble(rslt);
                                            }
                                        }

                                    }

                                    todaysHistoryAdapter = new TodaysSummaryAdapter(getApplicationContext(), todaySummary);
                                    mTodaysSummary.setAdapter(todaysHistoryAdapter);
                                    totAmnt.setText(""+ttlAmnt);
                                    totRslt.setText(""+ttlResult);
                                }else
                                {
                                    Toast.makeText(getApplicationContext(), "No transaction present to display", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    } catch (Exception e) {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.hide();
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "History Not Present!!!", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                }
            });
            strReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

// Adding request to request queue
            AppControler.getInstance().addToRequestQueue(strReq, tag_string_req);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please check internet connection!!!",Toast.LENGTH_SHORT).show();
        }
    }



}


