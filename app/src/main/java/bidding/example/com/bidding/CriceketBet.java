package bidding.example.com.bidding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CriceketBet extends Fragment {

    ListView listMatches;
    private static String[] Matches = {"Mumbai Vs Pune","Chennai Vs Kolkatta","Punjab Vs Rajasthan","Bengaluru Vs Mumbai","Punjab Vs Kochi","Pune Vs Rajasthan","Kolkatta Vs Bengaluru"};

    public CriceketBet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_criceket_bet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listMatches = (ListView) view.findViewById(R.id.listMatch);
        listMatches.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Matches));

        listMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                   startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
                else if (position == 1) {
                    startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
                else if (position == 2) {
                    startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
                else if (position == 3) {
                    startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
                else if (position == 4) {
                    startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
                else if (position == 5) {
                    startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
                else if (position == 6) {
                    startActivity(new Intent(getActivity(), ScreenSlide.class).putExtra("position",position));
                }
            }
        });
    }
}
