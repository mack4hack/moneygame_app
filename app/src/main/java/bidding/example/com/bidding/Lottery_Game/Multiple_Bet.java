package bidding.example.com.bidding.Lottery_Game;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import bidding.example.com.bidding.AppControler;
import bidding.example.com.bidding.AppDB.DbAdapter;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.ModelClass;
import bidding.example.com.bidding.R;

public class Multiple_Bet extends Fragment implements View.OnClickListener{

    static boolean status=false;
    private HashMap<String,String> parameter;
    private List<ModelClass> ApiParameter;
    private ProgressDialog dialog;
    private ProgressBar balanceStatus;
    private TextView FirstTextView0,FirstTextView1,FirstTextView2,FirstTextView3,FirstTextView4,FirstTextView5,FirstTextView6,FirstTextView7,FirstTextView8,FirstTextView9;
    private EditText FirstEditText0,FirstEditText1,FirstEditText2,FirstEditText3,FirstEditText4,FirstEditText5,FirstEditText6,FirstEditText7,FirstEditText8,FirstEditText9;
    private TextView text_progress, text_bet_ttl1, text_bet_ttl2, text_bet_ttl3;
    private TextView SecondTextView0,SecondTextView1,SecondTextView2,SecondTextView3,SecondTextView4,SecondTextView5,SecondTextView6,SecondTextView7,SecondTextView8,SecondTextView9;
    private EditText SecondEditText0,SecondEditText1,SecondEditText2,SecondEditText3,SecondEditText4,SecondEditText5,SecondEditText6,SecondEditText7,SecondEditText8,SecondEditText9;
    private ConnectionDetector connectionDetector;
    private TextView mTimer,mCurrentResult,mCurrentSession;
    private Button FirstBtnPlaceBet,SecondBtnPlaceBet,JodiBtnPlaceBet,AutoFill;

    private EditText jodiNumberEditText0,jodiNumberEditText1,jodiNumberEditText2,jodiNumberEditText3,jodiNumberEditText4,jodiNumberEditText5,jodiNumberEditText6,jodiNumberEditText7,jodiNumberEditText8,jodiNumberEditText9;
    private EditText jodiAmountEditText0,jodiAmountEditText1,jodiAmountEditText2,jodiAmountEditText3,jodiAmountEditText4,jodiAmountEditText5,jodiAmountEditText6,jodiAmountEditText7,jodiAmountEditText8,jodiAmountEditText9;

    private String[] number;//=new String[10];
    private String[] amount;//=new String[10];

    private String[] number2;//=new String[10];
    private String[] amount2;//=new String[10];

    private String[] number3;//=new String[10];
    private String[] amount3;//=new String[10];

    private int currentMinute,currentHoure;
    private static int flag;
    static String no1, no2, no3;
    String result, session, currenttime;
    Double default_amnt, prsnt_amnt, diff, percent;
    Integer total1=0;

    public SharedPreferences preferences;
    CounterClass counterClass;
    HorizontalScrollView scroll1, scroll2, scroll3;

    private static final String FORMAT = "%02d:%02d";
    long millis=900000, sec;
    public static int flg=0;

    private ProgressDialog pDialog;
    public Multiple_Bet() {
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
        return inflater.inflate(R.layout.fragment_multiple__bet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initization(view);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences=getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE);

        FirstBtnPlaceBet.setOnClickListener(this);
        SecondBtnPlaceBet.setOnClickListener(this);
        JodiBtnPlaceBet.setOnClickListener(this);
        AutoFill.setOnClickListener(this);

    }

