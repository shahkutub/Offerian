package com.sadi.offerian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sadi.offerian.model.DistrictsResponse;
import com.sadi.offerian.model.DristictsNameModel;
import com.sadi.offerian.retrofit.Api;
import com.sadi.offerian.utils.AlertMessage;
import com.sadi.offerian.utils.ApiService;
import com.sadi.offerian.utils.BusyDialog;
import com.sadi.offerian.utils.NetInfo;
import com.sadi.offerian.utils.RetroClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sadi on 11/12/2017.
 */

public class LandingActivity extends AppCompatActivity {
    Context con;
    private Button btnSignUp,btnLogin;
    private ArrayList<DristictsNameModel> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        con  = this;
        initialization();
    }

    private void initialization() {
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con,SignUpActivity.class));
            }
        });



        getDistricts();

    }

    private void getDistricts() {

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

        Call<List<DristictsNameModel>> call = api.getMyJSON();

        call.enqueue(new Callback<List<DristictsNameModel>>() {
            @Override
            public void onResponse(Call<List<DristictsNameModel>> call, Response<List<DristictsNameModel>> response) {
                List<DristictsNameModel> heroList = response.body();

                //Creating an String array for the ListView
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName_en();
                    Log.e("heroes",""+heroes[i]);
                }


                busyNow.dismis();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<List<DristictsNameModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            busyNow.dismis();
            }
        });
    }



//    protected void registerServer(final String url) {
//
//        if (!NetInfo.isOnline(con)) {
//            AlertMessage.showMessage(con,"Alert","No Internet connection");
//            return;
//        }
//
//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();
//
//        final AsyncHttpClient client = new AsyncHttpClient();
//
//        final RequestParams param = new RequestParams();
//
//        try {
//
////            param.put("email", etEmail.getText().toString());
////            param.put("first_name", etFirstName.getText().toString());
////            param.put("last_name", etLastName.getText().toString());
////            param.put("password", etConfirmPass.getText().toString());
////            param.put("contact_number", etPhone.getText().toString());
////            param.put("device_type", "android");
////            param.put("device_token", PersistData.getStringData(con,AppConstant.GCMID));
////            param.put("login_type", "email");
////            param.put("photo", new File(picture));
//
//            Log.e("","");
//        } catch (final Exception e1) {
//            e1.printStackTrace();
//        }
//
//        client.post(url, param, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                if (busyNow != null) {
//                    busyNow.dismis();
//                }
//                try {
//                    Log.e("Response", ">>" + new String(response));
//                    if (!TextUtils.isEmpty(new String(response))) {
//                        Gson g = new Gson();
//                        LoginResponse mLoginResponse = g.fromJson(new String(response), LoginResponse.class);
//                        if (mLoginResponse.isStatus()) {
//                            // PersistData.setStringData(con,AppConstant.loginRespone,response);
//
//
//                            changeVisibility("photo");
//
//
//                        }else {
//                            AlertMessage.showMessage(con,getString(R.string.app_name),mLoginResponse.getMessage());
//                        }
//                    }
//
//                } catch (final Exception e) {
//
//                    e.printStackTrace();
//                }
//            }
//
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//
//                e.printStackTrace();
//
//                if (busyNow != null) {
//                    busyNow.dismis();
//                }
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//
//            }
//        });
//
//    }

}
