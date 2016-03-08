package bidding.example.com.bidding;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

import bidding.example.com.bidding.APICALL.ApiCall;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.Lottery_Game.Multiple_Bet;
import bidding.example.com.bidding.Lottery_Game.Single_Bet;
import bidding.example.com.bidding.ResultChart.result_chart;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Toolbar toolbar;
    ProgressDialog pDialog;
    private static int count = -1;
    private boolean exit=false;
    String presnt_amount, result, dTime="";
    double default_amnt, prsnt_amnt, percentage, prcnt;
    ImageView icon;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        icon = (ImageView) findViewById(R.id.imageView);
        username = (TextView) findViewById(R.id.textuser);
        username.setText(getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("username", ""));

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DashBoard fragment = new DashBoard();
        fragmentTransaction.replace(R.id.containar, fragment);
        fragmentTransaction.commit();

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 01);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        startService(new Intent(getBaseContext(), timeService.class));

//        Intent intent = new Intent(this, UpdateService.class);
//        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
//        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24 * 60 * 60 * 1000, pintent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

           /* if(count == 0)
            {
                count=-1;
                super.onBackPressed();
            }
            else
            {
                count++;
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }*/
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);
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
            SharedPreferences settings = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            stopService();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            return true;
        }
        if (id == R.id.game_2) {
            startActivity(new Intent(getApplicationContext(),MainPage.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), timeService.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.dash_board) {
            // Handle the camera action
            toolbar.setTitle("Lottery");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DashBoard fragment = new DashBoard();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.chart) {
            toolbar.setTitle("Chart");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DashBoard fragment = new DashBoard();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
            startActivity(new Intent(getApplicationContext(),result_chart.class));

        }
        else if(id==R.id.today_summary)
        {
            toolbar.setTitle("Todays Summary");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TodaysSummary fragment = new TodaysSummary();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.today_history)
        {
            Home.toolbar.setTitle("History");
            android.support.v4.app.FragmentManager TodayManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction TodayTransaction = TodayManager.beginTransaction();
            TodaysHistory Today = new TodaysHistory();
            TodayTransaction.replace(R.id.containar, Today);
            TodayTransaction.commit();
        } else if (id == R.id.cancel_bet)
        {
            try
            {
                CurrentResult();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (id == R.id.limit)
        {
           getPresentAmount();

        }
        else if (id == R.id.single_bet)
        {
            toolbar.setTitle("Single Bet");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Single_Bet fragment = new Single_Bet();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.multiple_bet)
        {
            toolbar.setTitle("Multiple Bet");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Multiple_Bet fragment = new Multiple_Bet();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if(id == R.id.profile)
        {
            toolbar.setTitle("Profile");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Profile fragment = new Profile();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if(id == R.id.terms)
        {
            toolbar.setTitle("Terms & Condition");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TermAndCondition fragment = new TermAndCondition();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.logout) {
            SharedPreferences settings = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            stopService();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void CurrentResult()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(this);
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

                            String betStatus = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("latest_bet", "");
                            String drawTime = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("drawtimeSession", "");
                            if(betStatus.equals("not_placed"))
                            {
                                Toast.makeText(getApplicationContext(), "You dont have any bet to cancel!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Log.i("drwtime",""+drawTime+","+dTime);
                                if(dTime.equals(drawTime)) {
                                    final AlertDialog.Builder infoDialog = new AlertDialog.Builder(Home.this);
                                    infoDialog.setTitle("Bet Information");
                                    infoDialog.setMessage("You are about to cancel bet of chips " + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("bet_amount", ""));

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
                                            final AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
                                            dialog.setTitle("Confirmation");
                                            dialog.setCancelable(true);
                                            // ...Irrelevant code for customizing the buttons and title
                                            LayoutInflater inflater = getLayoutInflater();
                                            View dialogView = inflater.inflate(R.layout.custom_layout, null);
                                            dialog.setView(dialogView);

                                            final EditText mPassword = (EditText) dialogView.findViewById(R.id.editConfPassword);
                                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    if (TextUtils.isEmpty(mPassword.getText().toString().trim())) {
                                                        Toast.makeText(getApplicationContext(), "please enter pasword!!!", Toast.LENGTH_SHORT).show();
                                                    } else {

                                                        if (getApplicationContext().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_password", "").equals(mPassword.getText().toString().trim())) {
                                                            String res = "player_id=" + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "");
                                                            dialogInterface.cancel();
                                                            new AsynCancelBet().execute(getString(R.string.cancel_bet), res);

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Invalid password!!!", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getApplicationContext(), "Bet cannot be cancelled now", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(getApplicationContext(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),getString(R.string.internet_err),Toast.LENGTH_SHORT).show();
        }

    }

    private String getPresentAmount()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if(connectionDetector.isConnectingToInternet()) {
            String tag_string_req = "string_req";
            String url = getString(R.string.url_amount) + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "");

            final ProgressDialog pDialog = new ProgressDialog(this);
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
                            default_amnt = Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("default_amt", ""));
                            prsnt_amnt = (Double.parseDouble(result));
                            if(default_amnt>prsnt_amnt){
                                percentage = default_amnt-prsnt_amnt;
                                Log.i("percentag",""+percentage);
                                prcnt = (percentage/default_amnt)*100;
                                Log.i("percentag",""+default_amnt);
                                Log.i("percentag",""+(default_amnt/percentage));
                                presnt_amount = "Loss";
                            }
                            else {
                                percentage = prsnt_amnt-default_amnt;
                                prcnt = (percentage/default_amnt)*100;
                                presnt_amount = "Profit";
                            }
                            int pay=(int) Math.round(Double.parseDouble(result))-(int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("default_amt", "")));
                            final AlertDialog.Builder infoDialog = new AlertDialog.Builder(Home.this);
                            infoDialog.setTitle("Account Information");
                            infoDialog.setMessage("Default Chips : " + (int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("default_amt", ""))) + "\n" +
                                    "Present Chips : " + (int) Math.round(Double.parseDouble(result)) + "\n" +
                                    presnt_amount + " :" + prcnt + "%" + "\n" +
                                    "Net Payable : " +pay);
                            infoDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            infoDialog.create();
                            infoDialog.show();
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
                        Toast.makeText(getApplicationContext(), "Present Chips Not Present!!!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"please check internet connection!!!",Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    class AsynCancelBet extends AsyncTask<String,Void,String>
    {
        String mResponse=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(Home.this);
            pDialog.setMessage("loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... lists) {
            //List<ModelClass> item=lists[0];
            ApiCall call=new ApiCall(getApplicationContext());
            mResponse = call.HttpPost(lists[0], lists[1]);//item.get(0).getApiUrl(),item.get(0).getApiParameter());

            return mResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("cancel bet", "" + s);
            pDialog.dismiss();
            if(mResponse!=null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equals("true")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
                        editor.putString("latest_bet","not_placed");
                        editor.putString("game_type", "-1");
                        editor.commit();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Something went wrong, please try again!!!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
