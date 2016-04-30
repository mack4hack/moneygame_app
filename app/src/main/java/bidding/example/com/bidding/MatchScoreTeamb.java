package bidding.example.com.bidding;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import bidding.example.com.bidding.Adapter.ScoreCardAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.ScoreCardGetSet;

/**
 * Created by root on 3/11/16.
 */
public class MatchScoreTeamb  extends Activity {

    String match_id, match_name, date, venue, tot, win;
    TextView matchnm, tdate, tvenue, total, winner;
    ListView lbatsman, lbowler, lwickets;
    ConnectionDetector connectionDetector;
    ScoreCardAdapter adapter;
    private List<ScoreCardGetSet> matchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_score_card);

        connectionDetector=new ConnectionDetector(getApplicationContext());

        match_id=this.getIntent().getStringExtra("match_id");
        match_name=this.getIntent().getStringExtra("match_name");
        date= this.getIntent().getStringExtra("date");
        venue=this.getIntent().getStringExtra("venue");
        tot=this.getIntent().getStringExtra("runb");
        win=this.getIntent().getStringExtra("win");

        matchnm = (TextView) findViewById(R.id.textteam);
        matchnm.setText(match_name);
        tdate = (TextView) findViewById(R.id.textdate);
        tdate.setText(date);
        tvenue = (TextView) findViewById(R.id.textvenue);
        tvenue.setText(venue);
        total = (TextView) findViewById(R.id.textrun);
        total.setText(tot);
        winner = (TextView) findViewById(R.id.textwin);
        winner.setText("Winner : "+win);

        lbatsman = (ListView) findViewById(R.id.listbatsman);
//        lbowler = (ListView) findViewById(R.id.listbowler);
//        lwickets = (ListView) findViewById(R.id.listwickets);

        getScoreCard();

    }

    @Override
    public void onBackPressed(){
        //Your code here
        super.onBackPressed();
    }

    private void getScoreCard()
    {
        if(connectionDetector.isConnectingToInternet())
        {

            String tag_json_obj = "json_obj_req";
            final String TAG = "response";
            final String url = getString(R.string.score_card)+match_id;//+ URLEncoder.encode("/"+postString);

            final ProgressDialog pDialog = new ProgressDialog(MatchScoreTeamb.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    pDialog.hide();
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("status").equals("true"))
                        {
                            JSONObject jsonObject = object.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("Live_Score_Team2");
                            if(jsonArray.length()!=0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject childObject = jsonArray.getJSONObject(i);
                                    ScoreCardGetSet item = new ScoreCardGetSet();
                                    item.setPlayer_id(childObject.getString("player_id"));
                                    item.setPlayer_name(childObject.getString("player_name"));
                                    item.setBalls(childObject.getString("player_balls"));
                                    item.setRuns(childObject.getString("player_runs"));
                                    item.setStrikerate(childObject.getString("player_strike_rate"));
                                    matchList.add(item);
                                }
                            }

                            adapter = new ScoreCardAdapter(getApplicationContext(), matchList);
                            lbatsman.setAdapter(adapter);

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
                    pDialog.hide();
                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(getApplicationContext(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                    VolleyLog.d("Current Result", "Error: " + error.getMessage());
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

}

