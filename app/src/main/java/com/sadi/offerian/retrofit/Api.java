package com.sadi.offerian.retrofit;

import com.sadi.offerian.model.DristictsNameModel;
import com.sadi.offerian.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Sadi on 11/18/2017.
 */

public interface Api {

    String BASE_URL = "http://offerian.com/";

    @GET("api/apps/getalldistricts")
    Call<List<DristictsNameModel>> getMyJSON();


    @Headers("Content-Type: application/json")
    @POST("api/apps/signup")
    Call<User> getUser(@Body String body);
}
