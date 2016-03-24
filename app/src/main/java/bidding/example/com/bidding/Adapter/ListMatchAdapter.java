package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bidding.example.com.bidding.GetterSetter.MatchListGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by root on 3/21/16.
 */
public class ListMatchAdapter extends BaseAdapter
{
    Context context;
    List<MatchListGetSet> matchList;
    MatchListGetSet item;

    public ListMatchAdapter(Context context,List<MatchListGetSet> matchList)
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


    class ViewHolder
    {
        TextView mName, mDate;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        ViewHolder viewHolder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.child_matchlist,viewGroup, false);
        viewHolder = new ViewHolder();
        viewHolder.mName = (TextView) itemView.findViewById(R.id.txtname);
        viewHolder.mDate = (TextView) itemView.findViewById(R.id.txttime);

        item = matchList.get(position);

        viewHolder.mName.setText(item.getName());
        try {
            String date = "";
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dte = df.parse(item.getDate());
            date = df1.format(dte);
            viewHolder.mDate.setText(date);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return itemView;
    }
}


