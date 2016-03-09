package bidding.example.com.bidding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.MatchListGetSet;


public class UpcomingMatches extends Fragment {

    CalendarPickerView calendar;
    ConnectionDetector connectionDetector;
    String time1, time2;
    private List<MatchListGetSet> matchList = new ArrayList<>();
    public static List<MatchListGetSet> matchListUpcoming = new ArrayList<>();

    public UpcomingMatches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = (CalendarPickerView) view.findViewById(R.id.upcomingCalendar);

        connectionDetector=new ConnectionDetector(getActivity());

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today);

        getMatchList();


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (getHolidays().contains(date)) {
                    Log.i("date",""+date);
                    MatchListGetSet item1 = new MatchListGetSet();
                    for(int i=0; i<matchList.size(); i++) {
                        MatchListGetSet item = matchList.get(i);
                        String dt = item.getDate();
                        if(item.getDate().contains(date.toString())){
                            item1.setName(item.getName());
                            item1.setDate(item.getDate());
                            matchListUpcoming.add(item1);
                        }

                    }
                   startActivity(new Intent(getActivity(), UpcomingList.class));
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    private void getMatchList()
    {
        if(connectionDetector.isConnectingToInternet())
        {

            String tag_json_obj = "json_obj_req";
            final String TAG = "response";
            final String url = getString(R.string.get_match_list);//+ URLEncoder.encode("/"+postString);

            final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                            JSONObject innerObject = object.getJSONObject("data");
                            Log.d(TAG, innerObject.toString());
                            JSONArray jsonArray = innerObject.getJSONArray("Cricket_Match");

                            if(jsonArray.length()!=0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject childObject = jsonArray.getJSONObject(i);
                                    MatchListGetSet item = new MatchListGetSet();
                                    Calendar cal = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//                                    df.setTimeZone (TimeZone.getTimeZone ("IST"));
                                    cal.add(cal.HOUR, -10);
                                    time1 = df.format(new Date(cal.getTimeInMillis()));
//                                    Log.i("time1", "" + time1);

                                    Calendar cal1 = Calendar.getInstance();
                                    cal1.add(cal1.HOUR, 48);
                                    time2= df.format(new Date(cal1.getTimeInMillis()));
//                                    Log.i("time2", "" + time2);

                                    String time = childObject.getString("start_date");
                                    time = time.replace("T"," ");
                                    time = time.replace("-","/");
                                    String [] split = time.split("\\u002B");
                                    if(df.parse(split[0]).after(df.parse(time1))) // && df.parse(split[0]).before(df.parse(time2)
                                    {
                                        item.setId(childObject.getString("id"));;
                                        item.setName(childObject.getString("name"));
                                        item.setDate(split[0]);
                                        item.setVenue(childObject.getString("venue"));
                                        matchList.add(item);

                                    }

                                }

                            }

                            calendar.highlightDates(getHolidays());
                            Log.i("date", "" + getHolidays());
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.hide();
                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(getActivity(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(),getString(R.string.internet_err),Toast.LENGTH_SHORT).show();
        }

    }


    private ArrayList<Date> getHolidays(){
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        ArrayList<Date> holidays = new ArrayList<>();
        for(int i=0; i<matchList.size(); i++){
            MatchListGetSet item = matchList.get(i);
            try {
                Date dte = df1.parse(item.getDate());
//                Log.i("date",""+dte);
//                String date1 = sdf.format(dte);
//                Date date=sdf.parse(date1);
                holidays.add(dte);
            }
            catch (Exception e){
            e.printStackTrace();
            }
        }

        return holidays;
    }
}
