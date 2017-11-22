package com.sadi.offerian;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadi.offerian.model.User;
import com.sadi.offerian.retrofit.Api;
import com.sadi.offerian.utils.AlertMessage;
import com.sadi.offerian.utils.AppConstant;
import com.sadi.offerian.utils.BusyDialog;
import com.sadi.offerian.utils.CustomRequest;
import com.sadi.offerian.utils.NetInfo;
import com.sadi.offerian.utils.PersistData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission_group.CAMERA;

/**
 * Created by Sadi on 11/12/2017.
 */

public class SignUpActivity extends AppCompatActivity implements Callback<User> {
    Context con;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;
    private EditText etFullName, etMobile, etPassword;
    private Button btnSubmit;
    private Spinner spinnerArea, spinnerGender;
    private String ip_address, os_version, band_name, model, imei,fullname,mobile,password,area,disid,gender;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE};

    private String loginUrl = "http://offerian.com/api/apps/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        con = this;
        initialization();


    }


    private void initialization() {
        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Toast.makeText(con, ""+wm.getConnectionInfo().getIpAddress(), Toast.LENGTH_SHORT).show();
        band_name = Build.MANUFACTURER;
        model = Build.MODEL;
        os_version = String.valueOf(Build.VERSION.SDK_INT);
        String versionRelease = Build.VERSION.RELEASE;
//        requestPermission();
//        checkPermission();

        if(checkPermission()){
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            imei = String.valueOf(telephonyManager.getDeviceId());


        }else if(!checkPermission()){
            requestPermission();
        }


        etFullName = (EditText)findViewById(R.id.etFullName);
        etMobile = (EditText)findViewById(R.id.etMobile);
        etPassword = (EditText)findViewById(R.id.etPassword);
        spinnerArea = (Spinner)findViewById(R.id.spinnerArea);
        spinnerGender = (Spinner)findViewById(R.id.spinnerGender);

        List<String> listdisName = new ArrayList<>();
        List<String> listGender = new ArrayList<>();
        listGender.add(0,"Select your gender");
        listGender.add(1,"Male");
        listGender.add(2,"Female");

        listdisName.add(0,"Select your Area");

        for(int i=0;i<AppConstant.listDistrict.size();i++){
            listdisName.add(AppConstant.listDistrict.get(i).getName_en());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, listdisName);
        ArrayAdapter<String> adapterGen = new ArrayAdapter<String>(this, R.layout.spinner_item, listGender);
        spinnerArea.setAdapter(adapter);
        spinnerGender.setAdapter(adapterGen);

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = spinnerArea.getSelectedItem().toString();

                if(position>0){
                    for(int i = 0; i<AppConstant.listDistrict.size();i++){
                        if(area.equalsIgnoreCase(AppConstant.listDistrict.get(i).getName_en())){
                            disid = AppConstant.listDistrict.get(i).getBd_district_id();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = spinnerGender.getSelectedItem().toString();
                if(position>0){
                    if(spinnerGender.getSelectedItem().toString().equalsIgnoreCase("Male")){
                        gender = "1";
                    }else {
                        gender = "2";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etFullName.getText().toString())){
                    AlertMessage.showMessage(con,"Alert!","Enter Full name.");
                }else if(TextUtils.isEmpty(etMobile.getText().toString())){
                    AlertMessage.showMessage(con,"Alert!","Enter mobile your number.");
                }else if(TextUtils.isEmpty(etPassword.getText().toString())){
                    AlertMessage.showMessage(con,"Alert!","Enter password.");
                }else if(area.equalsIgnoreCase("Select your Area")){
                    AlertMessage.showMessage(con,"Alert!","Select your Area.");
                }else if(gender.equalsIgnoreCase("Select your gender")){
                    AlertMessage.showMessage(con,"Alert!","Select your gender.");

                }else {
                     fullname = etFullName.getText().toString();
                     mobile = etMobile.getText().toString();
                     password = etPassword.getText().toString();
                     area = spinnerArea.getSelectedItem().toString();
                     gender = spinnerGender.getSelectedItem().toString();
                    vollRequestPost();
                }

            }


        });

    }

