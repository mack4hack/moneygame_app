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
    MatchListGetSet item;

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


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View itemView = inflater.inflate(R.layout.child_match_list,viewGroup, false);
            viewHolder = new Holder();
            viewHolder.mName = (TextView) itemView.findViewById(R.id.txtname);
            viewHolder.mDate = (TextView) itemView.findViewById(R.id.txttime);
            viewHolder.enable = (ImageView) itemView.findViewById(R.id.green_dot);


       item = matchList.get(position);

        viewHolder.mName.setText(item.getName().trim());
        try {
            String date = "";
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dte = df.parse(item.getDate());
            date = df1.format(dte);
            viewHolder.mDate.setText(date);
            Calendar cal1 = Calendar.getInstance();
            cal1.add(cal1.HOUR, 48);
            String time2= df.format(new Date(cal1.getTimeInMillis()));
            if (dte.before(df.parse(time2))) {
                viewHolder.enable.setImageResource(R.drawable.green_dot);
            }
            else{

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }


        return itemView;
    }
}

class Holder
{
    TextView mName, mDate;
    ImageView enable;
}

