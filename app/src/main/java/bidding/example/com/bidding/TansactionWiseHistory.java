package bidding.example.com.bidding;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.List;

import bidding.example.com.bidding.Adapter.TimeSlotAdapter;
import bidding.example.com.bidding.Adapter.TransacionDetailsAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.HistoryGetSet;
import bidding.example.com.bidding.GetterSetter.TransactionDetailsGetSet;

public class TansactionWiseHistory extends AppCompatActivity {

    private ListView mList;
    private List<HistoryGetSet> list = new ArrayList<>();
    private TimeSlotAdapter adapter;
    List<TransactionDetailsGetSet> transList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tansaction_wise_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mList= (ListView) findViewById(R.id.transactionHistory);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());

        getTransactionDetails(getString(R.string.get_transaction) +getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "") + "&date="+formattedDate+"&draw_time=" + getIntent().getStringExtra("timeslot"));

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistoryGetSet item = list.get(i);
                getDetails(getString(R.string.get_specific_transaction)+item.getTransactionNo());
            }
        });
    }


    private void showDetails(List<TransactionDetailsGetSet> List,String tno,String dTime,String tTime,String result,String whichBet,String payout)
    {
// custom dialog
        final Dialog dialog = new Dialog(TansactionWiseHistory.this);
        dialog.setContentView(R.layout.transaction_details_custom_layout);
        dialog.setTitle("Transaction Details");


        TextView mDigit,mChip,mTransNo,mDrawTime,mTransTime,mResult,mTotal,mBetText,mWin,mLoss;
        ListView mListView;

//        mDigit = (TextView) dialog.findViewById(R.id.digit);
  //      mChip = (TextView) dialog.findViewById(R.id.chip);
        mTransNo = (TextView) dialog.findViewById(R.id.trans_id);
        mDrawTime = (TextView) dialog.findViewById(R.id.draw_time);
        mTransTime = (TextView) dialog.findViewById(R.id.trans_time);
        mResult = (TextView) dialog.findViewById(R.id.result);
        mTotal =  (TextView) dialog.findViewById(R.id.total);
        mBetText = (TextView) dialog.findViewById(R.id.betText);
        mWin = (TextView) dialog.findViewById(R.id.win);
        mLoss = (TextView) dialog.findViewById(R.id.loss);

        mListView = (ListView) dialog.findViewById(R.id.list);

        TransacionDetailsAdapter adapter = new TransacionDetailsAdapter(getApplicationContext(),List);
        mListView.setAdapter(adapter);
        /*mDigit.setText(digit);
        mChip.setText(chip);*/
        mTransNo.setText(tno);
        mDrawTime.setText(dTime);
        mTransTime.setText(tTime);
        mResult.setText(result);

        int chip=0;
        for(TransactionDetailsGetSet item:transList)
        {
            chip+=Integer.parseInt(item.getChip());
        }
        mTotal.setText(""+chip);

        if(whichBet.equals("Single Digit Second")) {
            mBetText.setText("Single Digit Second");
            //double amt = Integer.parseInt(Chip)*8.5;

        }
        else if(whichBet.equals("Single Digit First"))
        {
            mBetText.setText("Single Digit First");
            //double amt = Integer.parseInt(mChip.getText().toString()) * 8.5;
        //    mTotal.setText(mChip.getText().toString());
        }

        if(payout.equals("0"))
        {
            mWin.setText(payout);
            mLoss.setText(mTotal.getText().toString());
        }
        else
        {
            mWin.setText(mTotal.getText().toString());
            mLoss.setText("0");
        }

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }*/
        dialog.show();
    }
    private void getTransactionDetails(String url)
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";

            Log.i("url",url);
            final ProgressDialog pDialog = new ProgressDialog(TansactionWiseHistory.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            final String TAG = "login";
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    pDialog.hide();
                    try
                    {
                        Log.i("res",""+response);
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("status").equals("true")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = object.getJSONArray("data_weekly");
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                /*{
                                    "sr_no": 1,
                                        "user_code": "AUR00100001",
                                        "bet_amount": "200",
                                        "payout": null,
                                        "transaction_id": "AUR00100001L294",
                                        "total_bet": 200,
                                        "total_wins": 0
                                }*/
                                    HistoryGetSet item = new HistoryGetSet();
                                    JSONObject trnsaction = jsonArray.getJSONObject(i);

                                    item.setTransactionNo(trnsaction.getString("transaction_id"));
                                    item.setAmount(trnsaction.getString("bet_amount"));
                                    if (trnsaction.getString("payout").equals("null")) {
                                        item.setTime("0");
                                    } else {
                                        item.setTime(trnsaction.getString("payout"));
                                    }

                                    list.add(item);
                                }
                                adapter = new TimeSlotAdapter(getApplicationContext(), list);
                                mList.setAdapter(adapter);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"No transaction present to dusplay!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"something went wrong please try again!",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
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
            Toast.makeText(getApplicationContext(),"please check internet connetion!!!",Toast.LENGTH_SHORT).show();
        }
    }


    private void getDetails(String url)
    {

        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";

            Log.i("url",url);
            final ProgressDialog pDialog = new ProgressDialog(TansactionWiseHistory.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            final String TAG = "login";
            StringRequest strReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    pDialog.hide();
                    String payout="",digit,bAmt,wamt,whichBet="",transactionNo = null;
                    try
                    {
                        Log.i("res",""+response);

                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("status").equals("true")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = object.getJSONArray("data_weekly");
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    /*{
                                        "sr_no": 1,
                                            "bet_amount": "40",
                                            "payout": null,
                                            "transaction_id": "L332",
                                            "total_bet": 40,
                                            "total_wins": 0,
                                            "first_digit": "2",
                                            "bet_amount_first": "40",
                                            "win_amount_first": "",
                                            "second_digit": "",
                                            "bet_amount_second": "",
                                            "win_amount_second": ""
                                    }*/

                                    JSONObject trnsaction = jsonArray.getJSONObject(i);

                                    TransactionDetailsGetSet item = new TransactionDetailsGetSet();
                                    if (trnsaction.getString("first_digit").equals("")) {
                                        /*digit = trnsaction.getString("second_digit");
                                        bAmt =  trnsaction.getString("bet_amount_second");
                                        wamt =  trnsaction.getString("win_amount_second");*/
                                        item.setDigit(trnsaction.getString("second_digit"));
                                        item.setChip(trnsaction.getString("bet_amount_second"));
                                        whichBet = "Single Digit Second";
                                    } else {
                                        /*digit = trnsaction.getString("first_digit");
                                        bAmt =  trnsaction.getString("bet_amount_first");
                                        wamt =  trnsaction.getString("win_amount_first");*/
                                        item.setDigit(trnsaction.getString("first_digit"));
                                        item.setChip(trnsaction.getString("bet_amount_first"));
                                        whichBet = "Single Digit First";
                                    }

                                    transList.add(item);

                                    transactionNo = trnsaction.getString("transaction_id");
                                    if (trnsaction.getString("payout").equals("null")) {
                                        payout = "0";
                                    } else {
                                        payout = trnsaction.getString("payout");
                                    }
                                    //String digit,String chip,String tno,String dTime,String tTime,String result



                                }
                                showDetails(transList,transactionNo,"","","",whichBet,payout);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"No transaction present to dusplay!",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
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
            Toast.makeText(getApplicationContext(),"please check internet connetion!!!",Toast.LENGTH_SHORT).show();
        }
    }
}
