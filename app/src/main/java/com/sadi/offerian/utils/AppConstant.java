package com.sadi.offerian.utils;


import com.sadi.offerian.model.DristictsNameModel;

import java.util.ArrayList;
import java.util.List;

public class AppConstant {


    public static List<DristictsNameModel> listDistrict = new ArrayList<>();

    public static final String[] FACEBOOK_PERMISSION = {"email", "user_about_me", "read_stream", "user_photos", "public_profile" };
    public static String COLOR_MAIN = "#A551D0";
    public static String id="id";
    //public static String pushID="pushID";
    public static String token="token";

    public static final String COLOR_DEFAULT_MAIN = "#A551D0";
    public static final String COLOR_DEFAULT_SECONDARY = "#FFFFFF";


    public static final String COLOR_SETTINGS = "#4C436E";
    public static final String COLOR_SHARE = "#0054A5";
    public static final String BTN_COLOR_ORIGIN = "#B166D6";

    public static int POSITION = 0;
    public static String GCMID = "gcmid";
    public static String SystemTime = "systemtime";
    public static String CATEGORY_RESPONSE = "category_response";
    public static String HOMERESPONSE = "homeresponse";
    public static String CATEGORYTYPE = "";
    public static String CATEGORYTITLE = "";
    public static int scroolBeforeLatItem =2;
    public static String TITLE= "";
    public static String URL = "";
    public static String FRAGMENTPOSITON ="fragmentposition";
    public static boolean REFRESHFLAG = false;
    public static boolean DETAILSSETTING = false;

    public static final String[] allMonth = {"জানুয়ারী","ফেব্রুয়ারি","মার্চ","এপ্রিল","মে","জুন","জুলাই","অগাস্ট","সেপ্টেম্বর","অক্টবর","নভেম্বর","ডিসেম্বর"};

    private static final char[] banglaDigits = {'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
    private static final char[] englishDigits = {'0','1','2','3','4','5','6','7','8','9'};
    public static String session_id;


    public  static final String getDigitBanglaFromEnglish(String number){
        if(number==null)
            return new String("");
        StringBuilder builder = new StringBuilder();
        try{
            for(int i =0;i<number.length();i++){
                if(Character.isDigit(number.charAt(i))){
                    if(((int)(number.charAt(i))-48)<=9){
                        builder.append(banglaDigits[(int)(number.charAt(i))-48]);
                    }else{
                        builder.append(number.charAt(i));
                    }
                }else{
                    builder.append(number.charAt(i));
                }
            }
        }catch(Exception e){

            return new String("");
        }
        return builder.toString();
    }

    public static int currentItem =0;
    public static String newsID ="";
    public static String newsDeatailsId ="";
    public static String oneTimeCall = "oneTimeCall";
    public static boolean openPush =true;

    public static String notificationSettingsOn = "notificationSettingsOn";
    public static String notificationSoundOn = "notificationSoundOn";
    public static String defaultValueSet = "defaultValueSet";
    public static String notificationVibrateOn = "notificationVibrateOn";
}
