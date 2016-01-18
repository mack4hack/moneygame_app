package bidding.example.com.bidding.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import bidding.example.com.bidding.GetterSetter.chartGetSet;
import bidding.example.com.bidding.R;
import bidding.example.com.bidding.ResultChart.result_chart;

/**
 * Created by Sandesh on 04-Dec-15.
 */
public class chartAdapter extends BaseAdapter
{
    Context context;
    List<HashMap<String, String>> chartList;
    public chartAdapter(Context context,List<HashMap<String, String>> chartList)
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
        TextView mNumber31,mNumber32,mNumber33,mNumber34,mNumber35,mNumber36,mNumber37,mNumber38,mNumber39,mNumber40,mNumber41,mNumber42,mNumber43,mNumber44,mNumber45,mNumber46,mNumber47,mNumber48,mNumber49,mNumber50,mNumber51,mNumber52,mNumber53,mNumber54,mNumber55,mNumber56,mNumber57,mNumber58,mNumber59,mNumber60;
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

//            viewHolder.mTime = (TextView) convertView.findViewById(R.id.text_view1);
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
            viewHolder.mNumber31 = (TextView) convertView.findViewById(R.id.text_view32);
            viewHolder.mNumber32 = (TextView) convertView.findViewById(R.id.text_view33);
            viewHolder.mNumber33 = (TextView) convertView.findViewById(R.id.text_view34);
            viewHolder.mNumber34 = (TextView) convertView.findViewById(R.id.text_view35);
            viewHolder.mNumber35 = (TextView) convertView.findViewById(R.id.text_view36);
            viewHolder.mNumber36 = (TextView) convertView.findViewById(R.id.text_view37);
            viewHolder.mNumber37 = (TextView) convertView.findViewById(R.id.text_view38);
            viewHolder.mNumber38 = (TextView) convertView.findViewById(R.id.text_view39);
            viewHolder.mNumber39 = (TextView) convertView.findViewById(R.id.text_view40);
            viewHolder.mNumber40 = (TextView) convertView.findViewById(R.id.text_view41);
            viewHolder.mNumber41 = (TextView) convertView.findViewById(R.id.text_view42);
            viewHolder.mNumber42 = (TextView) convertView.findViewById(R.id.text_view43);
            viewHolder.mNumber43 = (TextView) convertView.findViewById(R.id.text_view44);
            viewHolder.mNumber44 = (TextView) convertView.findViewById(R.id.text_view45);
            viewHolder.mNumber45 = (TextView) convertView.findViewById(R.id.text_view46);
            viewHolder.mNumber46 = (TextView) convertView.findViewById(R.id.text_view47);
            viewHolder.mNumber47 = (TextView) convertView.findViewById(R.id.text_view48);
            viewHolder.mNumber48 = (TextView) convertView.findViewById(R.id.text_view49);
            viewHolder.mNumber49 = (TextView) convertView.findViewById(R.id.text_view50);
            viewHolder.mNumber50 = (TextView) convertView.findViewById(R.id.text_view51);
            viewHolder.mNumber51 = (TextView) convertView.findViewById(R.id.text_view52);
            viewHolder.mNumber52 = (TextView) convertView.findViewById(R.id.text_view53);
            viewHolder.mNumber53 = (TextView) convertView.findViewById(R.id.text_view54);
            viewHolder.mNumber54 = (TextView) convertView.findViewById(R.id.text_view55);
            viewHolder.mNumber55 = (TextView) convertView.findViewById(R.id.text_view56);
            viewHolder.mNumber56 = (TextView) convertView.findViewById(R.id.text_view57);
            viewHolder.mNumber57 = (TextView) convertView.findViewById(R.id.text_view58);
            viewHolder.mNumber58 = (TextView) convertView.findViewById(R.id.text_view59);
            viewHolder.mNumber59 = (TextView) convertView.findViewById(R.id.text_view60);
            viewHolder.mNumber60 = (TextView) convertView.findViewById(R.id.text_view61);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder) convertView.getTag();
        }

        HashMap<String, String> rowItem = chartList.get(position);
//        Log.i("rowitwm", "" + rowItem);
//        viewHolder.mTime.setText(rowItem.get(result_chart.TIMESLOT));

//       for(int k=0; k<96; k++){
//            viewHolder.mTime.setText(rowItem.getTime());
//        }

