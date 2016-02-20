package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bidding.example.com.bidding.AppDB.DbAdapter;
import bidding.example.com.bidding.R;
import bidding.example.com.bidding.ScreenSlidePageFragment;

/**
 * Created by root on 2/20/16.
 */
public class ListDetailsAdapter extends BaseAdapter
{
    Context context;
    DbAdapter dbAdapter;

    public ListDetailsAdapter(Context context)
    {
        this.context = context;
        dbAdapter = new DbAdapter(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
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

        dbAdapter.open();
        Cursor cur = dbAdapter.GetMatchOdds(Integer.parseInt(ScreenSlidePageFragment.matchid));
        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                viewHolder.mName.setText(cur.getString(7));
                viewHolder.mOdds.setText(cur.getString(5));
            } while (cur.moveToNext());
        }

        dbAdapter.close();


        return convertView;
    }
}
