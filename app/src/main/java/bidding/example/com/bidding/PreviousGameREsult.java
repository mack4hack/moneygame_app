package bidding.example.com.bidding;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PreviousGameREsult extends Fragment {

    private static String[] GAME = {"TOSS \t\t\t\t Mumbai","1ST BALL MUMBAI \t\t 2 runs","1ST BALL PUNE \t\t 4 runs","1ST OVER RUNS MUMBAI  10 runs","1ST OVER RUNS PUNE \t 12 runs","SESSION MUMBAI \t   67 to 70","SESSION PUNE \t\t 71 to 74","1ST WICKET MUMBAI \t\t LBW","1ST WICKET PUNE \t Caught by keeper","HIGHEST OPENING PARTNERSHIP \t\t Mumbai","RACE TO 50 RUNS \t\t Pune","RUNS AT 1ST WICKET MUMBAI \t 31 to 40","RUNS AT 1ST WICKET PUNE \t\t\t 51 to 60","TO MAKE 50 MUMBAI \t\t 1","TO MAKE 50 PUNE \t\t 2","TO MAKE 100 MUMBAI \t\t 1","TO MAKE 100 PUNE \t 1","INNINGS RUN RATE MUMBAI 6.01 to 6.5","INNINGS RUN RATE PUNE  5.51 to 6.00"};
    ListView previouslist;

    public PreviousGameREsult() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_game_result, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        previouslist = (ListView) view.findViewById(R.id.listPrevious);
        previouslist.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, GAME));

    }

}
