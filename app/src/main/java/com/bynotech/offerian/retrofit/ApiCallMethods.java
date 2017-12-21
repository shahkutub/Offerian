package com.bynotech.offerian.retrofit;

import android.support.v7.widget.LinearLayoutManager;

import com.bynotech.offerian.model.CommonResponse;
import com.bynotech.offerian.model.OfferDetails;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.NetInfo;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by NanoSoft on 12/21/2017.
 */

public class ApiCallMethods {



    public void saveOffer(android.content.Context context, String session_id, String campaign_id) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert","No internet connection!");
        }

//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<CommonResponse> call = api.saveOffer(session_id,campaign_id);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse data = response.body();


            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }


    public void placeOrder(android.content.Context context, String session_id, String campaign_id) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert","No internet connection!");
        }

//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<CommonResponse> call = api.placeOrder(session_id,campaign_id);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse data = response.body();


            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }

}


