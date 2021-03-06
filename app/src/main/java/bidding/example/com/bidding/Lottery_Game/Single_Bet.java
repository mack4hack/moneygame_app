package bidding.example.com.bidding.Lottery_Game;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import bidding.example.com.bidding.APICALL.ApiCall;
import bidding.example.com.bidding.AppControler;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.R;

public class Single_Bet extends Fragment implements View.OnClickListener
{

    private ProgressDialog dialog;
    private ProgressBar balanceStatus;
    private static int flag;
    private static int FirstNo,SecondNo,JodiNo;
    private EditText mEditFirstSingleDigit,mEditSecondSingleDigit,mEditJodi,mEditFirstSingleAmout,mEditSecondSingleAmout,mEditJodiAmout;
    private Button mBtnFirstSingleDigit,mBtnSecondSingleDigit,mBtnJodi;
    private TextView mFirstSingleDigitReturn,mSecondSingleDigitReturn,mJodiReturn,mCurrentResult, text_progrees;
    private TextView FirstBetTotal, SecondBetTotal, JodiBetTotal;
    public  TextView mTimer,mCurrentSession;
    ProgressDialog pDialog;
    private View view=null;
    private boolean cancel=false;
    private String playerId,postString, result;
    private ConnectionDetector connectionDetector;
    private int currentMinute,currentHoure;
    CounterClass counterClass;
    Double default_amnt, prsnt_amnt, diff, percent;
    String session,currenttime;
    public SharedPreferences preferences;
    private static final String FORMAT = "%02d:%02d";
    long millis=900000, sec;
    public static int flg=0;

