package bidding.example.com.bidding.AppDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sandesh on 01-Dec-15.
 */
public class DbAdapter extends SQLiteOpenHelper
{
    private static final int db_version=1;
    public static final String TAG = "DBAdapter";
    public static final String DATABASE_NAME = "App_DB";
    public static final String DATABASE_First_Number_TABLE = "MultipleBetNoFirst";
    public static final String DATABASE_Second_Number_TABLE = "MultipleBetNoSecond";
    public static final String DATABASE_History_TABLE = "HistoryTable";
    public static final String DATABASE_Match_Details_TABLE = "MatchDetailsTable";
    public static final String DATABASE_Match_Details_Odds_TABLE = "MatchDetailsOddsTable";

    public SQLiteDatabase db;

    public static final String id="Id";
    public static final String col_1="first";
    public static final String col_2="second";
    public static final String col_3="third";
    public static final String col_4="four";

    public static final String id21="Id";
    public static final String col_21="second_first";
    public static final String col_22="second_second";
    public static final String col_23="second_third";
    public static final String col_24="second_fourth";
    public static final String col_25="second_fifth";
    public static final String col_26="second_sixth";
    public static final String col_27="second_seventh";
    public static final String col_28="second_eight";

  //Today History Table
    public static final String result_id="id";
    public static final String number="bet_number";
    public static final String amount="bet_amount";
    public static final String time="bet_time";
    public static final String result="result_status";
    public static final String margin="charges";
    public static final String date="date";

//    Match Details Table
    public static final String match_autoid="id";
    public static final String match_id="match_id";
    public static final String match_name="name";
    public static final String match_format="format";
    public static final String match_venue="venue";
    public static final String match_start_date="start_date";
    public static final String match_winner_team="winner_team";
    public static final String match_status="status";

    //    Match Details Odds Table
    public static final String odds_autoid="id";
    public static final String odds_id="oid";
    public static final String odds_match_id="match_id";
    public static final String odds_m_id="m_id";
    public static final String odds_odd_id="odd_id";
    public static final String odds_odd="odds";
    public static final String odds_total_chips="total_chips";
    public static final String odds_payout="payout";
    public static final String odds_result_bet="result_bet";
    public static final String odds_game_close="game_close";
    public static final String odds_perticulars="perticulars";
    public static final String odds_name="name";
    public static final String odds_unique="ounique";
    public static final String odds_format="format";
    public static final String odds_venue="venue";
    public static final String odds_start_date="start_date";
    public static final String odds_team_a="team_a";
    public static final String odds_team_b="team_b";
    public static final String odds_winner_team="winner_team";
    public static final String odds_status="status";
    public static final String odds_game_name="game_name";

  //Create First Table
    public static final String Create_first_table="create table "+DATABASE_First_Number_TABLE+"("+id+" integer primary key autoincrement,"+col_1+" text,"+col_2+" text,"+col_3+" text,"+col_4+" text)";//Create First Table

  //Create Second Table
    public static final String Create_second_table="create table "+DATABASE_Second_Number_TABLE+"("+id21+" integer primary key autoincrement,"+col_21+" text,"+col_22+" text,"+col_23+" text,"+col_24+" text,"+col_25+" text,"+col_26+" text,"+col_27+" text,"+col_28+" text)";

  //Create History Table
    public static final String Create_history_table = "create table "+DATABASE_History_TABLE+"("+result_id+" integer primary key autoincrement,"+number+" text,"+amount+" text,"+time+" text,"+result+" text,"+margin+" text,"+date+" text)";

    //Create Match Details Table
    public static final String Create_Match_Details_table = "create table "+DATABASE_Match_Details_TABLE+"("+match_autoid+" integer primary key autoincrement,"+match_id+" text,"+match_name+" text,"+match_format+" text,"+match_venue+" text,"+match_start_date+" text,"+match_winner_team+" text,"+match_status+" text)";

    //Create Match Details Odds Table
    public static final String Create_Match_Details_Odds_table = "create table "+DATABASE_Match_Details_Odds_TABLE+"("+odds_autoid+" integer primary key autoincrement,"+odds_id+" text,"+odds_match_id+" text,"+odds_m_id+" text,"+odds_odd_id+" text,"+odds_odd+" text,"+odds_total_chips+" text,"+odds_payout+" text,"+odds_result_bet+" text,"+odds_game_close+" text" +
            ","+odds_perticulars+" text,"+odds_name+" text,"+odds_unique+" text,"+odds_format+" text,"+odds_venue+" text,"+odds_start_date+" text,"+odds_team_a+" text,"+odds_team_b+" text,"+odds_winner_team+" text,"+odds_status+" text,"+odds_game_name+" text)";

