package com.bynotech.offerian.activity;

/**
 * Created by NanoSoft on 11/23/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynotech.offerian.R;
import com.bynotech.offerian.fragment.TabFragmentCompany;
import com.bynotech.offerian.model.OfferDetails;
import com.bynotech.offerian.retrofit.Api;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.AppConstant;
import com.bynotech.offerian.utils.NetInfo;
import com.bynotech.offerian.utils.PersistData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Sadi on 11/22/2017.
 */

public class BusinessProfileActivity extends AppCompatActivity {

    Context con;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private ImageView imgBackCompany,imgComVerified;
    private TextView tvCompany,tvRating,tvFollower;
    OfferDetails offerDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);

        con  = this;
        initialization();
    }

    private void initialization() {

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        TabFragmentCompany fragment = new TabFragmentCompany();


        Bundle bundle = new Bundle();
        bundle.putInt("pos", 0);
        PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 0);
        fragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.containerViewCompany, fragment).commit();

        imgBackCompany = (ImageView)findViewById(R.id.imgBackCompany);
        tvCompany = (TextView) findViewById(R.id.tvCompany);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvFollower = (TextView) findViewById(R.id.tvFollower);

        imgBackCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getOfferById(AppConstant.offerId);
    }


    private void getOfferById(String offerId) {

        if(!NetInfo.isOnline(con)){
            AlertMessage.showMessage(con,"Alert","No internet connection!");
        }

//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<OfferDetails> call = api.getOfferData(offerId);

        call.enqueue(new Callback<OfferDetails>() {
            @Override
            public void onResponse(Call<OfferDetails> call, Response<OfferDetails> response) {
                offerDetails = response.body();


                tvCompany.setText(offerDetails.getCompany_name().toString());
                //tvRating.setText(offerDetails.getR.toString());
                //tvCompany.setText(offerDetails.getCompany_name().toString());



            }

            @Override
            public void onFailure(Call<OfferDetails> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }
}