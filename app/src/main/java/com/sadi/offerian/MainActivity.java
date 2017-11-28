package com.sadi.offerian;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadi.offerian.fragment.TabFragment;
import com.sadi.offerian.fragment.TabFragmentMain;
import com.sadi.offerian.utils.AppConstant;
import com.sadi.offerian.utils.BusyDialog;
import com.sadi.offerian.utils.NetInfo;
import com.sadi.offerian.utils.PersistData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
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

    private TextView tvProfile,tvMyOrder,tvMyFavorite,tvMyBusiness,tvSettings,tvLogOut,tvProName
            ,tvWallet;

    String profile_picture,full_name,reward_point,user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con  = this;
        initialization();
    }

    private void initialization() {

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

        TabFragmentMain fragment = new TabFragmentMain();

        Bundle bundle = new Bundle();
        bundle.putInt("pos", 0);
        PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 0);
        fragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.containerView, fragment).commit();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, new TabFragmentMain()).commit();

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
        tvLogOut = (TextView)findViewById(R.id.tvLogOut);
        tvProName = (TextView)findViewById(R.id.tvProName);
        tvWallet = (TextView)findViewById(R.id.tvWallet);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(con,EditProfileActivity.class));
            }
        });

        volleyRequestProfile();
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
        final BusyDialog busyNow = new BusyDialog(con, true,false);
        busyNow.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://offerian.com/api/apps/getuserdata/20";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        busyNow.dismis();

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
                busyNow.dismis();
                //Toast.makeText(getApplicationContext(), "Slow net connection", Toast.LENGTH_SHORT).show();
            }
        }) ;

        //NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (NetInfo.isOnline(con)) {
            queue.add(stringRequest);
        }
    }

}
