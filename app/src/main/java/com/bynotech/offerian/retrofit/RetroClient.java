//package com.bynotech.offerian.retrofit;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by Sadi on 11/17/2017.
// */
//
//public class RetroClient {
//
//    /********
//     * URLS
//     *******/
//    private static final String ROOT_URL = "http://offerian.com/";
//
//    /**
//     * Get Retrofit Instance
//     */
//    private static Retrofit getRetrofitInstance() {
//        return new Retrofit.Builder()
//                .baseUrl(ROOT_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    /**
//     * Get API Service
//     *
//     * @return API Service
//     */
//    public static ApiService getApiService() {
//        return getRetrofitInstance().create(ApiService.class);
//    }
//}
