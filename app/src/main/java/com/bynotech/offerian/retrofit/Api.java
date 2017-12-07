package com.bynotech.offerian.retrofit;

import com.bynotech.offerian.model.BusinessInfo;
import com.bynotech.offerian.model.DristictsNameModel;
import com.bynotech.offerian.model.OfferInfo;
import com.bynotech.offerian.model.UserLoginResponse_Info;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sadi on 11/18/2017.
 */

public interface Api {

    String BASE_URL = "http://offerian.com/";
    String BASE_URLsreda = "http://192.168.0.119/renewableenergy/api/";
            //"login";

    @GET("api/apps/getalldistricts")
    Call<List<DristictsNameModel>> getMyJSON();

    @FormUrlEncoded
    @POST("login")
    Call<UserLoginResponse_Info> login(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/apps/alloffer")
    Call<List<OfferInfo>> getAllOffers(
            @Field("session_id") String session_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("/api/apps/allbusiness")
    Call<List<BusinessInfo>> getAllBusiness(
            @Field("session_id") String session_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

}
