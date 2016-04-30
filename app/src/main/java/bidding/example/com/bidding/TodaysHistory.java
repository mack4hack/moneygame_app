package bidding.example.com.bidding;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bidding.example.com.bidding.Adapter.HistoryAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.HistoryGetSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysHistory extends Activity {

    ListView listView;
    TextView total_bets, winnings, profit_loss;
    int bet=0, win=0, prftlos=0;
    List<HistoryGetSet> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_todays_history);

        listView = (ListView) findViewById(R.id.list);
        total_bets = (TextView) findViewById(R.id.txttotalbets);
        winnings = (TextView) findViewById(R.id.txtwinnings);
        profit_loss = (TextView) findViewById(R.id.txtprftlos);

        getHistory();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistoryGetSet item = historyList.get(i);
                //item.getTimeSlotId();
                startActivity(new Intent(TodaysHistory.this, TodaysSummaryActivity.class).putExtra("date", item.getDate()));

            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }

    private void getHistory()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(TodaysHistory.this);
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance(Locale.GERMANY);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);

            int sunday=calendar.get(Calendar.DAY_OF_WEEK);
            String[] days = new String[7];
            if (sunday==Calendar.SUNDAY){
                calendar.add(Calendar.DAY_OF_MONTH,-6);
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            for (int i = 0; i < 7; i++)
            {
                days[i] = format.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            String week= days[0]+"%20To%20"+days[6];
            try {

                String url = getString(R.string.get_history_by_week) + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "")+"&week="+week;
                Log.i("url", "" + url);
                final ProgressDialog pDialog = new ProgressDialog(TodaysHistory.this);
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
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("status").equals("true"))
                                {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for(int i=0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject item = jsonArray.getJSONObject(i);
                                        int pl=0;
                                        HistoryGetSet rowItem = new HistoryGetSet();
                                        rowItem.setAmount(item.getString("bet_amount"));
                                        rowItem.setDate(item.getString("date"));
                                        rowItem.setTotal_bet(item.getString("total_bet"));
                                        rowItem.setTotal_wins(item.getString("total_wins"));
                                        rowItem.setPayout(item.getString("payout"));

                                        String wins = item.getString("payout");
                                        wins=wins.replace(",","");
                                        String bets= item.getString("bet_amount");
                                        bets=bets.replace(",","");
                                        if(wins.equals("null")) {
                                            wins="0";
                                        }
                                        pl = (int) Math.round(Double.parseDouble(wins)) - (int) Math.round(Double.parseDouble(bets));
                                        rowItem.setProftlos(String.valueOf(pl));
                                        String ttlbet =item.getString("bet_amount");
                                        ttlbet = ttlbet.replace(",","");
                                        if(ttlbet.equals("null")){
                                            ttlbet="0";
                                        }
                                        bet+= (int)Math.round(Double.parseDouble(ttlbet));
                                        String ttlwin =wins;
                                        ttlwin = ttlwin.replace(",","");
                                        if(ttlwin.equals("null")){
                                            ttlwin="0";
                                        }
                                        win+= (int)Math.round(Double.parseDouble(ttlwin));
                                        prftlos+= pl;


                                        historyList.add(rowItem);

                                    }
                                    HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(),historyList);
                                    listView.setAdapter(adapter);
                                    total_bets.setText(""+bet);
                                    winnings.setText(""+win);
                                    profit_loss.setText("" + prftlos);

                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"something went wrong, please try again!!!",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "History for Current Week Not Present!!!", Toast.LENGTH_SHORT).show();
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
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"please check internet connetion!!!",Toast.LENGTH_SHORT).show();
        }
    }

}
