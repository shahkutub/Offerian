package com.bynotech.offerian.model;

/**
 * Created by NanoSoft on 11/30/2017.
 */

public class OfferInfo {
    private String company_logo;
    private String company_name;
    private String company_page_name;
    private String company_varification;
    private String campaign_ribon;
    private String description;
    private String reward_point;
    private String offer_id;
    private String delivery_type;
    private String product_photo;
    private String offer_limit_total;
    private String offer_response;
    private String offer_left;
    private String item_type;
    private String area_name;

    public OfferInfo(String company_logo, String company_name, String company_page_name,
                     String company_varification, String campaign_ribon, String
                             description, String reward_point, String offer_id, String
                             delivery_type, String product_photo, String offer_limit_total,
                     String offer_response, String offer_left, String item_type,
                     String area_name) {
        this.company_logo = company_logo;
        this.company_name = company_name;
        this.company_page_name = company_page_name;
        this.company_varification = company_varification;
        this.campaign_ribon = campaign_ribon;
        this.description = description;
        this.reward_point = reward_point;
        this.offer_id = offer_id;
        this.delivery_type = delivery_type;
        this.product_photo = product_photo;
        this.offer_limit_total = offer_limit_total;
        this.offer_response = offer_response;
        this.offer_left = offer_left;
        this.item_type = item_type;
        this.area_name = area_name;
    }

    public OfferInfo() {
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_page_name() {
        return company_page_name;
    }

    public void setCompany_page_name(String company_page_name) {
        this.company_page_name = company_page_name;
    }

    public String getCompany_varification() {
        return company_varification;
    }

    public void setCompany_varification(String company_varification) {
        this.company_varification = company_varification;
    }

    public String getCampaign_ribon() {
        return campaign_ribon;
    }

    public void setCampaign_ribon(String campaign_ribon) {
        this.campaign_ribon = campaign_ribon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReward_point() {
        return reward_point;
    }

    public void setReward_point(String reward_point) {
        this.reward_point = reward_point;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getOffer_limit_total() {
        return offer_limit_total;
    }

    public void setOffer_limit_total(String offer_limit_total) {
        this.offer_limit_total = offer_limit_total;
    }

    public String getOffer_response() {
        return offer_response;
    }

    public void setOffer_response(String offer_response) {
        this.offer_response = offer_response;
    }

    public String getOffer_left() {
        return offer_left;
    }

    public void setOffer_left(String offer_left) {
        this.offer_left = offer_left;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
