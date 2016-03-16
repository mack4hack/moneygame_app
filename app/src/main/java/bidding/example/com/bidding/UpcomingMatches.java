package bidding.example.com.bidding;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import bidding.example.com.bidding.ConnectionDetect.ConnectionDetector;
import bidding.example.com.bidding.GetterSetter.MatchListGetSet;


public class UpcomingMatches extends Fragment{

    ConnectionDetector connectionDetector;
    String time1, time2;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private List<MatchListGetSet> matchList = new ArrayList<>();
    public static List<MatchListGetSet> matchListUpcoming;
    private Map<Date, List<Date>> bookings = new HashMap<>();

    private static final String tag = "MyCalendarActivity";

    private TextView currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    public UpcomingMatches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
                + year);

        prevMonth = (ImageView) getActivity().findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (month <= 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
                        + month + " Year: " + year);
                setGridCellAdapterToDate(month, year);
            }
        });

        currentMonth = (TextView) getActivity().findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

        nextMonth = (ImageView) getActivity().findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month > 11) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
                Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
                        + month + " Year: " + year);
                setGridCellAdapterToDate(month, year);
            }
        });

        calendarView = (GridView) getActivity().findViewById(R.id.calendar);

        connectionDetector=new ConnectionDetector(getActivity());


        getMatchList();
// Initialised


        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
//        calendar.init(today, nextYear.getTime())
//                .withSelectedDate(today);

//        calendar.setShowWeekNumber(false);


       /* calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (getHolidays().contains(date)) {
                    Log.i("date", "" + date);
                    MatchListGetSet item1 = new MatchListGetSet();
                    for (int i = 0; i < matchList.size(); i++) {
                        MatchListGetSet item = matchList.get(i);
                        String dt = item.getDate();
                        if (item.getDate().contains(date.toString())) {
                            item1.setName(item.getName());
                            item1.setDate(item.getDate());
                            matchListUpcoming.add(item1);
                        }

                    }
                    startActivity(new Intent(getActivity(), UpcomingList.class));
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });*/

    }

    /**
     *
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getActivity(),
                R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }


    private void getMatchList()
    {
        if(connectionDetector.isConnectingToInternet())
        {

            String tag_json_obj = "json_obj_req";
            final String TAG = "response";
            final String url = getString(R.string.get_match_list);//+ URLEncoder.encode("/"+postString);

            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    pDialog.hide();
                    try {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("status").equals("true"))
                        {
                            JSONObject innerObject = object.getJSONObject("data");
                            Log.d(TAG, innerObject.toString());
                            JSONArray jsonArray = innerObject.getJSONArray("Cricket_Match");

                            if(jsonArray.length()!=0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject childObject = jsonArray.getJSONObject(i);
                                    MatchListGetSet item = new MatchListGetSet();
                                    Calendar cal = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
//                                    df.setTimeZone (TimeZone.getTimeZone ("IST"));
                                    cal.add(cal.HOUR, -10);
                                    time1 = df.format(new Date(cal.getTimeInMillis()));
//                                    Log.i("time1", "" + time1);

                                    Calendar cal1 = Calendar.getInstance();
                                    cal1.add(cal1.HOUR, 48);
                                    time2= df.format(new Date(cal1.getTimeInMillis()));
//                                    Log.i("time2", "" + time2);

                                    String time = childObject.getString("start_date");
                                    time = time.replace("T"," ");
                                    time = time.replace("-","/");
                                    String [] split1=time.split(", ");
                                    String t= split1[1];
                                    String [] split = t.split("\\u002B");
                                    if(df.parse(split[0]).after(df.parse(time1))) // && df.parse(split[0]).before(df.parse(time2)
                                    {
                                        item.setId(childObject.getString("id"));;
                                        item.setName(childObject.getString("name"));
                                        item.setDate(split[0]);
                                        item.setVenue(childObject.getString("venue"));
                                        matchList.add(item);

                                    }

                                }

                            }
                            adapter = new GridCellAdapter(getActivity(),
                                    R.id.calendar_day_gridcell, month, year);
                            adapter.notifyDataSetChanged();
                            calendarView.setAdapter(adapter);
//                            addEvents(calendar, Calendar.MARCH);
//                            calendar.highlightDates(getHolidays());
//                            Log.i("date", "" + getHolidays());
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.hide();
                    if(error instanceof TimeoutError)
                    {
                        Toast.makeText(getActivity(), "Request Timeout!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                    VolleyLog.d("CUrrent Result", "Error: " + error.getMessage());
                    //    pDialog.hide();
                }
            }) ;

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
            AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }
        else
        {
            Toast.makeText(getActivity(),getString(R.string.internet_err),Toast.LENGTH_SHORT).show();
        }

    }


// Inner Class
public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String tag = "GridCellAdapter";
    private final Context _context;

    private final List<String> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat" };
    private final String[] months = { "January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31 };
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private Button gridcell;
    private TextView num_events_per_day;
    private final ArrayList<Date> events;
    private final HashMap<String, Integer> eventsPerMonthMap;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "dd-MMM-yyyy");

    // Days in Current Month
    public GridCellAdapter(Context context, int textViewResourceId,
                           int month, int year) {
        super();
        this._context = context;
        this.list = new ArrayList<String>();
        Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
                + "Year: " + year);
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
        Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
        Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
        Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

// Print Month
        printMonth(month, year);

// Find Number of Events
        events = getHolidays();
        Log.i("e",""+events);
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        getHolidays();
    }

    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Prints Month
     *
     * @param mm
     * @param yy
     */
    private void printMonth(int mm, int yy) {
        Log.d(tag, "==> printMonth: mm: " + mm + " "  + "yy: " + yy);
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        Log.d(tag, "Current Month: " + " "  + currentMonthName + " having "
                + daysInMonth + "days ");

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
        Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
            Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
            Log.d(tag, "**–> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            Log.d(tag, "***—> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK)-1;
        trailingSpaces = currentWeekDay;

        Log.d(tag, "Week Day:" + currentWeekDay + " is "
                + getWeekDayAsString(currentWeekDay));
        Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
        Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

// Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            Log.d(tag,
                    "PREV MONTH:= "
                            + prevMonth
                            + " => "
                            + getMonthAsString(prevMonth)
                            + " "
                            + String.valueOf((daysInPrevMonth
                            - trailingSpaces + DAY_OFFSET)
                            + i));
            list.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i)
                    + "-GREY"
                    + "-"
                    + getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);
        }

// Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            Log.d(currentMonthName, String.valueOf(i) + " "
                    + getMonthAsString(currentMonth) + " " + yy);
            if (i == getCurrentDayOfMonth()) {
                list.add(String.valueOf(i) + "-BLUE" + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            } else {
                list.add(String.valueOf(i) + "-WHITE" + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            }
        }

// Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
            list.add(String.valueOf(i + 1) + "-GREY" + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }


    }

    /**
     * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
     * ALL entries from a SQLite database for that month. Iterate over the
     * List of All entries, and get the dateCreated, which is converted into
     * day.
     *
     * @param year
     * @param month
     * @return
     */
    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                int month) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("17",3);
        map.put("19",3);
        map.put("22",3);
        return map;
    }

    private ArrayList<Date> getHolidays(){
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        ArrayList<Date> holidays = new ArrayList<>();
        for(int i=0; i<matchList.size(); i++){
            MatchListGetSet item = matchList.get(i);
            try {
                Date dte = df1.parse(item.getDate());
                String date1 = dateFormatter.format(dte);
                Log.i("date",""+date1);
                Date date=dateFormatter.parse(date1);
                holidays.add(date);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return holidays;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.screen_gridcell, parent, false);
        }

// Get a reference to the Day gridcell
        gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
        gridcell.setOnClickListener(this);

// ACCOUNT FOR SPACING

        Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];


// Set the Day GridCell
        gridcell.setText(theday);
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);
        Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
                + theyear+ " "+weekdays);

        if (day_color[1].equals("GREY")) {
            gridcell.setTextColor(getResources()
                    .getColor(R.color.lightgray02));
            gridcell.setBackgroundColor(getResources().getColor(R.color.lightgray));
        }
        if (day_color[1].equals("WHITE")) {
            gridcell.setTextColor(getResources().getColor(
                    R.color.black));
            gridcell.setBackgroundColor(getResources().getColor(R.color.lightgray));
        }

        try {
            Date sdate = dateFormatter.parse(theday + "-" + themonth + "-" + theyear);
            String Sundate= sdate.toString();
            String [] split= Sundate.split(" ");
            if (split[0].equals("Sun")) {
                gridcell.setTextColor(getResources().getColor(
                        R.color.red));
                gridcell.setBackgroundColor(getResources().getColor(R.color.lightgray));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if ((!events.isEmpty()) && (events != null)) {
            try {
                Log.i("event",""+dateFormatter.parse(theday + "-" + themonth + "-" + theyear));
                if (events.contains(dateFormatter.parse(theday + "-" + themonth + "-" + theyear))) {
                    num_events_per_day = (TextView) row
                            .findViewById(R.id.num_events_per_day);
//                    Integer numEvents = (Integer) events.get(theday);
//                    num_events_per_day.setText(numEvents.toString());
                    gridcell.setTextColor(getResources().getColor(
                            R.color.lightblue));
                    gridcell.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if (day_color[1].equals("BLUE")) {
            gridcell.setTextColor(getResources().getColor(
                    R.color.white));
            gridcell.setBackgroundColor(getResources().getColor(R.color.headblue));
        }

        return row;
    }

    @Override
    public void onClick(View view) {
        String date_month_year = (String) view.getTag();
        Log.e("Selected date", date_month_year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
//        String date = day + "-" + (month + 1) + "-" + year;

        try {
            if (getHolidays().contains(dateFormatter.parse(date_month_year))) {

                matchListUpcoming = new ArrayList<MatchListGetSet>();
                for (int i = 0; i < matchList.size(); i++) {
                    MatchListGetSet item1 = new MatchListGetSet();
                    MatchListGetSet item = matchList.get(i);
                    String dt = item.getDate();
//                    Log.i("date", "" + sdf.format(df1.parse(dt)));
//                    Log.i("date2", "" + sdf.format(sdf.parse(date_month_year)));
                    if (dateFormatter.format(df1.parse(dt)).equals(dateFormatter.format(dateFormatter.parse(date_month_year)))) {
                        item1.setName(item.getName());
                        item1.setDate(item.getDate());
                        matchListUpcoming.add(item1);
//                        Log.i("list",""+item1.getName());
                    }

                }
                startActivity(new Intent(getActivity(), UpcomingList.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }
}
}
