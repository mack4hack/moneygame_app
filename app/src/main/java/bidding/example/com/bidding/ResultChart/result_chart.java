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
    private HashMap<String,String> time = new HashMap<>();
    private String[] monthName = {"select Month","January","February","March","April","May","June","July","August","October","November","December"};
    private Spinner mMonth;
    chartAdapter adapter;
    ArrayList<chartGetSet> list = new ArrayList<chartGetSet>();
   public static List<HashMap<String, String>> timeslot = new ArrayList<HashMap<String, String>>();
        String[] times = {"00.15","00.30","00.45","1.00","1.15","1.30","1.45","2.00","2.15","2.30","2.45","3.00","3.15","3.30","3.45","4.00","4.15","4.30","4.45","5.00","5.15","5.30","5.45","6.00","6.15","6.30","6.45","7.00","7.15","7.30","7.45","8.00","8.15","8.30","8.45","9.00","9.15","9.30","9.45","10.00","10.15","10.30","10.45","11.00","11.15","11.30","11.45","12.00","12.15","12.30","12.45","13.00","13.15","13.30","13.45","14.00","14.15","14.30","14.45","15.00","15.15","15.30","15.45","16.00","16.15","16.30","16.45","17.00","17.15","17.30","17.45","18.00","18.15","18.30","18.45","19.00","19.15","19.30","19.45","20.00","20.15","20.30","20.45","21.00","21.15","21.30","21.45","22.00","22.15","22.30","22.45","23.00","23.15","23.30","23.45","00.00"};
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
        int yr= c.get(Calendar.YEAR);
        int mnth =c.get(Calendar.MONTH);
        mnth=mnth+1;
        String month = String.valueOf(yr)+"-"+String.valueOf(mnth);
        Log.i("month",""+month);
        getChart(month);

        for(int i=0; i<60; i++){
            String date=  getCalculatedDate("MM-dd", -i);
            Log.i("date",""+date);
            if(i==0){
                t60.setText(date);
            }
            if(i==1){
                t59.setText(date);
            }
            if(i==2){
                t58.setText(date);
            }
            if(i==3){
                t57.setText(date);
            }
            if(i==4){
                t56.setText(date);
            }
            if(i==5){
                t55.setText(date);
            }
            if(i==6){
                t54.setText(date);
            }
            if(i==7){
                t53.setText(date);
            }
            if(i==8){
                t52.setText(date);
            }
            if(i==9){
                t51.setText(date);
            }
            if(i==10){
                t50.setText(date);
            }
            if(i==11){
                t49.setText(date);
            }
            if(i==12){
                t48.setText(date);
            }
            if(i==13){
                t47.setText(date);
            }
            if(i==14){
                t46.setText(date);
            }
            if(i==15){
                t45.setText(date);
            }
            if(i==16){
                t44.setText(date);
            }
            if(i==17){
                t43.setText(date);
            }
            if(i==18){
                t42.setText(date);
            }
            if(i==19){
                t41.setText(date);
            }
            if(i==20){
                t40.setText(date);
            }
            if(i==21){
                t39.setText(date);
            }
            if(i==22){
                t38.setText(date);
            }
            if(i==23){
                t37.setText(date);
            }
            if(i==24){
                t36.setText(date);
            }
            if(i==25){
                t35.setText(date);
            }if(i==26){
                t34.setText(date);
            }
            if(i==27){
                t33.setText(date);
            }
            if(i==28){
                t32.setText(date);
            }
            if(i==29){
                t31.setText(date);
            }
            if(i==30){
                t30.setText(date);
            }
            if(i==31){
                t29.setText(date);
            }
            if(i==32){
                t28.setText(date);
            }
            if(i==33){
                t27.setText(date);
            }
            if(i==34){
                t26.setText(date);
            }
            if(i==35){
                t25.setText(date);
            }
            if(i==36){
                t24.setText(date);
            }
            if(i==37){
                t23.setText(date);
            }
            if(i==38){
                t22.setText(date);
            }
            if(i==39){
                t21.setText(date);
            }
            if(i==40){
                t20.setText(date);
            }
            if(i==41){
                t19.setText(date);
            }
            if(i==42){
                t18.setText(date);
            }
            if(i==43){
                t17.setText(date);
            }
            if(i==44){
                t16.setText(date);
            }
            if(i==45){
                t15.setText(date);
            }
            if(i==46){
                t14.setText(date);
            }
            if(i==47){
                t13.setText(date);
            }
            if(i==48){
                t12.setText(date);
            }
            if(i==49){
                t11.setText(date);
            }
            if(i==50){
                t10.setText(date);
            }
            if(i==51){
                t9.setText(date);
            }
            if(i==52){
                t8.setText(date);
            }
            if(i==53){
                t7.setText(date);
            }
            if(i==54){
                t6.setText(date);
            }
            if(i==55){
                t5.setText(date);
            }
            if(i==56){
                t4.setText(date);
            }
            if(i==57){
                t3.setText(date);
            }
            if(i==58){
                t2.setText(date);
            }
            if(i==59){
                t1.setText(date);
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
            String url = getString(R.string.get_chart)+month;
            Log.i("chart url",""+url);

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
                        if(outerObject.getString("status").equals("true")) {
                            if (!chartList.isEmpty()) {
                                chartList.clear();
                                adapter.notifyDataSetChanged();
                            }
//                            try {

                            JSONObject jsonobj = outerObject.getJSONObject("data");
                            JSONArray jArray = jsonobj.names();
                            list = new ArrayList<chartGetSet>();
                            if(jsonobj.length()!=0) {
                                for(int j = 0; j < jsonobj.length(); j++) {
                                    chartGetSet item = new chartGetSet();
//                                    JSONObject jobj = outerObject.getJSONObject(jArray.getString(j));
                                    JSONArray jsonArray= jsonobj.getJSONArray(jArray.getString(j));
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                          for(int t=0; t<96; t++){

//                                              time.put(object.getString("timeslot_id"),times[i]);
//                                                  timeslot.add(time);
                                          }

                                        String dt = object.getString("date");
                                        String split = dt.substring(5);
//                                        Log.i("date1", split[1]);
//                                        Log.i("date2", split[2]);
                                        String date = dt.substring(5);
                                        Log.i("date1", date);
                                        for(int k=1; k<=96; k++) {
                                            if (date.equals(t1.getText().toString())&& object.getString("timeslot_id").equals(String.valueOf(k))) {
                                                item.setTime("00.15");
                                                item.setDate1(object.getString("lucky_number"));
                                                Log.i("date3", date);

                                                //item.setTime(object.getString("timeslot_id"));
                                            } else {
                                                //item.setTime("-");
                                                item.setDate1("-");
                                            }
                                            list.add(item);
                                        }
                                        if (date.equals(t2.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate2(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate2("-");
                                        }

                                        if (date.equals(t3.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate3(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate3("-");
                                        }

                                        if (date.equals(t4.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate4(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate4("-");
                                        }

                                        if (date.equals(t5.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate5(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate5("-");
                                        }

                                        if (date.equals(t6.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate6(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate6("-");
                                        }

                                        if (date.equals(t7.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate7(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate7("-");
                                        }

                                        if (date.equals(t8.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate8(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate8("-");
                                        }

                                        if (date.equals(t9.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate9(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate9("-");
                                        }

                                        if (date.equals(t10.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate10(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate10("-");
                                        }

                                        if (date.equals(t11.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate11(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate11("-");
                                        }

                                        if (date.equals(t12.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate12(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate12("-");
                                        }

                                        if (date.equals(t13.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate13(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate13("-");
                                        }

                                        if (date.equals(t14.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate14(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate14("-");
                                        }

                                        if (date.equals(t15.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate15(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate15("-");
                                        }

                                        if (date.equals(t16.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate16(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate16("-");
                                        }
                                        if (date.equals(t17.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate17(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate17("-");
                                        }

                                        if (date.equals(t18.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate18(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate18("-");
                                        }

                                        if (date.equals(t19.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate19(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate19("-");
                                        }

                                        if (date.equals(t20.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate20(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTim(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate20("-");
                                        }

                                        if (date.equals(t21.getText().toString())) {
                                            //Log.i("Time Slot",""+object.getString("timeslot_id")+" "+object.getString("lucky_number"));
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate21(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate21("-");
                                        }

                                        if (date.equals(t22.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate22(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate22("-");
                                        }

                                        if (date.equals(t23.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate23(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate23("-");
                                        }

                                        if (date.equals(t24.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate24(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate24("-");
                                        }

                                        if (date.equals(t25.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate25(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate25("-");
                                        }

                                        if (date.equals(t26.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate26(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate26("-");
                                        }

                                        if (date.equals(t27.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate27(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate27("-");
                                        }
                                        if (date.equals(t28.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate28(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate28("-");
                                        }

                                        if (date.equals(t29.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate29(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate29("-");
                                        }

                                        if (date.equals(t30.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate30(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate30("-");
                                        }

                                        if (date.equals(t31.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate31(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate31("-");
                                        }
                                        if (date.equals(t32.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate32(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate32("-");
                                        }
                                        if (date.equals(t33.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate33(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate33("-");
                                        }
                                        if (date.equals(t34.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate34(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate34("-");
                                        }
                                        if (date.equals(t35.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate35(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate35("-");
                                        }
                                        if (date.equals(t36.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate36(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate36("-");
                                        }
                                        if (date.equals(t37.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate37(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate37("-");
                                        }
                                        if (date.equals(t38.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate38(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate38("-");
                                        }
                                        if (date.equals(t39.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate39(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate39("-");
                                        }
                                        if (date.equals(t40.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate40(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate40("-");
                                        }
                                        if (date.equals(t41.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate41(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate41("-");
                                        }
                                        if (date.equals(t42.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate42(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate42("-");
                                        }
                                        if (date.equals(t43.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate43(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate43("-");
                                        }
                                        if (date.equals(t44.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate44(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate44("-");
                                        }
                                        if (date.equals(t45.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate45(object.getString("lucky_number"));
                                            Log.i("date3", date);
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate45("-");
                                        }
                                        if (date.equals(t46.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate46(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate46("-");
                                        }
                                        if (date.equals(t47.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate47(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate47("-");
                                        }
                                        if (date.equals(t48.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate48(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate48("-");
                                        }
                                        if (date.equals(t49.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate49(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate49("-");
                                        }
                                        if (date.equals(t50.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate50(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate50("-");
                                        }
                                        if (date.equals(t51.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate51(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate51("-");
                                        }
                                        if (date.equals(t52.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate52(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate52("-");
                                        }  if (date.equals(t53.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate53(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate53("-");
                                        }
                                        if (date.equals(t54.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate54(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate54("-");
                                        }
                                        if (date.equals(t55.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate55(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate55("-");
                                        }
                                        if (date.equals(t56.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate56(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate56("-");
                                        }
                                        if (date.equals(t57.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate57(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate57("-");
                                        }
                                        if (date.equals(t58.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate58(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate58("-");
                                        }
                                        if (date.equals(t59.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate59(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate59("-");
                                        }
                                        if (date.equals(t60.getText().toString())) {
                                            item.setTime(object.getString("timeslot_id"));
                                            item.setDate60(object.getString("lucky_number"));
                                            //item.setTime(object.getString("timeslot_id"));
                                        } else {
                                            //item.setTime("-");
                                            item.setDate60("-");
                                        }


                                        /*else if(object.getString("date").equals(1))
                                {
                                    item.setTime(object.getString("timeslot_id"));
                                    item.setDate1(object.getString("lucky_number"));
                                    //item.setTime(object.getString("timeslot_id"));
                                }*/


                                    }
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
