package com.rhezarijaya.githubrrpromax.util;

import com.rhezarijaya.githubrrpromax.service.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtil {
    public static final String UNEXPECTED_ERROR = "Unexpected error. Please try again";

    public static APIService getAPIService(){
        return new Retrofit.Builder()
                .baseUrl(Constants.GITHUB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);
    }

    public static String responseCodeFormatter(int code){
        if(code >= 500){
            return code + " - Server Error";
        }else if(code >= 400){
            return code + " - Client Error";
        }else{
            return UNEXPECTED_ERROR;
        }
    }
}
