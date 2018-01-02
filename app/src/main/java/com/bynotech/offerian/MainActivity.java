package com.bynotech.offerian;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bynotech.offerian.fragment.TabFragmentMain;
import com.bynotech.offerian.utils.AppConstant;
import com.bynotech.offerian.utils.LocationMgr;
import com.bynotech.offerian.utils.NetInfo;
import com.bynotech.offerian.utils.PersistData;
import com.bynotech.offerian.utils.PersistentUser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity{
    Context con;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private ImageView imgBell,imgSearch;
    private CircleImageView profile_image;
    private AutoCompleteTextView autoComSearch;
    private LinearLayout linProfile;
    private String[] searchItems = {"AZAD pharma","SADI pharma","Kamal pharma", "Azhar pharma", "Bahar pharma", "sumon pharma"};

    private TextView tvTitle,tvProfile,tvMyOrder,tvMyFavorite,tvMyBusiness,tvSettings,tvLogOut,tvProName
            ,tvWallet;

    String profile_picture,full_name,reward_point,user_name;


    LocationMgr mgr;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con  = this;
        //getLocation();


        initialization();
        statusCheck();
    }

    public void statusCheck() {

        mgr = new LocationMgr(con);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                if (mgr.mGoogleApiClient == null) {
                    mgr.buildGoogleApiClient();
                }
            }
        } else {
            if (mgr.mGoogleApiClient == null) {
                mgr.buildGoogleApiClient();
            }
        }
    }




    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mgr.mGoogleApiClient == null) {
                            mgr.buildGoogleApiClient();
                        }
                        // mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    private void initialization() {
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();



        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        TabFragmentMain fragment = new TabFragmentMain(tvTitle);

        Bundle bundle = new Bundle();
        bundle.putInt("pos", 0);
        PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 0);
        fragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.containerView, fragment).commit();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, new TabFragmentMain(tvTitle)).commit();

                return true;
            }

        });
        imgSearch = (ImageView)findViewById(R.id.imgSearch);
        imgBell = (ImageView)findViewById(R.id.imgBell);
        autoComSearch = (AutoCompleteTextView)findViewById(R.id.autoComSearch);
        autoComInit(autoComSearch);

        ArrayAdapter adapterSearch = new ArrayAdapter(con, android.R.layout.simple_list_item_1,searchItems);
        autoComSearch.setAdapter(adapterSearch);
        autoComSearch.setThreshold(1);//start searching from 1 character

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgSearch.setVisibility(View.GONE);
                autoComSearch.setVisibility(View.VISIBLE);

                autoComSearch.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        autoComSearch.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                                MotionEvent.ACTION_DOWN, 0, 0, 0));
                        autoComSearch.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                                MotionEvent.ACTION_UP, 0, 0, 0));

                    }
                }, 200);
            }

        });


        linProfile = (LinearLayout)findViewById(R.id.linProfile);
        tvProfile = (TextView)findViewById(R.id.tvProfile);
        tvMyOrder = (TextView)findViewById(R.id.tvMyOrder);
        tvMyBusiness = (TextView)findViewById(R.id.tvMyBusiness);
        tvMyFavorite = (TextView)findViewById(R.id.tvMyFavorite);
        tvSettings = (TextView)findViewById(R.id.tvSettings);
        tvLogOut = (TextView)findViewById(R.id.tvLogOut);
        tvProName = (TextView)findViewById(R.id.tvProName);
        tvWallet = (TextView)findViewById(R.id.tvWallet);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersistentUser.logOut(con);
                PersistData.setStringData(con,AppConstant.session_id,"");
                finish();
            }
        });
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(con,EditProfileActivity.class));
            }
        });

        volleyRequestProfile();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void autoComInit(final AutoCompleteTextView autoCompleteTextView) {

        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_cross, 0);
        String value = "";//any text you are pre-filling in the EditText
        final Drawable x = getResources().getDrawable(R.drawable.icon_cross);//your x image, this one from standard android images looks pretty good actually
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        autoCompleteTextView.setCompoundDrawables(null, null, value.equals("") ? null : x, null);
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (autoCompleteTextView.getCompoundDrawables()[2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() > autoCompleteTextView.getWidth() - autoCompleteTextView.getPaddingRight() - x.getIntrinsicWidth()) {
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.setCompoundDrawables(null, null, null, null);
                }
                return false;
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteTextView.setCompoundDrawables(null, null, autoCompleteTextView.getText().toString().equals("") ? null : x, null);

                if(s.length()==0){
                    imgSearch.setVisibility(View.VISIBLE);
                    autoComSearch.setVisibility(View.GONE);
                    hideSoftKeyboard(MainActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    }
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void volleyRequestProfile(){
//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://offerian.com/api/apps/getuserdata/20";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //busyNow.dismis();

                        Log.e("response",""+response);
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = new JSONObject(response);
                            profile_picture = jsonObject.getString("profile_picture");
                            full_name = jsonObject.getString("full_name");
                            reward_point = jsonObject.getString("reward_point");
                            user_name = jsonObject.getString("user_name");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        tvProName.setText(full_name);
                        tvWallet.setText("Wallet point:"+reward_point);
                        if(profile_picture!=null){
                            Picasso.with(con).load(profile_picture).into(profile_image);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // busyNow.dismis();
                //Toast.makeText(getApplicationContext(), "Slow net connection", Toast.LENGTH_SHORT).show();
            }
        }) ;

        //NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (NetInfo.isOnline(con)) {
            queue.add(stringRequest);
        }
    }

    public void exitFromApp() {
        final CharSequence[] items = { "NO", "YES" };
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Exit from app?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        return;
                    case 1:
                        // onStopRecording();
                        LandingActivity.landingActivity.finish();
                        finish();


                        break;
                    default:
                        return;
                }
            }
        });
        builder.show();
        builder.create();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if(buttonView.getVisibility()==View.GONE){
//                buttonView.setVisibility(View.VISIBLE);
//            }else {
            exitFromApp();
            // }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    

}
