package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bidding.example.com.bidding.GetterSetter.HistoryGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by Sandesh on 02-Dec-15.
 */
public class HistoryAdapter extends BaseAdapter
{
    Context context;
    List<HistoryGetSet> historyList;
    public HistoryAdapter(Context context,List<HistoryGetSet> historyList)
    {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return historyList.get(i).hashCode();
    }

    class Holder
    {
        TextView mNumber,mAmt,mBetTime,mMargin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_history,null);
            viewHolder = new Holder();
            viewHolder.mNumber = (TextView) convertView.findViewById(R.id.number);
            viewHolder.mAmt = (TextView) convertView.findViewById(R.id.amount);
            viewHolder.mBetTime = (TextView) convertView.findViewById(R.id.time);
            viewHolder.mMargin = (TextView) convertView.findViewById(R.id.charge);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }
        HistoryGetSet item = historyList.get(position);

        viewHolder.mNumber.setText(item.getNumber());
        viewHolder.mBetTime.setText(item.getTime());
        viewHolder.mAmt.setText(item.getAmount());
        viewHolder.mMargin.setText(item.getCharge());

        if(item.getResult().equals("0"))
        {
            convertView.setBackgroundColor(Color.GRAY);

        }

        return convertView;
    }
}
