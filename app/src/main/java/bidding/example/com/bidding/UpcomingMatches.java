package bidding.example.com.bidding;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class UpcomingMatches extends Fragment {

    Button play, later_today, tomorrow, next;

    public UpcomingMatches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        play = (Button) view.findViewById(R.id.btn_play);
        later_today = (Button) view.findViewById(R.id.btn_today);
        tomorrow = (Button) view.findViewById(R.id.btn_tmrw);
        next = (Button) view.findViewById(R.id.btn_next);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlayDetails.class));
            }
        });

    }
}
