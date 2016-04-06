package bidding.example.com.bidding;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bidding.example.com.bidding.APICALL.ApiCall;


public class Profile extends Fragment {

    private EditText mPassword;
    private TextView mName,mUserCode,mContact,mEmail;
    private ImageView mImageView;
    private Button btnEditSave;
    ProgressDialog pDialog;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pDialog = new ProgressDialog(getActivity());
        mPassword = (EditText) view.findViewById(R.id.editPassword);
        mPassword.setEnabled(false);
        mName = (TextView) view.findViewById(R.id.txtName);
        mUserCode = (TextView) view.findViewById(R.id.UserCode);
        mContact = (TextView) view.findViewById(R.id.contact);
        mEmail = (TextView) view.findViewById(R.id.Email);
        btnEditSave = (Button) view.findViewById(R.id.btnEdit);

      //get user profile
        new getProfile().execute(getString(R.string.get_profile)+getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getString("player_id",""));

       btnEditSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
               dialog.setTitle("Confirmation");
               dialog.setCancelable(true);
               // ...Irrelevant code for customizing the buttons and title
               LayoutInflater inflater = getActivity().getLayoutInflater();
               View dialogView = inflater.inflate(R.layout.change_password_layout, null);
               dialog.setView(dialogView);

               final EditText mOldPassword = (EditText) dialogView.findViewById(R.id.oldPassword);
               final EditText mNewPassword = (EditText) dialogView.findViewById(R.id.newPassword);
               final EditText mConfPassword = (EditText) dialogView.findViewById(R.id.confirmPassword);



               dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(final DialogInterface dialogInterface, int i) {
                       if(TextUtils.isEmpty(mOldPassword.getText().toString().trim()))
                       {
                           Toast.makeText(getActivity(),"please enter old password",Toast.LENGTH_SHORT).show();
                       }
                       else if(TextUtils.isEmpty(mNewPassword.getText().toString().trim()))
                       {
                           Toast.makeText(getActivity(),"please enter new password!!!",Toast.LENGTH_SHORT).show();
                       }
                       else if(TextUtils.isEmpty(mConfPassword.getText().toString().trim()))
                       {
                           Toast.makeText(getActivity(),"please re-enter password!!!",Toast.LENGTH_SHORT).show();
                       }
                       else if(!getActivity().getSharedPreferences(getString(R.string.prefrence),Context.MODE_PRIVATE).getString("player_password","").equals(mOldPassword.getText().toString().trim()))
                       {
                           Toast.makeText(getActivity(),"old password not match!!!",Toast.LENGTH_SHORT).show();
                       }
                       else if(!mNewPassword.getText().toString().trim().equals(mConfPassword.getText().toString().trim()))
                       {
                           Toast.makeText(getActivity(),"password not matched!!!",Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           //Update password in prefrences
                           //Toast.makeText(getActivity(),"Password Changed Local",Toast.LENGTH_SHORT).show();
                           String tag_json_obj = "json_obj_req";
                           final String TAG = "response";
                           final String url = getString(R.string.password_chng);//+ URLEncoder.encode("/"+postString);

                           final ProgressDialog pDialog = new ProgressDialog(getActivity());
                           pDialog.setMessage("Loading...");
                           pDialog.show();

                           StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                                   url, new Response.Listener<String>() {

                               @Override
                               public void onResponse(String response) {
                                   Log.d(TAG, response.toString());
                                   pDialog.hide();
                                   try {
                                       JSONObject object = new JSONObject(response);
                                       if (object.getString("status").equals("true")) {
                                           dialogInterface.cancel();
                                           Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                       }
                                       else {
                                           Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                       }
                                   } catch (Exception e) {
                                       Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                       e.printStackTrace();
                                   }
                               }
                           }, new Response.ErrorListener() {

                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   Toast.makeText(getActivity(), "Something went wrong please try again!!!", Toast.LENGTH_SHORT).show();
                                   error.printStackTrace();
                                   VolleyLog.d(TAG, "Error: " + error.getMessage());
                                   pDialog.hide();
                               }
                           }) {
                               @Override
                               protected Map<String, String> getParams() throws AuthFailureError {

                                   HashMap<String, String> map = new HashMap<>();

                                   map.put("player_id", getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_id", ""));
                                   map.put("password", mConfPassword.getText().toString().trim());

                                   for (String key : map.keySet()) {
                                       Log.i("values", "key = " + key + " val = " + map.get(key));
                                   }
                                   return map;
                               }

                               @Override
                               public Map<String, String> getHeaders() throws AuthFailureError {
                                   Map<String, String> params = new HashMap<String, String>();
                                   params.put("Content-Type", "application/x-www-form-urlencoded");
                                   return params;
                               }
                           };

                           jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
                                   DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                   DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
                           AppControler.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

                       }

                   }
               });

               dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });
               dialog.create();
               dialog.show();
           }
       });
    }

    class getProfile extends AsyncTask<String,Void,String>
    {
        String Response = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                Response = new ApiCall(getActivity()).HttpGet(strings[0]);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return Response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
            {
                pDialog.dismiss();
            }
            try
            {
                if (s!=null)
                {
                    JSONObject jsonObject=new JSONObject(s);
                    mName.setText(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
                    mUserCode.setText(jsonObject.getString("user_code"));
                    mContact.setText(jsonObject.getString("contact_no"));
                    mEmail.setText(jsonObject.getString("email_id"));
                    mPassword.setText(getActivity().getSharedPreferences(getString(R.string.prefrence), Context.MODE_PRIVATE).getString("player_password",""));
                }
                else
                {
                    Toast.makeText(getActivity(),"something went wrong please try again!!!",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e)
            {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.getString("status").equals("false")) {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }


}
