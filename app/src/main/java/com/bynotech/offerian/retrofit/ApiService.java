//package com.bynotech.offerian.retrofit;
//
//import com.bynotech.offerian.model.DristictsNameModel;
//import com.bynotech.offerian.model.User;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.Headers;
//import retrofit2.http.POST;
//
///**
// * Created by Sadi on 11/17/2017.
// */
//
//public interface ApiService {
//    /*
//    Retrofit get annotation with our URL
//    And our method that will return us the List of ContactList
//    */
//    @GET("api/apps/getalldistricts")
//    Call<List<DristictsNameModel>> getMyJSON();
//
//    @Headers("Content-Type: application/json")
//    @POST("login")
//    Call<User> getUser(@Body String body);
//}
