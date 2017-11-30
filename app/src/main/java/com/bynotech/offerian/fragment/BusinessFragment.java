package com.bynotech.offerian.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bynotech.offerian.R;
import com.bynotech.offerian.model.BusinessInfo;
import com.bynotech.offerian.model.OfferInfo;
import com.bynotech.offerian.retrofit.Api;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.BusyDialog;
import com.bynotech.offerian.utils.NetInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BusinessFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    ProgressBar progressShow;
    private boolean isViewShown = false;
    Context con;
    private boolean bgflag = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.business,null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        initUI();
    }


    public void initUI() {
//        swipeRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);
//        progressShow = getView().findViewById(R.id.progressShow);
//        mRecyclerView = getView().findViewById(R.id.recyclerview);
////
////        Drawable dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
////        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
////        mRecyclerView.addItemDecoration(dividerItemDecoration);
//
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(con);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                bgflag = true;
//               // requestGetNeslist(AllURL.getHomeNews());
//            }
//        });

        getAllBusiness();
    }


    private void getAllBusiness() {

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

        Call<List<BusinessInfo>> call = api.getAllBusiness("20","12.343433","17.334334");

        call.enqueue(new Callback<List<BusinessInfo>>() {
            @Override
            public void onResponse(Call<List<BusinessInfo>> call, Response<List<BusinessInfo>> response) {
                List<BusinessInfo> offerInfoList = response.body();

                for (int i = 0; i < offerInfoList.size(); i++) {
                    //heroes[i] = heroList.get(i).getName_en();
                    Log.e("name",""+offerInfoList.get(i).getName());
                }

                //busyNow.dismis();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<List<BusinessInfo>> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }

}
