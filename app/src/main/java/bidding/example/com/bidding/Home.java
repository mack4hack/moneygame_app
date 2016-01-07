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
import android.widget.Toast;

import org.json.JSONObject;

import bidding.example.com.bidding.APICALL.ApiCall;
import bidding.example.com.bidding.Lottery_Game.Multiple_Bet;
import bidding.example.com.bidding.ResultChart.result_chart;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Toolbar toolbar;
    ProgressDialog pDialog;
    private static int count = -1;
    private boolean exit=false;

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

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DashBoard fragment = new DashBoard();
        fragmentTransaction.replace(R.id.containar, fragment);
        fragmentTransaction.commit();
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
            stopService();
                startActivity(new Intent(getApplicationContext(),Login.class));
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
            toolbar.setTitle("Single Bet");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DashBoard fragment = new DashBoard();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.chart) {
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
            Home.toolbar.setTitle("Today History");
            android.support.v4.app.FragmentManager TodayManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction TodayTransaction = TodayManager.beginTransaction();
            TodaysHistory Today = new TodaysHistory();
            TodayTransaction.replace(R.id.containar, Today);
            TodayTransaction.commit();
        } else if (id == R.id.cancel_bet)
        {
            try
            {
                String betStatus = getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("latest_bet", "");
                if(betStatus.equals("not_placed"))
                {
                    Toast.makeText(getApplicationContext(), "You dont have any bet to cancel!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final AlertDialog.Builder infoDialog = new AlertDialog.Builder(this);
                    infoDialog.setTitle("Bet Information");
                    infoDialog.setMessage("You are about to cancel bet of amount RS." + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("bet_amount", ""));

                    infoDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    infoDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
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
                                            String res = "player_id=" + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", "") + "&game_type=" + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("game_type", "") + "&digit=" + getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("digit", "");
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
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (id == R.id.limit)
        {
            try {
                final AlertDialog.Builder infoDialog = new AlertDialog.Builder(this);
                infoDialog.setTitle("Account Information");
                infoDialog.setMessage("Default Amount : Rs." + (int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("default_amt", ""))) + "\n" +
                        "Present Amount : Rs." + (int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("present_amount", ""))) + "\n" +
                        "Profit/Loss(%) : " + (int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("present_amount", ""))) / (int) Math.round(Double.parseDouble(getSharedPreferences(getString(R.string.prefrence), MODE_PRIVATE).getString("default_amt", ""))) + "%");
                infoDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.dismiss();
                    }
                });
                infoDialog.create();
                infoDialog.show();

            }catch (Exception e)
            {
                e.printStackTrace();
            }
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
            toolbar.setTitle("Multiple Bet");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Profile fragment = new Profile();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if(id == R.id.terms)
        {
            toolbar.setTitle("Term & Condition");
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TermAndCondition fragment = new TermAndCondition();
            fragmentTransaction.replace(R.id.containar, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.logout) {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
