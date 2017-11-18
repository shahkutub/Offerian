package com.sadi.offerian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sadi.offerian.model.DistrictsResponse;
import com.sadi.offerian.model.DristictsNameModel;
import com.sadi.offerian.utils.ApiService;
import com.sadi.offerian.utils.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //Creating an object of our api interface
        ApiService api = RetroClient.getApiService();

        /**
         * Calling JSON
         */
        Call<DistrictsResponse> call = api.getMyJSON();

        call.enqueue(new Callback<DistrictsResponse>() {
            @Override
            public void onResponse(Call<DistrictsResponse> call, Response<DistrictsResponse> response) {

                Log.e("Districtsresponse",""+response);
                if(response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    contactList = response.body().getDistricts();
                    Log.e("Districts",""+contactList);
                    if(contactList!=null){
                        Toast.makeText(LandingActivity.this, ""+contactList.get(0).getName_en(), Toast.LENGTH_SHORT).show();

                    }



                }
            }

            @Override
            public void onFailure(Call<DistrictsResponse> call, Throwable t) {

            }
        });

    }
}
