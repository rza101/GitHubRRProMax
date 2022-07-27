package com.rhezarijaya.githubrrpromax.service;

import com.rhezarijaya.githubrrpromax.model.SearchResponse;
import com.rhezarijaya.githubrrpromax.model.UserDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("search/users")
    Call<SearchResponse> getSearch(@Header("Authentication") String api_token, @Query("q") String q);

    @GET("users/{username}")
    Call<UserDetail> getUserDetail(@Header("Authentication") String api_token, @Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<UserDetail>> getUserFollowers(@Header("Authentication") String api_token, @Path("username") String username);

    @GET("users/{username}/following")
    Call<List<UserDetail>> getUserFollowing(@Header("Authentication") String api_token, @Path("username") String username);
}
