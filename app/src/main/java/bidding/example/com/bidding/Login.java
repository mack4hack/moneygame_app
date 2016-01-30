package bidding.example.com.bidding;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bidding.example.com.bidding.AppDB.DbAdapter;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout mInputUsername,mInputPassword;
    private EditText mUserName,mPassword;
    private Button mLogin;

    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try
        {
            DbAdapter dbAdapter = new DbAdapter(getApplicationContext());
            dbAdapter.open();
            dbAdapter.InsertFirst("11", "16", "61", "66");
            dbAdapter.InsertFirst("22", "27", "72", "77");
            dbAdapter.InsertFirst("33", "38", "83", "88");
            dbAdapter.InsertFirst("44", "49", "94", "99");
            dbAdapter.InsertFirst("55", "50", "05", "00");

            dbAdapter.InsertSecond("01", "06", "51", "56", "10", "15", "60", "65");
            dbAdapter.InsertSecond("02", "07", "52", "57", "20", "25", "70", "75");
            dbAdapter.InsertSecond("03", "08", "53", "58", "30", "35", "80", "85");
            dbAdapter.InsertSecond("04", "09", "54", "59", "40", "45", "90", "95");

            dbAdapter.InsertSecond("12", "17", "62", "67", "21", "26", "71", "76");
            dbAdapter.InsertSecond("13", "18", "63", "68", "31", "36", "81", "86");
            dbAdapter.InsertSecond("14", "19", "64", "69", "41", "46", "91", "96");

            dbAdapter.InsertSecond("23","28","73","78","32","37","82","87");
            dbAdapter.InsertSecond("24","29","74","79","42","47","92","97");
            long re=dbAdapter.InsertSecond("34","39","84","89","43","48","93","98");
            Log.i("inserted", "" + re);

            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dbAdapter.deleteOldRecord(sdf.format(dt));

            dbAdapter.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        preferences=getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE);
        if(preferences.getString("logged","").equals("logged")){
            finish();
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
        }

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("latest_bet","not_placed");
        editor.putString("geme_type", "-1");
        editor.commit();
        mInputUsername= (TextInputLayout) findViewById(R.id.input_user_name);
        mInputPassword= (TextInputLayout) findViewById(R.id.input_password);

        mUserName= (EditText) findViewById(R.id.username);
        mPassword= (EditText) findViewById(R.id.password);

        mLogin= (Button) findViewById(R.id.btnLogin);

        mInputUsername.setHint("Enter UserName");
        mInputPassword.setHint("Enter Password");

        mLogin.setOnClickListener(this);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onClick(View view)
    {
        String UserName,Password;
        UserName=mUserName.getText().toString().trim();
        Password=mPassword.getText().toString().trim();

        if(TextUtils.isEmpty(UserName))
        {
            mInputUsername.setError("Please Enter UserName");
            mInputPassword.setError("");
            mUserName.requestFocus();
            mUserName.setFocusable(true);
        }
        else if(TextUtils.isEmpty(Password))
        {
            mInputUsername.setError("");
            mInputPassword.setError("Please Enter Password");
            mPassword.requestFocus();
            mPassword.setFocusable(true);
        }
       /* else if(mUserName.getText().toString().trim().equals("admin"))
        {
            mInputUsername.setError("Invalid UserName!!!");
            mInputPassword.setError("");
            mUserName.requestFocus();
            mUserName.setFocusable(true);
        }*/
        else
        {
            String  tag_string_req = "string_req";
            String url = getString(R.string.login);

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();
            final String TAG="login";
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    pDialog.hide();
                    try {

                        Log.i("response",""+response);
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("true")) {




                            JSONObject innerObject = new JSONObject(object.getString("data"));
                            //System.out.println(" 2. " + sdf.format(dt));
                            String[] serverTime = innerObject.getString("date").split(" ");
                            try
                            {
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                Date systemDate = Calendar.getInstance().getTime();
                                String myDate = sdf.format(systemDate);
//                  txtCurrentTime.setText(myDate);
                                Log.i("ServerTime",""+serverTime[1]);
                                Date Date1 = sdf.parse(myDate);
                                Date Date2 = sdf.parse(serverTime[1]);

                                long millse = Date1.getTime() - Date2.getTime();
                                long mills = Math.abs(millse);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("time",serverTime[1]);
                                int Hours = (int) (mills/(1000 * 60 * 60));
                                int Mins = (int) (mills/(1000*60)) % 60;
                                long Secs = (int) (mills / 1000) % 60;

                                String diff = Hours + ":" + Mins + ":" + Secs; // updated value every1 second
                                Log.i("Time", "" + diff);

                                editor.putInt("houre", Hours);
                                editor.putInt("min", Mins);
                                editor.putLong("sec", Secs);

                                String default_amnt= innerObject.getString("default_amount");

                                editor.putString("default_amt",innerObject.getString("default_amount"));
                                editor.putString("present_amount",innerObject.getString("present_amount"));
                                editor.putString("total_game", innerObject.getString("lottery_game_played"));
                           //store UserId and Password
                                editor.putString("player_id", innerObject.getString("player_id"));
                                editor.putString("player_password", mPassword.getText().toString().trim());

                                editor.putString("logged","logged");
                                editor.putString("username", mUserName.getText().toString().trim());
                                editor.commit();

                                /*Thread myThread = null;
                                Runnable myRunnableThread = new CountDownRunner(serverTime[1]);
                                myThread= new Thread(myRunnableThread);
                                myThread.start();*/
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                            startService();

                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (object.getString("status").equals("false")) {
                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(),"something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error)
                {

                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(getApplicationContext(),"Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.hide();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> map=new HashMap<>();
                    map.put("identity",mUserName.getText().toString().trim());
                    map.put("password",mPassword.getText().toString().trim());

                    return map;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;

                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

// Adding request to request queue
            AppControler.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

    }


    public void doWork(final String time)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                    Date systemDate = Calendar.getInstance().getTime();
                    String myDate = sdf.format(systemDate);
//                  txtCurrentTime.setText(myDate);

                    Date Date1 = sdf.parse(myDate);
                    Date Date2 = sdf.parse(time);

                    long millse = Date1.getTime() - Date2.getTime();
                    long mills = Math.abs(millse);

                    int Hours = (int) (mills/(1000 * 60 * 60));
                    int Mins = (int) (mills/(1000*60)) % 60;
                    long Secs = (int) (mills / 1000) % 60;

                    String diff = Hours + ":" + Mins + ":" + Secs; // updated value every1 second
                    Log.i("Time", "" + diff);
                }
                catch (Exception e)
                {

                }
            }
        });
    }

    public void MyTask()
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");



                    Date systemDate = Calendar.getInstance().getTime();
                    String myDate = sdf.format(systemDate);
//                  txtCurrentTime.setText(myDate);

                    Date Date1 = sdf.parse(myDate);
                    int h1 = Date1.getHours();
                    int m1 = Date1.getMinutes();
                    int s1 = Date1.getSeconds();

                    if (h1 == 0) {
                        h1 = 12;
                    } else if (h1 > 12) {
                        h1 = h1 - 12;

                    }
                    if (h1 < 10) {
                        h1 = Integer.parseInt("0" + h1);
                    }
		/*if (m < 10) {
			m = "0" + m;
		}*/
                    if (m1 >= 45) {
                        m1 = 59-m1;
                    }
                    else if (m1 >= 30) {
                        m1 = 44-m1;
                    }
                    else if (m1 >= 15) {
                        m1 = 29-m1;
                    }
                    else if (m1 < 15) {
                        m1 = 14-m1;
                    }if(m1 < 10){
                    m1 = Integer.parseInt("0" + m1);
                }

                    if(s1 == 60)
                    {
                        s1 = 00;
                    }
                    else if(s1 == 0)
                    {
                        s1 = 0;
                    }
                    else if(s1 > 0 && s1 < 60)
                    {
                        s1 = 60 - s1;
                    }
                    if(s1 < 10)
                    {
                        s1 = Integer.parseInt("0" + s1);
                    }

                    Log.i("min",""+getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getInt("min",0));
                    if(m1 > getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getInt("min",0))
                    {
                        m1 = m1 - getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getInt("min",0);
                    }

                    if(s1 > getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getLong("sec",0))
                    {
                        s1 = (int) (s1 - getSharedPreferences(getString(R.string.prefrence),MODE_PRIVATE).getLong("sec", 0));
                    }
                    Log.i("CounDown",""+m1+":"+s1);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    class CountDownRunner implements Runnable
    {
        String time;
        public CountDownRunner(String time)
        {
            this.time = time;
        }
        // @Override
        public void run()
        {
            while(!Thread.currentThread().isInterrupted())
            {
                try
                {
                    //doWork(time);
                    MyTask();
                    Thread.sleep(1000); // Pause of 1 Second
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                catch(Exception e)
                {
                }
            }
        }
    }

        // Method to start the service
    public void startService() {
        //startService(new Intent(getBaseContext(), tim.class));


    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), timeService.class));
        stopService(new Intent(getBaseContext(), UpdateService.class));
    }
}
