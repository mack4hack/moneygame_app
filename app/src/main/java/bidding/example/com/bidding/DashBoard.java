package bidding.example.com.bidding;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import bidding.example.com.bidding.APICALL.ApiCall;
import bidding.example.com.bidding.Chart.MainActivity;
import bidding.example.com.bidding.Chart.SampleObject;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.Lottery_Game.Multiple_Bet;
import bidding.example.com.bidding.Lottery_Game.Single_Bet;
import bidding.example.com.bidding.ResultChart.result_chart;


public class DashBoard extends Fragment implements View.OnClickListener{



    private CardView mLotteryCard,mCricketCard,mSingleBetCard,mMultipleBetCard,mTodayHistory,mChart,mProfile,mCancelBet,mTermCondition,mTodaysSummary;
    ProgressDialog pDialog;
    String dTime="";

    public static String res;
    public static List<SampleObject> sampleObjects = new ArrayList<SampleObject>();
    public DashBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        /*mLotteryCard= (CardView) view.findViewById(R.id.card_view1);*/
        /*mCricketCard= (CardView) view.findViewById(R.id.card_view2);;*/
        mSingleBetCard= (CardView) view.findViewById(R.id.single_bet_card);
        mMultipleBetCard= (CardView) view.findViewById(R.id.multiple_bet_card);
        mTodayHistory= (CardView) view.findViewById(R.id.todays_history_card);
        mChart= (CardView) view.findViewById(R.id.chart_card);
        mProfile= (CardView) view.findViewById(R.id.profile_card);
        mCancelBet= (CardView) view.findViewById(R.id.cancel_card);
        mTermCondition = (CardView) view.findViewById(R.id.termcondition);
        mTodaysSummary = (CardView) view.findViewById(R.id.today_summary);

        mSingleBetCard.setCardBackgroundColor(Color.parseColor("#0bc3bb"));
        mMultipleBetCard.setCardBackgroundColor(Color.parseColor("#12132f"));
        mTodayHistory.setCardBackgroundColor(Color.parseColor("#2a221f"));
        mTodaysSummary.setCardBackgroundColor(Color.parseColor("#8c9e90"));
        mChart.setCardBackgroundColor(Color.parseColor("#3aace2"));
        mCancelBet.setCardBackgroundColor(Color.parseColor("#710302"));
        mProfile.setCardBackgroundColor(Color.parseColor("#CDAF95"));
        mTermCondition.setCardBackgroundColor(Color.parseColor("#cf5300"));