    private void initization(View v)
    {

        pDialog = new ProgressDialog(getActivity());
        connectionDetector = new ConnectionDetector(getActivity());
        mTimer= (TextView) v.findViewById(R.id.time_left);
        mCurrentResult = (TextView) v.findViewById(R.id.current_result);
        mCurrentSession= (TextView) v.findViewById(R.id.current_seesion);
        text_progress = (TextView) v.findViewById(R.id.txtprogress2);

        //First
        FirstEditText0= (EditText) v.findViewById(R.id.firsteditText0);
        FirstEditText1= (EditText) v.findViewById(R.id.firsteditText1);
        FirstEditText2= (EditText) v.findViewById(R.id.firsteditText2);
        FirstEditText3= (EditText) v.findViewById(R.id.firsteditText3);
        FirstEditText4= (EditText) v.findViewById(R.id.firsteditText4);
        FirstEditText5= (EditText) v.findViewById(R.id.firsteditText5);
        FirstEditText6= (EditText) v.findViewById(R.id.firsteditText6);
        FirstEditText7= (EditText) v.findViewById(R.id.firsteditText7);
        FirstEditText8= (EditText) v.findViewById(R.id.firsteditText8);
        FirstEditText9= (EditText) v.findViewById(R.id.firsteditText9);

        FirstTextView0= (TextView) v.findViewById(R.id.firsttextview0);
        FirstTextView1= (TextView) v.findViewById(R.id.firsttextview1);
        FirstTextView2= (TextView) v.findViewById(R.id.firsttextview2);
        FirstTextView3= (TextView) v.findViewById(R.id.firsttextview3);
        FirstTextView4= (TextView) v.findViewById(R.id.firsttextview4);
        FirstTextView5= (TextView) v.findViewById(R.id.firsttextview5);
        FirstTextView6= (TextView) v.findViewById(R.id.firsttextview6);
        FirstTextView7= (TextView) v.findViewById(R.id.firsttextview7);
        FirstTextView8= (TextView) v.findViewById(R.id.firsttextview8);
        FirstTextView9= (TextView) v.findViewById(R.id.firsttextview9);

    //Second
        SecondEditText0= (EditText) v.findViewById(R.id.secondeditText0);
        SecondEditText1= (EditText) v.findViewById(R.id.secondeditText1);
        SecondEditText2= (EditText) v.findViewById(R.id.secondeditText2);
        SecondEditText3= (EditText) v.findViewById(R.id.secondeditText3);
        SecondEditText4= (EditText) v.findViewById(R.id.secondeditText4);
        SecondEditText5= (EditText) v.findViewById(R.id.secondeditText5);
        SecondEditText6= (EditText) v.findViewById(R.id.secondeditText6);
        SecondEditText7= (EditText) v.findViewById(R.id.secondeditText7);
        SecondEditText8= (EditText) v.findViewById(R.id.secondeditText8);
        SecondEditText9= (EditText) v.findViewById(R.id.secondeditText9);

        SecondTextView0= (TextView) v.findViewById(R.id.secondtextview0);
        SecondTextView1= (TextView) v.findViewById(R.id.secondtextview1);
        SecondTextView2= (TextView) v.findViewById(R.id.secondtextview2);
        SecondTextView3= (TextView) v.findViewById(R.id.secondtextview3);
        SecondTextView4= (TextView) v.findViewById(R.id.secondtextview4);
        SecondTextView5= (TextView) v.findViewById(R.id.secondtextview5);
        SecondTextView6= (TextView) v.findViewById(R.id.secondtextview6);
        SecondTextView7= (TextView) v.findViewById(R.id.secondtextview7);
        SecondTextView8= (TextView) v.findViewById(R.id.secondtextview8);
        SecondTextView9= (TextView) v.findViewById(R.id.secondtextview9);

     //Jodi
        jodiNumberEditText0= (EditText) v.findViewById(R.id.jodiNumberEditText0);
        jodiNumberEditText1= (EditText) v.findViewById(R.id.jodiNumberEditText1);
        jodiNumberEditText2= (EditText) v.findViewById(R.id.jodiNumberEditText2);
        jodiNumberEditText3= (EditText) v.findViewById(R.id.jodiNumberEditText3);
        jodiNumberEditText4= (EditText) v.findViewById(R.id.jodiNumberEditText4);
        jodiNumberEditText5= (EditText) v.findViewById(R.id.jodiNumberEditText5);
        jodiNumberEditText6= (EditText) v.findViewById(R.id.jodiNumberEditText6);
        jodiNumberEditText7= (EditText) v.findViewById(R.id.jodiNumberEditText7);
        jodiNumberEditText8= (EditText) v.findViewById(R.id.jodiNumberEditText8);
        jodiNumberEditText9= (EditText) v.findViewById(R.id.jodiNumberEditText9);

        jodiAmountEditText0= (EditText) v.findViewById(R.id.jodiAmountEditText0);
        jodiAmountEditText1= (EditText) v.findViewById(R.id.jodiAmountEditText1);
        jodiAmountEditText2= (EditText) v.findViewById(R.id.jodiAmountEditText2);
        jodiAmountEditText3= (EditText) v.findViewById(R.id.jodiAmountEditText3);
        jodiAmountEditText4= (EditText) v.findViewById(R.id.jodiAmountEditText4);
        jodiAmountEditText5= (EditText) v.findViewById(R.id.jodiAmountEditText5);
        jodiAmountEditText6= (EditText) v.findViewById(R.id.jodiAmountEditText6);
        jodiAmountEditText7= (EditText) v.findViewById(R.id.jodiAmountEditText7);
        jodiAmountEditText8= (EditText) v.findViewById(R.id.jodiAmountEditText8);
        jodiAmountEditText9= (EditText) v.findViewById(R.id.jodiAmountEditText9);

        FirstBtnPlaceBet= (Button) v.findViewById(R.id.btnFirstBet);
        SecondBtnPlaceBet= (Button) v.findViewById(R.id.btnSecondBet);
        JodiBtnPlaceBet= (Button) v.findViewById(R.id.btnJodiBet);
        AutoFill = (Button) v.findViewById(R.id.btnautofill);
        scroll1 = (HorizontalScrollView) v.findViewById(R.id.scrollView1);

        balanceStatus = (ProgressBar) v.findViewById(R.id.ProgressBar02);
        balanceStatus.setMax((int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("default_amt", ""))));
        getPresentAmount();
       /* try {

            if (Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", ""))) < 0) {
                balanceStatus.setProgress(0);
            } else {
                int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
                balanceStatus.setProgress(bal);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/

        flg=0;
        new CountDownTimer(900000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {


            }

            public void onFinish() {
            }
        }.start();
       /* try
        {
            Thread myThread = null;
            Runnable myRunnableThread = new CountDownRunner();
            myThread= new Thread(myRunnableThread);
            myThread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        CurrentResult();
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
                                text_progress.setText(result);

                            }

                            if(default_amnt>prsnt_amnt){
                                diff = default_amnt-prsnt_amnt;
                                Log.i("percentag",""+diff);
                                percent = (diff/default_amnt)*100;
                                Log.i("percentag",""+default_amnt);
                                Log.i("percentag",""+(default_amnt/diff));

                            }
                            else {
                                diff = prsnt_amnt-default_amnt;
                                percent = (diff/default_amnt)*100;

                            }
                        }

                    } catch (Exception e) {
                        pDialog.hide();
                        Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnFirstBet:
                if (FirstEditText0.getText().toString().equals("0") && FirstEditText1.getText().toString().equals("0") &&
                        FirstEditText2.getText().toString().equals("0") && FirstEditText3.getText().toString().equals("0") &&
                        FirstEditText4.getText().toString().equals("0") && FirstEditText5.getText().toString().equals("0") &&
                        FirstEditText6.getText().toString().equals("0") && FirstEditText7.getText().toString().equals("0") &&
                        FirstEditText8.getText().toString().equals("0") && FirstEditText9.getText().toString().equals("0")) {
                    Toast.makeText(getActivity(), "please enter chips!!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(FirstEditText0.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText1.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText2.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText3.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText4.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText5.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText6.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText7.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText8.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText9.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "please enter chips!!!", Toast.LENGTH_SHORT).show();
                }

             /*   else if(Integer.parseInt(FirstEditText0.getText().toString())%20 ==0  && Integer.parseInt(FirstEditText1.getText().toString())%20 ==0 &&
                        Integer.parseInt(FirstEditText2.getText().toString())%20 ==0 && Integer.parseInt(FirstEditText3.getText().toString())%20 ==0 &&
                        Integer.parseInt(FirstEditText4.getText().toString())%20 ==0 && Integer.parseInt(FirstEditText5.getText().toString())%20 ==0 &&
                        Integer.parseInt(FirstEditText6.getText().toString())%20 ==0 && Integer.parseInt(FirstEditText7.getText().toString())%20 ==0 &&
                        Integer.parseInt(FirstEditText8.getText().toString())%20 ==0 && Integer.parseInt(FirstEditText9.getText().toString())%20 ==0){
                    Toast.makeText(getActivity(), "please enter amount!!!", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    number = new String[10];
                    amount = new String[10];
                    String msg = "please enter valid chips at position ";
                    if (!TextUtils.isEmpty(FirstEditText0.getText().toString().trim())) {
                        number[0] = "0";
                        amount[0] = FirstEditText0.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(FirstEditText1.getText().toString().trim())) {

                        number[1] = "1";
                        amount[1] = FirstEditText1.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(FirstEditText2.getText().toString().trim())) {
                        number[2] = "2";
                        amount[2] = FirstEditText2.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(FirstEditText3.getText().toString().trim())) {
                        number[3] = "3";
                        amount[3] = FirstEditText3.getText().toString().trim();
                    }

                    if (!TextUtils.isEmpty(FirstEditText4.getText().toString().trim())) {
                        number[4] = "4";
                        amount[4] = FirstEditText4.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(FirstEditText5.getText().toString().trim())) {
                        number[5] = "5";
                        amount[5] = FirstEditText5.getText().toString().trim();
                    }

                    if (!TextUtils.isEmpty(FirstEditText6.getText().toString().trim())) {
                        number[6] = "6";
                        amount[6] = FirstEditText6.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(FirstEditText7.getText().toString().trim())) {
                        number[7] = "7";
                        amount[7] = FirstEditText7.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(FirstEditText8.getText().toString().trim())) {
                        number[8] = "8";
                        amount[8] = FirstEditText8.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(FirstEditText9.getText().toString().trim())) {

                        number[9] = "9";
                        amount[9] = FirstEditText9.getText().toString().trim();

                    }

                    if (connectionDetector.isConnectingToInternet()) {
                        for (int i = 0; i < 10; i++) {
                            if (amount[i] != null) {
                                if((int)Math.round(Double.parseDouble(amount[i]))>(int)Math.round(Double.parseDouble(result))){
                                    Toast.makeText(getActivity(), "Not enough chips found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (Integer.parseInt(amount[i]) < 20 || Integer.parseInt(amount[i])%20!=0) {
                                    Toast.makeText(getActivity(), msg + i, Toast.LENGTH_SHORT).show();
                                    return;

                                }
                                else
                                {
//                                    break;
                                }
                            }
                        }
                        int amt = 0;
                        for (int j = 0; j < 10; j++) {
                            if (amount[j] != null) {
                                amt += Integer.parseInt(amount[j]);
                            }
                        }
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Confirmation");
                        dialog.setMessage("You are about to place bet of: "+amt+ " chips");
                        dialog.setCancelable(true);
                        // ...Irrelevant code for customizing the buttons and title

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!session.equals("00:00 am")) {
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
                                            Log.d(TAG, response.toString());
                                            pDialog.hide();
                                            try {
                                                JSONObject object = new JSONObject(response);
                                                if (object.getString("status").equals("true")) {
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                                    flag = 1;

                                                    int amt = 0;
                                                    for (int j = 0; j < 10; j++) {
                                                        if (amount[j] != null) {
                                                            amt += Integer.parseInt(amount[j]);
                                                        }
                                                    }
                                                    //int result = (int) (Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) - (amt * 9.0));
//                                        text_bet_ttl1.setText("Bets Total: "+amt);
                                                    int res = (int) (amt * 9.0);

//                                        int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
//                                        int famt = bal - res;

                                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
                                                    editor.putString("latest_bet", no1);
                                                    editor.putString("bet_amount", String.valueOf(amt));
                                                    editor.putString("digit", no1);
                                                    editor.putString("game_type", "1");
                                                    editor.putString("drawtimeSession",session);
//                                        editor.putString("present_amount",""+famt);
                                                    editor.commit();
                                                    getPresentAmount();
//                                        balanceStatus.setProgress(famt);

                                                    for (int i = 0; i < 10; i++) {
                                                        number[i] = "";
                                                        amount[i] = "";
                                                    }
                                                    clearFields(1);
                                                } else {
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                            error.printStackTrace();
                                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                                            pDialog.hide();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            HashMap<String, String> map = new HashMap<>();
                                            int cnt = 0;
                                            map.put("player_id", getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                                            for (int i = 0; i < number.length; i++) {
                                                if (number[i] != null) {
                                                    no1 = number[i];
                                                    map.put("data[" + cnt + "][digit]", number[i]);
                                                    map.put("data[" + cnt + "][bet_amount]", amount[i]);
                                                    cnt++;
                                                }
                                            }
                                            for (String key : map.keySet()) {
                                                Log.i("vales", "key = " + key + " val = " + map.get(key));
                                            }
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
                                else{
                                    Toast.makeText(getActivity(), "Draw is closed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        dialog.create();
                        dialog.show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.internet_err), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btnSecondBet:
                if (SecondEditText0.getText().toString().equals("0") && SecondEditText1.getText().toString().equals("0") &&
                        SecondEditText2.getText().toString().equals("0") && SecondEditText3.getText().toString().equals("0") &&
                        SecondEditText4.getText().toString().equals("0") && SecondEditText5.getText().toString().equals("0") &&
                        SecondEditText6.getText().toString().equals("0") && SecondEditText7.getText().toString().equals("0") &&
                        SecondEditText8.getText().toString().equals("0") && SecondEditText9.getText().toString().equals("0"))
                {
                    Toast.makeText(getActivity(), "please enter chips!!!", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(SecondEditText0.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText1.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText2.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText3.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText4.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText5.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText6.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText7.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText8.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText9.getText().toString().trim()))
                {

                    Toast.makeText(getActivity(), "please enter chips!!!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    number2 = new String[10];
                    amount2 = new String[10];
                    StringBuilder builder = new StringBuilder();
                    String msg = "please enter chips greater than 20 for digit ";

                    if (!TextUtils.isEmpty(SecondEditText0.getText().toString().trim())) {
                        number2[0] = "0";
                        amount2[0] = SecondEditText0.getText().toString().trim();
                    }

                    if (!TextUtils.isEmpty(SecondEditText1.getText().toString().trim())) {
                        number2[1] = "1";
                        amount2[1] = SecondEditText1.getText().toString().trim();
                    }

                    if (!TextUtils.isEmpty(SecondEditText2.getText().toString().trim())) {
                        number2[2] = "2";
                        amount2[2] = SecondEditText2.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(SecondEditText3.getText().toString().trim())) {
                        number2[3] = "3";
                        amount2[3] = SecondEditText3.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(SecondEditText4.getText().toString().trim())) {
                        number2[4] = "4";
                        amount2[4] = SecondEditText4.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(SecondEditText5.getText().toString().trim())) {
                        number2[5] = "5";
                        amount2[5] = SecondEditText5.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(SecondEditText6.getText().toString().trim())) {
                        number2[6] = "6";
                        amount2[6] = SecondEditText6.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(SecondEditText7.getText().toString().trim())) {
                        number2[7] = "7";
                        amount2[7] = SecondEditText7.getText().toString().trim();
                    }

                    if (!TextUtils.isEmpty(SecondEditText8.getText().toString().trim())) {
                        number2[8] = "8";
                        amount2[8] = SecondEditText8.getText().toString().trim();
                    }


                    if (!TextUtils.isEmpty(SecondEditText9.getText().toString().trim())) {
                        number2[9] = "9";
                        amount2[9] = SecondEditText9.getText().toString().trim();
                    }

                    if (connectionDetector.isConnectingToInternet()) {
                        for (int i = 0; i < 10; i++) {
                            if (amount2[i] != null) {
                                if((int)Math.round(Double.parseDouble(amount2[i]))>(int)Math.round(Double.parseDouble(result))){
                                    Toast.makeText(getActivity(), "Not enough chips found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if (Integer.parseInt(amount2[i]) < 20 || Integer.parseInt(amount2[i])%20!=0) {
                                    Toast.makeText(getActivity(), msg + i, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else
                                {
//                                    break;
                                }
                            }
                        }
                        int amt = 0;
                        for (int j = 0; j < 10; j++) {
                            if (amount2[j] != null) {
                                amt += Integer.parseInt(amount2[j]);
                            }
                        }
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Confirmation");
                        dialog.setMessage("You are about to place bet of: "+amt+ " chips");
                        dialog.setCancelable(true);
                        // ...Irrelevant code for customizing the buttons and title

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!session.equals("00:00 am")) {
                                    String tag_json_obj = "json_obj_req";
                                    final String TAG = "response";
                                    final String url = getString(R.string.PlaceBetSecond);//+ URLEncoder.encode("/"+postString);

                                    //final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                                                    flag = 2;

                                                    int amt = 0;
                                                    for (int j = 0; j < 10; j++) {
                                                        if (amount2[j] != null) {
                                                            amt += Integer.parseInt(amount2[j]);
                                                        }
                                                    }
                                                    int res = (int) (amt * 9.0);
//                                        text_bet_ttl2.setText("Bets Total: :"+amt);

//                                        int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
//                                        int famt = bal - res;

                                                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
                                                    editor.putString("latest_bet", no2);
                                                    editor.putString("bet_amount", String.valueOf(amt));
                                                    editor.putString("digit", no2);
                                                    editor.putString("game_type", "1");
                                                    editor.putString("drawtimeSession",session);
//                                        editor.putString("present_amount",""+famt);
                                                    editor.commit();
                                                    getPresentAmount();
//                                        balanceStatus.setProgress(famt);

                                                    for (int i = 0; i < 10; i++) {
                                                        number2[i] = "";
                                                        amount2[i] = "";
                                                    }
                                                    clearFields(2);
                                                } else {
                                                    Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                            error.printStackTrace();
                                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                                            pDialog.hide();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            HashMap<String, String> map = new HashMap<>();
                                            int cnt = 0;
                                            map.put("player_id", getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                                            for (int i = 0; i < number2.length; i++) {
                                                if (number2[i] != null) {
                                                    no2 = number2[i];
                                                    map.put("data[" + cnt + "][digit]", number2[i]);
                                                    map.put("data[" + cnt + "][bet_amount]", amount2[i]);
                                                    cnt++;
                                                }
                                            }

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
                                else {
                                    Toast.makeText(getActivity(),"Draw is closed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        dialog.create();
                        dialog.show();
                    } else {
                        Toast.makeText(getActivity(),"please check internet connection!!!", Toast.LENGTH_SHORT).show();
                    }
                    //new AsynTaskCall().execute(getString(R.string.PlaceBetSecond),builder.toString());
                }
                break;

            case R.id.btnJodiBet:
                if(connectionDetector.isConnectingToInternet())
                {
                    if(TextUtils.isEmpty(jodiNumberEditText0.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText1.getText().toString().trim()) &&
                       TextUtils.isEmpty(jodiNumberEditText2.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText3.getText().toString().trim()) &&
                       TextUtils.isEmpty(jodiNumberEditText4.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText5.getText().toString().trim()) &&
                       TextUtils.isEmpty(jodiNumberEditText6.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText7.getText().toString().trim()) &&
                       TextUtils.isEmpty(jodiNumberEditText8.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText9.getText().toString().trim()))
                    {
                        Toast.makeText(getActivity(),"please enter number!!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(jodiAmountEditText0.getText().toString().trim()) && TextUtils.isEmpty(jodiAmountEditText1.getText().toString()) &&
                            TextUtils.isEmpty(jodiAmountEditText2.getText().toString().trim()) && TextUtils.isEmpty(jodiAmountEditText3.getText().toString().trim()) &&
                            TextUtils.isEmpty(jodiAmountEditText4.getText().toString().trim()) && TextUtils.isEmpty(jodiAmountEditText5.getText().toString().trim()) &&
                            TextUtils.isEmpty(jodiAmountEditText6.getText().toString().trim()) && TextUtils.isEmpty(jodiAmountEditText7.getText().toString().trim()) &&
                            TextUtils.isEmpty(jodiAmountEditText8.getText().toString().trim()) && TextUtils.isEmpty(jodiAmountEditText9.getText().toString().trim()))
                    {
                        Toast.makeText(getActivity(),"please enter chips!!!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        number3 = new String[10];
                        amount3 = new String[10];

                        if (!TextUtils.isEmpty(jodiNumberEditText0.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText0.getText().toString().trim())) {

                                number3[0] = jodiNumberEditText0.getText().toString().trim();
                                amount3[0] = jodiAmountEditText0.getText().toString().trim();

                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText1.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText1.getText().toString().trim())) {
                            number3[1] = jodiNumberEditText1.getText().toString().trim();
                            amount3[1] = jodiAmountEditText1.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText2.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText2.getText().toString().trim())) {
                            number3[2] = jodiNumberEditText2.getText().toString().trim();
                            amount3[2] = jodiAmountEditText2.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText3.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText3.getText().toString().trim())) {
                            number3[3] = jodiNumberEditText3.getText().toString().trim();
                            amount3[3] = jodiAmountEditText3.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText4.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText4.getText().toString().trim())) {
                            number3[4] = jodiNumberEditText4.getText().toString().trim();
                            amount3[4] = jodiAmountEditText4.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText5.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText5.getText().toString().trim())) {
                            number3[5] = jodiNumberEditText5.getText().toString().trim();
                            amount3[5] = jodiAmountEditText5.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText6.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText6.getText().toString().trim())) {
                            number3[6] = jodiNumberEditText6.getText().toString().trim();
                            amount3[6] = jodiAmountEditText6.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText7.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText7.getText().toString().trim())) {
                            number3[7] = jodiNumberEditText7.getText().toString().trim();
                            amount3[7] = jodiAmountEditText7.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText8.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText8.getText().toString().trim())) {
                            number3[8] = jodiNumberEditText8.getText().toString().trim();
                            amount3[8] = jodiAmountEditText8.getText().toString().trim();
                        }

                        if (!TextUtils.isEmpty(jodiNumberEditText9.getText().toString().trim()) && !TextUtils.isEmpty(jodiAmountEditText9.getText().toString().trim())) {
                            number3[9] = jodiNumberEditText9.getText().toString().trim();
                            amount3[9] = jodiAmountEditText9.getText().toString().trim();
                        }

                        for(int j = 0; j<number3.length; j++){
                            if(number3[j] != null) {
                                if (number3[j].length() != 2) {
                                    Toast.makeText(getActivity(), "please enter valid number", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                        for(int i=0;i<amount3.length;i++)
                        {
                            if(amount3[i] != null)
                            {
                                if((int)Math.round(Double.parseDouble(amount3[i]))>(int)Math.round(Double.parseDouble(result))){
                                    Toast.makeText(getActivity(), "Not enough chips found", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if(Integer.parseInt(amount3[i]) < 20 || Integer.parseInt(amount3[i])%2!=0)
                                {
                                    Toast.makeText(getActivity(),"please enter valid chips",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else
                                {
//                                    break;
                                }
                            }
                        }
                        int amt=0;
                        for(int j=0;j<10;j++)
                        {
                            if(amount3[j]!=null)
                            {
                                amt+=Integer.parseInt(amount3[j]);
                            }
                        }
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Confirmation");
                        dialog.setMessage("You are about to place bet of: "+amt+ " chips");
                        dialog.setCancelable(true);
                        // ...Irrelevant code for customizing the buttons and title

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!session.equals("00:00 am")) {
                        String tag_json_obj = "json_obj_req";
                        final String TAG = "response";
                        final String url = getString(R.string.PlaceBetJodi);//+ URLEncoder.encode("/"+postString);

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
                                        flag = 3;

                                        int amt=0;
                                        for(int j=0;j<10;j++)
                                        {
                                            if(amount3[j]!=null)
                                            {
                                                amt+=Integer.parseInt(amount3[j]);
                                            }
                                        }

//                                        text_bet_ttl3.setText("Bets Total:"+amt);
                                        int res = (int) (amt * 90);

//                                        int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
//                                        int famt = bal - res;

                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
//                                        editor.putString("present_amount",""+famt);
                                        editor.putString("latest_bet", no3);
                                        editor.putString("bet_amount",String.valueOf(amt));
                                        editor.putString("game_type", "1");
                                        editor.putString("digit", no3);
                                        editor.putString("drawtimeSession",session);
                                        editor.commit();
                                        getPresentAmount();
//                                        balanceStatus.setProgress(famt);

                                        for(int i=0;i<10;i++)
                                        {
                                            number3[i]="";
                                            amount3[i]="";
                                        }

                                        clearFields(3);
                                    } else {
                                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                                pDialog.hide();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                HashMap<String, String> map = new HashMap<>();
                                int cnt = 0;
                                map.put("player_id", getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                                for (int i = 0; i < number3.length; i++) {
                                    if (number3[i] != null) {
                                        no3 = number3[i];
                                        map.put("data[" + cnt + "][digit]", number3[i]);
                                        map.put("data[" + cnt + "][bet_amount]", amount3[i]);
                                        cnt++;
                                    }
                                }
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
                    });

                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    dialog.create();
                    dialog.show();
                    }

                } else {
                    Toast.makeText(getActivity(), "please check internet connection!!!", Toast.LENGTH_SHORT).show();
                }
                break;
                    case R.id.btnautofill:
                        if (TextUtils.isEmpty(jodiNumberEditText0.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText1.getText().toString().trim()) &&
                                TextUtils.isEmpty(jodiNumberEditText2.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText3.getText().toString().trim()) &&
                                TextUtils.isEmpty(jodiNumberEditText4.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText5.getText().toString().trim()) &&
                                TextUtils.isEmpty(jodiNumberEditText6.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText7.getText().toString().trim()) &&
                                TextUtils.isEmpty(jodiNumberEditText8.getText().toString().trim()) && TextUtils.isEmpty(jodiNumberEditText9.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(),"Please enter number!!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    number3=new String[10];
                    amount3=new String[10];
                    if (!TextUtils.isEmpty(jodiNumberEditText0.getText().toString().trim())) {
                        if(TextUtils.isEmpty(jodiAmountEditText0.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[0] = jodiNumberEditText0.getText().toString().trim();
                            amount3[0] = jodiAmountEditText0.getText().toString().trim();
                        }
                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText1.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText1.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[1] = jodiNumberEditText1.getText().toString().trim();
                            amount3[1] = jodiAmountEditText1.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText2.getText().toString().trim())) {
                        if(TextUtils.isEmpty(jodiAmountEditText2.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[2] = jodiNumberEditText2.getText().toString().trim();
                            amount3[2] = jodiAmountEditText2.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText3.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText3.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[3] = jodiNumberEditText3.getText().toString().trim();
                            amount3[3] = jodiAmountEditText3.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText4.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText4.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[4] = jodiNumberEditText4.getText().toString().trim();
                            amount3[4] = jodiAmountEditText4.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText5.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText5.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[5] = jodiNumberEditText5.getText().toString().trim();
                            amount3[5] = jodiAmountEditText5.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText6.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText6.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[6] = jodiNumberEditText6.getText().toString().trim();
                            amount3[6] = jodiAmountEditText6.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText7.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText7.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[7] = jodiNumberEditText7.getText().toString().trim();
                            amount3[7] = jodiAmountEditText7.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText8.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText0.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[8] = jodiNumberEditText8.getText().toString().trim();
                            amount3[8] = jodiAmountEditText8.getText().toString().trim();
                        }

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText9.getText().toString().trim()) ) {
                        if(TextUtils.isEmpty(jodiAmountEditText9.getText().toString().trim())){
                            Toast.makeText(getActivity(),"Please enter chips!!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            number3[9] = jodiNumberEditText9.getText().toString().trim();
                            amount3[9] = jodiAmountEditText9.getText().toString().trim();
                        }

                    }

                    try
                    {
                        DbAdapter adapter = new DbAdapter(getActivity());
                        adapter.open();
                        for(int i=0;i<number3.length;i++)
                        {
                            if(number3[i]!=null && amount3[i]!=null)
                            {
                                Cursor cursor = adapter.GetRowFromFirst(number3[i]);
                                if(cursor.getCount()>0)
                                {
                                    cursor.moveToFirst();
                                    jodiNumberEditText0.setText(cursor.getString(1));
                                    jodiAmountEditText0.setText(amount3[i]);
                                    jodiNumberEditText1.setText(cursor.getString(2));
                                    jodiAmountEditText1.setText(amount3[i]);
                                    jodiNumberEditText2.setText(cursor.getString(3));
                                    jodiAmountEditText2.setText(amount3[i]);
                                    jodiNumberEditText3.setText(cursor.getString(4));
                                    jodiAmountEditText3.setText(amount3[i]);
                                    break;
                                }
                                else
                                {
                                    Cursor cursor_second = adapter.GetRowFromSecond(number3[i]);
                                    if(cursor_second.getCount()>0)
                                    {
                                     cursor_second.moveToFirst();
                                        jodiNumberEditText0.setText(cursor_second.getString(1));
                                        jodiAmountEditText0.setText(amount3[i]);
                                        jodiNumberEditText1.setText(cursor_second.getString(2));
                                        jodiAmountEditText1.setText(amount3[i]);
                                        jodiNumberEditText2.setText(cursor_second.getString(3));
                                        jodiAmountEditText2.setText(amount3[i]);
                                        jodiNumberEditText3.setText(cursor_second.getString(4));
                                        jodiAmountEditText3.setText(amount3[i]);
                                        jodiNumberEditText4.setText(cursor_second.getString(5));
                                        jodiAmountEditText4.setText(amount3[i]);
                                        jodiNumberEditText5.setText(cursor_second.getString(6));
                                        jodiAmountEditText5.setText(amount3[i]);
                                        jodiNumberEditText6.setText(cursor_second.getString(7));
                                        jodiAmountEditText6.setText(amount3[i]);
                                        jodiNumberEditText7.setText(cursor_second.getString(8));
                                        jodiAmountEditText7.setText(amount3[i]);
                                        jodiNumberEditText8.setText(cursor_second.getString(9));
                                        jodiAmountEditText8.setText(amount3[i]);

                                        break;
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),"not found any related to number!!!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        adapter.close();

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
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
//                            JSONObject obj = innerObject.getJSONObject("lucky_number");
                            innerObject.getString("start");
                            session=innerObject.getString("end");
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
                        Log.i("Error","Request Timeout");
                    }
                    else {
                        error.printStackTrace();
                        VolleyLog.d("Current Result", "Error: " + error.getMessage());
                    }
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

    private void countDown(final TextView tv, final int count) {
        if (count == 0) {
//            tv.setText(""); //Note: the TextView will be visible again here.
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

    private void clearFields(int no)
    {
        if(no == 1) {
            FirstEditText0.getText().clear();
            FirstEditText1.getText().clear();
            FirstEditText2.getText().clear();
            FirstEditText3.getText().clear();
            FirstEditText4.getText().clear();
            FirstEditText5.getText().clear();
            FirstEditText6.getText().clear();
            FirstEditText7.getText().clear();
            FirstEditText8.getText().clear();
            FirstEditText9.getText().clear();
        }
        else if(no == 2)
        {
            SecondEditText0.getText().clear();
            SecondEditText1.getText().clear();
            SecondEditText2.getText().clear();
            SecondEditText3.getText().clear();
            SecondEditText4.getText().clear();
            SecondEditText5.getText().clear();
            SecondEditText6.getText().clear();
            SecondEditText7.getText().clear();
            SecondEditText8.getText().clear();
            SecondEditText9.getText().clear();
        }
        else if(no == 3)
        {
            jodiNumberEditText0.getText().clear();
            jodiNumberEditText1.getText().clear();
            jodiNumberEditText2.getText().clear();
            jodiNumberEditText3.getText().clear();
            jodiNumberEditText4.getText().clear();
            jodiNumberEditText5.getText().clear();
            jodiNumberEditText6.getText().clear();
            jodiNumberEditText7.getText().clear();
            jodiNumberEditText8.getText().clear();
            jodiNumberEditText9.getText().clear();

            jodiAmountEditText0.getText().clear();
            jodiAmountEditText1.getText().clear();
            jodiAmountEditText2.getText().clear();
            jodiAmountEditText3.getText().clear();
            jodiAmountEditText4.getText().clear();
            jodiAmountEditText5.getText().clear();
            jodiAmountEditText6.getText().clear();
            jodiAmountEditText7.getText().clear();
            jodiAmountEditText8.getText().clear();
            jodiAmountEditText9.getText().clear();
        }
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
            }
            catch (Exception e)
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
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 50))
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
                            int status = 0;
                            if(object.getString("status").equals("true"))
                            {
                                for(int i=0;i<number.length;i++)
                                {
                                    if(number[i]!=null)
                                    {
                                        if(Integer.parseInt(number[i]) == Integer.parseInt(object.getString("lucky_number")))
                                        {
                                            status = 1;
                                            Toast.makeText(getActivity(),"Congratulation, You Win The Bet.",Toast.LENGTH_SHORT).show();
                                            try {
                                                double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number[i]) * 9.0);
                                                balanceStatus.setProgress((int) result);
                                            }catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                            break;
                                        }

                                    }
                                }
                                if(status == 0)
                                {
                                    Toast.makeText(getActivity(),"Sorry, You lose The Bet",Toast.LENGTH_SHORT).show();
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
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 50))
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
                                int status = 0;
                                if(object.getString("status").equals("true"))
                                {
                                    for(int i=0;i<number2.length;i++)
                                    {
                                        if(number2[i]!=null)
                                        {
                                            if(Integer.parseInt(number2[i]) == Integer.parseInt(object.getString("lucky_number")))
                                            {
                                                status = 1;
                                                Toast.makeText(getActivity(),"Congratulation, You Win The Bet.",Toast.LENGTH_SHORT).show();
                                                try {
                                                    double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number2[i]) * 9.0);
                                                    balanceStatus.setProgress((int) result);
                                                }catch (Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                break;
                                            }

                                        }
                                    }
                                    if(status == 0)
                                    {
                                        Toast.makeText(getActivity(),"Sorry, You lose The Bet",Toast.LENGTH_SHORT).show();
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
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 50))
        {
            flag=0;
//                            Toast.makeText(getActivity(),"Api Call For Third",Toast.LENGTH_SHORT).show();

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
                            int status = 0;
                            if(object.getString("status").equals("true"))
                            {
                                for(int i=0;i<number3.length;i++)
                                {
                                    if(number3[i]!=null)
                                    {
                                        if(Integer.parseInt(number3[i]) == Integer.parseInt(object.getString("lucky_number")))
                                        {
                                            status = 1;
                                            Toast.makeText(getActivity(),"Congratulation, You Win The Bet.",Toast.LENGTH_SHORT).show();
                                            try {
                                                double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number3[i]) * 90);
                                                balanceStatus.setProgress((int) result);
                                            }catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }

                                            break;
                                        }

                                    }
                                }
                                if(status == 0)
                                {
                                    Toast.makeText(getActivity(),"Sorry, You lose The Bet",Toast.LENGTH_SHORT).show();

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


