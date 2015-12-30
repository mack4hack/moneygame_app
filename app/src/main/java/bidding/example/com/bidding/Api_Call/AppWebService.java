/*
Class Name      :  .java 
Developed By    :  Raghwendra Suryawanshi
Purpose         :   class is used to . 
Created Date    :  09/03/2015
Modified By     : 
Modified Date   :  
Modified Reason :  
Status          :  
 */
package bidding.example.com.bidding.Api_Call;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AppWebService 

{
	private InputStream is;
	private StringBuilder sb;
	private String result="-1";
	public Utility utility;
	public static boolean DemoLink = false;// Change during test and development

	private static final String MainURL = new String("http://www.");

	private static final String ProdURL = new String("http://www.pixmadness.in/Letsplay/login");
	
	private static final String DEvURL = new String("http://www.ecs-tech.in/chaitanyadairy/t/pages/service/index.php?/UploadDownload_laxmipipes_dev/");

	public AppWebService(Context context) {
		super();
		utility = new Utility(context);
	}

	public String uplode(JSONArray array, String method)
	{
		// TODO Auto-generated method stub
		// satish sir send
		Utility.showLog(Utility.MSGUtility.TAG, "in side uplode");
		try {
			Utility.LogUtility.showLog(ProdURL);
			URL call_url=new URL(ProdURL);
			HttpURLConnection urlConnection= (HttpURLConnection) call_url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			urlConnection.setReadTimeout(80000);
			urlConnection.connect();
			OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			Log.i("pass val",""+array.toString());
			writer.write(array.toString());
			writer.close();
			out.close();
			if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
			{
				InputStreamReader in=new InputStreamReader(urlConnection.getInputStream());
				BufferedReader bufferedReader=new BufferedReader(in);


				result=bufferedReader.readLine();
			}
			/*HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 80000);
			HttpConnectionParams.setSoTimeout(httpParams, 80000);

			HttpClient client = new DefaultHttpClient(httpParams);
			Utility.showLog(Utility.MSGUtility.TAG,
					"" + method + "::" + array.toString());
			Utility.LogUtility.showLog("AppWebService ", " uplode " + getMainURL()
					+ "UploadData/" + method + "?");
			// ......................................................................................
			HttpPost request = new HttpPost(getMainURL() + "UploadData/"
					+ method + "?");
			request.setEntity(new ByteArrayEntity(array.toString().getBytes(
					"UTF8")));
			request.setHeader("json", array.toString());

			HttpResponse response = client.execute(request);

			// Response from server
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need

			if (entity != null) {
				is = entity.getContent();
				Utility.showLog(Utility.MSGUtility.TAG, "Read from server :" + result);
			}*/
			// ......................................................................................
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Utility.showLog(Utility.MSGUtility.TAG, "i am here::" + result);
		} catch (Exception e) {
			Utility.showLog(Utility.MSGUtility.TAG,
					"Error converting result " + e.toString());
			return "fail";
		}*/
		return result;
	}

	public static String getJSONString(String url) {
		String result = null;
		String URL;
		URL = new String(url);
		InputStream inputStream;
		try {
			HttpParams httpParams = new BasicHttpParams();
			ConnManagerParams.setTimeout(httpParams, 80000);
			HttpConnectionParams.setConnectionTimeout(httpParams, 80000);
			HttpConnectionParams.setSoTimeout(httpParams, 80000);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpPost httppost = new HttpPost(URL);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "iso-8859-1"), 8);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			result = stringBuilder.toString();
			// utility.showLog("Result : ", result);
			Log.i("Result : ", result);
		} catch (Exception e) {
			// error_flag=1;
			Log.e("log_tag", "Error converting result " + e.toString());
		}
		return result;
	}

	public static String getMainURL() {
		if (DemoLink) {
			return DEvURL;
		} else {
			return ProdURL;
		}
	}

}
