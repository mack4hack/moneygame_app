package bidding.example.com.bidding;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bidding.example.com.bidding.GetterSetter.MatchDetailsGetSet;

/**
 * Created by root on 3/15/16.
 */
public class OddsService extends Service {

        Timer timer;
        Thread thread;
        String id, batteam, ballteam, run;

        @Override
        public void onCreate() {
        super.onCreate();

    }

        @Override
        public int onStartCommand(Intent intent, final int flags, int startId) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        timer= new Timer();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                            connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED)
                    {
                        Log.i("service", "start");
                        getMatchDetailsOdds();

                    }

                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Log.i("Timer Check", "Timer Task Checkk");
                    if (!thread.isAlive()) {

                        Log.i("Thread is sleeped ", "Thread Started");
                        //  thread.interrupt();;
                        thread.run();
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        timer.schedule(timerTask, 1000, 3 * 60000);


        return START_STICKY;
    }


        @Override
        protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }

        @Override
        public void onDestroy() {
        super.onDestroy();
    }


        @Override
        public void onLowMemory() {
        super.onLowMemory();
        Log.i("Low Memory", "Low Memory");
        try {
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        @Override
        public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

        @Override
        public IBinder onBind(Intent intent) {
        return null;
    }

    private void getMatchDetailsOdds(){
        String tag_json_obj = "json_obj_req";
        final String TAG = "response";
        final String url = getString(R.string.get_match_odds);
        Log.i("url", "" + url);

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {

                Log.d(TAG, response.toString());
//                dbAdapter= new DbAdapter(getActivity());
//                dbAdapter.open();

                ScreenSlidePageFragment.matchidrecrd = new ArrayList<>();
                ScreenSlidePageFragment.matchlivercrd = new ArrayList<>();
                ScreenSlidePageFragment.matchtossrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchfrstbalfrstinrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchfrstballscndinrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchfrstoverarcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchfrstoverbrcrd = new ArrayList<>();
                ScreenSlidePageFragment.match10overarcrd = new ArrayList<>();
                ScreenSlidePageFragment.match10overbrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchfrstwcktarcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchfrstwcktbrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchhighopnrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchrace50rcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchrunatwicktarcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchrunatwicktbrcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchmake30arcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchmake30brcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchmake50arcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchmake50brcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchmake100arcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchmake100brcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchinnrunratearcrd = new ArrayList<>();
                ScreenSlidePageFragment.matchinnrunratebrcrd = new ArrayList<>();

                try {
                    JSONObject object = new JSONObject(response);

                    if(object.getString("status").equals("true"))
                    {
                        JSONObject jsonObject = object.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("Match_Odds");
                        for(int i=0; i<jsonArray.length(); i++){
                            MatchDetailsGetSet matchDetailsGetSet = new MatchDetailsGetSet();
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            id = object1.getString("id");
                            ScreenSlidePageFragment.matchid = object1.getString("match_id");
                            ScreenSlidePageFragment.mid = object1.getString("m_id");
                            ScreenSlidePageFragment.oddid = object1.getString("odd_id");
                            ScreenSlidePageFragment.odd = object1.getString("odds");
                            ScreenSlidePageFragment.ttlchip = object1.getString("total_chips");
                            ScreenSlidePageFragment.pyout = object1.getString("payout");
                            ScreenSlidePageFragment.resltbet = object1.getString("result_bet");
                            ScreenSlidePageFragment.gmcls = object1.getString("game_close");
                            ScreenSlidePageFragment.prtclr = object1.getString("perticulars");
                            ScreenSlidePageFragment.nm = object1.getString("name");
                            ScreenSlidePageFragment.unq = object1.getString("unique");
                            ScreenSlidePageFragment.frmt = object1.getString("format");
                            ScreenSlidePageFragment.ven = object1.getString("venue");
                            String time = object1.getString("start_date");
                            time = time.replace("T", " ");
                            time = time.replace("-", "/");
                            String [] split = time.split("\\u002B");
                            ScreenSlidePageFragment.strt =split[0];
                            ScreenSlidePageFragment.teama = object1.getString("team_a");
                            ScreenSlidePageFragment.teamb = object1.getString("team_b");
                            ScreenSlidePageFragment.wnr = object1.getString("winner_team");
                            ScreenSlidePageFragment.sts = object1.getString("status");
                            ScreenSlidePageFragment.gmnm = object1.getString("game_name");
//                            dbAdapter.InsertOdds(id,matchid,mid,oddid,odd,ttlchip,pyout,resltbet,gmcls,prtclr,nm,unq,frmt,ven,strt,teama,teamb,wnr,sts,gmnm);

                            matchDetailsGetSet.setId(id);
                            matchDetailsGetSet.setMatchid(ScreenSlidePageFragment.matchid);
                            matchDetailsGetSet.setMid(ScreenSlidePageFragment.mid);
                            matchDetailsGetSet.setOddid(ScreenSlidePageFragment.oddid);
                            matchDetailsGetSet.setOdd(ScreenSlidePageFragment.odd);
                            matchDetailsGetSet.setTtlchip(ScreenSlidePageFragment.ttlchip);
                            matchDetailsGetSet.setPayout(ScreenSlidePageFragment.pyout);
                            matchDetailsGetSet.setResultbet(ScreenSlidePageFragment.resltbet);
                            matchDetailsGetSet.setGameclose(ScreenSlidePageFragment.gmcls);
                            matchDetailsGetSet.setPrtclr(ScreenSlidePageFragment.prtclr);
                            matchDetailsGetSet.setName(ScreenSlidePageFragment.nm);
                            matchDetailsGetSet.setUnique(ScreenSlidePageFragment.unq);
                            matchDetailsGetSet.setFormat(ScreenSlidePageFragment.frmt);
                            matchDetailsGetSet.setVenue(ScreenSlidePageFragment.ven);
                            matchDetailsGetSet.setStrt(ScreenSlidePageFragment.strt);
                            matchDetailsGetSet.setTeama(ScreenSlidePageFragment.teama);
                            matchDetailsGetSet.setTeamb(ScreenSlidePageFragment.teamb);
                            matchDetailsGetSet.setWinner(ScreenSlidePageFragment.wnr);
                            matchDetailsGetSet.setStatus(ScreenSlidePageFragment.sts);
                            matchDetailsGetSet.setGamename(ScreenSlidePageFragment.gmnm);

                            ScreenSlidePageFragment.matchDetailsGetSetList.add(matchDetailsGetSet);
                            ScreenSlidePageFragment.matchids.add(ScreenSlidePageFragment.matchid);

                            if(ScreenSlidePageFragment.matchid.equals(ScreenSlidePageFragment.matchiid)){

                                ScreenSlidePageFragment.matchidrecrd.add(matchDetailsGetSet);
//                                Log.i("matchrcrd",""+matchidrecrd.size());
                            }
                        }

                        for(int j=0; j < ScreenSlidePageFragment.matchidrecrd.size(); j++){
                            MatchDetailsGetSet mids = ScreenSlidePageFragment.matchidrecrd.get(j);

                            if(j==0) {
                                ScreenSlidePageFragment.mid1 = mids.getMid();
                            }
                            if (ScreenSlidePageFragment.mid1.equals(mids.getMid().toString())) {
//                                        m.setTeamb("team_a");
                                ScreenSlidePageFragment.matchlivercrd.add(mids);
                                ScreenSlidePageFragment.matchstatus = mids.getStatus();
                                ScreenSlidePageFragment.head = mids.getGamename();
                                ScreenSlidePageFragment.head = ScreenSlidePageFragment.head.replace("_", " ");
                                ScreenSlidePageFragment.title=mids.getName();

                            }
                            if(mids.getMid().equals("2")){
                                ScreenSlidePageFragment.matchtossrcrd.add(mids);
                            }
                            if(mids.getMid().equals("3")){
                                ScreenSlidePageFragment.matchfrstbalfrstinrcrd.add(mids);

                            }
                            if(mids.getMid().equals("4")){
                                ScreenSlidePageFragment.matchfrstballscndinrcrd.add(mids);
                            }
                            if(mids.getMid().equals("5")){
                                ScreenSlidePageFragment.matchfrstoverarcrd.add(mids);

                            }
                            if(mids.getMid().equals("6")){
                                ScreenSlidePageFragment.matchfrstoverbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("7")){
                                ScreenSlidePageFragment.match10overarcrd.add(mids);

                            }
                            if(mids.getMid().equals("8")){
                                ScreenSlidePageFragment.match10overbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("9")){
                                ScreenSlidePageFragment.matchfrstwcktarcrd.add(mids);

                            }
                            if(mids.getMid().equals("10")){
                                ScreenSlidePageFragment.matchfrstwcktbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("11")){
                                ScreenSlidePageFragment.matchhighopnrcrd.add(mids);

                            }
                            if(mids.getMid().equals("12")){
                                ScreenSlidePageFragment.matchrace50rcrd.add(mids);

                            }
                            if(mids.getMid().equals("13")){
                                ScreenSlidePageFragment.matchrunatwicktarcrd.add(mids);

                            }
                            if(mids.getMid().equals("14")){
                                ScreenSlidePageFragment.matchrunatwicktbrcrd.add(mids);

                            }
                            if(mids.getMid().equals("15")){
                                ScreenSlidePageFragment.matchmake30arcrd.add(mids);

                            }
                            if(mids.getMid().equals("16")){
                                ScreenSlidePageFragment.matchmake30brcrd.add(mids);

                            }
                            if(mids.getMid().equals("17")){
                                ScreenSlidePageFragment.matchmake50arcrd.add(mids);

                            }
                            if(mids.getMid().equals("18")){
                                ScreenSlidePageFragment.matchmake50brcrd.add(mids);

                            }
                            if(mids.getMid().equals("19")){
                                ScreenSlidePageFragment.matchmake100arcrd.add(mids);

                            }
                            if(mids.getMid().equals("20")){
                                ScreenSlidePageFragment.matchmake100brcrd.add(mids);

                            }
                            if(mids.getMid().equals("21")){
                                ScreenSlidePageFragment.matchinnrunratearcrd.add(mids);

                            }
                            if(mids.getMid().equals("22")){
                                ScreenSlidePageFragment.matchinnrunratebrcrd.add(mids);

                            }

                        }
                        ScreenSlidePageFragment.listDetailsAdapter.notifyDataSetChanged();

                    }
//                    dbAdapter.close();

                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof TimeoutError)
                {

                }
                else {

                }
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}

