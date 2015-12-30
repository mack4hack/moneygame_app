/*
Class Name      :  UploadAllData.java 
Developed By    :  Raghwendra Suryawanshi
Purpose         :  UploadAllData class is used to upload all data from local to server side. 
Created Date    :  17/03/2015
Modified By     :  Raghwendra Suryawanshi
Modified Date   :  
Modified Reason :  
Status          :  
 */
package bidding.example.com.bidding.Api_Call;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;

public class UploadAllData
{
	int proess = 0;
	//public static Utility utility;
	public static JSONArray array;
	//public static DataBaseHelper db;
	public static Context appContext;
	public static AppWebService appWebService;
	public static int moveflag=0;
	public static int flag=0;
	public Button btnupload;
	public Activity activity;
	public String moveactivity="";
	public Intent intent;
	
	public UploadAllData(Context context)
	{
		UploadAllData.appContext = context;
		//utility = new Utility(appContext);
		appWebService = new AppWebService(appContext);
		//db = new DataBaseHelper(appContext);

	}

	public boolean uploadAll() {
		/*if (BU.isConnectingToInternet(appContext))
		{
			LogUtility.showLog("Inside downloadAll of download class");
			try {
				new UploadTask().execute("All");
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
*/
		return false;
	}

	//on update and exit flag
	public boolean uploadTable(Activity activity,String moveactivity,String strTable)
	{
		/*if (BU.isConnectingToInternet(appContext))
		{
			LogUtility.showLog("Inside downloadAll of download class");
			try {
				this.activity=activity;
				this.moveactivity=moveactivity;
				moveflag=1;
				new UploadTask().execute(strTable);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}*/
		return false;
	}
	

	//insert
	public boolean uploadTable(String strTable)
	{

			Utility.LogUtility.showLog("Inside downloadAll of download class");
			try
			{
				new UploadTask().execute(strTable);
				return true;
			} catch (Exception e)
			{
				// TODO: handle exception
				return false;
			}


	}

	public class UploadTask extends AsyncTask<String, Void, Void>
	{
		 ProgressDialog u_dialog;
		String uploadTable;
		

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
	//		LogUtility.showLog("Inside onPreExecute");
			u_dialog = new ProgressDialog(appContext);
			u_dialog.setTitle("Upload data");
			u_dialog.setMessage("Please Wait While uploading data");
			u_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			u_dialog.setCanceledOnTouchOutside(false);
			
			u_dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			uploadTable = new String(params[0]);
			

			/*if (uploadTable.equals(Tablenames.TABLE_VENDOR_REGISTRATION))
			{
				Log.i("ECS", "uploadTable :" + uploadTable);
	//			uploadVenderDataNow();

			}*/
			return null;
		}
//...................
		@Override
		protected void onPostExecute(Void result)
		{
			// TODO Auto-generated method stub

			if (u_dialog.isShowing()) 
			{
		      u_dialog.dismiss();
		      
	//	      LTU.LE("UPLOAD TABlE", uploadTable);

		      Toast.makeText(appContext, "Data uploaded Successfully",Toast.LENGTH_LONG).show();	
			}
			super.onPostExecute(result);
		}
		private void uploadIncomeNow()
		{
			// sends local product registration data to appWebService.uplode()

			/*ModelIncomeDetails object;
			ArrayList<ModelIncomeDetails> list;
			if (getIncome()) {
				try {
					LogUtility.showLog("uploadIncomeNow", "array");
					LogUtility.showLog(" ", " " + array);
					String str_success = appWebService.uplode(array,
							DataBaseConstants.Tablenames.TABLE_INCOME);
					Utility.showLog("UploadTask ", "str_success " + str_success);
					if (str_success.equals("fail")) {
						Utility.showLog("UploadTask ", ""
								+ MSGUtility.DATA_UPLOAD_ERROR);
					} else {
						if (str_success != null || str_success != "null") {
							Utility.showLog("after if ", "str_success :"
									+ str_success);
							JSONArray jArray = new JSONArray();
							try {
								jArray = new JSONArray(str_success);
								list = new ArrayList<ModelIncomeDetails>();
								JSONObject json_data;
								Utility.showLog("UploadTask ", "jArray.length() :"
										+ jArray.length());
								for (int i = 0; i < jArray.length(); i++) {
									// code here
									Utility.showLog("UploadTask ",
											"jArray.length() :" + jArray.length());
									Utility.showLog("UploadTask ", "i :" + i);
									object = new ModelIncomeDetails();
									json_data = jArray.getJSONObject(i);

									object.setPhpID(""
											+ json_data
													.getString(DataBaseConstants.ConstantsColumns.PHP_ID));
									object.setId(""
											+ json_data
													.getString(DataBaseConstants.ConstantsColumns.WEB_ID));
									list.add(object);

								}
								DBIncome.updateLocal(list);
								// DBOrderDetails.updateAsPerMaster();
							} catch (Exception e) {
								e.printStackTrace();
								Utility.showLog("UploadTask ",
										"in side Try Exception e" + e.toString()
												+ str_success);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					Utility.showLog("UploadTask ", " " + e.toString());
				}
			} else {
				Utility.showLog("UploadTask ", " " + MSGUtility.DATA_UPLOAD_ERROR);
				// utility.showToastShort("UploadTask ",
				// );

			}
*/
		}



	public boolean getOrderStatus() {
		// get vendor payment data from local db
/*		array = new JSONArray();
		ArrayList<ModelOrderMaster> list = new ArrayList<ModelOrderMaster>();
		try {
			db = new DataBaseHelper(appContext);
			list = DBOrderMaster.getStatusToUpload();
			LTU.LI(MU.TAG, "getOrderStatus.size() " + list.size());
			if (list != null) {
				LTU.LI(MU.TAG, "getRawMaterialStock list.size() " + list.size());
				if (list.size() > 0) 
				{
					// isAvailable = true;
					for (int i = 0; i < list.size(); i++) {
						try {
							JSONObject obj = new JSONObject();
							obj.put(ConstantsOrderMaster.PHP_ID, list.get(i).getPhp_id());
							LTU.LE("ORDER PID", list.get(i).getPhp_id());
						    obj.put(ConstantsColumns.WEB_ID, list.get(i).getId());
						    LTU.LE("ORDER ID", list.get(i).getId());
							obj.put(ConstantsOrderMaster.STATUS, list.get(i).getStatus());
							LTU.LE("ORDER STATUS", list.get(i).getStatus());

							array.put(obj);
							Utility.showLog("UploadTask ","array.put(obj); exitxddvfvdfvd");
						} catch (Exception e) {
							// Auto-generated blockS
							e.printStackTrace();
							Utility.showLog("UploadTask ",
									"getLocalData in side for-try Exception e"
											+ e.toString());
						}
					}
				} else {
					// no record found
					Utility.showLog("UploadTask  >0 ",
							"No data found to upload");
					return false;
				}
			} else {
				// no record found
				Utility.showLog("UploadTask !=null ", "No data found to upload");
				return false;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Utility.showLog("UploadTask ", "Exception e1 " + e1.toString());
			return false;
		}
	*/	return true;

	}
}}
