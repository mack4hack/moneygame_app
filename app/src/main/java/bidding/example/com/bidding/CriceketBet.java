package bidding.example.com.bidding;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import bidding.example.com.bidding.Adapter.MatchListAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.MatchListGetSet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CriceketBet extends Fragment {

    ListView listMatches;
    private List<MatchListGetSet> matchList = new ArrayList<>();
    public static List<MatchListGetSet> matchListGetSets;
    private MatchListAdapter matchListAdapter;
    private ConnectionDetector connectionDetector;
    private static String[] Matches = {"Mumbai Vs Pune","Chennai Vs Kolkatta","Punjab Vs Rajasthan","Bengaluru Vs Mumbai","Punjab Vs Kochi","Pune Vs Rajasthan","Kolkatta Vs Bengaluru"};
    String time1, time2;

    public CriceketBet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_criceket_bet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        connectionDetector=new ConnectionDetector(getActivity());

        listMatches = (ListView) view.findViewById(R.id.listMatch);
        getMatchList();
//        listMatches.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Matches));

        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        listMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MatchListGetSet item = matchList.get(position);
                try {
                    if (df.parse(item.getDate()).before(df.parse(time2))) {
                        startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position", position).putExtra("match_id",item.getId()));
                    }
                    else{
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Alert");
                        dialog.setMessage("Betting for this match will start 48 hours before");
                        dialog.setCancelable(true);
                        // ...Irrelevant code for customizing the buttons and title

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                            });
                        dialog.create();
                        dialog.show();
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
                            matchListGetSets = new ArrayList<>();
                            if(jsonArray.length()!=0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject childObject = jsonArray.getJSONObject(i);
                                    MatchListGetSet item = new MatchListGetSet();
                                    Calendar cal = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                                    cal.add(cal.HOUR, -10);
                                    time1 = df.format(new Date(cal.getTimeInMillis()));
                                    Log.i("time1", "" + time1);

                                    Calendar cal1 = Calendar.getInstance();
                                    cal1.add(cal1.HOUR, 48);
                                    time2= df.format(new Date(cal1.getTimeInMillis()));
                                    Log.i("time2", "" + time2);

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
                                        if (df.parse(item.getDate()).before(df.parse(time2))) {
                                            matchListGetSets.add(item);
                                        }
                                    }

                                }
                                matchListAdapter = new MatchListAdapter(getActivity(), matchList);
                                listMatches.setAdapter(matchListAdapter);
                            }

                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
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
}
