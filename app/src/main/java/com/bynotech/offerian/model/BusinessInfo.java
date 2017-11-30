package com.bynotech.offerian.model;

/**
 * Created by NanoSoft on 11/30/2017.
 */

public class BusinessInfo {
    private String page_name;
    private String logo_url;
    private String name;
    private String business_category;
    private String company_varification;
    private String area_name;

    public BusinessInfo() {
    }

    public BusinessInfo(String page_name, String logo_url, String name, String business_category, String company_varification, String area_name) {
        this.page_name = page_name;
        this.logo_url = logo_url;
        this.name = name;
        this.business_category = business_category;
        this.company_varification = company_varification;
        this.area_name = area_name;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusiness_category() {
        return business_category;
    }

    public void setBusiness_category(String business_category) {
        this.business_category = business_category;
    }

    public String getCompany_varification() {
        return company_varification;
    }

    public void setCompany_varification(String company_varification) {
        this.company_varification = company_varification;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
