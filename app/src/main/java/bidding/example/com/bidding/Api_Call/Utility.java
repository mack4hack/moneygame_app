/*
Class Name      :  Utility.java 
Developed By    :  Raghwendra Suryawanshi
Purpose         :  Utility class is used for basic utility which are used in app. 
Created Date    :  09/03/2015
Modified By     :  Raghwendra Suryawanshi
Modified Date   :  28/01/2015
Modified Reason :  added new methods..
Status          :  working.
 */
package bidding.example.com.bidding.Api_Call;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utility {
	public static ArrayList<String> modelListUtil;
	public static ArrayList<String> colorListUtil;
	public static Context appContext;
	public ProgressDialog u_dialog;
	public static final String MOBILE_PATTERN = "[0-9]{10}";
	public static final String LAND_LINE_PATTERN = "[0-9]{11}";
	public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	public static final String VEHICLE_NO_PATTERN = "[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)?[0-9]{4}";
	private static int currentYear, currentMonth, currentDay;

	public Utility(Context context) {
		this.appContext = context;
		modelListUtil = new ArrayList<String>();
		colorListUtil = new ArrayList<String>();
	}

	public static String getCurrentDateTimeStamp(String format) {

		DateFormat dateFormatter = new SimpleDateFormat(format);
		dateFormatter.setLenient(false);
		Date today = new Date();
		String s = dateFormatter.format(today);
		return s;
	}

	public void showDialog(String msg) {
		// To show Dialog...........
		AlertDialog.Builder alert = new AlertDialog.Builder(appContext);
		alert.setTitle("Alert");
		alert.setMessage(msg);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		alert.show();

	}

	public static void showLog(String caller, String msg) {
		// To show Log
		Log.i(MSGUtility.TAG, caller + " :" + msg);
	}

	public static void showLog(String msg) {
		// To show Log
		Log.i(MSGUtility.TAG, " " + msg);
	}
	public void showToastLong(String caller, String msg) {
		// To show Toast Long(duration)....
		Log.i(caller, "showToastLong " + msg);
		Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToastShort(String caller, String msg) {
		// To show Toast Short(duration)....
		Log.i(MSGUtility.TAG, "Toast " + caller + " :" + msg);
		Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToastShort(Context context, String caller, String msg) {
		// To show Toast Short(duration)....
		Log.i(MSGUtility.TAG, caller + ":: showToastShort :" + msg);
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

//	public static boolean isConnectingToInternet() {
//		// Method to check internet connection
//		ConnectivityManager connectivity = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (connectivity != null) {
//			NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null)
//				for (int i = 0; i < info.length; i++)
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						return true;
//					}
//		}
//		return false;
//	}

	public static CharSequence getCurrentDateStamp() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;

	}

	/*
	 * public static String getCurrentDateTimeStamp(String format) {
	 * 
	 * DateFormat dateFormatter = new SimpleDateFormat(format);
	 * dateFormatter.setLenient(false); Date today = new Date(); String s =
	 * dateFormatter.format(today); return s; }
	 */

	// add new
	public static String getColor() {
		// return color for action bar
		return "#3C99F3";
	}

	public void showProgessDialog() {
		u_dialog = new ProgressDialog(appContext);
		u_dialog.setTitle("Uploading data");
		u_dialog.setMessage("Please Wait While Uploading");
		u_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		u_dialog.setCanceledOnTouchOutside(false);
		u_dialog.show();
	}

	// inner classes...

	public static class LogUtility {
		public static void showLog(String msg) {
			// To show Log
			Log.i(MSGUtility.TAG, msg);
		}

		public static void showLog(String msg, String msg2) {
			// To show Log
			Log.i(MSGUtility.TAG, msg + " : " + msg2);
		}

	}

	// Retrieve Devices IMEI Number(Added on 18/02/2015)
	public static String getIMEI(Context con) {
		String identifier = null;
		TelephonyManager tm;
		try {
			tm = (TelephonyManager) con
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm != null)
				identifier = tm.getDeviceId();
			if (identifier == null || identifier.length() == 0)
				identifier = Secure.getString(con.getContentResolver(),
						Secure.ANDROID_ID);
		} catch (Exception e) {
			return "Not Available";
		}
		return identifier;
	}

	public static class ValidationUtility {

		public static boolean isEmpty(EditText editText) {
			// method to check edit text is fill or no
			if (editText.getText().toString().equals("")) {
				return true;
			}
			return false;
		}

		public static boolean isDotOnly(EditText editText) {
			// method to check edit text is fill or no
			if (editText.getText().toString().charAt(0) == '.') {
				return true;
			}

			if (editText.getText().toString().length() == 1
					&& editText.getText().toString().charAt(0) == '.') {
				return true;
			}
			return false;
		}

		public static boolean isAutocompleteEmpty(AutoCompleteTextView editText) {
			// method to check edit text is fill or no
			if (editText.getText().toString().equals("")) {
				return true;
			}
			return false;
		}

		public static boolean isEmailId(EditText editText) {
			// method to check edit text is fill or no
			if (editText.getText().toString().matches(EMAIL_PATTERN)) {
				return true;
			}
			return false;
		}

		public static boolean isVehicleNo(EditText editText) {
			if (editText.getText().toString().matches(VEHICLE_NO_PATTERN)) {
				return true;
			}

			return false;
		}

		public static boolean isContactNo(EditText editText) {

			if (editText.getText().toString().length() != 0
					&& editText.getText().toString().length() != 10)
				return true;
			// method to check edit text is fill or no
			if (editText.getText().toString().length() > 10)
				return true;

			/*
			 * if (editText.getText().toString().matches(MOBILE_PATTERN)) {
			 * return true; } if
			 * (editText.getText().toString().matches(LAND_LINE_PATTERN)) {
			 * return true; }
			 */

			return false;
		}

	}

	public class MSGUtility {
		// contain list of msg which are show in toast or log

		public static final String YES = "Y";
		public static final String NO = "N";
		public static final String PHP_YES = "1";
		public static final String PHP_NO = "0";
		public static final String TAG = "ECS";
		public static final String ADVANCE_AMOUNT_ERROR = "Sorry, advance amount cannot bet greater than actual amount";
		public static final String FLAVOUR = "Enter Flavour";
		public static final String WAIT = "Please wait while downloading...";
		public static final String SAME_STORE_NOT_ALLOWED = "Sorry, cannot be shifted to same store...";
		public static final String ACCESS_DENIED = "Sorry, Access Denied...";
		public static final String MANDATORY = "Please enter input...";
		public static final String AMOUNT_DOT_ONLY = "Sorry, dot only in amount field not allowed !!!";
		public static final String BLANK_AMOUNT = "Please enter amount !!!";
		public static final String CONTACT_ERROR = "Please enter correct contact no !!!";
		public static final String DATA_ADD_SUCCESS = "Data Added Successfully !!!";
		public static final String ORDER_DELETED = "Order Deleted Successfully !!!";
		public static final String DATA_ADD_ERROR = "Error !!!";
		public static final String DATA_FETCHING_ERROR = "Sorry, error while fetching data";
		public static final String ATTENDANCE_INCORRECT = "Sorry, you cannot give attendance within 5 minutes again...";
		public static final String ATTENDANCE_SUCCESSFUL = "Thank you, attendance successfully noted...";
		public static final String ATTENDANCE_ERROR = "Error in noting attendance, please try after sometime";
		public static final String DATA_UPLOAD_SUCCESS = "Data Uploaded Successfully !!!";
		public static final String DATA_UPLOAD_ERROR = "Error to Upload data !!!";
		public static final String DATA_DELETE_ERROR = "Error to delete data !!!";
		public static final String DATA_UPDATE_ERROR = "Error to Update local data !!!";
		public static final String DATA_NO_FOUND = "No data found from web !!!";
		public static final String ALL_MANDATORY = "All fields are mandatory !!!";
		public static final String vehicle_not_found = "Vehicle not found!!!";
		public static final String LOGIN_ERROR = "Incorrect Id Or Password!!!";
		public static final String LOGOUT_ERROR = "Error to logout";
		public static final String LOGIN_DONE = "Please wait your app is getting ready";
		public static final String COMING_SOON = "Coming soon.!!!";
		public static final String SOURCE_OR_TYPE = "Please enter either source or type";
		public static final String NO_ACCESS = "Sorry, access denied, please contact admin...";
		public static final String INVALID_USER_LOGGED = "Sorry, Some other store user has already logged in...";
		public static final String NO_INTERNET_CONNECTION = "No Internet connection.....!!!";
		public static final String LOGIN_CHECK = "Please wait while authenticating login credentials...";
		public static final String DATA_DOWNLOAD_SUCCESS = "Data Downloaded Successfully !!!";
		public static final String NO_CHECKLIST_DATA = "Sorry, No checklist data found...";
		public static final String DATA_UPDATED = "Data Updated Successfully !!!";
		public static final String DATA_DELETED = "Data Deleted Successfully !!!";
		public static final String CHECKLIST_UPLOAD = "Please Wait While Loading Checklist...";
		public static final int SELECT = 1;
		public static final int INSTER = 2;
		public static final int UPDATE = 3;
		public static final int DELETE = 4;

	}

	public class FlagUtility {

		public static final int INSTER = 1;
		public static final int SELECT = 2;
		public static final int UPDATE = 3;
		public static final int DELETE = 4;

		public static final int SELECT_WHERE_ALL = 1;
		public static final int SELECT_WHERE_ID = 2;
		public static final int SELECT_WHERE_DATE_NAME = 3;
		public static final int SELECT_WHERE_NAME = 4;
		public static final int SELECT_WHERE_DATE = 5;

		public static final int DATE_CUURENT_AND_NEW = 1;
		public static final int DATE_NEW_AND_OLD = 2;

		public static final int IN = 1;
		public static final int OUT = 2;
		public static final int ALL = 3;

	}

	public class ColorUtility {

		public static final String RED = "#FF0000";
	}

	public static class DateUtility {
		public final static String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
		public final static String DMY = "dd-MM-yyyy";
		public final static String YMD = "yyyy-MM-dd";
		public final static String HMS = "HH:mm:ss";
		public static final DateFormat TWELVE_TF = new SimpleDateFormat(
				"hh:mma");
		// Replace with kk:mm if you want 1-24 interval
		public static final DateFormat TWENTY_FOUR_TF = new SimpleDateFormat(
				"HH:mm");

		public static String getCurrentDateTimeStamp(String format) {

			DateFormat dateFormatter = new SimpleDateFormat(format);
			dateFormatter.setLenient(false);
			Date today = new Date();
			String s = dateFormatter.format(today);
			return s;
		}

		public static String getCurrentDate() {
			try {
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int monthOfYear = c.get(Calendar.MONTH);
				int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
				String date_selected = "";

				if ((monthOfYear >= 0 && monthOfYear < 9)
						&& (dayOfMonth > 0 && dayOfMonth < 10))
					date_selected = String.valueOf(year) + "-0"
							+ String.valueOf(monthOfYear + 1) + "-0"
							+ String.valueOf(dayOfMonth);
				else if (monthOfYear >= 0 && monthOfYear < 9)
					date_selected = String.valueOf(year) + "-0"
							+ String.valueOf(monthOfYear + 1) + "-"
							+ String.valueOf(dayOfMonth);
				else if (dayOfMonth > 0 && dayOfMonth < 10)
					date_selected = String.valueOf(year) + "-"
							+ String.valueOf(monthOfYear + 1) + "-0"
							+ String.valueOf(dayOfMonth);
				else
					date_selected = String.valueOf(year) + "-"
							+ String.valueOf(monthOfYear + 1) + "-"
							+ String.valueOf(dayOfMonth);
				return date_selected;
			} catch (Exception e) {
				return "";
			}
		}

		public static String getddmmyyDate(String dt) {
			// Converts mm-dd-yy format to dd-mm-yy Added on 05/12/2013
			String dd = "", mm = "", yy = "";
			int i = 0;
			try {
				for (String retval : dt.split("-")) {
					if (i == 0)
						yy = retval;
					else if (i == 1)
						mm = retval;
					else
						dd = retval;

					i++;
				}
				return (dd + "-" + mm + "-" + yy).toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}

		public static String getddmmyyyyDate(String dt, String dateTime) {
			// Converts mm-dd-yy format to dd-mm-yy Added on 05/12/2013
			String dd = "", mm = "", yy = "", time = "";
			if (dateTime.equals("dateTime")) {
				String dtarray[] = dt.split(" ");

				dt = dtarray[0];// 2015-02-23
				time = dtarray[1];// 11:30:30

			}

			int i = 0;
			try {
				for (String retval : dt.split("-")) {
					if (i == 0)
						yy = retval;
					else if (i == 1)
						mm = retval;
					else
						dd = retval;

					i++;
				}
				return (dd + "-" + mm + "-" + yy).toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}

		public static String getyymmddDate(String dt)
		// Converts mm-dd-yy format to dd-mm-yy Added on 05/12/2013
		{
			String dd = "", mm = "", yy = "";
			int i = 0;
			try {
				for (String retval : dt.split("-")) {
					if (i == 0)
						dd = retval;
					else if (i == 1)
						mm = retval;
					else
						yy = retval;
					i++;
				}
				return (yy + "-" + mm + "-" + dd).toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}

		public static void showDatePickerDialog(Context context,
				final int dateFlg, final EditText dateEditText) {
			// Displays Date picker
			final Calendar c = Calendar.getInstance();
			currentYear = c.get(Calendar.YEAR);
			currentMonth = c.get(Calendar.MONTH);
			currentDay = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog datepicker = new DatePickerDialog(context,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view,
								int selectedYear, int monthOfYear,
								int dayOfMonth) {
							int year = selectedYear;
							int month = monthOfYear;
							int day = dayOfMonth;
							if (dateFlg == FlagUtility.DATE_CUURENT_AND_NEW) {
								if ((year != currentYear)
										|| (month < currentMonth && year == currentYear)
										|| (day < currentDay
												&& year == currentYear && month <= currentMonth)) {
									Toast.makeText(appContext,
											"Please select proper date.",
											Toast.LENGTH_SHORT).show();
									dateEditText.setText(Utility
											.getCurrentDateTimeStamp(""));

								} else {
									String date_selected;
									if ((monthOfYear >= 0 && monthOfYear < 9)
											&& (dayOfMonth > 0 && dayOfMonth < 10))
										date_selected = "0"
												+ String.valueOf(dayOfMonth)
												+ "-0"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else if (monthOfYear >= 0
											&& monthOfYear < 9)
										date_selected = String
												.valueOf(dayOfMonth)
												+ "-0"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else if (dayOfMonth > 0 && dayOfMonth < 10)
										date_selected = "0"
												+ String.valueOf(dayOfMonth)
												+ "-"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else
										date_selected = String
												.valueOf(dayOfMonth)
												+ "-"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									dateEditText.setText(date_selected);
								}
							} else if (dateFlg == FlagUtility.DATE_NEW_AND_OLD) {
								String date_selected;
								if ((monthOfYear >= 0 && monthOfYear < 9)
										&& (dayOfMonth > 0 && dayOfMonth < 10))
									date_selected = "0"
											+ String.valueOf(dayOfMonth) + "-0"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else if (monthOfYear >= 0 && monthOfYear < 9)
									date_selected = String.valueOf(dayOfMonth)
											+ "-0"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else if (dayOfMonth > 0 && dayOfMonth < 10)
									date_selected = "0"
											+ String.valueOf(dayOfMonth) + "-"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else
									date_selected = String.valueOf(dayOfMonth)
											+ "-"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								dateEditText.setText(date_selected);
							}
						}
					}, currentYear, currentMonth, currentDay);
			datepicker.show();
		}

		public static void showDatePickerDialog(Context context,
				final int dateFlg, final TextView dateTextView) {
			// Displays Date picker
			final Calendar c = Calendar.getInstance();
			currentYear = c.get(Calendar.YEAR);
			currentMonth = c.get(Calendar.MONTH);
			currentDay = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog datepicker = new DatePickerDialog(context,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view,
								int selectedYear, int monthOfYear,
								int dayOfMonth) {
							int year = selectedYear;
							int month = monthOfYear;
							int day = dayOfMonth;
							if (dateFlg == FlagUtility.DATE_CUURENT_AND_NEW) {
								if ((year != currentYear)
										|| (month < currentMonth && year == currentYear)
										|| (day < currentDay
												&& year == currentYear && month <= currentMonth)) {
									/*Toast.makeText(appContext,
											"Please select proper date.",
											Toast.LENGTH_SHORT).show();
									dateTextView.setText(Utility
											.getCurrentDateTimeStamp(""));
*/
								} else {
									String date_selected;
									if ((monthOfYear >= 0 && monthOfYear < 9)
											&& (dayOfMonth > 0 && dayOfMonth < 10))
										date_selected = "0"
												+ String.valueOf(dayOfMonth)
												+ "-0"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else if (monthOfYear >= 0
											&& monthOfYear < 9)
										date_selected = String
												.valueOf(dayOfMonth)
												+ "-0"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else if (dayOfMonth > 0 && dayOfMonth < 10)
										date_selected = "0"
												+ String.valueOf(dayOfMonth)
												+ "-"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else
										date_selected = String
												.valueOf(dayOfMonth)
												+ "-"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									dateTextView.setText(date_selected);
								}
							} else if (dateFlg == FlagUtility.DATE_NEW_AND_OLD) {
								String date_selected;
								if ((monthOfYear >= 0 && monthOfYear < 9)
										&& (dayOfMonth > 0 && dayOfMonth < 10))
									date_selected = "0"
											+ String.valueOf(dayOfMonth) + "-0"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else if (monthOfYear >= 0 && monthOfYear < 9)
									date_selected = String.valueOf(dayOfMonth)
											+ "-0"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else if (dayOfMonth > 0 && dayOfMonth < 10)
									date_selected = "0"
											+ String.valueOf(dayOfMonth) + "-"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else
									date_selected = String.valueOf(dayOfMonth)
											+ "-"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								dateTextView.setText(date_selected);
							}
						}
					}, currentYear, currentMonth, currentDay);
			datepicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
			datepicker.show();
		}
		//Added on Date 09/07/2015 for Selecting previous and current date
		public static void showDatePickerDialogForMaxDate(Context context,
				final int dateFlg, final EditText dateEditText) {
			// Displays Date picker
			final Calendar c = Calendar.getInstance();
			currentYear = c.get(Calendar.YEAR);
			currentMonth = c.get(Calendar.MONTH);
			currentDay = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog datepicker = new DatePickerDialog(context,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view,
								int selectedYear, int monthOfYear,
								int dayOfMonth) {
							int year = selectedYear;
							int month = monthOfYear;
							int day = dayOfMonth;
							if (dateFlg == FlagUtility.DATE_CUURENT_AND_NEW) {
								if ((year != currentYear)
										|| (month < currentMonth && year == currentYear)
										|| (day < currentDay
												&& year == currentYear && month <= currentMonth)) {
//									Toast.makeText(appContext,
//											"Please select proper date.",
//											Toast.LENGTH_SHORT).show();
//									dateEditText.setText(Utility
//											.getCurrentDateTimeStamp(""));

								} else {
									String date_selected;
									if ((monthOfYear >= 0 && monthOfYear < 9)
											&& (dayOfMonth > 0 && dayOfMonth < 10))
										date_selected = "0"
												+ String.valueOf(dayOfMonth)
												+ "-0"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else if (monthOfYear >= 0
											&& monthOfYear < 9)
										date_selected = String
												.valueOf(dayOfMonth)
												+ "-0"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else if (dayOfMonth > 0 && dayOfMonth < 10)
										date_selected = "0"
												+ String.valueOf(dayOfMonth)
												+ "-"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									else
										date_selected = String
												.valueOf(dayOfMonth)
												+ "-"
												+ String.valueOf(monthOfYear + 1)
												+ "-"
												+ String.valueOf(selectedYear);

									dateEditText.setText(date_selected);
								}
							} else if (dateFlg == FlagUtility.DATE_NEW_AND_OLD) {
								String date_selected;
								if ((monthOfYear >= 0 && monthOfYear < 9)
										&& (dayOfMonth > 0 && dayOfMonth < 10))
									date_selected = "0"
											+ String.valueOf(dayOfMonth) + "-0"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else if (monthOfYear >= 0 && monthOfYear < 9)
									date_selected = String.valueOf(dayOfMonth)
											+ "-0"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else if (dayOfMonth > 0 && dayOfMonth < 10)
									date_selected = "0"
											+ String.valueOf(dayOfMonth) + "-"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								else
									date_selected = String.valueOf(dayOfMonth)
											+ "-"
											+ String.valueOf(monthOfYear + 1)
											+ "-"
											+ String.valueOf(selectedYear);
								dateEditText.setText(date_selected);
							}
						}
					}, currentYear, currentMonth, currentDay);
			datepicker.getDatePicker().setMaxDate(System.currentTimeMillis()+100);
			datepicker.show();
		}

		public static String convertTo24HoursFormat(String twelveHourTime)
				throws ParseException {
			// Returns date in 24 hour format
			return TWENTY_FOUR_TF.format(TWELVE_TF.parse(twelveHourTime));
		}

		public static String convertTo12HoursFormat(String twentyFourHourTime)
				throws ParseException {
			// Returns date in 24 hour format
			return TWELVE_TF.format(TWENTY_FOUR_TF.parse(twentyFourHourTime));
		}

	}

	public static class TimeUtility {
		public static String time;
		public static int currentHour, currentMinute, currentSeconds;
		public static int currentYear, currentMonth, currentDay;
		public static int myFlg = 0;
		public static String cmpftime, cmpttime, cmpfdate, cmptdate,
				aTime = "";

		public static String showTimePickerDialog(final Context appContext,
				final TextView eStartTime) {

			final Calendar c = Calendar.getInstance();
			currentHour = c.get(Calendar.HOUR_OF_DAY);
			currentMinute = c.get(Calendar.MINUTE);
			currentSeconds = c.get(Calendar.SECOND);
			TimePickerDialog tpd = new TimePickerDialog(appContext,
					new OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minutes) {
							int hour = hourOfDay;
							int minute = minutes;
							String time = "" + hourOfDay + "" + minutes + "00";
							TimeUtility.time = time;
							int flg = 0;
							String strHour, strMinutes, strAMPM;

							if (hour > 12) {
								flg = 1;
								hour = hour - 12;
								strAMPM = "PM";
							} else {
								strAMPM = "AM";
							}
							if (hour < 10) {
								strHour = "0" + hour;
							} else {
								strHour = "" + hour;
							}
							if (minute < 10) {
								strMinutes = "0" + minute;
							} else {
								strMinutes = "" + minute;
							}
							eStartTime.setText(strHour + ":" + strMinutes
									+ strAMPM);

						}
					}, currentHour, currentMinute, false);
			tpd.show();

			return "";
		}

		public static void showTimePickerDialogForFixTime(
				final Context appContext, final TextView eStartTime,
				final String startTime, final String endTime) {
			final Calendar c = Calendar.getInstance();
			currentHour = c.get(Calendar.HOUR_OF_DAY);
			currentMinute = c.get(Calendar.MINUTE);
			currentSeconds = c.get(Calendar.SECOND);
			TimePickerDialog tpd = new TimePickerDialog(appContext,
					new OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minutes) {
							int hour = hourOfDay;
							int minute = minutes;
							String time = "" + hourOfDay + "" + minutes + "00";
							TimeUtility.time = time;
							int flg = 0;
							String strHour, strMinutes, strAMPM;

							if (hour > 12) {
								flg = 1;
								hour = hour - 12;
								strAMPM = "PM";
							} else {
								strAMPM = "AM";
							}
							if (hour < 10) {
								strHour = "0" + hour;
							} else {
								strHour = "" + hour;
							}
							if (minute < 10) {
								strMinutes = "0" + minute;
							} else {
								strMinutes = "" + minute;
							}
							eStartTime.setText(strHour + ":" + strMinutes
									+ strAMPM);

						}
					}, currentHour, currentMinute, false);
			tpd.show();
		}
	}

}
