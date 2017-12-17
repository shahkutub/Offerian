package com.bynotech.offerian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bynotech.offerian.model.DristictsNameModel;
import com.bynotech.offerian.model.UserLoginResponse_Info;
import com.bynotech.offerian.retrofit.Api;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.AppConstant;
import com.bynotech.offerian.utils.BusyDialog;
import com.bynotech.offerian.utils.NetInfo;

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
    public static LandingActivity landingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        con  = this;
        landingActivity = this;
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
                startActivity(new Intent(con,SignInActivity.class));
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

                AppConstant.listDistrict = heroList;
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


}
