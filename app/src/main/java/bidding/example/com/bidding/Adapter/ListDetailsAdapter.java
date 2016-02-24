package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bidding.example.com.bidding.AppDB.DbAdapter;
import bidding.example.com.bidding.GetterSetter.MatchDetailsGetSet;
import bidding.example.com.bidding.R;
import bidding.example.com.bidding.ScreenSlide;

/**
 * Created by root on 2/20/16.
 */
public class ListDetailsAdapter extends BaseAdapter
{
    Context context;
    DbAdapter dbAdapter;
    List<MatchDetailsGetSet> matchDetailsGetSetList;

    public ListDetailsAdapter(Context context, List<MatchDetailsGetSet> matchDetailsGetSetList)
    {
        this.context = context;
        this.matchDetailsGetSetList = matchDetailsGetSetList;
//        dbAdapter = new DbAdapter(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return matchDetailsGetSetList.get(i).hashCode();
    }

    class Holder
    {
        TextView mName,mOdds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_match_bet,null);
            viewHolder = new Holder();
            viewHolder.mName = (TextView) convertView.findViewById(R.id.txtteam);
            viewHolder.mOdds = (TextView) convertView.findViewById(R.id.txtodd);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }

        MatchDetailsGetSet item = matchDetailsGetSetList.get(position);
        Log.i("match", "" + ScreenSlide.match_id);
        if(item.getMatchid().equals(ScreenSlide.match_id)) {
            Log.i("match1", "" + item.getMatchid());
            if (item.getName().equals(ScreenSlide.match_nm)) {
                if (item.getGamename().equals("match_win_loss")) {
                    Log.i("match2", "" + item.getName());
                    if (position == 0) {
                        viewHolder.mName.setText(item.getTeama());
                        viewHolder.mOdds.setText(item.getOdd());
                    }
                    if (position == 1) {
                        viewHolder.mName.setText(item.getTeamb());
                        viewHolder.mOdds.setText(item.getOdd());
                    }
                }
            }
        }

//        dbAdapter.open();
//        Cursor cur = dbAdapter.GetMatchOdds(Integer.parseInt(ScreenSlidePageFragment.matchid));
//        if (cur.getCount() > 0) {
//            cur.moveToFirst();
//            do {
//                viewHolder.mName.setText(cur.getString(7));
//                viewHolder.mOdds.setText(cur.getString(5));
//            } while (cur.moveToNext());
//        }
//
//        dbAdapter.close();




        return convertView;
    }
}
