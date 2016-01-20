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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import bidding.example.com.bidding.APICALL.ApiCall;
import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.Lottery_Game.Multiple_Bet;
import bidding.example.com.bidding.ResultChart.result_chart;

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Toolbar toolbar;
    ProgressDialog pDialog;
    private static int count = -1;
    private boolean exit=false;
    String presnt_amount, result;
    double default_amnt, prsnt_amnt, percentage, prcnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Cricket_Home fragment = new Cricket_Home();
        fragmentTransaction.replace(R.id.containar1, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

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
        getMenuInflater().inflate(R.menu.home, menu);
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
            startActivity(new Intent(getApplicationContext(),Login.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            finish();
            return true;
        }
        if (id == R.id.game_1) {
            startActivity(new Intent(getApplicationContext(),Home.class));
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
//            toolbar.setTitle("Home");
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            DashBoard fragment = new DashBoard();
//            fragmentTransaction.replace(R.id.containar1, fragment);
//            fragmentTransaction.commit();
        }
        else if (id == R.id.bet) {
//            startActivity(new Intent(getApplicationContext(),result_chart.class));

        }
        else if(id==R.id.today_history)
        {
//            toolbar.setTitle("Todays Summary");
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            TodaysSummary fragment = new TodaysSummary();
//            fragmentTransaction.replace(R.id.containar1, fragment);
//            fragmentTransaction.commit();
        }
        else if(id==R.id.my_accounts)
        {
//            toolbar.setTitle("Todays Summary");
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            TodaysSummary fragment = new TodaysSummary();
//            fragmentTransaction.replace(R.id.containar1, fragment);
//            fragmentTransaction.commit();
        }
        else if (id == R.id.previous_result)
        {
            MainPage.toolbar.setTitle("Previous Game Result");
            android.support.v4.app.FragmentManager previousManager = this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction previousTransaction = previousManager.beginTransaction();
            PreviousGameREsult previousGameREsult = new PreviousGameREsult();
            previousTransaction.replace(R.id.containar1, previousGameREsult);
            previousTransaction.commit();
        }
        else if (id == R.id.upcoming_matches)
        {
            MainPage.toolbar.setTitle("Upcoming Matches");
            android.support.v4.app.FragmentManager upcomingManager = this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction upcomingTransaction = upcomingManager.beginTransaction();
            UpcomingMatches upcomingMatches = new UpcomingMatches();
            upcomingTransaction.replace(R.id.containar1, upcomingMatches);
            upcomingTransaction.commit();
        }
        else if (id == R.id.cancel_bet)
        {
//            getPresentAmount();

        }
        else if (id == R.id.cancelled_bets)
        {
//            toolbar.setTitle("Multiple Bet");
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            Multiple_Bet fragment = new Multiple_Bet();
//            fragmentTransaction.replace(R.id.containar1, fragment);
//            fragmentTransaction.commit();
        }
        else if(id == R.id.score_card)
        {
//            toolbar.setTitle("Multiple Bet");
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            Profile fragment = new Profile();
//            fragmentTransaction.replace(R.id.containar1, fragment);
//            fragmentTransaction.commit();
        }
        else if (id == R.id.logout) {
            SharedPreferences settings = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            stopService();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                            final AlertDialog.Builder infoDialog = new AlertDialog.Builder(MainPage.this);
                            infoDialog.setTitle("Account Information");
                            infoDialog.setMessage("Default Amount : Rs." + (int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("default_amt", ""))) + "\n" +
                                    "Present Amount : Rs." + (int) Math.round(Double.parseDouble(result)) + "\n" +
                                    presnt_amount +" :" + prcnt+"%");
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
                        Toast.makeText(getApplicationContext(), "Present Amount Not Present!!!", Toast.LENGTH_SHORT).show();
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
            pDialog=new ProgressDialog(MainPage.this);
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
                        editor.putString("geme_type", "-1");
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
