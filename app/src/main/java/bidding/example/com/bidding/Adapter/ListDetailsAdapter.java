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

/**
 * Created by root on 2/20/16.
 */
public class ListDetailsAdapter extends BaseAdapter
{
    Context context;
    DbAdapter dbAdapter;
    List<MatchDetailsGetSet> matchLive;

    public ListDetailsAdapter(Context context, List<MatchDetailsGetSet> matchLive)
    {
        this.context = context;
        this.matchLive = matchLive;
//        dbAdapter = new DbAdapter(context);
    }

    @Override
    public int getCount() {
        return matchLive.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return matchLive.get(i).hashCode();
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

        MatchDetailsGetSet item = matchLive.get(position);

        Log.i("list",""+item.getId());
            viewHolder.mName.setText(item.getPrtclr());
            viewHolder.mOdds.setText(item.getOdd());



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
