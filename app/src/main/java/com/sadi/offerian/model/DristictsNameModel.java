package com.sadi.offerian.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sadi on 11/15/2017.
 */

public class DristictsNameModel {


    @SerializedName("bd_district_id")
    @Expose
    private String bd_district_id;

    @SerializedName("name_en")
    @Expose
    private String name_en;


    public String getBd_district_id() {
        return bd_district_id;
    }

    public void setBd_district_id(String bd_district_id) {
        this.bd_district_id = bd_district_id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }
}
