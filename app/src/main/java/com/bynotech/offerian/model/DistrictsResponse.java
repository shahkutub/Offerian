package com.bynotech.offerian.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sadi on 11/15/2017.
 */

public class DistrictsResponse {

    @SerializedName("districts")
    @Expose
    private ArrayList<DristictsNameModel> districts = new ArrayList<>();

    public ArrayList<DristictsNameModel> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<DristictsNameModel> districts) {
        this.districts = districts;
    }
}
