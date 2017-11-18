package com.sadi.offerian.utils;

import com.sadi.offerian.model.DistrictsResponse;
import com.sadi.offerian.model.DristictsNameModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sadi on 11/17/2017.
 */

public interface ApiService {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("api/apps/getalldistricts")
    Call<List<DristictsNameModel>> getMyJSON();
}
