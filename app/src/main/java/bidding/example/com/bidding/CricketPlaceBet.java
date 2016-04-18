package bidding.example.com.bidding;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;

/**
 * Created by root on 2/15/16.
 */
public class CricketPlaceBet extends Activity {

    TextView odd, retrn, match, time, heading, gamenm;
    EditText bet;
    Button placebet, cancel;
    String odds, mtch, dt, headng, mid, oddid, chips, mtchid, game, result;
    Integer chipamnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_odd);

        odds = this.getIntent().getStringExtra("odd");
        mtch = this.getIntent().getStringExtra("match");
        mtchid = this.getIntent().getStringExtra("matchid");
        dt = this.getIntent().getStringExtra("date");
        headng = this.getIntent().getStringExtra("game");
        mid = this.getIntent().getStringExtra("mid");
        oddid = this.getIntent().getStringExtra("oddid");
        game = this.getIntent().getStringExtra("partclr");


        gamenm = (TextView) findViewById(R.id.textgame);
        odd = (TextView) findViewById(R.id.textOdds);
        retrn = (TextView) findViewById(R.id.textrtrn);
        match = (TextView) findViewById(R.id.textmatch);
        time = (TextView) findViewById(R.id.textdate);
        heading = (TextView) findViewById(R.id.textHeading);
        bet = (EditText) findViewById(R.id.editBet);

        placebet = (Button) findViewById(R.id.btnPlaceBet);
        cancel = (Button) findViewById(R.id.btnCancel);

        game=game.replace("_"," ");
        gamenm.setText(game);
        odd.setText(odds);
        match.setText(mtch);
        try {
            String date = "";
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            df.setTimeZone (TimeZone.getTimeZone("IST"));
            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            df1.setTimeZone (TimeZone.getTimeZone ("IST"));
            Date dte = df1.parse(dt);
            date = df1.format(dte);
            time.setText(date);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        headng = headng.replace("_"," ");
        heading.setText(headng);

        getPresentAmount();

        placebet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bet.getText().toString().equals("")) {
                    chips = bet.getText().toString().trim();
                    chipamnt = Integer.parseInt(chips);
//                    Double ret = Double.parseDouble(odds) * Double.parseDouble(chips);
//                    retrn.setText(String.valueOf(ret));
                    if (chipamnt >= 200) {
                        if (chipamnt > (int) Math.round(Double.parseDouble(result))) {
                            Toast.makeText(getApplicationContext(), "Not enough chips found", Toast.LENGTH_SHORT).show();
                        } else {
                            String tag_json_obj = "json_obj_req";
                            final String TAG = "response";
                            final String url = getString(R.string.place_cricket_bet);//+ URLEncoder.encode("/"+postString);

                            final ProgressDialog pDialog = new ProgressDialog(CricketPlaceBet.this);
                            pDialog.setMessage("Loading...");
                            pDialog.show();

                            StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                                    url, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Log.d(TAG, response.toString());
                                    pDialog.hide();
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        if (object.getBoolean("status")) {
                                            finish();
                                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();

                                            editor.putString("betmatchid", mtchid);
                                            editor.putString("latest_bet", chips);
                                            editor.putString("game_type", "2");
                                            editor.putString("betchips", bet.getText().toString().trim());
                                            editor.putString("betmid", mid);
                                            editor.putString("betoddid", oddid);
                                            editor.putString("betodds", odds);
                                            editor.putString("retrn", retrn.getText().toString().trim());
                                            editor.commit();

                                        }
                                        else {
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

                                    error.printStackTrace();
                                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                                    pDialog.hide();

                                   if(error instanceof ServerError){
                                        try {
                                            String responseBody = new String( error.networkResponse.data, "utf-8" );
                                            JSONObject jsonObject = new JSONObject( responseBody );
                                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                        } catch ( JSONException e ) {
                                            //Handle a malformed json response
                                        } catch (UnsupportedEncodingException err){

                                        }

                                    }
                                    else{
                                       Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                   }
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    HashMap<String, String> map = new HashMap<>();

                                    map.put("player_id", getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                                    map.put("match_id", ScreenSlide.match_id);
                                    map.put("m_id", mid);
                                    map.put("odds", odds);
                                    map.put("chips", bet.getText().toString().trim());
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
                    else{
                        Toast.makeText(getApplicationContext(), "Chips should be more than 200", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter chips", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.i("amt", "" + charSequence);
                if (!charSequence.toString().equals("")) {
                    Double value=(Integer.parseInt(charSequence.toString()) * Double.parseDouble(odds));
                    retrn.setText("" + String.format("%.2f",value));
                } else {
                    retrn.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String getPresentAmount()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";
            String url = getString(R.string.url_amount) + getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "");

            final ProgressDialog pDialog1 = new ProgressDialog(CricketPlaceBet.this);
            pDialog1.setMessage("Loading...");
            pDialog1.show();
            final String TAG = "login";

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    pDialog1.hide();
                    try {
                        Log.i("response", "" + response);
                        if(response != null)
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            result = jsonObject.getString("present_amount");

                        }

                    } catch (Exception e) {
                        pDialog1.hide();
                        Toast.makeText(getApplicationContext(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog1.hide();
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else if(error instanceof ServerError){
                        try {
                            String responseBody = new String( error.networkResponse.data, "utf-8" );
                            JSONObject jsonObject = new JSONObject( responseBody );
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch ( JSONException e ) {
                            //Handle a malformed json response
                        } catch (UnsupportedEncodingException err){

                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Present Chips Not Present!!!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"please check internet connection!!!",Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