//        for(int i = 0; i<chartList.size(); i++) {

            viewHolder.mNumber1.setText(rowItem.get(result_chart.TIMESLOT1));
            viewHolder.mNumber2.setText(rowItem.get(result_chart.TIMESLOT2));
            viewHolder.mNumber3.setText(rowItem.get(result_chart.TIMESLOT3));
            viewHolder.mNumber4.setText(rowItem.get(result_chart.TIMESLOT4));
            viewHolder.mNumber5.setText(rowItem.get(result_chart.TIMESLOT5));
            viewHolder.mNumber6.setText(rowItem.get(result_chart.TIMESLOT6));
            viewHolder.mNumber7.setText(rowItem.get(result_chart.TIMESLOT7));
            viewHolder.mNumber8.setText(rowItem.get(result_chart.TIMESLOT8));
            viewHolder.mNumber9.setText(rowItem.get(result_chart.TIMESLOT9));
            viewHolder.mNumber10.setText(rowItem.get(result_chart.TIMESLOT10));
            viewHolder.mNumber11.setText(rowItem.get(result_chart.TIMESLOT11));
            viewHolder.mNumber12.setText(rowItem.get(result_chart.TIMESLOT12));
            viewHolder.mNumber13.setText(rowItem.get(result_chart.TIMESLOT13));
            viewHolder.mNumber14.setText(rowItem.get(result_chart.TIMESLOT14));
            viewHolder.mNumber15.setText(rowItem.get(result_chart.TIMESLOT15));
            viewHolder.mNumber16.setText(rowItem.get(result_chart.TIMESLOT16));
            viewHolder.mNumber17.setText(rowItem.get(result_chart.TIMESLOT17));
            viewHolder.mNumber18.setText(rowItem.get(result_chart.TIMESLOT18));
            viewHolder.mNumber19.setText(rowItem.get(result_chart.TIMESLOT19));
            viewHolder.mNumber20.setText(rowItem.get(result_chart.TIMESLOT20));
            viewHolder.mNumber21.setText(rowItem.get(result_chart.TIMESLOT21));
            viewHolder.mNumber22.setText(rowItem.get(result_chart.TIMESLOT22));
            viewHolder.mNumber23.setText(rowItem.get(result_chart.TIMESLOT23));
            viewHolder.mNumber24.setText(rowItem.get(result_chart.TIMESLOT24));
            viewHolder.mNumber25.setText(rowItem.get(result_chart.TIMESLOT25));
            viewHolder.mNumber26.setText(rowItem.get(result_chart.TIMESLOT26));
            viewHolder.mNumber27.setText(rowItem.get(result_chart.TIMESLOT27));
            viewHolder.mNumber28.setText(rowItem.get(result_chart.TIMESLOT28));
            viewHolder.mNumber29.setText(rowItem.get(result_chart.TIMESLOT29));
            viewHolder.mNumber30.setText(rowItem.get(result_chart.TIMESLOT30));
            viewHolder.mNumber31.setText(rowItem.get(result_chart.TIMESLOT31));
            viewHolder.mNumber32.setText(rowItem.get(result_chart.TIMESLOT32));
            viewHolder.mNumber33.setText(rowItem.get(result_chart.TIMESLOT33));
            viewHolder.mNumber34.setText(rowItem.get(result_chart.TIMESLOT34));
            viewHolder.mNumber35.setText(rowItem.get(result_chart.TIMESLOT35));
            viewHolder.mNumber36.setText(rowItem.get(result_chart.TIMESLOT36));
            viewHolder.mNumber37.setText(rowItem.get(result_chart.TIMESLOT37));
            viewHolder.mNumber38.setText(rowItem.get(result_chart.TIMESLOT38));
            viewHolder.mNumber39.setText(rowItem.get(result_chart.TIMESLOT39));
            viewHolder.mNumber40.setText(rowItem.get(result_chart.TIMESLOT40));
            viewHolder.mNumber41.setText(rowItem.get(result_chart.TIMESLOT41));
            viewHolder.mNumber42.setText(rowItem.get(result_chart.TIMESLOT42));
            viewHolder.mNumber43.setText(rowItem.get(result_chart.TIMESLOT43));
            viewHolder.mNumber44.setText(rowItem.get(result_chart.TIMESLOT44));
            viewHolder.mNumber45.setText(rowItem.get(result_chart.TIMESLOT45));
            viewHolder.mNumber46.setText(rowItem.get(result_chart.TIMESLOT46));
            viewHolder.mNumber47.setText(rowItem.get(result_chart.TIMESLOT47));
            viewHolder.mNumber48.setText(rowItem.get(result_chart.TIMESLOT48));
            viewHolder.mNumber49.setText(rowItem.get(result_chart.TIMESLOT49));
            viewHolder.mNumber50.setText(rowItem.get(result_chart.TIMESLOT50));

        viewHolder.mNumber51.setText(rowItem.get(result_chart.TIMESLOT51));
        viewHolder.mNumber52.setText(rowItem.get(result_chart.TIMESLOT52));
        viewHolder.mNumber53.setText(rowItem.get(result_chart.TIMESLOT53));
        viewHolder.mNumber54.setText(rowItem.get(result_chart.TIMESLOT54));
        viewHolder.mNumber55.setText(rowItem.get(result_chart.TIMESLOT55));
        viewHolder.mNumber56.setText(rowItem.get(result_chart.TIMESLOT56));
        viewHolder.mNumber57.setText(rowItem.get(result_chart.TIMESLOT57));
        viewHolder.mNumber58.setText(rowItem.get(result_chart.TIMESLOT58));
        viewHolder.mNumber59.setText(rowItem.get(result_chart.TIMESLOT59));
        viewHolder.mNumber60.setText(rowItem.get(result_chart.TIMESLOT60));
//        }

        return convertView;
    }
}
