package com.sadi.offerian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadi.offerian.utils.AlertMessage;
import com.sadi.offerian.utils.AppConstant;
import com.sadi.offerian.utils.BusyDialog;
import com.sadi.offerian.utils.NetInfo;
import com.sadi.offerian.utils.PersistData;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sadi on 11/21/2017.
 */

public class SMScodeVarifyActivity extends AppCompatActivity{

    Context con;
    EditText etSmsCode;
    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_veryfication);
        con = this;
        //init views
         etSmsCode = (EditText) findViewById(R.id.etSmsCode);
         Button BtnSubmitCode = (Button) findViewById(R.id.BtnSubmitCode);

        //init SmsVerifyCatcher
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                etSmsCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        //set phone number filter if needed
        smsVerifyCatcher.setPhoneNumberFilter("+8804445600111");
        //smsVerifyCatcher.setFilter("regexp");

        //button for sending verification code manual
        BtnSubmitCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etSmsCode.getText().toString())){
                    vollRequestPost();
                }else {
                    AlertMessage.showMessage(con,"Alert!","Enter sms code");
                }
            }
        });
    }

    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void vollRequestPost(){
        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String loginUrl ="http://offerian.com/api/apps/mobilevarification";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        busyNow.dismis();
                        Log.e("response",""+response);
                        Toast.makeText(con, "json "+response, Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            //int session_id = jsonObject.getInt("session_id");
                            //Toast.makeText(SignUpActivity.this, "string "+status, Toast.LENGTH_SHORT).show();

                            // PersistData.setStringData(con,AppConstant.session_id,session_id);

//                            if (status.equalsIgnoreCase("200")){
//                                Toast.makeText(SignUpActivity.this, "Wait please! verification code msg will send to you.", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(con,SMScodeVarifyActivity.class));
//                            }else if(status.equalsIgnoreCase("201")){
//                                Toast.makeText(SignUpActivity.this, "Mobile already exist", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(con,SMScodeVarifyActivity.class));
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                busyNow.dismis();
                //Toast.makeText(getApplicationContext(), "Slow net connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                // Map<String, String> params = new HashMap<>();
                parameters.put("session_id", PersistData.getStringData(con, AppConstant.session_id));
                parameters.put("otp", etSmsCode.getText().toString());

                return parameters;
            }
        };
        //NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (NetInfo.isOnline(con)) {
            queue.add(stringRequest);
        }
    }
}