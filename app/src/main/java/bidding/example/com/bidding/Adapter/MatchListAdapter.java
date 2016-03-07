package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bidding.example.com.bidding.GetterSetter.MatchListGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by root on 2/17/16.
 */
public class MatchListAdapter extends BaseAdapter
{
    Context context;
    List<MatchListGetSet> matchList;

    public MatchListAdapter(Context context,List<MatchListGetSet> matchList)
    {
        this.context = context;
        this.matchList = matchList;
    }

    @Override
    public int getCount() {
        return matchList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return matchList.get(i).hashCode();
    }

    class Holder
    {
        TextView mName, mDate;
        ImageView enable;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_match_list,null);
            viewHolder = new Holder();
            viewHolder.mName = (TextView) convertView.findViewById(R.id.txtname);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.txttime);
            viewHolder.enable = (ImageView) convertView.findViewById(R.id.green_dot);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }
        MatchListGetSet item = matchList.get(position);

        viewHolder.mName.setText(item.getName().trim());
        try {
            String date = "";
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dte = df.parse(item.getDate());
            date = df1.format(dte);
            viewHolder.mDate.setText(date);
            Calendar cal1 = Calendar.getInstance();
            cal1.add(cal1.HOUR, 48);
            String time2= df.format(new Date(cal1.getTimeInMillis()));
            if (dte.before(df.parse(time2))) {
                viewHolder.enable.setImageResource(R.drawable.enable);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}