        /*mLotteryCard.setOnClickListener(this);*/
        /*mCricketCard.setOnClickListener(this);*/
        mSingleBetCard.setOnClickListener(this);
        mMultipleBetCard.setOnClickListener(this);
        mTodayHistory.setOnClickListener(this);
        mChart.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mCancelBet.setOnClickListener(this);
        mTermCondition.setOnClickListener(this);
        mTodaysSummary.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            /*case R.id.card_view1:
                try {
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Single_Bet fragment = new Single_Bet();
                    fragmentTransaction.replace(R.id.containar, fragment);
                    fragmentTransaction.commit();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;*/
            case R.id.single_bet_card:
                try {
                    Home.toolbar.setTitle("Single Bet");
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Single_Bet fragment = new Single_Bet();
                    fragmentTransaction.replace(R.id.containar, fragment);
                    fragmentTransaction.commit();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.multiple_bet_card:
                Home.toolbar.setTitle("Multiple Bet");
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Multiple_Bet fragment = new Multiple_Bet();
                fragmentTransaction.replace(R.id.containar, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.chart_card:
                startActivity(new Intent(getActivity(), result_chart.class));
                break;
            case R.id.todays_history_card:
                Home.toolbar.setTitle("History");
                android.support.v4.app.FragmentManager TodayManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction TodayTransaction = TodayManager.beginTransaction();
                Container_lottery Today = new Container_lottery();
                TodayTransaction.replace(R.id.containar, Today);
                TodayTransaction.commit();
                break;
            case R.id.cancel_card:
                try
                {
                    CurrentResult();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.profile_card:
                Toast.makeText(getActivity(),"User Profile",Toast.LENGTH_SHORT).show();
                Home.toolbar.setTitle("Profile");
                android.support.v4.app.FragmentManager profileManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction profileTransaction = profileManager.beginTransaction();
                Profile profile = new Profile();
                profileTransaction.replace(R.id.containar, profile);
                profileTransaction.commit();
                break;
            case R.id.termcondition:
                Home.toolbar.setTitle("Terms & Conditions");
                android.support.v4.app.FragmentManager termManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction termTransaction = termManager.beginTransaction();
                TermAndCondition termfragment = new TermAndCondition();
                termTransaction.replace(R.id.containar, termfragment);
                termTransaction.commit();
                break;
            case R.id.today_summary:
                Home.toolbar.setTitle("Todays Summary");
                android.support.v4.app.FragmentManager tManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction tTransaction = tManager.beginTransaction();
                TodaysSummary tSummary = new TodaysSummary();
                tTransaction.replace(R.id.containar, tSummary);
                tTransaction.commit();
                break;
        }

    }

    private void CurrentResult()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if(connectionDetector.isConnectingToInternet())
        {
            //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
            String tag_json_obj = "json_obj_req";
            final String TAG = "response";
            final String url = getString(R.string.get_current_result);//+ URLEncoder.encode("/"+postString);

            //          final ProgressDialog pDialog = new ProgressDialog(getActivity());
            //pDialog.setMessage("Loading...");
            //pDialog.show();

            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    //      pDialog.hide();
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("status").equals("true"))
                        {
                            JSONObject innerObject = object.getJSONObject("data");
//                            JSONObject obj= innerObject.getJSONObject("lucky_number");
                            innerObject.getString("start");
                            dTime=innerObject.getString("end");
                            String betStatus = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("latest_bet", "");
                            String drawTime = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("drawtimeSession", "");
                            if(betStatus.equals("not_placed"))
                            {
                                Toast.makeText(getActivity(), "You dont have any bet to cancel!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Log.i("drwtime",""+drawTime+","+dTime);
                                if(dTime.equals(drawTime)) {
                                    final AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());
                                    infoDialog.setTitle("Bet Information");
                                    infoDialog.setMessage("You are about to cancel bet of chips " + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("bet_amount", ""));

                                    infoDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                                    infoDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            // custom dialog
                                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                            dialog.setTitle("Confirmation");
                                            dialog.setCancelable(true);
                                            // ...Irrelevant code for customizing the buttons and title
                                            LayoutInflater inflater = getActivity().getLayoutInflater();
                                            View dialogView = inflater.inflate(R.layout.custom_layout, null);
                                            dialog.setView(dialogView);

                                            final EditText mPassword = (EditText) dialogView.findViewById(R.id.editConfPassword);
                                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    if (TextUtils.isEmpty(mPassword.getText().toString().trim())) {
                                                        Toast.makeText(getActivity(), "please enter pasword!!!", Toast.LENGTH_SHORT).show();
                                                    } else {

                                                        if (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_password", "").equals(mPassword.getText().toString().trim())) {
                                                            String res = "player_id=" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "");
                                                            dialogInterface.cancel();
                                                            new AsynCancelBet().execute(getString(R.string.cancel_bet), res);

                                                        } else {
                                                            Toast.makeText(getActivity(), "Invalid password!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });

                                            dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            });
                                            dialog.create();
                                            dialog.show();
                                        }
                                    });

                                    infoDialog.create();
                                    infoDialog.show();
                                }
                                else{
                                    Toast.makeText(getActivity(), "Bet cannot be cancelled now", Toast.LENGTH_SHORT).show();
                                }
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

    class AsynCancelBet extends AsyncTask<String,Void,String>
    {
        String mResponse=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... lists) {
            //List<ModelClass> item=lists[0];
            ApiCall call=new ApiCall(getActivity());
            mResponse = call.HttpPost(lists[0], lists[1]);//item.get(0).getApiUrl(),item.get(0).getApiParameter());

            return mResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("cancel bet",""+s);
            pDialog.dismiss();
            if(mResponse!=null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equals("true")) {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
                        editor.putString("latest_bet","not_placed");
                        editor.putString("game_type", "-1");
                        editor.commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    public void getres()
    {
        String  tag_string_req = "string_req";
        String url = "http://lottery.pixmadness.in/api/bets/luckyNumberchart?month=2015-11";//getString(R.string.login);

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        final String TAG="login";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                pDialog.hide();
                try {

                    //res = response;
                    JSONObject object=new JSONObject(response);
                    if(object.getString("status").equals("true")) {
                        try {
                            JSONObject ob = new JSONObject(response);
                            JSONArray array=ob.getJSONArray("data");
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject in=array.getJSONObject(i);
                                SampleObject sampleObject = new SampleObject(
                                        in.getString("date"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number"),
                                        in.getString("lucky_number")
                                );

                                sampleObjects.add(sampleObject);
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Something went wrong please try again",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    pDialog.hide();
                    Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });




// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}
