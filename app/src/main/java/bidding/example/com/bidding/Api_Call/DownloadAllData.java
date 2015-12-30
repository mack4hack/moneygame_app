/*
Class Name      :  DownloadAllData.java 
Developed By    :  Raghwendra Suryawanshi
Purpose         :  DownloadAllData class is used to download all data from server and save to local tables. 
Created Date    :  17/03/2015
Modified By     : Snehal Wadgaonkar 
Modified Date   :  06/05/2015
Modified Reason :  Added Raw Material, Vendor, Purchase download code.
Status          :  
 */
package bidding.example.com.bidding.Api_Call;

public class DownloadAllData {
/*
	public static Context myContext;
	public static JSONArray jArray;
	public static int AcountUpdateCall;
	public static DataBaseHelper db;
	public static int Flag=0;
	public static int vatAndExciseDuty=0;
	ModelAccountUpdate accountUpdateObject;
	List<ModelDealerPricelist> listDealerPrice;
	List<ModelDealerPaymentHistory> listDealerPaymentHistory;
	List<ModelDealerOrderMasterHistory> listDealerOrderMasterHistory;
	List<ModelDealerOrderDetailHistory> listDealerOrderDetailHistory;
	HashMap<String,List<ModelDealerOrderDetailHistory>> maporderDetails;
	List<ModelPendingPipes> listPendingpipes;
	String orderId;
	String customerID;
	String callActivity;
	public DownloadAllData(Context context) {
		myContext = context;
		db = new DataBaseHelper(myContext);
		//downloadAll();
	}

	public boolean downloadAll() {
		if (BU.isConnectingToInternet(myContext)) {
			// LogUtility.showLog("Inside downloadAll of download class");
			try {
				//listDealerPrice=new ArrayList<ModelDealerPricelist>();
				new DownloadTask().execute("All");
				
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}

	public boolean downloadRawMaterial() {
		if (BU.isConnectingToInternet(myContext)) 
		{
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_RAW_MATERIAL_REGISTRATION);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	
	public boolean downloadProductRegistration() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask()
						.execute(DataBaseConstants.Tablenames.TABLE_PRODUCT_REGISTRATION);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}

	public boolean downloadVendor() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask()
						.execute(DataBaseConstants.Tablenames.TABLE_VENDOR_REGISTRATION);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	
	public boolean downloadConfiguration() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_FREQUENT_PRICE_UPDATES);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	
	public boolean downloadPurchaseDetails() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask()
						.execute(DataBaseConstants.Tablenames.TABLE_PURCHASE);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadPurchaseItem() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(Tablenames.TABLE_PURCHASE_ITEM);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadDirectSale() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(Tablenames.TABLE_DIRECT_SALE);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadDirectSaleProduct() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(Tablenames.TABLE_DIRECT_SALE_PRODUCT);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	
	public boolean downloadVendorPayment() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask()
						.execute(DataBaseConstants.Tablenames.TABLE_VENDOR_PAYMENT);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadIncome() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_INCOME);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	
	public boolean downloadCustomerRegistration()
	{
		if (BU.isConnectingToInternet(myContext)) 
		{
			try
			{
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_CUSTOMER_REGISTRATION);
				return true;
			} catch (Exception e)
			{
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadExpense() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_EXPENSE);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadmanuFacture() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_MANUFACTURE_MASTER);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadmanufactureDetails() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_MANUFACTURE_DETAILS);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadAddPipesData() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_ADD_PIPES_ADMIN);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadAddpipesProduct() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute(DataBaseConstants.Tablenames.TABLE_ADD_PIPES_PRODUCT_ADMIN);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadAccountUpdate(int val) 
	{
		if (BU.isConnectingToInternet(myContext)) {
			try {
				AcountUpdateCall=val;
				new DownloadTask().execute("getAccountUpdates");
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadDealerPricelist() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute("getPricelist");
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadDealerPaymentHistory() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				new DownloadTask().execute("getPaymentDetails"+"/"+DBLoginAccess.getEmployeeID());
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadDealerOrderHistory(String callActivity,String customerID) {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				this.callActivity=callActivity;
				this.customerID=customerID;
				new DownloadTask().execute("getDealerOrderMaster"+"/"+customerID);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadDealerOrderDetailHistory(String callActivity,String orderId) {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				this.callActivity=callActivity;
				this.orderId=orderId;
				LTU.LE("ORDER ID IN DOWNLOAD",orderId);
				new DownloadTask().execute("getDealerOrderDetails"+"/"+orderId);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		
		return false;
	}
	public boolean downloadPendingPipes() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				
				LTU.LE("ORDER ID IN DOWNLOAD",orderId);
				new DownloadTask().execute("getPendingProductDetails");
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		
		return false;
	}
	public boolean downloadUser() {
		if (BU.isConnectingToInternet(myContext)) {
			try {
				
				new DownloadTask().execute("tbl_login");
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public boolean downloadShortCodes()
	{
		if (BU.isConnectingToInternet(myContext))
		{
			try 
			{
				
				new DownloadTask().execute("getShortcode"+"/"+DBLoginAccess.getEmployeeID());
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return false;
	}
	public class DownloadTask extends AsyncTask<String, Void, Void> {

		public ProgressDialog u_dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// LogUtility.showLog("Inside onPreExecute ");

			u_dialog = new ProgressDialog(myContext);
			u_dialog.setTitle("Download data");
			u_dialog.setMessage("Please Wait While Downloading Data From The Server...");
			u_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			u_dialog.setCanceledOnTouchOutside(false);
			u_dialog.show();
			// LogUtility.showLog("Inside u_dialog show");
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String downloadTable = new String(params[0]);

			if (downloadTable.equals("All")) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadUserNow();
				downloadDirectSaleNow();
				downloadDirectSaleProductNow();
				downloadProductRegistrationNow();
				downloadPurchaseNow();
				downloadRawMaterialRegistrationNow();
				downloadVendorNow();
				downloadVendorPaymentNow();
				
				downloadConfigurationNow();
				downloadPurchaseItemNow();
				downloadCustomerRegistrationNow();
				downloadExpenseNow();
				downloadIncomeNow();
				downloadManufactureDetailsNow();
				downloadManufactureNow();
				downloadShortCodesNow();
				downloadAddPipesNow();
				 downloadAddPipesProductNow();
				//downloadAccountUpdateNow();
			} else if (downloadTable.equals(DataBaseConstants.Tablenames.TABLE_PURCHASE)) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadPurchaseNow();
			} else if (downloadTable
					.equals(DataBaseConstants.Tablenames.TABLE_VENDOR_REGISTRATION)) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadVendorNow();
			} else if (downloadTable
					.equals(DataBaseConstants.Tablenames.TABLE_RAW_MATERIAL_REGISTRATION)) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadRawMaterialRegistrationNow();
			}else if (downloadTable
					.equals(DataBaseConstants.Tablenames.TABLE_VENDOR_PAYMENT)) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadVendorPaymentNow();
			}else if (downloadTable
					.equals(DataBaseConstants.Tablenames.TABLE_PRODUCT_REGISTRATION)) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadProductRegistrationNow();
			}else if (downloadTable
					.equals(DataBaseConstants.Tablenames.TABLE_FREQUENT_PRICE_UPDATES)) {
				Log.i("ECS", "DownloadTable :" + downloadTable);
				downloadConfigurationNow();
			}
			else if(downloadTable.equals(Tablenames.TABLE_PURCHASE_ITEM))
			
			{
				downloadPurchaseItemNow();
			}
			else if(downloadTable.equals(Tablenames.TABLE_DIRECT_SALE))
				
			{
				downloadDirectSaleNow();
			}
               else if(downloadTable.equals(Tablenames.TABLE_DIRECT_SALE_PRODUCT))
				
			{
				downloadDirectSaleProductNow();
			}
               else if(downloadTable.equals(Tablenames.TABLE_MANUFACTURE_DETAILS))
   				
   			{
   				downloadManufactureDetailsNow();
   			}
                 
               else if(downloadTable.equals(Tablenames.TABLE_MANUFACTURE_MASTER))
   				
   			{
   				downloadManufactureNow();
   			}
               else if(downloadTable.equals("getAccountUpdates"))
      				
      			{
      				downloadAccountUpdateNow();
      			}
               else if(downloadTable.equals("getPricelist"))
     				
     			{
     				downloadDealerPricelistNow();
     			}
               else if(downloadTable.equals("getPaymentDetails"+"/"+DBLoginAccess.getEmployeeID()))
    				
    			{
    				downloadDealerPaymentHistoryNow();
    			}
//               else if(downloadTable.equals("getAllOrderDetails"+"/"+DBLoginAccess.getEmployeeID()))
//   				
//   			{
//            	   //"getDealerOrderMaster"+"/"+DBLoginAccess.getEmployeeID())
//   				//downloadDealerOrderMsterHistoryNow();
//            	   downloadDealerOrderMasterAndDetailHistoryNow();
//   			}
               else if(downloadTable.equals("getDealerOrderMaster"+"/"+customerID))
      				
      			{
      				downloadDealerOrderMsterHistoryNow();
      			}
               else if(downloadTable.equals("getDealerOrderDetails"+"/"+orderId))
      				
      			{
      				downloadDealerOrderDetailHistoryNow();
      			}

               else if(downloadTable.equals("getPendingProductDetails"))
      				
      			{
      				downloadPendingPipesNow();
      			}
               else if(downloadTable.equals("tbl_login"))
     				
     			{
     				downloadUserNow();
     			}
                          
               else if(downloadTable.equals("getShortcode"+"/"+DBLoginAccess.getEmployeeID()))
    				
    			{
    				downloadShortCodesNow();
    			}
               else if (downloadTable.equals(Tablenames.TABLE_ADD_PIPES_ADMIN))
               {
            	   downloadAddPipesNow();
               }
               else if(downloadTable.equals(Tablenames.TABLE_ADD_PIPES_PRODUCT_ADMIN))
               {
            	   downloadAddPipesProductNow();
               }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			// TODO Auto-generated method stub

			if (u_dialog.isShowing()) 
			{
             u_dialog.dismiss();
				LTU.TES(myContext,MU.TAG,MU.SUCCESS_DOWNLOAD);
				if(accountUpdateObject!=null&&AcountUpdateCall==1)
				{
					AccountUpdateActivity activity=(AccountUpdateActivity)myContext;
					activity.getServiceData(accountUpdateObject);
				}
				if(accountUpdateObject!=null&&AcountUpdateCall==2)
				{
					VendorPaymentActivity activity=(VendorPaymentActivity)myContext;
					activity.getServiceData(accountUpdateObject);
				}
				if(accountUpdateObject!=null&&AcountUpdateCall==3)
				{
					VendorPaymentUpdateActivity activity=(VendorPaymentUpdateActivity)myContext;
					activity.getServiceData(accountUpdateObject);
				}
				if(listDealerPaymentHistory!=null)
				{
					DealerPaymentHistoryActivity activity=(DealerPaymentHistoryActivity)myContext;
					activity.setAdapter(listDealerPaymentHistory);
				}
				 if(listDealerPrice!=null)
				
				{
					DealerPricelistActivity activity=(DealerPricelistActivity)myContext;
					activity.setAdapter(listDealerPrice);
				}
//				 if(listDealerOrderMasterHistory!=null)
//				 {
//					 DealerOrderHistoryActivity activity=(DealerOrderHistoryActivity)myContext;
//					 activity.setAdapter(listDealerOrderMasterHistory,maporderDetails);
//				 }
				 if(listDealerOrderMasterHistory!=null&&callActivity.equals(ConstantsLogInAccess.DEALER_ORDER_HISTORY_ACTIVITY))
				 {
					 DealerOrderHistoryActivity activity=(DealerOrderHistoryActivity)myContext;
					 activity.setAdapter(listDealerOrderMasterHistory);
				 }
				 if(listDealerOrderMasterHistory!=null&&callActivity.equals(ConstantsLogInAccess.ORDER_LIST_ACTIVITY))
				 {
					 OrderListActivity activity=(OrderListActivity)myContext;
					 activity.setAdapter(listDealerOrderMasterHistory);
				 }
				 if(listDealerOrderDetailHistory!=null&&callActivity.equals(ConstantsLogInAccess.DEALER_ORDER_HISTORY_ACTIVITY))
				 {
					 LTU.LE("ORDER ID IN DOWNLOAD POST EXECUTE",orderId);
					 DealerOrderHistoryActivity activity=(DealerOrderHistoryActivity)myContext;
					 activity.showDialog(orderId, listDealerOrderDetailHistory);
				 }
				 if(listDealerOrderDetailHistory!=null&&callActivity.equals(ConstantsLogInAccess.ORDER_LIST_ACTIVITY))
				 {
					 OrderListActivity activity =(OrderListActivity)myContext;
					 activity.showDialog(orderId, listDealerOrderDetailHistory);
				 }
				 if(listPendingpipes!=null)
				 {
					 PipesActivity activity=(PipesActivity)myContext;
					 activity.setAdapter(listPendingpipes);
				 }
				if(Flag==1)
				{
					StockUpdateActivity activity=(StockUpdateActivity)myContext;
					
					activity.SetAdapterToListView();
					Flag=0;
				}
				
				// LogUtility.showLog("inside if u_dialog.dismiss() was called");
			}
			super.onPostExecute(result);
		}

	}
	
	public void downloadRawMaterialRegistrationNow() {
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_RAW_MATERIAL_REGISTRATION);
		ModelRawMaterialRegistrationDetails object;
		ArrayList<ModelRawMaterialRegistrationDetails> list = new ArrayList<ModelRawMaterialRegistrationDetails>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelRawMaterialRegistrationDetails();
					
						
						object.setItemName(json_data
								.getString(ConstantsRawMaterialRegistration.ITEM_NAME));
						object.setCategory(json_data
								.getString(ConstantsRawMaterialRegistration.CATEGORY));
						object.setItemDescription(json_data
								.getString(ConstantsRawMaterialRegistration.ITEM_DESCRIPTION));
						object.setItemNote(json_data
								.getString(ConstantsRawMaterialRegistration.ITEM_NOTE));
						object.setSubcategory(json_data
								.getString(ConstantsRawMaterialRegistration.SUBCATEGORY));
						object.setUnit(json_data
								.getString(ConstantsRawMaterialRegistration.UNIT));
						object.setWarningQty(json_data
								.getString(ConstantsRawMaterialRegistration.WARNING_QTY));
						object.setStock(json_data.getString(ConstantsRawMaterialRegistration.STOCK));
						object.setIsActive(json_data
								.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
					*/
/*	object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedBy(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedBy(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.setId(json_data.getString(ConstantsColumns.WEB_ID));
					*/
/*	object.setPhpID(json_data
								.getString(ConstantsColumns.PHP_ID));*//*


						list.add(object);
						LTU.LI("downloadProductsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadProductsNow ",
								"insertServerData try {");

						DBRawMaterialRegistration.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadPendingPipesNow() {
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadAccountUpdate("getPendingProductDetails");
		ModelPendingPipes object;
		listPendingpipes = new ArrayList<ModelPendingPipes>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelPendingPipes();
					object.setProduct(json_data.getString(ConstantsDealerOrderHistoryDetail.PRODUCT_ID));
						
					object.setQuantity(json_data.getString(ConstantsDealerOrderHistoryDetail.QUANTITY));	
								
						listPendingpipes.add(object);
						LTU.LI("downloadProductsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadProductsNow ",
								"insertServerData try {");

						//DBRawMaterialRegistration.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadManufactureDetailsNow() {
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_MANUFACTURE_DETAILS);
		ModelManufactureTempDetails object;
		ArrayList<ModelManufactureTempDetails> list = new ArrayList<ModelManufactureTempDetails>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelManufactureTempDetails();
					
						
						
						object.setBatch_no(json_data
								.getString(ConstantsManufactureDetail.BATCH_ID));
						object.setRaw_material_id(json_data
								.getString(ConstantsManufactureDetail.RAW_MATERIAL_ID));
						object.setRequirment(json_data
								.getString(ConstantsManufactureDetail.REQUIREMENT));
						object.setPer_material_requirement(json_data
								.getString(ConstantsManufactureDetail.PER_MATERIAL_REQUIREMENT));
						
						object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
					*/
/*	object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedby(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedby(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.set_id(json_data.getString(ConstantsColumns.WEB_ID));
					*/
/*	object.setPhpID(json_data
								.getString(ConstantsColumns.PHP_ID));*//*


						list.add(object);
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");

						DBManufactureDetails.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	
	public void downloadManufactureNow() {
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_MANUFACTURE_MASTER);
		ModelManufactureTempMaster object;
		ArrayList<ModelManufactureTempMaster> list = new ArrayList<ModelManufactureTempMaster>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelManufactureTempMaster();
					
						
						object.setProduct_id(json_data.getString(ConstantsManufacturing.PRODUCT_ID));
						object.setBatch_id(json_data.getString(ConstantsManufacturing.BATCH_ID));
						object.setTotal_weight(json_data.getString(ConstantsManufacturing.TOTAL_WEIGHT));
						object.setNo_of_pipes(json_data.getString(ConstantsManufacturing.NO_OF_PIPES));
						object.setNarration(json_data.getString(ConstantsManufacturing.NARRATION));
						object.setScrap(json_data.getString(ConstantsManufacturing.SCRAP));
						object.setIs_completed(json_data.getString(ConstantsManufacturing.IS_COMPLETED));
						object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
					*/
/*	object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedby(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedby(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.set_id(json_data.getString(ConstantsColumns.WEB_ID));
					*/
/*	object.setPhpID(json_data
								.getString(ConstantsColumns.PHP_ID));*//*


						list.add(object);
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");

						DBManufactureMaster.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadDealerPricelistNow() 
	{
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadAccountUpdate("getPricelist"+"/"+vatAndExciseDuty);
		ModelDealerPricelist object;
		listDealerPrice = new ArrayList<ModelDealerPricelist>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelDealerPricelist();
					   object.setSizeInMM(json_data.getString(ConstantsDealerPricelist.SIZE_IN_MM));
						object.setSizeInInch(json_data.getString(ConstantsDealerPricelist.SIZE_IN_INCH));
						object.setTwoAndHalfKg(json_data.getString(ConstantsDealerPricelist.TWO_AND_HALF_KG));
						object.setFourKg(json_data.getString(ConstantsDealerPricelist.FOUR_KG));
						object.setSixkg(json_data.getString(ConstantsDealerPricelist.SIX_KG));
						object.setEightKg(json_data.getString(ConstantsDealerPricelist.EIGHT_KG));
						object.setTenKg(json_data.getString(ConstantsDealerPricelist.TEN_KG));
						
					

						listDealerPrice.add(object);
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");

						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadDealerPaymentHistoryNow() 
	{
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadAccountUpdate("getPaymentDetails"+"/"+DBLoginAccess.getEmployeeID());
		LTU.LE("APPNDED URL", "getPaymentDetails"+"/"+DBLoginAccess.getEmployeeID());
		ModelDealerPaymentHistory object;
		listDealerPaymentHistory = new ArrayList<ModelDealerPaymentHistory>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelDealerPaymentHistory();
					   object.setDate(json_data.getString(ConstantsDealerPaymentHistory.DATE));
						object.setAmount(json_data.getString(ConstantsDealerPaymentHistory.AMOUNT));
						object.setStatus(json_data.getString(ConstantsDealerPaymentHistory.STATUS));
						
						
					

						listDealerPaymentHistory.add(object);
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");

						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadDealerOrderMsterHistoryNow() 
	{
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadAccountUpdate("getDealerOrderMaster"+"/"+customerID);
		ModelDealerOrderMasterHistory object;
		listDealerOrderMasterHistory = new ArrayList<ModelDealerOrderMasterHistory>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelDealerOrderMasterHistory();
					   object.setId(json_data.getString(ConstantsDealerOrderHistoryMaster.ID));
					   
						object.setDate(json_data.getString(ConstantsDealerOrderHistoryMaster.REQUIRED_DATE));
						object.setQuantity(json_data.getString(ConstantsDealerOrderHistoryMaster.TOTAL_QUANTITY));
						object.setAmount(json_data.getString(ConstantsDealerOrderHistoryMaster.TOTAL_AMOUNT));
						object.setRequiredDate(json_data.getString(ConstantsDealerOrderHistoryMaster.REQUIRED_DATE));
					    object.setDealer(json_data.getString(ConstantsDealerOrderHistoryMaster.CUSTOMER_ID));
					    LTU.LE("DOWNLOAD CUSTOMER ", json_data.getString(ConstantsDealerOrderHistoryMaster.CUSTOMER_ID));
					    object.setStatus(json_data.getString(ConstantsDealerOrderHistoryMaster.STATUS));
					

						listDealerOrderMasterHistory.add(object);
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");
                 DBOrderMaster.insertServerData(listDealerOrderMasterHistory);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadDealerOrderMasterAndDetailHistoryNow() 
	{
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadAccountUpdate("getAllOrderDetails"+"/"+DBLoginAccess.getEmployeeID());
		ModelDealerOrderMasterHistory object = null;
		String orderID="0";
		int currenti=0;
		ModelDealerOrderDetailHistory detailObject = null;
		maporderDetails=new HashMap<String, List<ModelDealerOrderDetailHistory>>();
		listDealerOrderMasterHistory = new ArrayList<ModelDealerOrderMasterHistory>();
		listDealerOrderDetailHistory=new ArrayList<ModelDealerOrderDetailHistory>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i+=currenti) 
	{
						JSONObject json_data = jArray.getJSONObject(i);
						if(!orderID.equals(json_data.getString(ConstantsDealerOrderHistoryMaster.ID)))
						{
						object = new ModelDealerOrderMasterHistory();
						orderID=json_data.getString(ConstantsDealerOrderHistoryMaster.ID);
						currenti=i;
					    object.setId(json_data.getString(ConstantsDealerOrderHistoryMaster.ID));
					    LTU.LE("ORDER ID",json_data.getString(ConstantsDealerOrderHistoryMaster.ID) );
					    
						object.setDate(json_data.getString(ConstantsDealerOrderHistoryMaster.REQUIRED_DATE));
						object.setQuantity(json_data.getString(ConstantsDealerOrderHistoryMaster.TOTAL_QUANTITY));
						object.setAmount(json_data.getString(ConstantsDealerOrderHistoryMaster.TOTAL_AMOUNT));
						object.setRequiredDate(json_data.getString(ConstantsDealerOrderHistoryMaster.REQUIRED_DATE));
						listDealerOrderMasterHistory.add(object);
						}
						for (int j = currenti; j < jArray.length(); j++) 
						{
							
						JSONObject json_data1 = jArray.getJSONObject(j);
						if(json_data1.getString(ConstantsDealerOrderHistoryMaster.ID).equals(orderID))
						{
						detailObject=new ModelDealerOrderDetailHistory();
						detailObject.setProduct(json_data1.getString(ConstantsDealerOrderHistoryDetail.PRODUCT_ID));
						LTU.LE("PRODUCT ID",json_data1.getString(ConstantsDealerOrderHistoryDetail.PRODUCT_ID));
						detailObject.setQty(json_data1.getString(ConstantsDealerOrderHistoryDetail.QUANTITY));
						detailObject.setPrice(json_data1.getString(ConstantsDealerOrderHistoryDetail.RATE));
						detailObject.setAmount(json_data1.getString(ConstantsDealerOrderHistoryDetail.AMOUNT));
						listDealerOrderDetailHistory.add(detailObject);
						}
					
						else
						{
							maporderDetails.put(orderID, listDealerOrderDetailHistory);
							break;
						}
						}
					    
                         
						
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");

						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadDealerOrderDetailHistoryNow() 
	{
		LTU.LI("downloadProductsNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadAccountUpdate("getDealerOrderDetails"+"/"+orderId);
		ModelDealerOrderDetailHistory object;
		listDealerOrderDetailHistory = new ArrayList<ModelDealerOrderDetailHistory>();
		try {
			LTU.LI("downloadProductsNow ", "inside Try");
			LTU.LI("downloadProductsNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadProductsNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelDealerOrderDetailHistory();
					    object.setProduct(json_data.getString(ConstantsDealerOrderHistoryDetail.PRODUCT_ID));
						object.setQty(json_data.getString(ConstantsDealerOrderHistoryDetail.QUANTITY));
						object.setPrice(json_data.getString(ConstantsDealerOrderHistoryDetail.RATE));
						object.setEditadQty(json_data.getString(ConstantsOrderDetails.EDITED_QTY));
						LTU.LE("EDITED QTY IN DOWNLOAD ", json_data.getString(ConstantsOrderDetails.EDITED_QTY));
						object.setAmount(json_data.getString(ConstantsDealerOrderHistoryDetail.AMOUNT));
						object.setPhpid(json_data.getString(ConstantsColumns.WEB_ID));
						
						
					
					

						listDealerOrderDetailHistory.add(object);
						LTU.LI("downloadManufactureDetailsNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadManufactureDetailsNow ",
								"insertServerData try {");
                   DBEditedOrderDetails.InsertServerData(listDealerOrderDetailHistory);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadCustomerRegistrationNow() {
		//Downloads product registration details from server 
		
		LTU.LI("downloadCustomerRegistrationNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_CUSTOMER_REGISTRATION);
		ModelCustomerRegistration object;
		ArrayList<ModelCustomerRegistration> list = new ArrayList<ModelCustomerRegistration>();
		try {
			LTU.LI("downloadCustomerRegistrationNow ", "inside Try");
			LTU.LI("downloadCustomerRegistrationNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadCustomerRegistrationNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelCustomerRegistration();
					
						object.setAddress(json_data
								.getString(ConstantsCustomerRegistration.ADDRESS));
						object.setCompanyName(json_data
								.getString(ConstantsCustomerRegistration.COMPANY_NAME));
						
						object.setContactPerson(json_data
								.getString(ConstantsCustomerRegistration.CONTACT_PERSON));
						object.setCustomerType(json_data
								.getString(ConstantsCustomerRegistration.CUSTOMER_TYPE));
						object.setEmailAddress(json_data
								.getString(ConstantsCustomerRegistration.EMAIL_ADDRESS));
						object.setLandlineNo(json_data
								.getString(ConstantsCustomerRegistration.LANDLINE));
						 
						object.setMobileNo(json_data
								.getString(ConstantsCustomerRegistration.MOBILE_NO));
						object.setAltmobileno(json_data
								.getString(ConstantsCustomerRegistration.ALT_MOBILE_NO));
						object.setIsActive(json_data
								.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
						*/
/*object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedBy(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedBy(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.setId(json_data
								.getString(ConstantsColumns.WEB_ID));
						*/
/*object.setPhpId(json_data
								.getString(ConstantsColumns.PHP_ID));*//*

						
						list.add(object);
						
					}
					try {
						LTU.LI("downloadProductsNow ",
								"insertServerData try {");

						DBCustomerRegistration.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside downloadCustomerRegistrationNow insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadCustomerRegistrationNow",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadCustomerRegistrationNow",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadCustomerRegistrationNow",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadCustomerRegistrationNow",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadExpenseNow() {
		//Downloads vendor payment details from server 
		
		LTU.LI("downloadExpenseNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_EXPENSE);
		ModelExpenseDetails object;
		ArrayList<ModelExpenseDetails> list = new ArrayList<ModelExpenseDetails>();
		try {
			LTU.LI("downloadExpenseNow ", "inside Try");
			LTU.LI("downloadExpenseNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadExpenseNow ","inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelExpenseDetails();
					
						object.setAmount(json_data
								.getString(ConstantsExpense.AMOUNT));
						object.setDate(json_data
								.getString(ConstantsExpense.DATE));
						
						object.setIs_approve(json_data.getString(ConstantsExpense.IS_APPROVED));
						object.setFrom(json_data.getString(ConstantsExpense.FROM_CITY));
						object.setTo(json_data.getString(ConstantsExpense.TO_CITY));
						object.setWorker_name(json_data.getString(ConstantsExpense.WORKER_NAME));
						object.setRaw_mat_bill_no(json_data.getString(ConstantsExpense.RAW_MATERIAL_BILL_NO));
						object.setPart_name(json_data.getString(ConstantsExpense.PART_NAME));
						object.setPart_qty(json_data.getString(ConstantsExpense.PART_QTY));
						object.setNarration(json_data.getString(ConstantsExpense.NARRATION));
						object.setMaintanance_bill_no(json_data.getString(ConstantsExpense.MAINTENANCE_BILL_NO));
						object.setMonth(json_data.getString(ConstantsExpense.BILL_MONTH));
						object.setYear(json_data.getString(ConstantsExpense.BILL_YEAR));
						object.setExpenseType(json_data
								.getString(ConstantsExpense.TYPE));
						object.setIsActive(json_data
								.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
						*/
/*object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedBy(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedBy(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.setId(json_data
								.getString(ConstantsColumns.WEB_ID));
						*/
/*object.setPhpId(json_data
								.getString(ConstantsColumns.PHP_ID));*//*

						
						list.add(object);
						LTU.LI("downloadExpenseNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadExpenseNow ",
								"insertServerData ");

						DBExpense.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadExpense",
								"Inside DBExpense insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadAllExpense",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadExpense",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadExpense",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadExpense",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadVendorNow() {
		LTU.LI("downloadVendorNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_VENDOR_REGISTRATION);
		ModelVender object;
		ArrayList<ModelVender> list = new ArrayList<ModelVender>();
		try {
			LTU.LI("downloadVendorNow ", "inside Try");
			LTU.LI("downloadVendorNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadVendorNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelVender();
					
						object.setAddress(json_data
								.getString(VendorRegistrationConstants.ADDREESS));
						object.setAccountNo(json_data
								.getString(VendorRegistrationConstants.BANK_ACC_NO));
						object.setBranch(json_data
								.getString(VendorRegistrationConstants.BANK_BRANCH));
						object.setBankName(json_data
								.getString(VendorRegistrationConstants.BANK_NAME));
						object.setContactPerson(json_data
								.getString(VendorRegistrationConstants.CONTACT_PERSON));
						object.setEmailId(json_data
								.getString(VendorRegistrationConstants.EMAIL_ID));
						object.setIfscCode(json_data
								.getString(VendorRegistrationConstants.IFSC_CODE));
						object.setAndroidApp(json_data
								.getString(VendorRegistrationConstants.IS_ANDROID));
						object.setMobileNo(json_data
								.getString(VendorRegistrationConstants.MOBILE));
						object.setFreqOfPayment(json_data
								.getString(VendorRegistrationConstants.PAYMENT_FREQUENCY));
						object.setPaymentMode(json_data
								.getString(VendorRegistrationConstants.PAYMENT_MODE));
						object.setPhone(json_data
								.getString(VendorRegistrationConstants.PHONE));
						//object.setVenderCode(json_data.getString(VendorRegistrationConstants.VENDOR_CODE));
						object.setVenderName(json_data.getString(VendorRegistrationConstants.VENDOR_NAME));
						object.setWhatsappNo(json_data
								.getString(VendorRegistrationConstants.WHATSAPP_NO));
					object.setStrAvailableMaterial(json_data.getString(ConstantsVendorRegistration.AVAILABLE_MATERIAL));
						object.setIsActive(json_data
								.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
						*/
/*object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedby(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedby(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.setId(json_data.getString(ConstantsColumns.WEB_ID));
						*/
/*object.setPhpId(json_data
								.getString(ConstantsColumns.PHP_ID));*//*

						
						list.add(object);
						LTU.LI("downloadVendorNowNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadVendorNowNow",
								"insertServerData try {");

						DBVendorRegistration.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadProducts",
								"Inside DBProductRegistraion insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadProducts",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadProducts",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadProducts",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}
	public void downloadIncomeNow() {
		//Downloads vendor payment details from server 
		
		LTU.LI("downloadIncomeNow ", "inside");
		// TODO Auto-generated method stub
		String result = downloadData(DataBaseConstants.Tablenames.TABLE_INCOME);
		ModelIncomeDetails object;
		ArrayList<ModelIncomeDetails> list = new ArrayList<ModelIncomeDetails>();
		try {
			LTU.LI("downloadIncomeNow ", "inside Try");
			LTU.LI("downloadIncomeNow ", "Result :" + result);

			if (result != null) {
				jArray = new JSONArray(result);
				if (jArray.length() > 0) {
					LTU.LI("downloadIncomeNow ",
							"inside jArray.length() > 0");

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						object = new ModelIncomeDetails();
					
						object.setAmount(json_data
								.getString(ConstantsIncome.AMOUNT));
						object.setDate(json_data
								.getString(ConstantsIncome.DATE));
						object.setNarration(json_data
								.getString(ConstantsIncome.NARRATION));
						object.setBankName(json_data
								.getString(ConstantsIncome.BANK_NAME));
						object.setContactPerson(json_data
								.getString(ConstantsIncome.CONTACT_PERSON));
						object.setCustomer(json_data
								.getString(ConstantsIncome.CUSTOMER_ID));
						object.setPaymentMode(json_data
								.getString(ConstantsIncome.PAYMENT_MODE));
						object.setMobileNo(json_data.getString(ConstantsIncome.CONTACT_NO));
						object.setIs_cleared(json_data.getString(ConstantsIncome.IS_CLEARED));
						object.setTransChequeNo(json_data
								.getString(ConstantsIncome.TRANSACTION_NO));
						//object.setIs_approved(json_data.getString(ConstantsIncome.IS_APPROVED));
						object.setIsActive(json_data
								.getString(ConstantsColumns.IS_ACTIVE));
						object.setIsDeleted(json_data
								.getString(ConstantsColumns.IS_DELETED));
						*/
/*object.setAppId(json_data
								.getString(ConstantsColumns.APP_ID));*//*

						object.setCreatedBy(json_data
								.getString(ConstantsColumns.CREATED_BY));
						object.setCreatedTime(json_data
								.getString(ConstantsColumns.CREATED_TIME));
						object.setUpdatedBy(json_data
								.getString(ConstantsColumns.UPDATED_BY));
						object.setUpdatedTime(json_data
								.getString(ConstantsColumns.UPDATED_TIME));
						
						object.setId(json_data
								.getString(ConstantsColumns.WEB_ID));
						*/
/*object.setPhpId(json_data
								.getString(ConstantsColumns.PHP_ID));*//*

						
						list.add(object);
						LTU.LI("downloadIncomeNow ",
								"object is add");
					}
					try {
						LTU.LI("downloadIncomeNow ",
								"insertServerData ");

						DBIncome.insertServerData(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LTU.LI("DownloadAllData downloadIncomeNow",
								"Inside DBIncome.insertServerData Exception Block :"
										+ e.toString());
					}
				} else {
					LTU.TES(myContext,
							"DownloadAllData downloadIncomeNow",
							MU.DATA_NO_FOUND);
				}
			} else {
				LTU.TES(myContext,"DownloadAllData downloadIncomeNow",
						"No data found from web!!!");
				LTU.LI("DownloadAllData downloadProducts",
						"Result else   :");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LTU.LI("DownloadAllData downloadIncomeNow",
					"Inside JSONException Block :" + e1.toString());

			e1.printStackTrace();
		}
	}


public void downloadPurchaseNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(DataBaseConstants.Tablenames.TABLE_PURCHASE);
	ModelPurchaseDetails object;
	ArrayList<ModelPurchaseDetails> list = new ArrayList<ModelPurchaseDetails>();
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelPurchaseDetails();
				
					object.setDate((json_data.getString(PurchaseConstants.DATE)));
				    object.setRefNo(json_data.getString(PurchaseConstants.PO_REF_NO));
		
					object.setTaxes(json_data.getString(PurchaseConstants.TAX));
					object.setTotal(json_data.getString(PurchaseConstants.TOTAL));
					object.setVendor(json_data.getString(PurchaseConstants.VENDOR_ID));
					object.setBillAmount(json_data.getString(PurchaseConstants.BILL_AMOUNT));
					object.setPaymentMode(json_data.getString(PurchaseConstants.PAYMENT_MODE));
					object.setPaymentTransactionNo(json_data.getString(PurchaseConstants.PAYMENT_TRANSACTION_NO));
					object.setDelivery_status(json_data.getString(PurchaseConstants.DELIVERY_STATUS));
					
					object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data.getString(ConstantsColumns.IS_DELETED));
					*/
/*
					object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					object.setCreatedBy(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedBy(json_data
							.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data
							.getString(ConstantsColumns.UPDATED_TIME));
					*/
/*object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					object.setId(json_data
							.getString(ConstantsColumns.WEB_ID));

					list.add(object);
					LTU.LI("downloadPurchaseNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadPurchaseNow ",
							"insertServerData try {");

					DBPurchase.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadPurchase",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadPurchase",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadPurchase",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadPurchase",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadPurchase",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}

public void downloadPurchaseItemNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(Tablenames.TABLE_PURCHASE_ITEM);
	ModelPurchaseItem object;
	ArrayList<ModelPurchaseItem> list = new ArrayList<ModelPurchaseItem>();
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelPurchaseItem();
				   
					object.setPurchase_id(json_data.getString(PurchaseItemConstants.PURCHASE_ID));
					object.setItemId(json_data.getString(PurchaseItemConstants.ITEM_ID));
					object.setQty(json_data.getString(PurchaseItemConstants.ITEM_QTY));
					object.setRate(json_data.getString(PurchaseItemConstants.ITEM_RATE));
					object.setAmount(json_data.getString(PurchaseItemConstants.AMOUNT));
			        object.setCreatedby(json_data.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data.getString(ConstantsColumns.CREATED_TIME));
					object.setCreatedby(json_data.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data.getString(ConstantsColumns.UPDATED_TIME));
					object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data.getString(ConstantsColumns.IS_DELETED));
					object.setPhpId(json_data.getString(ConstantsColumns.WEB_ID));
					
					
					

					list.add(object);
					LTU.LI("downloadPurchaseNow ",
							"object is add");
				}
				try 
				{
					LTU.LI("downloadPurchaseItemNow ",
							"insertServerData try {");

					DBPurchaseItem.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadPurchase",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadPurchase",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadPurchase",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadPurchase",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadPurchase",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}

public void downloadAccountUpdateNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadAccountUpdate("getAccountUpdates");
	
	
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				
					JSONObject json_data = jArray.getJSONObject(0);
			         accountUpdateObject = new ModelAccountUpdate();
				   
			       accountUpdateObject.setAccount_amount(json_data.getString(ConstantsAccountUpdate.ACCOUNT_AMOUNT));
					LTU.LE("DOWNLOAD", json_data.getString(ConstantsAccountUpdate.ACCOUNT_AMOUNT));
					accountUpdateObject.setPending_deebit_cheque(json_data.getString(ConstantsAccountUpdate.PENDING_DEBIT_CHEQUE));
					LTU.LE("DOWNLOAD", json_data.getString(ConstantsAccountUpdate.PENDING_DEBIT_CHEQUE));
					accountUpdateObject.setPending_credit_cheaque(json_data.getString(ConstantsAccountUpdate.PENDING_CREDIT_CHEQUE));
					accountUpdateObject.setAvailable_balance(json_data.getString(ConstantsAccountUpdate.AVAILABLE_BALANCE));
					
					
                    
					
					LTU.LI("downloadPurchaseNow ",
							"object is add");
				
				try 
				{
					LTU.LI("downloadPurchaseItemNow ",
							"insertServerData try {");

					//DBPurchaseItem.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadPurchase",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadPurchase",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadPurchase",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadPurchase",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadPurchase",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}

public void downloadDirectSaleNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(Tablenames.TABLE_DIRECT_SALE);
	ModelDirectSaleDetail object;
	ArrayList<ModelDirectSaleDetail> list = new ArrayList<ModelDirectSaleDetail>();
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelDirectSaleDetail();

					object.setPhpID(json_data.getString(ConstantsColumns.WEB_ID));
					object.setDate(json_data.getString(ConstantsDircetSale.DATE));
					object.setCustomerName(json_data.getString(ConstantsDircetSale.CUSTOMER_NAME));
					object.setContactNo(json_data.getString(ConstantsDircetSale.CONTACT_NO));
					object.setAddress(json_data.getString(ConstantsDircetSale.ADDRESS));
					object.setTotal(json_data.getString(ConstantsDircetSale.TOTAL));
					object.setVat(json_data.getString(ConstantsDircetSale.VAT));
					object.setTaxes(json_data.getString(ConstantsDircetSale.TAX));
					object.setDiscount(json_data.getString(ConstantsDircetSale.DISCOUNT));
					object.setBillAmount(json_data.getString(ConstantsDircetSale.BILL_AMOUNT));
					object.setPaymentMode(json_data.getString(ConstantsDircetSale.PAYMENT_MODE));
					object.setBill_no(json_data.getString(ConstantsDircetSale.BILL_NO));
					object.setSaleType(json_data.getString(ConstantsDircetSale.SALE_TYPE));
					object.setPaymentTransactionNo(json_data.getString(ConstantsDircetSale.PAYMENT_TRANSACTION_NO));
					object.setCreatedBy(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedBy(json_data
							.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data
							.getString(ConstantsColumns.UPDATED_TIME));
					object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data.getString(ConstantsColumns.IS_DELETED));
					*/
/*
					object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					
					*/
/*object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					

					list.add(object);
					LTU.LI("downloadDirctSaleNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadDirctSaleNow",
							"insertServerData try {");

					DBDirctSale.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadDirctSale",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadDirctSale",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadDirctSale",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadDirctSale",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadDirctSale",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}
public void downloadAddPipesNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(Tablenames.TABLE_ADD_PIPES_ADMIN);
	ModelAddPipe object;
	ArrayList<ModelAddPipe> list = new ArrayList<ModelAddPipe>();
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelAddPipe();

					object.setPhpID(json_data.getString(ConstantsColumns.WEB_ID));
					object.setDate(json_data.getString(ConstantsAddPipeAdmin.DATE));
					object.setCustomerName(json_data.getString(ConstantsAddPipeAdmin.CUSTOMER_NAME));
					object.setNarration(json_data.getString(ConstantsAddPipeAdmin.NARRATION));
					object.setBill_no(json_data.getString(ConstantsAddPipeAdmin.BILL_NO));
					object.setTotal(json_data.getString(ConstantsAddPipeAdmin.TOTAL));
				
					object.setCreatedBy(json_data.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedBy(json_data.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data.getString(ConstantsColumns.UPDATED_TIME));
					object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data.getString(ConstantsColumns.IS_DELETED));
					*/
/*
					object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					
					*/
/*object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					

					list.add(object);
					LTU.LI("downloadDirctSaleNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadDirctSaleNow",
							"insertServerData try {");

					DBAddPipe.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadDirctSale",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadDirctSale",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadDirctSale",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadDirctSale",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadDirctSale",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}
public void downloadDirectSaleProductNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(DataBaseConstants.Tablenames.TABLE_DIRECT_SALE_PRODUCT);
	ModelDirectSaleItem object;
	ArrayList<ModelDirectSaleItem> list = new ArrayList<ModelDirectSaleItem>();
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelDirectSaleItem();

					object.setDirect_sale_id(json_data.getString(ConstantsDirectSaleProduct.DIRECT_SALE_ID));
					object.setProduct_id(json_data.getString(ConstantsDirectSaleProduct.PRODUCT_ID));
					object.setIsISO(json_data.getString(ConstantsDirectSaleProduct.IS_ISI));
					object.setQty(json_data.getString(ConstantsDirectSaleProduct.PRODUCT_QTY));
					object.setRate(json_data.getString(ConstantsDirectSaleProduct.RATE));
					object.setTotal(json_data.getString(ConstantsDirectSaleProduct.AMOUNT));
					
					object.setPhpID(json_data.getString(ConstantsColumns.WEB_ID));

					object.setIsActive(json_data.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data.getString(ConstantsColumns.IS_DELETED));
					*/
/*
					object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					object.setCreatedby(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedby(json_data
							.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data
							.getString(ConstantsColumns.UPDATED_TIME));
					*/
/*object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					
					list.add(object);
					LTU.LI("downloadPurchaseNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadPurchaseNow ",
							"insertServerData try {");

					DBDirectSaleProduct.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadPurchase",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadPurchase",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadPurchase",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadPurchase",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadPurchase",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}
public void downloadAddPipesProductNow() {
	LTU.LI("downloadPurchaseNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(DataBaseConstants.Tablenames.TABLE_ADD_PIPES_PRODUCT_ADMIN);
	ModelAddPipeProduct object;
	ArrayList<ModelAddPipeProduct> list = new ArrayList<ModelAddPipeProduct>();
	try {
		LTU.LI("downloadPurchaseNow ", "inside Try");
		LTU.LI("downloadPurchaseNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadPurchaseNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelAddPipeProduct();

					object.setAdd_pipe_id(json_data.getString(ConstantsAddPipeProductAdmin.ADD_PIPE_ID));
					object.setProduct_id(json_data.getString(ConstantsAddPipeProductAdmin.PRODUCT_ID));
					object.setIs_isi(json_data.getString(ConstantsAddPipeProductAdmin.IS_ISI));
					object.setProduct_qty(json_data.getString(ConstantsAddPipeProductAdmin.PRODUCT_QTY));
					object.setRate(json_data.getString(ConstantsAddPipeProductAdmin.RATE));
					object.setAmount(json_data.getString(ConstantsAddPipeProductAdmin.AMOUNT));
					
					object.setPhpID(json_data.getString(ConstantsColumns.WEB_ID));

					object.setIs_active(json_data.getString(ConstantsColumns.IS_ACTIVE));
					object.setIs_deleted(json_data.getString(ConstantsColumns.IS_DELETED));
					*/
/*
					object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					object.setCreated_by(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreated_time(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdated_by(json_data
							.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdated_time(json_data.getString(ConstantsColumns.UPDATED_TIME));
					*/
/*object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					
					list.add(object);
					LTU.LI("downloadPurchaseNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadPurchaseNow ",
							"insertServerData try {");

					DBAddPipeProduct.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadPurchase",
							"Inside DBPurchase insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadPurchase",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadPurchase",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadPurchase",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadPurchase",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}

public void downloadVendorPaymentNow() {
	//Downloads vendor payment details from server 
	
	LTU.LI("downloadVendorNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(DataBaseConstants.Tablenames.TABLE_VENDOR_PAYMENT);
	ModelVendorPaymentDetails object;
	ArrayList<ModelVendorPaymentDetails> list = new ArrayList<ModelVendorPaymentDetails>();
	try {
		LTU.LI("downloadVendorNow ", "inside Try");
		LTU.LI("downloadVendorNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadVendorNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelVendorPaymentDetails();
				
					object.setPaymentAmount(json_data
							.getString(ConstantsVendorPayment.AMOUNT));
					object.setChequeOrTransNO(json_data
							.getString(ConstantsVendorPayment.CHEQUE_OR_TRANS_NO));
					object.setPaymentDate(json_data
							.getString(ConstantsVendorPayment.DATE));
					object.setDescription(json_data
							.getString(ConstantsVendorPayment.DESCRIPTION));
					object.setModeOfPayment(json_data
							.getString(ConstantsVendorPayment.PAYMENT_MODE));
					object.setVendorName(json_data
							.getString(ConstantsVendorPayment.VENDOR_NAME));
					object.setIsActive(json_data
							.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data
							.getString(ConstantsColumns.IS_DELETED));
					object.setIs_cleared(json_data.getString(ConstantsVendorPayment.IS_CLEARED));
					*/
/*object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					object.setIs_approve(json_data.getString(ConstantsVendorPayment.IS_APPROVED));
					object.setCreatedBy(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedBy(json_data
							.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data
							.getString(ConstantsColumns.UPDATED_TIME));
					
					object.setId(json_data
							.getString(ConstantsColumns.WEB_ID));
					*/
/*object.setPhpId(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					
					list.add(object);
					LTU.LI("downloadProductsNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadProductsNow ",
							"insertServerData try {");

					DBVendorPayment.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadProducts",
							"Inside DBProductRegistraion insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadProducts",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadProducts",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadProducts",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadProducts",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}
public void downloadUserNow() {
	LTU.LI("downloadProductsNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData("tbl_login");
	ModelUser object;
	ArrayList<ModelUser> list = new ArrayList<ModelUser>();
	try {
		LTU.LI("downloadProductsNow ", "inside Try");
		LTU.LI("downloadProductsNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadProductsNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelUser();
				
					
					object.setUserName(json_data.getString(ConstantsAddUser.USER_NAME));
					object.setAddress(json_data.getString(ConstantsAddUser.ADDRESS));
					object.setContactno(json_data.getString(ConstantsAddUser.CONTACT_NO));
					object.setEmail(json_data.getString(ConstantsAddUser.EMAIL));
					object.setPassword(json_data.getString(ConstantsAddUser.PASSWORD));
					object.setDealerId(json_data.getString(ConstantsAddUser.DEALER_ID));
					object.setRole(json_data
							.getString(ConstantsAddUser.ROLE));
					
					object.setIsActive(json_data
							.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data
							.getString(ConstantsColumns.IS_DELETED));
				*/
/*	object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					object.setCreatedby(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedby(json_data.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data
							.getString(ConstantsColumns.UPDATED_TIME));
					
					object.setPhpid(json_data.getString(ConstantsColumns.WEB_ID));
				*/
/*	object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*


					list.add(object);
					LTU.LI("downloadProductsNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadProductsNow ",
							"insertServerData try {");

					DBUser.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadProducts",
							"Inside DBProductRegistraion insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadProducts",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadProducts",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadProducts",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadProducts",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}
public void downloadShortCodesNow() {
	LTU.LI("downloadProductsNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadAccountUpdate("getShortcode"+"/"+DBLoginAccess.getEmployeeID());
	ModelShortcode object;
	ArrayList<ModelShortcode> list = new ArrayList<ModelShortcode>();
	try {
		LTU.LI("downloadProductsNow ", "inside Try");
		LTU.LI("downloadProductsNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadProductsNow ",
						"inside jArray.length() > 0");
                 DBNotification.deleteShortcodes();
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelShortcode();
				
					
					//object.setId(json_data.getString(ConstantsShortCode.ID));
					object.setFullName(json_data.getString(ConstantsShortCode.SHORTCODE_FULLNAME));
					//object.setServerId(json_data.getString(ConstantsShortCode.SHORTCODE_SERVER_ID));
					object.setShortName(json_data.getString(ConstantsShortCode.ACTUAL_SHORTCODE));
					
				*/
/*	object.setPhpID(json_data
							.getString(ConstantsColumns.PHP_ID));*//*


					//.add(object);
					DBNotification.insertShortcodes(object);
					LTU.LI("downloadProductsNow ",
							"object is add");
				}
				try {
					LTU.LI("downloadProductsNow ",
							"insertServerData try {");

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadProducts",
							"Inside DBProductRegistraion insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadProducts",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadProducts",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadProducts",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadProducts",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}

//Added 15.05.2015
public void downloadProductRegistrationNow() {
	//Downloads product registration details from server 
	
	LTU.LI("downloadProductRegistrationNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(DataBaseConstants.Tablenames.TABLE_PRODUCT_REGISTRATION);
	ModelProductRegisrationDetails object;
	ArrayList<ModelProductRegisrationDetails> list = new ArrayList<ModelProductRegisrationDetails>();
	try {
		LTU.LI("downloadProductRegistrationNow ", "inside Try");
		LTU.LI("downloadProductRegistrationNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadProductRegistrationNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelProductRegisrationDetails();
				
					object.setIsi(json_data
							.getString(ConstantsProductRegistration.ISI));
					object.setFinalprice(json_data
							.getString(ConstantsProductRegistration.FINAL_PRICE));
					object.setDealerPrice(json_data.getString(ConstantsProductRegistration.DEALER_PRICE));
					object.setPipeWeight(json_data
							.getString(ConstantsProductRegistration.PIPE_WEIGHT));
					object.setPdtName(json_data
							.getString(ConstantsProductRegistration.PRODUCT_NAME));
					object.setSizeInches(json_data
							.getString(ConstantsProductRegistration.SIZE_INCHES));
					object.setSizeMM(json_data
							.getString(ConstantsProductRegistration.SIZE_MM));
					object.setThickness(json_data
							.getString(ConstantsProductRegistration.THICKNESS));
					object.setWorkingPressure(json_data
							.getString(ConstantsProductRegistration.WORKING_PRESSURE));
					object.setSalesRate(json_data.getString(ConstantsProductRegistration.SALES_RATE));
					object.setStock(json_data.getString(ConstantsProductRegistration.STOCK));
					object.setIsActive(json_data
							.getString(ConstantsColumns.IS_ACTIVE));
					object.setIsDeleted(json_data
							.getString(ConstantsColumns.IS_DELETED));
					*/
/*object.setAppId(json_data
							.getString(ConstantsColumns.APP_ID));*//*

					object.setCreatedBy(json_data
							.getString(ConstantsColumns.CREATED_BY));
					object.setCreatedTime(json_data
							.getString(ConstantsColumns.CREATED_TIME));
					object.setUpdatedBy(json_data
							.getString(ConstantsColumns.UPDATED_BY));
					object.setUpdatedTime(json_data
							.getString(ConstantsColumns.UPDATED_TIME));
					
					object.setId(json_data.getString(ConstantsColumns.WEB_ID));
					*/
/*object.setPhpId(json_data
							.getString(ConstantsColumns.PHP_ID));*//*

					
					list.add(object);
					LTU.LI("downloadProductsNow ",
							"object is add Sales Rate :"+json_data
							.getString(ConstantsProductRegistration.SALES_RATE));
				}
				try {
					LTU.LI("downloadProductsNow ",
							"insertServerData try {");

					DBProductRegistration.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadProducts",
							"Inside DBProductRegistraion insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadProducts",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadProducts",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadProducts",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadProducts",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}

//Added 15.05.2015
public void downloadConfigurationNow()
{
	//Downloads product registration details from server 
	
	LTU.LI("downloadConfigurationNow ", "inside");
	// TODO Auto-generated method stub
	String result = downloadData(DataBaseConstants.Tablenames.TABLE_FREQUENT_PRICE_UPDATES);
	ModelPriceCalculation object;
	ArrayList<ModelPriceCalculation> list = new ArrayList<ModelPriceCalculation>();
	try {
		LTU.LI("downloadConfigurationNow ", "inside Try");
		LTU.LI("downloadConfigurationNow ", "Result :" + result);

		if (result != null) {
			jArray = new JSONArray(result);
			if (jArray.length() > 0) {
				LTU.LI("downloadProductRegistrationNow ",
						"inside jArray.length() > 0");

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					object = new ModelPriceCalculation();
				
					object.setDate(json_data
							.getString(ConstantsPriceCalculation.DATE));
					object.setLength(json_data
							.getString(ConstantsPriceCalculation.LENGTH));
					object.setProduction(json_data
							.getString(ConstantsPriceCalculation.PRODUCTION));
					object.setRexin(json_data
							.getString(ConstantsPriceCalculation.REXIN));
					object.setTransport(json_data
							.getString(ConstantsPriceCalculation.TRANSPORT));
				
					
					list.add(object);
					
				}
				try {
					LTU.LI("downloadConfigurationNow ",
							"insertServerData try {");

					DBConfiguration.insertServerData(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					LTU.LI("DownloadAllData downloadConfigurationNow",
							"Inside DBConfiguration insertServerData Exception Block :"
									+ e.toString());
				}
			} else {
				LTU.TES(myContext,
						"DownloadAllData downloadConfiguration",
						MU.DATA_NO_FOUND);
			}
		} else {
			LTU.TES(myContext,"DownloadAllData downloadConfiguration",
					"No data found from web!!!");
			LTU.LI("DownloadAllData downloadConfiguration",
					"Result else   :");
		}
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		LTU.LI("DownloadAllData downloadConfiguration",
				"Inside JSONException Block :" + e1.toString());

		e1.printStackTrace();
	}
}


private String downloadData(String tableName) {
	// TODO Auto-generated method stub
	StringBuffer url = new StringBuffer(AppWebService.getMainURL());
	url.append("viewData" + "/" + tableName);
	String downloadLink = new String(url);
	return AppWebService.getJSONString(downloadLink);
}
private String downloadAccountUpdate(String tableName) {
	// TODO Auto-generated method stub
	StringBuffer url = new StringBuffer(AppWebService.getMainURL());
	url.append(tableName);
	String downloadLink = new String(url);
	return AppWebService.getJSONString(downloadLink);
}

*/

}
	

