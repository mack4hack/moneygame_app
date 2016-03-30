package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bidding.example.com.bidding.GetterSetter.CancelledGetSet;
import bidding.example.com.bidding.GetterSetter.TransactionDetailsGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by Gaurav on 29/03/16.
 */
public class CancelledAdapter extends BaseAdapter {
    Context context;
    List<CancelledGetSet> canclldList;

    public CancelledAdapter(Context context,List<CancelledGetSet> canclldList)
    {
        this.context = context;
        this.canclldList = canclldList;
    }

    @Override
    public int getCount() {
        return canclldList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return canclldList.get(i).hashCode();
    }

    class Holder
    {
        TextView mtransid,mChip, mMatch, mGame;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_cancelled_list,null);
            viewHolder = new Holder();
            viewHolder.mtransid = (TextView) convertView.findViewById(R.id.tr_id);
            viewHolder.mChip = (TextView) convertView.findViewById(R.id.chip);
            viewHolder.mMatch = (TextView) convertView.findViewById(R.id.mtch);
            viewHolder.mGame = (TextView) convertView.findViewById(R.id.game);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }
        CancelledGetSet item = canclldList.get(position);

        viewHolder.mGame.setText(item.getGame());
        viewHolder.mtransid.setText(item.getTrans_id());
        viewHolder.mChip.setText(item.getChips());
        viewHolder.mMatch.setText(item.getMatch_name());

        return convertView;
    }
}
