package com.bynotech.offerian.retrofit;

import com.bynotech.offerian.model.BusinessInfo;
import com.bynotech.offerian.model.DristictsNameModel;
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
    Call<List<BusinessInfo>> getAllBusiness(
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

}
