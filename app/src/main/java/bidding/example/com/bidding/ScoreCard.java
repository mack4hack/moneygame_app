package bidding.example.com.bidding;

import android.app.ProgressDialog;
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


public class ScoreCard extends Fragment {

    ConnectionDetector connectionDetector;
    ListView listMatches;
    private List<MatchListGetSet> matchList = new ArrayList<>();
    private MatchListAdapter matchListAdapter;

    public ScoreCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score_card, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectionDetector=new ConnectionDetector(getActivity());

        listMatches = (ListView) view.findViewById(R.id.listMatch);
        getMatchList();

        listMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatchListGetSet item = matchList.get(position);
                startActivity(new Intent(getActivity(), Container.class).putExtra("match_id", item.getId()).putExtra("match_name", item.getName()).putExtra("teama", item.getTeama()).putExtra("teamb",item.getTeamb()).putExtra("date",item.getDate()).putExtra("venue",item.getVenue()));

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
                                for (int i = 1; i < jsonArray.length(); i++) {
                                    JSONObject childObject = jsonArray.getJSONObject(i);
                                    MatchListGetSet item = new MatchListGetSet();
                                    Calendar cal = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
//                                    df.setTimeZone (TimeZone.getTimeZone ("IST"));
                                    cal.add(Calendar.DAY_OF_WEEK, -7);
                                    String time1 = df.format(new Date(cal.getTimeInMillis()));

                                    String time = childObject.getString("start_date");
                                    time = time.replace("T"," ");
                                    time = time.replace("-","/");
                                    String [] split1=time.split(", ");
                                    String t= split1[1];

                                    String [] split = t.split("\\u002B");

                                    if(df.parse(split[0]).after(df.parse(time1))) {
                                        if (childObject.getString("status").equals("started") || childObject.getString("status").equals("completed")) // && df.parse(split[0]).before(df.parse(time2)
                                        {
                                            item.setId(childObject.getString("id"));
                                            ;
                                            item.setName(childObject.getString("name"));
                                            item.setDate(split[0]);
                                            item.setVenue(childObject.getString("venue"));
                                            item.setTeama(childObject.getString("team_a"));
                                            item.setTeamb(childObject.getString("team_b"));
                                            matchList.add(item);

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
