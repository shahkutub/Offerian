package com.bynotech.offerian;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.AppConstant;
import com.bynotech.offerian.utils.BusyDialog;
import com.bynotech.offerian.utils.IPAddressUtils;
import com.bynotech.offerian.utils.NetInfo;
import com.bynotech.offerian.utils.PersistData;
import com.bynotech.offerian.utils.PersistentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission_group.CAMERA;

/**
 * Created by Sadi on 11/12/2017.
 */

public class SignInActivity extends AppCompatActivity {
    Context con;
    private EditText etMobileLogIn,etPassLogin;
    private Button BtnLogin;
    private String ip_addressIPv4, os_version, band_name, model,screen_size,imei,fullname,mobile,password,area,disid,gender;
    private TextView tvForgotPass;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        con  = this;
        initialization();
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
        //Toast.makeText(con, "ip_addressIPv4:"+ip_addressIPv4+"ip_addressIPv6:"+ip_addressIPv6, Toast.LENGTH_SHORT).show();

        Log.e("ip_addressIPv4",ip_addressIPv4);
        Log.e("ip_addressIPv6",ip_addressIPv6);
        screen_size = getScreenResolution(con);
        if(checkPermission()){
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            imei = String.valueOf(telephonyManager.getDeviceId());


        }else if(!checkPermission()){
            requestPermission();
        }

        etMobileLogIn = (EditText)findViewById(R.id.etMobileLogIn);
        etPassLogin = (EditText)findViewById(R.id.etPassLogin);

        BtnLogin = (Button) findViewById(R.id.BtnLogin);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogueForgotpass();
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etMobileLogIn.getText().toString())){
                    AlertMessage.showMessage(con,"Alert!","Enter user name");
                }else if(TextUtils.isEmpty(etPassLogin.getText().toString())){
                    AlertMessage.showMessage(con,"Alert!","Enter password");
                }else {
                    mobile = etMobileLogIn.getText().toString();
                    password = etPassLogin.getText().toString();
                    //startActivity(new Intent(con,MainActivity.class));
                    volleySignInRequestPost();
                }

            }
        });
    }

    private void dialogueForgotpass() {

        Dialog dialog = new Dialog(con);
        dialog.setContentView(R.layout.forgot_pass);
        EditText etforGotPass = (EditText)dialog.findViewById(R.id.etforGotPass);
        Button btnSearchAc = (Button)dialog.findViewById(R.id.btnSearchAc);

        dialog.show();




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
                    Toast.makeText(con, ""+imei, Toast.LENGTH_SHORT).show();

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
        new AlertDialog.Builder(con)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void volleySignInRequestPost(){
        if(!NetInfo.isOnline(con)){
            AlertMessage.showMessage(con,"Alert!","No internet Connection");
        }
        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();
        String loginUrl = "http://offerian.com/api/apps/login";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        busyNow.dismis();
                        Log.e("response",""+response);

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

                        PersistData.setStringData(con, AppConstant.session_id,""+session_id);

                        if (status==200){
                            PersistentUser.setLogin(con);
                            Toast.makeText(con, "Login Success.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(con, MainActivity.class));
                            finish();
                        }
                        if(status==201){
                            Toast.makeText(con, "User not Found.", Toast.LENGTH_SHORT).show();

                        }
                        if(status==202){
                            Toast.makeText(con, "password incorrect.", Toast.LENGTH_SHORT).show();
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

                parameters.put("user_name", mobile);
               // parameters.put("mobile", mobile);
//                parameters.put("bd_district_id", disid);
//                parameters.put("gender", gender);
                parameters.put("password", password);
                //parameters.put("reffered_by", "");
                parameters.put("ip_address",  "122.122.122.122");
                parameters.put("os",  "android");
                parameters.put("os_version",  os_version);
                parameters.put("band_name",  band_name);
                parameters.put("divice_name",  band_name);
                parameters.put("model",  model);
                parameters.put("imei", imei);
                parameters.put("fcm_token", PersistData.getStringData(con,AppConstant.fcmToken));
                parameters.put("operator", "gp");
                parameters.put("screen_size", screen_size);
                return parameters;
            }
        };

        if (NetInfo.isOnline(con)) {
            queue.add(stringRequest);
        }
    }

    private String getScreenResolution(Context context)
    {
        String sc_size="";
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        sc_size = String.valueOf(width)+","+String.valueOf(height);
        return sc_size;
    }
}