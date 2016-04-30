package bidding.example.com.bidding;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import org.json.JSONArray;


public class Container_lottery extends Fragment {
    private TabHost tabHost;
    ConnectivityManager connec;
    private static final String ARG_SECTION_NUMBER = "section_number";
    JSONArray jsonarray;
    ProgressDialog mProgressDialog;
    String preferences, usr, user, des;

    public static Container_lottery newInstance(int sectionNumber) {
        Container_lottery fragment = new Container_lottery();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_container_lottery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            connec = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            tabHost = (TabHost) view.findViewById(R.id.tabHost);
            LocalActivityManager mLocalActivityManager = new LocalActivityManager(getActivity(), false);
            mLocalActivityManager.dispatchCreate(savedInstanceState);
            tabHost.setup(mLocalActivityManager);

            TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
            TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
            TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");
            Resources res = getResources();

            tab1.setIndicator("Current Week");
            tab1.setContent(new Intent(getActivity(), TodaysHistory.class));

            tab2.setIndicator("Last Week");
            ;
            tab2.setContent(new Intent(getActivity(), TodaysHistoryLastWeek.class));

            tabHost.addTab(tab1);
            tabHost.addTab(tab2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}