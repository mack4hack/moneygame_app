package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bidding.example.com.bidding.GetterSetter.TransactionDetailsGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by root on 3/21/16.
 */
public class CricketTransactionAdapter extends BaseAdapter {
    Context context;
    List<TransactionDetailsGetSet> historyList;

    public CricketTransactionAdapter(Context context,List<TransactionDetailsGetSet> historyList)
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
        TextView mDigit,mChip, mPayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.transaction_child,null);
            viewHolder = new Holder();
            viewHolder.mDigit = (TextView) convertView.findViewById(R.id.transactionNo);
            viewHolder.mChip = (TextView) convertView.findViewById(R.id.chip);
            viewHolder.mChip = (TextView) convertView.findViewById(R.id.payout);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }
        TransactionDetailsGetSet item = historyList.get(position);

        //viewHolder.mTransactionNo.setText(item.getNumber());
        viewHolder.mDigit.setText(item.getDigit());
        viewHolder.mChip.setText(item.getChip());
        viewHolder.mPayout.setText(item.getChip());

        return convertView;
    }
}