    public Single_Bet()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single__bet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences=getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE);

        pDialog = new ProgressDialog(getActivity());
        playerId = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getString("player_id", "");

        flg=0;
        new CountDownTimer(900000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {


            }

            public void onFinish() {
            }
        }.start();
        //service = new timeService();
        /*try
        {
            Thread myThread = null;
            Runnable myRunnableThread = new CountDownRunner();
            myThread= new Thread(myRunnableThread);
            myThread.start();
        }
        catch (Exception e)
        {

        }*/
        connectionDetector=new ConnectionDetector(getActivity());

        text_progrees = (TextView) view.findViewById(R.id.txtprogress);
        balanceStatus = (ProgressBar) view.findViewById(R.id.ProgressBar01);
        int default_amnt= (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("default_amt", "")));
        balanceStatus.setMax(default_amnt);
        getPresentAmount();

        //Edit text for digit
        mEditFirstSingleDigit= (EditText) view.findViewById(R.id.edit_first_digit);
        mEditSecondSingleDigit= (EditText) view.findViewById(R.id.edit_second_digit);
        mEditJodi= (EditText) view.findViewById(R.id.edit_jodi_digit);

     //Edit text for number
        mEditFirstSingleAmout= (EditText) view.findViewById(R.id.edit_first_amount);
        mEditSecondSingleAmout= (EditText) view.findViewById(R.id.edit_second_digit_amount);
        mEditJodiAmout= (EditText) view.findViewById(R.id.edit_jodi_digit_amount);

     //TextView for return result
        mFirstSingleDigitReturn= (TextView) view.findViewById(R.id.first_return_amount);
        mSecondSingleDigitReturn= (TextView) view.findViewById(R.id.return_second_amount);
        mJodiReturn= (TextView) view.findViewById(R.id.return_jodi_amount);

        FirstBetTotal= (TextView) view.findViewById(R.id.first_bet_ttl);
        SecondBetTotal= (TextView) view.findViewById(R.id.second_bets_ttl);
        JodiBetTotal= (TextView) view.findViewById(R.id.jodi_bets_ttl);

        mTimer= (TextView) view.findViewById(R.id.time_left);
        mCurrentResult = (TextView) view.findViewById(R.id.current_result);
        mCurrentSession = (TextView) view.findViewById(R.id.current_seesion);

     //Button to place bet
        mBtnFirstSingleDigit= (Button) view.findViewById(R.id.btn_first_place_bet);
        mBtnSecondSingleDigit= (Button) view.findViewById(R.id.btn_second_place_bet);
        mBtnJodi= (Button) view.findViewById(R.id.btn_jodi_place_bet);

     //Event Listenerr for buttons
        mBtnFirstSingleDigit.setOnClickListener(this);
        mBtnSecondSingleDigit.setOnClickListener(this);
        mBtnJodi.setOnClickListener(this);

    //Edittext listener
        mEditFirstSingleAmout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().equals("")) {
                            mFirstSingleDigitReturn.setText("" + (Integer.parseInt(charSequence.toString()) * 9.0));
                    FirstBetTotal.setText(charSequence.toString());
                }
                else
                {
                    mFirstSingleDigitReturn.setText("");
                    FirstBetTotal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditSecondSingleAmout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.i("amt", "" + charSequence);
                if(!charSequence.toString().equals("")) {
                    mSecondSingleDigitReturn.setText("" + (Integer.parseInt(charSequence.toString()) * 9.0));
                    SecondBetTotal.setText(charSequence.toString());
                }
                else
                {
                    mSecondSingleDigitReturn.setText("");
                    SecondBetTotal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditJodiAmout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("amt", "" + charSequence);
                if(!charSequence.toString().equals("")) {
                    mJodiReturn.setText(""+Integer.parseInt(charSequence.toString()) * 90);
                    JodiBetTotal.setText(charSequence.toString());
                }
                else
                {
                    mJodiReturn.setText("");
                    JodiBetTotal.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//get current result
        CurrentResult();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private String getPresentAmount()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";
            String url = getString(R.string.url_amount) + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "");

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
                            JSONObject jsonObject = new JSONObject(response);
                            result = jsonObject.getString("present_amount");
                            default_amnt = Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), getActivity().MODE_PRIVATE).getString("default_amt", ""));
                            prsnt_amnt = (Double.parseDouble(result));

                            if(prsnt_amnt<0)
                            {
                                balanceStatus.setProgress(0);
                            }
                            else
                            {
                                int bal = (int) Math.round(prsnt_amnt);
                                balanceStatus.setProgress(bal);
                                text_progrees.setText(result);

                            }

                            if(default_amnt>prsnt_amnt){
                                diff = default_amnt-prsnt_amnt;
                                percent = (diff/default_amnt)*100;

                            }
                            else {
                                diff = prsnt_amnt-default_amnt;
                                percent = (diff/default_amnt)*100;

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
                        Toast.makeText(getActivity(), "Present Chips Not Present!!!", Toast.LENGTH_SHORT).show();
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
        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.custom_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
//            case R.id.game_2:
//                try {
//                    Home.toolbar.setTitle("Multiple Bet");
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Multiple_Bet fragment = new Multiple_Bet();
//                    fragmentTransaction.replace(R.id.containar, fragment);
//                    fragmentTransaction.commit();
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_first_place_bet:
                        try {
                            if (TextUtils.isEmpty(mEditFirstSingleDigit.getText().toString().trim())) {
                                mEditFirstSingleDigit.requestFocus();
                                mEditFirstSingleDigit.setFocusable(true);
                                mEditFirstSingleDigit.setError("please enter digit");
                                view = mEditFirstSingleDigit;
                                cancel = true;
                            }
                            else if(TextUtils.isEmpty(mEditFirstSingleAmout.getText().toString().trim()))
                            {
                                mEditFirstSingleAmout.requestFocus();
                                mEditFirstSingleAmout.setFocusable(true);
                                mEditFirstSingleAmout.setError("please enter chips");
                                view = mEditFirstSingleAmout;
                                cancel = true;
                            }
                            else if(Integer.parseInt(mEditFirstSingleAmout.getText().toString().trim())>Double.parseDouble(result)){
                                mEditFirstSingleAmout.requestFocus();
                                mEditFirstSingleAmout.setFocusable(true);
                                mEditFirstSingleAmout.setError("Not enough chips found");
                                view = mEditFirstSingleAmout;
                                cancel = true;
                            }
                            else if(Integer.parseInt(mEditFirstSingleAmout.getText().toString().trim())<20)
                            {
                                mEditFirstSingleAmout.requestFocus();
                                mEditFirstSingleAmout.setFocusable(true);
                                mEditFirstSingleAmout.setError("please enter chips greater than 20");
                                view = mEditFirstSingleAmout;
                                cancel = true;
                            }
                            else if(Integer.parseInt(mEditFirstSingleAmout.getText().toString().trim())%20!=0)
                            {
                                mEditFirstSingleAmout.requestFocus();
                                mEditFirstSingleAmout.setFocusable(true);
                                mEditFirstSingleAmout.setError("please enter valid chips!!!");
                                view = mEditFirstSingleAmout;
                                cancel = true;
                            }
                            else
                            {

                                if(connectionDetector.isConnectingToInternet())
                                {
                                    if (!session.equals("00:00 am")) {

                                    FirstNo=Integer.parseInt(mEditFirstSingleDigit.getText().toString().trim());

                                    postString="player_id="+playerId+"&data[0][digit]="+mEditFirstSingleDigit.getText().toString().trim()+"&data[0][bet_amount]="+mEditFirstSingleAmout.getText().toString().trim();
                                    flag = 1;
//                                    new AsynTaskCall().execute(getString(R.string.PlaceBetFirst),postString);


                                    String tag_json_obj = "json_obj_req";
                                    final String TAG = "response";
                                    final String url = getString(R.string.PlaceBetFirst);//+ URLEncoder.encode("/"+postString);

                                    //final ProgressDialog pDialog = new ProgressDialog(getActivity());
                                    pDialog.setMessage("Loading...");
                                    pDialog.show();

                                    StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                                            url, new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            pDialog.hide();
                                            try {
                                                Log.d(TAG, response.toString());

                                                JSONObject object = new JSONObject(response);
                                                if (object.getString("status").equals("true"))
                                                {
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();

                                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();

                                                        editor.putString("latest_bet", mEditFirstSingleDigit.getText().toString().trim());
                                                        editor.putString("game_type", "1");
                                                        editor.putString("bet_amount",mEditFirstSingleAmout.getText().toString().trim());
                                                        editor.putString("digit", mEditFirstSingleDigit.getText().toString().trim());
                                                        editor.putString("drawtimeSession",session);

                                                        flag = 1;
                                                        FirstNo = Integer.parseInt(mEditFirstSingleDigit.getText().toString().trim());

                                                    int res = (int) (Integer.parseInt(mEditFirstSingleDigit.getText().toString().trim()) * 9.0);

//                                                    int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
//                                                    int amt = bal - res;
//                                                    editor.putString("present_amount", "" + amt);
                                                    editor.commit();

//                                                    balanceStatus.setProgress(amt);
                                                    getPresentAmount();
                                                    mEditFirstSingleAmout.getText().clear();
                                                    mEditFirstSingleDigit.getText().clear();
                                                    mFirstSingleDigitReturn.setHint("00");
                                                }
                                                else
                                                {
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                                }
                                            }catch (Exception e)
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
                                            else{
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                            }
                                            error.printStackTrace();
                                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                                            pDialog.hide();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            HashMap<String, String> map = new HashMap<>();
                                            map.put("player_id", playerId);
                                            map.put("data[0][digit]", mEditFirstSingleDigit.getText().toString().trim());
                                            map.put("data[0][bet_amount]", mEditFirstSingleAmout.getText().toString().trim());
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
                                else
                                {
                                    Toast.makeText(getActivity(),"Draw is closed",Toast.LENGTH_SHORT).show();
                                }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),getString(R.string.internet_err),Toast.LENGTH_SHORT).show();
                                }

                            }
                            }catch(Exception e)
                            {
                                e.printStackTrace();
                            }

                break;
            case R.id.btn_second_place_bet:
                    try
                    {
                        if (TextUtils.isEmpty(mEditSecondSingleDigit.getText().toString().trim())) {
                            mEditSecondSingleDigit.requestFocus();
                            mEditSecondSingleDigit.setFocusable(true);
                            mEditSecondSingleDigit.setError("please enter digit");
                            view = mEditSecondSingleDigit;
                            cancel = true;
                        }
                        else if(TextUtils.isEmpty(mEditSecondSingleAmout.getText().toString().trim()))
                        {
                            mEditSecondSingleAmout.requestFocus();
                            mEditSecondSingleAmout.setFocusable(true);
                            mEditSecondSingleAmout.setError("please enter chips");
                            view = mEditSecondSingleAmout;
                            cancel = true;
                        }
                        else if(Integer.parseInt(mEditSecondSingleAmout.getText().toString().trim())>Double.parseDouble(result)) {
                            mEditSecondSingleAmout.requestFocus();
                            mEditSecondSingleAmout.setFocusable(true);
                            mEditSecondSingleAmout.setError("Not enough chips found");
                            view = mEditSecondSingleAmout;
                            cancel = true;
                        }
                        else if(Integer.parseInt(mEditSecondSingleAmout.getText().toString().trim())<20)
                        {
                            mEditSecondSingleAmout.requestFocus();
                            mEditSecondSingleAmout.setFocusable(true);
                            mEditSecondSingleAmout.setError("please enter chips greater than 20");
                            view = mEditSecondSingleAmout;
                            cancel = true;
                        }
                        else if(Integer.parseInt(mEditSecondSingleAmout.getText().toString().trim())%20!=0)
                        {
                            mEditSecondSingleAmout.requestFocus();
                            mEditSecondSingleAmout.setFocusable(true);
                            mEditSecondSingleAmout.setError("please enter valid chips!!!");
                            view = mEditSecondSingleAmout;
                            cancel = true;
                        }
                        else {

                            if(connectionDetector.isConnectingToInternet()) {

                                if (!session.equals("00:00 am")) {
                                    //                postString="player_id="+playerId+"&data[0][digit]="+mEditSecondSingleDigit.getText().toString().trim()+"&data[0][bet_amount]="+mEditSecondSingleAmout.getText().toString().trim();
                                    SecondNo = Integer.parseInt(mEditSecondSingleDigit.getText().toString().trim());

//                                new AsynTaskCall().execute(getString(R.string.PlaceBetSecond),postString);

                                    String tag_json_obj = "json_obj_req";
                                    final String TAG = "response";
                                    final String url = getString(R.string.PlaceBetSecond);//+ URLEncoder.encode("/"+postString);

                                    //              final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                                                if (object.getString("status").equals("true")) {
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();

                                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
//                                                if (flag == 2) {
                                                    editor.putString("latest_bet", mEditSecondSingleDigit.getText().toString().trim());
                                                    editor.putString("game_type", "2");
                                                    editor.putString("bet_amount", mEditSecondSingleAmout.getText().toString().trim());
                                                    editor.putString("digit", mEditSecondSingleDigit.getText().toString().trim());
                                                    editor.putString("drawtimeSession",session);
//                                                }


                                                    flag = 2;
//                                                int res = (int) (Integer.parseInt(mEditSecondSingleDigit.getText().toString().trim()) * 9.0);
//
//                                                int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
//                                                int amt = bal - res;
//                                                editor.putString("present_amount", ""+amt);

                                                    editor.commit();


                                                    getPresentAmount();
                                                    mEditSecondSingleDigit.getText().clear();
                                                    mEditSecondSingleAmout.getText().clear();
                                                    mSecondSingleDigitReturn.setHint("00");
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (error instanceof TimeoutError) {
                                                Toast.makeText(getActivity(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                            }
                                            error.printStackTrace();
                                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                                            pDialog.hide();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            HashMap<String, String> map = new HashMap<>();
                                            map.put("player_id", playerId);
                                            map.put("data[0][digit]", mEditSecondSingleDigit.getText().toString().trim());
                                            map.put("data[0][bet_amount]", mEditSecondSingleAmout.getText().toString().trim());
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
                                } else {
                                    Toast.makeText(getActivity(), "Draw is closed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), getString(R.string.internet_err), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                break;
            case R.id.btn_jodi_place_bet:

                    try
                    {

                        if (TextUtils.isEmpty(mEditJodi.getText().toString().trim())) {
                            mEditJodi.requestFocus();
                            mEditJodi.setFocusable(true);
                            mEditJodi.setError("please enter digit");
                            view = mEditJodi;
                            cancel = true;
                        }
                        else if(TextUtils.isEmpty(mEditJodiAmout.getText().toString().trim()))
                        {
                            mEditJodiAmout.requestFocus();
                            mEditJodiAmout.setFocusable(true);
                            mEditJodiAmout.setError("please enter chips");
                            view = mEditJodiAmout;
                            cancel = true;
                        }
                        else if(Integer.parseInt(mEditJodiAmout.getText().toString().trim())>Double.parseDouble(result)) {
                            mEditJodiAmout.requestFocus();
                            mEditJodiAmout.setFocusable(true);
                            mEditJodiAmout.setError("Not enough chips found");
                            view = mEditJodiAmout;
                            cancel = true;
                        }
                        else if(Integer.parseInt(mEditJodiAmout.getText().toString().trim())<20)
                        {
                            mEditJodiAmout.requestFocus();
                            mEditJodiAmout.setFocusable(true);
                            mEditJodiAmout.setError("please enter chips greater than 20");
                            view = mEditJodiAmout;
                            cancel = true;
                        }
                        else if(Integer.parseInt(mEditJodiAmout.getText().toString().trim())%2!=0)
                        {
                            mEditJodiAmout.requestFocus();
                            mEditJodiAmout.setFocusable(true);
                            mEditJodiAmout.setError("please enter valid chips!!!");
                            view = mEditJodiAmout;
                            cancel = true;
                        }
                        else {
                            if (connectionDetector.isConnectingToInternet()) {
                                //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
                                if (!session.equals("00:00 am")) {
                                    String tag_json_obj = "json_obj_req";
                                    final String TAG = "response";
                                    final String url = getString(R.string.PlaceBetJodi);//+ URLEncoder.encode("/"+postString);

                                    //          final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                                                if (object.getString("status").equals("true")) {
                                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
//                                                if(flag==3)
//                                                {
                                                    editor.putString("latest_bet", mEditJodi.getText().toString().trim());
                                                    editor.putString("bet_amount", mEditJodiAmout.getText().toString().trim());
                                                    editor.putString("game_type", "3");
                                                    editor.putString("digit", mEditJodi.getText().toString().trim());
                                                    editor.putString("drawtimeSession",session);
//                                                }


                                                    flag = 3;
                                                    JodiNo = Integer.parseInt(mEditJodi.getText().toString().trim());

//                                                int res = (int) (Integer.parseInt(mEditSecondSingleDigit.getText().toString().trim()) * 90);
//
//
//                                                int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
//                                                int amt = bal - res;
//                                                editor.putString("present_amount", ""+amt);

                                                    editor.commit();
                                                    getPresentAmount();

//                                                balanceStatus.setProgress(amt);

                                                    mEditJodi.getText().clear();
                                                    mEditJodiAmout.getText().clear();
                                                    mJodiReturn.setHint("00");
                                                }

                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (error instanceof TimeoutError) {
                                                Toast.makeText(getActivity(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                            }
                                            error.printStackTrace();
                                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                                            pDialog.hide();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Log.i(" Perems ", "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim());
                                            HashMap<String, String> map = new HashMap<>();
                                            map.put("player_id", playerId);
                                            map.put("data[0][digit]", mEditJodi.getText().toString().trim());
                                            map.put("data[0][bet_amount]", mEditJodiAmout.getText().toString().trim());
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
                                } else {
                                    Toast.makeText(getActivity(), "Draw is closed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getActivity(), getString(R.string.internet_err), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                break;
        }
    }



    public class MyTimer extends TimerTask {

        long time;
        Context context;
        String Result;
        public MyTimer(long time,Context context)
        {
            this.time=time;
            this.context=context;
        }

        @Override
        public void run() {
            Log.i("Timerlog", "" + (System.currentTimeMillis() - time));

            if((System.currentTimeMillis()-time)>=900000) {
                Log.i("Timerlog", "trigger");
                new AsynTask().execute();
                this.cancel();
            }

        }

        class AsynTask extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Result = new ApiCall(context).HttpGet("http://lottery.pixmadness.in/api/bets/LuckyNumber");
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                return Result;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try
                {
                    //Toast.makeText(getActivity(),""+result,Toast.LENGTH_SHORT).show();
                    JSONObject object=new JSONObject(result);
                    /*{
                        status: true
                        lucky_number: "31"
                    }*/
                    if(object.getString("status").equals("true"))
                    {
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                            editor.putString("latest_bet","not_placed");
                            editor.putString("geme_type","-1");
                            editor.commit();
                        if(flag==1)
                        {
                            //for(int i = 0; i < object.getString("lucky_number").length(); i++) {
                                int j = Character.digit(object.getString("lucky_number").charAt(0), 10);
                                System.out.println("digit: " + j);
                            //}
                            if(FirstNo==j)
                            {
                                Toast.makeText(getActivity(),"Congratulation,You Win The Bet",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Sorry,You Lose The Bet",Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(flag==2)
                        {
                            if(SecondNo==Integer.parseInt(object.getString("lucky_number")))
                            {
                                int j = Character.digit(object.getString("lucky_number").charAt(1), 10);
                                System.out.println("digit: " + j);
                                //}
                                if(SecondNo==j)
                                {
                                    Toast.makeText(getActivity(),"Congratulation,You Win The Bet",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Sorry,You Lose The Bet",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        if(flag==3)
                        {
                            if(JodiNo==Integer.parseInt(object.getString("lucky_number")))
                            {
                                Toast.makeText(getActivity(),"Congratulation,You Win The Bet",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }



    private void CurrentResult()
    {
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
                            session=  innerObject.getString("end");
                            String time=innerObject.getString("current");
                            String split[]=time.split(" ");
                            String split1[]=split[1].split(" ");
                            String splithrs[]=split1[0].split(":");
                            Log.i("min",""+splithrs[1]);


                            mCurrentSession.setText("Current Draw: " + innerObject.getString("end"));
                            mCurrentResult.setText(innerObject.getString("lucky_number"));

                            int m1=Integer.parseInt(splithrs[1]);
                            int s1=Integer.parseInt(splithrs[2]);
                            if ( m1>= 45) {
                                m1 = 59 - m1;
                            } else if (m1 >= 30) {
                                m1 = 44 - m1;
                            } else if (m1 >= 15) {
                                m1 = 29 - m1;
                            } else if (m1 < 15) {
                                m1 = 14 - m1;
                            }

                            if (s1 == 60) {
                                s1 = 00;
                            } else if (s1 == 0) {
                                s1 = 60;
                            } else if (s1 > 0 && s1 < 60) {
                                s1 = 60 - s1;
                            }

                            currenttime = String.valueOf(m1)+":"+splithrs[2];
                            Log.i("current time",""+currenttime);

                            long timelong=((m1*60)+s1)*1000;
                            if(flg!=0){
                                counterClass.cancel();
                                flg=0;
                            }
                            sec=timelong;
                            counterClass = new CounterClass(sec,1000);
                            counterClass.start();
                            flg=1;

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

    private void countDown(final TextView tv, final int count) {
        if (count == 0) {
            //tv.setText(""); //Note: the TextView will be visible again here.
            return;
        }
        //tv.setText(count);
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                countDown(tv, count - 1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        tv.startAnimation(animation);
    }



@Override
    public void onDestroy() {
        super.onDestroy();
        counterClass.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        counterClass.cancel();
    }

    public class CounterClass extends CountDownTimer
    {
        String hms;
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            try {

                mTimer.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                int min=(int)TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - (int)TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                int sec=(int)TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - (int)TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                editor.putInt("currentMinute", min);
                editor.putInt("currentSecond",sec);
                editor.commit();
//                mTimer.setText("" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) + ":" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0));
                if(min == 0 && sec <= 10 ){
                    countDown(mCurrentResult, 10);
                }
               /* if (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) == 14 &&
                        (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) >= 51 &&
                                getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) <= 60)) {
                    countDown(mCurrentResult, 14);
                }*/

                if(min == 00 &&
                        sec == 01 ){
                    if(flg!=0){
                        flg=0;
                        counterClass.cancel();

                    }
                    sec=900000;
                    counterClass = new CounterClass(sec,1000);
                    counterClass.start();
                    flg=1;
                }

                if (min == 14 && sec== 50 && sec >= 40) {
                    CurrentResult();
                }

                singleFirstLuckyNo();
                singleSecondLuckyNo();
                jodiLuckyNo();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {

            this.start();
        }
    }

//get lucky no if single first bet is placed
    private void singleFirstLuckyNo()
    {
        if(flag==1 && (getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 14 &&
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentSecond",0) == 40))
        {
            flag=0;
            //Toast.makeText(getActivity(),"Api Call For First",Toast.LENGTH_SHORT).show();

            if(connectionDetector.isConnectingToInternet())
            {
                //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
                String tag_json_obj = "json_obj_req";
                final String TAG = "response";
                final String url = getString(R.string.get_result);//+ URLEncoder.encode("/"+postString);

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
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                                editor.putString("latest_bet","not_placed");
                                editor.putString("geme_type","-1");
                                editor.putString("bet_amount","no_amount");
                                editor.commit();

                                if(FirstNo == Integer.parseInt(object.getString("lucky_number")))
                                {
                                    Toast.makeText(getActivity(),"Congratulation, You Won The Bet.",Toast.LENGTH_SHORT).show();
                                    try {

                                        double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (FirstNo * 9.0);
                                        balanceStatus.setProgress((int) result);
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Sorry, You loss the bet.",Toast.LENGTH_SHORT).show();
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
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //    pDialog.hide();
                    }
                });

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

//get lucky no if single second bet is placed
    private void singleSecondLuckyNo()
    {
        if(flag == 2 && (getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 14 &&
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 40))
        {
            flag=0;
            //Toast.makeText(getActivity(),"Api Call For Second",Toast.LENGTH_SHORT).show();

            if(connectionDetector.isConnectingToInternet())
            {
                //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
                String tag_json_obj = "json_obj_req";
                final String TAG = "response";
                final String url = getString(R.string.get_result);//+ URLEncoder.encode("/"+postString);

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
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                                editor.putString("latest_bet","not_placed");
                                editor.putString("geme_type","-1");
                                editor.putString("bet_amount","no_amount");
                                editor.commit();

                                if(SecondNo == Integer.parseInt(object.getString("lucky_number")))
                                {
                                    Toast.makeText(getActivity(),"Congratulation, You Won The Bet.",Toast.LENGTH_SHORT).show();
                                    try {
                                        double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (SecondNo * 9.0);
                                        balanceStatus.setProgress((int) result);
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Sorry, You loss the bet.",Toast.LENGTH_SHORT).show();
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
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //    pDialog.hide();
                    }
                });

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

//get lucky no if Jodi bet is placed
    private void jodiLuckyNo()
    {
        if(flag==3 && (getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 14 &&
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 40))
        {
            flag=0;
            //Toast.makeText(getActivity(),"Api Call For Third",Toast.LENGTH_SHORT).show();

            if(connectionDetector.isConnectingToInternet())
            {
                //            postString = "player_id=" + playerId + "&data[0][digit]=" + mEditJodi.getText().toString().trim() + "&data[0][bet_amount]=" + mEditJodiAmout.getText().toString().trim();
                            /*JodiNo=Integer.parseInt(mEditJodi.getText().toString().trim());
                            flag = 3;
                            new AsynTaskCall().execute(getString(R.string.PlaceBetJodi),postString);

*/
                String tag_json_obj = "json_obj_req";
                final String TAG = "response";
                final String url = getString(R.string.get_result);//+ URLEncoder.encode("/"+postString);

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
                                SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                                editor.putString("latest_bet","not_placed");
                                editor.putString("geme_type","-1");
                                editor.putString("bet_amount","no_amount");
                                editor.commit();

                                if(JodiNo == Integer.parseInt(object.getString("lucky_number")))
                                {
                                    Toast.makeText(getActivity(),"Congratulation, You Won The Bet.",Toast.LENGTH_SHORT).show();
                                    try {
                                        int result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (JodiNo * 90);
                                        balanceStatus.setProgress(result);
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Sorry, You loss the bet.",Toast.LENGTH_SHORT).show();
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
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
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

    @Override
    public void onResume() {
        super.onResume();
        CurrentResult();
    }
}