//    private void signUpToserver() {
//
//        busyDialog = new BusyDialog(con,false);
//
//        busyDialog.show();
//
////        Map<String, String> params = new HashMap<>();
////        params.put("full_name", fullname);
////        params.put("mobile", mobile);
////        params.put("bd_district_id", disid);
////        params.put("gender", gender);
////        params.put("password", password);
////        params.put("ip_address", ip_address);
////        params.put("os", "android");
////        params.put("os_version", os_version);
////        params.put("band_name", band_name);
////        params.put("model", model);
////        params.put("imei", imei);
////        params.put("operator", "gp");
////        params.put("screen_size", "355,750");
////                     signupToServer();
////                    startActivity(new Intent(con,MainActivity.class));
//
//        Map<String, String> params = new HashMap<>();
//        params.put("full_name", fullname);
//        params.put("mobile", mobile);
//        params.put("bd_district_id", disid);
//        params.put("gender", gender);
//        params.put("password", password);
//        params.put("ip_address",  "122.122.122.122");
//        params.put("imei", imei);
//        params.put("operator", "gp");
//        params.put("screen_size", "355,750");
//
//
////        RequestQueue requestQueue = Volley.newRequestQueue(con);
////        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, "http://offerian.com/api/apps/signup", params,
////                createRequestSuccessListener(), createRequestErrorListener());
////        requestQueue.add(jsObjRequest);
//    }
//    private com.android.volley.Response.Listener<JSONObject> createRequestSuccessListener() {
//        com.android.volley.Response.Listener<JSONObject> response = new com.android.volley.Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                busyDialog.dismis();
//                Log.e("response",""+response.toString());
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String successValue = jsonObject.getString("success");
//                    String message = jsonObject.getString("message");
//
//
//                    String status = response.get("status").toString();
//                    String session_id = response.get("session_id").toString();
//                    PersistData.setStringData(con,AppConstant.session_id,session_id);
//
//                    if (status.equalsIgnoreCase("200") ){
//                        Toast.makeText(SignUpActivity.this, "Wait please! verification code msg will send to you.", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(con,SMScodeVarifyActivity.class));
//                    }else if(status.equalsIgnoreCase("201")){
//                        Toast.makeText(SignUpActivity.this, "Mobile already exist", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        return response;
//    }


//    private com.android.volley.Response.ErrorListener createRequestErrorListener() {
//        com.android.volley.Response.ErrorListener errorResponse = new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                busyDialog.dismis();
//                Log.e("errorResponse",""+error.toString());
//            }
//        };
//
//        return errorResponse;
//    }


    private void signupToServer() {

        if(!NetInfo.isOnline(con)){
            AlertMessage.showMessage(con,"Alert","No internet connection!");
        }

        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);


        // prepare call in Retrofit 2.0
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", "sample@gmail.com");
            paramObject.put("pass", "4384984938943");

            Call<User> userCall = api.getUser(paramObject.toString());
            userCall.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {

    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readPhoneAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    imei = String.valueOf(telephonyManager.getDeviceId());
                  //  Toast.makeText(con, ""+imei, Toast.LENGTH_SHORT).show();

                    if (locationAccepted && cameraAccepted && readPhoneAccepted) {
                       // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    } else {

                        //Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_PHONE_STATE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SignUpActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void vollRequestPost(){
        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        busyNow.dismis();
                        Log.e("response",""+response);
                        Toast.makeText(SignUpActivity.this, "json "+response, Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            //int session_id = jsonObject.getInt("session_id");
                            //Toast.makeText(SignUpActivity.this, "string "+status, Toast.LENGTH_SHORT).show();

                            // PersistData.setStringData(con,AppConstant.session_id,session_id);

                            if (status.equalsIgnoreCase("200")){
                                Toast.makeText(SignUpActivity.this, "Wait please! verification code msg will send to you.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(con,SMScodeVarifyActivity.class));
                            }else if(status.equalsIgnoreCase("201")){
                                Toast.makeText(SignUpActivity.this, "Mobile already exist", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(con,SMScodeVarifyActivity.class));
                            }
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
                parameters.put("full_name", fullname);
                parameters.put("mobile", mobile);
                parameters.put("bd_district_id", disid);
                parameters.put("gender", gender);
                parameters.put("password", password);
                parameters.put("ip_address",  "122.122.122.122");
                parameters.put("imei", imei);
                parameters.put("operator", "gp");
                parameters.put("screen_size", "355,750");
                return parameters;
            }
        };
        //NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (NetInfo.isOnline(con)) {
            queue.add(stringRequest);
        }
    }


    }


