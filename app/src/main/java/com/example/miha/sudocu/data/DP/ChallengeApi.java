package com.example.miha.sudocu.data.DP;


import com.example.miha.sudocu.data.model.Challenge;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface ChallengeApi {
    String URL = "http://mysimple.zzz.com.ua/";

    @GET("getAllScore")
    Call<ArrayList<Challenge>> getAllScore();// метод к которому будим обращатся
    @GET("login")
    Call<Object> login(@QueryMap Map<String,String> options);
}
