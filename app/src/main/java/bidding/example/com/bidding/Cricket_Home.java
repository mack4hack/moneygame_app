package bidding.example.com.bidding;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bidding.example.com.bidding.APICALL.ApiCall;
import bidding.example.com.bidding.Chart.SampleObject;

/**
 * Created by Gaurav on 18/01/16.
 */
public class Cricket_Home extends Fragment implements View.OnClickListener{



    private ImageView cbet,chistory,caccnt,cprvsgame,cupcmngmtch,ccnclbet,ccnclldbet,cscrcrd;
    ProgressDialog pDialog;

    public static String res;
    public static List<SampleObject> sampleObjects = new ArrayList<SampleObject>();
    public Cricket_Home() {
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
        return inflater.inflate(R.layout.fragment_cricket_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cbet= (ImageView) view.findViewById(R.id.bet);
        chistory = (ImageView) view.findViewById(R.id.history);
        caccnt= (ImageView) view.findViewById(R.id.accounts);
        cprvsgame= (ImageView) view.findViewById(R.id.previous_game_rslt);
        cupcmngmtch= (ImageView) view.findViewById(R.id.upcoming);
        ccnclbet= (ImageView) view.findViewById(R.id.cancel_bet);
        ccnclldbet = (ImageView) view.findViewById(R.id.cnclld_bets);
        cscrcrd = (ImageView) view.findViewById(R.id.score_crd);

        cbet.setOnClickListener(this);
        chistory.setOnClickListener(this);
        caccnt.setOnClickListener(this);
        cprvsgame.setOnClickListener(this);
        cupcmngmtch.setOnClickListener(this);
        ccnclbet.setOnClickListener(this);
        ccnclldbet.setOnClickListener(this);
        cscrcrd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bet:
                MainPage.toolbar.setTitle("Bet");
                android.support.v4.app.FragmentManager betManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction betTransaction = betManager.beginTransaction();
                CriceketBet bet = new CriceketBet();
                betTransaction.replace(R.id.containar1, bet);
                betTransaction.commit();
                break;

            case R.id.history:


                break;
            case R.id.accounts:

                break;
            case R.id.cancel_bet:
                try
                {
                    String betStatus = getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("latest_bet", "");
                    if(betStatus.equals("not_placed"))
                    {
                        Toast.makeText(getActivity(), "You dont have any bet to cancel!!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());
                        infoDialog.setTitle("Bet Information");
                        infoDialog.setMessage("You are about to cancel bet of chips " + getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("betchips", ""));

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
                                                new AsynCancelBet().execute(getString(R.string.cancel_cricket_bet), res);

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
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.previous_game_rslt:
                MainPage.toolbar.setTitle("Results");
                android.support.v4.app.FragmentManager previousManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction previousTransaction = previousManager.beginTransaction();
                PreviousGameREsult previousGameREsult = new PreviousGameREsult();
                previousTransaction.replace(R.id.containar1, previousGameREsult);
                previousTransaction.commit();
                break;
            case R.id.upcoming:
                MainPage.toolbar.setTitle("Matches");
                android.support.v4.app.FragmentManager upcomingManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction upcomingTransaction = upcomingManager.beginTransaction();
                UpcomingMatches upcomingMatches = new UpcomingMatches();
                upcomingTransaction.replace(R.id.containar1, upcomingMatches);
                upcomingTransaction.commit();
                break;
            case R.id.score_crd:
                MainPage.toolbar.setTitle("Score Card");
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScoreCard fragment = new ScoreCard();
                fragmentTransaction.replace(R.id.containar1, fragment);
                fragmentTransaction.commit();
                break;
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
            Log.i("cancel bet", "" + s);
            pDialog.dismiss();
            if(mResponse!=null)
            {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equals("true")) {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor =getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).edit();
                        editor.putString("latest_bet","not_placed");
                        editor.putString("game_type", "-1");
                        editor.commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Something went wrong, please try again!!!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
