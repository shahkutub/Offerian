package com.bynotech.offerian.retrofit;

import com.bynotech.offerian.model.BusinessDirectoryInfo;
import com.bynotech.offerian.model.CommonResponse;
import com.bynotech.offerian.model.DristictsNameModel;
import com.bynotech.offerian.model.OfferDetails;
import com.bynotech.offerian.model.OfferInfo;
import com.bynotech.offerian.model.ReviewInfo;

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

    @GET("api/apps/getalldistricts")
    Call<List<DristictsNameModel>> getMyJSON();

//    @POST("/api/apps/alloffer")
//    Call<List<OfferInfo>> getAllOffers();

    @FormUrlEncoded
    @POST("/api/apps/alloffer")
    Call<List<OfferInfo>> getAllOffers(
            @Field("session_id") String session_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("/api/apps/allbusiness")
    Call<List<BusinessDirectoryInfo>> getAllBusiness(
            @Field("session_id") String session_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );



    @FormUrlEncoded
    @POST("/api/apps/allreview")
    Call<List<ReviewInfo>> getAllReview(
            @Field("session_id") String session_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );


    @FormUrlEncoded
    @POST("/api/apps/offerdata")
    Call<OfferDetails> getOfferData(
            @Field("offer_id") String offer_id
    );

    @FormUrlEncoded
    @POST("/api/apps/offerorder")
    Call<CommonResponse> placeOrder(
            @Field("session_id") String session_id,
            @Field("campaign_id") String campaign_id
    );

    @FormUrlEncoded
    @POST("/api/apps/saveoffer")
    Call<CommonResponse> saveOffer(
            @Field("session_id") String session_id,
            @Field("campaign_id") String campaign_id
    );

    @FormUrlEncoded
    @POST("/api/apps/updatepassword")
    Call<CommonResponse> updatepassword(
            @Field("session_id") String session_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/apps/resetpassword")
    Call<CommonResponse> resetpassword(
            @Field("user_name") String user_name,
            @Field("ip_address") String ip_address,
            @Field("os") String os,
            @Field("os_version") String os_version,
            @Field("band_name") String band_name,
            @Field("divice_name") String divice_name,
            @Field("model") String model,
            @Field("imei") String imei,
            @Field("fcm_token") String fcm_token,
            @Field("operator") String operator,
            @Field("screen_size") String screen_size

    );

}
