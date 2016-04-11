package bidding.example.com.bidding;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import bidding.example.com.bidding.Adapter.ListDetailsAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.MatchDetailsGetSet;

public class RedsultDetails extends AppCompatActivity {

    String id, match_name, date;
    TextView tMatch, tDate;
    ListView gameResult;
    private ConnectionDetector connectionDetector;
    private ListDetailsAdapter matchListAdapter;
    private List<MatchDetailsGetSet> matchListGetSets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redsult_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectionDetector=new ConnectionDetector(getApplicationContext());

        id= this.getIntent().getStringExtra("id");
        match_name= this.getIntent().getStringExtra("s_name");
        date= this.getIntent().getStringExtra("date");

        tMatch=(TextView) findViewById(R.id.txtmatch);
        tDate=(TextView) findViewById(R.id.txtstart);
        gameResult=(ListView) findViewById(R.id.listDetail);

        tMatch.setText(match_name);
        tDate.setText(date);

        getResultList();


    }


    private void getResultList()
    {
        if(connectionDetector.isConnectingToInternet())
        {

            String tag_json_obj = "json_obj_req";
            final String TAG = "response";
            final String url = getString(R.string.match_result)+id;//+ URLEncoder.encode("/"+postString);

            final ProgressDialog pDialog = new ProgressDialog(RedsultDetails.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    pDialog.hide();
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("status").equals("true"))
                        {
                            JSONObject innerObject = object.getJSONObject("data");
                            Log.d(TAG, innerObject.toString());
                            JSONObject jsonObject = innerObject.getJSONObject("match_result");
                            matchListGetSets = new ArrayList<>();
                            JSONArray jArray= jsonObject.names();
                            if(jArray.length()!=0) {
                                for(int i=0;i<jsonObject.length();i++) {
                                    MatchDetailsGetSet item = new MatchDetailsGetSet();

                                    item.setPrtclr(jArray.getString(i));
                                    item.setOdd(jsonObject.getString(jArray.getString(i)));
                                    matchListGetSets.add(item);
                                }
                                matchListAdapter = new ListDetailsAdapter(getApplicationContext(), matchListGetSets);
                                gameResult.setAdapter(matchListAdapter);
                            }

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


}
