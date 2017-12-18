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
import com.bynotech.offerian.model.BusinessDirectoryInfo;
import com.bynotech.offerian.retrofit.Api;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.NetInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;


public class BusinessDirectoryFragment extends Fragment {

    private RecyclerView recyclerviewBusiness;
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
        recyclerviewBusiness = (RecyclerView)getView().findViewById(R.id.recyclerviewBusiness);

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

        Call<List<BusinessDirectoryInfo>> call = api.getAllBusiness("20","12.343433","17.334334");

        call.enqueue(new Callback<List<BusinessDirectoryInfo>>() {
            @Override
            public void onResponse(Call<List<BusinessDirectoryInfo>> call, Response<List<BusinessDirectoryInfo>> response) {
                List<BusinessDirectoryInfo> offerInfoList = response.body();


                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity(), VERTICAL, false);
                recyclerviewBusiness.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(con,
                        layoutManager.getOrientation());
                dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));

                recyclerviewBusiness.addItemDecoration(dividerItemDecoration);
                BusinessDirectoryAdapter adapter = new BusinessDirectoryAdapter(offerInfoList, R.layout.raw_business_directory, con);

                recyclerviewBusiness.setAdapter(adapter);;
                for (int i = 0; i < offerInfoList.size(); i++) {
                    //heroes[i] = heroList.get(i).getName_en();
                    Log.e("name",""+offerInfoList.get(i).getName());
                }

                //busyNow.dismis();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<List<BusinessDirectoryInfo>> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }

}
