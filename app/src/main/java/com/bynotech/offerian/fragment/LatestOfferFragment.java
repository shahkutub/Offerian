package com.bynotech.offerian.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bynotech.offerian.R;
import com.bynotech.offerian.adapter.BusinessDirectoryAdapter;
import com.bynotech.offerian.adapter.OfferAdapter;
import com.bynotech.offerian.model.BusinessDirectoryInfo;
import com.bynotech.offerian.model.OfferInfo;
import com.bynotech.offerian.retrofit.Api;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.NetInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;


public class LatestOfferFragment extends Fragment {

    private RecyclerView recyclerviewLatestOffer;
    private LinearLayoutManager mLayoutManager;
    ProgressBar progressShow;
    private boolean isViewShown = false;
    Context con;
    private boolean bgflag = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lattest_offer,null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        initUI();
    }


    public void initUI() {
        recyclerviewLatestOffer = (RecyclerView)getView().findViewById(R.id.recyclerviewLatestOffer);

        getLatestOffers();
    }


    private void getLatestOffers() {

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

        Call<List<OfferInfo>> call = api.getAllOffers("20","12.343433","17.334334");

        call.enqueue(new Callback<List<OfferInfo>>() {
            @Override
            public void onResponse(Call<List<OfferInfo>> call, Response<List<OfferInfo>> response) {
                List<OfferInfo> offerInfoList = response.body();


                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
                recyclerviewLatestOffer.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(con,
                        layoutManager.getOrientation());
                dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));

                recyclerviewLatestOffer.addItemDecoration(dividerItemDecoration);
                OfferAdapter adapter = new OfferAdapter(offerInfoList,R.layout.raw_offer,con);

                recyclerviewLatestOffer.setAdapter(adapter);;
                for (int i = 0; i < offerInfoList.size(); i++) {
                    //heroes[i] = heroList.get(i).getName_en();
                    Log.e("Company name",""+offerInfoList.get(i).getCompany_name());
                }


                // busyNow.dismis();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<List<OfferInfo>> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }

}
