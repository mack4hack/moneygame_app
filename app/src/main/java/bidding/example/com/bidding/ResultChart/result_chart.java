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
    HorizontalListView horizontalListView;
    private List<chartGetSet> chartList = new ArrayList<>();
    private HashMap<String,String> month = new HashMap<>();
    private String[] monthName = {"select Month","January","February","March","April","May","June","July","August","October","November","December"};
    private Spinner mMonth;
    chartAdapter adapter;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33,t34,t35,t36,t37,t38,t39,t40,t41,t42,t43,t44,t45,t46,t47,t48,t49,t50,t51,t52,t53,t54,t55,t56,t57,t58,t59,t60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.resultList);

        t1 = (TextView) findViewById(R.id.text_view2);
        t2 = (TextView) findViewById(R.id.text_view3);
        t3 = (TextView) findViewById(R.id.text_view4);
        t4 = (TextView) findViewById(R.id.text_view5);
        t5 = (TextView) findViewById(R.id.text_view6);
        t6 = (TextView) findViewById(R.id.text_view7);
        t7 = (TextView) findViewById(R.id.text_view8);
        t8 = (TextView) findViewById(R.id.text_view9);
        t9 = (TextView) findViewById(R.id.text_view10);
        t10 = (TextView) findViewById(R.id.text_view11);
        t11 = (TextView) findViewById(R.id.text_view12);
        t12 = (TextView) findViewById(R.id.text_view13);
        t13 = (TextView) findViewById(R.id.text_view14);
        t14 = (TextView) findViewById(R.id.text_view15);
        t15 = (TextView) findViewById(R.id.text_view16);
        t16 = (TextView) findViewById(R.id.text_view17);
        t17 = (TextView) findViewById(R.id.text_view18);
        t18 = (TextView) findViewById(R.id.text_view19);
        t19 = (TextView) findViewById(R.id.text_view20);
        t20 = (TextView) findViewById(R.id.text_view21);
        t21 = (TextView) findViewById(R.id.text_view22);
        t22 = (TextView) findViewById(R.id.text_view23);
        t23 = (TextView) findViewById(R.id.text_view24);
        t24 = (TextView) findViewById(R.id.text_view25);
        t25 = (TextView) findViewById(R.id.text_view26);
        t26 = (TextView) findViewById(R.id.text_view27);
        t27 = (TextView) findViewById(R.id.text_view28);
        t28 = (TextView) findViewById(R.id.text_view29);
        t29 = (TextView) findViewById(R.id.text_view30);
        t30 = (TextView) findViewById(R.id.text_view31);
        t31 = (TextView) findViewById(R.id.text_view32);
        t32 = (TextView) findViewById(R.id.text_view33);
        t33 = (TextView) findViewById(R.id.text_view34);
        t34 = (TextView) findViewById(R.id.text_view35);
        t35 = (TextView) findViewById(R.id.text_view36);
        t36 = (TextView) findViewById(R.id.text_view37);
        t37 = (TextView) findViewById(R.id.text_view38);
        t38 = (TextView) findViewById(R.id.text_view39);
        t39 = (TextView) findViewById(R.id.text_view40);
        t40 = (TextView) findViewById(R.id.text_view41);
        t41 = (TextView) findViewById(R.id.text_view42);
        t42 = (TextView) findViewById(R.id.text_view43);
        t43 = (TextView) findViewById(R.id.text_view44);
        t44 = (TextView) findViewById(R.id.text_view45);
        t45 = (TextView) findViewById(R.id.text_view46);
        t46 = (TextView) findViewById(R.id.text_view47);
        t47 = (TextView) findViewById(R.id.text_view48);
        t48 = (TextView) findViewById(R.id.text_view49);
        t49 = (TextView) findViewById(R.id.text_view50);
        t50 = (TextView) findViewById(R.id.text_view51);
        t51 = (TextView) findViewById(R.id.text_view52);
        t52 = (TextView) findViewById(R.id.text_view53);
        t53 = (TextView) findViewById(R.id.text_view54);
        t54 = (TextView) findViewById(R.id.text_view55);
        t55 = (TextView) findViewById(R.id.text_view56);
        t56 = (TextView) findViewById(R.id.text_view57);
        t57 = (TextView) findViewById(R.id.text_view58);
        t58 = (TextView) findViewById(R.id.text_view59);
        t59 = (TextView) findViewById(R.id.text_view60);
        t60 = (TextView) findViewById(R.id.text_view61);


        Calendar c = Calendar.getInstance();
        int mnth =c.get(Calendar.MONTH);
        int yr= c.get(Calendar.YEAR);
        String month = String.valueOf(yr)+"-"+String.valueOf(mnth);
        getChart(month);

        for(int i=0; i<60; i++){
           String date=  getCalculatedDate("dd/MM", -i);
            Log.i("date",""+date);
            if(i==0){
                t1.setText(date);
            }
            if(i==1){
                t2.setText(date);
            }
            if(i==2){
                t3.setText(date);
            }
            if(i==3){
                t4.setText(date);
            }
            if(i==4){
                t5.setText(date);
            }
            if(i==5){
                t6.setText(date);
            }
            if(i==6){
                t7.setText(date);
            }
            if(i==7){
                t8.setText(date);
            }
            if(i==8){
                t9.setText(date);
            }
            if(i==9){
                t10.setText(date);
            }
            if(i==10){
                t11.setText(date);
            }if(i==11){
                t12.setText(date);
            }
            if(i==12){
                t13.setText(date);
            }
            if(i==13){
                t14.setText(date);
            }
            if(i==14){
                t15.setText(date);
            }
            if(i==0){
                t1.setText(date);
            }
            if(i==1){
                t2.setText(date);
            }
            if(i==2){
                t3.setText(date);
            }
            if(i==3){
                t4.setText(date);
            }
            if(i==4){
                t5.setText(date);
            }
            if(i==5){
                t6.setText(date);
            }
            if(i==6){
                t7.setText(date);
            }
            if(i==7){
                t8.setText(date);
            }
            if(i==8){
                t9.setText(date);
            }
            if(i==9){
                t10.setText(date);
            }
            if(i==10){
                t11.setText(date);
            }if(i==11){
                t12.setText(date);
            }
            if(i==12){
                t13.setText(date);
            }
            if(i==13){
                t14.setText(date);
            }
            if(i==14){
                t15.setText(date);
            }
            if(i==15){
                t16.setText(date);
            }
            if(i==16){
                t17.setText(date);
            }
            if(i==17){
                t18.setText(date);
            }
            if(i==18){
                t19.setText(date);
            }
            if(i==19){
                t20.setText(date);
            }
            if(i==20){
                t21.setText(date);
            }
            if(i==21){
                t22.setText(date);
            }
            if(i==22){
                t23.setText(date);
            }
            if(i==23){
                t24.setText(date);
            }
            if(i==24){
                t25.setText(date);
            }
            if(i==25){
                t26.setText(date);
            }if(i==26){
                t27.setText(date);
            }
            if(i==27){
                t28.setText(date);
            }
            if(i==28){
                t29.setText(date);
            }
            if(i==29){
                t30.setText(date);
            }
            if(i==30){
                t31.setText(date);
            }
            if(i==31){
                t32.setText(date);
            }
            if(i==32){
                t33.setText(date);
            }
            if(i==33){
                t34.setText(date);
            }
            if(i==34){
                t35.setText(date);
            }
            if(i==35){
                t36.setText(date);
            }
            if(i==36){
                t37.setText(date);
            }
            if(i==37){
                t38.setText(date);
            }
            if(i==38){
                t39.setText(date);
            }
            if(i==39){
                t40.setText(date);
            }
            if(i==40){
                t41.setText(date);
            }if(i==41){
                t42.setText(date);
            }
            if(i==42){
                t43.setText(date);
            }
            if(i==43){
                t44.setText(date);
            }
            if(i==44){
                t45.setText(date);
            }
            if(i==45){
                t46.setText(date);
            }
            if(i==46){
                t47.setText(date);
            }
            if(i==47){
                t48.setText(date);
            }
            if(i==48){
                t49.setText(date);
            }
            if(i==49){
                t50.setText(date);
            }
            if(i==50){
                t51.setText(date);
            }
            if(i==51){
                t52.setText(date);
            }
            if(i==52){
                t53.setText(date);
            }
            if(i==53){
                t54.setText(date);
            }
            if(i==54){
                t55.setText(date);
            }
            if(i==55){
                t56.setText(date);
            }if(i==56){
                t57.setText(date);
            }
            if(i==57){
                t58.setText(date);
            }
            if(i==58){
                t59.setText(date);
            }
            if(i==59){
                t60.setText(date);
            }



        }

    }

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
