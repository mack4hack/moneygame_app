package bidding.example.com.bidding.ResultChart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;

import bidding.example.com.bidding.Adapter.chartAdapter;
import bidding.example.com.bidding.AppControler;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.chartGetSet;
import bidding.example.com.bidding.R;

public class result_chart extends AppCompatActivity {

    private ListView listView;
    private List<chartGetSet> chartList = new ArrayList<>();
    private HashMap<String,String> month = new HashMap<>();
    private String[] monthName = {"select Month","January","February","March","April","May","June","July","August","October","November","December"};
    private Spinner mMonth;
    chartAdapter adapter;
    private TextView t1,t2,t3,t4,t5,t6,t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.resultList);
        mMonth = (Spinner) findViewById(R.id.monthSpinner);

        HorizontalListView horizontalListView = (HorizontalListView) findViewById(R.id.horizontallistview);
        horizontalListView.setAdapter(mAdapter);

        month.put("January","1");
        month.put("February","2");
        month.put("March","3");
        month.put("April","4");
        month.put("May","5");
        month.put("June","6");
        month.put("July","7");
        month.put("August","8");
        month.put("September","9");
        month.put("October","10");
        month.put("November","11");
        month.put("December", "12");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,monthName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMonth.setAdapter(adapter);

        mMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    getChart(month.get(monthName[position]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for(int i=0; i<60; i++){
           String date=  getCalculatedDate("dd-MM-yyyy", -i);
            Log.i("date",""+date);
        }

    }
    private static String[] dataObjects = new String[]{ "Text #1",
            "Text #2",
            "Text #3","Text #4","Text #5","Text #6","Text #7","Text #8","Text #9","Text #10" };

    private BaseAdapter mAdapter = new BaseAdapter() {


        @Override
        public int getCount() {
            return dataObjects.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_horizontalview, null);
            TextView title = (TextView) retval.findViewById(R.id.htext);

            title.setText(dataObjects[position]);

            return retval;
        }

    };

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    private void getChart(String month)
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";
            String url = getString(R.string.get_chart) +"2015-"+month;

            final ProgressDialog pDialog = new ProgressDialog(result_chart.this);
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
                        JSONObject outerObject = new JSONObject(response);
                        if(outerObject.getString("status").equals("true"))
                        {
                            if(!chartList.isEmpty())
                            {
                                chartList.clear();
                                adapter.notifyDataSetChanged();
                            }

                            JSONArray jsonArray = outerObject.getJSONArray("data");
                            if(jsonArray.length()!=0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    chartGetSet item = new chartGetSet();
                                    if (object.getString("date").equals("01")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate1(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate1("-");
                                    }

                                    if (object.getString("date").equals("02")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate2(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate2("-");
                                    }

                                    if (object.getString("date").equals("03")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate3(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate3("-");
                                    }

                                    if (object.getString("date").equals("04")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate4(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate4("-");
                                    }

                                    if (object.getString("date").equals("05")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate5(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate5("-");
                                    }

                                    if (object.getString("date").equals("06")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate6(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate6("-");
                                    }

                                    if (object.getString("date").equals("07")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate7(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate7("-");
                                    }

                                    if (object.getString("date").equals("08")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate8(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate8("-");
                                    }

                                    if (object.getString("date").equals("09")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate9(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate9("-");
                                    }

                                    if (object.getString("date").equals("10")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate10(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate10("-");
                                    }

                                    if (object.getString("date").equals("11")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate11(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate11("-");
                                    }

                                    if (object.getString("date").equals("12")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate12(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate12("-");
                                    }

                                    if (object.getString("date").equals("13")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate13(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate13("-");
                                    }

                                    if (object.getString("date").equals("14")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate14(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate14("-");
                                    }

                                    if (object.getString("date").equals("15")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate15(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate15("-");
                                    }

                                    if (object.getString("date").equals("16")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate16(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate16("-");
                                    }
                                    if (object.getString("date").equals("17")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate17(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate17("-");
                                    }

                                    if (object.getString("date").equals("18")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate18(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate18("-");
                                    }

                                    if (object.getString("date").equals("19")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate19(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate19("-");
                                    }

                                    if (object.getString("date").equals("20")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate20(object.getString("lucky_number"));
                                        //item.setTim(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate20("-");
                                    }

                                    if (object.getString("date").equals("21")) {
                                        //Log.i("Time Slot",""+object.getString("timeslot_id")+" "+object.getString("lucky_number"));
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate21(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate21("-");
                                    }

                                    if (object.getString("date").equals("22")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate22(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate22("-");
                                    }

                                    if (object.getString("date").equals("23")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate23(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate23("-");
                                    }

                                    if (object.getString("date").equals("24")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate24(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate24("-");
                                    }

                                    if (object.getString("date").equals("25")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate25(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate25("-");
                                    }

                                    if (object.getString("date").equals("26")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate26(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate26("-");
                                    }

                                    if (object.getString("date").equals("27")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate27(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate27("-");
                                    }
                                    if (object.getString("date").equals("28")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate28(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate28("-");
                                    }

                                    if (object.getString("date").equals("29")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate29(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate29("-");
                                    }

                                    if (object.getString("date").equals("30")) {
                                        item.setTime(object.getString("timeslot_id"));
                                        item.setDate30(object.getString("lucky_number"));
                                        //item.setTime(object.getString("timeslot_id"));
                                    } else {
                                        //item.setTime("-");
                                        item.setDate30("-");
                                    }/*else if(object.getString("date").equals(1))
                                {
                                    item.setTime(object.getString("timeslot_id"));
                                    item.setDate1(object.getString("lucky_number"));
                                    //item.setTime(object.getString("timeslot_id"));
                                }*/

                                    chartList.add(item);
                                }

                                 adapter = new chartAdapter(getApplicationContext(), chartList);
                                listView.setAdapter(adapter);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"No Data Present To Display!!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"something went wrong please try again!!!",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
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
