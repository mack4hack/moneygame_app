package bidding.example.com.bidding;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2/15/16.
 */
public class CricketPlaceBet extends Activity {

    TextView odd, retrn, match, time, heading;
    EditText bet;
    Button placebet, cancel;
    String odds, mtch, dt, headng, mid, oddid, chips;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_odd);

        odds = this.getIntent().getStringExtra("odd");
        mtch = this.getIntent().getStringExtra("match");
        dt = this.getIntent().getStringExtra("date");
        headng = this.getIntent().getStringExtra("game");
        mid = this.getIntent().getStringExtra("mid");
        oddid = this.getIntent().getStringExtra("oddid");


        odd = (TextView) findViewById(R.id.textOdds);
        retrn = (TextView) findViewById(R.id.textrtrn);
        match = (TextView) findViewById(R.id.textmatch);
        time = (TextView) findViewById(R.id.textdate);
        heading = (TextView) findViewById(R.id.textHeading);
        bet = (EditText) findViewById(R.id.editBet);

        placebet = (Button) findViewById(R.id.btnPlaceBet);
        cancel = (Button) findViewById(R.id.btnCancel);

        odd.setText(odds);
        match.setText(mtch);
        time.setText(dt);
        heading.setText(headng);



        placebet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bet.getText().toString().equals("")) {
                    chips=bet.getText().toString();
                    Double ret = Double.parseDouble(odds)*Double.parseDouble(chips);
                    retrn.setText(String.valueOf(ret));
                    String tag_json_obj = "json_obj_req";
                    final String TAG = "response";
                    final String url = getString(R.string.place_cricket_bet);//+ URLEncoder.encode("/"+postString);

//                    final ProgressDialog pDialog = new ProgressDialog(getApplicationContext());
//                    pDialog.setMessage("Loading...");
//                    pDialog.show();

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

                            map.put("player_id", getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                            map.put("match_id", ScreenSlide.match_id);
                            map.put("m_id", mid);
                            map.put("odds", odds);
                            map.put("chips", bet.getText().toString());
                            map.put("odd_id", oddid);

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
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
