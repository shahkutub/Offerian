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
import com.sadi.offerian.utils.IPAddressUtils;
import com.sadi.offerian.utils.NetInfo;
import com.sadi.offerian.utils.PersistData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
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

public class SignUpActivity extends AppCompatActivity {
    Context con;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;
    private EditText etFullName, etMobile, etPassword;
    private Button btnSubmit;
    private Spinner spinnerArea, spinnerGender;
    private String ip_addressIPv4, os_version, band_name, model, imei,fullname,mobile,password,area,disid,gender;
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
        getLocalIpAddress();

    }


    private void initialization() {
        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        //String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        band_name = Build.MANUFACTURER;
        model = Build.MODEL;
        os_version = String.valueOf(Build.VERSION.SDK_INT);
        String versionRelease = Build.VERSION.RELEASE;

        ip_addressIPv4 = IPAddressUtils.getIPAddress(true); // IPv4
        String ip_addressIPv6 = IPAddressUtils.getIPAddress(false); // IPv6
        Toast.makeText(con, "ip_addressIPv4:"+ip_addressIPv4+"ip_addressIPv6:"+ip_addressIPv6, Toast.LENGTH_SHORT).show();

        Log.e("ip_addressIPv4",ip_addressIPv4);
        Log.e("ip_addressIPv6",ip_addressIPv6);

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
                //startActivity(new Intent(con,SMScodeVarifyActivity.class));

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
                    volleySignupRequestPost();
                }

            }


        });

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


    private void volleySignupRequestPost(){
        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        busyNow.dismis();
//                        Log.e("response",""+response);
//                        Toast.makeText(SignUpActivity.this, "json "+response, Toast.LENGTH_SHORT).show();


                        JSONObject jsonObject = null;
                        int status = 0;
                        int session_id = 0;
                        try {
                            jsonObject = new JSONObject(response);
                             status = jsonObject.getInt("status");
                            session_id = jsonObject.getInt("session_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                            Log.e("session_id",""+session_id);

                            PersistData.setStringData(con,AppConstant.session_id,""+session_id);

                            if (status==200){
                                Toast.makeText(SignUpActivity.this, "Wait please! verification code msg will send to you.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(con,SMScodeVarifyActivity.class));
                            }
                            if(status==201){
                                Toast.makeText(SignUpActivity.this, "Mobile already exist", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(con,SMScodeVarifyActivity.class));
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

        if (NetInfo.isOnline(con)) {
            queue.add(stringRequest);
        }
    }


    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i("ip", "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("", ex.toString());
        }
        return null;
    }

    }


