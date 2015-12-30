package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bidding.example.com.bidding.GetterSetter.chartGetSet;
import bidding.example.com.bidding.R;

/**
 * Created by Sandesh on 04-Dec-15.
 */
public class chartAdapter extends BaseAdapter
{
    Context context;
    List<chartGetSet> chartList;
    public chartAdapter(Context context,List<chartGetSet> chartList)
    {
        this.context = context;
        this.chartList = chartList;
    }

    @Override
    public int getCount() {
        return chartList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return chartList.get(i).hashCode();
    }

    class Holder
    {
        TextView mTime,mNumber1,mNumber2,mNumber3,mNumber4,mNumber5,mNumber6,mNumber7,mNumber8,mNumber9,mNumber10,mNumber11,mNumber12,mNumber13,mNumber14,mNumber15,mNumber16,mNumber17,mNumber18,mNumber19,mNumber20,mNumber21,mNumber22,mNumber23,mNumber24,mNumber25,mNumber26,mNumber27,mNumber28,mNumber29,mNumber30;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        Holder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_chart_result,null);
            viewHolder = new Holder();

            viewHolder.mTime = (TextView) convertView.findViewById(R.id.text_view1);
            viewHolder.mNumber1 = (TextView) convertView.findViewById(R.id.text_view2);
            viewHolder.mNumber2 = (TextView) convertView.findViewById(R.id.text_view3);
            viewHolder.mNumber3 = (TextView) convertView.findViewById(R.id.text_view4);
            viewHolder.mNumber4 = (TextView) convertView.findViewById(R.id.text_view5);
            viewHolder.mNumber5 = (TextView) convertView.findViewById(R.id.text_view6);
            viewHolder.mNumber6 = (TextView) convertView.findViewById(R.id.text_view7);
            viewHolder.mNumber7 = (TextView) convertView.findViewById(R.id.text_view8);
            viewHolder.mNumber8 = (TextView) convertView.findViewById(R.id.text_view9);
            viewHolder.mNumber9 = (TextView) convertView.findViewById(R.id.text_view10);
            viewHolder.mNumber10 = (TextView) convertView.findViewById(R.id.text_view11);
            viewHolder.mNumber11 = (TextView) convertView.findViewById(R.id.text_view12);
            viewHolder.mNumber12 = (TextView) convertView.findViewById(R.id.text_view13);
            viewHolder.mNumber13 = (TextView) convertView.findViewById(R.id.text_view14);
            viewHolder.mNumber14 = (TextView) convertView.findViewById(R.id.text_view15);
            viewHolder.mNumber15 = (TextView) convertView.findViewById(R.id.text_view16);
            viewHolder.mNumber16 = (TextView) convertView.findViewById(R.id.text_view17);
            viewHolder.mNumber17 = (TextView) convertView.findViewById(R.id.text_view18);
            viewHolder.mNumber18 = (TextView) convertView.findViewById(R.id.text_view19);
            viewHolder.mNumber19 = (TextView) convertView.findViewById(R.id.text_view20);
            viewHolder.mNumber20 = (TextView) convertView.findViewById(R.id.text_view21);
            viewHolder.mNumber21 = (TextView) convertView.findViewById(R.id.text_view22);
            viewHolder.mNumber22 = (TextView) convertView.findViewById(R.id.text_view23);
            viewHolder.mNumber23 = (TextView) convertView.findViewById(R.id.text_view24);
            viewHolder.mNumber24 = (TextView) convertView.findViewById(R.id.text_view25);
            viewHolder.mNumber25 = (TextView) convertView.findViewById(R.id.text_view26);
            viewHolder.mNumber26 = (TextView) convertView.findViewById(R.id.text_view27);
            viewHolder.mNumber27 = (TextView) convertView.findViewById(R.id.text_view28);
            viewHolder.mNumber28 = (TextView) convertView.findViewById(R.id.text_view29);
            viewHolder.mNumber29 = (TextView) convertView.findViewById(R.id.text_view30);
            viewHolder.mNumber30 = (TextView) convertView.findViewById(R.id.text_view31);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }

        chartGetSet rowItem = chartList.get(position);

        viewHolder.mTime.setText(rowItem.getTime());

        viewHolder.mNumber1.setText(rowItem.getDate1());
        viewHolder.mNumber2.setText(rowItem.getDate2());
        viewHolder.mNumber3.setText(rowItem.getDate3());
        viewHolder.mNumber4.setText(rowItem.getDate4());
        viewHolder.mNumber5.setText(rowItem.getDate5());
        viewHolder.mNumber6.setText(rowItem.getDate6());
        viewHolder.mNumber7.setText(rowItem.getDate7());
        viewHolder.mNumber8.setText(rowItem.getDate8());
        viewHolder.mNumber9.setText(rowItem.getDate9());
        viewHolder.mNumber10.setText(rowItem.getDate10());
        viewHolder.mNumber11.setText(rowItem.getDate11());
        viewHolder.mNumber12.setText(rowItem.getDate12());
        viewHolder.mNumber13.setText(rowItem.getDate13());
        viewHolder.mNumber14.setText(rowItem.getDate14());
        viewHolder.mNumber15.setText(rowItem.getDate15());
        viewHolder.mNumber16.setText(rowItem.getDate16());
        viewHolder.mNumber17.setText(rowItem.getDate17());
        viewHolder.mNumber18.setText(rowItem.getDate18());
        viewHolder.mNumber19.setText(rowItem.getDate19());
        viewHolder.mNumber20.setText(rowItem.getDate20());
        viewHolder.mNumber21.setText(rowItem.getDate21());
        viewHolder.mNumber22.setText(rowItem.getDate22());
        viewHolder.mNumber23.setText(rowItem.getDate23());
        viewHolder.mNumber24.setText(rowItem.getDate24());
        viewHolder.mNumber25.setText(rowItem.getDate25());
        viewHolder.mNumber26.setText(rowItem.getDate26());
        viewHolder.mNumber27.setText(rowItem.getDate27());
        viewHolder.mNumber28.setText(rowItem.getDate28());
        viewHolder.mNumber29.setText(rowItem.getDate29());
        viewHolder.mNumber30.setText(rowItem.getDate30());

        return convertView;
    }
}