    public DbAdapter(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    //Open the connection
    public SQLiteDatabase open() throws SQLException
    {
        db = this.getWritableDatabase();
        return db;
    }

    //close the connection
    public void close(SQLiteDatabase db)
    {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(Create_first_table);
            db.execSQL(Create_second_table);
            db.execSQL(Create_history_table);
            db.execSQL(Create_Match_Details_table);
            db.execSQL(Create_Match_Details_Odds_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXIST"+ Create_first_table);
        db.execSQL("DROP TABLE IF EXIST"+ Create_second_table);
        db.execSQL("DROP TABLE IF EXIST"+ Create_history_table);
        db.execSQL("DROP TABLE IF EXIST" + Create_Match_Details_table);
        db.execSQL("DROP TABLE IF EXIST" + Create_Match_Details_Odds_table);
        onCreate(db);
    }

    public long InsertFirst(String first,String two,String three,String four)
    {
        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(col_1,first);
            values.put(col_2,two);
            values.put(col_3,three);
            values.put(col_4,four);

            result = db.insert(DATABASE_First_Number_TABLE,null,values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public long InsertSecond(String first,String two,String three,String four,String Five,String Six,String Seven,String Eight)
    {
        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(col_21,first);
            values.put(col_22,two);
            values.put(col_23,three);
            values.put(col_24,four);
            values.put(col_25,Five);
            values.put(col_26,Six);
            values.put(col_27,Seven);
            values.put(col_28,Eight);

            result = db.insert(DATABASE_Second_Number_TABLE,null,values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public long InsertDetails(String id,String name,String format,String venue, String start, String winner, String status)
    {
        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(match_id,id);
            values.put(match_name,name);
            values.put(match_format,format);
            values.put(match_venue,venue);
            values.put(match_start_date,start);
            values.put(match_winner_team,winner);
            values.put(match_status,status);

            result = db.insert(DATABASE_Match_Details_TABLE,null,values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public long InsertOdds(String id, String mcthid,String mid,String oddid,String odd, String total_chip, String payout, String result_bet,String gameclose,String particular,String name,String unique, String format,
                           String venue, String strtdt,String teama,String teamb,String winner,String status, String game_name)
    {
        long result = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(odds_id,id);
            values.put(odds_m_id,mid);
            values.put(odds_odd_id,oddid);
            values.put(odds_odd,odd);
            values.put(odds_total_chips,total_chip);
            values.put(odds_payout,payout);
            values.put(odds_result_bet,result_bet);
            values.put(odds_game_close,gameclose);
            values.put(odds_perticulars,particular);
            values.put(odds_name,name);
            values.put(odds_unique,unique);
            values.put(odds_format,format);
            values.put(odds_venue,venue);
            values.put(odds_start_date,strtdt);
            values.put(odds_team_a,teama);
            values.put(odds_team_b,teamb);
            values.put(odds_winner_team,winner);
            values.put(odds_status,status);
            values.put(odds_game_name,game_name);


            result = db.insert(DATABASE_Match_Details_Odds_TABLE,null,values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public Cursor GetDetails(int no) throws SQLException
    {
        String query = "SELECT * FROM "+ DATABASE_Match_Details_TABLE+" where "+match_autoid+"='"+no+"'";

        return db.rawQuery(query,null);
    }

    public Cursor GetMatchOdds(int no) throws SQLException
    {
        String query = "SELECT * FROM "+ DATABASE_Match_Details_Odds_TABLE+" where "+id+"='"+no+"'";

        return db.rawQuery(query,null);
    }

    public Cursor GetRowFromFirst(String no) throws SQLException
    {
        String query = "SELECT * FROM "+ DATABASE_First_Number_TABLE+" where "+col_1+"='"+no+"' or "+ col_2 +"='"+no +"' or "+ col_3+"='"+no +"' or "+ col_4+"='"+no+"'";

        return db.rawQuery(query,null);
    }

    public Cursor GetRowFromSecond(String no) throws SQLException
    {
        String query = "SELECT * FROM "+ DATABASE_Second_Number_TABLE+" where "+col_21+"='"+no+"' or "+ col_22 +"='"+no +"' or "+ col_23+"='"+no +"' or "+ col_24+"='"+no+"' or "+ col_25+"='"+no+"' or "+ col_26+"='"+no+"' or "+ col_27+"='"+no+"' or "+ col_28+"='"+no+"'";
Log.i("Query",""+query);
        return db.rawQuery(query,null);
    }

    public long InsertBet(String no,String amt,String betTime,String betResult,String marginAmt,String betDate)
    {
        long QueryResult = -1;
        try
        {
            ContentValues values = new ContentValues();

            values.put(number,no);
            values.put(amount,amt);
            values.put(time,betTime);
            values.put(result,betResult);
            values.put(margin,marginAmt);
            values.put(date,betDate);

            QueryResult = db.insert(DATABASE_History_TABLE,null,values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return QueryResult;
    }

    public Cursor getTodayHistory()
    {
        Cursor cursor = null;
        try
        {
            String query = "select * from "+DATABASE_History_TABLE;
            cursor = db.rawQuery(query, null);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return cursor;
    }

    public void deleteOldRecord(String date)
    {
        String query = "delete from "+DATABASE_History_TABLE+" where "+date+" not like "+date;
        db.execSQL(query);
    }
}
