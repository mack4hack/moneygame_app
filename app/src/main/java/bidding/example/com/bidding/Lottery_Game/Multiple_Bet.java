package bidding.example.com.bidding.Lottery_Game;


import android.app.ProgressDialog;
import android.content.Context;
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

    CounterClass counterClass;

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

        balanceStatus = (ProgressBar) v.findViewById(R.id.ProgressBar02);
        try {
            balanceStatus.setMax((int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("default_amt", ""))));
            if (Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", ""))) < 0) {
                balanceStatus.setProgress(0);
            } else {
                int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
                balanceStatus.setProgress(bal);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        counterClass = new CounterClass(90000,1000);
        counterClass.start();
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
        HorizontalScrollView sv=(HorizontalScrollView)v.findViewById(R.id.scrollView1);
        sv.setAddStatesFromChildren(true);
        CurrentResult();
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
                    Toast.makeText(getActivity(), "please enter amount!!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(FirstEditText0.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText1.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText2.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText3.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText4.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText5.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText6.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText7.getText().toString().trim()) &&
                        TextUtils.isEmpty(FirstEditText8.getText().toString().trim()) && TextUtils.isEmpty(FirstEditText9.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "please enter amount!!!", Toast.LENGTH_SHORT).show();
                } else {
                    number = new String[10];
                    amount = new String[10];
                    String msg = "please enter valid amount at position ";
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

                    //new AsynTaskCall().execute(getString(R.string.PlaceBetFirst),parameter.toString());
                    if (connectionDetector.isConnectingToInternet()) {
                        for (int i = 0; i < 10; i++) {
                            if (amount[i] != null) {
                                if (Integer.parseInt(amount[i]) < 20 || Integer.parseInt(amount[i])%20!=0) {
                                    Toast.makeText(getActivity(), msg + i, Toast.LENGTH_SHORT).show();
                                    return;

                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
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

                                        int amt=0;
                                        for(int j=0;j<10;j++)
                                        {
                                            if(amount[j]!=null)
                                            {
                                                amt+=Integer.parseInt(amount[j]);
                                            }
                                        }
                                        //int result = (int) (Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) - (amt * 8.5));

                                        int res = (int) (amt * 8.5);

                                        int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
                                        int famt = bal - res;

                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();

                                        editor.putString("present_amount",""+famt);
                                        editor.commit();
                                        balanceStatus.setProgress(famt);

                                        for(int i=0;i<10;i++)
                                        {
                                            number[i]="";
                                            amount[i]="";
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
                    Toast.makeText(getActivity(), "please enter amount!!!", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(SecondEditText0.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText1.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText2.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText3.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText4.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText5.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText6.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText7.getText().toString().trim()) &&
                        TextUtils.isEmpty(SecondEditText8.getText().toString().trim()) && TextUtils.isEmpty(SecondEditText9.getText().toString().trim()))
                {

                    Toast.makeText(getActivity(), "please enter amount!!!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    number2 = new String[10];
                    amount2 = new String[10];
                    StringBuilder builder = new StringBuilder();
                    String msg = "please enter amount greater than 20 for digit ";

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
                                if (Integer.parseInt(amount2[i]) < 20 || Integer.parseInt(amount2[i])%20!=0) {
                                    Toast.makeText(getActivity(), msg + i, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }

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
                                    if (object.getString("status").equals("true"))
                                    {
                                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                        flag = 2;

                                        int amt=0;
                                        for(int j=0;j<10;j++)
                                        {
                                            if(amount2[j]!=null)
                                            {
                                                amt+=Integer.parseInt(amount2[j]);
                                            }
                                        }
                                        int res = (int) (amt * 8.5);

                                        int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
                                        int famt = bal - res;

                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                                        editor.putString("present_amount",""+famt);
                                        editor.commit();
                                        balanceStatus.setProgress(famt);

                                        for(int i=0;i<10;i++)
                                        {
                                            number2[i]="";
                                            amount2[i]="";
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
                        Toast.makeText(getActivity(),"please enter number!!!",Toast.LENGTH_SHORT).show();
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

                        for(int i=0;i<amount3.length;i++)
                        {
                            if(amount3[i] != null)
                            {
                                if(Integer.parseInt(amount3[i]) < 20 || Integer.parseInt(amount3[i])%2!=0)
                                {
                                    Toast.makeText(getActivity(),"please enter valid amount",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }



                        String tag_json_obj = "json_obj_req";
                        final String TAG = "response";
                        final String url = getString(R.string.PlaceBetJodi);//+ URLEncoder.encode("/"+postString);

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
                                        flag = 3;

                                        int amt=0;
                                        for(int j=0;j<10;j++)
                                        {
                                            if(amount3[j]!=null)
                                            {
                                                amt+=Integer.parseInt(amount3[j]);
                                            }
                                        }


                                        int res = (int) (amt * 85);

                                        int bal = (int) Math.round(Double.parseDouble(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")));
                                        int famt = bal - res;

                                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).edit();
                                        editor.putString("present_amount",""+famt);
                                        editor.commit();
                                        balanceStatus.setProgress(famt);

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
                                        map.put("data[" + cnt + "][digit]", number3[i]);
                                        map.put("data[" + cnt + "][bet_amount]", amount3[i]);
                                        cnt++;
                                    }
                                }
                                /*for (String key : map.keySet()) {
                                    Log.i("vales", "key = " + key + " val = " + map.get(key));
                                }*/
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
                }
                else
                {
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
                    Toast.makeText(getActivity(),"please enter number!!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    number3=new String[10];
                    if (!TextUtils.isEmpty(jodiNumberEditText0.getText().toString().trim())) {
                        number3[0] = jodiNumberEditText0.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText1.getText().toString().trim()) ) {
                        number3[1] = jodiNumberEditText1.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText2.getText().toString().trim())) {
                        number3[2] = jodiNumberEditText2.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText3.getText().toString().trim()) ) {
                        number3[3] = jodiNumberEditText3.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText4.getText().toString().trim()) ) {
                        number3[4] = jodiNumberEditText4.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText5.getText().toString().trim()) ) {
                        number3[5] = jodiNumberEditText5.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText6.getText().toString().trim()) ) {
                        number3[6] = jodiNumberEditText6.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText7.getText().toString().trim()) ) {
                        number3[7] = jodiNumberEditText7.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText8.getText().toString().trim()) ) {
                        number3[8] = jodiNumberEditText8.getText().toString().trim();

                    }

                    if (!TextUtils.isEmpty(jodiNumberEditText9.getText().toString().trim()) ) {
                        number3[9] = jodiNumberEditText9.getText().toString().trim();

                    }
                    try
                    {
                        DbAdapter adapter = new DbAdapter(getActivity());
                        adapter.open();
                        for(int i=0;i<number3.length;i++)
                        {
                            if(number3[i]!=null)
                            {
                                Cursor cursor = adapter.GetRowFromFirst(number3[i]);
                                if(cursor.getCount()>0)
                                {
                                    cursor.moveToFirst();
                                    jodiNumberEditText0.setText(cursor.getString(1));
                                    jodiNumberEditText1.setText(cursor.getString(2));
                                    jodiNumberEditText2.setText(cursor.getString(3));
                                    jodiNumberEditText3.setText(cursor.getString(4));
                                    break;
                                }
                                else
                                {
                                    Cursor cursor_second = adapter.GetRowFromSecond(number3[i]);
                                    if(cursor_second.getCount()>0)
                                    {
                                     cursor_second.moveToFirst();
                                        jodiNumberEditText0.setText(cursor_second.getString(1));
                                        jodiNumberEditText1.setText(cursor_second.getString(2));
                                        jodiNumberEditText2.setText(cursor_second.getString(3));
                                        jodiNumberEditText3.setText(cursor_second.getString(4));
                                        jodiNumberEditText4.setText(cursor_second.getString(5));
                                        jodiNumberEditText5.setText(cursor_second.getString(6));
                                        jodiNumberEditText6.setText(cursor_second.getString(7));
                                        jodiNumberEditText7.setText(cursor_second.getString(8));
                                        jodiNumberEditText8.setText(cursor_second.getString(9));
                                        //jodiNumberEditText9.setText(cursor.getString(1));
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
                            innerObject.getString("lucky_number");
                            innerObject.getString("start");
                            innerObject.getString("end");

                            mCurrentSession.setText("Current Session: "+innerObject.getString("end"));
                            mCurrentResult.setText(innerObject.getString("lucky_number"));
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
                        VolleyLog.d("CUrrent Result", "Error: " + error.getMessage());
                    }
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
            tv.setText(""); //Note: the TextView will be visible again here.
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
                mTimer.setText("" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) + ":" + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0));

                if (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) == 14 &&
                        (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) >= 51 &&
                                getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentSecond", 0) <= 60)) {
                    countDown(mCurrentResult, 14);
                }
                if (getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) == 14 &&
                        getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getInt("currentMinute", 0) == 40) {
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
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 40))
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
                                                double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number[i]) * 8.5);
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
                                                    double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number2[i]) * 8.5);
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
                getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getInt("currentMinute",0) == 40))
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
                                                double result = Integer.parseInt(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("present_amount", "")) + (Integer.parseInt(number3[i]) * 85);
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
}


