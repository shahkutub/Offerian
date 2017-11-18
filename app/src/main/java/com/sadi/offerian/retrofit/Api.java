package com.sadi.offerian.retrofit;

import com.sadi.offerian.model.DristictsNameModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sadi on 11/18/2017.
 */

public interface Api {

    String BASE_URL = "http://offerian.com/";

    @GET("api/apps/getalldistricts")
    Call<List<DristictsNameModel>> getMyJSON();
}
