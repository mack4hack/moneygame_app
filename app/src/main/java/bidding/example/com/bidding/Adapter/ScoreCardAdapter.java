package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bidding.example.com.bidding.GetterSetter.ScoreCardGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by root on 3/11/16.
 */
public class ScoreCardAdapter extends BaseAdapter
{
    Context context;
    List<ScoreCardGetSet> matchList;

    public ScoreCardAdapter(Context context,List<ScoreCardGetSet> matchList)
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
        TextView mName, mRuns, mRate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_batsman_list,null);
            viewHolder = new Holder();
            viewHolder.mName = (TextView) convertView.findViewById(R.id.txtbatsman);
            viewHolder.mRuns = (TextView) convertView.findViewById(R.id.txtruns);
            viewHolder.mRate = (TextView) convertView.findViewById(R.id.txtrate);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }
        ScoreCardGetSet item = matchList.get(position);

        viewHolder.mName.setText(item.getPlayer_name().trim());
        viewHolder.mRuns.setText(item.getRuns()+"("+item.getBalls()+")");
        viewHolder.mRate.setText(item.getStrikerate());

        return convertView;
    }
}


