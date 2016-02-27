package bidding.example.com.bidding;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
 * A simple {@link Fragment} subclass.
 */
public class TodaysSummary extends Fragment {

    private ListView mTodaysSummary;
    private List<HistoryGetSet> todaySummary = new ArrayList<>();
    private TodaysSummaryAdapter todaysHistoryAdapter;
    String data="";
    public TodaysSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todays_summary, container, false);

        mTodaysSummary = (ListView) view.findViewById(R.id.today_summary);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());

        try {
            data = getArguments().getString("date");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(!data.equals("")){
            String dte= getArguments().getString("date");
            String date="";
            try {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                Date dt = df1.parse(dte);
                date = df.format(dt);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            getDetails(getString(R.string.get_acc_by_date) + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id","") + "&date=" +date);
        }
        else{
            getDetails(getString(R.string.get_acc_by_date) + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "") + "&date=" + formattedDate);
        }


        mTodaysSummary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistoryGetSet item = todaySummary.get(i);
                //item.getTimeSlotId();
                Log.i("userid",""+item.getTimeSlotId()+" id "+getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                Intent intent = new Intent(getActivity(),TansactionWiseHistory.class);
                intent.putExtra("timeslot",item.getTimeSlotId());
                startActivity(intent);
            }
        });
    }

    private void getDetails(String url)
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";

            final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                                    /*{
                                        "sr_no": 1,
                                            "user_code": null,
                                            "bet_amount": null,
                                            "payout": null,
                                            "balance": 0,
                                            "total_bet": 0,
                                            "total_wins": 0,
                                            "total_balance": 0,
                                            "draw_time": "12:15 am",
                                            "timeslot_id": 1
                                    },*/
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
                                        }

                                    }

                                    todaysHistoryAdapter = new TodaysSummaryAdapter(getActivity(), todaySummary);
                                    mTodaysSummary.setAdapter(todaysHistoryAdapter);
                                }else
                                {
                                    Toast.makeText(getActivity(),"No transaction present to display",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    } catch (Exception e) {
                        pDialog.hide();
                        Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.hide();
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getActivity(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "History Not Present!!!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(),"please check internet connection!!!",Toast.LENGTH_SHORT).show();
        }
    }



}
